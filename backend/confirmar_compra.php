<?php
include 'config/conexion.php';

$json = file_get_contents('php://input');
$data = json_decode($json, true);

if (isset($data['id_usuario']) && isset($data['butacas'])) {
    $id_usuario = $data['id_usuario'];
    $id_sesion = $data['id_sesion'];
    $success = true;

    mysqli_begin_transaction($conexion);

    foreach ($data['butacas'] as $b) {
        $id_butaca = $b['id_butaca'];
        $precio = $b['precio'];

        $q1 = "INSERT INTO tickets (id_usuario, id_funcion, id_butaca, precio, fecha_compra) VALUES ('$id_usuario', '$id_sesion', '$id_butaca', '$precio', NOW())";

        $q2 = "UPDATE butacas_evento SET id_estado = 4 WHERE id_funcion = '$id_sesion' AND id_butaca = '$id_butaca'";

        if (!mysqli_query($conexion, $q1) || !mysqli_query($conexion, $q2)) {
            $success = false;
            break;
        }
    }

    if ($success) {
        mysqli_commit($conexion);
        echo json_encode(["success" => true]);
    } else {
        mysqli_rollback($conexion);
        echo json_encode(["success" => false, "message" => "Error en la BD"]);
    }
}
?>