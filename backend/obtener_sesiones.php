<?php
header('Content-Type: application/json; charset=utf-8');
require_once "config/conexion.php";

$id = $_GET['id_concierto'];

// Filtro para futuras sesiones
$fechaActual = date('Y-m-d');
$horaActual = date('H:i:s');

$sql = "SELECT id_sesion, fecha, hora
        FROM sesion
        WHERE id_concierto = ?
        AND (
            fecha > ?
            OR (fecha = ? AND hora >= ?)
        )
        ORDER BY fecha, hora";

// usamos prepare para evitar sql-inyection(ataques)
$stmt = $conexion->prepare($sql);
$stmt->bind_param("isss", $id, $fechaActual, $fechaActual, $horaActual);
$stmt->execute();

$result = $stmt->get_result();

$sesiones = array();

while ($row = $result->fetch_assoc()) {
    $sesiones[] = $row;
}

echo json_encode($sesiones);

$stmt->close();
$conexion->close();
?>