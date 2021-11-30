const URL_BASE = "ws://localhost:8080/mywebsocket";
let socket = new WebSocket(URL_BASE + "/mensaje-grupal");
const divMensajes = document.getElementById("sala-msj");
const formMensaje = document.getElementById('form-mensaje');
const elemMensaje = document.getElementById('mensaje');
formMensaje.addEventListener('submit', function (event){
	event.preventDefault();
	const mensaje = {
		tipo: "mensaje-grupal", 
		contenido: {
			fechaCreacion: null, 
			contenido: elemMensaje.value  
		}
	};
	socket.send(JSON.stringify(mensaje));
	elemMensaje.value="";
});
const elemNombreUsuario = document.getElementById('nombre-usuario');
const formNombreUsuario = document.getElementById('form-nombre-usuario');
formNombreUsuario.addEventListener('submit', function (event) {
	event.preventDefault();
	const mensaje = {
		tipo: "sesion", 
		contenido: {
			nombreUsuario: elemNombreUsuario.value 
		}
	};
	socket.send(JSON.stringify(mensaje));
});
socket.onopen = function(e) {
  console.log("[open] Connection established");
};

socket.onmessage = function(event) {
  const nuevoMsj = document.createElement('div');
  const mensajeRespuesta = JSON.parse(event.data);
  if(mensajeRespuesta.tipo === 'sesion') {
	//...
  } else if(mensajeRespuesta.tipo === 'mensaje-grupal') {
	console.log(mensajeRespuesta.contenido);
	nuevoMsj.innerHTML = "<div>" + mensajeRespuesta.contenido.idUsuario 
	+ "<br> "+mensajeRespuesta.contenido.contenido
	+ "<br>"+mensajeRespuesta.contenido.fechaCreacion+"<div>";
  }
  divMensajes.appendChild(nuevoMsj);
};

socket.onclose = function(event) {
  if (event.wasClean) {
    console.log('`[close] Connection closed cleanly, code=${event.code} reason=${event.reason}');
  } else {
    // e.g. server process killed or network down
    // event.code is usually 1006 in this case
    console.log('[close] Connection died');
  }
};

socket.onerror = function(error) {
	console.log('[error] ${error.message}');
};
obj = {
	tipo: "establecer",
	propiedades: {
		id: "", 
		nombre:"",
	}
};
obj = {
	tipo: "mensaje-privado",
	mensaje: { 
		contenido:"",
	}
};
obj = {
	tipo: "mensaje-grupal",
	mensaje: {
		contenido: ""
	}
};