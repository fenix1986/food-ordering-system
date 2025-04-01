package pl.metaclass.ordering.system.order.service.domain.ports.output.message.publisher.restuarantapproval;

import pl.metaclass.ordering.system.domain.event.publisher.DomainEventPublisher;
import pl.metaclass.ordering.system.order.service.domain.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
