package com.fn.ai.notification.slack.infrastructure.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fn.ai.notification.slack.common.CustomException;
import com.fn.ai.notification.slack.common.ErrorType;
import com.fn.ai.notification.slack.presentation.dto.request.SlackRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
public class SlackApiClient {

    @Value("${slack.bot.token}")
    private String botToken;

    private final RestTemplate restTemplate;

    private static final String POST_MESSAGE_URL = "https://slack.com/api/chat.postMessage";

    /**
     * Slack API를 호출하여 메시지 전송 & 전송 시간 반환
     */
    public Timestamp sendMessage(SlackRequestDto requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(botToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = String.format("{\"channel\":\"%s\", \"text\":\"%s\"}",
                requestDto.getRecipientSlackId(),
                requestDto.getMessage());

        HttpEntity<String> httpRequest = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                POST_MESSAGE_URL, HttpMethod.POST, httpRequest, String.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CustomException(ErrorType.SLACK_SEND_FAILED);
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());
            if (!root.get("ok").asBoolean()) {
                if ("channel_not_found".equals(root.get("error").asText())) {
                    throw new CustomException(ErrorType.CHANNEL_NOT_FOUND);
                }
                throw new CustomException(ErrorType.INTERNAL_SERVER_ERROR);
            }
            String tsString = root.get("ts").asText();
            return convertTsToTimestamp(tsString);
        } catch (Exception e) {
            throw new CustomException(ErrorType.INTERNAL_SERVER_ERROR);
        }
    }

    private Timestamp convertTsToTimestamp(String tsString) {
        String[] parts = tsString.split("\\.");
        long seconds = Long.parseLong(parts[0]);
        return new Timestamp(seconds * 1000);
    }
}
