<?php
		
		$link = mysql_connect('localhost', 'root', '')
			or die('No se pudo conectar: ' . mysql_error());
		
		mysql_select_db('guessit') or die('No se pudo seleccionar la base de datos');
		mysql_query ( "SET NAMES 'utf8'" );
		$id_aula = $_POST['id_aula'];
		$sql = "SELECT id, nombre FROM categoria WHERE id_aula = '".$id_aula."'";
		
		$result = mysql_query($sql);
		
		$string_result = "";
		
		if(count($result) > 0){
			while($row = mysql_fetch_assoc($result)){
				$string_result .= $row['id'].";".$row['nombre'].";";
			}
		}
		mysql_close($link);
		echo $string_result;
?>