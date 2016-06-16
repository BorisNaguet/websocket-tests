package dw.websocket.api;

public class Message {

	public Message() {
	}

	private String text;
	private String to;
	private String tenant;
	
	public String getText() {
		return text;
	}
	
	public String getTo() {
		return to;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	public String getTenant() {
		return tenant;
	}
	
	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[").append(text).append(", ").append(to).append(", ").append(tenant).append("]");
		return builder.toString();
	}
	
	
}
