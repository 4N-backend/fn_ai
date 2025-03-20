package com.fn.ai.delivery.application;

import com.fn.ai.delivery.presentation.dto.DeliveryArriveHubRequestDto;
import com.fn.ai.delivery.presentation.dto.DeliveryArriveHubResponseDto;
import com.fn.ai.delivery.presentation.dto.DeliveryCompleteResponseDto;
import com.fn.ai.delivery.presentation.dto.DeliveryCreateRequestDto;
import com.fn.ai.delivery.presentation.dto.DeliveryCreateResponseDto;
import com.fn.ai.delivery.presentation.dto.DeliveryDepartHubResponseDto;
import com.fn.ai.delivery.presentation.dto.DeliveryDetailsResponseDto;
import com.fn.ai.delivery.presentation.dto.DeliverySummaryResponseDto;
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
