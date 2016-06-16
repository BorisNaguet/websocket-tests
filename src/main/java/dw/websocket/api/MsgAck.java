package dw.websocket.api;

public class MsgAck {

	private String tenant;
	private String id;
	private String status;
	
	public MsgAck() {
	}

	public MsgAck(String tenant, String id, String status) {
		super();
		this.tenant = tenant;
		this.id = id;
		this.status = status;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[").append(tenant).append(", ").append(id).append(", ").append(status).append("]");
		return builder.toString();
	}
	
	
	
}
