package com.asg.auditorioestelar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.ViewHolder> {
    private List<Entrada> lista;

    public HistorialAdapter(List<Entrada> lista) { this.lista = lista; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entrada, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Entrada e = lista.get(position);
        holder.t.setText(e.getTitulo());
        holder.f.setText(e.getFecha());
        holder.p.setText(e.getTotal() + "€");
    }

    @Override
    public int getItemCount() { return lista.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView t, f, p;
        public ViewHolder(View itemView) {
            super(itemView);
            t = itemView.findViewById(R.id.txtTituloEntrada);
            f = itemView.findViewById(R.id.txtFechaEntrada);
            p = itemView.findViewById(R.id.txtTotalEntrada);
        }
    }
}