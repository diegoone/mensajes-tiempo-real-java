const URL_BASE = "ws://localhost:8080/mywebsocket";
let socket = new WebSocket(URL_BASE + "/mensaje-grupal");
const divMensajes = document.getElementById("sala-msj");
const divGrupos = document.getElementById('contenedor-grupos');
const formMensaje = document.getElementById('form-mensaje');
const elemMensaje = document.getElementById('mensaje');
const sesion = {};
const listaGrupos = [];
var idGrupoSeleccionado = null;
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
const getGrupoSeleccionado = function () {
	const grupo = listaGrupos.find( g => g.idGrupo === idGrupoSeleccionado);
	return grupo;
};
formMensaje.addEventListener('submit', function (event){
	event.preventDefault();
	//obtener el grupo seleccionado
	const grupo = getGrupoSeleccionado();;
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
	const clon = plantilla.content.firstElementChild.cloneNode(true);
	clon.querySelector('div[data-nombre-usuario]').innerText = mensaje.nombreUsuario;
	clon.querySelector('div[data-contenido]').innerText = mensaje.contenido;
	clon.querySelector('div[data-fecha-creacion]').innerText = mensaje.fechaCreacion;
	if(esMiPropioMensaje === true) {
		clon.classList.add('elemento-mi-mensaje');
	} else {
		clon.classList.add('elemento-mensaje-grupal');
	}
	return clon;
}
const ElementoGrupo = function (mensaje) {
	const plantilla = document.getElementById('plantilla-grupo');
	const clon = plantilla.content.firstElementChild.cloneNode(true);
	clon.querySelector('[data-nombre-grupo]').innerText = mensaje.nombreGrupo;
	return clon;
}
const seleccionarGrupo = function (divGrupos, elemGrupo) {
	const gruposActivos = divGrupos.children;
	//en teoria solo deberia de haber un elemento activo a la ves, 
	//pero en caso que el usuario halla activado desde el inspector, aqui se desactiva
	var i = 0;
	for ( ; i < gruposActivos.length; i++ ) {
		if( gruposActivos[i] === elemGrupo ) {
			gruposActivos[i].classList.add('active');
		} else { 
			gruposActivos[i].classList.remove('active');
		}
	}
}
const cargarMensajes = function (divMensajes, grupo) {
	var elemMensaje = null;
	divMensajes.innerHTML = '';
	grupo.listaMensajes.forEach( mensaje => {
		elemMensaje= ElementoMensaje(mensaje, mensaje.idUsuario === sesion.idUsuario);
		divMensajes.prepend(elemMensaje);
	});
};
socket.onmessage = function(event) {
  const mensajeRespuesta = JSON.parse(event.data);
  const mensaje = mensajeRespuesta.contenido;
  if(mensajeRespuesta.tipo === 'sesion') {
	sesion.nombreUsuario = mensaje.nombreUsuario;
	sesion.idUsuario = mensaje.idUsuario;
  } else if(mensajeRespuesta.tipo === 'solicitud-grupo' && mensajeRespuesta.contenido.solicitud == 'crear-grupo') {
	const nuevoGrupo = mensajeRespuesta.contenido; 
	listaGrupos.push(nuevoGrupo);
	//por defecto iniciar la lista de mensajes vacio e ir agregando en cuanto se reciban
	nuevoGrupo.listaMensajes = [];
	const elemGrupo = ElementoGrupo(nuevoGrupo);
	elemGrupo.addEventListener('click', function (event) {
		seleccionarGrupo(divGrupos, elemGrupo);
		idGrupoSeleccionado = nuevoGrupo.idGrupo;
		//actualizar el div con los mensajes del grupo
		const grupo = listaGrupos.find( g => g.idGrupo === nuevoGrupo.idGrupo);
		cargarMensajes(divMensajes, grupo);
	});
	divGrupos.prepend(elemGrupo);
	idGrupoSeleccionado = nuevoGrupo.idGrupo;
	seleccionarGrupo(divGrupos, elemGrupo);
  }
  else if(mensajeRespuesta.tipo === 'mensaje-grupal') {
	const mensajeGrupo = mensajeRespuesta.contenido;
	const grupo = listaGrupos.find( g => g.idGrupo === mensajeGrupo.idGrupo);
	grupo.listaMensajes.push(mensaje);
	cargarMensajes(divMensajes, grupo);
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