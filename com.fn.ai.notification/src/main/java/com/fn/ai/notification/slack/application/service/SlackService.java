package com.fn.ai.notification.slack.application.service;

import com.fn.ai.notification.slack.presentation.dto.request.SlackRequestDto;
import com.fn.ai.notification.slack.presentation.dto.response.SlackResponseDto;

public interface SlackService {
    SlackResponseDto createSlackMessage(SlackRequestDto requestDto);
}
