import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DecodificadorMensaje implements Decoder.Text<Mensaje> {
	@Override
	public void init(EndpointConfig ec) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public Mensaje decode(String text) throws DecodeException {
		Mensaje mensaje = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			mensaje = mapper.readValue(text, Mensaje.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mensaje;
//	      // Read message...
//	      if ( /* message is an A message */ )
//	         return new MessageA(...);
//	      else if ( /* message is a B message */ )
//	         return new MessageB(...);
	}

	@Override
	public boolean willDecode(String string) {
		// Determine if the message can be converted into either a
		// MessageA object or a MessageB object...
		return true;
	}
}
