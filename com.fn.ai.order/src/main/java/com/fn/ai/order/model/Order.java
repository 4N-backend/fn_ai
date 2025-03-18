package com.fn.ai.order.model;

import com.fn.ai.order.presentation.dto.OrderCreateRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity(name = "p_order")
public class Order {

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
  private List<OrderItem> orderItems;

  public static Order create(OrderCreateRequestDto requestDto) {
    return Order.builder()
        .receiverId(requestDto.receiverId())
        .supplierId(requestDto.supplierId())
        .instruction(requestDto.instruction())
        .build();
  }

  public void updateDeliveryId(UUID deliveryId) {
    this.deliveryId = deliveryId;
  }

  public void addOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
    for (OrderItem orderItem : orderItems) {
      orderItem.addOrder(this);
    }
  }

}
