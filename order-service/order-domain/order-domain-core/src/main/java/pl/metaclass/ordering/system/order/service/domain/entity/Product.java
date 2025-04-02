package pl.metaclass.ordering.system.order.service.domain.entity;

import pl.metaclass.ordering.system.domain.entity.BaseEntity;
import pl.metaclass.ordering.system.domain.valueobject.Money;
import pl.metaclass.ordering.system.domain.valueobject.ProductId;

public class Product extends BaseEntity<ProductId> {
	private String name;
	private Money price;

	public Product(ProductId productId, String name, Money price) {
		super.setId(productId);
		this.name = name;
		this.price = price;
	}

	public Product(ProductId productId) {
		super.setId(productId);
	}

	public String getName() {
		return name;
	}

	public Money getPrice() {
		return price;
	}

	public void updateWithConfirmedNameAndPrice(String name, Money price) {
		this.name = name;
		this.price = price;
	}
}
