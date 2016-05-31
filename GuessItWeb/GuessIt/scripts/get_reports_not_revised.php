<?php

	$mysqli = mysqli_connect("localhost","root","juanmo91","guessit");
	mysqli_query($mysqli,"SET NAMES 'utf8'");
	$sql = "SELECT puntuaciones.id AS pid, puntuaciones.motivo AS motivo, definiciones.palabra AS palabra, definiciones.articulo AS articulo, definiciones.frase AS frase, definiciones.pista AS pista, usuarios.nombre AS nombre, usuarios.apellidos AS apellidos FROM puntuaciones, definiciones, usuarios WHERE puntuaciones.reporte = 1 AND puntuaciones.revision = 0 AND definiciones.id_aula = ".$id_grupo." AND definiciones.id = puntuaciones.id_palabra AND usuarios.id = puntuaciones.id_usuario";
	//echo $sql;
	$res = mysqli_query($mysqli, $sql);
	echo form_open('index.php/Main/review_report','class="form"');
	echo '<input type="hidden" class="form-control" name="gid" value="'.$id_grupo.'">';
	foreach($res as $row){
		echo '<div class="panel panel-info">';
			echo '<input type="hidden" class="form-control" name="pid[]" value="'.$row['pid'].'">';
			echo '<div class="panel-heading">';
				echo $row['motivo'];
			echo '</div>';
			echo '<div class="panel-body">';
				echo "<b>Word: </b>".$row['palabra'];
				echo '<br>';
				echo "<b>Article: </b>".$row['articulo'];
				echo '<br>';
				echo "<b>Definition: </b>".$row['frase'];
				echo '<br>';
				echo "<b>Hint: </b>".$row['pista'];
				echo '<br>';
				echo "<b>Responsible: </b>".$row['nombre']." ".$row['apellidos'];
				echo '<br>';
				echo "<b>Reason: </b>".$row['motivo'];
				echo '<br>';
				echo '<b>Review: </b><select name="correccion[]">';
				echo '<option value="0"> Not revised </option>';
				echo '<option value="1"> Correct </option>';
				echo '<option value="2"> Wrong content </option>';
				echo '<option value="3"> Offensive </option>';
				echo '<option value="4"> Linguistic mistakes </option>';
				echo '<option value="5"> Difficult </option>';
				echo '</select>';
			echo '</div>';
		echo '</div>';
	}
	echo '<input type="submit" class="btn btn-primary" value="Submit review">';
	
	echo form_close();
	mysqli_close($mysqli);
?>