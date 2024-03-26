package com.ar.vgmsistemas.entity;

import com.ar.vgmsistemas.utils.Formatter;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

public class AuditoriaGps implements Serializable {

    private static final long serialVersionUID = -8930011811769915730L;

    public static final String TABLE = "auditoria_gps";

    private long idAuditoria;

    private Integer idVendedor;

    private Date feRegistroMovil;

    private Date feRegistroServidor;

    private String tiAccion;

    private String idMovil;

    private String feRegistroMovilWs;

    public static final String TI_ACCION_ENCIENDE = "E";

    public static final String TI_ACCION_APAGA = "A";

    public long getIdAuditoria() {
        return idAuditoria;
    }

    public void setIdAuditoria(long idAuditoria) {
        this.idAuditoria = idAuditoria;
    }

    public Date getFeRegistroMovil() {
        return feRegistroMovil;
    }

    public void setFeRegistroMovil(Date feRegistroMovil) {
        this.feRegistroMovil = feRegistroMovil;
        try {
            this.setFeRegistroMovilWs(Formatter.formatDateWs(feRegistroMovil));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
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

    public String getFeRegistroMovilWs() {
        return feRegistroMovilWs;
    }

    public void setFeRegistroMovilWs(String feRegistroMovilWs) {

        this.feRegistroMovilWs = feRegistroMovilWs;
    }

    public Date getFeRegistroServidor() {
        return feRegistroServidor;
    }

    public void setFeRegistroServidor(Date feRegistroServidor) {
        this.feRegistroServidor = feRegistroServidor;
    }

    public AuditoriaGps() {
    }

    public AuditoriaGps(long idAuditoria, Integer idVendedor, Date feRegistroMovil,
                        Date feRegistroServidor, String tiAccion, String idMovil,
                        String feRegistroMovilWs) {
        this.idAuditoria = idAuditoria;
        this.idVendedor = idVendedor;
        this.feRegistroMovil = feRegistroMovil;
        this.feRegistroServidor = feRegistroServidor;
        this.tiAccion = tiAccion;
        this.idMovil = idMovil;
        this.feRegistroMovilWs = feRegistroMovilWs;
    }
}
