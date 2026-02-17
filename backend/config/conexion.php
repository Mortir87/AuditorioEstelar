<?php
// Mostrar errores
error_reporting(E_ALL);
ini_set('display_errors', 1);

// Datos de conexión a la base de datos
$hostname = "localhost";
$database = "teatro_proyecto";
$username = "root";
$password = "";

// Crear conexión
$conexion = new mysqli($hostname, $username, $password, $database);

// Errores de conexión
if ($conexion->connect_error) {
	// Terminar la ejecución y enviar mensaje JSON
    die(json_encode("Error: Fallo de conexión a la base de datos"));
}

// fijar charset UTF-8 PROBLEMAS DE CONEXION.
$conexion->set_charset("utf8");
?>