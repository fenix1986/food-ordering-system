package pl.metaclass.ordering.system.order.service.domain.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.metaclass.ordering.system.domain.valueobject.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PaymentResponse {
	private final String id;
	private final String sagaId;
	private final String orderId;
	private final String paymentId;
	private final String customerId;
	private final BigDecimal price;
	private final Instant createdAt;
	private final PaymentStatus paymentStatus;
	private final List<String> failureMessages;
}
