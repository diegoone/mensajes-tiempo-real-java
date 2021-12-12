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
	.contenedor-mensajes {
	   max-height: 500px;
	   overflow-y: scroll;
	}
	</style>
</head>
<body>
	<div class="container">
<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
  <li class="nav-item" role="presentation">
    <button class="nav-link active" id="" data-bs-toggle="pill" data-bs-target="#perfil" type="button" role="tab" aria-controls="pills-home" aria-selected="true">Perfíl</button>
  </li>
  <li class="nav-item" role="presentation">
    <button class="nav-link" id="" data-bs-toggle="pill" data-bs-target="#mensajes-privados" type="button" role="tab" aria-controls="pills-profile" aria-selected="false">Privado</button>
  </li>
  <li class="nav-item" role="presentation">
    <button class="nav-link" id="" data-bs-toggle="pill" data-bs-target="#mensajes-grupales" type="button" role="tab" aria-controls="pills-contact" aria-selected="false">Mensajes grupales</button>
  </li>
</ul>
<div class="tab-content" id="pills-tabContent">
  <div class="tab-pane fade show active" id="perfil" role="tabpanel" aria-labelledby="pills-home-tab">
  	<h2>Perfil</h2>
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
		</div>
  	</div>

  </div>
  <div class="tab-pane fade" id="mensajes-privados" role="tabpanel" aria-labelledby="pills-profile-tab">
    <h2>Privados</h2>
  </div>
  <div class="tab-pane fade" id="mensajes-grupales" role="tabpanel" aria-labelledby="pills-contact-tab">
    <h2>Lista Grupos</h2>
    <div class="row">
    	<div class="col-md-4">
    		<div class="btn-group">
    			<button class="btn-primary">Crear grupo</button>
    			<button class="btn-secondary">Unirse</button>
    		</div>
		    	<ol id="contenedor-grupos" class="list-group list-group-numbered">
				  
				</ol>
    	</div>
    	<div class="col-md-4">
			<h2 class="h5">Conversación grupal</h2>
			<form id="form-mensaje" action="#">
				<label for="exampleInputPassword1">Mensaje</label>
				<div class="input-group mb-3">
					<input type="text" class="form-control" id="mensaje" autocomplete='off'>
					<button id="btn-enviar" class="btn btn-sm btn-primary"
						type="submit">Enviar</button>
				</div>
			</form>
			<div class="contenedor-mensajes" id="sala-msj"></div>
    	</div>
    </div>    
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
	<template>
		<li class="list-group-item d-flex justify-content-between align-items-start">
			<div class="ms-2 me-auto">
				  <div class="fw-bold">Subheading</div>
				  Content for list item
			</div>
			<span class="badge bg-primary rounded-pill">14</span>
		</li>
	</template>
	<script src="bootstrap-5.1.3-dist/js/bootstrap.js"></script>
	<script src="js/aplicacion.js"></script>
</body>
</html>
