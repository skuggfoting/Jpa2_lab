package se.sml.ecommerce.repository.storage;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import se.sml.ecommerce.model.User;
import se.sml.ecommerce.repository.UserRepository;
import se.sml.ecommerce.repository.checkedexception.RepositoryException;

public class JpaUserRepository implements UserRepository
{

	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("PersistenceUnit");

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
	public Collection<User> getAll() throws RepositoryException
	{
		try
		{
			EntityManager manager = factory.createEntityManager();
			Collection<User> users = manager.createNamedQuery("User.getAll", User.class).getResultList();
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

	@Override
	public void update(Long objectId, String updateProperty, Object updatedValue) throws RepositoryException
	{
		try
		{
			EntityManager manager = factory.createEntityManager();
			User user = getById(objectId);

			switch (updateProperty)
			{
			case ("updateUsername"):
				String username = (String) updatedValue;
				user.setPassword(username);
				break;
			case ("updatePassword"):
				String password = (String) updatedValue;
				user.setPassword(password);
				break;
			case ("updateStatus"):
				String status = (String) updatedValue;
				user.setStatus(status);
				break;
			}

			manager.getTransaction().begin();
			user = manager.merge(user);
			manager.getTransaction().commit();
			manager.close();
		}
		catch (Exception e)
		{
			throw new RepositoryException(e);
		}
	}
}