package pl.metaclass.ordering.system.order.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import pl.metaclass.ordering.system.order.service.domain.event.OrderCreatedEvent;
import pl.metaclass.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;

@Slf4j
@Component
public class OrderCreatedEventApplicationListener {

	private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

	public OrderCreatedEventApplicationListener(OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher) {
		this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
	}

	@TransactionalEventListener
	void process(OrderCreatedEvent orderCreatedEvent) {

	}
}
