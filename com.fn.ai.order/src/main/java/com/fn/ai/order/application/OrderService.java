package com.fn.ai.order.application;

import com.fn.ai.common.context.UserContext;
import com.fn.ai.common.exception.BaseException;
import com.fn.ai.common.exception.code.CommonResponseCode;
import com.fn.ai.order.application.client.CompanyClient;
import com.fn.ai.order.application.client.DeliveryClient;
import com.fn.ai.order.application.client.ProductClient;
import com.fn.ai.order.application.dto.CompanyResponseDto;
import com.fn.ai.order.application.dto.DeliveryCreateRequestDto;
import com.fn.ai.order.application.dto.DeliveryCreateResponseDto;
import com.fn.ai.order.application.dto.ProductStockRequestDto;
import com.fn.ai.order.domain.model.Order;
import com.fn.ai.order.domain.repository.OrderRepository;
import com.fn.ai.order.presentation.dto.OrderCreateRequestDto;
import com.fn.ai.order.presentation.dto.OrderCreateResponseDto;
import com.fn.ai.order.presentation.dto.OrderResponseDto;
import com.fn.ai.order.presentation.dto.OrderSearchRequestDto;
import com.fn.ai.order.presentation.dto.OrderSearchResponseDto;
import com.fn.ai.order.presentation.dto.OrderUpdateRequestDto;
import com.fn.ai.order.presentation.dto.OrderUpdateResponseDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

  private final CompanyClient companyClient;
  private final DeliveryClient deliveryClient;
  private final ProductClient productClient;
  private final OrderRepository orderRepository;

  public OrderCreateResponseDto createOrder(OrderCreateRequestDto requestDto) {
    //배송에 넘겨줄 HubId,주소 추출
    CompanyResponseDto companyResponseDto = companyClient.getHubByCompanyId(
        requestDto.supplierId(), requestDto.receiverId()).orElseThrow(() ->
        new BaseException(CommonResponseCode.BAD_REQUEST.getCode(), "허브ID 요청에 실패했습니다."));

    List<ProductStockRequestDto> stockDtoList = requestDto.orderItems().stream()
        .map(ProductStockRequestDto::of).toList();

    //상품 존재 확인 및 재고 감소
    if (!productClient.reduceStockByOrderItems(stockDtoList)) {
      throw new BaseException(CommonResponseCode.BAD_REQUEST.getCode(), "재고 감소에 실패하였습니다.");
    }

    //주문생성
    Order order = orderRepository.save(Order.create(requestDto));

    try {//배송통신로직
      DeliveryCreateResponseDto deliveryResponseDto = deliveryClient.createDelivery(
          DeliveryCreateRequestDto.of(
              order.getId(),
              companyResponseDto.produceHubId(),
              companyResponseDto.receiveHubId(),
              companyResponseDto.receiveHubAddress())).orElseThrow(() ->
          new BaseException(CommonResponseCode.BAD_REQUEST.getCode(), "배송생성에 실패했습니다."));

      // 받아온 배송 ID를 set해준다
      order.updateDeliveryId(deliveryResponseDto.deliveryId());
    } catch (RuntimeException e) {
      // 배송 생성 실패 시 재고 롤백
      if (!productClient.increaseStockByOrderItems(stockDtoList)) {
        throw new BaseException(CommonResponseCode.BAD_REQUEST.getCode(), "재고롤백 실패");
      }
      throw new BaseException(CommonResponseCode.BAD_REQUEST.getCode(), e.getMessage());
    }

    return OrderCreateResponseDto.of(order);
  }

  @Transactional(readOnly = true)
  public OrderResponseDto findByOrderId(UUID orderId) {
    Order order = orderRepository.findById(orderId).orElseThrow(() ->
        new BaseException(CommonResponseCode.DATA_NOT_FOUND.getCode(), "주문 정보가 없습니다."));

    return OrderResponseDto.from(order);
  }

  public OrderUpdateResponseDto updateOrder(OrderUpdateRequestDto requestDto, UUID orderId) {
    Order order = orderRepository.findById(orderId).orElseThrow(() ->
        new BaseException(CommonResponseCode.DATA_NOT_FOUND.getCode(),
            "주문 정보가 없습니다."));

    order.updateOrder(requestDto);

    return OrderUpdateResponseDto.from(order);
  }

  public OrderResponseDto deleteOrder(UUID orderId, UserContext userInfo) {
    Order order = orderRepository.findById(orderId).orElseThrow(() ->
        new BaseException(CommonResponseCode.DATA_NOT_FOUND.getCode(),
            "주문 정보가 없습니다."));

    order.delete();

    return OrderResponseDto.from(order);
  }

  @Transactional(readOnly = true)
  public Page<OrderSearchResponseDto> search(OrderSearchRequestDto requestDto, Pageable pageable) {
    return orderRepository.searchOrder(requestDto, pageable);
  }
}
