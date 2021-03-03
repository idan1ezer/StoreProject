package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import observ.Receiver;
import observ.SaleMessage;
import observ.Sender;

public class Customer implements Observer, Serializable,Receiver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String phoneNumber;
	private Boolean notifications;
	
	public Customer(String name, String phoneNumber, Boolean notifications) {
		super();
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.notifications = notifications;
	}

	public Customer(RandomAccessFile raf) {
		try {
			this.name = raf.readUTF();
			this.phoneNumber = raf.readUTF();
			this.notifications = raf.readBoolean();
		} catch (FileNotFoundException e) {
			System.out.println("DeleteStrFromFileMethodException: File Not Found! " + e.getMessage());
		} catch (IOException e) {
			System.out.println("DeleteStrFromFileMethodException: Input Output Exception! " + e.getMessage());
		}		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	
	public Boolean getNotifications() {
		return notifications;
	}

	public void setNotifications(Boolean notifications) {
		this.notifications = notifications;
	}

	@Override
	public String toString() {
		return "Name: " + name + "\tPhone: " + phoneNumber + "\n";
	}

	@Override
	public void update(Observable obs, Object obj) {
		if(obs instanceof SaleMessage)
			System.out.println(name + " Hi!\nNews Updated!\n" + obs);
	}


	@Override
	public String receiveMessage(Sender s, SaleMessage saleMsg) {
		StringBuffer msg = new StringBuffer();
		msg.append("New Message Has Arrived ---> From: " + this.name + "\t" + this.phoneNumber +"\n");
		return msg.toString();
	}


	
	
	
	
	
}
