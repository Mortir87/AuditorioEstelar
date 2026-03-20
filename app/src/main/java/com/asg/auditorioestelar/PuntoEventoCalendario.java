package com.asg.auditorioestelar;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.HashSet;

public class PuntoEventoCalendario implements DayViewDecorator {

    private final int color;
    private final HashSet<CalendarDay> fechas;

    public PuntoEventoCalendario(int color, HashSet<CalendarDay> fechas) {
        this.color = color;
        this.fechas = fechas;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        // Consultamos si hay evento
        return fechas.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        // Añadimos el punto al día con 10 de radio
        view.addSpan(new DotSpan(10, color));
    }
}