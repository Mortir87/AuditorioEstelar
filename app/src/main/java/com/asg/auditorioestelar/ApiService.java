package com.asg.auditorioestelar;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @POST("registrar.php")
    Call<String> registrarUsuario(@Body Usuario usuario);
    @POST("sucio.php")
    Call<String> publicarRegistro(@Body Sucio registro);
    @POST("confirmar_reserva.php")
    Call<Map<String, Object>> confirmarReserva(@Body Map<String, Object> body);
    @GET("mostrar_cartelera.php")
    Call<List<Concierto>> obtenerConciertos();
    @GET("obtener_sesiones.php")
    Call<List<Sesion>> obtenerSesiones(@Query("id_concierto") int idConcierto);
    @GET("obtener_butacas_sesion.php")
    Call<List<Butaca>> obtenerButacas(@Query("id_sesion") int idSesion);
    @GET("obtener_calendario.php")
    Call<List<EventoCalendario>> obtenerCalendario();

    @POST("login.php")
    Call<LoginResponse> loginUsuario(@Body Usuario usuario);

    @POST("confirmar_compra.php")
    Call<Map<String, Object>> ejecutarCompra(@Body Map<String, Object> body);

    @GET("obtener_historial.php")
    Call<List<Entrada>> getHistorial(@Query("id_usuario") String idUsuario);

    @GET("obtener_reservas_pendientes.php")
    Call<List<ReservaPendiente>> getReservasPendientes(@Query("id_usuario") int idUsuario);

    @POST("pagar_reserva.php")
    Call<ResponseBody> pagarReserva(
            @Body Map<String,Integer> body
    );
}
