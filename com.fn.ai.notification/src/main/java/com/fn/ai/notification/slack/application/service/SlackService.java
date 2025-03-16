package com.fn.ai.notification.slack.application.service;

import com.fn.ai.notification.slack.presentation.dto.request.SlackCreateRequestDto;
import com.fn.ai.notification.slack.presentation.dto.request.SlackUpdateRequestDto;
import com.fn.ai.notification.slack.presentation.dto.response.SlackCreateResponseDto;
import com.fn.ai.notification.slack.presentation.dto.response.SlackUpdateResponseDto;

import java.util.UUID;

public interface SlackService {

    SlackCreateResponseDto createSlackMessage(SlackCreateRequestDto requestDto);

    SlackUpdateResponseDto updateSlackMessage(UUID slackId, SlackUpdateRequestDto updateRequestDto);
}
