package com.asg.auditorioestelar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProgramacionFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdaptadorProgramacion adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_programacion, container, false);

        //recuperamos el boton volver aunque vengamos del home.
        View btnBack = getActivity().findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setVisibility(View.VISIBLE);
        }

        recyclerView = view.findViewById(R.id.recyclerProgramacion);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cargarConciertos();

        return view;
    }

    private void cargarConciertos() {

        ApiService apiService = RetrofitClient
                .getClient("http://10.0.2.2/teatro/")
                .create(ApiService.class);

        apiService.obtenerConciertos().enqueue(new Callback<List<Concierto>>() {
            @Override
            public void onResponse(Call<List<Concierto>> call, Response<List<Concierto>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    List<Concierto> lista = response.body();
                    adapter = new AdaptadorProgramacion(lista);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Concierto>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}