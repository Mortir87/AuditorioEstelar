package com.asg.auditorioestelar;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PerfilFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // "Inflamos" el diseño que creamos arriba
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}