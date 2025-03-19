package com.fn.ai.order.presentation.dto;

import java.util.UUID;

public record OrderItemRequestDto(UUID productId,
                                  int quantity) {

}
