package com.asg.auditorioestelar;

public class LoginResponse {

    private boolean success;
    private Usuario usuario;
    private String message; //manejo de errores

    public boolean esCorrecto() {
        return success;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getMensaje() {
        return message;
    }
}
