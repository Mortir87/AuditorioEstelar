package com.asg.auditorioestelar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarioFragment extends Fragment {

    private TextInputEditText edtSucio;
    private Button btnPublicar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflamos el layout correcto
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        // Inicializamos las vistas
        edtSucio = view.findViewById(R.id.edtSucio);
        btnPublicar = view.findViewById(R.id.btnPublicar);

        btnPublicar.setOnClickListener(v -> publicar());

        return view;
    }

    private void publicar() {
        String texto = edtSucio.getText().toString().trim();
        if (texto.isEmpty()) {
            Toast.makeText(getContext(), "Escribe algo", Toast.LENGTH_SHORT).show();
            return;
        }

        Sucio nuevo = new Sucio(texto);

        ApiService apiService = RetrofitClient
                .getClient("http://10.0.2.2/teatro/")
                .create(ApiService.class);

        apiService.publicarRegistro(nuevo).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getContext(),"CORRECTO: dato sucio añadido ", Toast.LENGTH_LONG).show();
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