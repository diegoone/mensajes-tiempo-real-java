import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
public class EndpointSesion extends Endpoint {
	@Override
	public void onOpen(final Session session, EndpointConfig config) {
		session.addMessageHandler(new MessageHandler.Whole<String>() {
			@Override
			public void onMessage(String msg) {
				try {
					msg = msg.trim();
					msg = msg.replace(' ','\0') + Math.random();
					Map<String, Object> estado = session.getUserProperties();
					estado.put("nombreUsuario", msg);
					session.getBasicRemote();
					session.getBasicRemote().sendText(msg);
				} catch( IOException e) {
					
				}
			}
		});
	}
}
