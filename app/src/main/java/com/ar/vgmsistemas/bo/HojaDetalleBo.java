package com.ar.vgmsistemas.bo;

import android.content.Context;
import android.location.Location;

import com.ar.vgmsistemas.entity.Cheque;
import com.ar.vgmsistemas.entity.Deposito;
import com.ar.vgmsistemas.entity.Documento;
import com.ar.vgmsistemas.entity.Entrega;
import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.entity.ListaHojaDetalle;
import com.ar.vgmsistemas.entity.ListaPkHojaDetalle;
import com.ar.vgmsistemas.entity.ListaPrecio;
import com.ar.vgmsistemas.entity.ListaVentas;
import com.ar.vgmsistemas.entity.Movimiento;
import com.ar.vgmsistemas.entity.PagoEfectivo;
import com.ar.vgmsistemas.entity.Retencion;
import com.ar.vgmsistemas.entity.UbicacionGeografica;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.entity.key.PkDocumento;
import com.ar.vgmsistemas.entity.key.PkHojaDetalle;
import com.ar.vgmsistemas.gps.GPSManagement;
import com.ar.vgmsistemas.repository.IChequeRepository;
import com.ar.vgmsistemas.repository.IDepositoRepository;
import com.ar.vgmsistemas.repository.IDocumentoRepository;
import com.ar.vgmsistemas.repository.IEntregaRepository;
import com.ar.vgmsistemas.repository.IHojaDetalleRepository;
import com.ar.vgmsistemas.repository.IMovimientoRepository;
import com.ar.vgmsistemas.repository.IPagoEfectivoRepository;
import com.ar.vgmsistemas.repository.IRetencionRepository;
import com.ar.vgmsistemas.repository.IUbicacionGeograficaRepository;
import com.ar.vgmsistemas.repository.IVentaRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.CodeResult;
import com.ar.vgmsistemas.utils.ComparatorDateTime;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.Matematica;
import com.ar.vgmsistemas.ws.RepartoWs;
import com.ar.vgmsistemas.ws.UbicacionGeograficaWs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;


public class HojaDetalleBo implements IEntityBo<HojaDetalle> {
    //REPOSITORY
    private final IHojaDetalleRepository _hojaDetalleRepository;
    private final IChequeRepository _chequeRepository;
    private final IRetencionRepository _retencionRepository;
    private final IPagoEfectivoRepository _pagoEfectivoRepository;
    private final IDepositoRepository _depositoRepository;
    private final IUbicacionGeograficaRepository _ubicacionGeograficaRepository;
    private final IMovimientoRepository _movimientoRepository;
    private final IEntregaRepository _entregaRepository;
    private final IDocumentoRepository _documentoRepository;
    private final IVentaRepository _ventaRepository;

    //BD
    private final RepositoryFactory _repoFactory;

    //BO
    private final EmpresaBo _empresaBo;// = new EmpresaBo();
    private static HojaBo mHojaBo;
    private int idHoja;
    private int idSucursal;


    public HojaDetalleBo(RepositoryFactory repoFactory) {
        this._repoFactory = repoFactory;
        _empresaBo = new EmpresaBo(_repoFactory);
        mHojaBo = new HojaBo(_repoFactory);

        this._hojaDetalleRepository = _repoFactory.getHojaDetalleRepository();
        this._chequeRepository = _repoFactory.getChequeRepository();
        this._retencionRepository = _repoFactory.getRetencionRepository();
        this._pagoEfectivoRepository = _repoFactory.getPagoEfectivoRepository();
        this._depositoRepository = _repoFactory.getDepositoRepository();
        this._ubicacionGeograficaRepository = _repoFactory.getUbicacionGeograficaRepository();
        this._movimientoRepository = _repoFactory.getMovimientoRepository();
        this._entregaRepository = _repoFactory.getEntregaRepository();
        this._documentoRepository = _repoFactory.getDocumentoRepository();
        this._ventaRepository = _repoFactory.getVentaRepository();
    }

    public HojaDetalleBo(int idSucursal, int idHoja, RepositoryFactory repoFactory) {
        this._repoFactory = repoFactory;
        this.idSucursal = idSucursal;
        this.idHoja = idHoja;
        _empresaBo = new EmpresaBo(_repoFactory);

        this._hojaDetalleRepository = _repoFactory.getHojaDetalleRepository();
        this._chequeRepository = _repoFactory.getChequeRepository();
        this._retencionRepository = _repoFactory.getRetencionRepository();
        this._pagoEfectivoRepository = _repoFactory.getPagoEfectivoRepository();
        this._depositoRepository = _repoFactory.getDepositoRepository();
        this._ubicacionGeograficaRepository = _repoFactory.getUbicacionGeograficaRepository();
        this._movimientoRepository = _repoFactory.getMovimientoRepository();
        this._entregaRepository = _repoFactory.getEntregaRepository();
        this._documentoRepository = _repoFactory.getDocumentoRepository();
        this._ventaRepository = _repoFactory.getVentaRepository();
    }

    /**
     * @return listado de hojas detalle que corresponden con el id que se paso en el constructor
     * HojaDetalleBo(int idHoja), es necesario utilizar este constructor para que este metodo puede utilizarse
     */
    public List<HojaDetalle> recoveryAllEntities() throws Exception {
        return _hojaDetalleRepository.recoveryByHoja(idSucursal, idHoja);
    }

    public List<HojaDetalle> recoveryByIdEntrega(int idEntrega) throws Exception {
        return _hojaDetalleRepository.recoveryByIdEntrega(idEntrega);
    }

    public HojaDetalle recoveryByIDConEntrega(PkHojaDetalle pkHojaDetalle) throws Exception {
        return _hojaDetalleRepository.recoveryByIDConEntrega(pkHojaDetalle);
    }

    public void setAnulado(HojaDetalle detalle) throws Exception {
        detalle.setPrNotaCredito(detalle.getPrTotal());
        _hojaDetalleRepository.update(detalle);
        _hojaDetalleRepository.updateState(detalle, HojaDetalle.ANULADO);

    }

    public boolean isPrerendida(int idHoja) throws Exception {
        setIdHoja(idHoja);
        List<HojaDetalle> detalles = recoveryAllEntities();
        for (HojaDetalle detalle : detalles) {
            if (!hojaTratada(detalle)) {
                return false;
            }
        }
        return true;
    }

    public void setNoEntregado(HojaDetalle detalle) throws Exception {
        _hojaDetalleRepository.updateState(detalle, HojaDetalle.PENDIENTE);
    }

    public List<HojaDetalle> recoveryAEnviar() throws Exception {
        return _hojaDetalleRepository.recoveryAEnviar();
    }

    public List<HojaDetalle> recoveryAEnviarUnicaEntrega() throws Exception {
        return _hojaDetalleRepository.recoveryAEnviarUnicaEntrega();
    }

    public boolean isEnviado(HojaDetalle detalle) throws Exception {
        return _hojaDetalleRepository.isEnviado(detalle);
    }

    public static Double getEnCuentaCorriente(HojaDetalle hojaDetalle) {
        return Matematica.Round(hojaDetalle.getPrTotal() - hojaDetalle.getPrNotaCredito() - hojaDetalle.getPrPagado(), 2);
    }

    public static Double getTotalPagado(Entrega entre) {
        double monto;
        monto = entre.calcularTotalEntregasCheque() + entre.calcularTotalPagosEfectivo()
                + entre.calcularTotalRetenciones() + entre.calcularTotalEntregasDeposito();
        return monto;
    }

    public Entrega recoveryEntregas(Entrega entrega) throws Exception {
        List<Cheque> cheques = _chequeRepository.recoveryByEntrega(entrega);
        entrega.setCheques(cheques);

        List<PagoEfectivo> pagosEfectivo = _pagoEfectivoRepository.recoveryByEntrega(entrega);
        entrega.setPagosEfectivo(pagosEfectivo);

        List<Retencion> retenciones = _retencionRepository.recoveryByEntrega(entrega);
        entrega.setRetenciones(retenciones);

        List<Deposito> depositos = _depositoRepository.recoveryByEntrega(entrega);
        entrega.setDepositos(depositos);
        return entrega;
    }

    public void guardarHojaDetalle(Venta credito, final HojaDetalle hojaDetalle, final Context context)
            throws Exception {
        boolean isGuardado;//Inicia en falso
        try {

            if (hojaDetalle.getIdMovil() != null) {
                MovimientoBo movimientoBo = new MovimientoBo(_repoFactory);
                Movimiento movimiento = movimientoBo.getMovimiento(hojaDetalle.getIdMovil());
                if (movimiento != null) {
                    movimiento.setIsModificado(true);
                    _movimientoRepository.updateIsModificado(movimiento);
                }
            }

            VentaBo ventaBo = new VentaBo(_repoFactory);
            ventaBo.eliminarCreditoFactura(hojaDetalle.getId().getIdFcnc(),
                    hojaDetalle.getId().getIdTipoab(), hojaDetalle.getId().getIdPtovta(),
                    hojaDetalle.getId().getIdNumdoc());

            String idMovil = generarIdMovil(hojaDetalle);
            if (credito != null) {

                VentaDetalleBo detalleBo = new VentaDetalleBo(_repoFactory);
                detalleBo.updateXDevolucion(credito);
                PkDocumento pkDocumento = new PkDocumento();
                pkDocumento.setIdDocumento(credito.getId().getIdDocumento());
                pkDocumento.setIdLetra(credito.getId().getIdLetra());
                pkDocumento.setPuntoVenta(credito.getId().getPuntoVenta());
                Documento documentoNC = _documentoRepository.recoveryDocumentoAnulaMovil(pkDocumento);
                if (documentoNC == null) {
                    throw new Exception("No esta configurada la nota de credito movil para el documento: " +
                            pkDocumento.getIdDocumento() + "-" + pkDocumento.getIdLetra() + "-" + pkDocumento.getPuntoVenta());
                }

                // recupero el siguiente numdoc para la NC
                DocumentoBo documentoBo = new DocumentoBo(_repoFactory);
                long siguienteNumdoc = documentoBo.recoveryNumeroDocumento(documentoNC.getId().getIdDocumento(),
                        documentoNC.getId().getIdLetra(), documentoNC.getId().getPuntoVenta());
                credito.setIdMovil(idMovil);
                List<VentaDetalle> detallesAEliminar = new ArrayList<>();
                for (VentaDetalle vd : credito.getDetalles()) {

                    if (vd.isCabeceraPromo()) {
                        if (vd.getListaPrecio().getTipoLista() == ListaPrecio.TIPO_LISTA_COMBOS_COMUNES) {
                            List<VentaDetalle> ventaDetalleList;
                            VentaDetalleBo ventaDetalleBo = new VentaDetalleBo(_repoFactory);
                            ventaDetalleList = ventaDetalleBo.recoveryDetallesCombo(vd);
                            double precio = 0d;
                            double precio_iva = 0d;
                            for (VentaDetalle item : ventaDetalleList) {
                                precio += (item.getPrecioUnitarioSinIva() * item.getCantidad()) / vd.getCantidad();
                                precio_iva += (item.getPrecioIvaUnitario() * item.getCantidad()) / vd.getCantidad();
                            }
                            vd.setPrecioUnitarioSinDescuento(precio);
                            vd.setPrecioUnitarioSinDescuentoCliente(precio);
                            vd.setPrecioUnitarioSinDescuentoProveedor(precio);
                            vd.setPrecioUnitarioSinIva(precio);
                            vd.setPrecioIvaUnitario(precio_iva);
                        } else {
                            for (VentaDetalle vdc : vd.getDetalleCombo()) {
                                if (vdc.getBultosDevueltos() == 0 && vdc.getUnidadesDevueltas() == 0) {
                                    detallesAEliminar.add(vdc);
                                } else {
                                    vdc.getId().setIdDocumento(documentoNC.getId().getIdDocumento());
                                    vdc.getId().setIdLetra(documentoNC.getId().getIdLetra());
                                    vdc.getId().setIdNumeroDocumento(siguienteNumdoc);
                                    vdc.getId().setPuntoVenta(documentoNC.getId().getPuntoVenta());
                                    vdc.setBultos(vdc.getBultosDevueltos());
                                    vdc.setUnidades(vdc.getUnidadesDevueltas());
                                    vdc.setIdMovil(credito.getIdMovil());
                                }
                            }
                        }

                    }
                    vd.setIdFcSecuencia(vd.getId().getSecuencia());
                    if (vd.getBultosDevueltos() == 0 && vd.getUnidadesDevueltas() == 0) {
                        detallesAEliminar.add(vd);
                    } else {
                        vd.getId().setIdDocumento(documentoNC.getId().getIdDocumento());
                        vd.getId().setIdLetra(documentoNC.getId().getIdLetra());
                        vd.getId().setIdNumeroDocumento(siguienteNumdoc);
                        vd.getId().setPuntoVenta(documentoNC.getId().getPuntoVenta());
                        vd.setBultos(vd.getBultosDevueltos());
                        vd.setUnidades(vd.getUnidadesDevueltas());
                        vd.setIdMovil(credito.getIdMovil());
                    }
                }
                credito.getDetalles().removeAll(detallesAEliminar);
                if (credito.getDetalles().size() > 0) {// si el credito tiene datalles guardo
                    PkDocumento idDoc = new PkDocumento();
                    idDoc.setIdDocumento(documentoNC.getId().getIdDocumento());
                    idDoc.setIdLetra(documentoNC.getId().getIdLetra());
                    idDoc.setPuntoVenta(documentoNC.getId().getPuntoVenta());

                    // Actualizo número de documento
                    _documentoRepository.updateNumeroDocumento(idDoc, siguienteNumdoc);

                    credito.setIdFcNumdoc(hojaDetalle.getId().getIdNumdoc());
                    credito.setIdFcFcnc(hojaDetalle.getId().getIdFcnc());
                    credito.setIdFcPtovta(hojaDetalle.getId().getIdPtovta());
                    credito.setIdFcTipoab(hojaDetalle.getId().getIdTipoab());

                    VentaBo.actualizarTotales(credito, true);

                    credito.getId().setIdNumeroDocumento(siguienteNumdoc);
                    credito.getId().setIdDocumento(documentoNC.getId().getIdDocumento());
                    credito.getId().setIdLetra(documentoNC.getId().getIdLetra());
                    credito.getId().setPuntoVenta(documentoNC.getId().getPuntoVenta());

                    detalleBo.create(credito);
                    _ventaRepository.create(credito);
                    hojaDetalle.setPrNotaCredito(credito.getTotal());
                }
            }

            hojaDetalle.setIdMovil(idMovil);

            if (hojaDetalle.getEntrega() != null) {
                controlarEntregasAnteriores(hojaDetalle);
                int id_entrega = _entregaRepository.create(hojaDetalle.getEntrega());
                hojaDetalle.getEntrega().setId(id_entrega);
                hojaDetalle.setPrPagado(hojaDetalle.getEntrega().obtenerTotalPagos());
                guardarPagosEfectivos(hojaDetalle);
                guardarCheques(hojaDetalle);
                guardarDepositos(hojaDetalle);
                guardarRetenciones(hojaDetalle);

            }
            _hojaDetalleRepository.update(hojaDetalle);

            Location location = null;

            if (_empresaBo.isRegistrarLocalizacion() && ComparatorDateTime.validarRangoHorarioEnvioLocalizacion()) {
                GPSManagement gps = new GPSManagement(context.getApplicationContext(), hojaDetalle.getIdMovil(),
                        UbicacionGeografica.OPERACION_REPARTO);
                gps.getLocation(false);
                location = gps.get_location();
            }

            registrarMovimientoHojaDetalle(hojaDetalle, location);
            isGuardado = true;
        } catch (Exception e) {
            ErrorManager.manageException(HojaDetalleBo.class.getCanonicalName(), "Guardar Metodo HojasDetalle", e);
            throw e;
        }

        if (isGuardado && PreferenciaBo.getInstance().getPreferencia().isEnvioHojaDetalleAutomatico()) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        //enviarPedido(venta, ubicacionGeograficaEnviar, context);
                        SincronizacionBo sincronizacionBo = new SincronizacionBo(context, _repoFactory);
                        sincronizacionBo.enviarHojasDetallePendientes();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }
    }

    //ESTA FUNCION GUARDA LAS HOJAS_DETALLE QUE TIENEN UNA MISMA ENTREGA
    public void guardarHojaDetalle(Venta credito, final HojaDetalle hojaDetalle, final Context context, final boolean crearEntrega)
            throws Exception {
        boolean isGuardado;//inicializa en false
        try {
            if (hojaDetalle.getIdMovil() != null) {
                MovimientoBo movimientoBo = new MovimientoBo(_repoFactory);
                Movimiento movimiento = movimientoBo.getMovimiento(hojaDetalle.getIdMovil());
                if (movimiento != null) {
                    movimiento.setIsModificado(true);
                    _movimientoRepository.updateIsModificado(movimiento);
                }
            }

            VentaBo ventaBo = new VentaBo(_repoFactory);
            ventaBo.eliminarCreditoFactura(hojaDetalle.getId().getIdFcnc(),
                    hojaDetalle.getId().getIdTipoab(), hojaDetalle.getId().getIdPtovta(),
                    hojaDetalle.getId().getIdNumdoc());

            String idMovil = generarIdMovil(hojaDetalle);
            if (credito != null) {

                VentaDetalleBo detalleBo = new VentaDetalleBo(_repoFactory);
                detalleBo.updateXDevolucion(credito);
                PkDocumento pkDocumento = new PkDocumento();
                pkDocumento.setIdDocumento(credito.getId().getIdDocumento());
                pkDocumento.setIdLetra(credito.getId().getIdLetra());
                pkDocumento.setPuntoVenta(credito.getId().getPuntoVenta());
                boolean isComprobanteNC = credito.getDocumento().getFuncionTipoDocumento() == 2;
                Documento documentoNC = null;
                long siguienteNumdoc = 0;
                if (!isComprobanteNC) {
                    documentoNC = _documentoRepository.recoveryDocumentoAnulaMovil(pkDocumento);
                    if (documentoNC == null) {
                        throw new Exception("No esta configurada la nota de credito movil para el documento: " +
                                pkDocumento.getIdDocumento() + "-" + pkDocumento.getIdLetra() + "-" + pkDocumento.getPuntoVenta());
                    }

                    // recupero el siguiente numdoc para la NC
                    DocumentoBo documentoBo = new DocumentoBo(_repoFactory);
                    siguienteNumdoc = documentoBo.recoveryNumeroDocumento(documentoNC.getId().getIdDocumento(),
                            documentoNC.getId().getIdLetra(), documentoNC.getId().getPuntoVenta());
                }
                credito.setIdMovil(idMovil);
                List<VentaDetalle> detallesAEliminar = new ArrayList<>();
                for (VentaDetalle vd : credito.getDetalles()) {

                    if (vd.isCabeceraPromo()) {
                        if (vd.getListaPrecio().getTipoLista() == ListaPrecio.TIPO_LISTA_COMBOS_COMUNES) {
                            List<VentaDetalle> ventaDetalleList;
                            VentaDetalleBo ventaDetalleBo = new VentaDetalleBo(_repoFactory);
                            ventaDetalleList = ventaDetalleBo.recoveryDetallesCombo(vd);
                            double precio = 0d;
                            double precio_iva = 0d;
                            for (VentaDetalle item : ventaDetalleList) {
                                precio += (item.getPrecioUnitarioSinIva() * item.getCantidad()) / vd.getCantidad();
                                precio_iva += (item.getPrecioIvaUnitario() * item.getCantidad()) / vd.getCantidad();
                            }
                            vd.setPrecioUnitarioSinDescuento(precio);
                            vd.setPrecioUnitarioSinDescuentoCliente(precio);
                            vd.setPrecioUnitarioSinDescuentoProveedor(precio);
                            vd.setPrecioUnitarioSinIva(precio);
                            vd.setPrecioIvaUnitario(precio_iva);
                        } else {
                            for (VentaDetalle vdc : vd.getDetalleCombo()) {
                                if (vdc.getBultosDevueltos() == 0 && vdc.getUnidadesDevueltas() == 0) {
                                    detallesAEliminar.add(vdc);
                                } else {
                                    if (documentoNC != null) {
                                        vdc.getId().setIdDocumento(documentoNC.getId().getIdDocumento());
                                        vdc.getId().setIdLetra(documentoNC.getId().getIdLetra());
                                        vdc.getId().setIdNumeroDocumento(siguienteNumdoc);
                                        vdc.getId().setPuntoVenta(documentoNC.getId().getPuntoVenta());
                                        vdc.setBultos(vdc.getBultosDevueltos());
                                        vdc.setUnidades(vdc.getUnidadesDevueltas());
                                        vdc.setIdMovil(credito.getIdMovil());
                                    } else {
                                        throw new Exception("VDC: No esta configurada la nota de credito movil para el documento: " +
                                                vdc.getId().getIdDocumento() + "-" + vdc.getId().getIdLetra() + "-" + vdc.getId().getPuntoVenta());
                                    }
                                }
                            }
                        }

                    }
                    vd.setIdFcSecuencia(vd.getId().getSecuencia());
                    if (vd.getBultosDevueltos() == 0 && vd.getUnidadesDevueltas() == 0) {
                        detallesAEliminar.add(vd);
                    } else {
                        if (documentoNC != null) {
                            vd.getId().setIdDocumento(documentoNC.getId().getIdDocumento());
                            vd.getId().setIdLetra(documentoNC.getId().getIdLetra());
                            vd.getId().setIdNumeroDocumento(siguienteNumdoc);
                            vd.getId().setPuntoVenta(documentoNC.getId().getPuntoVenta());
                            vd.setBultos(vd.getBultosDevueltos());
                            vd.setUnidades(vd.getUnidadesDevueltas());
                            vd.setIdMovil(credito.getIdMovil());
                        } else {
                            throw new Exception("VD: No esta configurada la nota de credito movil para el documento: " +
                                    vd.getId().getIdDocumento() + "-" + vd.getId().getIdLetra() + "-" + vd.getId().getPuntoVenta());
                        }
                    }
                }
                credito.getDetalles().removeAll(detallesAEliminar);
                if (credito.getDetalles().size() > 0 && documentoNC != null) {// si el credito tiene datalles guardo
                    PkDocumento idDoc = new PkDocumento();
                    idDoc.setIdDocumento(documentoNC.getId().getIdDocumento());
                    idDoc.setIdLetra(documentoNC.getId().getIdLetra());
                    idDoc.setPuntoVenta(documentoNC.getId().getPuntoVenta());
                    // Actualizo numero de documento
                    _documentoRepository.updateNumeroDocumento(idDoc, siguienteNumdoc);

                    credito.setIdFcNumdoc(hojaDetalle.getId().getIdNumdoc());
                    credito.setIdFcFcnc(hojaDetalle.getId().getIdFcnc());
                    credito.setIdFcPtovta(hojaDetalle.getId().getIdPtovta());
                    credito.setIdFcTipoab(hojaDetalle.getId().getIdTipoab());

                    VentaBo.actualizarTotales(credito, true);

                    credito.getId().setIdNumeroDocumento(siguienteNumdoc);
                    credito.getId().setIdDocumento(documentoNC.getId().getIdDocumento());
                    credito.getId().setIdLetra(documentoNC.getId().getIdLetra());
                    credito.getId().setPuntoVenta(documentoNC.getId().getPuntoVenta());

                    detalleBo.create(credito);
                    _ventaRepository.create(credito);
                    hojaDetalle.setPrNotaCredito(credito.getTotal());
                }
            }
            hojaDetalle.setIdMovil(idMovil);

            if (crearEntrega) {
                if (hojaDetalle.getEntrega() != null) {
                    controlarEntregasAnteriores(hojaDetalle);
                    int id_entrega = _entregaRepository.create(hojaDetalle.getEntrega());
                    hojaDetalle.getEntrega().setId(id_entrega);
                    guardarPagosEfectivos(hojaDetalle);
                    guardarCheques(hojaDetalle);
                    guardarDepositos(hojaDetalle);
                    guardarRetenciones(hojaDetalle);

                }
            }

            _hojaDetalleRepository.update(hojaDetalle);

            Location location = null;

            if (_empresaBo.isRegistrarLocalizacion() && ComparatorDateTime.validarRangoHorarioEnvioLocalizacion()) {
                GPSManagement gps = new GPSManagement(context.getApplicationContext(), hojaDetalle.getIdMovil(),
                        UbicacionGeografica.OPERACION_REPARTO);
                gps.getLocation(false);
                location = gps.get_location();
            }

            registrarMovimientoHojaDetalle(hojaDetalle, location);
            isGuardado = true;
        } catch (Exception e) {
            throw e;
        }

        if (isGuardado && PreferenciaBo.getInstance().getPreferencia().isEnvioHojaDetalleAutomatico()) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        SincronizacionBo sincronizacionBo = new SincronizacionBo(context, _repoFactory);
                        sincronizacionBo.enviarHojasDetallesPendientes();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }
    }


    /**
     * @param detalle: hoja detalle item
     * @return <b>true</b> si la hoja fue firmada, anulada, no entregada o se realizó algún pago; <b>false</b> en otro caso
     */

    public static boolean hojaTratada(HojaDetalle detalle) {
        boolean value;
        value = (!(detalle.getIdMovil() == null));
        return value;
    }

    public void controlarEntregasAnteriores(HojaDetalle hojaDetalle) throws Exception {
        HojaDetalleBo hojaDetalleBo = new HojaDetalleBo(_repoFactory);
        HojaDetalle hojaDetalleBD = new HojaDetalle();
        hojaDetalleBD.getEntrega().setId(hojaDetalle.getEntrega().getId());
        hojaDetalleBD.setEntrega(hojaDetalleBo.recoveryEntregas(hojaDetalleBD.getEntrega()));
        if (hojaDetalleBD.getEntrega().getEntregasEfectivo().size() > 0) {
            deletePagosEfectivosByEntrega(hojaDetalleBD);
        }
        if (hojaDetalleBD.getEntrega().getCheques().size() > 0) {
            deleteChequesByEntrega(hojaDetalleBD);
        }
        if (hojaDetalleBD.getEntrega().getEntregasEfectivo().size() > 0) {
            deleteDepositosByEntrega(hojaDetalleBD);
        }
        if (hojaDetalleBD.getEntrega().getEntregasEfectivo().size() > 0) {
            deleteRetencionesByEntrega(hojaDetalleBD);
        }
        hojaDetalleBo.actualizarValoresEntrega(hojaDetalleBD.getEntrega().getId());
        _entregaRepository.delete(hojaDetalleBD.getEntrega().getId());

    }

    private String generarIdMovil(HojaDetalle hojaDetalle) {
        String idVendedor = Formatter.formatNumber(PreferenciaBo.getInstance().getPreferencia().getIdVendedor(),
                "000");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String fecha;
        fecha = sdf.format(Calendar.getInstance().getTime());
        String documento = hojaDetalle.getId().getIdFcnc();
        String letra = hojaDetalle.getId().getIdTipoab();
        String puntoVenta = Formatter.formatNumber(hojaDetalle.getId().getIdPtovta(), "0000");
        String numeroDocumento = Formatter.formatNumber(hojaDetalle.getId().getIdNumdoc(), "00000000");
        String idMovil = idVendedor + "-" + fecha + "-" + documento + "-" + letra + "-" + puntoVenta + "-"
                + numeroDocumento;
        return idMovil;
    }

    public synchronized int enviarHojaDetalle(HojaDetalle hojaDetalle, Venta credito,
                                              UbicacionGeografica ubicacionGeografica, boolean prerendida, Context context) throws Exception {
        // Env�o la Venta
        int result = CodeResult.RESULT_ERROR;
        RepartoWs repartoWs = new RepartoWs(context);
        if (credito != null && credito.getDetalles().size() > 0) {
            result = repartoWs.updateHojaDetalleCredito(credito, hojaDetalle, prerendida);
        } else {
            result = repartoWs.updateHojaDetalle(hojaDetalle, prerendida);
        }
        // Envío la ubicación geográfica

        if (result == CodeResult.RESULT_OK) {
            if (_empresaBo.isRegistrarLocalizacion()) {
                if (ubicacionGeografica != null) {

                    if (ubicacionGeografica.getFechaPosicionMovil() != null) {
                        UbicacionGeograficaWs ubicacionGeograficaWs = new UbicacionGeograficaWs(context);
                        ubicacionGeograficaWs.send(ubicacionGeografica);
                        // Actualizo fecha de sincronización de la ubicación geográfica
                        _ubicacionGeograficaRepository.updateFechaSincronizacion(ubicacionGeografica.getIdLegajo(),
                                ubicacionGeografica.getFechaPosicionMovil());
                    }
                }
            }
            _movimientoRepository.updateFechaSincronizacion(hojaDetalle);
        }

        return result;
    }

    //METODO NUEVO PARA ENVIAR HOJAS_DETALLES CON SOLO UNA ENTREGA ASOCIADA
    public synchronized int enviarHojasDetalles(ListaHojaDetalle hojasDetalles, ListaVentas creditos,
                                                UbicacionGeografica ubicacionGeografica, boolean prerendida, Context context) throws Exception {
        // Envío la Venta
        int result = CodeResult.RESULT_ERROR;
        RepartoWs repartoWs = new RepartoWs(context);
        if (creditos != null & creditos.getVentas().size() > 0) {
            result = repartoWs.updateHojaDetalleCreditoPorEntrega(creditos, hojasDetalles, prerendida);
        } else {
            result = repartoWs.updateHojaDetallePorEntrega(hojasDetalles, prerendida);
        }
        // Envío la ubicación geográfica

        if (result == CodeResult.RESULT_OK) {
            if (_empresaBo.isRegistrarLocalizacion()) {
                if (ubicacionGeografica != null) {

                    if (ubicacionGeografica.getFechaPosicionMovil() != null) {
                        UbicacionGeograficaWs ubicacionGeograficaWs = new UbicacionGeograficaWs(context);
                        ubicacionGeograficaWs.send(ubicacionGeografica);
                        // Actualizo fecha de sincronización de la ubicación geográfica
                        _ubicacionGeograficaRepository.updateFechaSincronizacion(ubicacionGeografica.getIdLegajo(),
                                ubicacionGeografica.getFechaPosicionMovil());
                    }
                }
            }
            _movimientoRepository.updateFechasSincronizacion(hojasDetalles.getHojasDetalles());
        }

        return result;
    }

    private void registrarMovimientoHojaDetalle(HojaDetalle hojaDetalle, Location location) throws Exception {
        Movimiento movimiento = new Movimiento();
        movimiento.setTabla(HojaDetalle.TABLE);
        movimiento.setIdMovil(hojaDetalle.getIdMovil());
        movimiento.setTipo(Movimiento.ALTA);
        if (location != null) {
            movimiento.setLocation(location);
        }
        // Cliente
        movimiento.setIdSucursal((int) hojaDetalle.getCliente().getId().getIdSucursal());
        movimiento.setIdCliente((int) hojaDetalle.getCliente().getId().getIdCliente());
        movimiento.setIdComercio((int) hojaDetalle.getCliente().getId().getIdComercio());
        MovimientoBo movimientoBo = new MovimientoBo(_repoFactory);
        movimientoBo.create(movimiento);
    }

    private void guardarPagosEfectivos(HojaDetalle hojaDetalle) throws Exception {

        Iterator<PagoEfectivo> pagosEfectivo = hojaDetalle.getEntrega().getEntregasEfectivo().iterator();
        PagoEfectivoBo pagoEfectivoBo = new PagoEfectivoBo(_repoFactory);
        while (pagosEfectivo.hasNext()) {
            PagoEfectivo pagoEfectivo = pagosEfectivo.next();
            pagoEfectivo.setEntrega(hojaDetalle.getEntrega());
            pagoEfectivoBo.guardar(pagoEfectivo);
        }

    }

    private void guardarCheques(HojaDetalle hojaDetalle) throws Exception {
        Iterator<Cheque> cheques = hojaDetalle.getEntrega().getCheques().iterator();
        ChequeBo chequeBo = new ChequeBo(_repoFactory);
        while (cheques.hasNext()) {
            Cheque cheque = cheques.next();
            cheque.setEntrega(hojaDetalle.getEntrega());
            try {
                chequeBo.guardar(cheque);
            } catch (Exception e) {
                throw new Exception(ErrorManager.ErrorChequeRepetido);

            }
        }

    }

    private void guardarDepositos(HojaDetalle hojaDetalle) throws Exception {

        Iterator<Deposito> depositos = hojaDetalle.getEntrega().getDepositos().iterator();
        DepositoBo depositoBo = new DepositoBo(_repoFactory);
        while (depositos.hasNext()) {
            Deposito deposito = depositos.next();
            deposito.setEntrega(hojaDetalle.getEntrega());
            depositoBo.guardar(deposito);
        }

    }

    private void guardarRetenciones(HojaDetalle hojaDetalle) throws Exception {

        Iterator<Retencion> retenciones = hojaDetalle.getEntrega().getRetenciones().iterator();
        RetencionBo retencionBo = new RetencionBo(_repoFactory);
        while (retenciones.hasNext()) {
            Retencion retencion = retenciones.next();
            retencion.setEntrega(hojaDetalle.getEntrega());
            retencionBo.guardar(retencion);
        }

    }

    private void deletePagosEfectivosByEntrega(HojaDetalle hojaDetalle) throws Exception {

        Iterator<PagoEfectivo> pagosEfectivo = hojaDetalle.getEntrega().getEntregasEfectivo().iterator();
        PagoEfectivoBo pagoEfectivoBo = new PagoEfectivoBo(_repoFactory);
        while (pagosEfectivo.hasNext()) {
            PagoEfectivo pagoEfectivo = pagosEfectivo.next();
            pagoEfectivoBo.deleteByEntrega(pagoEfectivo);
        }
    }

    private void deleteChequesByEntrega(HojaDetalle hojaDetalle) throws Exception {
        Iterator<Cheque> cheques = hojaDetalle.getEntrega().getCheques().iterator();
        ChequeBo chequeBo = new ChequeBo(_repoFactory);
        while (cheques.hasNext()) {
            Cheque cheque = cheques.next();
            try {
                chequeBo.deleteByEntrega(cheque);
            } catch (Exception e) {
                throw new Exception(ErrorManager.ErrorChequeRepetido);

            }
        }

    }

    private void deleteDepositosByEntrega(HojaDetalle hojaDetalle) throws Exception {

        Iterator<Deposito> depositos = hojaDetalle.getEntrega().getDepositos().iterator();
        DepositoBo depositoBo = new DepositoBo(_repoFactory);
        while (depositos.hasNext()) {
            Deposito deposito = depositos.next();
            depositoBo.deleteByEntrega(deposito);
        }

    }

    private void deleteRetencionesByEntrega(HojaDetalle hojaDetalle) throws Exception {

        Iterator<Retencion> retenciones = hojaDetalle.getEntrega().getRetenciones().iterator();
        RetencionBo retencionBo = new RetencionBo(_repoFactory);
        while (retenciones.hasNext()) {
            Retencion retencion = retenciones.next();
            retencionBo.delete(retencion);
        }

    }

    private void actualizarValoresEntrega(int idEntrega) throws Exception {
        Iterator<HojaDetalle> hojasDetalles = recoveryByIdEntrega(idEntrega).iterator();
        while (hojasDetalles.hasNext()) {
            HojaDetalle hojaDetalle = hojasDetalles.next();
            _hojaDetalleRepository.updateValoresEntrega(hojaDetalle);
        }

    }

    public Location registrarUbicacionGeografica(Context context, String idMovil) throws Exception {
        // Consulto en tabla Empresa por sn_localizacion y además valido el
        // rango de envío de localización
        EmpresaBo _empresaBo = new EmpresaBo(_repoFactory);
        if (_empresaBo.isRegistrarLocalizacion() && ComparatorDateTime.validarRangoHorarioEnvioLocalizacion()) {
            GPSManagement gps = new GPSManagement(context.getApplicationContext(), idMovil, UbicacionGeografica.OPERACION_REPARTO);
            gps.getLocation(false);
            return gps.get_location();
        } else {
            return null;
        }
    }

    /**
     * la hoja detalle tiene algo en cuenta corriente?
     */
    public static boolean isEnCuentaCorriente(HojaDetalle hojaDetalle) {
        double enCtaCte = Matematica.Round(getEnCuentaCorriente(hojaDetalle), 2);
        //Tolerancia de 1 centavo para solucionar problemas de redondeo
        return enCtaCte > 0.01d;
    }

    /**
     * Se determina si el comercio tiene código de autorizacion para el ingreso se ctacte al momento
     * de guardar la hoja detalle en ctacte
     */
    public static boolean isCtrlCodCuentaCorriente(HojaDetalle hojaDetalle) {
        boolean seHaceCtrlCodAutorizaCtaCteReparto = (hojaDetalle.getCliente() != null && !hojaDetalle.getCliente().getCodAutCtaCte().equals(""));
        return seHaceCtrlCodAutorizaCtaCteReparto;
    }

    /**
     * Se determina si el código del comercio es igual al codigo ingresado
     */
    public static boolean ctrlCodCuentaCorriente(HojaDetalle hojaDetalle, String codigoAValidar) {
        return hojaDetalle.getCliente().isCodigoCorrecto(codigoAValidar);
    }

    public void setIdHoja(int idHoja) {
        this.idHoja = idHoja;
    }

    public int recoveryIdEntrega(HojaDetalle hojaDetalle) throws Exception {
        return _hojaDetalleRepository.recoveryIdEntrega(hojaDetalle);
    }

    public HojaDetalle recoveryById(PkHojaDetalle pkHojaDetalle) throws Exception {
        return _hojaDetalleRepository.recoveryByID(pkHojaDetalle);
    }

    public int estanEnHoja(ListaPkHojaDetalle listaPkHojaDetalle, Context context) throws Exception {
        RepartoWs repartoWs = new RepartoWs(context);
        int result;
        ListaPkHojaDetalle listaPkHojaDetalle1;
        listaPkHojaDetalle1 = repartoWs.estanEnHoja(listaPkHojaDetalle);
        if (listaPkHojaDetalle1.getPkHojasDetalles() != null && listaPkHojaDetalle1.getPkHojasDetalles().size() > 0) {
            throw new Exception(ErrorManager.ErrorHojasDetalles);
        } else {
            result = 0;
        }

        return result;
    }


}
