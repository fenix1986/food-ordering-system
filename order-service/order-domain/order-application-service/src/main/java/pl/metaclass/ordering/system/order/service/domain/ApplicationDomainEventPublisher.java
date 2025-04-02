package pl.metaclass.ordering.system.order.service.domain;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import pl.metaclass.ordering.system.domain.event.publisher.DomainEventPublisher;
import pl.metaclass.ordering.system.order.service.domain.event.OrderCreatedEvent;

@Slf4j
@Component
public class ApplicationDomainEventPublisher implements ApplicationEventPublisherAware,
		DomainEventPublisher<OrderCreatedEvent> {

	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public void setApplicationEventPublisher(@Nullable ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	public void publish(OrderCreatedEvent domainEvent) {
		this.applicationEventPublisher.publishEvent(domainEvent);
		log.info("Published order created event orderId: {}", domainEvent.getOrder().getId().getValue());
	}
}
