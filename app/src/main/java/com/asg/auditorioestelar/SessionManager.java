package com.asg.auditorioestelar;
import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "AuditorioPrefs";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context){
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }
    public String getIdUsuarioParaHistorial() {
        int id = prefs.getInt("id_usuario", -1);
        return String.valueOf(id);
    }

    //guardamos login
    public void guardarLogin(int id, String nombre, String email) {
        editor.putBoolean("logueado", true);
        editor.putInt("id_usuario", id);
        editor.putString("nombre", nombre);
        editor.putString("email", email);
        editor.apply();
    }

    //Logout
        public void cerrarSesion(){
        editor.clear();
        editor.apply();
    }

    //Estado o control de sesion
    public boolean estaLogueado(){
        return prefs.getBoolean("logueado", false);
    }

    //Datos del usuario
    public int getIdUsuario(){
        return prefs.getInt("id_usuario", -1);
    }

    public String getNombre(){
        return prefs.getString("nombre", "");
    }

    public String getEmail(){
        return prefs.getString("email", "");
    }
}
