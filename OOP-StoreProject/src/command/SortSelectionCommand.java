package command;

import model.Store;

public class SortSelectionCommand {
	private Store store;

	public SortSelectionCommand(Store store) {
		this.store = store;
	}

	public void execute(String type) throws Exception {
		store.createSortedProducts(type);
	}
}
