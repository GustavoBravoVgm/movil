package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "depositos",
        foreignKeys = {@ForeignKey(entity = BancoBd.class,
                parentColumns = "id_bancogirado",
                childColumns = "id_bancogirado_deposito"),
                @ForeignKey(entity = EntregaBd.class,
                        parentColumns = "id_entrega",
                        childColumns = "id_entrega")},
        indices = {@Index(name = "dpto_idx_id_entrega", value = {"id_entrega"}),
                @Index(name = "dpto_idx_id_banco", value = {"id_bancogirado_deposito"})}
)
public class DepositoBd implements Serializable {
    @ColumnInfo(name = "id_deposito")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "fe_deposito")
    @NonNull
    private String fechaDepositoMovil;

    @ColumnInfo(name = "nu_comprobante")
    private long numeroComprobante;

    @ColumnInfo(name = "pr_importe")
    private double importe;

    @ColumnInfo(name = "ca_cheques")
    private Integer cantidadCheques;

    @ColumnInfo(name = "id_bancogirado_deposito")
    private int idBancoGirado;

    @ColumnInfo(name = "id_entrega")
    private int idEntrega;

    /*Getters and Setters*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaDepositoMovil() {
        return fechaDepositoMovil;
    }

    public void setFechaDepositoMovil(String fechaDepositoMovil) {
        this.fechaDepositoMovil = fechaDepositoMovil;
    }

    public long getNumeroComprobante() {
        return numeroComprobante;
    }

    public void setNumeroComprobante(long numeroComprobante) {
        this.numeroComprobante = numeroComprobante;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public Integer getCantidadCheques() {
        return cantidadCheques;
    }

    public void setCantidadCheques(Integer cantidadCheques) {
        this.cantidadCheques = cantidadCheques;
    }

    public int getIdBancoGirado() {
        return idBancoGirado;
    }

    public void setIdBancoGirado(int idBancoGirado) {
        this.idBancoGirado = idBancoGirado;
    }

    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }
}
