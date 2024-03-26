package com.ar.vgmsistemas.database.dao.entity;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;

import com.ar.vgmsistemas.database.dao.entity.key.PkDocumentosListaBd;

@Entity(tableName = "documentos_lista",
        primaryKeys = {"id_doc", "id_letra", "id_ptovta", "id_lista"})
public class DocumentosListaBd {

    @NonNull
    @Embedded
    private PkDocumentosListaBd id;

    public PkDocumentosListaBd getId() {
        return id;
    }

    public void setId(PkDocumentosListaBd id) {
        this.id = id;
    }

}
