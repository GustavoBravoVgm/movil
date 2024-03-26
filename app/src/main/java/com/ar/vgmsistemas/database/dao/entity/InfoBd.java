package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "info")
public class InfoBd {
    @ColumnInfo(name = "empresa")
    @PrimaryKey
    @NonNull
    private String idEmpresa;

    @ColumnInfo(name = "version")
    private String version;

    @NonNull
    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(@NonNull String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
