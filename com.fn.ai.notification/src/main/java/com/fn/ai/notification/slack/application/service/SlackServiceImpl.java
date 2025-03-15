package com.fn.ai.notification.slack.application.service;

import com.fn.ai.notification.slack.domain.model.Slack;
import com.fn.ai.notification.slack.domain.repository.SlackRepository;

import com.fn.ai.notification.slack.infrastructure.client.SlackApiClient;
import com.fn.ai.notification.slack.presentation.dto.request.SlackRequestDto;
import com.fn.ai.notification.slack.presentation.dto.response.SlackResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SlackServiceImpl implements SlackService {

    private final SlackRepository slackRepository;
    private final SlackApiClient slackApiClient;

    @Transactional
    @Override
    public SlackResponseDto createSlackMessage(SlackRequestDto requestDto) {
        // Slack API 호출하여 메시지 전송, 전송 시간(Timestamp) 획득
        Timestamp sentAt = slackApiClient.sendMessage(requestDto);

        // 임의 사용자ID 설정 TODO: 실제 사용자ID로 변경
        UUID userId = UUID.randomUUID();

        // userId 임의로 설정하여 Slack 객체 생성
        Slack slack = Slack.of(
                userId,
                requestDto.getMessage(),
                requestDto.getRecipientSlackId(),
                sentAt
        );
        // DB 저장
        slackRepository.save(slack);

        return SlackResponseDto.from(slack);
    }
}
