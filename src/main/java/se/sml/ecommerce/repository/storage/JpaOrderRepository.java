package se.sml.ecommerce.repository.storage;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import se.sml.ecommerce.model.Order;
import se.sml.ecommerce.model.User;
import se.sml.ecommerce.repository.OrderRepository;
import se.sml.ecommerce.repository.checkedexception.RepositoryException;

public class JpaOrderRepository implements OrderRepository
{

	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("PersistenceUnit");

	@Override
	public void create(Order order) throws RepositoryException
	{
		try
		{
			EntityManager manager = factory.createEntityManager();
			manager.getTransaction().begin();

			manager.persist(order);

			manager.getTransaction().commit();
			manager.close(); //Skall ligga i ett finally-block, manager utanför
		}
		catch (PersistenceException e)
		{
			throw new RepositoryException();
		}
	}

	@Override
	public Order getById(Long orderId) throws RepositoryException
	{
		try
		{
			EntityManager manager = factory.createEntityManager();
			Order order = manager.find(Order.class, orderId);
			manager.close();
			return order;
		}
		catch (IllegalArgumentException e)
		{
			throw new RepositoryException();
		}
	}

	@Override
	public Collection<Order> getAll() throws RepositoryException
	{
		EntityManager manager = factory.createEntityManager();
		Collection<Order> orders = manager.createNamedQuery("Order.getAllOrders", Order.class).getResultList();
		manager.close();
		return orders;
	}

	@Override
	public Order getByName(String username) throws RepositoryException
	{
		try
		{
			EntityManager manager = factory.createEntityManager();
			Order order = manager.createNamedQuery("Order.getByUsername", Order.class).setParameter("username", username).getSingleResult();
			manager.close();
			return order;
		}
		catch (NoResultException e)
		{
			throw new RepositoryException();
		}
	}

	// Update an order specifying username, what values and which properties to
	@Override
	public void update(Long userId, String updateProperty, Object updatedValue) throws RepositoryException
	{
		try
		{
			EntityManager manager = factory.createEntityManager();
			Order order = getById(userId);

			switch (updateProperty)
			{
			case ("updateDate"):
				String date = (String) updatedValue;
				order.setDate(date);
				break;
			case ("updateOrderRow"):
//				String password = (String) updatedValue;
//				order.addOrderRow((Product)updatedValue);;
				break;
			case ("updateStatus"):
				String status = (String) updatedValue;
				order.setStatus(status);
				break;
			}

			manager.getTransaction().begin();
			order = manager.merge(order);
			manager.getTransaction().commit();
			manager.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RepositoryException(e);
		}
	}
	
/////////////// ALTERNATIV UPDATE!!!		
	public Order update2(Order order) throws RepositoryException
	{
		try
		{
			EntityManager manager = factory.createEntityManager();
			manager.getTransaction().begin();

			manager.merge(order);

			manager.getTransaction().commit();
			manager.close();

			return order;
		}
		catch (PersistenceException e)
		{
			throw new RepositoryException();
		}
	}

	public Collection<Order> getOrderByStatus(String status) throws RepositoryException
	{
		try
		{
			EntityManager manager = factory.createEntityManager();
			Collection<Order> orders = manager.createNamedQuery("Order.getOrderByStatus", Order.class).setParameter("status", status).getResultList();
			manager.close();

			if (orders.isEmpty() == false)
			{
				return orders;
			}
			else
			{
				throw new RepositoryException();
			}
		}
		catch (NoResultException e)
		{
			throw new RepositoryException();
		}
	}

	public Collection<Order> getOrdersByUserId(Long userId) throws RepositoryException
	{
		try
		{
			EntityManager manager = factory.createEntityManager();
			User user = manager.createNamedQuery("User.getOrdersByUserId", User.class).setParameter("id", userId).getSingleResult();
			manager.close();

			if (user.getOrder().isEmpty() == false)
			{
				return user.getOrder();
			}
			else
			{
				throw new RepositoryException();
			}
		}
		catch (NoResultException e)
		{
			throw new RepositoryException();
		}
	}

	public Collection<Order> getOrderByMinValue(double sum) throws RepositoryException
	{
		EntityManager manager = factory.createEntityManager();
		Collection<Order> orders = manager.createNamedQuery("Order.getOrderByMinValue", Order.class).setParameter("sum", sum).getResultList();
		manager.close();
		return orders;
	}
}
