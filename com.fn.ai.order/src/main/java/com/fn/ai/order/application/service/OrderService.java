package com.fn.ai.order.application.service;

import com.fn.ai.order.application.service.client.CompanyClient;
import com.fn.ai.order.application.service.client.DeliveryClient;
import com.fn.ai.order.application.service.client.ProductClient;
import com.fn.ai.order.application.service.dto.CompanyResponseDto;
import com.fn.ai.order.application.service.dto.DeliveryCreateRequestDto;
import com.fn.ai.order.application.service.dto.DeliveryCreateResponseDto;
import com.fn.ai.order.model.Order;
import com.fn.ai.order.model.OrderItem;
import com.fn.ai.order.model.OrderRepository;
import com.fn.ai.order.presentation.dto.OrderCreateRequestDto;
import com.fn.ai.order.presentation.dto.OrderCreateResponseDto;
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
    order.addOrderItems(requestDto.orderItems()
        .stream()
        .map(OrderItem::of)
        .toList());

    //배송통신로직
    DeliveryCreateResponseDto deliveryResponseDto = deliveryClient.createDelivery(
        DeliveryCreateRequestDto.of(
            order.getId(),
            companyResponseDto.receiverHubId(),
            companyResponseDto.supplierHubId(),
            companyResponseDto.supplierHubAddress())).orElseThrow(() ->
        new RuntimeException("fail to create delivery"));

    //받아온 배송ID를 set해준다
    order.updateDeliveryId(deliveryResponseDto.DeliveryId());

    return OrderCreateResponseDto.of(order);
  }

}
