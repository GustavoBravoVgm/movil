package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IVentaDetalleDao;
import com.ar.vgmsistemas.database.dao.entity.LineaIntegradoMercaderiaBd;
import com.ar.vgmsistemas.database.dao.entity.VentaDetalleBd;
import com.ar.vgmsistemas.database.dao.entity.key.PkVentaDetalleBd;
import com.ar.vgmsistemas.entity.LineaIntegradoMercaderia;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.entity.key.PkVenta;
import com.ar.vgmsistemas.entity.key.PkVentaDetalle;
import com.ar.vgmsistemas.repository.IVentaDetalleRepository;
import com.ar.vgmsistemas.utils.Formatter;

import java.util.ArrayList;
import java.util.List;

public class VentaDetalleRepositoryImpl implements IVentaDetalleRepository {
    private final AppDataBase _db;

    private IVentaDetalleDao _ventaDetalleDao;
    private LineaIntegradoMercaderiaRepositoryImpl _lineaIntegradoMercaderiaRepository;

    public VentaDetalleRepositoryImpl(AppDataBase db) {
        this._db = db;
        if (db != null) {
            this._ventaDetalleDao = db.ventaDetalleDao();
            this._lineaIntegradoMercaderiaRepository = new LineaIntegradoMercaderiaRepositoryImpl(_db);
        }
    }

    public PkVentaDetalle create(VentaDetalle ventaDetalle) throws Exception {
        ventaDetalle.setAnulo("N");
        _ventaDetalleDao.create(mappingToDb(ventaDetalle));
        return ventaDetalle.getId();
    }


    public long recoveryIdSecuencia(PkVenta id) throws Exception {
        return _ventaDetalleDao.recoveryIdSecuencia(id.getIdDocumento(), id.getIdLetra(), id.getPuntoVenta(),
                id.getIdNumeroDocumento());
    }


    public List<VentaDetalle> recoveryByVenta(Venta venta) throws Exception {
        List<VentaDetalleBd> listadoVentasDetalle = _ventaDetalleDao.recoveryByVenta(venta.getId().getIdDocumento(),
                venta.getId().getIdLetra(), venta.getId().getPuntoVenta(),
                venta.getId().getIdNumeroDocumento());
        List<VentaDetalle> detalles = new ArrayList<>();
        if (!listadoVentasDetalle.isEmpty()) {
            for (VentaDetalleBd item : listadoVentasDetalle) {
                detalles.add(mappingToDto(item));
            }
        }
        return detalles;
    }

    public List<List<VentaDetalle>> recoveryByVenta(List<Venta> ventas) throws Exception {
        List<List<VentaDetalle>> ventasDetalle = new ArrayList<>();
        for (int i = 0; i < ventas.size(); i++) {
            ventasDetalle.add(recoveryByVenta(ventas.get(i)));
        }
        return ventasDetalle;
    }

    /**
     * Actualiza el campo sn_anulo en S
     */
    public void delete(VentaDetalle ventaDetalle) throws Exception {
        PkVentaDetalle id = ventaDetalle.getId();
        _ventaDetalleDao.delete(id.getIdDocumento(), id.getIdLetra(), id.getPuntoVenta(),
                id.getIdNumeroDocumento(), id.getSecuencia());
    }

    public void anularVentasDetalleByVenta(Venta venta) throws Exception {
        PkVenta id = venta.getId();
        _ventaDetalleDao.anularVentasDetalleByVenta(id.getIdDocumento(), id.getIdLetra(),
                id.getPuntoVenta(), id.getIdNumeroDocumento());
    }

    public void deleteVentasDetalleByVenta(Venta venta) throws Exception {
        PkVenta id = venta.getId();
        _ventaDetalleDao.deleteVentasDetalleByVenta(id.getIdDocumento(), id.getIdLetra(),
                id.getPuntoVenta(), id.getIdNumeroDocumento());
    }

    public void delete(PkVentaDetalle id) throws Exception {

    }

    public List<VentaDetalle> recoveryAll() throws Exception {
        return null;
    }


    public VentaDetalle recoveryByID(PkVentaDetalle id) throws Exception {
        return null;
    }

    public void update(VentaDetalle entity) throws Exception {

    }

    public void updateXDevolucion(VentaDetalle ventaDetalle) throws Exception {
        PkVentaDetalle id = ventaDetalle.getId();
        _ventaDetalleDao.updateXDevolucion(id.getIdDocumento(), id.getIdLetra(),
                id.getPuntoVenta(), id.getIdNumeroDocumento(), id.getSecuencia(),
                ventaDetalle.getUnidadesDevueltas(), ventaDetalle.getBultosDevueltos());
    }

    @Override
    public List<LineaIntegradoMercaderia> getLineasIntegrado(int ti_empresa, String documento) throws Exception {
        List<LineaIntegradoMercaderiaBd> listadoLineasIntegradoBd = _ventaDetalleDao.getLineasIntegrado(ti_empresa, documento);
        List<LineaIntegradoMercaderia> lineas = new ArrayList<>();
        if (!listadoLineasIntegradoBd.isEmpty()) {
            for (LineaIntegradoMercaderiaBd item : listadoLineasIntegradoBd) {
                lineas.add(_lineaIntegradoMercaderiaRepository.mappingToDto(item));
            }
        }
        return lineas;
    }

    public List<VentaDetalle> recoveryArtComponentesCombo(VentaDetalle ventaDetalle) throws Exception {
        PkVentaDetalle id = ventaDetalle.getId();
        List<VentaDetalleBd> detallesEnBd = _ventaDetalleDao.recoveryArtComponentesCombo(
                ventaDetalle.getArticulo().getId(), id.getIdDocumento(), id.getIdLetra(),
                id.getPuntoVenta(), id.getIdNumeroDocumento());
        List<VentaDetalle> lineas = new ArrayList<>();
        if (!detallesEnBd.isEmpty()) {
            for (VentaDetalleBd item : detallesEnBd) {
                lineas.add(mappingToDto(item));
            }
        }
        return lineas;
    }

    public VentaDetalle mappingToDto(VentaDetalleBd ventaDetalleBd) throws Exception {
        VentaDetalle ventaDetalle = new VentaDetalle();
        if (ventaDetalleBd != null) {
            PkVentaDetalle id = new PkVentaDetalle(ventaDetalleBd.getId().getIdDocumento(),
                    ventaDetalleBd.getId().getIdLetra(), ventaDetalleBd.getId().getPuntoVenta(),
                    ventaDetalleBd.getId().getIdNumeroDocumento(), ventaDetalleBd.getId().getSecuencia());
            ventaDetalle.setId(id);
            //cargo articulo
            ArticuloRepositoryImpl articuloRepository = new ArticuloRepositoryImpl(this._db);
            ventaDetalle.setArticulo(articuloRepository.mappingToDto(this._db.articuloDao().recoveryByID(ventaDetalleBd.getIdArticulo())));
            ventaDetalle.setCantidad(ventaDetalleBd.getCantidad());
            ventaDetalle.setPrecioUnitarioSinIva(ventaDetalleBd.getPrecioUnitarioSinIva());
            ventaDetalle.setPrecioIvaUnitario(ventaDetalleBd.getPrecioIvaUnitario());
            ventaDetalle.setPrecioUnitarioSinDescuento(ventaDetalleBd.getPrecioUnitarioSinDescuento());
            ventaDetalle.setTasaDescuento(ventaDetalleBd.getTasaDescuento());
            ventaDetalle.setBultos(ventaDetalleBd.getBultos());
            ventaDetalle.setUnidades(ventaDetalleBd.getUnidades());
            //cargo lista de precio
            ListaPrecioRepositoryImpl listaPrecioRepository = new ListaPrecioRepositoryImpl(this._db);
            ventaDetalle.setListaPrecio(listaPrecioRepository.mappingToDto(this._db.listaPrecioDao().recoveryByID(ventaDetalleBd.getIdLista())));
            ventaDetalle.setAnulo(ventaDetalleBd.getAnulo());
            ventaDetalle.setFechaSincronizacion(ventaDetalleBd.getFechaSincronizacion() == null
                    ? null
                    : Formatter.convertToDateTime(ventaDetalleBd.getFechaSincronizacion()));
            ventaDetalle.setPrecioImpuestoInterno(ventaDetalleBd.getPrecioImpuestoInterno());
            ventaDetalle.setIdMovil(ventaDetalleBd.getIdMovil());
            ventaDetalle.setIdDescuentoProveedor(ventaDetalleBd.getIdDescuentoProveedor());
            ventaDetalle.setTasaDescuentoProveedor(ventaDetalleBd.getTasaDescuentoProveedor());
            ventaDetalle.setPrecioUnitarioSinDescuentoProveedor(ventaDetalleBd.getPrecioUnitarioSinDescuentoProveedor());
            ventaDetalle.setPrecioUnitarioSinDescuento(ventaDetalleBd.getPrecioUnitarioSinDescuento());
            ventaDetalle.setTasaDescuentoCliente(ventaDetalleBd.getTasaDescuentoCliente());
            ventaDetalle.setPrecioUnitarioSinDescuentoCliente(ventaDetalleBd.getPrecioUnitarioSinDescuentoCliente());
            ventaDetalle.setNumeroTropa(ventaDetalleBd.getNumeroTropa());
            ventaDetalle.setCaArticulosKilos(ventaDetalleBd.getCaArticulosKilos());
            ventaDetalle.setPrKiloUnitario(ventaDetalleBd.getPrKiloUnitario() == null
                    ? 0d
                    : ventaDetalleBd.getPrKiloUnitario());
            ventaDetalle.setIdCombo(ventaDetalleBd.getIdCombo());
            ventaDetalle.setCabeceraPromo(ventaDetalleBd.getSnCabeceraCombo() != null && ventaDetalleBd.getSnCabeceraCombo().equalsIgnoreCase("S"));
            ventaDetalle.setIdAccionesCom(ventaDetalleBd.getIdAccionesCom() != null
                    ? ventaDetalleBd.getIdAccionesCom()
                    : null);
            ventaDetalle.setUnidadesDevueltas(ventaDetalleBd.getUnidadesDevueltas());
            ventaDetalle.setBultosDevueltos(ventaDetalleBd.getBultosDevueltos());
            ventaDetalle.setTasaImpuestoInterno(ventaDetalleBd.getTasaImpuestoInterno());
            ventaDetalle.setIdPromo(ventaDetalleBd.getIdPromo() != null && ventaDetalleBd.getIdPromo() != 0
                    ? ventaDetalleBd.getIdPromo()
                    : null);
            ventaDetalle.setTipoLista(ventaDetalleBd.getTipoLista());
            ventaDetalle.setIdAccionEmp(ventaDetalleBd.getIdAccionEmp() == null
                    ? null
                    : ventaDetalleBd.getIdAccionEmp());
            ventaDetalle.setIsMaxAccomSuperado(ventaDetalleBd.getIsMaxAccomSuperado());
            ventaDetalle.setIdFcSecuencia(ventaDetalleBd.getIdFcSecuencia() == null
                    ? null
                    : ventaDetalleBd.getIdFcSecuencia());
            ventaDetalle.setCaKilos(ventaDetalleBd.getCaKilos() == null
                    ? 0d
                    : ventaDetalleBd.getCaKilos());
        }
        return ventaDetalle;
    }

    public VentaDetalleBd mappingToDb(VentaDetalle ventaDetalle) throws Exception {
        if (ventaDetalle != null) {
            PkVentaDetalleBd id = new PkVentaDetalleBd();

            id.setIdDocumento(ventaDetalle.getId().getIdDocumento());
            id.setIdLetra(ventaDetalle.getId().getIdLetra());
            id.setPuntoVenta(ventaDetalle.getId().getPuntoVenta());
            id.setIdNumeroDocumento(ventaDetalle.getId().getIdNumeroDocumento());
            id.setSecuencia(ventaDetalle.getId().getSecuencia());

            VentaDetalleBd ventaDetalleBd = new VentaDetalleBd();
            ventaDetalleBd.setId(id);
            ventaDetalleBd.setIdArticulo(ventaDetalle.getArticulo().getId());
            ventaDetalleBd.setCantidad(ventaDetalle.getCantidad());
            ventaDetalleBd.setPrecioIvaUnitario(ventaDetalle.getPrecioIvaUnitario());
            ventaDetalleBd.setBultos(ventaDetalle.getBultos());
            ventaDetalleBd.setUnidades(ventaDetalle.getUnidades());
            ventaDetalleBd.setIdLista(ventaDetalle.getListaPrecio().getId());
            ventaDetalleBd.setAnulo(ventaDetalle.getAnulo());
            ventaDetalleBd.setFechaSincronizacion(ventaDetalle.getFechaSincronizacion() == null
                    ? null
                    : Formatter.formatDateTimeToString(ventaDetalle.getFechaSincronizacion()));
            ventaDetalleBd.setPrecioImpuestoInterno(ventaDetalle.getPrecioImpuestoInterno());
            ventaDetalleBd.setIdMovil(ventaDetalle.getIdMovil());
            ventaDetalleBd.setIdDescuentoProveedor(ventaDetalle.getIdDescuentoProveedor());
            ventaDetalleBd.setPrecioUnitarioSinDescuentoProveedor(ventaDetalle.getPrecioUnitarioSinDescuentoProveedor());
            ventaDetalleBd.setTasaDescuentoProveedor(ventaDetalle.getTasaDescuentoProveedor());
            ventaDetalleBd.setPrecioUnitarioSinDescuentoCliente(ventaDetalle.getPrecioUnitarioSinDescuentoCliente());
            ventaDetalleBd.setTasaDescuentoCliente(ventaDetalle.getTasaDescuentoCliente());
            ventaDetalleBd.setPrecioUnitarioSinDescuento(ventaDetalle.getPrecioUnitarioSinDescuento());
            ventaDetalleBd.setTasaDescuento(ventaDetalle.getTasaDescuento());
            ventaDetalleBd.setPrecioUnitarioSinIva(ventaDetalle.getPrecioUnitarioSinIva());
            ventaDetalleBd.setNumeroTropa(ventaDetalle.getNumeroTropa());
            ventaDetalleBd.setCaArticulosKilos(ventaDetalle.getCaArticulosKilos());
            ventaDetalleBd.setPrKiloUnitario(ventaDetalle.getPrKiloUnitario());
            ventaDetalleBd.setIdCombo(ventaDetalle.getIdCombo());
            ventaDetalleBd.setSnCabeceraCombo(ventaDetalle.isCabeceraPromo() ? "S" : "N");
            ventaDetalleBd.setIdAccionesCom(ventaDetalle.getIdAccionesCom());
            ventaDetalleBd.setUnidadesDevueltas(ventaDetalle.getUnidadesDevueltas());
            ventaDetalleBd.setBultosDevueltos(ventaDetalle.getBultosDevueltos());
            ventaDetalleBd.setTasaImpuestoInterno(ventaDetalle.getTasaImpuestoInterno());
            ventaDetalleBd.setIdPromo(ventaDetalle.getIdPromo());
            ventaDetalleBd.setTipoLista(ventaDetalle.getTipoLista());
            ventaDetalleBd.setIdAccionEmp(ventaDetalle.getIdAccionEmp());
            ventaDetalleBd.setIsMaxAccomSuperado(ventaDetalle.getIsMaxAccomSuperado());
            ventaDetalleBd.setIdFcSecuencia(ventaDetalle.getIdFcSecuencia());
            ventaDetalleBd.setCaKilos(ventaDetalle.getCaKilos());

            ventaDetalleBd.setTipoOperacion(ventaDetalle.getTipoOperacion());
            ventaDetalleBd.setIdAccionComDetalleEmp(ventaDetalle.getIdAccionComDetalleEmp());
            ventaDetalleBd.setIdAccionComDetalleProveedor(ventaDetalle.getIdAccionComDetalleProveedor());

            return ventaDetalleBd;
        }
        return null;
    }

}
