package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.DocumentoBd;
import com.ar.vgmsistemas.entity.Documento;
import com.ar.vgmsistemas.entity.key.PkDocumento;

import java.util.List;


public interface IDocumentoRepository extends IGenericRepository<Documento, PkDocumento> {

    List<String> recoveryTiposDocumentos(String tipoDocumento) throws Exception;

    List<String> recoveryTiposDocumentos(String tipoDocumento, boolean soloAvion) throws Exception;

    List<Integer> recoveryPuntosVenta(String tipoDocumento) throws Exception;

    List<Integer> recoveryPuntosVenta(String tipoDocumento, boolean soloAvion) throws Exception;

    Documento recoveryDocReciboProvisorio() throws Exception;

    Documento recoveryDocReciboProvisorio(long idPtoVta) throws Exception;

    Documento recoveryDocumentoAnula(PkDocumento pkDocumento) throws Exception;

    List<Documento> recoveryDocumentosEgreso() throws Exception;

    Documento recoveryDocumentoEgresoByIdfcndEIdLetra(String idFcnd, String idLetra) throws Exception;

    List<String> recoveryLetrasByIdFcnc(String idFcnc) throws Exception;

    Documento recoveryDocumentoAnulaMovil(PkDocumento id) throws Exception;

    long recoveryNumeroDocumento(PkDocumento pkDocumento) throws Exception;

    void updateNumeroDocumento(PkDocumento pkDocumento, long idNumeroDoc) throws Exception;

    Documento mappingToDto(DocumentoBd documentoBd);
}
