package pl.metaclass.ordering.system.order.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pl.metaclass.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import pl.metaclass.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import pl.metaclass.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import pl.metaclass.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import pl.metaclass.ordering.system.order.service.domain.ports.input.service.OrderApplicationService;

@Slf4j
@Validated
@Service
class OrderApplicationServiceImpl implements OrderApplicationService {

	private final OrderCreateCommandHandler orderCreateCommandHandler;

	private final OrderTrackCommandHandler orderTrackCommandHandler;

	public OrderApplicationServiceImpl(OrderCreateCommandHandler orderCreateCommandHandler, OrderTrackCommandHandler orderTrackCommandHandler) {
		this.orderCreateCommandHandler = orderCreateCommandHandler;
		this.orderTrackCommandHandler = orderTrackCommandHandler;
	}

	@Override
	public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
		return orderCreateCommandHandler.createOrder(createOrderCommand);
	}

	@Override
	public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderCommand) {
		return orderTrackCommandHandler.trackOrder(trackOrderCommand);
	}
}
