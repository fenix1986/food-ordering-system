package pl.metaclass.ordering.system.order.service.domain;

import pl.metaclass.ordering.system.order.service.domain.entity.Order;
import pl.metaclass.ordering.system.order.service.domain.entity.Restaurant;
import pl.metaclass.ordering.system.order.service.domain.event.OrderCancelledEvent;
import pl.metaclass.ordering.system.order.service.domain.event.OrderCreatedEvent;
import pl.metaclass.ordering.system.order.service.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

	OrderCreatedEvent validateAndInitializeOrder(Order order, Restaurant restaurant);

	OrderPaidEvent payOrder(Order order);

	void approveOrder(Order order);

	OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

	void cancelOrder(Order order, List<String> failureMessages);
}
