package se.sml.ecommerce.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import se.sml.ecommerce.model.OrderRow;
import se.sml.ecommerce.model.Product;
import se.sml.ecommerce.repository.checkedexception.RepositoryException;

@Entity //Name kan ligga här istället
@Table(name = "Orders")
@NamedQueries(value = {
		@NamedQuery(name = "Order.getAllOrders", query = "SELECT e FROM Order e"),
		@NamedQuery(name = "Order.getOrderByStatus", query = "SELECT e FROM Order e WHERE e.status = :status"),
		@NamedQuery(name = "Order.getOrderByMinValue", query = "SELECT e FROM Order e WHERE e.sum >= :sum")
})

public final class Order
{
	@Id
	@GeneratedValue
	private Long orderId;

	@Column(nullable = false)
	private String date;
	@Column
	private double sum;
	@Column
	private String status;

	// One User for many Order
	@ManyToOne()
	private User user;

	// One Order for many OrderRow
	@Column(nullable = false)
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Collection<OrderRow> orderRows;

	protected Order()
	{
	}

	public Order(String date, User user, String status)
	{
		this.date = date;
		this.user = user;
		this.status = status;
		this.orderRows = new ArrayList<>();
	}

	public double getOrderSum()
	{
		for (OrderRow OrderRow : orderRows)
		{
			sum += OrderRow.getSum();
		}
		return sum;
	}

	public long getOrderId()
	{
		return orderId;
	}

	public String getDate()
	{
		return date;
	}

	// add any product of one piece
	public void addOrderRow(Product product)
	{
		addOrderRow(product, 1);

	}

	// add products of multiple pieces
	public void addOrderRow(Product product, int i)
	{
		orderRows.add(new OrderRow(product, i));
	}

	public Collection<OrderRow> getOrderRows()
	{
		return new ArrayList<>(orderRows);
	}

	public void setStatus(String status) throws RepositoryException
	{
		if (status == "Placed" || status == "Shipped" || status == "Payed" || status == "Cancelled")
		{
			this.status = status;
		}
		else
		{
			throw new RepositoryException("Status must be 'Placed', 'Shipped', 'Payed' or 'Cancelled'");
		}
	}

	public void setDate(String date)
	{
		this.date = date;

	}

	@Override
	public String toString()
	{
		return orderId + " : " + date + " : " + status + " : " + orderRows;
	}

	@Override
	public boolean equals(Object otherObj)
	{
		if (this == otherObj)
		{
			return true;
		}

		if (otherObj instanceof Order)
		{
			Order otherOrder = (Order) otherObj;
			return this.date.equals(otherOrder.date) && this.user.getUsername().equals(otherOrder.user.getUsername());
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += 37 * date.hashCode();
		result += 37 * user.getUsername().hashCode();
		return result;
	}

}
