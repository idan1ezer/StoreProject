package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Vector;
import java.util.AbstractMap.SimpleEntry;

import compare.CompareByCatalogNumDESC;
import compare.CompareByInsertionOrder;
import listeners.StoreListener;
import memento.CareTaker;
import memento.StoreMemento;
import model.Store.fileIterator;
import observ.SaleMessage;
import observ.Sender;

public class Store implements Serializable, Sender, Iterable<Entry<String, Product>> {

	private static Vector<StoreListener> allListeners = new Vector<StoreListener>();
	private TreeMap<String, Product> products;
	private CareTaker ct = new CareTaker();
	private String compareType;
	private boolean isFileEmpty;

	private static final long serialVersionUID = 1L;
	public static final String F_NAME = "products.txt";
	private File binFile = new File(F_NAME);


	public Store() throws Exception {
		if(binFile.length() != 0)
			isFileEmpty = false;
		else
			isFileEmpty = true;

		if (!isFileEmpty)
		{
			load();
		}
			
	}

	public void setComparator(Object o) {
		setComparator(o);
	}

//	public Comparator<Product> getComparator() {
//		return comparator;
//	}

	public TreeMap<String, Product> createSortedProducts(String type) {
		
		switch (type) {
		case "Descending":
			products = new TreeMap<String, Product>(new CompareByCatalogNumDESC());
			setCompareType("Descending");
			break;
		case "Insertion":
			products = new TreeMap<String, Product>(new CompareByInsertionOrder());
			setCompareType("Insertion");
			break;
		default:
			products = new TreeMap<String, Product>();
			setCompareType("Ascending");
			break;
		}
		
		return products;
	}
	
	

	public void addProductToStore(String name, String catalogNumber, int price, int priceOfSale, String customerName,
			String phoneNumber, boolean notifications) throws Exception {

		ct.save(new StoreMemento(this));
		Customer temp = new Customer(customerName, phoneNumber, notifications);
		Product product = new Product(name, catalogNumber, price, priceOfSale, temp);

		products.put(catalogNumber, product);
		save(this.binFile);
	}
	
	public void addProductFromFileToStore(String name, String catalogNumber, int price, int priceOfSale, String customerName,
			String phoneNumber, boolean notifications) throws Exception {

		ct.save(new StoreMemento(this));
		Customer temp = new Customer(customerName, phoneNumber, notifications);
		Product product = new Product(name, catalogNumber, price, priceOfSale, temp);

		products.put(catalogNumber, product);
	}

	public Product getProduct(String catalogNumber) {

		if (products.containsKey(catalogNumber))
			return products.get(catalogNumber);
		else
			return null;
	}


	@Override
	public String sendMessage(SaleMessage saleMsg) {
		StringBuffer msg = new StringBuffer();
		for (Map.Entry<String, Product> entry : products.entrySet()) {
			if (entry.getValue().getCustomer().getNotifications())
				msg.append(entry.getValue().getCustomer().receiveMessage(this, saleMsg));
		}
		return msg.toString();
	}

	public String sendMsgToCustomers(String msg) {
		SaleMessage saleMsg = new SaleMessage(msg);
		return sendMessage(saleMsg);
	}

	public TreeMap<String, Product> getProducts() {
		//System.out.println(products);
		return products;
	}
	
	public Vector<Product> getProductsToVector() {
		Vector<Product> productsVector = new Vector<Product>();
		for (Map.Entry<String, Product> entry : products.entrySet())
			productsVector.add(entry.getValue());
		
		//System.out.println(productsVector);
		return productsVector;		
	}

	public StoreMemento save() {
		return new StoreMemento(this);
	}
	public void removeLastProductAdded() {
		load(ct.load());
		save(binFile);
		
	}

	public void load(StoreMemento sm) {
		products = sm.getProducts();
		save(binFile);
	}

	public void registerListeners(StoreListener newListener) {
		allListeners.add(newListener);
	}

	public String getCompareType() {
		return compareType;
	}

	public void setCompareType(String compareType) {
		this.compareType = compareType;
	}
	
	public File getFile() {
		return binFile;
	}
	
	public Iterator<Entry<String, Product>> iterator() {
		return new fileIterator(this.binFile);
	}

	public void save(File binfile) {
		try {
			RandomAccessFile raf = new RandomAccessFile(binfile, "rw");
			String sortType = getSortType();
			raf.setLength(0); 
			raf.writeUTF(sortType); 
			for (Map.Entry<String, Product> entry : products.entrySet()) {
				raf.writeUTF(entry.getValue().getNumber());								
				raf.writeUTF(entry.getValue().getName());
				raf.writeUTF(entry.getValue().getNumber());
				raf.writeUTF(entry.getValue().getPriceString());
				raf.writeUTF(entry.getValue().getPriceOfSaleString());
				raf.writeUTF(entry.getValue().getCustomer().getName()); 
				raf.writeUTF(entry.getValue().getCustomer().getPhoneNumber());
				raf.writeBoolean(entry.getValue().getCustomer().getNotifications());
			}
			raf.close();
		} catch (Exception e) {
		}
	}

	private String getSortType() {
		return this.compareType;
	}

	public void load() {
		TreeMap<String, Product> newProducts = new TreeMap<String, Product>();
		Entry<String, Product> entry;
		Iterator<Entry<String, Product>> fIterator = iterator();
		if (fIterator.hasNext()) {
			entry = fIterator.next();
			newProducts = this.createSortedProducts(entry.getKey());
		}
		while (fIterator.hasNext()) {
			entry = fIterator.next();
			newProducts.put(entry.getKey(), entry.getValue());
		}
		this.products = newProducts;
	}

	public void removeAllProducts() {

		Iterator<Entry<String, Product>> fIterator = iterator();
		if (fIterator.hasNext())
			fIterator.next();

		while (fIterator.hasNext()) {
			fIterator.next();
			fIterator.remove();
		}
		load();
	}

	public boolean removeBySN(String SN) {

		Entry<String, Product> entry;
		boolean ok = true;
		Iterator<Entry<String, Product>> fIterator = iterator();
		if (fIterator.hasNext())
			fIterator.next();

		while (fIterator.hasNext() && ok) {
			entry = fIterator.next();
			if (entry.getKey().equalsIgnoreCase(SN)) {
				fIterator.remove();
				ok = false;
			}
		}
		if(!ok) {
			load();
			return true;
		}
		return false;
	}
	
	public class fileIterator implements Iterator<Entry<String, Product>>  {

		private File binfile;
		private long pos = 0;
		private long last = -1;

		public fileIterator(File binfile) {
			this.binfile = binfile;
		}

		@Override
		public boolean hasNext() {
			try {
				RandomAccessFile raf = new RandomAccessFile(binfile, "rw");
				boolean hasNext = (raf.length() - pos) > 0;
				raf.close();
				return hasNext;
			} catch (IOException e) {
				return false;
			}
		}

		@Override
		public Entry<String, Product> next() {
			try {
				RandomAccessFile raf = new RandomAccessFile(binfile, "rw");
				Entry<String, Product> res = null;
				if (pos == 0) {
					res = new SimpleEntry<>(raf.readUTF(), null);
					last = pos;
					pos = raf.getFilePointer();	
				} else {	
					raf.seek(pos);	
					res = new SimpleEntry<>(raf.readUTF(), new Product(raf.readUTF(), raf.readUTF(), raf.readUTF(),
							raf.readUTF(), raf.readUTF(), raf.readUTF(), raf.readBoolean()));
					last = pos;
					pos = raf.getFilePointer();
				}
				raf.close();
				return res;
			} catch (Exception e) {
				return null;
			}
		}

		public void remove() {
			if (last == -1)
				throw new IllegalStateException();
			try {
				RandomAccessFile raf = new RandomAccessFile(binfile, "rw");
				byte[] data = new byte[(int) (raf.length() - pos)];
				raf.seek(pos);
				raf.read(data);
				raf.setLength(last);
				raf.seek(last);
				raf.write(data);
				raf.close();
				pos = last;
				last = -1;
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (IllegalStateException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	

	}

	


}


	
	
	
	


