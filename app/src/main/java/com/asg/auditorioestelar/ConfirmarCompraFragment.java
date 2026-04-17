package com.asg.auditorioestelar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Map;

public class ConfirmarCompraFragment extends Fragment {

    private TextView txtButacas, txtTotal, txtConcierto, txtSesion;
    private String titulo, fecha, hora;
    private ArrayList<Butaca> butacasSeleccionadas;
    private int idSesion;

    private Button btnConfirmar;



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

        btnConfirmar = view.findViewById(R.id.btnConfirmar);
        btnConfirmar.setOnClickListener(v -> confirmarReserva());

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
    private void confirmarReserva() {
            if (butacasSeleccionadas == null || butacasSeleccionadas.isEmpty()) {
                Toast.makeText(getContext(), "No hay butacas seleccionadas", Toast.LENGTH_SHORT).show();
                return;
            }

            // CREAMOS un Map para enviar como JSON
            Map<String, Object> datos = new HashMap<>();
            datos.put("id_usuario", 8); // luego cambiar por el usuario logueado HARDCODE USER TEST
            datos.put("id_sesion", idSesion);

            // Lista de butacas
            List<Map<String, Object>> listaButacas = new ArrayList<>();
            for (Butaca b : butacasSeleccionadas) {
                Map<String, Object> asiento = new HashMap<>();
                asiento.put("id_butaca", b.getIdButaca());
                asiento.put("precio", b.getPrecio());
                listaButacas.add(asiento);
            }

            datos.put("butacas", listaButacas);

            // Llamada Retrofit
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

            apiService.confirmarReserva(datos).enqueue(new Callback<Map<String, Object>>() {
                @Override
                public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                    if (response.isSuccessful() && response.body() != null && Boolean.TRUE.equals(response.body().get("success"))) {
                        Toast.makeText(getContext(), "Reserva realizada ✔", Toast.LENGTH_LONG).show();
                        getParentFragmentManager().popBackStack();
                    } else {
                        Toast.makeText(getContext(), "Error al reservar", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
