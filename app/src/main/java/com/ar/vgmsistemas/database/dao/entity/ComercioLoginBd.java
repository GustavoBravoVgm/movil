package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.ar.vgmsistemas.database.dao.entity.key.PkComercioLoginBd;

import java.io.Serializable;

@Entity(tableName = "comercio_login")
public class ComercioLoginBd implements Serializable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private long miId;

    @Embedded
    private PkComercioLoginBd id;

    @ColumnInfo(name = "de_usuario")
    private String usuario;

    @ColumnInfo(name = "de_password")
    private String password;

    @ColumnInfo(name = "fe_creacion")
    private String fechaCreacion;

    @ColumnInfo(name = "ti_login")
    private String tipoLogin;

    public long getMiId() {
        return miId;
    }

    public void setMiId(long miId) {
        this.miId = miId;
    }

    public PkComercioLoginBd getId() {
        return id;
    }

    public void setId(PkComercioLoginBd _id) {
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

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        if (fechaCreacion == null) fechaCreacion = "";
        this.fechaCreacion = fechaCreacion;
    }
}
