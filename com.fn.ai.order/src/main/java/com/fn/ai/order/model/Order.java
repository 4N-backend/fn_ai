package com.fn.ai.order.model;

import com.fn.ai.common.entity.BaseEntity;
import com.fn.ai.order.presentation.dto.OrderCreateRequestDto;
import com.fn.ai.order.presentation.dto.OrderUpdateRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "p_order")
public class Order extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "order_id", nullable = false)
  private UUID id;

  @Column(nullable = false)
  private UUID receiverId;

  @Column(nullable = false)
  private UUID supplierId;

  private UUID deliveryId;

  private String instruction;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> orderItemList = new ArrayList<>();

  @Builder
  public Order(UUID receiverId, UUID supplierId, String instruction) {
    this.receiverId = receiverId;
    this.supplierId = supplierId;
    this.instruction = instruction;
  }

  public static Order create(OrderCreateRequestDto requestDto) {
    Order order = Order.builder()
        .receiverId(requestDto.receiverId())
        .supplierId(requestDto.supplierId())
        .instruction(requestDto.instruction())
        .build();

    order.addOrderItems(requestDto.orderItems()
        .stream()
        .map(OrderItem::of)
        .toList());

    return order;
  }

  public void addOrderItems(List<OrderItem> orderItems) {
    this.orderItemList.addAll(orderItems);
    for (OrderItem orderItem : orderItems) {
      orderItem.addOrder(this);
    }
  }

  public void updateOrder(OrderUpdateRequestDto requestDto) {
    this.receiverId = requestDto.receiverId();
    this.supplierId = requestDto.supplierId();
    this.deliveryId = requestDto.deliveryId();
    this.instruction = requestDto.instruction();

    addOrderItems(requestDto.orderItems()
        .stream()
        .map(OrderItem::of)
        .toList());
  }

  public void updateDeliveryId(UUID deliveryId) {
    this.deliveryId = deliveryId;
  }

}
