package com.asg.auditorioestelar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorReservaPendiente extends RecyclerView.Adapter<AdaptadorReservaPendiente.ViewHolder> {

    private List<ReservaPendiente> lista;
    private OnReservaClick listener;

    public interface OnReservaClick {
        void onReservaClick(ReservaPendiente reserva);
    }

    public AdaptadorReservaPendiente(List<ReservaPendiente> lista, OnReservaClick listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reserva_pendiente, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ReservaPendiente r = lista.get(position);

        holder.titulo.setText(r.getTitulo());
        holder.fecha.setText(r.getFecha());
        holder.butacas.setText("Butacas: " + r.getButacas());
        holder.total.setText(r.getTotal() + "€");

        holder.btnVerEntrada.setOnClickListener(v -> {
            if (listener != null) {
                listener.onReservaClick(r);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titulo, fecha, butacas, total;
        Button btnVerEntrada;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.txtTituloReserva);
            fecha = itemView.findViewById(R.id.txtFechaReserva);
            butacas = itemView.findViewById(R.id.txtButacasReserva);
            total = itemView.findViewById(R.id.txtTotalReserva);
            btnVerEntrada = itemView.findViewById(R.id.btnVerEntrada);
        }
    }
}