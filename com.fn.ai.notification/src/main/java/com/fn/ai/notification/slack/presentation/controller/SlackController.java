package com.fn.ai.notification.slack.presentation.controller;

import com.fn.ai.notification.slack.application.service.SlackService;
import com.fn.ai.notification.slack.common.ResponseDataDto;
import com.fn.ai.notification.slack.common.ResponseStatus;
import com.fn.ai.notification.slack.presentation.dto.request.SlackCreateRequestDto;
import com.fn.ai.notification.slack.presentation.dto.request.SlackUpdateRequestDto;
import com.fn.ai.notification.slack.presentation.dto.response.SlackCreateResponseDto;
import com.fn.ai.notification.slack.presentation.dto.response.SlackUpdateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/slacks")
@RequiredArgsConstructor
public class SlackController {

    private final SlackService slackService;

    /**
     * Slack 메시지 전송 API
     *
     * @param requestDto Slack 메시지 정보
     * @return 전송된 Slack 메시지 정보
     */
    @PostMapping
    public ResponseEntity<ResponseDataDto<SlackCreateResponseDto>> sendMessage(@RequestBody SlackCreateRequestDto requestDto) {
        return ResponseEntity.ok(
                new ResponseDataDto<>(ResponseStatus.CREATE_SLACK_SUCCESS, slackService.createSlackMessage(requestDto))
        );
    }

     /**
     * Slack 메시지 수정 API
     *
     * @param slackId Slack 메시지 ID
     * @param updateRequestDto 수정할 Slack 메시지 정보
     * @return 수정된 Slack 메시지 정보
     */
     @PatchMapping("/{slackId}")  // TODO: MASTER 권한만 수정할 수 있도록 권한 설정
     public ResponseEntity<ResponseDataDto<SlackUpdateResponseDto>> updateMessage(
             @PathVariable UUID slackId,
             @RequestBody SlackUpdateRequestDto updateRequestDto) {
         return ResponseEntity.ok(
                 new ResponseDataDto<>(ResponseStatus.UPDATE_SLACK_SUCCESS, slackService.updateSlackMessage(slackId, updateRequestDto))
         );
     }
}