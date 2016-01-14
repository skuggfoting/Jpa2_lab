package se.sml.ecommerce.repository.storage;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import se.sml.ecommerce.model.Product;
import se.sml.ecommerce.model.User;
import se.sml.ecommerce.repository.UserRepository;
import se.sml.ecommerce.repository.checkedexception.RepositoryException;

public class JpaUserRepository implements UserRepository
{

	// private HashMap<String, Object> userMap = new HashMap<>();

	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("PersistenceUnit");

	// EntityManager manager = factory.createEntityManager();

	@Override
	public void create(User user) throws RepositoryException
	{
		try
		{
			EntityManager manager = factory.createEntityManager();
			manager.getTransaction().begin();
			manager.persist(user);
			manager.getTransaction().commit();
			manager.close();

			manager = factory.createEntityManager();
		}
		catch (PersistenceException e)
		{
			throw new RepositoryException();
		}
	}

	@Override
	public User getById(Long userId) throws RepositoryException
	{
		try
		{
			EntityManager manager = factory.createEntityManager();
			User user = manager.find(User.class, userId);
			manager.close();
			return user;
		}
		catch (IllegalArgumentException e)
		{
			throw new RepositoryException();
		}
	}

	@Override
	public List<User> getAll() throws RepositoryException
	{
		try
		{
			EntityManager manager = factory.createEntityManager();
			List<User> users = manager.createNamedQuery("User.getAll", User.class).getResultList();
			manager.close();
			return users;
		}
		catch (NoResultException e)
		{
			throw new RepositoryException();
		}
	}

	@Override
	public User getByName(String username) throws RepositoryException
	{
		try
		{
			EntityManager manager = factory.createEntityManager();
			User user = manager.createNamedQuery("User.getByUsername", User.class).setParameter("username", username).getSingleResult();
			manager.close();
			return user;
		}
		catch (NoResultException e)
		{
			throw new RepositoryException();
		}
	}

	// Update a user specifying username, what values and which properties to update
		@Override
		public void update(String name, Object value, String updateProperty) throws RepositoryException
		{
			try
			{
				EntityManager manager = factory.createEntityManager();
				User user = getByName(name);

				switch (updateProperty)
				{
				case ("updateUsername"):
					String username = (String) value;
					user.setPassword(username);
					break;
				case ("updatePassword"):
					String password = (String) value;
					user.setPassword(password);
					break;
				case ("updateStatus"):
					String status = (String) value;
					user.setStatus(status);
					break;
				}

				manager.getTransaction().begin();
				user = manager.merge(user);
				manager.getTransaction().commit();
				manager.close();
			}
			catch (Exception e)	{
				throw new RepositoryException(e);
			}
		}
}
