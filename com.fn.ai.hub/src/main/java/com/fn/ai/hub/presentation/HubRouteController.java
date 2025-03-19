package com.fn.ai.hub.presentation;

import com.fn.ai.common.application.CommonResponse;
import com.fn.ai.common.exception.code.CommonResponseCode;
import com.fn.ai.hub.application.HubRouteService;
import com.fn.ai.hub.application.dto.request.HubRouteCreateRequestDto;
import com.fn.ai.hub.application.dto.request.HubRouteFindRequestDto;
import com.fn.ai.hub.application.dto.request.HubRouteUpdateRequestDto;
import com.fn.ai.hub.application.dto.response.HubRouteResponseDto;

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

    @PostMapping("/")
    public ResponseEntity<CommonResponse<HubRouteResponseDto>> createHubRoute(@RequestBody
                                                                              HubRouteCreateRequestDto requestDto) {

        HubRouteResponseDto responseDto = hubRouteService.createHubRoute(requestDto);
        return CommonResponse.of(CommonResponseCode.CREATED.getCode(),
                CommonResponseCode.CREATED.getMessage(), responseDto);
    }

    @PutMapping("/{route_id}")
    public ResponseEntity<CommonResponse<HubRouteResponseDto>> updateHubRoute(@RequestBody
                                                                              HubRouteUpdateRequestDto requestDto) {

        HubRouteResponseDto responseDto = hubRouteService.updateHubRoute(requestDto);
        return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
                CommonResponseCode.CREATED.getMessage(), responseDto);
    }

    @DeleteMapping("/{route_id}")
    public ResponseEntity<CommonResponse<HubRouteResponseDto>> deleteHubRoute(
            @PathVariable UUID route_id) {

        HubRouteResponseDto responseDto = hubRouteService.deleteHubRoute(route_id);
        return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
                CommonResponseCode.SUCCESS.getMessage(), responseDto);
    }

    @GetMapping("/{route_id}")
    public ResponseEntity<CommonResponse<HubRouteResponseDto>> getHubRoute(
            @PathVariable UUID route_id) {

        HubRouteResponseDto responseDto = hubRouteService.getHubRoute(route_id);
        return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
                CommonResponseCode.SUCCESS.getMessage(), responseDto);
    }

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

    @GetMapping("/search")
    public ResponseEntity<CommonResponse<Page<HubRouteResponseDto>>> searchHubRoute(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam boolean isAsc,
            @RequestParam String keyword
    ) {

        Page<HubRouteResponseDto> responseDto = hubRouteService.searchHubRoute(page, size, sortBy,
                isAsc, keyword);
        return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
                CommonResponseCode.SUCCESS.getMessage(), responseDto);
    }

    @PostMapping("/b2b")
    public ResponseEntity<CommonResponse<Queue<HubRouteResponseDto>>> findDeliveryRoute(@RequestBody
                                                                                          HubRouteFindRequestDto requestDto) {

        Queue<HubRouteResponseDto> responseDto = hubRouteService.findDeliveryRoute(requestDto);
        return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(),
                CommonResponseCode.SUCCESS.getMessage(), responseDto);
    }

    @GetMapping("/generate")
    public ResponseEntity<CommonResponse<String>> generateHubRoutes(){
        hubRouteService.generateHubRoutes();
        return CommonResponse.of(CommonResponseCode.SUCCESS.getCode(), CommonResponseCode.CREATED.getMessage(), "허브 그래프 생성");
    }
}
