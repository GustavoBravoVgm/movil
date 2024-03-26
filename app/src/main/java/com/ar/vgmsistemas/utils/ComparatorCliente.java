package com.ar.vgmsistemas.utils;

import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.Preferencia;

import java.util.Comparator;

public class ComparatorCliente implements Comparator<Cliente> {

    public int compare(Cliente cli1, Cliente cli2) {
        String valor1 = "";
        String valor2 = "";
        switch (PreferenciaBo.getInstance().getPreferencia().getOrdenPreferidoCCCliente()) {
            case Preferencia.CODIGO:
                valor1 = cli1.getId().toString();
                valor2 = cli2.getId().toString();
                break;
            case Preferencia.RAZON_SOCIAL:
                valor1 = cli1.getRazonSocial();
                valor2 = cli2.getRazonSocial();
                break;
            case Preferencia.DOMICILIO:
                valor1 = cli1.getDomicilio();
                valor2 = cli2.getDomicilio();
                break;
            case Preferencia.CC_SALDO_ASC:

                return compareDouble(cli1.getTotalSaldoCuentaCorriente(), cli2.getTotalSaldoCuentaCorriente());
            case Preferencia.CC_SALDO_DESC:
                return -1 * compareDouble(cli1.getTotalSaldoCuentaCorriente(), cli2.getTotalSaldoCuentaCorriente());
        }
        return valor1.compareToIgnoreCase(valor2);
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
