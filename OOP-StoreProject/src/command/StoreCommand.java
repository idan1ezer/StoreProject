package command;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TreeMap;
import java.util.Vector;

import model.Product;
import model.Store;

public class StoreCommand implements Command {
	private Store store;
	private SortSelectionCommand sortSelection;
	private AddProductCommand addProduct;
	private LoadProductCommand loadProduct;
	private ShowProductsCommand showProducts;
	private RemoveLastProductCommand removeLast;
	private RemoveProductByPNCommand removeByPN;
	private RemoveAllProductsCommand removeAll;
	private ShowProfitCommand showProfit;
	private SendMessageCommand sendMessage;
	
	public StoreCommand(Store store)
	{
		this.store = store;
		this.sortSelection = new SortSelectionCommand(store);
		this.addProduct = new AddProductCommand(store);
		this.loadProduct = new LoadProductCommand(store);
		this.showProducts = new ShowProductsCommand(store);
		this.removeLast = new RemoveLastProductCommand(store);
		this.removeByPN = new RemoveProductByPNCommand(store);
		this.removeAll = new RemoveAllProductsCommand(store);
		this.showProfit = new ShowProfitCommand(store);
		this.sendMessage = new SendMessageCommand(store);
	}
	
	@Override
	public void selectSortToModel(String type) throws Exception {
		sortSelection.execute(type);
	}

	@Override
	public void addProductToModel(String name, String catalogNumber, int price, int priceOfSale, String customerName,
			String phoneNumber, boolean notifications) throws Exception {
		addProduct.execute(name, catalogNumber, price, priceOfSale, customerName, phoneNumber, notifications);
	}

	@Override
	public Product loadProductToModel(String catalogNumber) throws Exception {
		return loadProduct.execute(catalogNumber);
	}
	
//	@Override
//	public TreeMap<String, Product> showProductsToModel() throws Exception {
//		return showProducts.execute();
//	}
	
	@Override
	public Vector<Product> showProductsToModel() throws Exception {
		return showProducts.execute();
	}
	
	@Override
	public void removeLastProductAdded() {
		removeLast.execute();
	}
	
	@Override
	public void removeProductByPN(String productNum) throws Exception {
		removeByPN.execute(productNum);
	}

	
	@Override
	public void deleteAllProducts() {
		try {
			removeAll.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public TreeMap<String, Product> showProfitToModel() throws Exception{
		return showProfit.execute();
	}
	
	@Override
	public String sendMessageToModel(String saleMsg) throws Exception {
		return sendMessage.execute(saleMsg);
	}
	
	public Store getStore() {
		return store;
	}












	
}
