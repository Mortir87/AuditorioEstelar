package com.asg.auditorioestelar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleConciertoFragment extends Fragment {

    private ImageView imagen;
    private TextView titulo, descripcion;
    private RecyclerView recyclerSesiones;
    private int idConcierto;

    public static DetalleConciertoFragment newInstance(int id, String titulo,
                                                       String descripcion, String cartelUrl) {
        DetalleConciertoFragment fragment = new DetalleConciertoFragment();
        Bundle args = new Bundle();
        args.putInt("id_concierto", id);
        args.putString("titulo", titulo);
        args.putString("descripcion", descripcion);
        args.putString("cartel_url", cartelUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalle_concierto, container, false);

        imagen = view.findViewById(R.id.imgDetalle);
        titulo = view.findViewById(R.id.txtTituloDetalle);
        descripcion = view.findViewById(R.id.txtDescripcionDetalle);
        recyclerSesiones = view.findViewById(R.id.recyclerSesiones);

        recyclerSesiones.setLayoutManager(new LinearLayoutManager(getContext()));

        // Recuperamos los datos del concierto (Bundle)
        Bundle args = getArguments();
        if (args != null) {
            idConcierto = args.getInt("id_concierto");
            titulo.setText(args.getString("titulo"));
            descripcion.setText(args.getString("descripcion"));


            Glide.with(getContext())
                    .load(args.getString("cartel_url"))
                    .into(imagen);
        }

        cargarSesiones();

        return view;
    }

    private void cargarSesiones() {

        ApiService apiService = RetrofitClient
                .getClient("http://10.0.2.2/teatro/")
                .create(ApiService.class);

        apiService.obtenerSesiones(idConcierto).enqueue(new Callback<List<Sesion>>() {
            @Override
            public void onResponse(Call<List<Sesion>> call, Response<List<Sesion>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    AdaptadorSesiones adapter = new AdaptadorSesiones(response.body(), sesion -> {
                        // Ir a butacas
                        Bundle bundle = new Bundle();
                        bundle.putInt("id_sesion", sesion.getIdSesion());

                        ButacasFragment fragment = new ButacasFragment();
                        fragment.setArguments(bundle);

                        getParentFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, fragment)
                                .addToBackStack(null)
                                .commit();
                    });

                    recyclerSesiones.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Sesion>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}