package aplicacion;

public class MensajePeticionGrupal {
	public String idGrupo;
	public String idUsuarioSolicitante;
	public String idUsuarioObjetivo;
	public String nombreGrupo;
	private String solicitud;
	public void setSolicitud(String solicitud) {
		if("crear-grupo".equals(solicitud) 
			|| "unir-a".equals(solicitud) 
			|| "salir-de".equals(solicitud) 
			|| "eliminar-grupo".equals(solicitud)) {
			this.solicitud = solicitud;
		}
	}
	public String getSolicitud() {
		return solicitud;
	}
	public boolean isCrearGrupo() {
		return  "crear-grupo".equals(solicitud); 
	}
	public boolean isUnirA() {
		return  "unir-a".equals(solicitud); 
	}
	public boolean isSalirDe() {
		return "salir-de".equals(solicitud);  
	}
	public boolean iseliminarGrupo() {
		return "eliminar-grupo".equals(solicitud); 
	}
}
