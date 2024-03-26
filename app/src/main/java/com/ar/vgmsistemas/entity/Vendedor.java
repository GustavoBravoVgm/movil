package com.ar.vgmsistemas.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Vendedor extends RecursoHumano implements Serializable {

    private static final long serialVersionUID = -1507602391963037654L;

    public static final String TI_CONTROL_PR_NO_CONTROLA = "NC";

    public static final String TI_CONTROL_PR_NO_PERMITE = "NP";

    public static final String TI_CONTROL_PR_CON_AUT = "CA";

    public static final String TI_CONTROL_PR_SIN_AUT = "SA";

    private int idVendedor;

    private String tiControlPedidoRentable;


    List<SecuenciaRuteo> _secuenciasRuteo = new ArrayList<>();

    public List<SecuenciaRuteo> getSecuenciasRuteo() {
        return this._secuenciasRuteo;
    }

    public void setSecuenciasRuteo(List<SecuenciaRuteo> secuenciasRuteo) {
        this._secuenciasRuteo = secuenciasRuteo;
    }

    public String getTiControlPedidoRentable() {
        return tiControlPedidoRentable;
    }

    public void setTiControlPedidoRentable(String tiControlPedidoRentable) {
        if (tiControlPedidoRentable == null) tiControlPedidoRentable = "NC";
        this.tiControlPedidoRentable = tiControlPedidoRentable;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }
}
