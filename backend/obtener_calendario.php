<?php
header('Content-Type: application/json; charset=utf-8');
require_once "config/conexion.php";

$sql = "SELECT 
            c.id_concierto, 
            c.titulo, 
            c.descripcion, 
            c.cartel_url, 
            s.id_sesion, 
            s.fecha, 
	DATE_FORMAT(s.hora, '%H:%i') AS hora
        FROM concierto c 
        INNER JOIN sesion s ON c.id_concierto = s.id_concierto 
        WHERE s.fecha >= CURDATE() 
        ORDER BY s.fecha ASC, s.hora ASC";

// Ejecutamos la consulta
$result = $conexion->query($sql);
$eventos = array();

if ($result) {
    while ($row = $result->fetch_assoc()) {
        // Aseguramos que sean enteros (aunque ya lo hacemos en la BBDD)
        $row['id_concierto'] = (int)$row['id_concierto'];
        $row['id_sesion'] = (int)$row['id_sesion'];
        $eventos[] = $row;
    }
}

// Enviamos el JSON
echo json_encode($eventos);

$conexion->close();
?>