package com.ar.vgmsistemas.view.reparto.hojas.detalles;

import com.ar.vgmsistemas.entity.VentaDetalle;

import java.util.Comparator;

public class HojaDetalleComparator implements Comparator<VentaDetalle> {
    private final int mCriteria;
    public static final int CODIGO = 1;
    public static final int DESCRIPCION = 2;
    public static final int SECUENCIA = 0;

    public HojaDetalleComparator(int criteria) {
        mCriteria = criteria;
    }

    @Override
    public int compare(VentaDetalle vd1, VentaDetalle vd2) {
        String valor1 = "";
        String valor2 = "";
        switch (mCriteria) {
            case DESCRIPCION:
                valor1 = vd1.getArticulo().getDescripcion();
                valor2 = vd2.getArticulo().getDescripcion();

                break;
            case SECUENCIA:
                Integer long1 = vd1.getId().getSecuencia();
                Integer long2 = vd2.getId().getSecuencia();
                return long1.compareTo(long2);

            default:
                valor1 = vd1.getArticulo().getCodigoEmpresa();
                valor2 = vd2.getArticulo().getCodigoEmpresa();
                break;
        }
        return valor1.compareTo(valor2);
    }

}
