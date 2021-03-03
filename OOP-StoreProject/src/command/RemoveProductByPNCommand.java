package command;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Store;

public class RemoveProductByPNCommand {
	private Store store;
	
	public RemoveProductByPNCommand(Store store) {
		this.store = store;
	}
	
	public void execute(String productNum) throws Exception {
		store.removeBySN(productNum);
	}
}
