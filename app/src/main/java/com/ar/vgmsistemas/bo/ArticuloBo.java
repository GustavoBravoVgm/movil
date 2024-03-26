package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.ClienteVendedor;
import com.ar.vgmsistemas.entity.ListaPrecio;
import com.ar.vgmsistemas.entity.ListaPrecioDetalle;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.entity.key.PkClienteVendedor;
import com.ar.vgmsistemas.repository.IArticuloRepository;
import com.ar.vgmsistemas.repository.IListaPrecioDetalleRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.ArrayList;
import java.util.List;

public class ArticuloBo {
    private static List<Articulo> _articulos;
    private final IListaPrecioDetalleRepository _listaPrecioDetalleRepository;
    private final IArticuloRepository _articuloRepository;

    //BO´s
    private final ClienteVendedorBo _clienteVendedorBo;

    public ArticuloBo(RepositoryFactory repoFactory) {
        this._articuloRepository = repoFactory.getArticuloRepository();
        this._listaPrecioDetalleRepository = repoFactory.getListaPrecioDetalleRepository();
        this._clienteVendedorBo = new ClienteVendedorBo(repoFactory);
    }

    public void recoveryListaPrecio(Articulo articulo) throws Exception {
        List<ListaPrecioDetalle> listaPrecios = _listaPrecioDetalleRepository.recoveryByArticulo(articulo.getId());
        articulo.setListaPreciosDetalle(listaPrecios);
    }

    public List<ListaPrecioDetalle> recoveryListaPrecioToMobile(Articulo articulo) throws Exception {
        return _listaPrecioDetalleRepository.recoveryByArticuloMovil(articulo.getId());
    }


    public void actualizarStock(Articulo articulo, double stock) throws Exception {
        _articuloRepository.updateStock(articulo, stock);
    }

    public Articulo recoveryById(Integer id) throws Exception {
        return _articuloRepository.recoveryByID(id);
    }

    public Articulo recoveryByIdWithClass(Integer id) throws Exception {
        return _articuloRepository.recoveryByID(id);
    }

    public List<Articulo> recoveryAll() throws Exception {
        _articulos = _articuloRepository.recoveryAll();
        return _articulos;
    }

    public List<Articulo> recoveryAllWithClass() throws Exception {
        _articulos = _articuloRepository.recoveryAll();
        return _articulos;
    }

    public List<Articulo> recoveryAllByCliente(Cliente mCliente, int idVendedor) throws Exception {
        PkClienteVendedor idVendComercio = new PkClienteVendedor();
        idVendComercio.setIdVendedor(idVendedor);
        idVendComercio.setIdSucursal(mCliente.getId().getIdSucursal());
        idVendComercio.setIdCliente(mCliente.getId().getIdCliente());
        idVendComercio.setIdComercio(mCliente.getId().getIdComercio());
        ClienteVendedor mVendComercio = _clienteVendedorBo.recoveryByID(idVendComercio);
        if (mVendComercio != null && mVendComercio.getSnFiltroArticulo().equalsIgnoreCase("S")) {
            if (mVendComercio.getTiFiltroArticuloSinc().equalsIgnoreCase("PR")) {
                _articulos = _articuloRepository.recoveryAllByCliente(mCliente);
            } else {
                return null;
            }
        } else {
            return null;
        }
        return _articulos;
    }

    public Articulo recoveryByIdSinCruce(Integer id) throws Exception {
        return _articuloRepository.recoveryByID(id);
    }

    public List<Articulo> getCantidadAcumuladaPorCliente(Cliente cliente) throws Exception {
        return _articuloRepository.recoveryCantidadesAcumuladasPorCliente(cliente);
    }

    public List<Articulo> getCantidadPorCliente(Cliente cliente) throws Exception {
        return _articuloRepository.recoveryCantidadesPorCliente(cliente);
    }

    /**
     * @param articulos the articulos to set
     */
    public void setArticulos(List<Articulo> articulos) {
        _articulos = articulos;
    }

    public double getPrecio(ListaPrecio _listaPrecioXDefecto, Articulo articulo) throws Exception {
        double precio = 0d;
        this.recoveryListaPrecio(articulo);
        for (ListaPrecioDetalle listaPrecioDetalle : articulo.getListaPreciosDetalle()) {
            if (listaPrecioDetalle.getListaPrecio().getId() == _listaPrecioXDefecto.getId()) {
                precio = listaPrecioDetalle.getPrecioFinal();
            }
        }
        return precio;
    }

    public List<Articulo> recoveryAll(ListaPrecio listaPrecioXDefecto) throws Exception {
        _articulos = _articuloRepository.recoveryAll(listaPrecioXDefecto);
        return _articulos;
    }

    public List<ListaPrecioDetalle> filtrarPorSubrubro(List<ListaPrecioDetalle> listasPrecioDetalle,
                                                       int idSubrubro) throws Exception {
        List<ListaPrecioDetalle> listasPrecioFiltradas = new ArrayList<ListaPrecioDetalle>();
        //Recorro la lista y controlo que el articulo de cada lista este en el subrubro ingresado
        for (int i = 0; i < listasPrecioDetalle.size(); i++) {
            int idArticulo = listasPrecioDetalle.get(i).getArticulo().getId();
            if (_articuloRepository.isArticuloInSubrubro(idArticulo, idSubrubro)) {
                //Como el articulo pertenece al rubro, lo agrego a la lista
                listasPrecioFiltradas.add(listasPrecioDetalle.get(i));
            }
        }
        return listasPrecioFiltradas;
    }

    public boolean articuloInSubrubro(Articulo articulo, int idSubrubro) throws Exception {
        return _articuloRepository.isArticuloInSubrubro(articulo.getId(), idSubrubro);
    }

    public double getSubtotal(VentaDetalle detalle) {
        double cantidad = detalle.getCantidad();
        double precioUnitarioSinIva = detalle.getPrecioUnitarioSinIva();
        double tasaImpuestoInterno = detalle.getArticulo().getTasaImpuestoInterno();
        double impuestoInterno = precioUnitarioSinIva * tasaImpuestoInterno;
        double tasaIva = detalle.getArticulo().getTasaIva();
        double precioIvaUnitario = precioUnitarioSinIva * tasaIva;
        double precioUnitarioConIva = precioUnitarioSinIva + precioIvaUnitario;
        double precioFinal = precioUnitarioConIva + impuestoInterno;
        return (cantidad * (precioFinal));
    }

    public static double getCantidad(double unidadesXBulto, int bultos, double unidades) {
        return bultos * unidadesXBulto + unidades;
    }
}
