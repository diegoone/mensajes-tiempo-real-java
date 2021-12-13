package aplicacion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.websocket.EncodeException;
import javax.websocket.Session;

import comunicacion.MensajeGenerico;

public class GrupoWebsocket {
	private Date fechaCreacion;
	private String idGrupo;
	private String nombre;
	private List<Session> sesiones;
	public GrupoWebsocket(String idGrupo, String nombre) {
		this.idGrupo = idGrupo;
		this.nombre = nombre;
		this.fechaCreacion = new Date();
		sesiones = new ArrayList<Session>();
	}
	
	public GrupoWebsocket(String idGrupo, String nombre, List<Session> sesiones) {
		this.idGrupo = idGrupo;
		this.nombre = nombre;
		if(sesiones!=null) {
			this.sesiones = sesiones; 
		} else {
			sesiones = new ArrayList<Session>();
		}
		this.fechaCreacion = new Date();
	}
	public boolean agregar(Session sesion) {
		if(sesiones.contains(sesion)) {
			return false;
		}
		sesiones.add(sesion);
		return true;
	}
	public boolean quitar(Session sesion) {
		return sesiones.remove(sesion);
	}
	public boolean quitarPorIdUsuario(String idUsuario) {
		int borrados = 0;
		List<Session> encontrados = buscarPorIdUsuario(idUsuario);
		for (Session sesion : encontrados) {
			if(sesiones.remove(sesion))
				borrados++;
		}
		return borrados > 0 && borrados == encontrados.size();
	}
	public List<Session> buscarPorIdUsuario(String idUsuario) {
		List<Session> encontrados = new ArrayList<Session>();
		Map<String, Object> properties = null;
		for (Session sesion : sesiones) {
			properties = sesion.getUserProperties();
			String idUsuarioActual = (String) properties.get("idUsuario");
			if(idUsuarioActual != null &&  idUsuario.equals(idUsuarioActual)) {
				encontrados.add(sesion);
			}
		}
		return encontrados;
	}
	public boolean estaEnGrupo(String idUsuario) {
		return buscarPorIdUsuario(idUsuario).size() > 0;
	}
	public void enviarATodos(MensajeGrupal mensaje) throws IOException, EncodeException {
		MensajeGenerico mensajeRespuesta = new MensajeGenerico();
		mensajeRespuesta.tipo = "mensaje-grupal";
		mensajeRespuesta.contenido = mensaje;
		for (Session sesion : sesiones) {
			sesion.getBasicRemote().sendObject(mensajeRespuesta);
		}
	}
	public String getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}
	public int size() {
		return this.sesiones.size();
	}
}
