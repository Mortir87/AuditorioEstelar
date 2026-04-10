package com.asg.auditorioestelar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.widget.TextView;

public class LoginFragment extends Fragment {

    private TextInputEditText edtCorreo, edtPassword;
    private Button btnIniciar, btnIrARegistro;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // layout del login
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        //iniciar sesion
        edtCorreo = view.findViewById(R.id.Correo);
        edtPassword = view.findViewById(R.id.Contraseña);
        btnIniciar = view.findViewById(R.id.btnIniciar);
        btnIrARegistro = view.findViewById(R.id.btnRegistrarse);

        btnIniciar.setOnClickListener(v -> ejecutarLogin());

        // ir a registro
        btnIrARegistro.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new RegistroFragment())
                    .addToBackStack(null)
                    .commit();
        });

        // recuperar contraseña
        TextView tvOlvidaste = view.findViewById(R.id.tvOlvidasteContraseña);
        if (tvOlvidaste != null) {
            tvOlvidaste.setOnClickListener(v -> {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new RecuperarPasswordFragment())
                        .addToBackStack(null)
                        .commit();
            });
        }

        return view;
    }

    private void ejecutarLogin() {
        String email = edtCorreo.getText().toString().trim();
        String pass = edtPassword.getText().toString().trim();

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // API
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Usamos el constructor vacío y setters para evitar errores de orden de parámetros
        Usuario loginUser = new Usuario();
        loginUser.setEmail(email);
        loginUser.setPassword(pass);

        apiService.loginUsuario(loginUser).enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                //debug para capturar el error con el JSON
                try {
                    Log.d("JSON", response.errorBody() != null
                            ? response.errorBody().string()
                            : "SIN ERROR");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("CODIGO_HTTP", String.valueOf(response.code()));

                //codigoo de respuesta del servidor
                if (response.isSuccessful() && response.body() != null) {

                    LoginResponse res = response.body();

                    if (res.esCorrecto()) {

                        Usuario u = res.getUsuario();

                        //Guardamos sesion
                        SharedPreferences pref = getActivity().getSharedPreferences("AuditorioPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putBoolean("logueado", true);
                        editor.putInt("id_usuario", u.getIdUsuario());
                        editor.putString("nombre", u.getNombre());
                        editor.putString("email", u.getEmail());

                        editor.apply();

                        Toast.makeText(getContext(), "Bienvenido " + u.getNombre(), Toast.LENGTH_SHORT).show();

                        //Volvemos al Home con el login correcto
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new HomeFragment())
                                .commit();

                    } else {
                        // Volvemos al login por el login incorrecto
                        Toast.makeText(getContext(), res.getMensaje(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("ERROR_RETROFIT", t.getMessage());
                t.printStackTrace();
                Toast.makeText(getContext(), "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}