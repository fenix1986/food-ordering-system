package pl.metaclass.ordering.system.order.service.domain.ports.output.respository;

import pl.metaclass.ordering.system.order.service.domain.entity.Order;
import pl.metaclass.ordering.system.order.service.domain.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {

	Order save(Order order);

	Optional<Order> findByTrackingId(TrackingId trackingId);
}
