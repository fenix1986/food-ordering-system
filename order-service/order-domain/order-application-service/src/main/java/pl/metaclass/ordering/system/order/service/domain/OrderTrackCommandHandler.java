package pl.metaclass.ordering.system.order.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.metaclass.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import pl.metaclass.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import pl.metaclass.ordering.system.order.service.domain.entity.Order;
import pl.metaclass.ordering.system.order.service.domain.exception.OrderNotFoundException;
import pl.metaclass.ordering.system.order.service.domain.mapper.OrderDataMapper;
import pl.metaclass.ordering.system.order.service.domain.ports.output.respository.OrderRepository;
import pl.metaclass.ordering.system.order.service.domain.valueobject.TrackingId;

import java.util.Optional;

@Slf4j
@Component
public class OrderTrackCommandHandler {

	private final OrderDataMapper orderDataMapper;
	private final OrderRepository orderRepository;

	public OrderTrackCommandHandler(OrderDataMapper orderDataMapper, OrderRepository orderRepository) {
		this.orderDataMapper = orderDataMapper;
		this.orderRepository = orderRepository;
	}

	@Transactional(readOnly = true)
	TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
		Optional<Order> optionalOrder =
				orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()));
		if (optionalOrder.isEmpty()) {
			log.warn("Order not found");
			throw new OrderNotFoundException("Order not found with tracking id: " + trackOrderQuery.getOrderTrackingId());
		}

		return orderDataMapper.orderToTrackOrderResponse(optionalOrder.get());
	}
}
