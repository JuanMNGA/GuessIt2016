<?php
	
	$link = mysql_connect('localhost', 'root', '')
	or die('No se pudo conectar: ' . mysql_error());
	//echo 'Conecxion exitosa';
	mysql_select_db('guessit') or die('No se pudo seleccionar la base de datos');
	mysql_query ( "SET NAMES 'utf8'" ); // NO OLVIDARSE DE ESTO PARA LOS CARACTERES ESPECIALES
	$name = $_POST['nombre'];
	$lastname = $_POST['apellidos'];
	$email = $_POST['email'];
	$usuario = $_POST['usuario'];
	$pass = $_POST['password'];
	$pass = password_hash($pass,PASSWORD_DEFAULT);
	
	$get_last_test = "SELECT test FROM usuarios ORDER BY id DESC LIMIT 1";
	$res = mysql_query($get_last_test);
	if(!$res){
		$test = 0;
	}else{
		$row = mysql_fetch_assoc($res);
		$test = !$row['test'];
	}
	
	$alta = $_POST['alta'];
	
	$sql="INSERT INTO usuarios(nombre,apellidos,email,usuario, password, alta, test) VALUES('$name','$lastname','$email','$usuario','$pass','$alta','$test')";

	mysql_query($sql);

	// Cerrar la conexión
	mysql_close($link);
	echo true;

?>