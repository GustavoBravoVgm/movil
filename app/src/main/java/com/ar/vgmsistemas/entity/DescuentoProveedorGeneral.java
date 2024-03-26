package com.ar.vgmsistemas.entity;

public class DescuentoProveedorGeneral {

    private int idDescuentoProveedor;

    private String descripcionDescuento;

    private int idProveedor;

    private int idNegocio;

    private int idRubro;

    private int idSubrubro;

    private int idArticulo;

    private double tasaDescuento;

    private int prioridad;

    private int idMarca;

    /*private Date feInicioVigencia;

    private Date feFinVigencia;

    private String snAplicaCliente;*/

    public int getIdDescuentoProveedor() {
        return idDescuentoProveedor;
    }

    public void setIdDescuentoProveedor(int idDescuentoProveedor) {
        this.idDescuentoProveedor = idDescuentoProveedor;
    }

    public String getDescripcionDescuento() {
        return descripcionDescuento;
    }

    public void setDescripcionDescuento(String descripcionDescuento) {
        this.descripcionDescuento = descripcionDescuento;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public int getIdNegocio() {
        return idNegocio;
    }

    public void setIdNegocio(int idNegocio) {
        this.idNegocio = idNegocio;
    }

    public int getIdRubro() {
        return idRubro;
    }

    public void setIdRubro(int idRubro) {
        this.idRubro = idRubro;
    }

    public int getIdSubrubro() {
        return idSubrubro;
    }

    public void setIdSubrubro(int idSubrubro) {
        this.idSubrubro = idSubrubro;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public double getTasaDescuento() {
        return tasaDescuento;
    }

    public void setTasaDescuento(double tasaDescuento) {
        this.tasaDescuento = tasaDescuento;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    /*public Date getFeInicioVigencia() {
        return feInicioVigencia;
    }

    public void setFeInicioVigencia(Date feInicioVigencia) {
        this.feInicioVigencia = feInicioVigencia;
    }

    public Date getFeFinVigencia() {
        return feFinVigencia;
    }

    public void setFeFinVigencia(Date feFinVigencia) {
        this.feFinVigencia = feFinVigencia;
    }

    public String getSnAplicaCliente() {
        return snAplicaCliente;
    }

    public void setSnAplicaCliente(String snAplicaCliente) {
        this.snAplicaCliente = snAplicaCliente;
    }*/

}
