package com.ar.vgmsistemas.database.dao.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "rrhh")
public class RecursoHumanoBd implements Serializable {
    @ColumnInfo(name = "id_legajo")
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "de_apellido")
    private String apellido;

    @ColumnInfo(name = "de_nombres")
    private String nombre;

    @ColumnInfo(name = "de_domicilio")
    private String domicilio;

    @ColumnInfo(name = "nu_documento")
    private String numeroDocumento;

    @ColumnInfo(name = "de_password")
    private String password;

    @ColumnInfo(name = "fe_tp_dma")
    private String horaEntradaManana;

    @ColumnInfo(name = "fe_tp_hma")
    private String horaSalidaManana;

    @ColumnInfo(name = "fe_tp_dta")
    private String horaEntradaTarde;

    @ColumnInfo(name = "fe_tp_hta")
    private String horaSalidaTarde;

    @ColumnInfo(name = "sn_activo")
    private String snActivo;

    @ColumnInfo(name = "id_categoria")
    private int categoria;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHoraEntradaManana() {
        return horaEntradaManana;
    }

    public void setHoraEntradaManana(String horaEntradaManana) {
        this.horaEntradaManana = horaEntradaManana;
    }

    public String getHoraSalidaManana() {
        return horaSalidaManana;
    }

    public void setHoraSalidaManana(String horaSalidaManana) {
        this.horaSalidaManana = horaSalidaManana;
    }

    public String getHoraEntradaTarde() {
        return horaEntradaTarde;
    }

    public void setHoraEntradaTarde(String horaEntradaTarde) {
        this.horaEntradaTarde = horaEntradaTarde;
    }

    public String getHoraSalidaTarde() {
        return horaSalidaTarde;
    }

    public void setHoraSalidaTarde(String horaSalidaTarde) {
        this.horaSalidaTarde = horaSalidaTarde;
    }

    public String getSnActivo() {
        return snActivo;
    }

    public void setSnActivo(String snActivo) {
        this.snActivo = snActivo;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
}
