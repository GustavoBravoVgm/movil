package com.ar.vgmsistemas.view.reparto.hojas;

import com.ar.vgmsistemas.entity.HojaDetalle;

import java.util.Comparator;

public class HojaComparator implements Comparator<HojaDetalle> {
    public static final int POS_RAZON_SOCIAL = 0;
    public static final int POS_CODIGO = 1;
    public static final int POS_SALDO_ASC = 2;
    public static final int POS_SALDO_DESC = 3;
    public int mPos;

    public HojaComparator(int posOrdenamiento) {
        mPos = posOrdenamiento;
    }

    @Override
    public int compare(HojaDetalle hoja1, HojaDetalle hoja2) {

        switch (mPos) {

            case POS_CODIGO:
                return (hoja1.getCliente().getId().toString().compareToIgnoreCase(hoja2.getCliente().getId().toString()));
            case POS_SALDO_ASC:
                return compareDouble(hoja1.getPrTotal(), hoja2.getPrTotal());
            case POS_SALDO_DESC:
                return -1 * compareDouble(hoja1.getPrTotal(), hoja2.getPrTotal());
            default:
                return hoja1.getCliente().getRazonSocial().compareToIgnoreCase(hoja2.getCliente().getRazonSocial());

        }

    }

    private int compareDouble(double valor1, double valor2) {
        if (valor1 < valor2) {
            return -1;
        } else if (valor1 == valor2) {
            return 0;
        }
        return 1;

    }

}
