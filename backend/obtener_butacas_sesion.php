<?php
header('Content-Type: application/json; charset=utf-8');
require_once "config/conexion.php";

// Validamos que existe
$id_sesion = isset($_GET['id_sesion']) ? intval($_GET['id_sesion']) : 0;

if ($id_sesion <= 0) {
    http_response_code(400); // ERROR
    echo json_encode(["error" => "ID de sesión inválido o no proporcionado"]);
    exit;
}

try {
    
    $sql = "SELECT 
                b.id_butaca, 
                b.fila, 
                b.numero, 
                z.nombre AS zona, 
                z.precio, 
                bs.estado 
            FROM butaca b
            JOIN zona z ON b.id_zona = z.id_zona
            JOIN butaca_sesion bs ON b.id_butaca = bs.id_butaca
            WHERE bs.id_sesion = ? 
            ORDER BY b.fila, b.numero";

    $stmt = $conexion->prepare($sql);
    
    // Vinculamos el parámetro (i = integer)
    $stmt->bind_param("i", $id_sesion);
    $stmt->execute();
    
    $result = $stmt->get_result();
    $butacas = [];

    while ($row = $result->fetch_assoc()) {
        $butacas[] = $row;
    }

    echo json_encode($butacas);

    // cerramos
    $stmt->close();

} catch (Exception $e) {
    http_response_code(500);
    echo json_encode(["error" => "Error en el servidor: " . $e->getMessage()]);
} finally {
    $conexion->close();
}
?>