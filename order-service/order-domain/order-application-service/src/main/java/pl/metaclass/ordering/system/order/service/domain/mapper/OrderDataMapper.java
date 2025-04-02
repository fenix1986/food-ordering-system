package pl.metaclass.ordering.system.order.service.domain.mapper;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import pl.metaclass.ordering.system.domain.valueobject.CustomerId;
import pl.metaclass.ordering.system.domain.valueobject.Money;
import pl.metaclass.ordering.system.domain.valueobject.ProductId;
import pl.metaclass.ordering.system.domain.valueobject.RestaurantId;
import pl.metaclass.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import pl.metaclass.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import pl.metaclass.ordering.system.order.service.domain.dto.create.OrderAddress;
import pl.metaclass.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import pl.metaclass.ordering.system.order.service.domain.entity.Order;
import pl.metaclass.ordering.system.order.service.domain.entity.OrderItem;
import pl.metaclass.ordering.system.order.service.domain.entity.Product;
import pl.metaclass.ordering.system.order.service.domain.entity.Restaurant;
import pl.metaclass.ordering.system.order.service.domain.valueobject.StreetAddress;

import java.util.List;
import java.util.UUID;

@Component
public class OrderDataMapper {

	public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
		return Restaurant.Builder.builder()
				.restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
				.products(createOrderCommand.getItems().stream()
						.map(orderItem -> new Product(new ProductId(orderItem.getProductId())))
						.toList())
				.build();
	}

	public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
		return Order.Builder.builder()
				.customerId(new CustomerId(createOrderCommand.getCustomerId()))
				.restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
				.deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getAddress()))
				.price(new Money(createOrderCommand.getPrice()))
				.items(orderItemsToOrderItemEntities(createOrderCommand.getItems()))
				.build();
	}

	public CreateOrderResponse orderToCreateOrderResponse(Order order, String message) {
		return CreateOrderResponse.builder()
				.orderTrackingId(order.getTrackingId().getValue())
				.orderStatus(order.getOrderStatus())
				.message(message)
				.build();
	}

	public TrackOrderResponse orderToTrackOrderResponse(Order order) {
		return TrackOrderResponse.builder()
				.orderTrackingId(order.getTrackingId().getValue())
				.orderStatus(order.getOrderStatus())
				.failureMessages(order.getFailureMessages())
				.build();
	}

	private StreetAddress orderAddressToStreetAddress(@NotNull OrderAddress address) {
		return new StreetAddress(UUID.randomUUID(), address.getStreet(), address.getPostalCode(), address.getCity());
	}

	private List<OrderItem> orderItemsToOrderItemEntities(@NotNull List<pl.metaclass.ordering.system.order.service.domain.dto.create.OrderItem> items) {
		return items.stream().map(item -> OrderItem.Builder.builder()
				.product(new Product(new ProductId(item.getProductId())))
				.price(new Money(item.getPrice()))
				.quantity(item.getQuantity())
				.subTotal(new Money(item.getSubTotal()))
				.build()
		).toList();
	}
}
