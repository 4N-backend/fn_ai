package com.fn.ai.notification.slack.infrastructure.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fn.ai.notification.slack.common.CustomException;
import com.fn.ai.notification.slack.common.ErrorType;
import com.fn.ai.notification.slack.infrastructure.dto.response.SlackSendResponse;
import com.fn.ai.notification.slack.presentation.dto.request.SlackCreateRequestDto;
import com.fn.ai.notification.slack.presentation.dto.request.SlackUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlackApiClient {

    @Value("${slack.bot.token}")
    private String botToken;

    private final RestTemplate restTemplate;

    private static final String POST_MESSAGE_URL = "https://slack.com/api/chat.postMessage";
    private static final String UPDATE_MESSAGE_URL = "https://slack.com/api/chat.update";

    /**
     * Slack 메시지 생성 요청
     * Slack API를 호출하여 메시지 전송 & 전송 시간 반환
     */
    public SlackSendResponse sendMessage(SlackCreateRequestDto requestDto) {
        HttpEntity<String> httpRequest = createHttpRequest(
                String.format("{\"channel\":\"%s\", \"text\":\"%s\"}",
                        requestDto.getRecipientSlackId(),
                        requestDto.getMessage())
        );

        ResponseEntity<String> response = restTemplate.exchange(
                POST_MESSAGE_URL, HttpMethod.POST, httpRequest, String.class
        );
        checkHttpStatus(response);

        return parseSlackResponse(response.getBody());
    }

    /**
     * Slack 메시지 수정 요청
     * 수정 후의 타임스탬프 반환
     * 기존 메시지 수정에는 채널, 수정 전 메시지의 ts, 새 메시지가 필요
     */
    public SlackSendResponse updateMessage(SlackUpdateRequestDto updateRequestDto, String recipientChannelId, String originalSlackTs) {
        HttpEntity<String> httpRequest = createHttpRequest(
                String.format("{\"channel\":\"%s\", \"ts\":\"%s\", \"text\":\"%s\"}",
                        recipientChannelId,
                        originalSlackTs,
                        updateRequestDto.getMessage())
        );

        ResponseEntity<String> response = restTemplate.exchange(
                UPDATE_MESSAGE_URL, HttpMethod.POST, httpRequest, String.class
        );
        checkHttpStatus(response);

        return parseSlackResponse(response.getBody());
    }


    /**
     * HTTP 요청 생성
     * Bearer 토큰 설정
     * JSON 형식의 body 설정
     * @param body HTTP 요청 body
     */
    private HttpEntity<String> createHttpRequest(String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(botToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }

    /**
     * HTTP 응답 상태 확인
     * 2xx 성공 코드가 아닌 경우 예외 처리
     */
    private void checkHttpStatus(ResponseEntity<String> response) {
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CustomException(ErrorType.SLACK_SEND_FAILED);
        }
    }

    /**
     * Slack API 응답 파싱
     * 응답이 정상적이지 않은 경우 예외 처리
     */
    private SlackSendResponse parseSlackResponse(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(responseBody);
            if (!root.get("ok").asBoolean()) {
                String slackError = root.has("error") ? root.get("error").asText() : "unknown_error";
                if ("channel_not_found".equals(slackError)) {
                    throw new CustomException(ErrorType.CHANNEL_NOT_FOUND);
                }
                throw new CustomException(ErrorType.INTERNAL_SERVER_ERROR);
            }
            String tsString = root.get("ts").asText();
            Timestamp sentAt = convertTsToTimestamp(tsString);
            String channel = root.get("channel").asText();
            return new SlackSendResponse(sentAt, tsString, channel);
        } catch (Exception e) {
            throw new CustomException(ErrorType.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Slack API 응답의 ts 문자열을 Timestamp로 변환
     */
    private Timestamp convertTsToTimestamp(String tsString) {
        String[] parts = tsString.split("\\.");
        long seconds = Long.parseLong(parts[0]);
        return new Timestamp(seconds * 1000);
    }
}