const URL_BASE = "ws://localhost:8080/mywebsocket";
let socket = new WebSocket(URL_BASE + "/mensaje-grupal");
const divMensajes = document.getElementById("sala-msj");
const divGrupos = document.getElementById('contenedor-grupos');
const formMensaje = document.getElementById('form-mensaje');
const elemMensaje = document.getElementById('mensaje');
const sesion = {};
const listaGrupos = [];

const formCrearGrupo = document.getElementById('form-crear-grupo');
formCrearGrupo.addEventListener('submit', function (event) {
	event.preventDefault();
	var elModalCrearGrupo = document.getElementById('modal-crear-grupo');
	var modalCrearGrupo = bootstrap.Modal.getOrCreateInstance(elModalCrearGrupo) // Returns a Bootstrap modal instance
	var inputNombreGrupo = document.getElementById('input-nombre-grupo');
	const nombreGrupo = inputNombreGrupo.value;
	inputNombreGrupo.value = '';
	const solicitudGrupo = {
		tipo: 'solicitud-grupo', 
		contenido: {
			solicitud: 'crear-grupo', 
			nombreGrupo: nombreGrupo
		}
	};
	modalCrearGrupo.hide();
	socket.send( JSON.stringify(solicitudGrupo) );
});
formMensaje.addEventListener('submit', function (event){
	event.preventDefault();
	//obtener el grupo seleccionado
	const grupo = listaGrupos[0];
	const mensaje = {
		tipo: "mensaje-grupal", 
		contenido: {
			fechaCreacion: null, 
			contenido: elemMensaje.value, 
			idGrupo: grupo.idGrupo
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
const ElementoMensaje = function (mensaje,esMiPropioMensaje) {
	const plantilla = document.getElementById("plantilla-mensaje");
	const clon = document.importNode(plantilla.content, true);
	clon.querySelector('div[data-nombre-usuario]').innerText = mensaje.nombreUsuario;
	clon.querySelector('div[data-contenido]').innerText = mensaje.contenido;
	clon.querySelector('div[data-fecha-creacion]').innerText = mensaje.fechaCreacion;
	if(esMiPropioMensaje === true) {
		clon.querySelector('div.elemento-mensaje').classList.add('elemento-mi-mensaje');
	} else {
		clon.querySelector('div.elemento-mensaje').classList.add('elemento-mensaje-grupal');
	}
	return clon;
}
const ElementoGrupo = function (mensaje) {
	const plantilla = document.getElementById('plantilla-grupo');
	const clon = document.importNode(plantilla.content, true);
	clon.querySelector('[data-nombre-grupo]').innerText = mensaje.nombreGrupo;
	return clon;
}
socket.onmessage = function(event) {
  var nuevoMsj = document.createElement('div');
  const mensajeRespuesta = JSON.parse(event.data);
  const mensaje = mensajeRespuesta.contenido;
  if(mensajeRespuesta.tipo === 'sesion') {
	sesion.nombreUsuario = mensaje.nombreUsuario;
	sesion.idUsuario = mensaje.idUsuario;
  } else if(mensajeRespuesta.tipo === 'solicitud-grupo' && mensajeRespuesta.contenido.solicitud == 'crear-grupo') {
	listaGrupos.push(mensajeRespuesta.contenido);
	const elemGrupo = ElementoGrupo(mensajeRespuesta.contenido);
	divGrupos.prepend(elemGrupo);
  }
  else if(mensajeRespuesta.tipo === 'mensaje-grupal') {
	const elemMensaje = ElementoMensaje(mensaje, mensaje.idUsuario === sesion.idUsuario);
	nuevoMsj = elemMensaje;
	divMensajes.prepend(nuevoMsj);
  }
  
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