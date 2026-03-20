package com.asg.auditorioestelar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import org.threeten.bp.DayOfWeek;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarioFragment extends Fragment {

    private MaterialCalendarView calendarView;
    private RecyclerView recyclerView;
    private List<EventoCalendario> listaCalendario = new ArrayList<>();
    private AdaptadorEventosCalendario adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendario, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        recyclerView = view.findViewById(R.id.recyclerEventosDia);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Estilo
        calendarView.setSelectionColor(Color.parseColor("#80D4A047"));
        calendarView.setHeaderTextAppearance(R.style.CalendarioMesGrande);
        calendarView.setDateTextAppearance(R.style.CalendarioNumerosGrandes);
        calendarView.setWeekDayTextAppearance(R.style.CalendarioSemanaGrande);

        // muestra solo el mes
        calendarView.setShowOtherDates(MaterialCalendarView.SHOW_NONE);

        // Empezamos desde el lunes
        calendarView.state().edit()
                .setFirstDayOfWeek(DayOfWeek.MONDAY)
                .commit();

        //español
        calendarView.setTitleMonths(getResources().getStringArray(R.array.meses_esp));
        calendarView.setWeekDayFormatter(dayOfWeek -> {
            switch (dayOfWeek) {
                case MONDAY: return "L";
                case TUESDAY: return "M";
                case WEDNESDAY: return "X";
                case THURSDAY: return "J";
                case FRIDAY: return "V";
                case SATURDAY: return "S";
                case SUNDAY: return "D";
                default: return "";
            }
        });


        // Cargamos datos
        descargarEventos();

        // Listener del calendario
        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            filtrarEventosPorDia(date);
        });

        return view;
    }

    private void descargarEventos() {
        ApiService api = RetrofitClient.getClient().create(ApiService.class);
        api.obtenerCalendario().enqueue(new Callback<List<EventoCalendario>>() {
            @Override
            public void onResponse(Call<List<EventoCalendario>> call, Response<List<EventoCalendario>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaCalendario = response.body();
                    actualizarPuntos();
                }
            }

            @Override
            public void onFailure(Call<List<EventoCalendario>> call, Throwable t) {
                Log.e("API", "Error: " + t.getMessage());
            }
        });
    }

    private void actualizarPuntos() {
        HashSet<CalendarDay> fechas = new HashSet<>();
        for (EventoCalendario e : listaCalendario) {
            CalendarDay dia = parsearFecha(e.getFecha());
            if (dia != null) fechas.add(dia);
        }
        // Aplicamos el decorador con el color dorado
        calendarView.addDecorator(new PuntoEventoCalendario(Color.parseColor("#D4A047"), fechas));
    }

    private void filtrarEventosPorDia(CalendarDay diaPulsado) {
        List<EventoCalendario> filtrados = new ArrayList<>();
        for (EventoCalendario e : listaCalendario) {
            CalendarDay fechaEvento = parsearFecha(e.getFecha());
            if (fechaEvento != null && fechaEvento.equals(diaPulsado)) {
                filtrados.add(e);
            }
        }

        // Inflamos el adaptador
        adapter = new AdaptadorEventosCalendario(filtrados, evento -> {
            abrirButacas(evento);
        });
        recyclerView.setAdapter(adapter);
    }

    private CalendarDay parsearFecha(String fechaString) {
        try {
            String[] p = fechaString.split("-");
            return CalendarDay.from(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]));
        } catch (Exception e) {
            return null;
        }
    }

    private void abrirButacas(EventoCalendario e) {
        Bundle b = new Bundle();
        b.putInt("id_sesion", e.getIdSesion());
        b.putString("titulo", e.getTitulo());
        b.putString("fecha", e.getFecha());
        b.putString("hora", e.getHora());

        ButacasFragment fragment = new ButacasFragment();
        fragment.setArguments(b);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}