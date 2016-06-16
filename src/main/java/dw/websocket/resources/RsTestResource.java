package dw.websocket.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dw.websocket.WebSocketApp;
import dw.websocket.api.Message;
import dw.websocket.api.MsgResponse;

@Path("/rs-test/{tenantid}")
@Consumes("application/json")
@Produces("application/json")
public class RsTestResource {
	Logger log = LoggerFactory.getLogger(getClass());
	
	public RsTestResource() {
	}

	@POST
	public MsgResponse sendMessage(@PathParam("tenantid") String tenantid, Message message) {
		message.setTenant(tenantid);
		log.debug("REST request to send " + message);
		return WebSocketApp.managerMock.sendMess(message);
	}
}
