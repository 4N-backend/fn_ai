package com.fn.ai.notification.slack.presentation.controller;

import com.fn.ai.notification.slack.application.service.SlackService;
import com.fn.ai.notification.slack.common.ResponseDataDto;
import com.fn.ai.notification.slack.common.ResponseStatus;
import com.fn.ai.notification.slack.presentation.dto.request.SlackCreateRequestDto;
import com.fn.ai.notification.slack.presentation.dto.request.SlackUpdateRequestDto;
import com.fn.ai.notification.slack.presentation.dto.response.SlackResponseDto;
import com.fn.ai.notification.slack.presentation.dto.response.SlackUpdateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<ResponseDataDto<SlackResponseDto>> sendMessage(@RequestBody SlackCreateRequestDto requestDto) {
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


    /**
     * Slack 메시지 단건 조회 API
     * @param slackId Slack 메시지 ID
     * @return 조회된 Slack 메시지 정보
     */
    @GetMapping("/{slackId}") // TODO: MASTER 권한만 수정할 수 있도록 권한 설정
    public ResponseEntity<ResponseDataDto<SlackResponseDto>> getMessage(
            @PathVariable UUID slackId) {
        return ResponseEntity.ok(
                new ResponseDataDto<>(ResponseStatus.GET_SLACK_SUCCESS, slackService.getSlackMessage(slackId))
        );

    }

    /**
     * Slack 메시지 전체 조회
     * @return 전체 Slack 메시지 목록
     */
    // TODO: MASTER 권한만 수정할 수 있도록 권한 설정
    // TODO: createdAt 기반으로 페이징 처리 - Audit 추가시 변경
    @GetMapping("/all")
    public ResponseEntity<ResponseDataDto<Page<SlackResponseDto>>> getAllMessage(
            @RequestParam(required = false) String recievedSlackId,
            @PageableDefault(sort = "sentAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<SlackResponseDto> page = slackService.getAllSlackMessage(recievedSlackId, pageable);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.GET_SLACK_SUCCESS, page));
    }
}