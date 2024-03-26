package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.ListaPrecioDetalle;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.entity.key.PkDocumentosLista;
import com.ar.vgmsistemas.entity.key.PkVenta;
import com.ar.vgmsistemas.repository.IDocumentosListaRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DocumentosListaBo {
    private static final String TAG = DocumentosListaBo.class.getCanonicalName();

    private final IDocumentosListaRepository _documentosListaRepository;

    public DocumentosListaBo(RepositoryFactory repoFactory) {
        this._documentosListaRepository = repoFactory.getDocumentosListaRepository();
    }

    public boolean isValid(String idDocumento, String idLetra, int idPtoVta, int idLista) throws Exception {
        PkVenta pkVenta = new PkVenta();
        pkVenta.setIdDocumento(idDocumento);
        pkVenta.setIdLetra(idLetra);
        pkVenta.setPuntoVenta(idPtoVta);
        PkDocumentosLista documentosLista = new PkDocumentosLista();
        documentosLista.setIdDocumento(idDocumento);
        documentosLista.setIdLetra(idLetra);
        documentosLista.setIdPtovta(idPtoVta);
        documentosLista.setIdLista(idLista);
        if (_documentosListaRepository.existsDocumentOnTable(pkVenta)) {
            return _documentosListaRepository.recoveryByID(documentosLista) != null;
        }
        return true;
    }

    /**
     * Verifica que el id_doc, id_leta, id_ptoVta exista en la tabla documentos_lista, si existe ve
     * si esta permitido o no (si existe) es decir le suma
     * a la consulta el id_lista, si existe una tupla entonces esta permitida la lista de precio.
     */
    public boolean isValid(PkDocumentosLista pk) throws Exception {
        PkVenta pkVenta = new PkVenta();
        pkVenta.setIdDocumento(pk.getIdDocumento());
        pkVenta.setIdLetra(pk.getIdLetra());
        pkVenta.setPuntoVenta(pk.getIdPtovta());
        if (_documentosListaRepository.existsDocumentOnTable(pkVenta)) {
            return _documentosListaRepository.recoveryByID(pk) != null;
        }

        return true;
    }

    /**
     * Devuelve una lista de booleanos que dice que posiciones no se deben permitir (en False)
     */
    public List<Boolean> getValidList(List<ListaPrecioDetalle> listaPrecioDetalles, PkVenta detalle) throws Exception {

        List<Boolean> listNoValid;
        if (_documentosListaRepository.existsDocumentOnTable(detalle)) {
            listNoValid = new ArrayList<>();
            int size = listaPrecioDetalles.size();
            for (int index = 0; index < size; index++) {
                PkDocumentosLista pk = new PkDocumentosLista();
                pk.setIdDocumento(detalle.getIdDocumento());
                pk.setIdLetra(detalle.getIdLetra());
                pk.setIdPtovta(detalle.getPuntoVenta());
                pk.setIdLista(listaPrecioDetalles.get(index).getListaPrecio().getId());
                listNoValid.add(isValid(pk));
            }
        } else {
            listNoValid = new ArrayList<>(Collections.nCopies(listaPrecioDetalles.size(), true));
        }
        return listNoValid;
    }

    public List<Boolean> getValidListCabecera(List<VentaDetalle> listDetalles, PkVenta pkVenta) throws Exception {
        List<Boolean> listNoValid = new ArrayList<>();
        int size = listDetalles.size();
        for (int index = 0; index < size; index++) {
            PkDocumentosLista pk = new PkDocumentosLista();
            pk.setIdDocumento(pkVenta.getIdDocumento());
            pk.setIdLetra(pkVenta.getIdLetra());
            pk.setIdPtovta(pkVenta.getPuntoVenta());
            pk.setIdLista(listDetalles.get(index).getListaPrecio().getId());
            listNoValid.add(isValid(pk));
        }
        return listNoValid;
    }

    public boolean isListTrue(List<Boolean> listBooleans) {
        for (Boolean val : listBooleans) {
            if (!val) return false;
        }
        return true;

    }
}
