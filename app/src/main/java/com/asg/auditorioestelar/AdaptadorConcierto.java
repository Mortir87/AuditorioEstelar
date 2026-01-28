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

public class AdaptadorConcierto extends RecyclerView.Adapter<AdaptadorConcierto.ViewHolder> {

        private List<Concierto> listaConciertos;

        public AdaptadorConcierto(List<Concierto> listaConciertos) {
            this.listaConciertos = listaConciertos;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_concierto, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Concierto concierto = listaConciertos.get(position);
            holder.titulo.setText(concierto.getTitulo());
            holder.descripcion.setText(concierto.getDescripcion());

            // Usamos Glide para cargar la url de la BBDD
            Glide.with(holder.itemView.getContext())
                    .load(concierto.getCartelUrl())
                    .into(holder.imagen);
        }

        @Override
        public int getItemCount() {
            return listaConciertos.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imagen;
            TextView titulo, descripcion;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imagen = itemView.findViewById(R.id.imageViewCartel);
                titulo = itemView.findViewById(R.id.textViewTitulo);
                descripcion = itemView.findViewById(R.id.textViewDescripcion);
            }
        }
    }

