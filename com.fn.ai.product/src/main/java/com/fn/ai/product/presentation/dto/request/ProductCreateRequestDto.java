package com.fn.ai.product.presentation.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record ProductCreateRequestDto(
    @NotBlank(message = "상품명은 필수입니다.")
    String productName,

    @Min(value = 1, message = "재고값은 1 이상이어야 합니다.")
    int stock,

    @NotBlank(message = "허브 Id는 필수입니다.")
    UUID hubId,

    @NotBlank(message = "업체 Id는 필수입니다.")
    UUID companyId
) {

}
