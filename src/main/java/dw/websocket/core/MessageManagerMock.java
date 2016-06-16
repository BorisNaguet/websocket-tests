package dw.websocket.core;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dw.websocket.api.Message;
import dw.websocket.api.MsgAck;
import dw.websocket.api.MsgResponse;

public class MessageManagerMock {
	Logger log = LoggerFactory.getLogger(getClass());
	
	public static final Map<String, Session> wsSessionByTenant = new ConcurrentHashMap<>();

	private final Random random = new Random();
	private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(200);

	
	public MessageManagerMock() {
	}

	public MsgResponse sendMess(Message message) {
		String messId = UUID.randomUUID().toString();
		int schedule = random.nextInt(1) + 1;
		
		executor.schedule(new Runnable() {
			@Override
			public void run() {
				log.debug("Run - messId: {} - message: {}" + message, messId, message);
				
				Session session = wsSessionByTenant.get(message.getTenant());
				if(session != null) {
					if(session.isOpen()) {
						try {
							session.getBasicRemote().sendObject(new MsgAck(message.getTenant(), messId, "OK"));
							log.debug("message sent - id: {} - {}", messId, message);
						}
						catch (Exception e) {
							log.error("Error on sending messageAck with id {} back on websocket {}", messId, message, e);
						}
					}
					else {
						log.warn("Session referenced for tenant but it's closed - messId: {} - message: {}", messId, message);
					}
				}
				else {
					log.warn("No session for tenant - only {} - messId: {} - message: {}" , wsSessionByTenant.keySet(), messId, message);
				}
			}
		}, schedule, TimeUnit.SECONDS);
		
		log.debug("Schedule message {} with id {} in {} seconds", message, messId, schedule);
		
		MsgResponse msgResponse = new MsgResponse();
		msgResponse.setId(messId);
		msgResponse.setMessage("Stored For futur sending");
		
		return msgResponse;
	}
}
