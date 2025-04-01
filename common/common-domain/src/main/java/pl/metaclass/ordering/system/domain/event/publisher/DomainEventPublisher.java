package pl.metaclass.ordering.system.domain.event.publisher;

import pl.metaclass.ordering.system.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent<?>> {

	void publish(T domainEvent);
}
