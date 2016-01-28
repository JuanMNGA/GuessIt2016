<?php

	$link = mysql_connect('localhost', 'root', '')
	or die('No se pudo conectar: ' . mysql_error());
	//echo 'Conecxion exitosa';
	mysql_select_db('prueba') or die('No se pudo seleccionar la base de datos');

	$usuario = $_POST['usuario'];
	$pass = $_POST['password'];
	$pass = password_hash($pass,PASSWORD_DEFAULT);
	
	$sql="INSERT INTO usuarios(usuario, password) VALUES('$usuario','$pass')";

	mysql_query($sql);

	// Cerrar la conexión
	mysql_close($link);
	echo true;

?>