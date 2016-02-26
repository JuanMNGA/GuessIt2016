<?php
if (!defined('BASEPATH'))
    exit('No direct script access allowed');

class Main extends CI_Controller
{
	public function __construct(){
		parent::__construct();
	}
	
	function index(){
        	$this->show_login();
        	//$this->show_main();
	}
	
	//Funciones para mostrar vistas
	
	function show_login(){
		$this->load->view('login/login_view');
	}
	
	function show_register(){
		$this->load->view('login/login_register_view');
	}
	
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%% ADMIN %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	
	function show_main_admin(){
		$this->load->view('main/admin/header');
		$this->load->view('main/admin/side_menu');
		$this->load->view('slave_menus/slave_menu_empty');
		$this->load->view('main/admin/footer');
	}
	
	// %%%%%%%%%%%%%%%% Teachers %%%%%%%%%%%%%%%%
	
	function show_slave_teacher_admin(){
		$this->load->view('main/admin/header');
		$this->load->view('main/admin/side_menu');
		$this->load->view('slave_menus/admin/slave_menu_teacher');
		$this->load->view('main/admin/footer');
	}
	
	function show_add_teacher_admin(){
		$this->load->view('main/admin/header');
		$this->load->view('main/admin/side_menu');
		$this->load->view('slave_menus/admin/slave_menu_teacher');
		$this->load->view('content/admin/add_teacher_view');
		$this->load->view('main/admin/footer');
	}
	
	function add_teacher(){
		$this->load->model('Login_Register_Model');
		$now = date("Y-m-d H:i:s");
		$data_to_store = array(
			'nombre' => $this->input->post('name'),
			'apellidos' => $this->input->post('lastname'),
			'email' => $this->input->post('email'),
			'password' => password_hash($this->input->post('password'),PASSWORD_DEFAULT),
			'centro' => $this->input->post('center'),
			'alta' => $now,
			'validar' => 1,
			'tipo' => 1
		);
		//if the insert has returned true then we show the flash message
		if ($this->Login_Register_Model->store_user($data_to_store)) {
			$data['flash_message'] = TRUE;
		} else {
			$data['flash_message'] = FALSE;
		}
		$this->show_add_teacher_admin();
	}
	
	function show_teacher_validate_admin(){
		$this->load->view('main/admin/header');
		$this->load->view('main/admin/side_menu');
		$this->load->view('slave_menus/admin/slave_menu_teacher');
		$this->load->view('content/admin/teachers_validate');
		$this->load->view('main/admin/footer');
	}
	
	function validate_teachers(){
		$query = $this->input->post('profesores_validados');
		$this->load->model('Teacher_Model');
		foreach($query as $id_profesor){
			$this->Teacher_Model->teacher_validate($id_profesor);
		}
		$this->show_teacher_validate_admin();
	}
	
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	
	function show_report_admin(){
		$this->load->view('main/admin/header');
		$this->load->view('main/admin/side_menu');
		$this->load->view('reports/admin/report_students');
		$this->load->view('main/admin/footer');
	}
	
	function show_report_definitions_admin(){
		$this->load->view('main/admin/header');
		$this->load->view('main/admin/side_menu');
		$this->load->view('reports/admin/report_definitions');
		$this->load->view('main/admin/footer');
	}
	
	// %%%%%%%%%%%%%  Languages %%%%%%%%%%%%%
	
	function show_languages_admin(){
		$this->load->view('main/admin/header');
		$this->load->view('main/admin/side_menu');
		$this->load->view('slave_menus/admin/slave_menu_languages');
		$this->load->view('main/admin/footer');
	}
	
	function show_languages_add_admin(){
		$this->load->view('main/admin/header');
		$this->load->view('main/admin/side_menu');
		$this->load->view('slave_menus/admin/slave_menu_languages');
		$this->load->view('content/admin/language_add');
		$this->load->view('main/admin/footer');
	}
	
	function show_languages_modify_admin(){
		$this->load->view('main/admin/header');
		$this->load->view('main/admin/side_menu');
		$this->load->view('slave_menus/admin/slave_menu_languages');
		$this->load->view('content/admin/language_modify');
		$this->load->view('main/admin/footer');
	}
	
	function add_language(){
		$this->load->model('Languages_Model');
		$langname = $this->input->post('newLang');
		$datalang = array(
			'nombre' => $langname
		);
		$this->Languages_Model->store_language($datalang);
		$this->show_languages_add_admin();
	}
	
	// %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	
	function show_profile_admin(){
		$this->load->view('main/admin/header');
		$this->load->view('main/admin/side_menu');
		$this->load->view('slave_menus/admin/slave_menu_teacher');
		$this->load->view('main/admin/footer');
	}
	
	// %%%%%%%%%%%%%%%%%%%%%%%%%% TEACHER %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	
	function show_main_teacher(){
		$this->load->view('main/teacher/header');
		$this->load->view('main/teacher/side_menu');
		$this->load->view('slave_menus/slave_menu_empty');
		$this->load->view('main/teacher/footer');
	}
	
	function show_slave_definitions_teacher(){
		$this->load->view('main/teacher/header');
		$this->load->view('main/teacher/side_menu');
		$this->load->view('slave_menus/teacher/slave_menu_definition');
		$this->load->view('main/teacher/footer');
	}
	
	function show_add_definitions_teacher(){
		$this->load->model('Login_Register_Model');
		$this->load->view('main/teacher/header');
		$this->load->view('main/teacher/side_menu');
		$this->load->view('slave_menus/teacher/slave_menu_definition');
		$id_profesor = $this->Login_Register_Model->get_teacher_id($this->session->userdata('email'));
		$data = array(
			'id_docente' => $id_profesor->id
		);
		$this->load->view('content/teacher/select_group_definition',$data);
		$this->load->view('main/teacher/footer');
	}
	
	function add_definitions(){
		$this->load->model('Login_Register_Model');
		$id_profesor = $this->Login_Register_Model->get_teacher_id($this->session->userdata('email'));
		$data = array(
			'id_docente' => $id_profesor->id
		);
		$this->load->view('main/teacher/header');
		$this->load->view('main/teacher/side_menu');
		$this->load->view('slave_menus/teacher/slave_menu_definition');
		$this->load->view('content/teacher/select_group_definition',$data);
		$id_grupo = $this->input->post('grupo_seleccionado');
		$data = array(
			'id_grupo' => $id_grupo,
			'id_docente' => $id_profesor->id
		);
		$this->load->view('content/teacher/add_definitions_form',$data);
		$this->load->view('main/teacher/footer');
	}
	
	function store_definition(){
		$this->load->model('Teacher_Model');
		$now = date("Y-m-d H:i:s");
		$data_to_store = array(
			'nivel' => $this->input->post('level'),
			'palabra' => $this->input->post('word'),
			'articulo' => $this->input->post('article'),
			'frase' => $this->input->post('definition'),
			'pista' => $this->input->post('hint'),
			'id_categoria' => $this->input->post('categoria'),
			'id_aula' => $this->input->post('gid'),
			'id_usuario' => $this->input->post('uid'),
			'fecha' => $now,
			'validar' => 1
		);
		$this->Teacher_Model->store_def($data_to_store);
		$this->add_definitions();
	}
	
	function show_report_teacher(){
		$this->load->view('main/teacher/header');
		$this->load->view('main/teacher/side_menu');
		$this->load->view('reports/teacher/report_students');
		$this->load->view('main/teacher/footer');
	}
	
	function show_report_definitions_teacher(){
		$this->load->view('main/teacher/header');
		$this->load->view('main/teacher/side_menu');
		$this->load->view('reports/teacher/report_definitions');
		$this->load->view('main/teacher/footer');
	}
	
	function show_slave_students_teacher(){
		$this->load->view('main/teacher/header');
		$this->load->view('main/teacher/side_menu');
		$this->load->view('slave_menus/teacher/slave_menu_students');
		$this->load->view('main/teacher/footer');
	}
	
	function show_add_student_teacher(){
		$this->load->view('main/teacher/header');
		$this->load->view('main/teacher/side_menu');
		$this->load->view('slave_menus/teacher/slave_menu_students');
		$this->load->view('content/teacher/add_student');
		$this->load->view('main/teacher/footer');
	}
	
	function show_validate_student_teacher(){
		$this->load->model('Login_Register_Model');
		$id_profesor = $this->Login_Register_Model->get_teacher_id($this->session->userdata('email'));
		$data = array(
			'id_docente' => $id_profesor->id
		);
		$this->load->view('main/teacher/header');
		$this->load->view('main/teacher/side_menu');
		$this->load->view('slave_menus/teacher/slave_menu_students');
		$this->load->view('content/teacher/select_group_student',$data);
		$this->load->view('main/teacher/footer');
	}
	
	function validate_student(){
		$this->load->model('Login_Register_Model');
		$id_profesor = $this->Login_Register_Model->get_teacher_id($this->session->userdata('email'));
		$data = array(
			'id_docente' => $id_profesor->id
		);
		$this->load->view('main/teacher/header');
		$this->load->view('main/teacher/side_menu');
		$this->load->view('slave_menus/teacher/slave_menu_students');
		$this->load->view('content/teacher/select_group_student',$data);
		$id_grupo = $this->input->post('grupo_seleccionado');
		$data = array(
			'id_grupo' => $id_grupo
		);
		$this->load->view('content/teacher/validate_students',$data);
		$this->load->view('main/teacher/footer');
	}
	
	function validate_student_form(){
		$query = $this->input->post('estudiantes_validados');
		$this->load->model('Teacher_Model');
		foreach($query as $id_alumno){
			$this->Teacher_Model->student_validate($id_alumno);
		}
		$this->show_validate_student_teacher();
	}
	
	function add_student(){
		$this->load->model('Login_Register_Model');
		$now = date("Y-m-d H:i:s");
		$data_to_store = array(
			'nombre' => $this->input->post('name'),
			'apellidos' => $this->input->post('lastname'),
			'usuario' => $this->input->post('username'),
			'email' => $this->input->post('email'),
			'password' => password_hash($this->input->post('password'),PASSWORD_DEFAULT),
			'alta' => $now,
			'validar' => 1,
			'tipo' => 0
		);
		//if the insert has returned true then we show the flash message
		if ($this->Login_Register_Model->store_user($data_to_store)) {
			$data['flash_message'] = TRUE;
		} else {
			$data['flash_message'] = FALSE;
		}
		$this->show_add_teacher_admin();
	}
	
	function show_slave_classroom_teacher(){
		$this->load->view('main/teacher/header');
		$this->load->view('main/teacher/side_menu');
		$this->load->view('slave_menus/teacher/slave_menu_classroom');
		$this->load->view('main/teacher/footer');
	}
	
	function show_add_classroom_teacher(){
		$this->load->view('main/teacher/header');
		$this->load->view('main/teacher/side_menu');
		$this->load->view('slave_menus/teacher/slave_menu_classroom');
		$this->load->view('content/teacher/add_class');
		$this->load->view('main/teacher/footer');
	}
	
	function add_class(){
		$this->load->model('Groups_Model');
		$this->load->model('Login_Register_Model');
		$groupname = $this->input->post('gname');
		$id_idioma = $this->input->post('idioma');
		$split_categoria = $this->input->post('categories');
		
		$categorias = array();
		
		$id_profesor = $this->Login_Register_Model->get_teacher_id($this->session->userdata('email'));
		
		$data = array(
			'nombre' => $groupname,
			'id_idioma' => $id_idioma,
			'activa' => 1,
			'id_docente' => $id_profesor->id
		);
		
		$aula = $this->Groups_Model->add_group($data);
		$id_aula = $aula->id;
		$tok = strtok($split_categoria,",");
		while($tok !== false){
			$categorias[] = $tok;
			//echo $tok;
			$data_cat = array(
				'nombre' => $tok,
				'id_aula' => $id_aula
			);
			$this->Groups_Model->add_categorie($data_cat);
			$tok = strtok(",");
		}
		
		$this->show_add_classroom_teacher();
	}
	
	function show_profile_teacher(){
		$this->load->view('main/teacher/header');
		$this->load->view('main/teacher/side_menu');
		$this->load->view('slave_menus/teacher/slave_menu_students');
		$this->load->view('main/teacher/footer');
	}
	
	// %%%%%%%%%%%%%%%%%%%%%%%% OTHERS %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	//Funciones de registro, login, cerrar sesion
	
	function input_login_user(){
        
		// Create an instance of the user model
		$this->load->model('Login_Register_Model');
		
		// Grab the email and password from the form POST
		$email = $this->input->post('email');
		$pass  = $this->input->post('password');
		
		//Ensure values exist for email and pass, and validate the user's credentials
		if ($email && $pass) {
			// If the user is valid, redirect to the main view
			if($this->Login_Register_Model->validate_user($email, $pass) == 2){
				$data = array(
					'email' => $email,
					'isLoggedIn' => true,
					'per_page' => 20
				);
				$this->session->set_userdata($data);
				$this->session->set_userdata($data);
				$this->show_main_admin();
			}else{
				if($this->Login_Register_Model->validate_user($email, $pass) == 1){
					$data = array(
						'email' => $email,
						'isLoggedIn' => true,
						'per_page' => 20
					);
					$this->session->set_userdata($data);
					$this->session->set_userdata($data);
					$this->show_main_teacher();
				}else{
					$this->index();
				}
			}
		} else {
		// Otherwise show the login screen with an error message.
			$this->index();
		}
	}
	
	function input_register(){
		$this->load->model('Login_Register_Model');
		$now = date("Y-m-d H:i:s");
		$data_to_store = array(
			'nombre' => $this->input->post('name'),
			'apellidos' => $this->input->post('lastname'),
			'email' => $this->input->post('email'),
			'password' => password_hash($this->input->post('password'),PASSWORD_DEFAULT),
			'centro' => $this->input->post('center'),
			'alta' => $now,
			'tipo' => 1
		);
		//if the insert has returned true then we show the flash message
		if ($this->Login_Register_Model->store_user($data_to_store)) {
			$data['flash_message'] = TRUE;
		} else {
			$data['flash_message'] = FALSE;
		}
		$this->index();
	}
	
	function logout_user(){
		$this->session->sess_destroy();
		$this->index();
	}
}
