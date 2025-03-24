package com.fn.ai.hub.presentation;

import com.fn.ai.common.application.CommonResponse;
import com.fn.ai.common.exception.code.CommonResponseCode;
import com.fn.ai.hub.application.HubService;
import com.fn.ai.hub.application.dto.request.HubCreateRequestDto;
import com.fn.ai.hub.application.dto.request.HubUpdateRequestDto;
import com.fn.ai.hub.application.dto.response.HubResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name ="Hub", description = "허브 관련 API")
@RestController
@RequestMapping("/api/hubs")
@RequiredArgsConstructor
public class HubController {

    private final HubService hubService;

    @Operation(summary = "허브 생성")
    @PostMapping("")
    public ResponseEntity<CommonResponse<HubResponseDto>> createHub(@RequestBody HubCreateRequestDto requestDto) {

        HubResponseDto responseDto = hubService.createHub(requestDto);
        return CommonResponse.of(CommonResponseCode.CREATED.getCode(),
            CommonResponseCode.CREATED.getMessage(), responseDto);
    }

    @Operation(summary = "허브 조회",description = "허브 Id로 허브를 조회합니다.")
    @GetMapping("/{hub_id}")
    public ResponseEntity<CommonResponse<HubResponseDto>> getHub(@PathVariable UUID hub_id) {

        HubResponseDto responseDto = hubService.getHub(hub_id);
        return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
            CommonResponseCode.CREATED.getMessage(), responseDto);
    }

    @Operation(deprecated = true,summary = "허브 조회",description = "feign클라이언트용 허브 조회 \n"
        + "반환값이 기본 ResponseEntity로, 커스텀 반환값을 사용하지 않아서 deprecated처리")
    @GetMapping("/client/{hub_id}")
    public HubResponseDto getHubClient(@PathVariable UUID hub_id) {
      return hubService.getHub(hub_id);
    }

    @Operation(summary = "모든 허브 조회",description = "모든 허브를 검색합니다.")
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

    @Operation(summary = "허브 검색",description = "키워드를 기준으로 허브를 검색합니다.")
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

    @Operation(summary = "허브 수정")
    @PutMapping("/{hub_id}")
    public ResponseEntity<CommonResponse<HubResponseDto>> updateHub(@RequestBody HubUpdateRequestDto requestDto,
        @PathVariable UUID hub_id) {

        HubResponseDto responseDto = hubService.updateHub(requestDto, hub_id);
        return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
            CommonResponseCode.SUCCESS.getMessage(), responseDto);
    }

    @Operation(summary = "허브 삭제")
    @DeleteMapping("/{hub_id}")
    public ResponseEntity<CommonResponse<HubResponseDto>> deleteHub(@PathVariable UUID hub_id) {

        HubResponseDto responseDto = hubService.deleteHub(hub_id);
        return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
            CommonResponseCode.SUCCESS.getMessage(), responseDto);
    }

    @Operation(summary = "허브 초기화",description = "17개 기본 허브 생성")
    @GetMapping("/init")
    public ResponseEntity<CommonResponse<String>> initHubRoutes() {
        hubService.initializeHubs();
        return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(), CommonResponseCode.CREATED.getMessage(), "허브 초기화");
    }
}
