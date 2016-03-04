<?php

	$mysqli = mysqli_connect("localhost","root","","guessit");
	$sql = "SELECT usuarios.id as id, usuarios.nombre as nombre, usuarios.apellidos as apellidos FROM usuarios, usuarios_aula WHERE usuarios.id = usuarios_aula.id_usuario AND usuarios_aula.id_aula = ".$id_grupo;
	mysqli_query($mysqli,"SET NAMES 'utf8'");
	$res = mysqli_query($mysqli, $sql);
	
	foreach($res as $row){
		echo "<option value=".$row['id'].">".$row['nombre']." ".$row['apellidos']."</option>";
	}
	
	mysqli_close($mysqli);

?>