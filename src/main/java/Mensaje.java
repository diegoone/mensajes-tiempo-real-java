import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Mensaje {
	@JsonFormat
    (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	public Date fechaCreacion;
	public String contenido;
	public String idUsuario;
}
