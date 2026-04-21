<?php
include 'conexion.php';

//ID del usuario
$id_usuario = $_GET['id_usuario'];

//concierto basado en la reserva
$sql = "SELECT c.titulo, v.fecha_venta, v.total 
        FROM venta v
        INNER JOIN reserva r ON v.id_reserva = r.id_reserva
        INNER JOIN sesion s ON r.id_sesion = s.id_sesion
        INNER JOIN concierto c ON s.id_concierto = c.id_concierto
        WHERE r.id_usuario = '$id_usuario'
        ORDER BY v.fecha_venta DESC";

$resultado = mysqli_query($conexion, $sql);
$historial = array();

while($fila = mysqli_fetch_assoc($resultado)) {
    $historial[] = $fila;
}

header('Content-Type: application/json');
echo json_encode($historial);
?>