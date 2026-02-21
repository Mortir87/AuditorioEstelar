package com.asg.auditorioestelar;

public class Concierto {
    private String titulo;
    private String descripcion;
    private String cartel_url;

    public Concierto(String titulo, String descripcion, String cartelUrl) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.cartel_url = cartelUrl;
    }

    // Getters
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getCartelUrl() { return cartel_url; }
}