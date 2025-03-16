package com.fn.ai.notification.slack.application.service;

import com.fn.ai.notification.slack.common.CustomException;
import com.fn.ai.notification.slack.common.ErrorType;
import com.fn.ai.notification.slack.domain.model.Slack;
import com.fn.ai.notification.slack.domain.repository.SlackRepository;
import com.fn.ai.notification.slack.infrastructure.client.SlackApiClient;
import com.fn.ai.notification.slack.infrastructure.dto.response.SlackSendResponse;
import com.fn.ai.notification.slack.presentation.dto.request.SlackCreateRequestDto;
import com.fn.ai.notification.slack.presentation.dto.request.SlackUpdateRequestDto;
import com.fn.ai.notification.slack.presentation.dto.response.SlackResponseDto;
import com.fn.ai.notification.slack.presentation.dto.response.SlackUpdateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SlackServiceImpl implements SlackService {

    private final SlackRepository slackRepository;
    private final SlackApiClient slackApiClient;

    @Transactional
    @Override
    public SlackResponseDto createSlackMessage(SlackCreateRequestDto requestDto) {
        SlackSendResponse sendResponse = slackApiClient.sendMessage(requestDto);

        UUID userId = UUID.randomUUID();
        Slack slack = Slack.of(
                userId,
                requestDto.getMessage(),
                requestDto.getRecipientSlackId(),
                sendResponse.getChannel(),           // 실제 채널 ID 사용
                sendResponse.getSentAt(),
                sendResponse.getTs()                 // 원본 ts 문자열 저장
        );
        slackRepository.save(slack);

        return SlackResponseDto.from(slack);
    }

    @Transactional
    @Override
    public SlackUpdateResponseDto updateSlackMessage(UUID slackId, SlackUpdateRequestDto updateRequestDto) {
        Slack slack = slackRepository.findById(slackId)
                .orElseThrow(() -> new CustomException(ErrorType.CHANNEL_NOT_FOUND));

        SlackSendResponse updateResponse = slackApiClient.updateMessage(updateRequestDto, slack.getChannelId(), slack.getSlackTs());

        slack.setMessage(updateRequestDto.getMessage());
        slack.setSentAt(updateResponse.getSentAt());
        slack.setSlackTs(updateResponse.getTs());
        slack.setChannelId(updateResponse.getChannel());

        slackRepository.save(slack);

        return SlackUpdateResponseDto.from(slack);
    }

    @Transactional(readOnly = true)
    @Override
    public SlackResponseDto getSlackMessage(UUID slackId) {
        Slack slack = slackRepository.findById(slackId)
                .orElseThrow(() -> new CustomException(ErrorType.SLACK_NOT_FOUND));
        return SlackResponseDto.from(slack);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<SlackResponseDto> getAllSlackMessage(String recievedSlackId, Pageable pageable) {
        Page<Slack> page;
        if (recievedSlackId != null && !recievedSlackId.isEmpty()) {
            page = slackRepository.findByRecipientSlackId(recievedSlackId, pageable);
        } else {
            page = slackRepository.findAll(pageable);
        }
        return page.map(SlackResponseDto::from);
    }
}
