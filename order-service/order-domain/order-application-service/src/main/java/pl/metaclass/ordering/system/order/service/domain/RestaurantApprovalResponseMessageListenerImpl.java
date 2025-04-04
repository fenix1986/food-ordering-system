package pl.metaclass.ordering.system.order.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pl.metaclass.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse;
import pl.metaclass.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener;

@Slf4j
@Validated
@Service
public class RestaurantApprovalResponseMessageListenerImpl implements RestaurantApprovalResponseMessageListener {

	@Override
	public void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse) {

	}

	@Override
	public void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse) {

	}
}
