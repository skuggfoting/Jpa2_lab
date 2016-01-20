package se.sml.ecommerce;

import java.util.Collection;

import se.sml.ecommerce.model.Order;
import se.sml.ecommerce.model.Product;
import se.sml.ecommerce.model.User;
import se.sml.ecommerce.repository.OrderRepository;
import se.sml.ecommerce.repository.ProductRepository;
import se.sml.ecommerce.repository.UserRepository;
import se.sml.ecommerce.repository.checkedexception.RepositoryException;
import se.sml.ecommerce.repository.storage.JpaOrderRepository;
import se.sml.ecommerce.repository.storage.JpaProductRepository;
import se.sml.ecommerce.repository.storage.JpaUserRepository;

public final class Main
{
	public static void main(String[] args) throws RepositoryException
	{

		// Initiate new user
		User mattias = new User("Mano", "1234", "Active");
		User shafi = new User("Shab", "5678", "Active");
		User lina = new User("Lica", "1357", "Passive");

		// Initiate new product
		Product product1 = new Product("milk", 33.55, "In stock");
		Product product2 = new Product("hat", 10.65, "Out of stock");
		Product product3 = new Product("computer", 20.8, "In stock");
		Product product4 = new Product("unicorn", 1337, "In stock");

		// Initiate order
		Order order1 = new Order("20151028", mattias, "Placed");
		order1.addOrderRow(product1, 5);
		order1.addOrderRow(product2);
		order1.addOrderRow(product3, 3);

		// Initiate order 2
		Order order2 = new Order("20160114", shafi, "Placed");
		order2.addOrderRow(product1, 3);
		order2.addOrderRow(product2);
		order2.addOrderRow(product3, 2);

		// Initiate order 3
		Order order3 = new Order("20160115", mattias, "Shipped");
		order3.addOrderRow(product1, 3);
		order3.addOrderRow(product2);
		order3.addOrderRow(product3, 2);

		// Initiate E-Commerce Service
		UserRepository userRepository = new JpaUserRepository();
		ProductRepository productRepository = new JpaProductRepository();
		OrderRepository orderRepository = new JpaOrderRepository();
		ECommerceService eService = new ECommerceService(userRepository, productRepository, orderRepository);

		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// PRODUCT KOD HÄR UNDER:

		// create one or more products and save it in the Database
		eService.createProduct(product1).createProduct(product2).createProduct(product3).createProduct(product4);

		// Update product
		eService.updateProduct(1L, "updateStatus", "Out of stock");

		// Get a product by product Id
		System.out.println(eService.getProductById(2L) + "\n");

		// Get all products
		Collection<Product> products = eService.getAllProducts();
		for (Product product : products)
		{
			System.out.println(product.toString());
		}

		// Get product by product name
		System.out.println("\n" + eService.getProductByName("milk") + "\n");

		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// USER KOD HÄR UNDER:

		// Create users
		eService.createUser(mattias).createUser(shafi).createUser(lina);

		// Update user
		eService.updateUser(5L, "Passive", "updateStatus");

		// Get an user by user Id
		System.out.println(eService.getUserById(5L) + "\n");

		// Get all users
		Collection<User> users = eService.getAllUsers();
		for (User user : users)
		{
			System.out.println(user.toString());
		}

		// Get user by username
		System.out.println("\n" + eService.getByUsername("mano") + "\n");

		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// ORDER KOD HÄR UNDER:

		// Create orders
		eService.createOrder(order1);
		eService.createOrder(order2);
		eService.createOrder(order3);

		// Update order
		eService.updateOrder(8L, "updateStatus", "Shipped");

		// ALTERNATIV UPDATE!!!
//		order1 = eService.getOrderById(8L);
//		order1.setStatus("Placed");
//		order1.addOrderRow(product4, 1);
//		System.out.println("HÄÄÄR: " + order1.getOrderRows().toString() + "\n");
//		order1.setDate("20160120");
//		System.out.println(eService.updateOrder2(order1) + "\n");

		
		// Get order by Id
		System.out.println("1: " + eService.getOrderById(8L) + "\n");

		// Get all orders
		System.out.println("2: " + eService.getAllOrders() + "\n");

		// Get all orders for specific user id
		System.out.println("3: " + eService.getAllOrders(5L) + "\n");

		// Get order by status
		System.out.println("4: " + eService.getOrderByStatus("Placed") + "\n");

		// Get order by min value
		System.out.println("5: " + eService.getOrderByMinValue(160) + "\n");

	}
}
