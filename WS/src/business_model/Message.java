package business_model;

public class Message {
	String msg;
	public Message(String msg) {
		this.msg = "{ \"message\": " + "\"" + msg + "\"}";
	}
	@Override
	public String toString() {
		return msg;
	}


}
