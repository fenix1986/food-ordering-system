package pl.metaclass.ordering.system.order.service.domain;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.metaclass.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import pl.metaclass.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import pl.metaclass.ordering.system.order.service.domain.entity.Customer;
import pl.metaclass.ordering.system.order.service.domain.entity.Order;
import pl.metaclass.ordering.system.order.service.domain.entity.Restaurant;
import pl.metaclass.ordering.system.order.service.domain.event.OrderCreatedEvent;
import pl.metaclass.ordering.system.order.service.domain.exception.OrderDomainException;
import pl.metaclass.ordering.system.order.service.domain.mapper.OrderDataMapper;
import pl.metaclass.ordering.system.order.service.domain.ports.output.respository.CustomerRepository;
import pl.metaclass.ordering.system.order.service.domain.ports.output.respository.OrderRepository;
import pl.metaclass.ordering.system.order.service.domain.ports.output.respository.RestaurantRepository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderCreateCommandHandler {

	private final OrderDomainService orderDomainService;
	private final OrderRepository orderRepository;
	private final CustomerRepository customerRepository;
	private final RestaurantRepository restaurantRepository;
	private final OrderDataMapper orderDataMapper;
	private final ApplicationDomainEventPublisher applicationDomainEventPublisher;

	public OrderCreateCommandHandler(OrderDomainService orderDomainService, OrderRepository orderRepository,
			CustomerRepository customerRepository, RestaurantRepository restaurantRepository,
			OrderDataMapper orderDataMapper, ApplicationDomainEventPublisher applicationDomainEventPublisher) {
		this.orderDomainService = orderDomainService;
		this.orderRepository = orderRepository;
		this.customerRepository = customerRepository;
		this.restaurantRepository = restaurantRepository;
		this.orderDataMapper = orderDataMapper;
		this.applicationDomainEventPublisher = applicationDomainEventPublisher;
	}

	@Transactional
	public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
		checkCustomer(createOrderCommand.getCustomerId());
		Restaurant restaurant = checkRestaurant(createOrderCommand);
		Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
		OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitializeOrder(order, restaurant);
		Order orderResult = saveOrder(order);
		log.info("Order with id {} created", orderResult.getId());
		applicationDomainEventPublisher.publish(orderCreatedEvent);
		return orderDataMapper.orderToCreateOrderResponse(orderResult);
	}

	private void checkCustomer(@NotNull UUID customerId) {
		Optional<Customer> customer = customerRepository.findCustomer(customerId);
		if (customer.isEmpty()) {
			log.warn("Customer with id {} not found", customerId);
			throw new OrderDomainException("Customer with id " + customerId + " not found");
		}
	}

	private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
		Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
		Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);
		if (optionalRestaurant.isEmpty()) {
			log.warn("Restaurant with id {} not found", restaurant.getId());
			throw new OrderDomainException("Restaurant with id " + restaurant.getId() + " not found");
		}
		return optionalRestaurant.get();
	}

	private Order saveOrder(Order order) {
		Order result = orderRepository.save(order);
		if (result == null) {
			log.error("Order with id {} not saved", order.getId());
			throw new OrderDomainException("Order with id " + order.getId() + " not saved");
		}
		log.info("Order with id {} saved", order.getId());
		return result;
	}
}
