package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IDocumentosListaDao;
import com.ar.vgmsistemas.database.dao.entity.DocumentosListaBd;
import com.ar.vgmsistemas.entity.DocumentosLista;
import com.ar.vgmsistemas.entity.key.PkDocumentosLista;
import com.ar.vgmsistemas.entity.key.PkVenta;
import com.ar.vgmsistemas.repository.IDocumentosListaRepository;

import java.util.ArrayList;
import java.util.List;

public class DocumentosListaRepositoryImpl implements IDocumentosListaRepository {

    private IDocumentosListaDao _documentosListaDao;

    public DocumentosListaRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._documentosListaDao = db.documentosListaDao();
        }
    }


    @Override
    public Long create(DocumentosLista entity) throws Exception {
        return null;
    }

    @Override
    public DocumentosLista recoveryByID(Long id) throws Exception {
        return null;
    }


    @Override
    public List<DocumentosLista> recoveryAll() throws Exception {
        List<DocumentosListaBd> listadoDocumentosBd = _documentosListaDao.recoveryAll();
        List<DocumentosLista> documentos = new ArrayList<>();
        if (!listadoDocumentosBd.isEmpty()) {
            for (DocumentosListaBd item : listadoDocumentosBd) {
                documentos.add(mappingToDto(item));
            }
        }
        return documentos;
    }

    @Override
    public void update(DocumentosLista entity) throws Exception {

    }

    @Override
    public void delete(DocumentosLista entity) throws Exception {

    }

    @Override
    public void delete(Long id) throws Exception {

    }


    @Override
    public DocumentosLista recoveryByID(PkDocumentosLista pk) throws Exception {
        return mappingToDto(_documentosListaDao.recoveryByID(pk.getIdDocumento(), pk.getIdLetra(),
                pk.getIdPtovta(), pk.getIdLista()));
    }

    @Override
    public boolean existsDocumentOnTable(PkVenta pkVenta) throws Exception {
        return _documentosListaDao.existsDocumentOnTable(pkVenta.getIdDocumento(),
                pkVenta.getIdLetra(), pkVenta.getPuntoVenta()) > 0;
    }

    @Override
    public DocumentosLista mappingToDto(DocumentosListaBd documentosListaBd) {
        DocumentosLista documentosLista = new DocumentosLista();
        if (documentosListaBd != null) {
            PkDocumentosLista id = new PkDocumentosLista();
            id.setIdDocumento(documentosListaBd.getId().getIdDocumento());
            id.setIdLetra(documentosListaBd.getId().getIdLetra());
            id.setIdPtovta(documentosListaBd.getId().getIdPtovta());
            id.setIdLista(documentosListaBd.getId().getIdLista());
            documentosLista.setId(id);
        }
        return documentosLista;
    }
}
