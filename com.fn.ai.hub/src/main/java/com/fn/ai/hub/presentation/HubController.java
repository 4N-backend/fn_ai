package com.fn.ai.hub.presentation;

import com.fn.ai.common.application.CommonResponse;
import com.fn.ai.common.exception.code.CommonResponseCode;
import com.fn.ai.hub.application.HubService;
import com.fn.ai.hub.application.dto.request.HubCreateRequestDto;
import com.fn.ai.hub.application.dto.request.HubUpdateRequestDto;
import com.fn.ai.hub.application.dto.response.HubResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hubs")
@RequiredArgsConstructor
public class HubController {

    private final HubService hubService;

    @PostMapping("")
    public ResponseEntity<CommonResponse<HubResponseDto>> createHub(@RequestBody HubCreateRequestDto requestDto) {

        HubResponseDto responseDto = hubService.createHub(requestDto);
        return CommonResponse.of(CommonResponseCode.CREATED.getCode(),
            CommonResponseCode.CREATED.getMessage(), responseDto);
    }

    @GetMapping("/{hub_id}")
    public ResponseEntity<CommonResponse<HubResponseDto>> getHub(@PathVariable UUID hub_id) {

        HubResponseDto responseDto = hubService.getHub(hub_id);
        return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
            CommonResponseCode.CREATED.getMessage(), responseDto);
    }

    @GetMapping("/client/{hub_id}")
    public HubResponseDto getHubClient(@PathVariable UUID hub_id) {
      return hubService.getHub(hub_id);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<Page<HubResponseDto>>> getAllHub(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam String sortBy,
        @RequestParam boolean isAsc
    ) {

        Page<HubResponseDto> responseDto = hubService.getAllHub(page, size, sortBy, isAsc);
        return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
            CommonResponseCode.SUCCESS.getMessage(), responseDto);
    }

    @GetMapping("/search")
    public ResponseEntity<CommonResponse<Page<HubResponseDto>>> searchHub(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam String sortBy,
        @RequestParam boolean isAsc,
        @RequestParam String keyword
    ) {

        Page<HubResponseDto> responseDto = hubService.searchHub(page, size, sortBy, isAsc,
            keyword);
        return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
            CommonResponseCode.SUCCESS.getMessage(), responseDto);
    }

    @PutMapping("/{hub_id}")
    public ResponseEntity<CommonResponse<HubResponseDto>> updateHub(@RequestBody HubUpdateRequestDto requestDto,
        @PathVariable UUID hub_id) {

        HubResponseDto responseDto = hubService.updateHub(requestDto, hub_id);
        return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
            CommonResponseCode.SUCCESS.getMessage(), responseDto);
    }

    @DeleteMapping("/{hub_id}")
    public ResponseEntity<CommonResponse<HubResponseDto>> deleteHub(@PathVariable UUID hub_id) {

        HubResponseDto responseDto = hubService.deleteHub(hub_id);
        return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
            CommonResponseCode.SUCCESS.getMessage(), responseDto);
    }

    @GetMapping("/init")
    public ResponseEntity<CommonResponse<String>> initHubRoutes() {
        hubService.initializeHubs();
        return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(), CommonResponseCode.CREATED.getMessage(), "허브 초기화");
    }
}
