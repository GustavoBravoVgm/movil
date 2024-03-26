package com.ar.vgmsistemas.utils;

import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.NoPedido;
import com.ar.vgmsistemas.entity.Preferencia;

import java.util.Comparator;
import java.util.Date;

public class ComparatorNoPedido implements Comparator<NoPedido> {

    public int compare(NoPedido v1, NoPedido v2) {
        switch (PreferenciaBo.getInstance().getPreferencia().getOrdenPreferidoNoPedido()) {
            case Preferencia.RAZON_SOCIAL:
                String valor1 = v1.getCliente().getRazonSocial();
                String valor2 = v2.getCliente().getRazonSocial();
                return valor1.compareTo(valor2);
            case Preferencia.FECHA:
                Date fecha1 = v1.getFechaNoPedido();
                Date fecha2 = v2.getFechaNoPedido();
                return fecha1.compareTo(fecha2);
            case Preferencia.MOTIVO:
                String motivo1 = v1.getMotivoNoPedido().getDescripcion();
                String motivo2 = v2.getMotivoNoPedido().getDescripcion();
                return motivo1.compareTo(motivo2);
        }
        return 0;
    }

}
