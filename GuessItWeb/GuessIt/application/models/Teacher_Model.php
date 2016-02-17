<?php

class Teacher_Model extends CI_Model
{

	function teacher_validate($id){
		$update_sql = "UPDATE usuarios SET validar=1 WHERE id=".$id;
		$this->db->query($update_sql);
	}

}