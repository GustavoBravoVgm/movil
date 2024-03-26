package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "auditoria_gps")
public class AuditoriaGpsBd implements Serializable {
    @ColumnInfo(name = "id_auditoria")
    @PrimaryKey(autoGenerate = true)
    private long idAuditoria;

    @ColumnInfo(name = "id_vendedor")
    private Integer idVendedor;

    @ColumnInfo(name = "fe_registro_movil")
    private String feRegistroMovil;

    @ColumnInfo(name = "fe_registro_servidor")
    private String feRegistroServidor;

    @ColumnInfo(name = "ti_accion")
    private String tiAccion;

    @ColumnInfo(name = "id_movil")
    private String idMovil;

    /*Getters-Setters*/

    public long getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(long idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getFeRegistroMovil() {
        return feRegistroMovil;
    }

    public void setFeRegistroMovil(String feRegistroMovil) {
        this.feRegistroMovil = feRegistroMovil;
    }

    public String getFeRegistroServidor() {
        return feRegistroServidor;
    }

    public void setFeRegistroServidor(String feRegistroServidor) {
        this.feRegistroServidor = feRegistroServidor;
    }

    public String getTiAccion() {
        return tiAccion;
    }

    public void setTiAccion(String tiAccion) {
        this.tiAccion = tiAccion;
    }

    public String getIdMovil() {
        return idMovil;
    }

    public void setIdMovil(String idMovil) {
        this.idMovil = idMovil;
    }
}
