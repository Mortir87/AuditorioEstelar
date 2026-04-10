<?php
error_reporting(0);
ini_set('display_errors', 0);
header('Content-Type: application/json; charset=utf-8');
require_once 'config/conexion.php'; //conectamos
$json = file_get_contents('php://input');
$data = json_decode($json, true);

//Validamos los datos
if (!$data || !isset($data['email']) || !isset($data['password'])) {
    echo json_encode([
        "success" => false,
        "message" => "Datos incompletos"
    ]);
    exit;
}

$email = $data['email'];
$password = $data['password'];

//Consultamos con prepare para evitar inyects
$sentencia = $conexion->prepare("SELECT * FROM usuario WHERE email = ?");
$sentencia->bind_param("s", $email);
$sentencia->execute();
$resultado = $sentencia->get_result();

//Si encontramos el usuario
if ($usuario = $resultado->fetch_assoc()) {

    if (password_verify($password, $usuario['password'])) {

        echo json_encode([
            "success" => true,
            "usuario" => [
                "id_usuario" => $usuario['id_usuario'],
                "nombre" => $usuario['nombre'],
                "email" => $usuario['email']
            ]
        ]);
        exit; //despues de un echo siempre salir del script

    } else {
        echo json_encode([
            "success" => false,
            "message" => "Password erroneo"
        ]);
        exit; //despues de un echo siempre salir del script
    }

} else {
    echo json_encode([
        "success" => false,
        "message" => "Usuario no encontrado"
    ]);
    exit; //despues de un echo siempre salir del script
}

$sentencia->close();
$conexion->close();
?>