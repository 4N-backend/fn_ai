package com.fn.ai.user.presentation.dto;

import java.util.UUID;

public record DeliveryManagerUpdaterRequestDto(
        UUID hubId,
        String type
) {}
