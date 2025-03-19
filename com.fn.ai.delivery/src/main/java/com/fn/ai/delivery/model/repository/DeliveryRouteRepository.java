package com.fn.ai.delivery.model.repository;

import com.fn.ai.delivery.model.DeliveryRoute;
import java.util.List;

public interface DeliveryRouteRepository {

  List<DeliveryRoute> saveAll(List<DeliveryRoute> deliveryRouteList);

}
