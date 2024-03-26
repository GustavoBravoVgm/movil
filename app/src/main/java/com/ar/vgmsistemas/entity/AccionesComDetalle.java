package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.entity.key.PkAccionesComDetalle;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class AccionesComDetalle {
    public AccionesComDetalle() {
    }

    public AccionesComDetalle(PkAccionesComDetalle id, Integer idProveedor, Integer idNegocio,
                              Integer idRubro, Integer idSubrubro, Integer idMarca, Integer idArticulo,
                              Double taDto, Double caMaxima, Double caVendida, Double taDtoBcerrado,
                              Double rgLimiteInf, Double rgLimiteSup, Double taDtoEmpresa) {
        this.id = id;
        this.idProveedor = idProveedor;
        this.idNegocio = idNegocio;
        this.idRubro = idRubro;
        this.idSubrubro = idSubrubro;
        this.idMarca = idMarca;
        this.idArticulo = idArticulo;
        this.taDto = taDto;
        this.caMaxima = caMaxima;
        this.caVendida = caVendida;
        this.taDtoBCerrado = taDtoBcerrado;
        this.rgLimiteInf = rgLimiteInf;
        this.rgLimiteSup = rgLimiteSup;
        this.taDtoEmpresa = taDtoEmpresa;
    }

    private PkAccionesComDetalle id;

    @SerializedName("idProveedor")
    private Integer idProveedor;

    @SerializedName("idNegocio")
    private Integer idNegocio;

    @SerializedName("idRubro")
    private Integer idRubro;

    @SerializedName("idSubrubro")
    private Integer idSubrubro;

    @SerializedName("idMarca")
    private Integer idMarca;

    @SerializedName("idArticulo")
    private Integer idArticulo;

    @SerializedName("taDto")
    private Double taDto;

    private Double caMaxima;

    private Double caVendida;

    private Double taDtoBCerrado;

    private Double rgLimiteInf;

    private Double rgLimiteSup;

    private Double taDtoEmpresa;

    /*Getters/Setters*/

    public PkAccionesComDetalle getId() {
        return id;
    }

    public void setId(PkAccionesComDetalle id) {
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

    public Double getTaDtoBcerrado() {
        return taDtoBCerrado;
    }

    public void setTaDtoBcerrado(Double taDtoBcerrado) {
        this.taDtoBCerrado = taDtoBcerrado;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccionesComDetalle)) return false;
        AccionesComDetalle that = (AccionesComDetalle) o;
        return Double.compare(that.getTaDto(), getTaDto()) == 0 && Double.compare(that.getCaMaxima(), getCaMaxima()) == 0 && Double.compare(that.getCaVendida(), getCaVendida()) == 0 && Double.compare(that.getTaDtoBcerrado(), getTaDtoBcerrado()) == 0 && Double.compare(that.getRgLimiteInf(), getRgLimiteInf()) == 0 && Double.compare(that.getRgLimiteSup(), getRgLimiteSup()) == 0 && Double.compare(that.getTaDtoEmpresa(), getTaDtoEmpresa()) == 0 && getId().equals(that.getId()) && getIdProveedor().equals(that.getIdProveedor()) && getIdNegocio().equals(that.getIdNegocio()) && getIdRubro().equals(that.getIdRubro()) && getIdSubrubro().equals(that.getIdSubrubro()) && getIdMarca().equals(that.getIdMarca()) && getIdArticulo().equals(that.getIdArticulo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getIdProveedor(), getIdNegocio(), getIdRubro(), getIdSubrubro(), getIdMarca(), getIdArticulo(), getTaDto(), getCaMaxima(), getCaVendida(), getTaDtoBcerrado(), getRgLimiteInf(), getRgLimiteSup(), getTaDtoEmpresa());
    }
}
