package aplicacion;

public class MensajeSesion {
	private String nombreUsuario;
	private String idUsuario;
	public MensajeSesion() {
	}
	
	public MensajeSesion(String nombreUsuario, String idUsuario) {
		this.nombreUsuario = nombreUsuario;
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
}
