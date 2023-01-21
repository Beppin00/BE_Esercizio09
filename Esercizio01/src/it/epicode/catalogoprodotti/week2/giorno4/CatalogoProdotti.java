/**
 * 
 */
package it.epicode.catalogoprodotti.week2.giorno4;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author Rino Marra
 *
 */
public class CatalogoProdotti {
	private static LocalDate start = LocalDate.of(2021, 2, 1);
	private static LocalDate end = LocalDate.of(2021, 4, 1);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
			
		long minDay = start.toEpochDay();
	    long maxDay = end.toEpochDay();
	    long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
	    LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
	    
		String categoryBooks = Category.BOOKS.name();
		String categoryCellulari = Category.CELLULARI.name();
		String categoryBaby = Category.BABY.name();
		String categoryBoys = Category.BOYS.name();
		
		List<Product> productRepo = Arrays.asList(new Product(12345678, "Java EE", categoryBooks, 120.00),
				new Product(00001000, "Cellulare", categoryCellulari, 500.00),
				new Product(12345678, "Java SE", categoryBooks, 99.00),
				new Product(12345678, "Camicia", categoryBooks, 50.00));
		List<Product> productOrderBaby = Arrays.asList(new Product(12345678, "Java EE", categoryBooks, 120.00),
				new Product(00001000, "Calzini", categoryBaby, 15.00),
				new Product(12345678, "Java SE", categoryBooks, 99.00),
				new Product(12345678, "Camicia", categoryBaby, 50.00));
		List<Product> productOrderBoys = Arrays.asList(new Product(12345678, "Camicia", categoryBoys, 120.00),
				new Product(00001000, "Calzini", categoryBaby, 15.00),
				new Product(12345678, "Scarpe", categoryBoys, 99.00),
				new Product(12345678, "Camicia", categoryBaby, 50.00));
		List<Customer> customerRepo = Arrays.asList(new Customer(1, "Mario Rossi", 2),
				new Customer(2, "Giulio Rossi", 1), new Customer(3, "Fabio Rossi", 1));
		Customer customer1 = new Customer(4, "Renato Rossi", 2);
		Customer customer2 = new Customer(5, "Francesco Rossi", 2);

		List<Order> orderRepo = Arrays.asList(new Order(1, "Evaso", randomDate, randomDate, productOrderBaby, customer1),
				new Order(2, "Pending", randomDate, randomDate, productRepo, customer2),
				new Order(3, "Pending", start, end, productRepo, customer2));

		printProductByCategory(productRepo, categoryBooks, 100);
		printOrderByProductCategory(orderRepo, categoryBaby);
		printProductCategoryWithDiscount(productOrderBoys, categoryBoys);
		printOrderByTierAndDate(orderRepo);

	}

	public static void printProductByCategory(List<Product> products, String category, double price) {

		List<Product> listProducts = products.stream().filter(p -> p.getCategory().equalsIgnoreCase(category))
				.filter(p -> p.getPrice() > price).collect(Collectors.toList());
		System.out.println("Lista dei prodotti:");
		for (Product product : listProducts) {
			System.out.println("ID: " + product.getId());
			System.out.println("Nome: " + product.getName());
			System.out.println("Categoria: " + product.getCategory());
			System.out.println("Prezzo: " + product.getPrice());
		}

	}

	private static void printOrderByProductCategory(List<Order> orders, String category) {

		List<Order> filteredOrder = orders.stream()
				.filter(t -> t.getProducts().stream().anyMatch(p -> p.getCategory().equals(category)))
				.distinct() 
				.collect(Collectors.toList());

		System.out.println("Lista degli Ordini che hanno dei prodotti appartenenti alla categoria Baby:");
		for (Order order : filteredOrder) {
			System.out.println("ID: " + order.getId());
			System.out.println("Stato: " + order.getStatus());
			System.out.println("Prodotti: " + order.getProducts());
			System.out.println("Cliente: " + order.getCustomer());
			System.out.println("Data ordine: " + order.getOrderDate());
			System.out.println("Data spedizione: " + order.getDeliveryDate());
		}

	}

	private static void printProductCategoryWithDiscount(List<Product> products, String category) {

		List<Product> filteredProduct = products.stream().filter(t -> t.getCategory().equals(category))
				.map(CatalogoProdotti::discountPrice).collect(Collectors.toList());

		System.out.println("Lista dei prodotti che appartengono alla categoria Boys con sconto 10%:");
		for (Product product : filteredProduct) {
			System.out.println("ID: " + product.getId());
			System.out.println("Nome: " + product.getName());
			System.out.println("Categoria: " + product.getCategory());
			System.out.println("Prezzo: " + product.getPrice());
		}

	}
	
	public static void printOrderByTierAndDate(List<Order> orders) {

		List<Order> listOrders  = orders.stream()
				.filter(o -> o.getCustomer().getTier() == 2)
				.filter(o -> o.getOrderDate().compareTo(CatalogoProdotti.start) >= 0)
				.filter(o -> o.getOrderDate().compareTo(CatalogoProdotti.end) <= 0)
				.distinct()
				.collect(Collectors.toList());
		System.out.println("Lista ordini effettuti dai Clienti di livello 2 e specifica data:");
		for (Order order : listOrders) {
			System.out.println("ID: " + order.getId());
			System.out.println("Stato: " + order.getStatus());
			System.out.println("Prodotti: " + order.getProducts());
			System.out.println("Cliente: " + order.getCustomer());
			System.out.println("Data ordine: " + order.getOrderDate());
			System.out.println("Data spedizione: " + order.getDeliveryDate());
		}

	}

	private static Product discountPrice(Product product) {
		product.setPrice(product.getPrice() - ((product.getPrice() * 10) / 100));
		return product;
	}

}
