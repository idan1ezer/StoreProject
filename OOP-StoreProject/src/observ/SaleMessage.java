package observ;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;

public class SaleMessage extends Observable{

//	private static SaleMsg[] _instance = new SaleMsg[1];
	
	private String msg;
	private LocalDateTime sent;
	
	public SaleMessage(String msg) {
		this.msg = msg;
		this.sent = LocalDateTime.now();	
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setSent(LocalDateTime sent) {
		this.sent = sent;
	}

	public LocalDateTime getSent() {
		return sent;
	}

	public String getTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return sent.format(formatter);
	}

	@Override
	public String toString() {
		return String.format("Date: %s\nMessage: %s\n", sent, msg);
	}
	
//	public static SaleMsg getInstace(int i) {
//		if(_instance[i]==null)
//			_instance[i] = new SaleMsg(msg);
//		return _instance[i];
//
//	}
	

}

