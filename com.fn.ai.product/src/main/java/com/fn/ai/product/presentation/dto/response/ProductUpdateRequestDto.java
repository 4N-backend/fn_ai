package com.fn.ai.product.presentation.dto.response;

import java.util.UUID;

public record ProductUpdateRequestDto(String productName,
                                      UUID hubId,
                                      UUID companyId,
                                      int stock) {

}
