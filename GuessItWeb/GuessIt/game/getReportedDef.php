<?php
		
		$link = mysql_connect('localhost', 'root', '')
			or die('No se pudo conectar: ' . mysql_error());
		
		mysql_select_db('guessit') or die('No se pudo seleccionar la base de datos');
		mysql_query ( "SET NAMES 'utf8'" );
		$id_usuario = $_POST['id_usuario'];
		$id_aula = $_POST['id_aula'];
		
		$string_result = "";
		
		$sql = "SELECT count(puntuaciones.id) as jugadas FROM puntuaciones, definiciones WHERE puntuaciones.id_palabra = definiciones.id AND puntuaciones.id_usuario = ".$id_usuario." AND definiciones.id_aula = ".$id_aula;
		$res = mysql_query($sql);
		$jugadas = mysql_fetch_assoc($res);
		
		$string_result .= $jugadas['jugadas'].";";
		
		$sql = "SELECT count(puntuaciones.id) as acertadas FROM puntuaciones, definiciones WHERE puntuaciones.acierto != 0 AND  puntuaciones.id_palabra = definiciones.id AND puntuaciones.id_usuario = ".$id_usuario." AND definiciones.id_aula = ".$id_aula;
		$res = mysql_query($sql);
		$acertadas = mysql_fetch_assoc($res);
		
		$string_result .= $acertadas['acertadas'].";";
		
		$sql = "SELECT AVG(puntuaciones.puntuacion) as media FROM puntuaciones, definiciones WHERE puntuaciones.reporte = 0 AND puntuaciones.id_usuario = ".$id_usuario." AND definiciones.id = puntuaciones.id_palabra AND definiciones.id_aula = ".$id_aula;
		$res = mysql_query($sql);
		$media = mysql_fetch_assoc($res);
		
		$string_result .= $media['media'].";";
		
		$sql = "SELECT categoria.nombre as categoria, count(puntuaciones.id) FROM categoria, definiciones, puntuaciones WHERE definiciones.id = puntuaciones.id_palabra AND definiciones.id_categoria = categoria.id AND puntuaciones.id_usuario = ".$id_usuario." AND definiciones.id_aula = ".$id_aula." GROUP BY definiciones.id_categoria LIMIT 1";
		$res = mysql_query($sql);
		$categoria = mysql_fetch_assoc($res);
		
		$string_result .= $categoria['categoria'].";";
		
		$sql = "SELECT definiciones.palabra as palabra FROM definiciones,puntuaciones WHERE puntuaciones.reporte = 1 AND puntuaciones.id_palabra = definiciones.id AND definiciones.id_usuario = ".$id_usuario." AND definiciones.id_aula = ".$id_aula;
		$res = mysql_query($sql);
		
		if(count($res) > 0){
			while($row = mysql_fetch_assoc($res)){
				$string_result .= $row['palabra'].";";
			}
		}
		
		mysql_close($link);
		echo $string_result;
?>