package com.asg.auditorioestelar;

import com.google.gson.annotations.SerializedName;

public class ReservaPendiente {

    @SerializedName("id_reserva")
    private int idReserva;
    private String titulo;
    private String fecha;
    private String butacas;
    private double total;

    public int getIdReserva() { return idReserva;}
    public String getTitulo() { return titulo; }
    public String getFecha() { return fecha; }
    public String getButacas() { return butacas; }
    public double getTotal() { return total; }
}
