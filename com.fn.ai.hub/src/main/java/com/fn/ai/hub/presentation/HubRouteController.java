package com.fn.ai.hub.presentation;

import com.fn.ai.common.application.CommonResponse;
import com.fn.ai.common.exception.code.CommonResponseCode;
import com.fn.ai.hub.application.HubRouteService;
import com.fn.ai.hub.application.dto.request.HubRouteCreateRequestDto;
import com.fn.ai.hub.application.dto.request.HubRouteFindRequestDto;
import com.fn.ai.hub.application.dto.request.HubRouteUpdateRequestDto;
import com.fn.ai.hub.application.dto.response.HubRouteResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Queue;
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
@RequestMapping("/api/hubs/routes")
@RequiredArgsConstructor
public class HubRouteController {

    private final HubRouteService hubRouteService;

    @Operation(summary = "허브 루트 생성")
    @PostMapping("/")
    public ResponseEntity<CommonResponse<HubRouteResponseDto>> createHubRoute(@RequestBody
    HubRouteCreateRequestDto requestDto) {

        HubRouteResponseDto responseDto = hubRouteService.createHubRoute(requestDto);
        return CommonResponse.of(CommonResponseCode.CREATED.getCode(),
            CommonResponseCode.CREATED.getMessage(), responseDto);
    }

    @Operation(summary = "허브 루트 변경")
    @PutMapping("/{route_id}")
    public ResponseEntity<CommonResponse<HubRouteResponseDto>> updateHubRoute(
        @PathVariable UUID route_id,
        @RequestBody
    HubRouteUpdateRequestDto requestDto) {

        HubRouteResponseDto responseDto = hubRouteService.updateHubRoute(route_id,requestDto);
        return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
            CommonResponseCode.CREATED.getMessage(), responseDto);
    }

    @Operation(summary = "허브 루트 삭제")
    @DeleteMapping("/{route_id}")
    public ResponseEntity<CommonResponse<HubRouteResponseDto>> deleteHubRoute(
        @PathVariable UUID route_id) {

        HubRouteResponseDto responseDto = hubRouteService.deleteHubRoute(route_id);
        return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
            CommonResponseCode.SUCCESS.getMessage(), responseDto);
    }

    @Operation(summary = "허브 루트 검색",description = "허브 루트 Id로 검색합니다.")
    @GetMapping("/{route_id}")
    public ResponseEntity<CommonResponse<HubRouteResponseDto>> getHubRoute(
        @PathVariable UUID route_id) {

        HubRouteResponseDto responseDto = hubRouteService.getHubRoute(route_id);
        return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
            CommonResponseCode.SUCCESS.getMessage(), responseDto);
    }

    @Operation(summary = "모든 허브루트 조회")
    @GetMapping("/")
    public ResponseEntity<CommonResponse<Page<HubRouteResponseDto>>> getAllHubRoutes(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam String sortBy,
        @RequestParam boolean isAsc
    ) {

        Page<HubRouteResponseDto> responseDto = hubRouteService.getAllHubRoutes(page, size, sortBy,
            isAsc);
        return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
            CommonResponseCode.SUCCESS.getMessage(), responseDto);
    }

    @Operation(summary = "허브 루트 검색",description = "키워드를 기준으로 허브 루트를 검색합니다.\n"
        + "키워드는 출발 허브의 이름입니다.")
    @GetMapping("/search")
    public ResponseEntity<CommonResponse<Page<HubRouteResponseDto>>> searchHubRoute(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam String sortBy,
        @RequestParam boolean isAsc,
        @RequestParam UUID keyword
    ) {

        Page<HubRouteResponseDto> responseDto = hubRouteService.searchHubRoute(page, size, sortBy,
            isAsc, keyword);
        return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
            CommonResponseCode.SUCCESS.getMessage(), responseDto);
    }

    @Operation(summary = "배달 루트 조회",description = "생성된 허브 루트들을 기준으로 가장 빠른 배달 루트를 생성합니다.")
    @PostMapping("/b2b")
    public Queue<HubRouteResponseDto> findDeliveryRoute(@RequestBody
    HubRouteFindRequestDto requestDto) {

      return hubRouteService.findDeliveryRoute(requestDto);
    }

    @Operation(summary = "허브 루트 생성" , description = "현재 데이터베이스에 있는 모든 허브간의 거리정보를 기준으로 허브 루트를 생성합니다.")
    @GetMapping("/generate")
    public ResponseEntity<CommonResponse<String>> generateHubRoutes() {
        hubRouteService.generateHubRoutes();
        return CommonResponse.of(CommonResponseCode.CREATED.getCode(),
            CommonResponseCode.CREATED.getMessage(), "허브 그래프 생성");
    }

    @Operation(summary = "허브간의 거리 및 시간 계산",description = "미리 모든 허브들간의 소요 거리와 시간을 Naver Map direction5 API를 이용해 계산하여 데이터베이스에 저장합니다.")
    @GetMapping("/generate/cal")
    public ResponseEntity<CommonResponse<String>> calculateAllHubRoutes() {
        hubRouteService.calculateAllHubRoutes();
        return CommonResponse.of(CommonResponseCode.CREATED.getCode(),
            CommonResponseCode.CREATED.getMessage(), "허브 그래프 생성");

    }
}
