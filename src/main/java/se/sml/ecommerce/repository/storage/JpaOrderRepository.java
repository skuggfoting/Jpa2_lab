package se.sml.ecommerce.repository.storage;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import se.sml.ecommerce.model.Order;
import se.sml.ecommerce.model.User;
import se.sml.ecommerce.repository.OrderRepository;
import se.sml.ecommerce.repository.checkedexception.RepositoryException;

public class JpaOrderRepository implements OrderRepository
{

	// private HashMap<String, Object> userMap = new HashMap<>();

	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("PersistenceUnit");

	// EntityManager manager = factory.createEntityManager();

	@Override
	public void create(Order order) throws RepositoryException
	{
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();

		manager.persist(order);

		manager.getTransaction().commit();
		manager.close();

		manager = factory.createEntityManager();
	}

	@Override
	public Order getById(Long orderId) throws RepositoryException
	{
		EntityManager manager = factory.createEntityManager();
		Order order = manager.find(Order.class, orderId);
		manager.close();
		return order;
	}

	@Override
	public List<Order> getAll() throws RepositoryException
	{
		EntityManager manager = factory.createEntityManager();
		List<Order> order = manager.createNamedQuery("Order.getAll", Order.class).getResultList();
		manager.close();
		return order;
	}

	@Override
	public Order getByName(String username) throws RepositoryException
	{
		EntityManager manager = factory.createEntityManager();
		Order order = manager.createNamedQuery("Order.getByUsername", Order.class).setParameter("username", username).getSingleResult();
		manager.close();
		return order;
	}

	// Update an order specifying username, what values and which properties to
	@Override
	public void update(String name, Object value, String updateProperty) throws RepositoryException
	{
		try
		{
			EntityManager manager = factory.createEntityManager();
			Order order = getByName(name);

			switch (updateProperty)
			{
			case ("updateUsername"):
				String username = (String) value;
//				order.setPassword(username);
				break;
			case ("updatePassword"):
				String password = (String) value;
//				order.setPassword(password);
				break;
			case ("updateStatus"):
				String status = (String) value;
//				order.setStatus(status);
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


	@Override
	public Collection<List<Order>> getAllOrders() throws RepositoryException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public List<Order> getOrderByStatus() throws RepositoryException
	{
		EntityManager manager = factory.createEntityManager();
		List<Order> order = manager.createNamedQuery("Order.getOrderByStatus", Order.class).getResultList();
		manager.close();
		return order;
	}

	@Override
	public List<Order> getAllOrdersFromUser(User user) throws RepositoryException
	{
		// TODO Auto-generated method stub
		return null;
	}

	public List<Order> getOrderByMinValue() throws RepositoryException
	{
		EntityManager manager = factory.createEntityManager();
		List<Order> order = manager.createNamedQuery("Order.getOrderByMinValue", Order.class).getResultList();
		manager.close();
		return order;
	}
}
