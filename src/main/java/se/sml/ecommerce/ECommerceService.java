package se.sml.ecommerce;

import java.util.Collection;

import se.sml.ecommerce.model.Order;
import se.sml.ecommerce.model.Product;
import se.sml.ecommerce.model.User;
import se.sml.ecommerce.repository.OrderRepository;
import se.sml.ecommerce.repository.ProductRepository;
import se.sml.ecommerce.repository.UserRepository;
import se.sml.ecommerce.repository.checkedexception.RepositoryException;
import se.sml.ecommerce.uncheckedexception.ECommerceServiceException;

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
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// USER KOD HÄR UNDER:

	public ECommerceService createUser(User user)
	{
		try
		{
			userRepository.create(user);
			return this;
		}
		catch (RepositoryException e)
		{
			throw new ECommerceServiceException("User already exists");
		}

	}

	public void updateUser(Long userId, String updateProperty, Object updatedValue)
	{
		try
		{
			userRepository.update(userId, updateProperty, updatedValue);
		}
		catch (RepositoryException e)
		{
			throw new ECommerceServiceException("Can't update the user, Please try again later", e);
		}
	}

	public User getUserById(long userId)
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

	public Collection<User> getAllUsers()
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
	public Collection<Product> getAllProducts()
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

	public void updateProduct(Long productId, String updateProperty, Object updatedValue)
	{
		try
		{
			productRepository.update(productId, updateProperty, updatedValue);
		}
		catch (RepositoryException e)
		{
			throw new ECommerceServiceException("Can't update the product, Please try again later");
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ORDER KOD HÄR UNDER:

	public ECommerceService createOrder(Order order)
	{
		if (correctOrder(order))
		{
			try
			{
				orderRepository.create(order);
				return this;
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

	public Order getOrderById(Long orderId)
	{
		try
		{
			return orderRepository.getById(orderId);
		}
		catch (RepositoryException e)
		{
			throw new ECommerceServiceException("No such order");
		}
	}

	public void updateOrder(Long orderId, String updateProperty, Object updatedValue)
	{
		try
		{
			orderRepository.update(orderId, updateProperty, updatedValue);
		}
		catch (RepositoryException e)
		{
			throw new ECommerceServiceException("No such order");
		}
	}

	/////////////// ALTERNATIV UPDATE!!!
	public Order updateOrder2(Order order)
	{
		try
		{
			return orderRepository.update2(order);
		}
		catch (RepositoryException e)
		{
			throw new ECommerceServiceException("No such order");
		}
	}

	public Collection<Order> getAllOrders()
	{
		try
		{
			return orderRepository.getAll();
		}
		catch (RepositoryException e)
		{
			throw new ECommerceServiceException("No orders");
		}
	}

	public Collection<Order> getAllOrders(Long userId)
	{
		try
		{
			return orderRepository.getOrdersByUserId(userId);
		}
		catch (RepositoryException e)
		{
			throw new ECommerceServiceException("No such user or no order");
		}
	}

	public Collection<Order> getOrderByStatus(String status)
	{
		try
		{
			return orderRepository.getOrderByStatus(status);
		}
		catch (RepositoryException e)
		{
			throw new ECommerceServiceException("No such user or no order");
		}
	}

	public Collection<Order> getOrderByMinValue(double sum)
	{
		try
		{
			return orderRepository.getOrderByMinValue(sum);
		}
		catch (RepositoryException e)
		{
			throw new ECommerceServiceException("No such user or no order");
		}
	}
}
