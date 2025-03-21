package com.fn.ai.product.application.dto;

import java.util.UUID;

public record ProductRequestDto(UUID productId, int stock) {

}
