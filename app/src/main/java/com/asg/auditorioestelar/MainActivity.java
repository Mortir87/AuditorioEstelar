package com.asg.auditorioestelar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Boton de atras
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

            if (bottomNav.getSelectedItemId() != R.id.nav_home) {
                bottomNav.setSelectedItemId(R.id.nav_home);
            } else {
                moveTaskToBack(true);
            }
        });


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // Listener para detectar los clics
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int id = item.getItemId();
            if (id == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (id == R.id.nav_perfil) {
                selectedFragment = new RegistroFragment();
            } else if (id == R.id.nav_calendario) {
                selectedFragment = new CalendarioFragment();
            } else if (id == R.id.nav_menu) {
                selectedFragment = new MenuFragment();
            }

            //ocultar boton atras en inicio
            if (id == R.id.nav_home) {
                selectedFragment = new HomeFragment();
                btnBack.setVisibility(View.GONE); // Se esconde en el inicio
            } else {
                // En las demás pantallas (Perfil, Calendario, etc.) se muestra
                btnBack.setVisibility(View.VISIBLE);
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });

        // Cargar el fragmento por defecto al abrir la app
        if (savedInstanceState == null) {
            bottomNav.setSelectedItemId(R.id.nav_home);
        }
    }
}
