package pl.metaclass.ordering.system.order.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pl.metaclass.ordering.system.order.service.domain.dto.message.PaymentResponse;
import pl.metaclass.ordering.system.order.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener;

@Slf4j
@Validated
@Service
public class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {
	@Override
	public void paymentCompleted(PaymentResponse paymentResponse) {

	}

	@Override
	public void paymentCancelled(PaymentResponse paymentResponse) {

	}
}
