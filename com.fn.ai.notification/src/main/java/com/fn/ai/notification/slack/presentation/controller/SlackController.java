package com.fn.ai.notification.slack.presentation.controller;

import com.fn.ai.notification.slack.application.service.SlackService;
import com.fn.ai.notification.slack.common.ResponseDataDto;
import com.fn.ai.notification.slack.common.ResponseStatus;
import com.fn.ai.notification.slack.presentation.dto.request.SlackRequestDto;
import com.fn.ai.notification.slack.presentation.dto.response.SlackResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/slacks")
@RequiredArgsConstructor
public class SlackController {

    private final SlackService slackService;

    @PostMapping
    public ResponseEntity<ResponseDataDto<SlackResponseDto>> sendMessage(@RequestBody SlackRequestDto requestDto) {
        return ResponseEntity.ok(
                new ResponseDataDto<>(ResponseStatus.CREATE_SLACK_SUCCESS, slackService.createSlackMessage(requestDto))
        );
    }
}