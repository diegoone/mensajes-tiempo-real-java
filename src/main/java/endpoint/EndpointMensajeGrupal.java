package endpoint;

import javax.naming.directory.InvalidSearchControlsException;
import javax.websocket.EncodeException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.OnClose;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import aplicacion.AdministradorGrupos;
import aplicacion.GrupoWebsocket;
import aplicacion.Mensaje;
import aplicacion.MensajeGrupal;
import aplicacion.MensajePeticionGrupal;
import aplicacion.MensajeSesion;
import aplicacion.MensajePeticionGrupal;

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
	private AdministradorGrupos adminGrupos;
	public EndpointMensajeGrupal() {
		adminGrupos = new AdministradorGrupos();
	}
	@OnOpen
	public void OnOpen(Session sesion, EndpointConfig c) {
		String idUsuario = CadenaAleatoria.generar(15);
		sesion.getUserProperties().put("idUsuario", idUsuario);
	}
	@OnClose
	public void OnClose(Session sesion) {
		adminGrupos.quitarSesionDeTodoGrupo(sesion);
	}
	@OnMessage
	public void OnMessage(Session sesion, MensajeGenerico mensajeWebSocket) {
		
		try {
			
			if(mensajeWebSocket.isSesion()) {
				MensajeSesion msjSesion = (MensajeSesion) mensajeWebSocket.contenido;
				procesarMensajeSesion(sesion, mensajeWebSocket ,msjSesion);
			} 
			else if(mensajeWebSocket.isSolicitudGrupo()) {
				procesarSolicitudGrupo(sesion, mensajeWebSocket);
			}
			else if(mensajeWebSocket.isGrupal()) {
				MensajeGrupal mensajeGrupal = (MensajeGrupal) mensajeWebSocket.contenido;
				procesarMensajeGrupal(sesion, mensajeWebSocket, mensajeGrupal);
			}
		} catch( IOException e) {
			
		} catch (EncodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void procesarMensajeSesion(Session sesion,MensajeGenerico mensajeWebSocket ,MensajeSesion msjSesion) throws IOException {
		Map<String, Object> estado = sesion.getUserProperties();
		String nombreUsuario = (String) estado.get("nombreUsuario");
		String idUsuario = (String) estado.get("idUsuario");
		
		msjSesion.setIdUsuario(idUsuario);
		if(nombreUsuario == null) {
			nombreUsuario = msjSesion.getNombreUsuario().trim();
			msjSesion.setNombreUsuario(nombreUsuario);
			estado.put("nombreUsuario", nombreUsuario);
		}
		mensajeWebSocket.contenido = msjSesion;
		try {
			sesion.getBasicRemote().sendObject(mensajeWebSocket);
		} catch (EncodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void procesarMensajeGrupal(Session sesion,MensajeGenerico mensajeWebSocket, MensajeGrupal mensajeGrupal) throws IOException, EncodeException {
		Map<String, Object> estado = sesion.getUserProperties();
		String nombreUsuario = (String) estado.get("nombreUsuario");
		String idUsuario = (String) estado.get("idUsuario");
		GrupoWebsocket grupo = adminGrupos.buscarPorId(mensajeGrupal.idGrupo);
		if (grupo == null) {
			return;
		}
		boolean estaEnGrupo = grupo.estaEnGrupo(idUsuario);
		if(!estaEnGrupo) {
			return;
		}
		mensajeGrupal.nombreUsuario = (String) estado.get("nombreUsuario");
		mensajeGrupal.idUsuario = idUsuario;
		mensajeGrupal.fechaCreacion = new Date();
		try {
			grupo.enviarATodos(mensajeGrupal);
		} catch (IOException | EncodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void procesarMensajePrivado() {
		
	}

	public void procesarSolicitudGrupo(Session sesion, MensajeGenerico mensajeWebSocket) throws IOException, EncodeException {
		MensajePeticionGrupal peticionGrupo = (MensajePeticionGrupal) mensajeWebSocket.contenido;
		if(peticionGrupo.isCrearGrupo()) {
			String nombreGrupo = peticionGrupo.nombreGrupo;
			GrupoWebsocket grupoCreado = adminGrupos.crearGrupo(nombreGrupo);
			if (grupoCreado == null) {

			} else {
				// el grupo fu?? creado, entonces agregar al usuario actual al grupo
				grupoCreado.agregar(sesion);
				peticionGrupo.idGrupo = grupoCreado.getIdGrupo();
				mensajeWebSocket.contenido = peticionGrupo;
				sesion.getBasicRemote().sendObject(mensajeWebSocket);
				MensajeGrupal primerMensaje = new MensajeGrupal();
				primerMensaje.fechaCreacion = grupoCreado.getFechaCreacion();
				primerMensaje.idGrupo = grupoCreado.getIdGrupo();
				primerMensaje.contenido = "Se cre?? el grupo";
				grupoCreado.enviarATodos(primerMensaje);
			}
		}
		else if(peticionGrupo.isUnirA()) {
			GrupoWebsocket grupoEncontrado = adminGrupos.buscarPorId(peticionGrupo.idGrupo);
			if(grupoEncontrado== null) {
				return;
			}
			grupoEncontrado.agregar(sesion);
		} else if(peticionGrupo.isSalirDe()) {
			GrupoWebsocket grupoEncontrado = adminGrupos.buscarPorId(peticionGrupo.idGrupo);
			if(grupoEncontrado== null) {
				return;
			}
			grupoEncontrado.quitar(sesion);
		}
	}
}
