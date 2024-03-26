package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import com.ar.vgmsistemas.database.dao.entity.key.PkChequeBd;

import java.io.Serializable;

@Entity(tableName = "cheques",
        primaryKeys = {"id_bancogirado_cheque", "id_cheque", "id_postal",
                "id_sucbanco", "id_nrocuenta"},
        foreignKeys = {@ForeignKey(entity = BancoBd.class,
                parentColumns = "id_bancogirado",
                childColumns = "id_bancogirado_cheque"),
                @ForeignKey(entity = EntregaBd.class,
                        parentColumns = "id_entrega",
                        childColumns = "id_entrega")},
        indices = @Index(value = "id_entrega", name = "ch_idx_id_entrega", orders = Index.Order.ASC)
)
public class ChequeBd implements Serializable {

    @NonNull
    @Embedded
    private PkChequeBd id;

    @ColumnInfo(name = "id_entrega")
    private Integer idEntrega;

    @ColumnInfo(name = "fe_cheque")
    private String fechaChequeMovil;

    @ColumnInfo(name = "pr_monto")
    private double importe;

    @ColumnInfo(name = "id_cuit")
    private String cuit;

    @ColumnInfo(name = "id_cliente")
    private Integer idCliente;

    @ColumnInfo(name = "id_sucursal")
    private Integer idSucursalCliente;

    @ColumnInfo(name = "ti_cheque")
    private String tipoCheque;

    /*Getters and Setters*/

    @NonNull
    public PkChequeBd getId() {
        return id;
    }

    public void setId(@NonNull PkChequeBd id) {
        this.id = id;
    }

    public Integer getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(Integer idEntrega) {
        this.idEntrega = idEntrega;
    }

    public String getFechaChequeMovil() {
        return fechaChequeMovil;
    }

    public void setFechaChequeMovil(String fechaChequeMovil) {
        this.fechaChequeMovil = fechaChequeMovil;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdSucursalCliente() {
        return idSucursalCliente;
    }

    public void setIdSucursalCliente(Integer idSucursalCliente) {
        this.idSucursalCliente = idSucursalCliente;
    }

    public String getTipoCheque() {
        return tipoCheque;
    }

    public void setTipoCheque(String tipoCheque) {
        this.tipoCheque = tipoCheque;
    }
}
