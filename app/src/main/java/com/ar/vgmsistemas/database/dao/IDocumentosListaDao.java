package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.ar.vgmsistemas.database.dao.entity.DocumentosListaBd;

import java.util.List;

@Dao
public interface IDocumentosListaDao {
    @Query(value = "SELECT documentos_lista.* " +
            "FROM   documentos_lista " +
            "WHERE  documentos_lista.id_doc = :idDocumento AND " +
            "       documentos_lista.id_letra = :idLetra AND " +
            "       documentos_lista.id_ptovta = :idPtovta AND " +
            "       documentos_lista.id_lista = :idLista")
    DocumentosListaBd recoveryByID(String idDocumento, String idLetra, int idPtovta, int idLista) throws Exception;

    @Query(value = "SELECT documentos_lista.* " +
            "FROM   documentos_lista ")
    List<DocumentosListaBd> recoveryAll() throws Exception;

    @Query(value = "SELECT COUNT(*) " +
            "FROM   documentos_lista " +
            "WHERE  documentos_lista.id_doc = :idDocumento AND " +
            "       documentos_lista.id_letra = :idLetra AND " +
            "       documentos_lista.id_ptovta = :puntoVenta")
    int existsDocumentOnTable(String idDocumento, String idLetra, int puntoVenta) throws Exception;
}
