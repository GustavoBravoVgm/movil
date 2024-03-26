package com.ar.vgmsistemas.bo;

import android.content.Context;
import android.location.Location;

import com.ar.vgmsistemas.entity.Compra;
import com.ar.vgmsistemas.entity.ComprasImpuestos;
import com.ar.vgmsistemas.entity.EntregaRendicion;
import com.ar.vgmsistemas.entity.Impuesto;
import com.ar.vgmsistemas.entity.Movimiento;
import com.ar.vgmsistemas.entity.UbicacionGeografica;
import com.ar.vgmsistemas.entity.key.PkCompra;
import com.ar.vgmsistemas.entity.key.PkComprasImpuestos;
import com.ar.vgmsistemas.entity.key.PkDocumento;
import com.ar.vgmsistemas.gps.GPSManagement;
import com.ar.vgmsistemas.repository.ICompraRepository;
import com.ar.vgmsistemas.repository.IComprasImpuestosRepository;
import com.ar.vgmsistemas.repository.IDocumentoRepository;
import com.ar.vgmsistemas.repository.IEntregaRendicionRepository;
import com.ar.vgmsistemas.repository.IMovimientoRepository;
import com.ar.vgmsistemas.repository.IUbicacionGeograficaRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.CodeResult;
import com.ar.vgmsistemas.utils.ComparatorDateTime;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Matematica;
import com.ar.vgmsistemas.view.reparto.hojas.FrmListadoEgresos;
import com.ar.vgmsistemas.ws.RendicionWs;
import com.ar.vgmsistemas.ws.UbicacionGeograficaWs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class RendicionBo {
    private static final String TAG = RendicionBo.class.getCanonicalName();
    //DAO
    private final IUbicacionGeograficaRepository _ubicacionGeograficaRepository;
    private final ICompraRepository _compraRepository;
    private final IEntregaRendicionRepository _entregaRendicionRepository;
    private final IMovimientoRepository _movimientoRepository;
    private final IComprasImpuestosRepository _comprasImpuestosRepository;
    private final IDocumentoRepository _documentoRepository;
    private final RepositoryFactory _repoFactory;


    // BO
    private EmpresaBo _empresaBo;

    public RendicionBo(RepositoryFactory repoFactory) {
        this._repoFactory = repoFactory;
        this._comprasImpuestosRepository = repoFactory.getComprasImpuestosRepository();
        this._documentoRepository = repoFactory.getDocumentoRepository();
        this._entregaRendicionRepository = repoFactory.getEntregaRendicionRepository();
        this._compraRepository = repoFactory.getCompraRepository();
        this._movimientoRepository = repoFactory.getMovimientoRepository();
        this._ubicacionGeograficaRepository = repoFactory.getUbicacionGeograficaRepository();
        this._empresaBo = new EmpresaBo(repoFactory);
    }

    public static double getGravadoFromImporte(double importe, double ta_impuesto) {
        return Matematica.Round(importe / ta_impuesto, 2);
    }

    public static double getImporteFromGravado(double gravado, double ta_impuesto) {
        return Matematica.Round(gravado * ta_impuesto, 2);
    }

    public static void sumarTotalesCompra(Compra compra) {
        compra.setPrPercIva(0d);
        compra.setPrPercIngrBruto(0d);
        compra.setPrIpInterno(0d);
        compra.setPrIva(0d);
        if (compra.getComprasImpuestos().size() > 0) //si tiene impuestos cargados el subtotal se calcula
            compra.setPrSubtotal(0d);
        for (ComprasImpuestos ci : compra.getComprasImpuestos()) {
            switch (ci.getImpuesto().getTiImpuesto()) {
                case Impuesto.TIPO_IMPUESTO_DGI: {
                    compra.setPrPercIva(compra.getPrPercIva() + ci.getPrImpuesto());
                    break;
                }
                case Impuesto.TIPO_IMPUESTO_DGR: {
                    compra.setPrPercIngrBruto(compra.getPrPercIngrBruto() + ci.getPrImpuesto());
                    break;
                }
                case Impuesto.TIPO_IMPUESTO_INTERNO: {
                    compra.setPrIpInterno(compra.getPrIpInterno() + ci.getPrImpuesto());
                    break;
                }
                case Impuesto.TIPO_IMPUESTO_IVA: {
                    compra.setPrIva(compra.getPrIva() + ci.getPrImpuesto());
                    compra.setPrSubtotal(compra.getPrSubtotal() + ci.getPrImpGravado());
                    break;
                }
            }
        }
        compra.setPrCompra(compra.getPrSubtotal() + compra.getPrExento() + compra.getPrPercIva() + compra.getPrPercIngrBruto() + compra.getPrIpInterno() + compra.getPrIva());
    }

    public synchronized int enviarEgreso(Compra egreso, Context context) throws Exception {
        // Envío el recibo
        RendicionWs rendicionWs = new RendicionWs(context);
        int resultWs = rendicionWs.enviarEgreso(egreso);
        if (!(resultWs == CodeResult.RESULT_VENDEDOR_INVALID)) {
            //Consulto en tabla Empresa por sn_localizacion
            if (_empresaBo.isRegistrarLocalizacion()) {

                // Recupero la ubicación geográfica
                UbicacionGeografica ubicacionGeograficaEgreso = _ubicacionGeograficaRepository.recoveryByIdMovil(egreso.getIdMovil());

                // Env?o la ubicación geográfica
                if (ubicacionGeograficaEgreso != null) {
                    if (ubicacionGeograficaEgreso.getFechaPosicionMovil() != null) {

                        UbicacionGeograficaWs ubicacionGeograficaWs = new UbicacionGeograficaWs(context);
                        ubicacionGeograficaWs.send(ubicacionGeograficaEgreso);
                        //Actualizo fecha de sincronizacion ubicación geográfica
                        _ubicacionGeograficaRepository.updateFechaSincronizacion(ubicacionGeograficaEgreso.getIdLegajo(), ubicacionGeograficaEgreso.getFechaPosicionMovil());
                    }
                }
            }
            // Por ahora se dejo esto asi por temas de compatibilidad pero habria que sacar afuera del hilo
            _movimientoRepository.updateFechaSincronizacion(egreso);
        }
        return resultWs;

    }

    public List<Compra> recoveryEgresosNoEnviados() throws Exception {
        List<Compra> egresos = _compraRepository.recoveryNoEnviados();
        for (Compra egreso : egresos) {
            egreso = recoveryEgreso(egreso);
        }
        return egresos;
    }

    public Compra recoveryEgreso(Compra egreso) throws Exception {

        List<ComprasImpuestos> comprasImpuestos = _comprasImpuestosRepository.recoveryByEgreso(egreso);
        egreso.setComprasImpuestos(comprasImpuestos);

        return egreso;
    }

    public List<EntregaRendicion> recoveryEntregasNoEnviadas() throws Exception {
        List<EntregaRendicion> entregas = _entregaRendicionRepository.recoveryNoEnviadas();
        return entregas;
    }

    public synchronized int enviarEntrega(EntregaRendicion entregaRendicion, Context context) throws Exception {
        // Env?o el recibo
        RendicionWs rendicionWs = new RendicionWs(context);
        int resultWs = rendicionWs.enviarEntrega(entregaRendicion);
        if (!(resultWs == CodeResult.RESULT_VENDEDOR_INVALID)) {
            //Consulto en tabla Empresa por sn_localizacion
            if (_empresaBo.isRegistrarLocalizacion()) {

                // Recupero la ubicación geográfica
                UbicacionGeografica ubicacionGeograficaEgreso = _ubicacionGeograficaRepository.recoveryByIdMovil(entregaRendicion.getIdMovil());

                // Env?o la ubicación geográfica
                if (ubicacionGeograficaEgreso != null) {
                    if (ubicacionGeograficaEgreso.getFechaPosicionMovil() != null) {

                        UbicacionGeograficaWs ubicacionGeograficaWs = new UbicacionGeograficaWs(context);
                        ubicacionGeograficaWs.send(ubicacionGeograficaEgreso);
                        //Actualizo fecha de sincronizacion ubicación geográfica
                        _ubicacionGeograficaRepository.updateFechaSincronizacion(ubicacionGeograficaEgreso.getIdLegajo(), ubicacionGeograficaEgreso.getFechaPosicionMovil());
                    }
                }
            }
            // Por ahora se dejo esto asi por temas de compatibilidad pero habria que sacar afuera del hilo
            _movimientoRepository.updateFechaSincronizacion(entregaRendicion);
        }
        return resultWs;

    }

    public void createEgreso(final Compra egreso, final Context context) throws Exception {
        boolean isGuardado = false;
        UbicacionGeografica ubicacionGeografica = null;
        try {
            //Seteo la fecha de registro
            if (egreso.getFeIngreso() == null)
                egreso.setFeIngreso(Calendar.getInstance().getTime());

            //Generando el idMovil
            String idMovil = generarIdMovil(egreso, Movimiento.ALTA);
            egreso.setIdMovil(idMovil);

            _compraRepository.create(egreso);

            //Actualizo numero de documento
            PkDocumento idDoc = new PkDocumento();
            idDoc.setIdDocumento(egreso.getId().getIdFcncnd());
            idDoc.setPuntoVenta(egreso.getId().getIdPuntoVenta());
            idDoc.setIdLetra(egreso.getIdLetra());
            _documentoRepository.updateNumeroDocumento(idDoc, egreso.getId().getIdNumero());

            // Registro los detalles de la venta
            this.registrarComprasImpuestos(egreso);

            //UbicacionGeografica
            Location location = null;
            //Consulto en tabla Empresa por sn_localizacion y ademas valido el rango horario de localizacion
            if (_empresaBo.isRegistrarLocalizacion() && ComparatorDateTime.validarRangoHorarioEnvioLocalizacion()) {
                GPSManagement gps = new GPSManagement(context.getApplicationContext(), idMovil, UbicacionGeografica.OPERACION_EGRESO);
                gps.getLocation(false);
                ubicacionGeografica = gps.getUbicacionGeografica();
                location = gps.get_location();
            }

            //Registro el movimiento
            this.registrarMovimiento(egreso, Movimiento.ALTA, location);
            isGuardado = true;
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "createEgreso", e);
            throw e;
        }

        if (isGuardado && PreferenciaBo.getInstance().getPreferencia().isEnvioEgresoAutomatico()) {
            //Hago el control de envio de pedidos con la fecha de entrega
            int isEnvioDiferido = ComparatorDateTime.compareDates(egreso.getFeFactura(), Calendar.getInstance().getTime());

            if (ComparatorDateTime.validarRangoHorarioEnvioPedidos() &&
                    ((isEnvioDiferido == ComparatorDateTime.FECHA_ENVIO_MENOR)
                            || (isEnvioDiferido == ComparatorDateTime.FECHA_ENVIO_IGUAL))) {
                final UbicacionGeografica ubicacionGeograficaEnviar = ubicacionGeografica;
                //Si esta habilitado, envio el pedido
                Thread t = new Thread() {
                    public void run() {
                        try {
                            //enviarPedido(venta, ubicacionGeograficaEnviar, context);
                            SincronizacionBo sincronizacionBo = new SincronizacionBo(context, _repoFactory);
                            sincronizacionBo.enviarEgresosPendientes();
                        } catch (Exception e) {
                            //No se trata la exception porque el servicio va a intentar despues
                        }
                    }
                };
                t.start();
            }
        }
    }

    public void updateEgreso(Compra egreso, Context context) throws Exception {
        try {
            // Actualizo el movimiento de la venta como modificado
            MovimientoBo movimientoBo = new MovimientoBo(_repoFactory);
            Movimiento movimiento = movimientoBo.getMovimiento(egreso.getIdMovil());
            movimiento.setIsModificado(true);
            _movimientoRepository.updateIsModificado(movimiento);

            // Genero el nuevo idMovil
            String idMovil = generarIdMovil(egreso, Movimiento.MODIFICACION);
            egreso.setIdMovil(idMovil);
            // Actualizo la venta (cabecera)
            _compraRepository.update(egreso);

            _comprasImpuestosRepository.deleteComprasImpuestosByCompra(egreso);
            // Luego de eliminar registro nuevamente los detalles
            this.registrarComprasImpuestos(egreso);
            //Registrar ubicacionGeografica
            Location location = null;
            //Consulto en tabla Empresa por sn_localizacion y ademas valido el rango horario configurado

            if (_empresaBo.isRegistrarLocalizacion() && ComparatorDateTime.validarRangoHorarioEnvioLocalizacion()) {
                GPSManagement gps = new GPSManagement(context.getApplicationContext(), idMovil, UbicacionGeografica.OPERACION_VENTA);
                location = gps.getLocation(false);
            }

            // Registro el movimiento de la modificacion
            registrarMovimiento(egreso, Movimiento.MODIFICACION, location);
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "update", e);
            throw new Exception(e);
        }
    }

    private String generarIdMovil(Compra egreso, String tipoMovimiento) {
        Integer idVendedor = PreferenciaBo.getInstance().getPreferencia().getIdVendedor();
        String documento = egreso.getId().getIdFcncnd();
        int numero = egreso.getId().getIdNumero();
        Integer idProveedor = egreso.getId().getIdProveedor();
        Integer puntoVenta = egreso.getId().getIdPuntoVenta();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String fecha = null;
        if (tipoMovimiento == Movimiento.ALTA) {
            fecha = sdf.format(egreso.getFeIngreso());
        }
        if (tipoMovimiento == Movimiento.MODIFICACION) {
            fecha = sdf.format(Calendar.getInstance().getTime());
        }

        return idVendedor + "-" + fecha + "-" + documento + "-" + numero + "-" + puntoVenta + "-" + idProveedor;
    }

    private void registrarComprasImpuestos(Compra egreso) throws Exception {
        Iterator<ComprasImpuestos> iterator = egreso.getComprasImpuestos().iterator();
        int secuencia = 1;
        while (iterator.hasNext()) {
            ComprasImpuestos comprasImpuestos = iterator.next();
            createCompraImpuestos(egreso, comprasImpuestos, secuencia);
            secuencia++;
        }

    }

    private void createCompraImpuestos(Compra egreso, ComprasImpuestos comprasImpuestos, int secuencia) throws Exception {

        PkComprasImpuestos pk = new PkComprasImpuestos();
        pk.setIdPuntoVenta(egreso.getId().getIdPuntoVenta());
        pk.setIdNumero(egreso.getId().getIdNumero());
        pk.setIdFcncnd(egreso.getId().getIdFcncnd());
        pk.setIdProveedor(egreso.getId().getIdProveedor());
        pk.setIdImpuesto(comprasImpuestos.getImpuesto().getId());
        pk.setIdTipoab(egreso.getIdLetra());
        pk.setIdSecuencia(secuencia);
        comprasImpuestos.setId(pk);
        //comprasImpuestos.setIdMovil(egreso.getIdMovil());

        _comprasImpuestosRepository.create(comprasImpuestos);
    }

    private void registrarMovimiento(Compra egreso, String tipoMovimiento, Location location) throws Exception {
        //Registro el movimiento
        Movimiento movimiento = new Movimiento();
        movimiento.setTabla(Compra.TABLE);
        movimiento.setIdMovil(egreso.getIdMovil());
        movimiento.setTipo(tipoMovimiento);
        //Location
        if (location != null) {
            movimiento.setLocation(location);
        }
        MovimientoBo movimientoBo = new MovimientoBo(_repoFactory);
        movimientoBo.create(movimiento);
    }

    public List<Compra> recoveryAllEgresos() throws Exception {
        return _compraRepository.recoveryAll();
    }

    public List<Compra> recoveryEgresosByHoja(int idHoja, int idSucursal) throws Exception {
        return _compraRepository.recoverEgresosByHoja(idHoja, idSucursal);
    }

    public List<Compra> filtrarEgresos(List<Compra> egresosFiltrar, int posicionFiltro) {

        List<Compra> egresosFiltrados = new ArrayList<>();

        for (int index = 0; index < egresosFiltrar.size(); index++) {
            Compra egreso = egresosFiltrar.get(index);
            Movimiento movimientoRecibo = null;
            try {
                movimientoRecibo = _movimientoRepository.recoveryByIdMovil(egreso.getIdMovil());
            } catch (Exception e) {

            }

            Date fechaSincronizacionRecibo;

            if (movimientoRecibo != null) {
                fechaSincronizacionRecibo = movimientoRecibo.getFechaSincronizacion();
            } else {
                fechaSincronizacionRecibo = Calendar.getInstance().getTime();
            }

            if ((posicionFiltro == FrmListadoEgresos.POSITION_FILTRO_TODOS)
                    || (posicionFiltro == FrmListadoEgresos.POSITION_FILTRO_ENVIADOS && fechaSincronizacionRecibo != null)
                    || (posicionFiltro == FrmListadoEgresos.POSITION_FILTRO_NO_ENVIADOS && fechaSincronizacionRecibo == null)
            ) {
                egresosFiltrados.add(egreso);
            }
        }

        return egresosFiltrados;

    }

    public boolean isEnviado(Compra egreso) throws Exception {
        return _compraRepository.isEnviado(egreso);
    }

    public Compra recoveryEgresoByID(PkCompra pk) throws Exception {
        return _compraRepository.recoveryByID(pk);
    }

    public void anularEgreso(final Compra egreso) throws Exception {
        //boolean isAnulada = false;
        try {
            _compraRepository.delete(egreso);
            _comprasImpuestosRepository.anularComprasImpuestoByCompra(egreso);

            registrarMovimiento(egreso, Movimiento.BAJA, null);

            // Registro la fecha de anulacion del movimiento
            _movimientoRepository.updateFechaAnulacion(egreso);
            //isAnulada = true;
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "anularEgreso", e);
            throw new Exception(e);
        }
    }

}
