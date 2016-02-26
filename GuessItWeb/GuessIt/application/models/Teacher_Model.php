<?php

class Teacher_Model extends CI_Model
{

	function teacher_validate($id){
		$update_sql = "UPDATE usuarios SET validar=1 WHERE id=".$id;
		$this->db->query($update_sql);
	}
	
	function student_validate($id){
		$update_sql = "UPDATE usuarios_aula SET validar=1 WHERE id_usuario=".$id;
		$this->db->query($update_sql);
	}
	
	function store_def($data){
		$insert = $this->db->insert('definiciones', $data);
	    	return $insert;
	}

}