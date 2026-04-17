package com.asg.auditorioestelar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale; //sin esto no coge bien las coordinadas.


public class MenuFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // "Inflamos" el diseño que creamos arriba

        return inflater.inflate(R.layout.fragment_menu, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //MAPA
        ImageView mapa = view.findViewById(R.id.iv_mapa_estatico);
        mapa.setOnClickListener(v -> {
            // coordenadas de Epsum, ahora Auditorio Estelar
            double latitud = 40.40248281355495;
            double longitud = -3.6988091643344623;
            String nombreSitio = "Auditorio Estelar";

            // generamos la query mediante URI, geo:lat,lon?q=sitio
            String uriBusqueda = String.format(Locale.US, "geo:%f,%f?q=%f,%f(%s)",
                    latitud, longitud, latitud, longitud, nombreSitio);
            Uri gmmIntentUri = Uri.parse(uriBusqueda);

            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            try {
                startActivity(mapIntent);
            } catch (Exception e) {
                // Si falla, abrimos en navegador (también con formato US)
                String webUrl = String.format(Locale.US, "https://www.google.com/maps/search/?api=1&query=%f,%f",
                        latitud, longitud);
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl)));
            }
        });

        //Logica del menu

        //Ver mi cuenta
        TextView verPerfil = view.findViewById(R.id.textViewPerfil);
        verPerfil.setOnClickListener(v -> {
            BottomNavigationView nav = getActivity().findViewById(R.id.bottom_navigation);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PerfilFragment())
                    .addToBackStack(null)
                    .commit();
            if (nav != null) {
                nav.setSelectedItemId(R.id.nav_perfil);
            }
        });

        //Ver programacion
        TextView verProgramacion = view.findViewById(R.id.textViewVerTodaLaProgramacion);
        verProgramacion.setOnClickListener(v -> {
            BottomNavigationView nav = getActivity().findViewById(R.id.bottom_navigation);
            if (nav != null) {
                nav.setSelectedItemId(R.id.nav_home);
            }
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ProgramacionFragment())
                    .addToBackStack(null)
                    .commit();
        });

        //Ver mi calendario
        TextView verCalendarioPerfil = view.findViewById(R.id.textViewCalendario);
        verPerfil.setOnClickListener(v -> {
            BottomNavigationView nav = getActivity().findViewById(R.id.bottom_navigation);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new CalendarioFragment())
                    .addToBackStack(null)
                    .commit();
            if (nav != null) {
                nav.setSelectedItemId(R.id.nav_calendario);
            }
        });
    }
}