package com.fn.ai.product.presentation.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ProductCreateRequestDto(
    @NotBlank(message = "상품명은 필수입니다.")
    String productName,

    @Min(value = 1, message = "재고값은 1 이상이어야 합니다.")
    int stock,

    @NotNull(message = "허브 Id는 필수입니다.")
    UUID hubId,

    @NotNull(message = "업체 Id는 필수입니다.")
    UUID companyId
) {

}
