package se.sml.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import se.sml.ecommerce.model.OrderRow;
import se.sml.ecommerce.model.Product;

/*
- Hämta alla order som har en viss status
- Hämta alla order med ett visst minimivärde (ex. alla order som har ett värde högre än 10 000kr)
 */
@Entity
@Table(name="Orders")
@NamedQueries(value = {
		@NamedQuery(name = "Order.getAllOrders", query = "SELECT e FROM Order e"),
//		@NamedQuery(name = "Order.getOrderByUsername", query = "SELECT e FROM Order e WHERE e.username = :username"),
//		@NamedQuery(name = "Order.getOrderByStatus", query = "SELECT e FROM Order e WHERE e.status = : status"),
//		@NamedQuery(name = "Order.getOrderByMinValue", query = "SELECT e FROM Order e WHERE e.status = : status")
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

	//One User for many Order
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	private User user;

	//One Order for many OrderRow
	@Column(nullable = false)
	@OneToMany(mappedBy = "order")			//Något är fiskigt här helt klart och korresponderande i orderRow
	private List<OrderRow> orderRows;

	protected Order()
	{
	}

	public Order(String date, User user)
	{
		this.date = date;
		this.user = user;
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
	public void addOrderItems(Product product)
	{
		addOrderItems(product, 1);

	}

	// add products of multiple pieces
	public void addOrderItems(Product product, int i)
	{
		orderRows.add(new OrderRow(product, i));
	}

	public List<OrderRow> getOrderRows()
	{
		return new ArrayList<>(orderRows);
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
