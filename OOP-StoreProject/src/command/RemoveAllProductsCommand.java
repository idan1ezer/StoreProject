package command;

import model.Store;

public class RemoveAllProductsCommand {
	private Store store;
	
	public RemoveAllProductsCommand(Store store) {
		this.store = store;
	}
	
	public void execute() throws Exception {
		store.removeAllProducts();
	}
}
