package com.asg.auditorioestelar;

public class Butaca {

    private int id_butaca;
    private int fila;
    private int numero;
    private String zona;
    private double precio;
    private String estado;

    // Estado en APP no en BBDD
    private boolean seleccionada = false;

    //getters
    public int getIdButaca() { return id_butaca; }
    public int getFila() { return fila; }
    public int getNumero() { return numero; }
    public String getZona() { return zona; }
    public double getPrecio() { return precio; }
    public String getEstado() { return estado; }
    public boolean getSeleccionada() {
        return seleccionada;
    }
    //setters
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }


}