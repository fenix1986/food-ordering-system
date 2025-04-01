package pl.metaclass.ordering.system.order.service.domain.ports.output.message.publisher.payment;

import pl.metaclass.ordering.system.domain.event.publisher.DomainEventPublisher;
import pl.metaclass.ordering.system.order.service.domain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
