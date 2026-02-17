<?php
header('Content-Type: application/json; charset=utf-8');
require_once "config/conexion.php";

$jsonRecibido = file_get_contents('php://input');
$datos = json_decode($jsonRecibido);

if ($datos && isset($datos->registro)) {
    $registro = $datos->registro;

    $sql = "INSERT INTO sucio (registro) VALUES (?)";
    $stmt = $conexion->prepare($sql);
    $stmt->bind_param("s", $registro);

    if ($stmt->execute()) {
        echo json_encode("Registro añadido correctamente");
    } else {
        echo json_encode("Error al añadir el registro");
    }

    $stmt->close();
} else {
    echo json_encode("Error: No se recibieron datos válidos.");
}

$conexion->close();
?>