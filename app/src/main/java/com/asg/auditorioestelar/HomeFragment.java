package com.asg.auditorioestelar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import android.os.Handler;
import android.os.Looper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;

    // Necesario para la rotación automática
    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;

    // Adaptador
    private AdaptadorConcierto adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflamos la vista
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Referencias de la interfaz
        viewPager2 = view.findViewById(R.id.viewPagerCartelera);
        tabLayout = view.findViewById(R.id.tabLayoutIndicador);

        // "Ver toda la programacion"
        TextView verProgramacion = view.findViewById(R.id.textVerProgramacion);

        verProgramacion.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ProgramacionFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // Cargar conciertos desde la API
        cargarConciertos();


        return view;
    }

    // Peticion API para obtener los conciertos (mostrar_cartelera.php)
    private void cargarConciertos() {

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        apiService.obtenerConciertos().enqueue(new Callback<List<Concierto>>() {
            @Override
            public void onResponse(Call<List<Concierto>> call, Response<List<Concierto>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    List<Concierto> listaConciertos = response.body();

                    // Creamos el adaptador
                    adapter = new AdaptadorConcierto(listaConciertos);
                    viewPager2.setAdapter(adapter);

                    // Indicadores (puntitos)
                    new TabLayoutMediator(tabLayout, viewPager2,
                            (tab, position) -> {
                            }).attach(); // Sin personalizar

                    // iniciamos rotación
                    iniRotacion();
                }
            }

            @Override
            public void onFailure(Call<List<Concierto>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    // Rotacion cartelera

    private void iniRotacion() {
        handler.removeCallbacks(runnable);
        runnable = new Runnable() {
            @Override
            public void run() {

                if (adapter == null || adapter.getItemCount() == 0) return;

                int currentItem = viewPager2.getCurrentItem();
                int totalItems = adapter.getItemCount();

                if (currentItem == totalItems - 1) {
                    viewPager2.setCurrentItem(0);
                } else {
                    viewPager2.setCurrentItem(currentItem + 1);
                }

                handler.postDelayed(this, 3000); // cada 3 segundos
            }
        };

        handler.postDelayed(runnable, 3000);
    }

    /* Detenemos el runnable en el cambio de fragment */
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        // Si ya tenemos el adaptador, reiniciamos la rotación al volver
        if (adapter != null) {
            iniRotacion();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // DETENER el giro inmediatamente cuando el fragmento deja de verse
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}