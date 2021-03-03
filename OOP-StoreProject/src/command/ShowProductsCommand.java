package command;

import java.util.Vector;

import model.Product;
import model.Store;

public class ShowProductsCommand {
	private Store store;
	
	public ShowProductsCommand(Store store) {
		this.store = store;
	}
	
	public Vector<Product> execute() throws Exception {
		return store.getProductsToVector();
	}
}
