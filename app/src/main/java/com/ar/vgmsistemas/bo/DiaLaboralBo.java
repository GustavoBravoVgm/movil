package com.ar.vgmsistemas.bo;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DiaLaboralBo {

    public boolean validateDiaLaboral() {

        boolean valido = true;

        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(Calendar.getInstance().getTime());

        int dia = cal.get(Calendar.DAY_OF_WEEK);

        if ((dia < 2) || (dia > 7)) {
            valido = false;
        }
        return valido;
    }
}
