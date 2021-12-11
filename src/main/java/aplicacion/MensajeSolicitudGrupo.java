package aplicacion;

import java.util.Date;

import javax.management.InvalidApplicationException;

public class MensajeSolicitudGrupo {
	public Date fecha;
	public String idUsuario;
	public String idGrupo;
	private String motivo;
	public void setMotivo(String motivo) {
		if("unir".equals(motivo) || "salir".equals(motivo)) {
			this.motivo = motivo;  
		} else {
			throw new IllegalArgumentException("motivo invalido");
		}
	}
	public boolean isUnir() {
		return "unir".equals(this.motivo);
	}
	public boolean isSalir() {
		return "salir".equals(this.motivo);
	}
	public String getMotivo() {
		return this.motivo;
	}
}
