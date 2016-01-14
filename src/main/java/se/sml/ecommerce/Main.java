package se.sml.ecommerce;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

	// private static final EntityManagerFactory factory =
	// Persistence.createEntityManagerFactory("PersistenceUnit");

	public static void main(String[] args) throws RepositoryException
	{

		// Initiate new user
		User mattias = new User("Mano", "1234", "active");
		User shafi = new User("Shab", "5678", "active");
		User lina = new User("Lica", "1357", "passive");
		// User lina2 = new User("991212", "Lina Carlén", "lamborgini");

		// Initiate new product
		Product product1 = new Product("milk", 33.55, "In Stock");
		Product product2 = new Product("hat", 10.65, "Out of Stock");
		Product product3 = new Product("computer", 20.8, "In Stock");
		// Product product1 = new Product("Milk", 11L, "In Stock");
		// Product product2 = new Product("Cola", 14L, "Out of Stock");
		// Product product3 = new Product("Juice", 17L, "In Stock");

		// Initiate order
		Order order1 = new Order("20151028", mattias, "Placed");
		order1.addOrderItems(product1, 5);
		order1.addOrderItems(product2);
		order1.addOrderItems(product3, 3);

		// Initiate order 2
		Order order2 = new Order("20160114", shafi, "Placed");
		order2.addOrderItems(product1, 3);
		order2.addOrderItems(product2);
		order2.addOrderItems(product3, 2);

		// Initiate E-Commerce Service
		UserRepository userRepository = new JpaUserRepository();
		ProductRepository productRepository = new JpaProductRepository();
		OrderRepository orderRepository = new JpaOrderRepository();
		ECommerceService eService = new ECommerceService(userRepository, productRepository, orderRepository);

		// create one or more products and save it in the Database
		eService.createProduct(product1).createProduct(product2).createProduct(product3);

		//
		// // get a product by product Id
		// System.out.println(eService.getProductById(2L));
		//
		// // get a product by product Name. Fetches a single product because
		// each product name is unique.
		// System.out.println(eService.getProductByName("AA"));

		// Create user
		eService.createUser(mattias);
		eService.createUser(shafi);
		eService.createUser(lina);

		// Find user by id
		// System.out.println(eService.findUserById(1L).toString());
		// System.out.println(eService.readUser(shafi));
		// System.out.println(eService.readUser(lina));

		// Get all users
		List<User> users = eService.getAllUsers();
		for (User user : users)
		{
			System.out.println(user.toString());
		}

		// Get user by username
		System.out.println(eService.getByUsername("Shab").toString());

		// // update user
		// mattias.setUsername("MattiasN");
		// System.out.println(eService.updateUser(mattias));
		// System.out.println(mattias.toString());

		// update user
		// User mattiasn = new User("bajs","macka","kiss","active");
		// mattias.setStatus("passive");
		// System.out.println(eService.setStatus(mattias));
		// System.out.println(mattias.toString());

		// Update a product specifying product name, what values and which
		// properties to update
		// String updateProperty = "updateStatus";
		// eService.updateProduct("milk", "out of stock", updateProperty);

		String updateProperty = "updateStatus";
		eService.updateUser("Mano", "Passive", updateProperty);

		eService.createOrder(order1);
		System.out.println(order2.toString());
		eService.createOrder(order2);
	}
}