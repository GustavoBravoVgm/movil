package com.ar.vgmsistemas.entity;

import java.io.Serializable;


public class Repartidor extends RecursoHumano implements Serializable {
    private static final long serialVersionUID = -7046071303736401170L;

    /*private int idRepartidor;

    public String snMovil = "S";

    @Override
    public int getId() {
        return idRepartidor;
    }

    @Override
    public void setId(int id) {
        this.idRepartidor = id;
    }

    public String getSnMovil() {
        return snMovil;
    }

    public void setSnMovil(String snMovil) {
        this.snMovil = snMovil;
    }*/

    @Override
    public String toString() {

        String texto = this.getId() + " - " + this.getApellido();
        return texto;
    }

    /*public Repartidor(int id, String snMovil) {
        this.idRepartidor = id;
        this.snMovil = snMovil;
    }*/

    public Repartidor() {
    }
}
