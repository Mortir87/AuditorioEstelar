package com.asg.auditorioestelar;

import android.os.Bundle;
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

            holder.itemView.setOnClickListener(v -> {

                // Usamos el metodo newInstance que ya tienes creado en el Fragment
                DetalleConciertoFragment fragment = DetalleConciertoFragment.newInstance(
                        concierto.getId_concierto(),
                        concierto.getTitulo(),
                        concierto.getDescripcion(),
                        concierto.getCartelUrl()
                );

                // Navegación (Casteamos a FragmentActivity que es más seguro que MainActivity)
                androidx.fragment.app.FragmentActivity actividad = (androidx.fragment.app.FragmentActivity) v.getContext();
                actividad.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            });
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

