import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import java.io.IOException;

public class EndpointMensajeGrupal extends Endpoint {
	@Override
	public void onOpen(final Session session, EndpointConfig config) {
		session.addMessageHandler(new MessageHandler.Whole<String>() {
			@Override
			public void onMessage(String msg) {
				try {
					for (Session ses : session.getOpenSessions()) {
						if(ses.isOpen()) {
							ses.getBasicRemote().sendText(msg);
						}
					}
				} catch( IOException e) {
					
				}
			}
		});
	}
}
