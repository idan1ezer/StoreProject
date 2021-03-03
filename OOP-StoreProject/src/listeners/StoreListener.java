package listeners;

import java.util.TreeMap;
import java.util.Vector;
import model.Product;


public interface StoreListener {
	
	void fireProductToUI(Product product);
	void fireAllProductsToUI(Vector<Product> products);
	void fireProfitToUI(TreeMap<String, Product> products);
	void fireMessagesToUI(StringBuffer messages);

}
