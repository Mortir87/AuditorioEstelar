package com.asg.auditorioestelar;

import com.google.gson.annotations.SerializedName;

public class Entrada {
    @SerializedName("titulo")
    private String titulo;

    @SerializedName("fecha_venta")
    private String fecha;

    @SerializedName("total")
    private String total;

    public Entrada(String titulo, String fecha, String total) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.total = total;
    }

    public String getTitulo() { return titulo; }
    public String getFecha() { return fecha; }
    public String getTotal() { return total; }
}