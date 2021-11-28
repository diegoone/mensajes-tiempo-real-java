const divMensajes = document.getElementById("sala-msj");
const URL_BASE = "ws://localhost:8080/mywebsocket";
let socket = new WebSocket(URL_BASE + "/mensaje-grupal");
const form = document.getElementById('form');
const elemMensaje = document.getElementById('mensaje');
const elemNombre = document.getElementById('nombre');
form.addEventListener('submit', function (event){
	event.preventDefault();
	socket.send(elemMensaje.value);
	elemMensaje.value="";
});
socket.onopen = function(e) {
  console.log("[open] Connection established");
};

socket.onmessage = function(event) {
  const nuevoMsj = document.createElement('div');
  nuevoMsj.innerText = event.data;
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