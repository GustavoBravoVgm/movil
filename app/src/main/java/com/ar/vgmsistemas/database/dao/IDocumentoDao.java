package com.ar.vgmsistemas.database.dao;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import com.ar.vgmsistemas.database.dao.entity.DocumentoBd;

import java.util.List;

@Dao
public interface IDocumentoDao {

    @Query(value = "SELECT DISTINCT(documentos.id_doc) AS id_doc " +
            "FROM   documentos " +
            "WHERE  documentos.sn_movil = 'S' AND " +
            "       documentos.ti_documento = :tipoDocumento AND " +
            "       ((:tipoDocumento = 'V' AND documentos.id_funcion = 1) OR :tipoDocumento <> 'V') AND" +
            "       ((:soloAvion = 1 AND documentos.ti_bn = 'N') OR :soloAvion = 0)")
    List<String> recoveryTiposDocumentos(String tipoDocumento, int soloAvion) throws Exception;//soloAvion:0 es false y 1 es true

    @Query(value = "SELECT DISTINCT(documentos.id_ptovta) AS id_ptovta " +
            "FROM   documentos " +
            "WHERE  documentos.sn_movil = 'S' AND " +
            "       documentos.id_doc = :idDocumento AND " +
            "       ((:soloAvion = 1 AND documentos.ti_bn = 'N') OR :soloAvion = 0)")
    List<Integer> recoveryPuntosVenta(String idDocumento, int soloAvion) throws Exception;//soloAvion:0 es false y 1 es true

    @Query(value = "SELECT documentos.* " +
            "FROM   documentos " +
            "WHERE  documentos.id_doc = :idDocumento AND " +
            "       documentos.id_letra = :idLetra AND " +
            "       documentos.id_ptovta = :puntoVenta")
    DocumentoBd recoveryByID(String idDocumento, String idLetra, int puntoVenta) throws Exception;

    @Update
    void updateNumeroDocumento(DocumentoBd... documentos) throws Exception;

    @Query(value = "UPDATE documentos " +
            "SET    id_numero = :idNumeroDoc " +
            "WHERE  documentos.id_doc = :idDocumento AND " +
            "       documentos.id_letra = :idLetra AND " +
            "       documentos.id_ptovta = :puntoVenta")
    void updateNumeroDocumento(String idDocumento, String idLetra, int puntoVenta, long idNumeroDoc) throws Exception;

    @Query(value = "SELECT (IFNULL(documentos.id_numero,0) + 1) AS id_numero " +
            "FROM   documentos " +
            "WHERE  documentos.id_doc = :idDocumento AND " +
            "       documentos.id_letra = :idLetra AND " +
            "       documentos.id_ptovta = :puntoVenta")
    long recoveryNumeroDocumento(String idDocumento, String idLetra, int puntoVenta) throws Exception;

    @Query(value = "SELECT documentos.* " +
            "FROM   documentos " +
            "WHERE  documentos.ti_documento = 'R' " +
            "LIMIT 1 ")
    DocumentoBd recoveryDocReciboProvisorio() throws Exception;

    @Query(value = "SELECT documentos.* " +
            "FROM   documentos " +
            "WHERE  documentos.ti_documento = 'R' AND " +
            "       documentos.id_ptovta = :idPtoVta ")
    DocumentoBd recoveryDocReciboProvisorio(long idPtoVta) throws Exception;

    @Query(value = "SELECT documentos.* " +
            "FROM   documentos " +
            "WHERE  EXISTS (SELECT 1 FROM documentos as doc " +
            "               WHERE   documentos.id_doc = doc.id_docanula_fcnc AND " +
            "                       documentos.id_letra = doc.id_docanula_tipoab AND " +
            "                       documentos.id_ptovta = doc.id_docanula_ptovta AND " +
            "                       doc.id_doc = :idDocumento AND " +
            "                       doc.id_letra = :idLetra AND " +
            "                       doc.id_ptovta = :puntoVenta ) ")
    DocumentoBd recoveryDocumentoAnula(String idDocumento, String idLetra, int puntoVenta) throws Exception;

    @Query(value = "SELECT documentos.* " +
            "FROM   documentos " +
            "WHERE  EXISTS (SELECT 1 " +
            "               FROM    documentos AS docFC " +
            "                       INNER JOIN documentos AS docNC " +
            "                           ON  docFC.id_docanula_fcnc = docNC.id_doc AND " +
            "                               docFC.id_docanula_tipoab = docNC.id_letra AND " +
            "                               docFC.id_docanula_ptovta= docNC.id_ptovta " +
            "               WHERE   docNC.id_doc = documentos.id_generar_fcnc AND " +  //Ahora busco el que genera ese documento, que sería el credito móvil
            "                       docNC.id_letra = documentos.id_generar_tipoab AND " +
            "                       docNC.id_ptovta = documentos.id_ptovta AND " +
            "                       docFC.id_doc = :idDocumento AND " +
            "                       docFC.id_letra = :idLetra AND " +
            "                       docFC.id_ptovta = :puntoVenta) ")
    DocumentoBd recoveryDocumentoAnulaMovil(String idDocumento, String idLetra, int puntoVenta) throws Exception;

    @Query(value = "SELECT documentos.* " +
            "FROM   documentos " +
            "WHERE  documentos.ti_documento = 'A' " +
            "GROUP BY documentos.id_doc")
    List<DocumentoBd> recoveryDocumentosEgreso() throws Exception;

    @Query(value = "SELECT documentos.id_letra " +
            "FROM   documentos " +
            "WHERE  documentos.id_doc = :idFcnc " +
            "GROUP BY documentos.id_letra")
    List<String> recoveryLetrasByIdFcnc(String idFcnc) throws Exception;

    @Query(value = "SELECT documentos.* " +
            "FROM   documentos " +
            "WHERE  documentos.ti_documento = 'A' AND " +
            "       documentos.id_doc = :idFcnd AND " +
            "       documentos.id_letra = :idLetra")
    DocumentoBd recoveryDocumentoEgresoByIdfcndEIdLetra(String idFcnd, String idLetra) throws Exception;
}
