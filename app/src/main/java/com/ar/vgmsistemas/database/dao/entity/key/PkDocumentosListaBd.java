package com.ar.vgmsistemas.database.dao.entity.key;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import java.io.Serializable;

public class PkDocumentosListaBd implements Serializable {
    @ColumnInfo(name = "id_doc")
    @NonNull
    private String idDocumento;

    @ColumnInfo(name = "id_letra")
    @NonNull
    private String idLetra;

    @ColumnInfo(name = "id_ptovta")
    private int idPtovta;

    @ColumnInfo(name = "id_lista")
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
