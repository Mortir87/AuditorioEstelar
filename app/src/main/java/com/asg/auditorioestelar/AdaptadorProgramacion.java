package com.asg.auditorioestelar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdaptadorProgramacion extends RecyclerView.Adapter<AdaptadorProgramacion.ViewHolder> {

    private List<Concierto> lista;

    public AdaptadorProgramacion(List<Concierto> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_concierto_banner, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Concierto c = lista.get(position);

        holder.titulo.setText(c.getTitulo());
        holder.descripcion.setText(c.getDescripcion());

        Glide.with(holder.itemView.getContext())
                .load(c.getCartelUrl())
                .into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView titulo, descripcion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imgBanner);
            titulo = itemView.findViewById(R.id.txtTituloBanner);
            descripcion = itemView.findViewById(R.id.txtDescripcionBanner);
        }
    }
}