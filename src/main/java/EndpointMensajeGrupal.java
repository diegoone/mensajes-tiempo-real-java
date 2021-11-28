import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;

@ServerEndpoint(
	value = "/mensaje-grupal",
	encoders = { CodificadorMensaje.class },
	decoders = { DecodificadorMensaje.class }
)
public class EndpointMensajeGrupal {
	@OnMessage
	public void OnMessage(Session sesion, Mensaje mensaje) {
		try {
			for (Session ses : sesion.getOpenSessions()) {
				if(ses.isOpen()) {
					try {
						ses.getBasicRemote().sendObject(mensaje);
					} catch (EncodeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch( IOException e) {
			
		}
	}
}
