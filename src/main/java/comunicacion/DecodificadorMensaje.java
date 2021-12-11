package comunicacion;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import aplicacion.Mensaje;
import aplicacion.MensajeGrupal;
import aplicacion.MensajeSesion;
import aplicacion.MensajeSolicitudGrupo;

public class DecodificadorMensaje implements Decoder.Text<MensajeGenerico> {
	@Override
	public void init(EndpointConfig ec) {
	}

	@Override
	public void destroy() {
	}

	@Override
	public MensajeGenerico decode(String text) throws DecodeException {
		MensajeGenerico mensajeGenerico = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			mensajeGenerico = mapper.readValue(text, MensajeGenerico.class);
			if(mensajeGenerico.isSesion() || mensajeGenerico.isEstablecer()) {
				MensajeSesion mensaje = mapper.convertValue(mensajeGenerico.contenido, MensajeSesion.class);
				mensajeGenerico.contenido = mensaje;
			} else if(mensajeGenerico.isGrupal()) {
				MensajeGrupal mensaje = mapper.convertValue(mensajeGenerico.contenido, MensajeGrupal.class);
				mensajeGenerico.contenido = mensaje;
			} else if(mensajeGenerico.isPrivado()) {
				Mensaje mensaje = mapper.convertValue(mensajeGenerico.contenido, Mensaje.class);
				mensajeGenerico.contenido = mensaje;
			} else if(mensajeGenerico.isSolicitudGrupo()) {
				MensajeSolicitudGrupo solicitudGrupo = mapper.convertValue(mensajeGenerico.contenido, MensajeSolicitudGrupo.class);
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mensajeGenerico;
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
