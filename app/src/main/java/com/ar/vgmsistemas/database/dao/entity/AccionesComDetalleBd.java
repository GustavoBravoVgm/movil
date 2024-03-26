package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.ar.vgmsistemas.database.dao.entity.key.PkAccionesComDetalleBd;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "acciones_com_detalle",
        primaryKeys = {"id_acciones_com", "id_acciones_com_detalle"},
        foreignKeys = @ForeignKey(entity = AccionesComBd.class,
                parentColumns = "id_acciones_com",
                childColumns = "id_acciones_com"))
public class AccionesComDetalleBd {
    @Embedded
    @NotNull
    private PkAccionesComDetalleBd id;

    @ColumnInfo(name = "id_proveedor")
    private Integer idProveedor;

    @ColumnInfo(name = "id_negocio")
    private Integer idNegocio;

    @ColumnInfo(name = "id_segmento")
    private Integer idRubro;

    @ColumnInfo(name = "id_subrubro")
    private Integer idSubrubro;

    @ColumnInfo(name = "id_linea")
    private Integer idMarca;

    @ColumnInfo(name = "id_articulos")
    private Integer idArticulo;

    @ColumnInfo(name = "ta_dto")
    private Double taDto;

    @ColumnInfo(name = "ca_maxima")
    private Double caMaxima;

    @ColumnInfo(name = "ca_vendida")
    private Double caVendida;

    @ColumnInfo(name = "ta_dto_bcerrado")
    private Double taDtoBCerrado;

    @ColumnInfo(name = "rg_limite_inf")
    private Double rgLimiteInf;

    @ColumnInfo(name = "rg_limite_sup")
    private Double rgLimiteSup;

    @ColumnInfo(name = "ta_dto_empresa")
    private Double taDtoEmpresa;

    /*Getters/Setters*/

    @NotNull
    public PkAccionesComDetalleBd getId() {
        return id;
    }

    public void setId(@NotNull PkAccionesComDetalleBd id) {
        this.id = id;
    }

    public Integer getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(Integer idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Integer getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(Integer idNegocio) {
        this.idNegocio = idNegocio;
    }

    public Integer getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(Integer idRubro) {
        this.idRubro = idRubro;
    }

    public Integer getIdSubrubro() {
        return idSubrubro;
    }

    public void setIdSubrubro(Integer idSubrubro) {
        this.idSubrubro = idSubrubro;
    }

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    public Integer getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Integer idArticulo) {
        this.idArticulo = idArticulo;
    }

    public Double getTaDto() {
        return taDto;
    }

    public void setTaDto(Double taDto) {
        this.taDto = taDto;
    }

    public Double getCaMaxima() {
        return caMaxima;
    }

    public void setCaMaxima(Double caMaxima) {
        this.caMaxima = caMaxima;
    }

    public Double getCaVendida() {
        return caVendida;
    }

    public void setCaVendida(Double caVendida) {
        this.caVendida = caVendida;
    }

    public Double getRgLimiteInf() {
        return rgLimiteInf;
    }

    public void setRgLimiteInf(Double rgLimiteInf) {
        this.rgLimiteInf = rgLimiteInf;
    }

    public Double getRgLimiteSup() {
        return rgLimiteSup;
    }

    public void setRgLimiteSup(Double rgLimiteSup) {
        this.rgLimiteSup = rgLimiteSup;
    }

    public Double getTaDtoEmpresa() {
        return taDtoEmpresa;
    }

    public void setTaDtoEmpresa(Double taDtoEmpresa) {
        this.taDtoEmpresa = taDtoEmpresa;
    }

    public Double getTaDtoBCerrado() {
        return taDtoBCerrado;
    }

    public void setTaDtoBCerrado(Double taDtoBCerrado) {
        this.taDtoBCerrado = taDtoBCerrado;
    }


}
