package com.ar.vgmsistemas.view.extend;

public class MenuItem {

    private String texto;
    private int icono;

    public MenuItem(String texto, int icono) {
        this.texto = texto;
        this.icono = icono;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public int getIcono() {
        return icono;
    }
}
