package pl.metaclass.ordering.system.order.service.domain.entity;

import pl.metaclass.ordering.system.domain.entity.AggregateRoot;
import pl.metaclass.ordering.system.domain.valueobject.*;
import pl.metaclass.ordering.system.order.service.domain.exception.OrderDomainException;
import pl.metaclass.ordering.system.order.service.domain.valueobject.OrderItemId;
import pl.metaclass.ordering.system.order.service.domain.valueobject.StreetAddress;
import pl.metaclass.ordering.system.order.service.domain.valueobject.TrackingId;

import java.util.List;
import java.util.UUID;

public class Order extends AggregateRoot<OrderId> {

	private final CustomerId customerId;
	private final RestaurantId restaurantId;
	private final StreetAddress deliveryAddress;
	private final Money price;
	private final List<OrderItem> items;

	private TrackingId trackingId;
	private OrderStatus orderStatus;
	private List<String> failureMessages;

	public void initializeOrder() {
		setId(new OrderId(UUID.randomUUID()));
		trackingId = new TrackingId(UUID.randomUUID());
		orderStatus = OrderStatus.PENDING;
		initializeOrderItems();
	}

	private void initializeOrderItems() {
		long itemId = 1L;
		for (OrderItem item : items) {
			item.initializeOrderItem(getId(), new OrderItemId(itemId++));
		}
	}

	public void validateOrder() {
		validateInitialOrder();
		validateTotalPrice();
		validateItemsPrice();
	}

	private void validateInitialOrder() {
		if (orderStatus != null || getId() != null) {
			throw new OrderDomainException("Order is already initialized");
		}
	}

	private void validateTotalPrice() {
		if (price == null || !price.isGreaterThanZero()) {
			throw new OrderDomainException("Total price should be greater than zero");
		}
	}

	private void validateItemsPrice() {
		Money orderItemsTotalPrice = items.stream()
				.map(orderItem -> {
					validateItemPrice(orderItem);
					return orderItem.getSubTotal();
				})
				.reduce(Money.ZERO, Money::add);

		if (!orderItemsTotalPrice.equals(price)) {
			throw new OrderDomainException("Total price should be equal to items total price");
		}
	}

	private void validateItemPrice(OrderItem orderItem) {
		if (!orderItem.isPriceValid()) {
			throw new OrderDomainException("Order item price is invalid");
		}
	}

	private Order(Builder builder) {
		super.setId(builder.orderId);
		customerId = builder.customerId;
		restaurantId = builder.restaurantId;
		deliveryAddress = builder.deliveryAddress;
		price = builder.price;
		items = builder.items;
		trackingId = builder.trackingId;
		orderStatus = builder.orderStatus;
		failureMessages = builder.failureMessages;
	}


	public CustomerId getCustomerId() {
		return customerId;
	}

	public RestaurantId getRestaurantId() {
		return restaurantId;
	}

	public StreetAddress getDeliveryAddress() {
		return deliveryAddress;
	}

	public Money getPrice() {
		return price;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public TrackingId getTrackingId() {
		return trackingId;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public List<String> getFailureMessages() {
		return failureMessages;
	}

	public static final class Builder {
		private OrderId orderId;
		private CustomerId customerId;
		private RestaurantId restaurantId;
		private StreetAddress deliveryAddress;
		private Money price;
		private List<OrderItem> items;
		private TrackingId trackingId;
		private OrderStatus orderStatus;
		private List<String> failureMessages;

		private Builder() {
		}

		public static Builder builder() {
			return new Builder();
		}

		public Builder orderId(OrderId val) {
			orderId = val;
			return this;
		}

		public Builder customerId(CustomerId val) {
			customerId = val;
			return this;
		}

		public Builder restaurantId(RestaurantId val) {
			restaurantId = val;
			return this;
		}

		public Builder deliveryAddress(StreetAddress val) {
			deliveryAddress = val;
			return this;
		}

		public Builder price(Money val) {
			price = val;
			return this;
		}

		public Builder items(List<OrderItem> val) {
			items = val;
			return this;
		}

		public Builder trackingId(TrackingId val) {
			trackingId = val;
			return this;
		}

		public Builder orderStatus(OrderStatus val) {
			orderStatus = val;
			return this;
		}

		public Builder failureMessages(List<String> val) {
			failureMessages = val;
			return this;
		}

		public Order build() {
			return new Order(this);
		}
	}
}
