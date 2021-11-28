import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
public class CodificadorMensaje implements Encoder.Text<Mensaje> {
	@Override
	public void init(EndpointConfig config) {}
	@Override
	public void destroy() {}
	@Override
	public String encode(Mensaje mensaje) throws EncodeException {
		ObjectMapper mapper = new ObjectMapper();
		String texto = null;
		try {
			texto = mapper.writeValueAsString(mensaje);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return texto;
	}
}
