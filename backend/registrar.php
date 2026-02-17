<?php
header('Content-Type: application/json; charset=utf-8');
require_once "config/conexion.php";

//JSON recibido
$jsonRecibido = file_get_contents('php://input');
$datos = json_decode($jsonRecibido);

if ($datos) {
    $nombre = $datos->nombre;
    $apellido = $datos->apellido;
    $email = $datos->email;
    $telefono = $datos->telefono;
    $password_encriptada = password_hash($datos->password, PASSWORD_DEFAULT);
    $rol = 'cliente';

    $sql = "INSERT INTO usuario (nombre, apellido, email, telefono, password, rol) VALUES (?, ?, ?, ?, ?, ?)";
    $sentencia = $conexion->prepare($sql);
    
    $sentencia->bind_param("ssssss", $nombre, $apellido, $email, $telefono, $password_encriptada, $rol);

    if ($sentencia->execute()) {
        echo json_encode("Usuario registrado correctamente");
    } else {
        echo json_encode("Error: El correo ya existe");
    }
    
    $sentencia->close();
} else {
    echo json_encode("Error: No se recibieron datos validos.");
}

$conexion->close();
?>