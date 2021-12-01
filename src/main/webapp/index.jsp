<html>
<head>
	<title>Conversación en tiempo real</title>
	<link rel="stylesheet" href="bootstrap-5.1.3-dist/css/bootstrap.css" />
	<style>
	.elemento-mensaje {
	padding: .5em 1em;
	border-radius: 1em;
	}
	.elemento-mi-mensaje {
		background-color: lightgray;
	}
	.elemento-mensaje-grupal {
		background-color: beige;
	}
	</style>
</head>
<body>
	<div class="container">
		<h1>Conversación en tiempo real</h1>
		<div class="row">
			<div class="col-md-6">
				<form id="form-nombre-usuario" action="#">
					<div class="form-group">
						<label for="nombre-usuario">¿Cuál es tu nombre?</label> <input type="text"
							class="form-control" id="nombre-usuario" autocomplete='off'>
						<button id="btn-enviar" class="btn btn-sm btn-primary"
							type="submit">Establecer nombre</button>
					</div>
				</form>
				<form id="form-mensaje" action="#">
					<div class="form-group">
						<label for="exampleInputPassword1">Mensaje</label> <input
							type="text" class="form-control" id="mensaje" autocomplete='off'>
					</div>
					<div class="">
						<button id="btn-enviar" class="btn btn-sm btn-primary"
							type="submit">Enviar</button>
					</div>
				</form>
			</div>
			<div class="col-md-6">
				<h2 class="h5">Conversación grupal</h2>
				<div id="sala-msj"></div>
			</div>
		</div>
	</div>
	<template id="plantilla-mensaje">
		<div class="elemento-mensaje">
			<div class="font-weight-bold" data-nombre-usuario>Nombre Usuario</div>
			<div data-contenido>Contenido</div>
			<div class="font-italic " data-fecha-creacion>Fecha creación</div>
		</div>
	</template>
	<script src="js/aplicacion.js"></script>
</body>
</html>
