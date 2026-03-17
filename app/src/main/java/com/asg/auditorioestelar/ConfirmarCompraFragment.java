package com.asg.auditorioestelar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ConfirmarCompraFragment extends Fragment {

    private TextView txtButacas, txtTotal, txtConcierto, txtSesion;
    private String titulo, fecha, hora;
    private ArrayList<Butaca> butacasSeleccionadas;
    private int idSesion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_confirmar_compra, container, false);

        txtConcierto = view.findViewById(R.id.txtConcierto);
        txtSesion = view.findViewById(R.id.txtSesion);
        txtButacas = view.findViewById(R.id.txtButacas);
        txtTotal = view.findViewById(R.id.txtTotal);

        // Recibir datos
        if (getArguments() != null) {
            butacasSeleccionadas = (ArrayList<Butaca>) getArguments().getSerializable("butacas");
            idSesion = getArguments().getInt("id_sesion");
            titulo = getArguments().getString("titulo");
            fecha = getArguments().getString("fecha");
            hora = getArguments().getString("hora");
        }

        mostrarDatos();

        return view;
    }

    private void mostrarDatos() {

        if (butacasSeleccionadas == null) return;

        StringBuilder texto = new StringBuilder();
        double total = 0;

        for (Butaca b : butacasSeleccionadas) {

            texto.append("Fila ")
                    .append(b.getFila())
                    .append(" - Asiento ")
                    .append(b.getNumero())
                    .append(" (")
                    .append(b.getZona())
                    .append(") - ")
                    .append(b.getPrecio())
                    .append("€\n");

            total += b.getPrecio();
        }
        txtConcierto.setText(titulo);
        txtSesion.setText("Fecha: " + fecha + " - Hora: " + hora);
        txtButacas.setText(texto.toString());
        txtTotal.setText("Total: " + total + " €");
    }
}