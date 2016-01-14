package se.sml.ecommerce.repository;

import java.util.Collection;
import java.util.List;

import se.sml.ecommerce.model.Order;
import se.sml.ecommerce.model.User;
import se.sml.ecommerce.repository.checkedexception.RepositoryException;

public interface OrderRepository extends CrudRepository<Order>
{
//	Object createOrder(Order order) throws RepositoryException;

	Collection<List<Order>> getAllOrders() throws RepositoryException;
	
	List<Order> getOrderByStatus() throws RepositoryException;

	List<Order> getAllOrdersFromUser(User user) throws RepositoryException;
	
	List<Order> getOrderByMinValue() throws RepositoryException;
}