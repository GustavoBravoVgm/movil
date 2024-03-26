package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.ListaPrecio;
import com.ar.vgmsistemas.entity.ListaPrecioDetalle;
import com.ar.vgmsistemas.entity.Subrubro;
import com.ar.vgmsistemas.entity.key.PkListaPrecioDetalle;
import com.ar.vgmsistemas.repository.IListaPrecioDetalleRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class ListaPrecioDetalleBo {
    private final IListaPrecioDetalleRepository _listaPrecioDetalleRepository;

    private final ArticuloBo _articuloBo;

    public ListaPrecioDetalleBo(RepositoryFactory repoFactory) {
        this._listaPrecioDetalleRepository = repoFactory.getListaPrecioDetalleRepository();
        this._articuloBo = new ArticuloBo(repoFactory);
    }

    public List<ListaPrecioDetalle> recoveryAll() throws Exception {
        return _listaPrecioDetalleRepository.recoveryAll();
    }

    public ListaPrecioDetalle recoveryById(int idArticulo, int idLista) throws Exception {
        return _listaPrecioDetalleRepository.recoveryById(idArticulo, idLista, 0, 0);
    }

    public List<ListaPrecioDetalle> recoveryByListaPrecio(ListaPrecio listaPrecio, Integer idProveedor) throws Exception {
        return _listaPrecioDetalleRepository.recoveryByListaPrecio(listaPrecio, idProveedor);
    }

    public List<ListaPrecioDetalle> recoveryByListaPrecio(ListaPrecio listaPrecio) throws Exception {
        return _listaPrecioDetalleRepository.recoveryByListaPrecio(listaPrecio, null);
    }

    public ListaPrecioDetalle recoveryById(PkListaPrecioDetalle id) throws Exception {
        return _listaPrecioDetalleRepository.recoveryByID(id);
    }

    public List<ListaPrecioDetalle> recoveryByArticulo(int idArticulo) throws Exception {
        return _listaPrecioDetalleRepository.recoveryByArticulo(idArticulo);
    }

    public List<ListaPrecioDetalle> recoveryByArticuloSubrubro(ListaPrecio listaPrecio, Subrubro subrubro, Integer idProveedor) throws Exception {
        return _listaPrecioDetalleRepository.recoveryByListaPrecioSubrubro(listaPrecio, subrubro, idProveedor);
    }

    public List<ListaPrecioDetalle> recoveryByArticuloAndLista(int idLista, int idArticulo) throws Exception {
        return _listaPrecioDetalleRepository.recoveryByArticuloAndLista(idLista, idArticulo);
    }

    public List<ListaPrecioDetalle> recoveryPromocionesYCombos() throws Exception {
        List<ListaPrecioDetalle> listaPrecios = _listaPrecioDetalleRepository.recoveryPromocionesYCombos();
        for (ListaPrecioDetalle lpd : listaPrecios) {
            if (lpd.getArticulo() == null) {
                Articulo articulo = _articuloBo.recoveryById(lpd.getId().getIdArticulo());
                lpd.setArticulo(articulo);
            }
        }
        return listaPrecios;
    }

    public static boolean havePrice(ListaPrecioDetalle detalle) {
        boolean havePrice;
        havePrice = detalle.getPrecioFinal() != 0;
        return havePrice;

    }
}
