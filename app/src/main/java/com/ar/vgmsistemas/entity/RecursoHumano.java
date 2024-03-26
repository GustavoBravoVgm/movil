package com.ar.vgmsistemas.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class RecursoHumano implements Serializable {

    private static final long serialVersionUID = 3172092902632970899L;

    @SerializedName("id")
    private int id;

    private String apellido;

    private String nombre;

    private String domicilio;

    private String numeroDocumento;

    private String password;

    private Date horaEntradaManana;

    private Date horaSalidaManana;

    private Date horaEntradaTarde;

    private Date horaSalidaTarde;

    private boolean isActivo;

    private int categoria;

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        this.id = _id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String _apellido) {
        this.apellido = _apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String _nombre) {
        this.nombre = _nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String _domicilio) {
        this.domicilio = _domicilio;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String _numeroDocumento) {
        this.numeroDocumento = _numeroDocumento;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String _password) {
        this.password = _password;
    }

    public Date getHoraEntradaManana() {
        return horaEntradaManana;
    }

    public void setHoraEntradaManana(Date _horaEntradaManana) {
        this.horaEntradaManana = _horaEntradaManana;
    }

    public Date getHoraSalidaManana() {
        return horaSalidaManana;
    }

    public void setHoraSalidaManana(Date _horaSalidaManana) {
        this.horaSalidaManana = _horaSalidaManana;
    }

    public Date getHoraEntradaTarde() {
        return horaEntradaTarde;
    }

    public void setHoraEntradaTarde(Date _horaEntradaTarde) {
        this.horaEntradaTarde = _horaEntradaTarde;
    }

    public Date getHoraSalidaTarde() {
        return horaSalidaTarde;
    }

    public void setHoraSalidaTarde(Date _horaSalidaTarde) {
        this.horaSalidaTarde = _horaSalidaTarde;
    }

    public boolean getIsActivo() {
        return isActivo;
    }

    public void setIsActivo(boolean _isActivo) {
        this.isActivo = _isActivo;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecursoHumano)) return false;
        RecursoHumano that = (RecursoHumano) o;
        return id == that.id && isActivo == that.isActivo && getCategoria() == that.getCategoria() &&
                Objects.equals(apellido, that.apellido) && Objects.equals(nombre, that.nombre) &&
                Objects.equals(domicilio, that.domicilio) && Objects.equals(numeroDocumento, that.numeroDocumento) &&
                Objects.equals(password, that.password) && Objects.equals(horaEntradaManana, that.horaEntradaManana) &&
                Objects.equals(horaSalidaManana, that.horaSalidaManana) && Objects.equals(horaEntradaTarde, that.horaEntradaTarde) &&
                Objects.equals(horaSalidaTarde, that.horaSalidaTarde);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apellido, nombre, domicilio, numeroDocumento, password, horaEntradaManana, horaSalidaManana, horaEntradaTarde, horaSalidaTarde, isActivo, getCategoria());
    }

    @Override
    public String toString() {
        return "RecursoHumano{" +
                "id=" + id +
                ", apellido='" + apellido + '\'' +
                ", nombre='" + nombre + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", numeroDocumento='" + numeroDocumento + '\'' +
                ", password='" + password + '\'' +
                ", horaEntradaManana=" + horaEntradaManana +
                ", horaSalidaManana=" + horaSalidaManana +
                ", horaEntradaTarde=" + horaEntradaTarde +
                ", horaSalidaTarde=" + horaSalidaTarde +
                ", categoria=" + categoria +
                '}';
    }
}
