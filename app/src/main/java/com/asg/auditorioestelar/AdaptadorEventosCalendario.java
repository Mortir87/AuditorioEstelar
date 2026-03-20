package com.asg.auditorioestelar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

    public class AdaptadorEventosCalendario extends RecyclerView.Adapter<AdaptadorEventosCalendario.ViewHolder> {

        private List<EventoCalendario> lista;
        private OnItemClickListener listener;

        // Interface para el clic
        public interface OnItemClickListener {
            void onItemClick(EventoCalendario evento);
        }

        public AdaptadorEventosCalendario(List<EventoCalendario> lista, OnItemClickListener listener) {
            this.lista = lista;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evento_calendario, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            EventoCalendario e = lista.get(position);
            holder.txtTitulo.setText(e.getTitulo());
            holder.txtHora.setText(e.getHora() + " h");

            holder.itemView.setOnClickListener(v -> listener.onItemClick(e));
        }

        @Override
        public int getItemCount() {
            return lista.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtTitulo, txtHora;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtTitulo = itemView.findViewById(R.id.txtTituloEvento);
                txtHora = itemView.findViewById(R.id.txtHoraEvento);
            }
        }
    }
