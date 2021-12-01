package endpoint;

import javax.naming.directory.InvalidSearchControlsException;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import aplicacion.GrupoWebsocket;
import aplicacion.Mensaje;
import aplicacion.MensajeSesion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import comunicacion.CodificadorMensaje;
import comunicacion.DecodificadorMensaje;
import comunicacion.MensajeGenerico;
import varios.CadenaAleatoria;
@ServerEndpoint(
	value = "/mensaje-grupal",
	encoders = { CodificadorMensaje.class },
	decoders = { DecodificadorMensaje.class }
)
public class EndpointMensajeGrupal {
	public List<GrupoWebsocket> listaGrupos;
	public EndpointMensajeGrupal() {
		listaGrupos = new ArrayList<GrupoWebsocket>();
	}
	@OnOpen
	public void OnOpen(Session sesion) {
		String idUsuario = CadenaAleatoria.generar(15);
		sesion.getUserProperties().put("idUsuario", idUsuario);
	}
	public boolean crearGrupo(String nombreGrupo) {
		String idGrupo = CadenaAleatoria.generar(5);
		GrupoWebsocket nuevoGrupo = new GrupoWebsocket(idGrupo, nombreGrupo);
		for (GrupoWebsocket grupo : listaGrupos) {
			if(grupo.getNombre().equals(nombreGrupo)) {
				return false;
			}
		}
		listaGrupos.add(nuevoGrupo);
		return true;
	}
	public boolean agregarSesion(String idGrupo, Session sesion) {
		GrupoWebsocket grupoEncontrado = buscarPorId(idGrupo);
		if(grupoEncontrado != null) {
			grupoEncontrado.agregar(sesion);
			return true;
		}
		return false;
	}
	public GrupoWebsocket buscarPorId(String idGrupo) {
		for (GrupoWebsocket grupo : listaGrupos) {
			if(grupo.getIdGrupo().equals(idGrupo))
				return grupo;
		}
		return null;
	}
	@OnMessage
	public void OnMessage(Session sesion, MensajeGenerico mensajeWebSocket) {
		
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
