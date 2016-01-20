package se.sml.ecommerce.repository;

import java.util.Collection;

import se.sml.ecommerce.model.Order;
import se.sml.ecommerce.repository.checkedexception.RepositoryException;

public interface OrderRepository extends CrudRepository<Order>
{
	Collection<Order> getOrderByStatus(String status) throws RepositoryException;

	Collection<Order> getOrdersByUserId(Long userId) throws RepositoryException;
	
	Collection<Order> getOrderByMinValue(double sum) throws RepositoryException;

/////////////// ALTERNATIV UPDATE!!!	
	Order update2(Order order) throws RepositoryException;
}