package comunicacion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MensajeGenerico {
	public Object contenido;
	public String tipo;
	public MensajeGenerico() {
		
	}
	public MensajeGenerico(Object contenido, String tipo) {
		this.contenido = contenido;
		this.tipo = tipo;
	}
	public Object getContenido() {
		return contenido;
	}
	public void setContenido(Object contenido) {
		this.contenido = contenido;
	}
	public String getTipo() {
		return tipo;
	}
	public boolean isPrivado() {
		return "mensaje-privado".equals(tipo);
	}
	public boolean isGrupal() {
		return "mensaje-grupal".equals(tipo);
	}
	public boolean isEstablecer() {
		return "establecer".equals(tipo);
	}
	public boolean isSesion() {
		return "sesion".equals(tipo);
	}
	public boolean isListaConectados() {
		return "lista-conectados".equals(tipo);
	}
	public boolean isCrearGrupo() {
		return "crear-grupo".equals(tipo);
	}
	public boolean isAgregarUsuarioAGrupo() {
		return "agregar-usuario-grupo".equals(tipo);
	}
	public boolean isQuitarUsuarioDeGrupo() {
		return "quitar-usuario-grupo".equals(tipo);
	}
	public boolean isSolicitudGrupo() {
		return "solicitud-grupo".equals(tipo); 
	}
	private boolean isTipoValido(String tipo) {
		List<String> tiposValidos = Arrays.asList(
			"mensaje-privado", "mensaje-grupal", 
			"establecer", "sesion", 
			"lista-conectados", "lista-grupos", 
			"crear-grupo", "agregar-usuario-grupo", "quitar-usuario-grupo", 
			"solicitud-grupo"
		);
		return tiposValidos.contains(tipo);
	}
	public void setTipo(String tipo) {
		if( isTipoValido(tipo) ) {
			this.tipo = tipo;
		}
		else throw new IllegalArgumentException("tipo invaludo");
	}
}
