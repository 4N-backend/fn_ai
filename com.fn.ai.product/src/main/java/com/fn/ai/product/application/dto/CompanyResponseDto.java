package com.fn.ai.product.application.dto;

import java.util.UUID;

public record CompanyResponseDto(UUID companyId,
                                 String companyName,
                                 UUID hubId,
                                 String Address,
                                 UUID companyManagerId) {

}
