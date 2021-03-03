package command;

import model.Store;

public class SendMessageCommand {
	private Store store;
	
	public SendMessageCommand(Store store) {
		this.store = store;
	}
	
	public String execute(String saleMsg) throws Exception{
		return store.sendMsgToCustomers(saleMsg);
	}
}
