package pl.metaclass.ordering.system.order.service.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.metaclass.ordering.system.domain.valueobject.*;
import pl.metaclass.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import pl.metaclass.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import pl.metaclass.ordering.system.order.service.domain.dto.create.OrderAddress;
import pl.metaclass.ordering.system.order.service.domain.dto.create.OrderItem;
import pl.metaclass.ordering.system.order.service.domain.entity.Customer;
import pl.metaclass.ordering.system.order.service.domain.entity.Order;
import pl.metaclass.ordering.system.order.service.domain.entity.Product;
import pl.metaclass.ordering.system.order.service.domain.entity.Restaurant;
import pl.metaclass.ordering.system.order.service.domain.exception.OrderDomainException;
import pl.metaclass.ordering.system.order.service.domain.mapper.OrderDataMapper;
import pl.metaclass.ordering.system.order.service.domain.ports.input.service.OrderApplicationService;
import pl.metaclass.ordering.system.order.service.domain.ports.output.respository.CustomerRepository;
import pl.metaclass.ordering.system.order.service.domain.ports.output.respository.OrderRepository;
import pl.metaclass.ordering.system.order.service.domain.ports.output.respository.RestaurantRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
public class OrderApplicationServiceTest {

	@Autowired
	private OrderApplicationService orderApplicationService;

	@Autowired
	private OrderDataMapper orderDataMapper;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private CustomerRepository customerRepository;

	private CreateOrderCommand createOrderCommand;
	private CreateOrderCommand createOrderCommandWrongPrice;
	private CreateOrderCommand createOrderCommandWrongProductPrice;
	private final UUID CUSTOMER_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
	private final UUID RESTAURANT_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
	private final UUID PRODUCT_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174002");
	private final UUID ORDER_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174003");
	private final BigDecimal PRICE = new BigDecimal("200.00");

	@BeforeAll
	public void init() {
		createOrderCommand = CreateOrderCommand.builder()
				.customerId(CUSTOMER_ID)
				.restaurantId(RESTAURANT_ID)
				.address(OrderAddress.builder()
						.street("Ulica")
						.postalCode("00-000")
						.city("Warszawa")
						.build())
				.price(PRICE)
				.items(List.of(
						OrderItem.builder()
								.productId(PRODUCT_ID)
								.quantity(1)
								.price(new BigDecimal("50.00"))
								.subTotal(new BigDecimal("50.00"))
								.build(),
						OrderItem.builder()
								.productId(PRODUCT_ID)
								.quantity(3)
								.price(new BigDecimal("50.00"))
								.subTotal(new BigDecimal("150.00"))
								.build()
				))
				.build();

		createOrderCommandWrongPrice = CreateOrderCommand.builder()
				.customerId(CUSTOMER_ID)
				.restaurantId(RESTAURANT_ID)
				.address(OrderAddress.builder()
						.street("Ulica")
						.postalCode("00-000")
						.city("Warszawa")
						.build())
				.price(new BigDecimal("250.00"))
				.items(List.of(
						OrderItem.builder()
								.productId(PRODUCT_ID)
								.quantity(1)
								.price(new BigDecimal("50.00"))
								.subTotal(new BigDecimal("50.00"))
								.build(),
						OrderItem.builder()
								.productId(PRODUCT_ID)
								.quantity(3)
								.price(new BigDecimal("50.00"))
								.subTotal(new BigDecimal("150.00"))
								.build()
				))
				.build();

		createOrderCommandWrongProductPrice = CreateOrderCommand.builder()
				.customerId(CUSTOMER_ID)
				.restaurantId(RESTAURANT_ID)
				.address(OrderAddress.builder()
						.street("Ulica")
						.postalCode("00-000")
						.city("Warszawa")
						.build())
				.price(new BigDecimal("210.00"))
				.items(List.of(
						OrderItem.builder()
								.productId(PRODUCT_ID)
								.quantity(1)
								.price(new BigDecimal("60.00"))
								.subTotal(new BigDecimal("60.00"))
								.build(),
						OrderItem.builder()
								.productId(PRODUCT_ID)
								.quantity(3)
								.price(new BigDecimal("50.00"))
								.subTotal(new BigDecimal("150.00"))
								.build()
				))
				.build();

		Customer customer = new Customer();
		customer.setId(new CustomerId(CUSTOMER_ID));

		Restaurant restaurantResponse = Restaurant.Builder.builder()
				.restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
				.products(List.of(
						new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00"))),
						new Product(new ProductId(PRODUCT_ID), "product-2", new Money(new BigDecimal("50.00")))
				))
				.active(true)
				.build();

		Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
		order.setId(new OrderId(ORDER_ID));

		when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
		when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
				.thenReturn(Optional.of(restaurantResponse));
		when(orderRepository.save(any(Order.class))).thenReturn(order);
	}

	@Test
	public void testCreateOrder() {
		CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderCommand);
		assertEquals(OrderStatus.PENDING, createOrderResponse.getOrderStatus());
		assertEquals("Order created successfully", createOrderResponse.getMessage());
		assertNotNull(createOrderResponse.getOrderTrackingId());
	}

	@Test
	public void testCreatedOrderWithWrongTotalPrice() {
		OrderDomainException orderDomainException = assertThrows(OrderDomainException.class, () -> orderApplicationService.createOrder(createOrderCommandWrongPrice));
		assertEquals("Total price should be equal to items total price", orderDomainException.getMessage());
	}

	@Test
	public void testCreatedOrderWithWrongProductPrice() {
		OrderDomainException orderDomainException = assertThrows(OrderDomainException.class, () -> orderApplicationService.createOrder(createOrderCommandWrongProductPrice));
		assertEquals("Order item price is invalid", orderDomainException.getMessage());
	}

	@Test
	public void testCreatedOrderWithInactiveRestaurant() {
		Restaurant restaurantResponse = Restaurant.Builder.builder()
				.restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
				.products(List.of(
						new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00"))),
						new Product(new ProductId(PRODUCT_ID), "product-2", new Money(new BigDecimal("50.00")))
				))
				.active(false)
				.build();

		when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
				.thenReturn(Optional.of(restaurantResponse));

		OrderDomainException orderDomainException = assertThrows(OrderDomainException.class, () -> orderApplicationService.createOrder(createOrderCommand));
		assertEquals("Restaurant is not active: " + createOrderCommand.getRestaurantId(), orderDomainException.getMessage());
	}
}
