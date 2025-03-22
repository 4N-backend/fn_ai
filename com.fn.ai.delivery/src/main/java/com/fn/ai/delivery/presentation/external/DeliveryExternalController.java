package com.fn.ai.delivery.presentation.external;

import com.fn.ai.common.application.CommonResponse;
import com.fn.ai.delivery.application.DeliveryAssignService;
import com.fn.ai.delivery.application.DeliveryService;
import com.fn.ai.delivery.presentation.external.dto.DeliveryArriveHubRequestDto;
import com.fn.ai.delivery.presentation.external.dto.DeliveryArriveHubResponseDto;
import com.fn.ai.delivery.presentation.external.dto.DeliveryAssignResponseDto;
import com.fn.ai.delivery.presentation.external.dto.DeliveryCompleteResponseDto;
import com.fn.ai.delivery.presentation.external.dto.DeliveryDepartHubResponseDto;
import com.fn.ai.delivery.presentation.external.dto.DeliveryDetailsResponseDto;
import com.fn.ai.delivery.presentation.external.dto.DeliverySummaryResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class DeliveryExternalController {

  private final DeliveryService deliveryService;
  private final DeliveryAssignService deliveryAssignService;

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
   * 배송담당자 배정
   */
  @PostMapping("/{deliveryId}/route/{sequence}/assign")
  public ResponseEntity<CommonResponse<DeliveryAssignResponseDto>> assign(
      @PathVariable UUID deliveryId,
      @PathVariable @PositiveOrZero int sequence
  ) {
    return CommonResponse.success(deliveryAssignService.assign(deliveryId, sequence));
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
