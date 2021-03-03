package observ;

public interface Receiver {
	String receiveMessage(Sender s, SaleMessage msg);
}
