package se.sml.ecommerce.model;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import se.sml.ecommerce.repository.checkedexception.RepositoryException;

@Entity
@NamedQueries(value = {
		@NamedQuery(name = "User.getAll", query = "SELECT e FROM User e"),
		@NamedQuery(name = "User.getByUsername", query = "SELECT e FROM User e WHERE e.username = :username"),
		@NamedQuery(name = "User.getOrdersByUserId", query = "SELECT e FROM User e JOIN FETCH e.order WHERE e.id = :id")
})

public final class User
{
	@Id
	@GeneratedValue
	private Long userId;

	@Column(unique = true, nullable = false)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String status;

	// Many Order for one User
	@OneToMany(mappedBy = "user")
	private Collection<Order> order;

	protected User()
	{
	}

	public User(String username, String password, String status) throws RepositoryException
	{
		this.username = username;
		this.password = password;

		setStatus(status);

	}

	public long getUserId()
	{
		return userId;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public String getStatus()
	{
		return status;
	}

	public Collection<Order> getOrder()
	{
		return new HashSet<>(order);
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setStatus(String status) throws RepositoryException
	{

		if (status == "Passive" || status == "Active")
		{
			this.status = status;
		}
		else
		{
			throw new RepositoryException("Status must be 'Active' or 'Passive'");
		}
	}

	@Override
	public String toString()
	{
		return userId + " : " + username + " : " + password + " : " + status;
	}

	@Override
	public boolean equals(Object otherObj)
	{
		if (this == otherObj)
		{
			return true;
		}

		if (otherObj instanceof User)
		{
			User otherUser = (User) otherObj;
			return this.username.equals(otherUser.username);
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 1;
		result += 37 * username.hashCode();
		return result;
	}
}