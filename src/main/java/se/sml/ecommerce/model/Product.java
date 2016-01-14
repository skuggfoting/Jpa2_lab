package se.sml.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries(value = {
        @NamedQuery(name = "Product.GetAll", query = "SELECT p FROM Product p"),
        @NamedQuery(name = "Product.GetByProductName", query = "SELECT p FROM Product p WHERE p.productName = :productName")
})
public class Product {
	@Id
	@GeneratedValue
	private Long productId;
	
	@Column(unique = true, nullable = false)
	private String productName;
	@Column(nullable = false)
	private double price;
	@Column(nullable = false)
	private String status;
	
//	@OneToOne
//	private final OrderRow orderRow;
	
	public Product() {}

	public Product(String productName, double price, String status) {
		this.productName = productName;
		this.price = price;
		this.status = status;
	}

	public Long getProductId() {
		return productId;
	}

	public String getProductName() {
		return productName;
	}

	public double getPrice() {
		return price;
	}

	public String getStatus() {
		return status;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public void setPrice(Long price) {
		this.price = price;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public boolean equals(Object otherObj) {
		if (this == otherObj) {
			return true;
		}
		
		if (otherObj instanceof Product) {
			Product otherProd = (Product) otherObj;
			return this.productName.equals(otherProd.productName);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		int result = 1;
		result += 37 * productName.hashCode();
		return result;
	}
	
	@Override
	public String toString() {
		return productName.toString() + " " + status.toString() + " " + price;
	}
}