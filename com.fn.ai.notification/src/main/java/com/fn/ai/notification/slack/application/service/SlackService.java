package com.fn.ai.notification.slack.application.service;

import com.fn.ai.notification.slack.presentation.dto.request.SlackCreateRequestDto;
import com.fn.ai.notification.slack.presentation.dto.request.SlackUpdateRequestDto;
import com.fn.ai.notification.slack.presentation.dto.response.SlackResponseDto;
import com.fn.ai.notification.slack.presentation.dto.response.SlackUpdateResponseDto;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SlackService {

    SlackResponseDto createSlackMessage(SlackCreateRequestDto requestDto);

    SlackUpdateResponseDto updateSlackMessage(UUID slackId, SlackUpdateRequestDto updateRequestDto);

    SlackResponseDto getSlackMessage(UUID slackId);

    Page<SlackResponseDto> getAllSlackMessage(String recievedSlackId, Pageable pageable);

    void deleteSlackMessage(UUID slackId);


}