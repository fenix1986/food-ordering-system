package pl.metaclass.ordering.system.order.service.domain.exception;

import pl.metaclass.ordering.system.domain.exception.DomainException;

public class OrderDomainException extends DomainException {

	public OrderDomainException(String message) {
		super(message);
	}

	public OrderDomainException(String message, Throwable cause) {
		super(message, cause);
	}
}
