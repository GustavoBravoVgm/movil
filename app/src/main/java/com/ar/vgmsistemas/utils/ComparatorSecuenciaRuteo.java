package com.ar.vgmsistemas.utils;

import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.SecuenciaRuteo;

import java.util.Comparator;

public class ComparatorSecuenciaRuteo implements Comparator<SecuenciaRuteo> {

    public int compare(SecuenciaRuteo ruta1, SecuenciaRuteo ruta2) {
        String valor1 = "";
        String valor2 = "";
        if (PreferenciaBo.getInstance().getPreferencia().getOrdenPreferidoCliente() == Preferencia.ORDEN_VISITA) {
            Integer orden1 = ruta1.getNumeroOrden();
            Integer orden2 = ruta2.getNumeroOrden();
            return orden1.compareTo(orden2);
        } else {
            switch (PreferenciaBo.getInstance().getPreferencia().getOrdenPreferidoCliente()) {
                case Preferencia.RAZON_SOCIAL:
                    valor1 = ruta1.getCliente().getRazonSocial();
                    valor2 = ruta2.getCliente().getRazonSocial();
                    break;
                case Preferencia.DOMICILIO:
                    valor1 = ruta1.getCliente().getDomicilio();
                    valor2 = ruta2.getCliente().getDomicilio();
                    break;
                case Preferencia.CODIGO:
                    valor1 = ruta1.getCliente().getId().toString();
                    valor2 = ruta2.getCliente().getId().toString();
                    break;
            }

            return valor1.compareToIgnoreCase(valor2);
        }
    }
}

