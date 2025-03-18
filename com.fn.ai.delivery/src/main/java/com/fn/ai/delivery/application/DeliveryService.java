package com.fn.ai.delivery.application;

import com.fn.ai.delivery.dto.DeliveryCreateRequestDto;
import com.fn.ai.delivery.dto.DeliveryCreateResponseDto;

public interface DeliveryService {

  DeliveryCreateResponseDto create(DeliveryCreateRequestDto requestDto);

}
