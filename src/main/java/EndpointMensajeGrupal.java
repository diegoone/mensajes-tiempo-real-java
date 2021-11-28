import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;

@ServerEndpoint("/mensaje-grupal")
public class EndpointMensajeGrupal {
	@OnMessage
	public void OnMessage(Session sesion, String msj) {
		try {
			for (Session ses : sesion.getOpenSessions()) {
				if(ses.isOpen()) {
					ses.getBasicRemote().sendText(msj);
				}
			}
		} catch( IOException e) {
			
		}
	}
}
