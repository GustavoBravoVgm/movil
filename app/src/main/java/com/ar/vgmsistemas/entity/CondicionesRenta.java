package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CondicionesRenta {

    @SerializedName("CondicionesRenta")
    private List<CondicionRenta> condicionesRenta;

    public void setCondicionesRenta(List<CondicionRenta> condicionesRenta) {
        this.condicionesRenta = condicionesRenta;
    }

    public List<CondicionRenta> getCondicionesRenta() {
        return condicionesRenta;
    }

}
