<?php

	/*
	$mysqli = mysqli_connect("localhost","root","","guessit");
	mysqli_query($mysqli,"SET NAMES 'utf8'");
	$sql = "SELECT id, nombre FROM aula WHERE id_docente = '".$id_docente."'";
	$res = mysqli_query($mysqli, $sql);
	
	mysqli_close($mysqli);
	*/

	function dias_transcurridos($fecha_i,$fecha_f)
	{
		$dias	= (strtotime($fecha_i)-strtotime($fecha_f))/86400;
		$dias 	= abs($dias); $dias = floor($dias);		
		return $dias;
	}
	
	// Todas las variables

	$grupo_id = $id_grupo;
	$docente_id = $id_docente;
	$id_alumnos = $alumnos;
	$informe_sel = $informe;
	$tipo_respuesta = $tipo;
	$tipo_rango = $rango;
	$inicio_rango = $rango_ini;
	$fin_rango = $rango_fin;
	$nivel_sel = $nivel;
	
	$dias_transcurridos = dias_transcurridos($inicio_rango, $fin_rango);
	
	$cadena_rango = "";
	$cadena_datasets = "";
	
	// Aqui el script para obtener los datos de la DB
	
	$mysqli = mysqli_connect("localhost","root","","guessit");
	mysqli_query($mysqli,"SET NAMES 'utf8'");
	
	for($i=0;$i<count($id_alumnos);$i++){
		$dia_inicio = $inicio_rango;
		for($j=0;$j<$dias_transcurridos;$j++){
			
		}
	}
	
	// Aqui el script chartjs

	echo '<canvas id="myChart" width="800" height="400"></canvas>';
	echo '<script>';
	
	echo 'var ctx = document.getElementById("myChart").getContext("2d");';
	
	echo 'var data = {';
	
	echo 'labels: '.$cadena_rango.",";
	
	echo 'datasets: ['.$cadena_datasets.']';	
	
	echo '};';
	
	echo 'var myLineChart = new Chart(ctx).Line(data, options);';
	
	echo '</script>';
	
	mysqli_close($mysqli);

?>