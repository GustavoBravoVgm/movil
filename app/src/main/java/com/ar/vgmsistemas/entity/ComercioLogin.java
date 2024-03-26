package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkComercioLogin;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class ComercioLogin implements Serializable {

    private static final long serialVersionUID = 6354610967643444849L;

    private long miId;

    @SerializedName("id")
    private PkComercioLogin id;

    private String usuario;

    private String password;

    private Date fechaCreacion;

    private String tipoLogin;

    public long getMiId() {
        return miId;
    }

    public void setMiId(long miId) {
        this.miId = miId;
    }

    public PkComercioLogin getId() {
        return id;
    }

    public void setId(PkComercioLogin _id) {
        this.id = _id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String _usuario) {
        if (_usuario == null) _usuario = "";
        this.usuario = _usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String _password) {
        if (_password == null) _password = "";
        this.password = _password;
    }

    public String getTipoLogin() {
        return tipoLogin;
    }

    public void setTipoLogin(String tipoLogin) {
        this.tipoLogin = tipoLogin;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
