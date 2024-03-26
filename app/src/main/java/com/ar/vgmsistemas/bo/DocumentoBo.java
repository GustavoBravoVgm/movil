package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Documento;
import com.ar.vgmsistemas.entity.key.PkDocumento;
import com.ar.vgmsistemas.repository.IDocumentoRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;


public class DocumentoBo {

    private final IDocumentoRepository _documentoRepository;

    public DocumentoBo(RepositoryFactory repoFactory) {
        _documentoRepository = repoFactory.getDocumentoRepository();
    }

    public List<String> recoveryTiposDocumentos(String tipoDocumento) throws Exception {
        return _documentoRepository.recoveryTiposDocumentos(tipoDocumento);
    }

    public List<String> recoveryTiposDocumentos(String tipoDocumento, boolean soloAvion) throws Exception {
        return _documentoRepository.recoveryTiposDocumentos(tipoDocumento, soloAvion);
    }

    public List<Integer> recoveryPuntosVenta(String tipoDocumento) throws Exception {
        return _documentoRepository.recoveryPuntosVenta(tipoDocumento);
    }

    public List<Integer> recoveryPuntosVenta(String tipoDocumento, boolean soloAvion) throws Exception {
        return _documentoRepository.recoveryPuntosVenta(tipoDocumento, soloAvion);
    }

    public List<Documento> recoveryAll() throws Exception {
        return _documentoRepository.recoveryAll();
    }

    public List<Documento> recoveryDocumentosEgreso() throws Exception {
        return _documentoRepository.recoveryDocumentosEgreso();
    }

    /**
     * @return la numeración actual del recibo provisorio
     * @throws Exception
     */
    public long recoveryNumeroReciboProv() throws Exception {
        Documento documento = recoveryDocumentoProv();
        return recoveryNumeroDocumento(documento.getId().getIdDocumento(), documento.getId().getIdLetra(), documento.getId().getPuntoVenta());
    }

    public Documento recoveryDocumentoProv() throws Exception {
        return _documentoRepository.recoveryDocReciboProvisorio();
    }

    public long recoveryNumeroReciboProv(long idPtoVta) throws Exception {
        Documento documento = recoveryDocumentoProv(idPtoVta);
        return recoveryNumeroDocumento(documento.getId().getIdDocumento(), documento.getId().getIdLetra(), documento.getId().getPuntoVenta());
    }

    public Documento recoveryDocumentoProv(long idPtoVta) throws Exception {
        return _documentoRepository.recoveryDocReciboProvisorio(idPtoVta);
    }

    public long recoveryNumeroDocumento(String idDocumento, String idLetra, int puntoVenta) throws Exception {
        PkDocumento idDoc = new PkDocumento();
        idDoc.setIdDocumento(idDocumento);
        idDoc.setIdLetra(idLetra);
        idDoc.setPuntoVenta(puntoVenta);
        return _documentoRepository.recoveryNumeroDocumento(idDoc);
    }

    public Documento recoveryById(PkDocumento id) throws Exception {
        return _documentoRepository.recoveryByID(id);
    }

    public Documento recoveryById(String idDocumento, String idLetra, int puntoVenta) throws Exception {
        PkDocumento documento = new PkDocumento();
        documento.setIdDocumento(idDocumento);
        documento.setIdLetra(idLetra);
        documento.setPuntoVenta(puntoVenta);

        return recoveryById(documento);
    }

    public void updateDocumento(Documento documento, long idDoc) throws Exception {
        _documentoRepository.updateNumeroDocumento(documento.getId(), idDoc);
    }

    public Documento recoveryDocumentoEgresoByIdfcndEIdLetra(String idFcnd, String idLetra) throws Exception {
        return _documentoRepository.recoveryDocumentoEgresoByIdfcndEIdLetra(idFcnd, idLetra);
    }

    public List<String> getLetrasByIdFcnc(String idFcnc) throws Exception {
        return _documentoRepository.recoveryLetrasByIdFcnc(idFcnc);
    }
}
