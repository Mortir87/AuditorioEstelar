package com.asg.auditorioestelar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;

public class RecuperarPasswordFragment extends Fragment {

    private TextInputEditText edtCorreo;
    private Button btnResetear, btnCancelar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recuperar_password, container, false);

        edtCorreo = view.findViewById(R.id.CorreoRecuperar);
        btnResetear = view.findViewById(R.id.btnResetear);
        btnCancelar = view.findViewById(R.id.btnCancelar);

        // Resetear
        btnResetear.setOnClickListener(v -> {
            String correo = edtCorreo.getText().toString().trim();
            if (correo.isEmpty()) {
                Toast.makeText(getContext(), "Escribe tu correo electrónico", Toast.LENGTH_SHORT).show();
            } else {
                // API
                Toast.makeText(getContext(), "Instrucciones enviadas a: " + correo, Toast.LENGTH_LONG).show();
                volverAlLogin();
            }
        });

        // vuelta al inicio
        btnCancelar.setOnClickListener(v -> {
            volverAlLogin();
        });

        return view;
    }

    private void volverAlLogin() {
        if (getParentFragmentManager() != null) {
            getParentFragmentManager().popBackStack(); // Regresa a la pantalla anterior (Login)
        }
    }
}