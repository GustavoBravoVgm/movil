package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IDocumentoDao;
import com.ar.vgmsistemas.database.dao.entity.DocumentoBd;
import com.ar.vgmsistemas.entity.Documento;
import com.ar.vgmsistemas.entity.key.PkDocumento;
import com.ar.vgmsistemas.repository.IDocumentoRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.util.ArrayList;
import java.util.List;

public class DocumentoRepositoryImpl implements IDocumentoRepository {
    private IDocumentoDao _documentoDao;

    public DocumentoRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._documentoDao = db.documentoDao();
        }
    }

    public PkDocumento create(Documento documento) throws Exception {
        return null;
    }

    public List<String> recoveryTiposDocumentos(String tipoDocumento) throws Exception {
        return _documentoDao.recoveryTiposDocumentos(tipoDocumento, Documento.TODOS_INT);
    }

    public List<String> recoveryTiposDocumentos(String tipoDocumento, boolean soloAvion) throws Exception {
        int soloAvionInt = (soloAvion ? 1 : 0);
        return _documentoDao.recoveryTiposDocumentos(tipoDocumento, soloAvionInt);
    }

    public List<Integer> recoveryPuntosVenta(String tipoDocumento) throws Exception {
        return recoveryPuntosVenta(tipoDocumento, false);
    }

    public List<Integer> recoveryPuntosVenta(String idDocumento, boolean soloAvion) throws Exception {
        int soloAvionInt = (soloAvion ? 1 : 0);
        return _documentoDao.recoveryPuntosVenta(idDocumento, soloAvionInt);
    }

    public List<Documento> recoveryAll() throws Exception {
        return null;
    }

    public Documento recoveryByID(PkDocumento id) throws Exception {
        return mappingToDto(_documentoDao.recoveryByID(id.getIdDocumento(), id.getIdLetra(), id.getPuntoVenta()));
    }

    public void delete(Documento entity) throws Exception {
    }

    public void delete(PkDocumento id) throws Exception {
    }

    public void update(Documento documento) throws Exception {

    }

    public void updateNumeroDocumento(PkDocumento pkDocumento, long idNumeroDoc) throws Exception {
        _documentoDao.updateNumeroDocumento(pkDocumento.getIdDocumento(), pkDocumento.getIdLetra(),
                pkDocumento.getPuntoVenta(), idNumeroDoc);
    }

    public long recoveryNumeroDocumento(PkDocumento pkDocumento) throws Exception {
        return _documentoDao.recoveryNumeroDocumento(pkDocumento.getIdDocumento(), pkDocumento.getIdLetra(), pkDocumento.getPuntoVenta());
    }

    @Override
    public Documento recoveryDocReciboProvisorio() throws Exception {
        return mappingToDto(_documentoDao.recoveryDocReciboProvisorio());
    }

    @Override
    public Documento recoveryDocReciboProvisorio(long idPtoVta) throws Exception {
        return mappingToDto(_documentoDao.recoveryDocReciboProvisorio(idPtoVta));
    }

    public Documento recoveryDocumentoAnula(PkDocumento id) throws Exception {
        Documento documento = recoveryByID(id);
        PkDocumento pkDocAnula = new PkDocumento();
        pkDocAnula.setIdDocumento(documento.getIdDocAnulaFcNc());
        pkDocAnula.setIdLetra(documento.getIdDocAnulaTipoAB());
        pkDocAnula.setPuntoVenta(documento.getIdDocAnulaPtoVta());

        Documento documentoAnula = mappingToDto(_documentoDao.recoveryDocumentoAnula(pkDocAnula.getIdDocumento(),
                pkDocAnula.getIdLetra(), pkDocAnula.getPuntoVenta()));
        if (documentoAnula != null) {
            return documentoAnula;
        } else {
            throw new Exception("No esta configurado el documento que anula al documento: " + id.getIdDocumento() + " - " + id.getIdLetra() + " - " + id.getPuntoVenta());
        }

    }

    public Documento recoveryDocumentoAnulaMovil(PkDocumento id) throws Exception {

        Documento documentoAnula = recoveryDocumentoAnula(id);

        if (documentoAnula != null) {
            Documento documentoAnulaMovil = null;
            //Ahora busco el que genera ese documento, que sería el crédito móvil
            documentoAnulaMovil = mappingToDto(_documentoDao.recoveryDocumentoAnulaMovil(documentoAnula.getId().getIdDocumento(),
                    documentoAnula.getId().getIdLetra(), documentoAnula.getId().getPuntoVenta()));
            return documentoAnulaMovil;
        } else {
            throw new Exception("No esta configurado el documento de crédito móvil para el documento: " + id.getIdDocumento() + " - " + id.getIdLetra() + " - " + id.getPuntoVenta());
        }

    }

    @Override
    public List<Documento> recoveryDocumentosEgreso() throws Exception {
        List<DocumentoBd> listadoDocumentosBd = _documentoDao.recoveryDocumentosEgreso();
        List<Documento> documentos = new ArrayList<>();
        if (!listadoDocumentosBd.isEmpty()) {
            for (DocumentoBd item : listadoDocumentosBd) {
                documentos.add(mappingToDto(item));
            }
        }
        return documentos;
    }

    @Override
    public List<String> recoveryLetrasByIdFcnc(String idFcnc) throws Exception {
        return _documentoDao.recoveryLetrasByIdFcnc(idFcnc);
    }

    @Override
    public Documento recoveryDocumentoEgresoByIdfcndEIdLetra(String idFcnd, String idLetra) throws Exception {
        return mappingToDto(_documentoDao.recoveryDocumentoEgresoByIdfcndEIdLetra(idFcnd, idLetra));
    }

    @Override
    public Documento mappingToDto(DocumentoBd documentoBd) {
        Documento documento = new Documento();
        if (documentoBd != null) {
            PkDocumento id = new PkDocumento(documentoBd.getId().getIdDocumento(),
                    documentoBd.getId().getIdLetra(), documentoBd.getId().getPuntoVenta());

            documento.setUltimoNumeroDocumento(documentoBd.getUltimoNumeroDocumento());
            documento.setDescripcion(documentoBd.getDescripcion());
            documento.setTipoBlancoNegro(documentoBd.getTipoBlancoNegro());
            documento.setIsMovilVisible(documentoBd.getIsMovilVisible());
            documento.setTipoDocumento(documentoBd.getTipoDocumento());
            documento.setCategoriaPlanCuenta(documentoBd.getCategoriaPlanCuenta() == null
                    ? 0
                    : documentoBd.getCategoriaPlanCuenta());
            documento.setFuncionTipoDocumento(documentoBd.getFuncionTipoDocumento());
            documento.setSnEstadistica(documentoBd.getSnEstadistica());
            documento.setNumeroLineas(documentoBd.getNumeroLineas());
            documento.setIsGenerar(documentoBd.getIsGenerar());
            documento.setSnFacturaElectronica(Formatter.parseBooleanString(
                    documentoBd.getIsFacturaElectronica()));
            documento.setTiAplicaFacturaElectronica(documentoBd.getTiAplicaFacturaElectronica() == null
                    ? 1
                    : documentoBd.getTiAplicaFacturaElectronica());
            documento.setTiAplicaIva(documentoBd.getTiAplicaIva());
            documento.setSnImprimirImpMovil((documentoBd.getSnImprimirImpMovil() == null) ?
                    "S" : documentoBd.getSnImprimirImpMovil());
            documento.setIdDocAnulaFcNc(documentoBd.getIdDocAnulaFcNc());
            documento.setIdDocAnulaTipoAB(documentoBd.getIdDocAnulaTipoAB());
            documento.setIdDocAnulaPtoVta(documentoBd.getIdDocAnulaPtoVta() == null
                    ? 0
                    : documentoBd.getIdDocAnulaPtoVta());
            documento.setSnPedidoRentable((documentoBd.getSnPedidoRentable() == null) ?
                    "N" : documentoBd.getSnPedidoRentable());
            documento.setIdGenerarFcNc(documentoBd.getIdGenerarFcNc());
            documento.setIdGenerarTipoAB(documentoBd.getIdGenerarTipoAB());
            documento.setIdGenerarPtoVta(documentoBd.getIdGenerarPtoVta() == null
                    ? 0
                    : documentoBd.getIdGenerarPtoVta());
            documento.setSnAplicaDgr((documentoBd.getSnAplicaDgr() == null) ?
                    "S" : documentoBd.getSnAplicaDgr());
            documento.setIdListaDefault(documentoBd.getIdListaDefault());
            documento.setTiModifVisualMovil(documentoBd.getTiModifVisualMovil());
            documento.setSnVentaDirecta(documentoBd.getSnVentaDirecta());
            documento.setTiImpresionMovil(documentoBd.getTiImpresionMovil() == null ? 1 : documentoBd.getTiImpresionMovil());
        }
        return documento;
    }
}

