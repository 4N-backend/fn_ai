package com.fn.ai.user.presentation.controller;

import com.fn.ai.user.application.service.DeliveryManagerService;
import com.fn.ai.user.presentation.dto.DeliveryManagerNextSequenceResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/delivery-managers")
@Slf4j
public class DeliveryManagerController {

  private final DeliveryManagerService deliveryManagerService;

  @GetMapping("/{lastSequence}/next")
  public DeliveryManagerNextSequenceResponseDto getNextDeliveryManager(
    @PathVariable long lastSequence) {
    return deliveryManagerService.getNextDeliveryManager(lastSequence);
  }
}
