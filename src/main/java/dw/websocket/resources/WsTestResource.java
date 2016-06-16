package dw.websocket.resources;

import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;

import dw.websocket.core.MessageManagerMock;

@Metered
@Timed
@ExceptionMetered
@ServerEndpoint(value="/ws-test/{tenant}", encoders={JsonEncoder.class})
public class WsTestResource {
	Logger log = LoggerFactory.getLogger(getClass());
	
	public WsTestResource() {
	}

	@OnOpen
	public void myOnOpen(final Session session, @PathParam("tenant") String tenant) throws IOException {
		log.debug("open on tenant: " + tenant);
		if(tenant == null) {
			throw new RuntimeException("Must provide a tenant");
		}
		Session existingSession = MessageManagerMock.wsSessionByTenant.get(tenant);
		if(existingSession != null && existingSession.isOpen()) {
			throw new RuntimeException("WS already opened for tenant " + tenant);
		}
		MessageManagerMock.wsSessionByTenant.put(tenant, session);
	}

	@OnMessage
	public void myOnMsg(@PathParam("tenant") String tenant, final Session session, String message) throws InterruptedException {
		log.debug("message on tenant {} - {}", tenant, message);
		Thread.sleep(10);
		//Should not happen
		session.getAsyncRemote().sendText("{ \"mess\": \"" + message.toUpperCase() + "\" }");
	}

	@OnClose
	public void myOnClose(@PathParam("tenant") String tenant, final Session session, CloseReason cr) {
		log.debug("CLose on tenant {} - {}", tenant, cr);
		if(tenant != null) {
			MessageManagerMock.wsSessionByTenant.remove(tenant);
		}
	}
	
}
