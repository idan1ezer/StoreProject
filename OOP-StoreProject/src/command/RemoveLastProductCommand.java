package command;

import model.Store;

public class RemoveLastProductCommand {
	private Store store;
	
	public RemoveLastProductCommand(Store store) {
		this.store = store;
	}
	
	public void execute() {
		store.removeLastProductAdded();
	}
}
