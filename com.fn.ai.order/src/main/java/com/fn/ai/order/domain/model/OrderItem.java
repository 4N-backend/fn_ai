package com.fn.ai.order.domain.model;

import com.fn.ai.common.entity.BaseEntity;
import com.fn.ai.order.presentation.dto.OrderItemRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@SQLRestriction("deleted_at IS NULL")
@Entity(name = "p_order_item")
public class OrderItem extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;

  private UUID productId;

  private int quantity;

  public static OrderItem of(OrderItemRequestDto requestDto) {
    return OrderItem.builder()
        .productId(requestDto.productId())
        .quantity(requestDto.quantity())
        .build();
  }

  public void addOrder(Order order) {
    this.order = order;
  }

}
