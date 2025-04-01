package pl.metaclass.ordering.system.order.service.domain.ports.output.respository;

import pl.metaclass.ordering.system.order.service.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

	Optional<Customer> findCustomer(UUID customerId);
}
