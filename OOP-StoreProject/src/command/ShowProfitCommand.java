package command;

import java.util.TreeMap;

import model.Product;
import model.Store;

public class ShowProfitCommand {
	private Store store;
	
	public ShowProfitCommand(Store store) {
		this.store = store;
	}
	
	public TreeMap<String, Product> execute() throws Exception {
		return store.getProducts();
	}
}
