package pl.metaclass.ordering.system.order.service.domain.ports.input.service;

import jakarta.validation.Valid;
import pl.metaclass.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import pl.metaclass.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import pl.metaclass.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import pl.metaclass.ordering.system.order.service.domain.dto.track.TrackOrderResponse;

public interface OrderApplicationService {

	CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);

	TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderCommand);
}
