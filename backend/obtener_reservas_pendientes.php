<?php
header('Content-Type: application/json; charset=utf-8');
require_once "config/conexion.php";

$id_usuario = $_GET['id_usuario'] ?? 0;

if ($id_usuario <= 0) {
    echo json_encode([]);
    exit;
}

$sql = "
SELECT 
    r.id_reserva,
    c.titulo,
    s.fecha,
    GROUP_CONCAT(CONCAT(b.fila,'-', b.numero) SEPARATOR ', ') AS butacas,
    SUM(z.precio) AS total
FROM reserva r
INNER JOIN reserva_butaca rb ON r.id_reserva = rb.id_reserva
INNER JOIN butaca b ON rb.id_butaca = b.id_butaca
INNER JOIN butaca_sesion bs 
    ON bs.id_butaca = rb.id_butaca 
    AND bs.id_sesion = rb.id_sesion
INNER JOIN sesion s ON rb.id_sesion = s.id_sesion
INNER JOIN concierto c ON s.id_concierto = c.id_concierto
INNER JOIN zona z ON b.id_zona = z.id_zona
WHERE r.id_usuario = ?
AND bs.estado = 'RESERVADA'
GROUP BY r.id_reserva, c.titulo, s.fecha
ORDER BY r.fecha_reserva DESC
";

$stmt = $conexion->prepare($sql);
$stmt->bind_param("i", $id_usuario);
$stmt->execute();

$result = $stmt->get_result();

$reservas = [];

while ($row = $result->fetch_assoc()) {
    $reservas[] = [
        "id_reserva" => (int)$row["id_reserva"],
        "titulo" => $row["titulo"],
        "fecha" => $row["fecha"],
        "butacas" => $row["butacas"],
        "total" => (float)$row["total"]
    ];
}

echo json_encode($reservas);

$stmt->close();
$conexion->close();
?>