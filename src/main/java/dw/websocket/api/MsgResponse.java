package dw.websocket.api;

public class MsgResponse {

	public MsgResponse() {
	}

	private String id;
	private String message;
	
	public String getId() {
		return id;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[").append(id).append(", ").append(message).append("]");
		return builder.toString();
	}
	
}
