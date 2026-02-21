<?php
header('Content-Type: application/json; charset=utf-8');
require_once "config/conexion.php";

$sql = "SELECT titulo, descripcion, cartel_url FROM concierto";
$result = $conexion->query($sql);

$conciertos = array();

if ($result) {
    while ($row = $result->fetch_assoc()) {
        $conciertos[] = $row;
    }
    echo json_encode($conciertos);
} else {
    echo json_encode([
        "error" => "Error al recibir los conciertos"
    ]);
}

$conexion->close();
?>