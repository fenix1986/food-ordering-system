package pl.metaclass.ordering.system.order.service.domain.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.metaclass.ordering.system.domain.valueobject.OrderApprovalStatus;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RestaurantApprovalResponse {
	private final String id;
	private final String sagaId;
	private final String orderId;
	private final String restaurantId;
	private final Instant createdAt;
	private final OrderApprovalStatus orderApprovalStatus;
	private final List<String> failureMessages;
}
