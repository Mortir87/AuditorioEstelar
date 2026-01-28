package com.asg.auditorioestelar;

public class Concierto {
    private String titulo;
    private String descripcion;
    private String cartelUrl;

    public Concierto(String titulo, String descripcion, String cartelUrl) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.cartelUrl = cartelUrl;
    }

    // Getters
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getCartelUrl() { return cartelUrl; }
}