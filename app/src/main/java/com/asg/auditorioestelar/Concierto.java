package com.asg.auditorioestelar;

public class Concierto {
    private int id_concierto;
    private String titulo;
    private String descripcion;
    private String cartel_url;

    public Concierto(String titulo, String descripcion, String cartelUrl) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.cartel_url = cartelUrl;
    }
    // contructor vacio
    public Concierto(){
    }
    // constructor
    public Concierto(int id_concierto, String titulo, String descripcion, String cartelUrl) {
        this.id_concierto = id_concierto;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.cartel_url = cartelUrl;
    }

    // Getters
    public int getId_concierto() { return id_concierto; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getCartelUrl() { return cartel_url; }
}