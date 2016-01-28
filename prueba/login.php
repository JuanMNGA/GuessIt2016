<?php
		
		$link = mysql_connect('localhost', 'root', '')
			or die('No se pudo conectar: ' . mysql_error());
		
		mysql_select_db('prueba') or die('No se pudo seleccionar la base de datos');
		
		$usuario = $_POST['usuario'];
		$pass = $_POST['password'];
		
		$sql="SELECT * FROM `usuarios` WHERE usuario='".$usuario."'";
		$result = mysql_query($sql);
		
		mysql_close($link);
		
		if (count($result) > 0) {
			// Check if password is correct
			$user = mysql_fetch_assoc($result);
       		if (password_verify($pass, $user['password'])) {
				echo true;
            }
        }

?>