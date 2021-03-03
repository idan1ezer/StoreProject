package command;

import model.Store;

public class AddProductCommand {
	private Store store;
	
	public AddProductCommand(Store store) {
		this.store = store;
	}
	
	public void execute(String name, String catalogNumber, int price, int priceOfSale, String customerName,
			String phoneNumber, boolean notifications) throws Exception {
		store.addProductToStore(name, catalogNumber, price, priceOfSale, customerName, phoneNumber, notifications);
	}
}

