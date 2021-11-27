<html>
	<head>
		<title>Conversación en tiempo real</title>
		<link rel="stylesheet" href="bootstrap-5.1.3-dist/css/bootstrap.css"/>
	</head>
<body>
<div class="container">
<h1>Conversación en tiempo real</h1>
<div class="row">
	<div class="col-md-6">
	<form id="form" action="#">
		<label class="">¿Cuál es tu nombre?</label>
		<input id="alias" name="alias">
		<input id="mensaje" name="msg">
		<button id="btn-enviar" class="btn btn-sm btn-primary" type="submit">Enviar</button>
	</form>
	</div>
	<div class="col-md-6">
		<h2 class="h5">Conversación grupal</h2>
		<div id="sala-msj"></div>
	</div>
</div>
</div>
<script>
const divMensajes = document.getElementById("sala-msj");
const host = "";
let socket = new WebSocket("ws://localhost:8080/mywebsocket/echo");
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
</script>
</body>
</html>
