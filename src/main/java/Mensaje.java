
public class Mensaje {
	public String contenido;
	public String tipo;
	
	public Mensaje(String contenido, String tipo) {
		super();
		this.contenido = contenido;
		this.tipo = tipo;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
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
		return "establecer-id".equals(tipo);
	}
	private boolean isTipoValido(String tipo) {
		return "mensaje-privado".equals(tipo) || "mensaje-grupal".equals(tipo) || "establecer-id".equals(tipo);   
	}
	public void setTipo(String tipo) {
		if( isTipoValido(tipo) ) {
			this.tipo = tipo;
		}
		else throw new IllegalArgumentException("tipo invaludo");
	}
}
