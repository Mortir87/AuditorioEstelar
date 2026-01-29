package com.asg.auditorioestelar; // Asegúrate de que este sea TU paquete real

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // inflamos la vista
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // cargamos los componentes
        ViewPager2 viewPager2 = view.findViewById(R.id.viewPagerCartelera);
        TabLayout tabLayout = view.findViewById(R.id.tabLayoutIndicador);


        // Datos de prueba hasta conectar a la BBDD
        List<Concierto> listaConciertos = new ArrayList<>();
        listaConciertos.add(new Concierto("Gran Concierto Estelar", "Una noche bajo las estrellas", "https://images.unsplash.com/photo-1540039155733-5bb30b53aa14?auto=format&fit=crop&w=800&q=80"));
        listaConciertos.add(new Concierto("Rock en el Auditorio", "Toda la energía del rock local", "https://images.unsplash.com/photo-1501281668745-f7f57925c3b4?auto=format&fit=crop&w=800&q=80"));
        listaConciertos.add(new Concierto("Festival de Jazz", "Los mejores exponentes del género", "https://images.unsplash.com/photo-1511192336575-5a79af67a629?auto=format&fit=crop&w=800&q=80"));

        // AdaptadorConcierto
        AdaptadorConcierto adapter = new AdaptadorConcierto(listaConciertos);
        viewPager2.setAdapter(adapter);

        // Puntitos del selector
        if (tabLayout != null && viewPager2 != null) {
            new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
        //no personalizamos
                }).attach();
        }

        return view;
    }
}