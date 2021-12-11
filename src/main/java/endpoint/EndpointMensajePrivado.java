package endpoint;
import javax.websocket.Endpoint;

import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Map;
@ServerEndpoint("/establecer-nombre")
public class EndpointMensajePrivado {
	@OnMessage
	public void OnMessage(Session sesion, String msj) {
		try {
			msj = msj.trim();
			msj = msj.replace(' ','\0') + Math.random();
			Map<String, Object> estado = sesion.getUserProperties();
			estado.put("nombreUsuario", msj);
			sesion.getBasicRemote();
			sesion.getBasicRemote().sendText(msj);
		} catch( IOException e) {
			
		}
	}
}
