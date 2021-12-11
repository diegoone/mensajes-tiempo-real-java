package aplicacion;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

import varios.CadenaAleatoria;

public class AdministradorGrupos {
	private List<GrupoWebsocket> listaGrupos;
	public AdministradorGrupos() {
		listaGrupos = new ArrayList<GrupoWebsocket>();
	}
	public GrupoWebsocket crearGrupo(String nombreGrupo) {
		String idGrupo = CadenaAleatoria.generar(5);
		GrupoWebsocket nuevoGrupo = new GrupoWebsocket(idGrupo, nombreGrupo);
		for (GrupoWebsocket grupo : listaGrupos) {
			if(grupo.getNombre().equals(nombreGrupo)) {
				return null;
			}
		}
		listaGrupos.add(nuevoGrupo);
		return nuevoGrupo;
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
}
