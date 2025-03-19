package com.fn.ai.delivery.application;

import com.fn.ai.delivery.presentation.dto.DeliveryCreateRequestDto;
import com.fn.ai.delivery.presentation.dto.DeliveryCreateResponseDto;

public interface DeliveryService {

  DeliveryCreateResponseDto create(DeliveryCreateRequestDto requestDto);

}
