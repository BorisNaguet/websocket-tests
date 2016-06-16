package dw.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;

import dw.websocket.config.WebSocketAppConfig;
import dw.websocket.core.MessageManagerMock;
import dw.websocket.resources.RsTestResource;
import dw.websocket.resources.WsTestResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.websockets.WebsocketBundle;

public class WebSocketApp extends Application<WebSocketAppConfig> {

	private WebsocketBundle websocketBundle;
	//easy singleton
	public static final MessageManagerMock managerMock = new MessageManagerMock();
	public static ObjectMapper objectMapper;

	@Override
	public void initialize(Bootstrap<WebSocketAppConfig> bootstrap) {
		super.initialize(bootstrap);
		websocketBundle = new WebsocketBundle(new Class[0]);
		bootstrap.addBundle(websocketBundle);
		
		objectMapper = bootstrap.getObjectMapper(); 
	}
	
	@Override
	public void run(WebSocketAppConfig configuration, Environment environment) throws Exception {
		websocketBundle.addEndpoint(WsTestResource.class);
		environment.jersey().register(RsTestResource.class);
	}

	public static void main(String[] args) {
		try {
			new WebSocketApp().run(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
