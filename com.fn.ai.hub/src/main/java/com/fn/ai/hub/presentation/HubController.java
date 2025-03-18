package com.fn.ai.hub.presentation;

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

    @PostMapping("/")
    public ResponseEntity<HubResponseDto> createHub(@RequestBody HubCreateRequestDto requestDto) {

        HubResponseDto responseDto = hubService.createHub(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{hub_id}")
    public ResponseEntity<HubResponseDto> getHub(@PathVariable UUID hub_id) {

        HubResponseDto responseDto = hubService.getHub(hub_id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/")
    public ResponseEntity<Page<HubResponseDto>> getAllHub(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam String sortBy,
        @RequestParam boolean isAsc
    ) {

        Page<HubResponseDto> responseDtos = hubService.getAllHub(page, size, sortBy, isAsc);
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<HubResponseDto>> searchHub(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam String sortBy,
        @RequestParam boolean isAsc,
        @RequestParam String keyword
    ) {

        Page<HubResponseDto> responseDtos = hubService.searchHub(page, size, sortBy, isAsc,
            keyword);
        return ResponseEntity.ok(responseDtos);
    }

    @PutMapping("/{hub_id}")
    public ResponseEntity<HubResponseDto> updateHub(@RequestBody HubUpdateRequestDto requestDto,
        @PathVariable UUID hub_id) {

        HubResponseDto responseDto = hubService.updateHub(requestDto, hub_id);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{hub_id}")
    public ResponseEntity<HubResponseDto> deleteHub(@PathVariable UUID hub_id) {

        HubResponseDto responseDto = hubService.deleteHub(hub_id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/test")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("호날두 사랑해");
    }
}
