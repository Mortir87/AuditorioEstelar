package com.asg.auditorioestelar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ButacasFragment extends Fragment {

    private RecyclerView recyclerPatio, recyclerAnfiteatro;
    private Button btnContinuar;
    private int idSesion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_butacas, container, false);

        recyclerPatio = view.findViewById(R.id.recyclerPatio);
        recyclerAnfiteatro = view.findViewById(R.id.recyclerAnfiteatro);
        btnContinuar = view.findViewById(R.id.btnContinuarCompra);

        // GRID de 4 columnas
        recyclerPatio.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerAnfiteatro.setLayoutManager(new GridLayoutManager(getContext(), 4));

        // Recibir id_sesion
        if (getArguments() != null) {
            idSesion = getArguments().getInt("id_sesion");
        }

        cargarButacas();

        return view;
    }

    private void cargarButacas() {

        ApiService apiService = RetrofitClient
                .getClient("http://10.0.2.2/teatro/")
                .create(ApiService.class);

        apiService.obtenerButacas(idSesion).enqueue(new Callback<List<Butaca>>() {
            @Override
            public void onResponse(Call<List<Butaca>> call, Response<List<Butaca>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    List<Butaca> listaCompleta = response.body();

                    // Separar por zonas
                    List<Butaca> patio = new ArrayList<>();
                    List<Butaca> anfiteatro = new ArrayList<>();

                    for (Butaca b : listaCompleta) {
                        if ("Patio".equalsIgnoreCase(b.getZona())) {
                            patio.add(b);
                        } else if ("Anfiteatro".equalsIgnoreCase(b.getZona())) {
                            anfiteatro.add(b);
                        }
                    }

                    // Adaptadores separados
                    AdaptadorButacas adapterPatio = new AdaptadorButacas(patio);
                    AdaptadorButacas adapterAnfiteatro = new AdaptadorButacas(anfiteatro);

                    recyclerPatio.setAdapter(adapterPatio);
                    recyclerAnfiteatro.setAdapter(adapterAnfiteatro);
                }
            }

            @Override
            public void onFailure(Call<List<Butaca>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}