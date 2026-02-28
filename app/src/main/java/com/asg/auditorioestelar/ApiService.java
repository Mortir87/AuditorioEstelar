package com.asg.auditorioestelar;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @POST("registrar.php")
    Call<String> registrarUsuario(@Body Usuario usuario);
    @POST("sucio.php")
    Call<String> publicarRegistro(@Body Sucio registro);
    @GET("mostrar_cartelera.php")
    Call<List<Concierto>> obtenerConciertos();
    @GET("obtener_sesiones.php")
    Call<List<Sesion>> obtenerSesiones(@Query("id_concierto") int idConcierto);
}
