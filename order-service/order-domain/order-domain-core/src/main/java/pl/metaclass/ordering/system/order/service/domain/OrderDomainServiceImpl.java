package pl.metaclass.ordering.system.order.service.domain;

import lombok.extern.slf4j.Slf4j;
import pl.metaclass.ordering.system.order.service.domain.entity.Order;
import pl.metaclass.ordering.system.order.service.domain.entity.Product;
import pl.metaclass.ordering.system.order.service.domain.entity.Restaurant;
import pl.metaclass.ordering.system.order.service.domain.event.OrderCancelledEvent;
import pl.metaclass.ordering.system.order.service.domain.event.OrderCreatedEvent;
import pl.metaclass.ordering.system.order.service.domain.event.OrderPaidEvent;
import pl.metaclass.ordering.system.order.service.domain.exception.OrderDomainException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {

	private static final String UTC = "UTC";

	@Override
	public OrderCreatedEvent validateAndInitializeOrder(Order order, Restaurant restaurant) {
		validateRestaurant(restaurant);
		setOrderProductInformation(order, restaurant);
		order.validateOrder();
		order.initializeOrder();

		log.info("Order with id {} is initialized", order.getId().getValue());
		return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
	}

	private void validateRestaurant(Restaurant restaurant) {
		if (!restaurant.isActive()) {
			throw new OrderDomainException("Restaurant is not active: " + restaurant.getId().getValue());
		}
	}

	private void setOrderProductInformation(Order order, Restaurant restaurant) {
		order.getItems().forEach(orderItem -> restaurant.getProducts().forEach(requestedProduct -> {
			Product currentProduct = orderItem.getProduct();
			if (currentProduct.equals(requestedProduct)) {
				currentProduct.updateWithConfirmedNameAndPrice(requestedProduct.getName(), requestedProduct.getPrice());
			}
		}));
	}

	@Override
	public OrderPaidEvent payOrder(Order order) {
		order.pay();
		log.info("Order with id {} has been payed", order.getId().getValue());
		return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
	}

	@Override
	public void approveOrder(Order order) {
		order.approve();
		log.info("Order with id {} has been approved", order.getId().getValue());
	}

	@Override
	public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
		order.initCancel(failureMessages);
		log.info("Order with id {} has been initialized to cancel", order.getId().getValue());
		return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
	}

	@Override
	public void cancelOrder(Order order, List<String> failureMessages) {
		order.cancel(failureMessages);
		log.info("Order with id {} has been cancelled", order.getId().getValue());
	}
}
