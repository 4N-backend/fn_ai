package com.fn.ai.delivery.presentation;

import static com.fn.ai.common.exception.code.CommonResponseCode.CREATED;

import com.fn.ai.common.application.CommonResponse;
import com.fn.ai.delivery.application.DeliveryService;
import com.fn.ai.delivery.presentation.dto.DeliveryArriveHubRequestDto;
import com.fn.ai.delivery.presentation.dto.DeliveryArriveHubResponseDto;
import com.fn.ai.delivery.presentation.dto.DeliveryCompleteResponseDto;
import com.fn.ai.delivery.presentation.dto.DeliveryCreateRequestDto;
import com.fn.ai.delivery.presentation.dto.DeliveryCreateResponseDto;
import com.fn.ai.delivery.presentation.dto.DeliveryDepartHubResponseDto;
import com.fn.ai.delivery.presentation.dto.DeliveryDetailsResponseDto;
import com.fn.ai.delivery.presentation.dto.DeliverySummaryResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/deliveries")
@Validated
public class DeliveryController {

  private final DeliveryService deliveryService;

  /**
   * 배송 생성
   */
  @PostMapping
  public ResponseEntity<CommonResponse<DeliveryCreateResponseDto>> create(
      @Valid @RequestBody DeliveryCreateRequestDto requestDto
  ) {
    return CommonResponse.success(CREATED.getMessage(), deliveryService.create(requestDto));
  }

  /**
   * 배송 단 건 조회
   */
  @GetMapping("/{deliveryId}")
  public ResponseEntity<CommonResponse<DeliveryDetailsResponseDto>> readOne(
      @PathVariable UUID deliveryId
  ) {
    return CommonResponse.success(deliveryService.readOne(deliveryId));
  }

  /**
   * 배송 목록 전체 조회
   */
  @GetMapping
  public ResponseEntity<CommonResponse<List<DeliverySummaryResponseDto>>> readAll() {
    return CommonResponse.success(deliveryService.readAll());
  }

  /**
   * 배송 삭제
   */
  @DeleteMapping("/{deliveryId}")
  public ResponseEntity<CommonResponse<Void>> delete(@PathVariable UUID deliveryId) {
    deliveryService.delete(deliveryId);
    return CommonResponse.success(null);
  }

  /**
   * 허브로부터 출발
   */
  @PostMapping("/{deliveryId}/route/{sequence}/depart")
  public ResponseEntity<CommonResponse<DeliveryDepartHubResponseDto>> departFromHub(
      @PathVariable UUID deliveryId,
      @PathVariable @PositiveOrZero int sequence
  ) {
    return CommonResponse.success(
        deliveryService.departFromHub(deliveryId, sequence));
  }

  /**
   * 허브 도착
   */
  @PostMapping("/{deliveryId}/route/{sequence}/arrive")
  public ResponseEntity<CommonResponse<DeliveryArriveHubResponseDto>> arriveToHub(
      @PathVariable UUID deliveryId,
      @PathVariable @PositiveOrZero int sequence,
      @Valid @RequestBody DeliveryArriveHubRequestDto requestDto
  ) {
    return CommonResponse.success(
        deliveryService.arriveToHub(deliveryId, sequence, requestDto));
  }

  /**
   * 배송 완료
   */
  @PostMapping("/{deliveryId}/complete")
  public ResponseEntity<CommonResponse<DeliveryCompleteResponseDto>> complete(
      @PathVariable UUID deliveryId
  ) {
    return CommonResponse.success(deliveryService.complete(deliveryId));
  }
}
