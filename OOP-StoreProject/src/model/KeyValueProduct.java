package model;

import java.util.Map.Entry;

public class KeyValueProduct implements Entry<String, Product>{
	private String catNum;
	private Product prod;
	
	public KeyValueProduct(String catNum, Product prod) {
		this.catNum = catNum;
		this.prod = prod;
	}
	
	public KeyValueProduct(KeyValueProduct other) {
		this.catNum = other.getKey();
		this.prod = other.getValue();
	}

	@Override
	public String getKey() {
		return this.catNum;
	}

	@Override
	public Product getValue() {
		return this.prod;
	}

	@Override
	public Product setValue(Product value) {
		return this.prod = value;
	}

}
