package com.fn.ai.order.application;

import com.fn.ai.order.application.client.CompanyClient;
import com.fn.ai.order.application.client.DeliveryClient;
import com.fn.ai.order.application.client.ProductClient;
import com.fn.ai.order.application.dto.CompanyResponseDto;
import com.fn.ai.order.application.dto.DeliveryCreateRequestDto;
import com.fn.ai.order.application.dto.DeliveryCreateResponseDto;
import com.fn.ai.order.model.Order;
import com.fn.ai.order.model.OrderRepository;
import com.fn.ai.order.presentation.dto.OrderCreateRequestDto;
import com.fn.ai.order.presentation.dto.OrderCreateResponseDto;
import com.fn.ai.order.presentation.dto.OrderResponseDto;
import com.fn.ai.order.presentation.dto.OrderUpdateRequestDto;
import com.fn.ai.order.presentation.dto.OrderUpdateResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
        new RuntimeException("No Hub found for given supplierId"));

    //상품 존재 확인 및 재고 감소
    if (!productClient.reduceStockByOrderItems(requestDto.orderItems())) {
      throw new RuntimeException("No exist Product");
    }

    //주문생성
    Order order = orderRepository.save(Order.create(requestDto));

    //배송통신로직
    DeliveryCreateResponseDto deliveryResponseDto = deliveryClient.createDelivery(
        DeliveryCreateRequestDto.of(
            order.getId(),
            companyResponseDto.receiverHubId(),
            companyResponseDto.supplierHubId(),
            companyResponseDto.supplierHubAddress())).orElseThrow(() ->
        new RuntimeException("fail to create delivery"));

    //받아온 배송ID를 set해준다
    order.updateDeliveryId(deliveryResponseDto.deliveryId());

    return OrderCreateResponseDto.of(order);
  }

  @Transactional(readOnly = true)
  public OrderResponseDto findByOrderId(UUID orderId) {
    Order order = orderRepository.findById(orderId).orElseThrow(() ->
        new RuntimeException("No Order found for given orderId"));
    return OrderResponseDto.from(order);
  }

  public OrderUpdateResponseDto updateOrder(OrderUpdateRequestDto requestDto, UUID orderId) {
    Order order = orderRepository.findById(orderId).orElseThrow(() ->
        new RuntimeException("No Order found for given orderId"));

    order.updateOrder(requestDto);

    return OrderUpdateResponseDto.from(order);
  }

  public void deleteOrder(UUID orderId) {
    Order order = orderRepository.findById(orderId).orElseThrow(() ->
        new RuntimeException("No Order found for given orderId"));
    //삭제 로직
  }
}
