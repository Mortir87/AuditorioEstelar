package com.asg.auditorioestelar;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorButacas extends RecyclerView.Adapter<AdaptadorButacas.ViewHolder> {

    private List<Butaca> lista;

    public AdaptadorButacas(List<Butaca> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_butaca, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Butaca b = lista.get(position);

        // Mostrar número del asiento centrado
        holder.txtButaca.setText(String.valueOf(b.getNumero()));

        // Aplicar color según estado
        switch (b.getEstado()) {
            case "VENDIDA":
                holder.imgButaca.setColorFilter(Color.RED);
                holder.imgButaca.setAlpha(0.5f); // Un poco apagada
                holder.imgButaca.setEnabled(false);
                holder.txtButaca.setEnabled(false);
                holder.txtButaca.setText("X");
                break;
            case "RESERVADA":
            case "BLOQUEADA":
                holder.imgButaca.setColorFilter(Color.GRAY);
                holder.imgButaca.setAlpha(0.3f); // Casi transparente
                holder.imgButaca.setEnabled(false);
                holder.txtButaca.setEnabled(false);
                holder.txtButaca.setText("");
                break;
            default: // DISPONIBLE
                if (b.getSeleccionada()) {
                    holder.imgButaca.setColorFilter(Color.parseColor("#D4A047")); //dorado_primario
                } else {
                    holder.imgButaca.setColorFilter(Color.parseColor("#1E3A5F")); //azul_primario
                }
                holder.imgButaca.setEnabled(true);
                holder.txtButaca.setEnabled(true);

                // Listener para seleccionar/desseleccionar
                holder.itemView.setOnClickListener(v -> {
                    b.setSeleccionada(!b.getSeleccionada());
                    notifyItemChanged(position);
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgButaca;
        TextView txtButaca;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgButaca = itemView.findViewById(R.id.imgButaca); // vector de asiento
            txtButaca = itemView.findViewById(R.id.txtButaca);   // número encima del vector
        }
    }
}