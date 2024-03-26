package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.DocumentosListaBd;
import com.ar.vgmsistemas.entity.DocumentosLista;
import com.ar.vgmsistemas.entity.key.PkDocumentosLista;
import com.ar.vgmsistemas.entity.key.PkVenta;

public interface IDocumentosListaRepository extends IGenericRepository<DocumentosLista, Long> {

    public DocumentosLista recoveryByID(PkDocumentosLista pk) throws Exception;

    public boolean existsDocumentOnTable(PkVenta pkVenta) throws Exception;

    DocumentosLista mappingToDto(DocumentosListaBd documentosListaBd);
}
