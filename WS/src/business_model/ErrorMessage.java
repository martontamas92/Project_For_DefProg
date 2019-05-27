package business_model;

public class ErrorMessage {
	String msg;
	public ErrorMessage(String msg) {
		this.msg = "{\"message\": " + "\"" + msg + "\"}"; 
	}
}
