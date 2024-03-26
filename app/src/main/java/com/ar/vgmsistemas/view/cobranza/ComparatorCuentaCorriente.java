package com.ar.vgmsistemas.view.cobranza;

import com.ar.vgmsistemas.entity.CuentaCorriente;

import java.util.Comparator;
import java.util.Date;

public class ComparatorCuentaCorriente implements Comparator<CuentaCorriente> {
    @Override
    public int compare(CuentaCorriente cc1, CuentaCorriente cc2) {
        Date date1 = cc1.getFechaVenta();
        Date date2 = cc2.getFechaVenta();
        return date1.compareTo(date2);
    }

}
