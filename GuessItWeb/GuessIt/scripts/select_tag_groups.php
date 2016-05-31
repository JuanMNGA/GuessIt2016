<?php

	$mysqli = mysqli_connect("localhost","root","juanmo91","guessit");
	$res = mysqli_query($mysqli, "SELECT * FROM idiomas");

	if(count($res) > 0){
			echo '<select name="idioma">';
			foreach($res as $row){
					echo '<option value="'.$row['id'].'">'.$row['nombre'].'</option>';
			}
			echo '</select>';
	}
	
	mysqli_close($mysqli);
?>