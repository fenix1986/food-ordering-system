package pl.metaclass.ordering.system.order.service.domain.exception;

import pl.metaclass.ordering.system.domain.exception.DomainException;

public class OrderNotFoundException extends DomainException {
	public OrderNotFoundException(String message) {
		super(message);
	}

	public OrderNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
