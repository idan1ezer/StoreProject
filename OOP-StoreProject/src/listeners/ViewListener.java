package listeners;

public interface ViewListener {
	
	public void selectionSortToModel(String type);

	public void addProductToModel(String name, String catalogNumber, int price, 
			int priceOfSale, String customerName, String phoneNumber, boolean notifications);
	
	public void loadProductToModel(String catalogNumber);
	
	public void showProductsToModel();
	
	public void removeLastProductAddedToModel();
	
	public void removeProductByPN(String productNum) throws Exception;
	
	public void removeAllProductsToModel();
	
	public void showProfitToModel();
	
	public void sendMessageToModel(String saleMessage);

}
