package com.fn.ai.delivery.application;

import com.fn.ai.delivery.presentation.dto.DeliveryCreateRequestDto;
import com.fn.ai.delivery.presentation.dto.DeliveryCreateResponseDto;
import com.fn.ai.delivery.presentation.dto.DeliveryDetailsResponseDto;
import com.fn.ai.delivery.presentation.dto.DeliverySummaryResponseDto;
import com.fn.ai.delivery.presentation.dto.DeliveryUpdateRequestDto;
import com.fn.ai.delivery.presentation.dto.DeliveryUpdateResponseDto;
import java.util.List;
import java.util.UUID;

public interface DeliveryService {

  DeliveryCreateResponseDto create(DeliveryCreateRequestDto requestDto);

  DeliveryUpdateResponseDto update(UUID deliveryId, DeliveryUpdateRequestDto requestDto);

  DeliveryDetailsResponseDto readOne(UUID deliveryId);

  List<DeliverySummaryResponseDto> readAll();

  void delete(UUID deliveryId);
}
