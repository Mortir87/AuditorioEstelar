<?php
header('Content-Type: application/json; charset=utf-8');
require_once "config/conexion.php";

$id_sesion = $_GET['id_sesion'];

$sql = "SELECT
            b.id_butaca,
            b.fila,
            b.numero,
            z.nombre AS zona,
            z.precio,
            bs.estado
        FROM butaca b
        JOIN zona z ON b.id_zona = z.id_zona
        JOIN butaca_sesion bs
            ON b.id_butaca = bs.id_butaca
        WHERE bs.id_sesion = $id_sesion
        ORDER BY b.fila, b.numero";

$result = $conexion->query($sql);

$butacas = array();

while ($row = $result->fetch_assoc()) {
    $butacas[] = $row;
}

echo json_encode($butacas);

$conexion->close();
?>