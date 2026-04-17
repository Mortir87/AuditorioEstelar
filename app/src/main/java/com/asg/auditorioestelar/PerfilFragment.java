package com.asg.auditorioestelar;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class PerfilFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        SessionManager session = new SessionManager(getContext()); //recordar que es session de usuario

        if (!session.estaLogueado()) {

            //No Login → ir a login
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .commit();

            return view;
        }

        // SI logueado → mostrar perfil
        TextView txtNombre = view.findViewById(R.id.txtNombre);
        txtNombre.setText(session.getNombre());

        Button btnLogout = view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(v -> {
            session.cerrarSesion();

            // Volver a login
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .commit();
        });

        return view;

    }

}