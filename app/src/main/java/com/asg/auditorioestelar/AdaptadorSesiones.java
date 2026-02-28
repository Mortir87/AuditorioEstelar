package com.asg.auditorioestelar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class AdaptadorSesiones extends RecyclerView.Adapter<AdaptadorSesiones.ViewHolder> {

    private List<Sesion> lista;
    private OnSesionClick listener;

    public interface OnSesionClick {
        void onClick(Sesion sesion);
    }

    public AdaptadorSesiones(List<Sesion> lista, OnSesionClick listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sesion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Sesion s = lista.get(position);
        holder.fecha.setText(s.getFecha());
        holder.hora.setText("Hora: " + s.getHora());

        holder.itemView.setOnClickListener(v -> listener.onClick(s));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fecha, hora;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.txtFechaSesion);
            hora = itemView.findViewById(R.id.txtHoraSesion);
        }
    }
}