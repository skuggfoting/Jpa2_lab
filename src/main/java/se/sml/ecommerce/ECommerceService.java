package se.sml.ecommerce;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import se.sml.ecommerce.model.Order;
import se.sml.ecommerce.model.Product;
import se.sml.ecommerce.model.User;
import se.sml.ecommerce.repository.OrderRepository;
import se.sml.ecommerce.repository.ProductRepository;
import se.sml.ecommerce.repository.UserRepository;
import se.sml.ecommerce.repository.checkedexception.RepositoryException;
import se.sml.ecommerce.repository.uncheckedexception.ECommerceServiceException;

public final class ECommerceService
{
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;

	public ECommerceService(UserRepository userRepository, ProductRepository productRepository, OrderRepository orderRepository)

	{
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.orderRepository = orderRepository;
		// this.orderRepository = orderRepository;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// USER KOD HÄR UNDER:

	public User createUser(User user)
	{
		try
		{
			userRepository.create(user);
			return user;
		}
		catch (RepositoryException e)
		{
			throw new ECommerceServiceException("User already exists");
		}

	}

	public void updateUser(String username, Object value, String updateProperty)
	{
		try
		{
			userRepository.update(username, value, updateProperty);
		}
		catch (RepositoryException e)
		{
			throw new ECommerceServiceException("Can't update the user, Please try again later", e);
		}
	}

	// public boolean updateUser(User user)
	// {
	// try
	// {
	// userRepository.update(user);
	// return true;
	// }
	// catch (RepositoryException e)
	// {
	// throw new ECommerceServiceException("Can't update user that does not
	// exist");
	// }
	// }

	// private boolean correctUserName(User user)
	// {
	// if (user.getUsername().trim().length() <= 30 && user.getUsername() !=
	// null)
	// {
	// if (checkPassword(user.getPassword()))
	// {
	// return true;
	// }
	// }
	// return false;
	// }
	//
	// private boolean checkPassword(String password)
	// {
	// // Regular expressions eller en if till
	// char a;
	// int count = 0;
	// assert password != null;
	// for (int i = 0; i < password.length(); i++)
	// {
	// a = password.charAt(i);
	// if (!Character.isLetterOrDigit(a))
	// {
	// return false;
	// }
	// else if (Character.isDigit(a))
	// {
	// count++;
	// }
	// }
	// return count >= 2;
	// }

	public User findUserById(long userId)
	{
		try
		{
			return userRepository.getById(userId);
		}
		catch (RepositoryException e)
		{
			throw new ECommerceServiceException("User does not exist");
		}
	}

	public User getByUsername(String username)
	{
		try
		{
			return userRepository.getByName(username);
		}
		catch (RepositoryException e)
		{
			throw new ECommerceServiceException("User does not exist");
		}
	}

	// public boolean setStatus(User user)
	// {
	// try
	// {
	// if (correctUserName(user) && userRepository.getByName(user.getUsername())
	// != null)
	// {
	// userRepository.update(user);
	// return true;
	// }
	// else
	// {
	// throw new ECommerceServiceException("User doesn't exist");
	// }
	// }
	// catch (RepositoryException e)
	// {
	// throw new ECommerceServiceException("User doesn't exist");
	// }
	// }

	public List<User> getAllUsers()
	{
		try
		{
			return userRepository.getAll();
		}
		catch (RepositoryException e)
		{
			throw new ECommerceServiceException("No saved users exists ", e);
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// PRODUCT KOD HÄR UNDER:

	// create one or more products
	// create one or more products //create
	public ECommerceService createProduct(Product product)
	{
		try
		{
			productRepository.create(product);
		}
		catch (RepositoryException ex)
		{
			throw new ECommerceServiceException("The product name '" + product.getProductName() + "' already exists. Please choose another name");
		}

		return this;
	}

	// get a product by product id
	public Product getProductById(Long id)
	{
		try
		{
			if (productRepository.getById(id) == null)
			{
				throw new ECommerceServiceException("The product with ID: " + id + " is not found in storage");
			}
			else
			{
				return productRepository.getById(id);
			}
		}
		catch (RepositoryException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	// get a product by product name
	public Product getProductByName(String name) throws ECommerceServiceException
	{
		Product product;
		try
		{
			product = productRepository.getByName(name);
		}
		catch (RepositoryException e)
		{
			throw new ECommerceServiceException("Product with the name '" + name + "' not found in storage");
		}
		return product;
	}

	// get all product
	public List<Product> getAllProduct()
	{
		try
		{
			return productRepository.getAll();
		}
		catch (RepositoryException e)
		{
			throw new ECommerceServiceException("No product is available at the storage", e);
		}
	}

	// Update a product specifying product name, what values and which
	// properties to update
	public void updateProduct(String prodName, Object value, String updateProperty)
	{
		try
		{
			productRepository.update(prodName, value, updateProperty);
		}
		catch (RepositoryException e)
		{
			throw new ECommerceServiceException("Can't update the product, Please try again later");
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ORDER KOD HÄR UNDER:

	public Order createOrder(Order order)
	{
		if (correctOrder(order))
		{
			try
			{
				orderRepository.create(order);
				return order;
			}
			catch (RepositoryException e)
			{
				throw new ECommerceServiceException("Could not create product", e);
			}
		}
		else
		{
			throw new ECommerceServiceException("Order is more than maximum limit of 50000");
		}
	}

	private final boolean correctOrder(Order order)
	{
		if (order.getOrderSum() <= 50000 && order.getOrderRows().isEmpty() == false)
		{
			return true;
		}
		return false;
	}

	// public Order findOrderById(String orderId)
	// {
	// try
	// {
	// return orderRepository.get(orderId);
	// }
	// catch (RepositoryException e)
	// {
	// throw new ECommerceServiceException("No such order");
	// }
	// }
	//
	// public void deleteOrder(String orderId)
	// {
	// try
	// {
	// orderRepository.delete(orderId);
	// }
	// catch (RepositoryException e)
	// {
	// throw new ECommerceServiceException("No such order");
	// }
	// }

//	public Order updateOrder(Order order)
//	{
//		try
//		{
//			orderRepository.update(order);
//			return order;
//		}
//		catch (RepositoryException e)
//		{
//			throw new ECommerceServiceException("No such order");
//		}
//	}

	public Collection<List<Order>> getAllOrders()
	{
		try
		{
			return orderRepository.getAllOrders();
		}
		catch (RepositoryException e)
		{
			throw new ECommerceServiceException("No orders");
		}
	}

	public List<Order> getAllOrders(User user)
	{
		try
		{
			return orderRepository.getAllOrdersFromUser(user);
		}
		catch (RepositoryException e)
		{
			throw new ECommerceServiceException("No such user or no order");
		}
	}
}
