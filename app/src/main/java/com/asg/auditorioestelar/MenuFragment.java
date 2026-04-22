package com.asg.auditorioestelar;
import android.app.AlertDialog;
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
        TextView verCalendario = view.findViewById(R.id.textViewVerCalendario);
        verCalendario.setOnClickListener(v -> {
            BottomNavigationView nav = getActivity().findViewById(R.id.bottom_navigation);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new CalendarioFragment())
                    .addToBackStack(null)
                    .commit();
            if (nav != null) {
                nav.setSelectedItemId(R.id.nav_calendario);
            }
        });
        //Sobre nosotros
        TextView verSobreNosotros = view.findViewById(R.id.textViewVerSobreNosotros);
        verSobreNosotros.setOnClickListener(v -> {
            // Inflamos dialog_sobrenosotros.xml
            View customView = getLayoutInflater().inflate(R.layout.dialog_sobrenosotros, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(customView);
            builder.setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();
        });


        //Condiciones de venta
        TextView verCondiciones = view.findViewById(R.id.textViewCondiciones);
        verCondiciones.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            //titulo y datos
            builder.setTitle("Condiciones de venta : Auditorio Estelar");
            builder.setMessage("Las condiciones de venta son las siguientes: \n1. Entradas no reembolsables.\n2. Prohibido el acceso con comida.\n3. Uso de mascarilla según normativa.");

            //cerrar
            builder.setPositiveButton("Aceptar", (dialog, which) -> {
                dialog.dismiss();
            });

            //mostrar
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        // Política de Privacidad
        TextView verPolitica = view.findViewById(R.id.textViewPolitica);
        verPolitica.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            //titulo y datos
            builder.setTitle("Política de Privacidad - Auditodio Estelar");
            builder.setMessage("En Auditorio Estelar protegemos tus datos:\n\n" +
                    "1. RESPONSABLE: Auditorio Estelar S.L.\n\n" +
                    "2. FINALIDAD: Gestionar la venta de entradas y enviarte avisos sobre el evento.\n\n" +
                    "3. LEGITIMACIÓN: Ejecución del contrato de compraventa.\n\n" +
                    "4. DERECHOS: Puedes acceder, rectificar y suprimir tus datos en cualquier momento enviando un correo a privacidad@estelar.com.\n\n" +
                    "5. CONSERVACIÓN: Tus datos se guardarán solo mientras sean necesarios para la gestión del evento y obligaciones legales.");

            builder.setPositiveButton("Cerrar", (dialog, which) -> {
                dialog.dismiss();
            });

            // Mas info a otra web aepd
            builder.setNeutralButton("Más información", (dialog, which) -> {

                //enlazamos con web de derechos
                String url = "https://www.aepd.es/es/derechos-y-deberes/conoce-tus-derechos";

                //Intent para aepd externa
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);

            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        }
    }