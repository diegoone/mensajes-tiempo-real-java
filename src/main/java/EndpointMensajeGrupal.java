import javax.naming.directory.InvalidSearchControlsException;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@ServerEndpoint(
	value = "/mensaje-grupal",
	encoders = { CodificadorMensaje.class },
	decoders = { DecodificadorMensaje.class }
)
public class EndpointMensajeGrupal {
	public int num = 1;
	@OnOpen
	public void OnOpen(Session sesion) {
		String idUsuario = RandomString.generar(15);
		sesion.getUserProperties().put("idUsuario", idUsuario);
	}
	@OnMessage
	public void OnMessage(Session sesion, MensajeGenerico mensajeWebSocket) {
		num++;
		try {
			Map<String, Object> estado = sesion.getUserProperties();
			String nombreUsuario = (String) estado.get("nombreUsuario");
			String idUsuario = (String) estado.get("idUsuario");
			
			if(mensajeWebSocket.isSesion()) {
				MensajeSesion mSesion = (MensajeSesion) mensajeWebSocket.contenido;
				mSesion.setIdUsuario(idUsuario);
				if(nombreUsuario == null) {
					nombreUsuario = mSesion.getNombreUsuario().trim();
					mSesion.setNombreUsuario(nombreUsuario);
					estado.put("nombreUsuario", nombreUsuario);
				}
				mensajeWebSocket.contenido = mSesion;
				try {
					sesion.getBasicRemote().sendObject(mensajeWebSocket);
				} catch (EncodeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if(mensajeWebSocket.isGrupal()) {
				Mensaje mensajeGrupal = (Mensaje) mensajeWebSocket.contenido;
				mensajeGrupal.nombreUsuario = (String) estado.get("nombreUsuario");
				mensajeGrupal.idUsuario = idUsuario;
				mensajeGrupal.fechaCreacion = new Date();
				for (Session ses : sesion.getOpenSessions()) {
					if(ses.isOpen()) {
						try {
							ses.getBasicRemote().sendObject(mensajeWebSocket);
						} catch (EncodeException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		} catch( IOException e) {
			
		}
	}
}
