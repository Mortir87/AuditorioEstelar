package com.asg.auditorioestelar;

public class ReservaPendiente {

    private int idReserva;
    private String titulo;
    private String fecha;
    private String butacas;
    private double total;

    public ReservaPendiente(int idReserva, String titulo, String fecha, String butacas, double total) {
        this.idReserva = idReserva;
        this.titulo = titulo;
        this.fecha = fecha;
        this.butacas = butacas;
        this.total = total;
    }

    // GETTERS
    public int getIdReserva() {
        return idReserva;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public String getButacas() {
        return butacas;
    }

    public double getTotal() {
        return total;
    }

    // SETTERS si
    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setButacas(String butacas) {
        this.butacas = butacas;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}