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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroFragment extends Fragment {

    private TextInputEditText edtNombre, edtApellido, edtCorreo, edtTelefono, edtPassword, edtConfirmar;
    private Button btnRegistrar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflamos el layout del formulario
        View view = inflater.inflate(R.layout.fragment_registro, container, false);

        //Inicia las vistas
        edtNombre = view.findViewById(R.id.Nombre);
        edtApellido = view.findViewById(R.id.Apellido);
        edtCorreo = view.findViewById(R.id.Correo);
        edtTelefono = view.findViewById(R.id.Telefono);
        edtPassword = view.findViewById(R.id.Contraseña);
        edtConfirmar = view.findViewById(R.id.RepetirContraseña);
        btnRegistrar = view.findViewById(R.id.btnRegistrarse);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarRegistro();
            }
        });

        return view;
    }

    private void enviarRegistro() {
        String pass = edtPassword.getText().toString().trim();
        String confirm = edtConfirmar.getText().toString().trim();

        if (!pass.equals(confirm)) {
            Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario nuevo = new Usuario(
                edtNombre.getText().toString().trim(),
                edtApellido.getText().toString().trim(),
                edtCorreo.getText().toString().trim(),
                edtTelefono.getText().toString().trim(),
                pass
        );

        ApiService apiService = RetrofitClient.getClient("http://10.0.2.2/teatro/").create(ApiService.class);

        apiService.registrarUsuario(nuevo).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(), response.body(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), "Fallo de red: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}