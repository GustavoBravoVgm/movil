package com.ar.vgmsistemas.entity.key;

import java.io.Serializable;

public class PkDocumentosLista implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idDocumento;

    private String idLetra;

    private int idPtovta;

    private int idLista;

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getIdLetra() {
        return idLetra;
    }

    public void setIdLetra(String idLetra) {
        this.idLetra = idLetra;
    }

    public int getIdPtovta() {
        return idPtovta;
    }

    public void setIdPtovta(int idPtovta) {
        this.idPtovta = idPtovta;
    }

    public int getIdLista() {
        return idLista;
    }

    public void setIdLista(int idLista) {
        this.idLista = idLista;
    }


}
