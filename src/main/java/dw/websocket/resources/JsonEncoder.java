package dw.websocket.resources;

import static dw.websocket.WebSocketApp.objectMapper;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonEncoder implements Encoder, Encoder.Text<Object> {

	@Override
	public void init(EndpointConfig config) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public String encode(Object object) throws EncodeException {
		try {
			return objectMapper.writer().writeValueAsString(object);
		}
		catch (JsonProcessingException e) {
			throw new EncodeException(object, "Can't encode in Json", e);
		}
//		return object.toString();
	}

}