package memento;

import java.time.LocalDate;
import java.util.TreeMap;

import model.Product;
import model.Store;

public class StoreMemento {

	private TreeMap<String, Product> products;
	private LocalDate created;
	
	public StoreMemento(Store store) {

		this.products = new TreeMap<>(store.getProducts());
		this.created = LocalDate.now();
	}

	public TreeMap<String, Product> getProducts() {
		return products;
	}
	
	public LocalDate getCreated() {
		return created;
	}

	
	
}
