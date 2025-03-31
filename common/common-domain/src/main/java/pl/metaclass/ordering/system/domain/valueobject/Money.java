package pl.metaclass.ordering.system.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {
	private final BigDecimal amount;

	public static Money ZERO = new Money(BigDecimal.ZERO);

	public Money(BigDecimal amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount cannot be null");
		}

		this.amount = setScale(amount);
	}

	private BigDecimal setScale(BigDecimal input) {
		return input.setScale(2, RoundingMode.HALF_EVEN);
	}

	public boolean isGreaterThanZero() {
		return amount.compareTo(BigDecimal.ZERO) > 0;
	}

	public boolean isGreaterThan(Money money) {
		if (money == null) {
			throw new IllegalArgumentException("Money cannot be null");
		}

		return amount.compareTo(money.amount) > 0;
	}

	public Money add(Money money) {
		if (money == null) {
			throw new IllegalArgumentException("Money cannot be null");
		}

		return new Money(this.amount.add(money.amount));
	}

	public Money subtract(Money money) {
		if (money == null) {
			throw new IllegalArgumentException("Money cannot be null");
		}

		return new Money(this.amount.subtract(money.amount));
	}

	public Money multiply(int multiplier) {
		return new Money(this.amount.multiply(new BigDecimal(multiplier)));
	}

	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Money money = (Money) o;
		return Objects.equals(amount, money.amount);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(amount);
	}
}
