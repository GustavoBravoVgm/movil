package com.ar.vgmsistemas.utils;

import com.ar.vgmsistemas.bo.PreferenciaBo;

public class TipoEmpresaCode {

    public static final int TYPE_NORMAL = 1;
    public static final int TYPE_HACIENDA = 2;

    public static boolean isNormal(int tipoEmpresa) {
        if (tipoEmpresa == TYPE_NORMAL) {
            return true;
        }
        return false;
    }

    public static boolean isHacienda() {
        return (PreferenciaBo.getInstance().getPreferencia().getTipoEmpresa() == TYPE_HACIENDA);
    }
}
