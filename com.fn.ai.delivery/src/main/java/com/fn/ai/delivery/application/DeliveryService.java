package com.fn.ai.delivery.application;

import com.fn.ai.delivery.presentation.external.dto.DeliveryArriveHubRequestDto;
import com.fn.ai.delivery.presentation.external.dto.DeliveryArriveHubResponseDto;
import com.fn.ai.delivery.presentation.external.dto.DeliveryCompleteResponseDto;
import com.fn.ai.delivery.presentation.external.dto.DeliveryDepartHubResponseDto;
import com.fn.ai.delivery.presentation.external.dto.DeliveryDetailsResponseDto;
import com.fn.ai.delivery.presentation.external.dto.DeliverySummaryResponseDto;
import com.fn.ai.delivery.presentation.internal.dto.DeliveryCreateRequestDto;
import com.fn.ai.delivery.presentation.internal.dto.DeliveryCreateResponseDto;
import java.util.List;
import java.util.UUID;

public interface DeliveryService {

  DeliveryCreateResponseDto create(DeliveryCreateRequestDto requestDto);

  DeliveryDetailsResponseDto readOne(UUID deliveryId);

  List<DeliverySummaryResponseDto> readAll();

  void delete(UUID deliveryId);

  DeliveryDepartHubResponseDto departFromHub(UUID deliveryId, int sequence);

  DeliveryArriveHubResponseDto arriveToHub(UUID deliveryId, int sequence,
      DeliveryArriveHubRequestDto requestDto);

  DeliveryCompleteResponseDto complete(UUID deliveryId);

}
