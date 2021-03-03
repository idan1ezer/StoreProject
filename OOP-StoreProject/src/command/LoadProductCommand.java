package command;

import model.Product;
import model.Store;

public class LoadProductCommand {
	private Store store;
	
	public LoadProductCommand(Store store) {
		this.store = store;
	}
	
	public Product execute(String catalogNumber) throws Exception {
		return store.getProduct(catalogNumber);
	}
}
