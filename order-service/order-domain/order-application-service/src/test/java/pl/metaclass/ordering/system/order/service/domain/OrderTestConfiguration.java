package pl.metaclass.ordering.system.order.service.domain;

import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.metaclass.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import pl.metaclass.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import pl.metaclass.ordering.system.order.service.domain.ports.output.message.publisher.restuarantapproval.OrderPaidRestaurantRequestMessagePublisher;
import pl.metaclass.ordering.system.order.service.domain.ports.output.respository.CustomerRepository;
import pl.metaclass.ordering.system.order.service.domain.ports.output.respository.OrderRepository;
import pl.metaclass.ordering.system.order.service.domain.ports.output.respository.RestaurantRepository;

@SpringBootApplication(scanBasePackages = "pl.metaclass.ordering.system")
public class OrderTestConfiguration {

	@Bean
	public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher() {
		return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher.class);
	}

	@Bean
	public OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher() {
		return Mockito.mock(OrderCancelledPaymentRequestMessagePublisher.class);
	}

	@Bean
	public OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher() {
		return Mockito.mock(OrderPaidRestaurantRequestMessagePublisher.class);
	}

	@Bean
	public OrderRepository getOrderRepository() {
		return Mockito.mock(OrderRepository.class);
	}

	@Bean
	public RestaurantRepository getRestaurantRepository() {
		return Mockito.mock(RestaurantRepository.class);
	}

	@Bean
	public CustomerRepository getCustomerRepository() {
		return Mockito.mock(CustomerRepository.class);
	}

	@Bean
	public OrderDomainService getOrderDomainService() {
		return new OrderDomainServiceImpl();
	}
}
