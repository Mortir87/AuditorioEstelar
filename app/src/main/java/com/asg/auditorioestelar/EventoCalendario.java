package com.asg.auditorioestelar;

import java.io.Serializable;

public class EventoCalendario implements Serializable {
    private int id_concierto;
    private String titulo;
    private String descripcion;
    private String cartel_url;
    private int id_sesion;
    private String fecha;
    private String hora;

    // Getters
    public int getIdConcierto() { return id_concierto; }
    public String getTitulo() { return titulo; }
    public String getCartelUrl() { return cartel_url; }
    public int getIdSesion() { return id_sesion; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
}