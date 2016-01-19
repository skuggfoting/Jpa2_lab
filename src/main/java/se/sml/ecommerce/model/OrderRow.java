package se.sml.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public final class OrderRow
{
	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	private int quantity;
	@Column
	private double sum;

	// One Product to one OrderRow
	@OneToOne()
	private Product product;

	protected OrderRow()
	{
	}

	public OrderRow(Product product, int quantity)
	{
		this.quantity = quantity;
		this.product = product;
	}

	public int getQuantity()
	{
		return quantity;
	}

	public double getSum()
	{
		sum = quantity * product.getPrice();
		return sum;
	}

	@Override
	public boolean equals(Object otherObj)
	{
		if (this == otherObj)
		{
			return true;
		}

		if (otherObj instanceof OrderRow)
		{
			OrderRow otherRows = (OrderRow) otherObj;
			return this.product.getProductName().equals(otherRows.product.getProductName());
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += 37 * product.getProductName().hashCode();
		return result;
	}
}
