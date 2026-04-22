<?php
header('Content-Type: application/json; charset=utf-8');
require_once "config/conexion.php";

// Recibir JSON
$data = json_decode(file_get_contents("php://input"), true);

if (!$data) {
    echo json_encode(["success" => false, "error" => "JSON vacío"]);
    exit;
}

// Validar datos
$id_usuario = $data['id_usuario'] ?? 0;
$id_sesion = $data['id_sesion'] ?? 0;
$butacas = $data['butacas'] ?? [];

if ($id_usuario <= 0 || $id_sesion <= 0 || empty($butacas)) {
    echo json_encode(["success" => false, "error" => "Datos incompletos"]);
    exit;
}


$conexion->begin_transaction();

try {

    //insertamos en reserva
    $stmt = $conexion->prepare("INSERT INTO reserva (id_usuario) VALUES (?)");
    $stmt->bind_param("i", $id_usuario);
    $stmt->execute();

    $id_reserva = $conexion->insert_id;

    // Utilizamos smtps para evitar sql-inyection(ataques)
    $stmt_rb = $conexion->prepare(
        "INSERT INTO reserva_butaca (id_reserva, id_sesion, id_butaca) VALUES (?, ?, ?)"
    );

    $stmt_update = $conexion->prepare(
        "UPDATE butaca_sesion SET estado='RESERVADA' WHERE id_sesion=? AND id_butaca=?"
    );

    $stmt_check = $conexion->prepare(
        "SELECT estado FROM butaca_sesion WHERE id_sesion=? AND id_butaca=?"
    );

        foreach ($butacas as $b) {

        $id_butaca = $b['id_butaca'] ?? 0;

        if ($id_butaca <= 0) {
            throw new Exception("ID de butaca inválido");
        }

        // Comprobamos si está disponible
        $stmt_check->bind_param("ii", $id_sesion, $id_butaca);
        $stmt_check->execute();
        $result = $stmt_check->get_result();
        $row = $result->fetch_assoc();

        if (!$row || $row['estado'] !== 'DISPONIBLE') {
            throw new Exception("Butaca $id_butaca no disponible");
        }

        // Insertamos en reserva_butaca
        $stmt_rb->bind_param("iii", $id_reserva, $id_sesion, $id_butaca);
        $stmt_rb->execute();

        // actualizamos a reservada
        $stmt_update->bind_param("ii", $id_sesion, $id_butaca);
        $stmt_update->execute();
    }

    // Si es correcto
    $conexion->commit();
	
	//cerramos stmt
	$stmt_check->close();
    $stmt_rb->close();
    $stmt_update->close();
	

    echo json_encode([
        "success" => true,
        "id_reserva" => $id_reserva,
        "mensaje" => "Reserva creada correctamente"
    ]);

} catch (Exception $e) {

    // Con error
    $conexion->rollback();

    echo json_encode([
        "success" => false,
        "error" => $e->getMessage()
    ]);
}
//cerramos conexion-
$conexion->close();
?>