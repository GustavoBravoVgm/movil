package com.ar.vgmsistemas.bo;

import android.content.Context;
import android.location.Location;

import com.ar.vgmsistemas.entity.Cheque;
import com.ar.vgmsistemas.entity.CondicionVenta;
import com.ar.vgmsistemas.entity.CuentaCorriente;
import com.ar.vgmsistemas.entity.Deposito;
import com.ar.vgmsistemas.entity.Entrega;
import com.ar.vgmsistemas.entity.Movimiento;
import com.ar.vgmsistemas.entity.PagoEfectivo;
import com.ar.vgmsistemas.entity.Recibo;
import com.ar.vgmsistemas.entity.ReciboDetalle;
import com.ar.vgmsistemas.entity.Retencion;
import com.ar.vgmsistemas.entity.UbicacionGeografica;
import com.ar.vgmsistemas.entity.Vendedor;
import com.ar.vgmsistemas.entity.key.PkCuentaCorriente;
import com.ar.vgmsistemas.entity.key.PkRecibo;
import com.ar.vgmsistemas.gps.GPSManagement;
import com.ar.vgmsistemas.gps.UtilGps;
import com.ar.vgmsistemas.repository.IChequeRepository;
import com.ar.vgmsistemas.repository.IClienteRepository;
import com.ar.vgmsistemas.repository.ICuentaCorrienteRepository;
import com.ar.vgmsistemas.repository.IDepositoRepository;
import com.ar.vgmsistemas.repository.IEntregaRepository;
import com.ar.vgmsistemas.repository.IMovimientoRepository;
import com.ar.vgmsistemas.repository.IPagoEfectivoRepository;
import com.ar.vgmsistemas.repository.IReciboDetalleRepository;
import com.ar.vgmsistemas.repository.IReciboRepository;
import com.ar.vgmsistemas.repository.IRetencionRepository;
import com.ar.vgmsistemas.repository.IUbicacionGeograficaRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.CodeResult;
import com.ar.vgmsistemas.utils.ComparatorDateTime;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.Matematica;
import com.ar.vgmsistemas.view.informes.FrmListadoRecibo;
import com.ar.vgmsistemas.ws.ReciboWs;
import com.ar.vgmsistemas.ws.UbicacionGeograficaWs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ReciboBo {
    private static final String TAG = ReciboBo.class.getCanonicalName();

    // DAO
    private final IReciboRepository _reciboRepository;
    private final IChequeRepository _chequeRepository;
    private final IClienteRepository _clienteRepository;
    private final IRetencionRepository _retencionRepository;
    private final IPagoEfectivoRepository _pagoEfectivoRepository;
    private final IDepositoRepository _depositoRepository;
    private final IReciboDetalleRepository _reciboDetalleRepository;
    private final ICuentaCorrienteRepository _cuentaCorrienteRepository;
    private final IMovimientoRepository _movimientoRepository;
    private final IEntregaRepository _entregaRepository;
    private final IUbicacionGeograficaRepository _ubicacionGeograficaRepository;
    private final RepositoryFactory _repoFactory;
    // BO
    private EmpresaBo _empresaBo;

    public ReciboBo(RepositoryFactory repoFactory) {
        this._repoFactory = repoFactory;
        this._chequeRepository = repoFactory.getChequeRepository();
        this._depositoRepository = repoFactory.getDepositoRepository();
        this._entregaRepository = repoFactory.getEntregaRepository();
        this._reciboDetalleRepository = repoFactory.getReciboDetalleRepository();
        this._ubicacionGeograficaRepository = repoFactory.getUbicacionGeograficaRepository();
        this._reciboRepository = repoFactory.getReciboRepository();
        this._clienteRepository = repoFactory.getClienteRepository();
        this._retencionRepository = repoFactory.getRetencionRepository();
        this._pagoEfectivoRepository = repoFactory.getPagoEfectivoRepository();
        this._cuentaCorrienteRepository = repoFactory.getCuentaCorrienteRepository();
        this._movimientoRepository = repoFactory.getMovimientoRepository();
        this._empresaBo = new EmpresaBo(repoFactory);
    }

    public boolean guardar(Recibo reciboArmado, Context context) throws Exception {
        final Recibo reciboGuardar = reciboArmado;
        boolean isGuardado = false;
        try {
            if (validateRecibo(reciboGuardar)) {
                isGuardado = tratarReciboValido(reciboArmado, reciboGuardar, context);
            } else {
                throw new Exception(ErrorManager.ErrorNumeroRecibo);
            }

        } catch (Exception e) {
            ErrorManager.manageException(TAG, "create", e);
            throw e;
        }

        if (isGuardado) {
            int idPtoVentaGuardado = reciboGuardar.getId().getIdPuntoVenta();
            long idReciboGuardado = reciboGuardar.getId().getIdRecibo();
            // Actualizo el ultimo id generado
            PreferenciaBo.getInstance().getPreferencia().setIdPuntoVentaUltimoRecibo(idPtoVentaGuardado);
            PreferenciaBo.getInstance().getPreferencia().setIdUltimoRecibo(idReciboGuardado);
            // #2854
            reciboArmado.getId().setIdPuntoVenta(idPtoVentaGuardado);
            reciboArmado.getId().setIdRecibo(idReciboGuardado);
        }

        return isGuardado;
    }

    private boolean validateRecibo(Recibo recibo) throws Exception {
        if (PreferenciaBo.getInstance().getPreferencia().isReciboProvisorio()) {
            DocumentoBo bo = new DocumentoBo(_repoFactory);
            bo.updateDocumento(bo.recoveryDocumentoProv(recibo.getId().getIdPuntoVenta()), recibo.getId().getIdRecibo());
            return true;
        }
        return _reciboRepository.validateNumeroRecibo(recibo.getId().getIdRecibo());
    }

    private boolean tratarReciboValido(Recibo reciboArmado, final Recibo reciboGuardar, Context context) throws Exception {
        double prMargenTolerancia = _empresaBo.recoveryEmpresa().getPrecioMargenToteranciaMovil();
        String deviceId = UtilGps.getIMEI(context);

        String idMovil = reciboGuardar.getVendedor().getId() + "-" +
                Formatter.formatDateTime(reciboGuardar.getFechaMovil()) + "-" +
                reciboGuardar.getId().getIdRecibo() + "-" +
                reciboGuardar.getId().getIdPuntoVenta() + "-" +
                reciboGuardar.getCliente().getId().getIdCliente();
        reciboArmado.setIdMovil(idMovil);
        reciboGuardar.setObservacion(reciboArmado.getObservacion());
        reciboArmado.setDeviceId(deviceId);
        double totalDocumentosImputados = reciboGuardar.obtenerTotalDocumentosImputados();
        double totalPagos = reciboGuardar.obtenerTotalPagos();
        double diferencia = totalPagos - totalDocumentosImputados;
        List<CuentaCorriente> listadoComprobantesGenerados = new ArrayList<>();
        if (totalDocumentosImputados < 0 && totalPagos > 0) {
            CuentaCorriente cc = generarAdelanto(reciboArmado, totalPagos);
            reciboArmado.getComprobantesGenerados().add(cc);
            listadoComprobantesGenerados.add(cc);
        } else if ((diferencia > prMargenTolerancia) && (totalPagos > 0)) {
            //Genero anticipo si paga de mas
            double montoAdelanto = totalPagos - totalDocumentosImputados;
            CuentaCorriente cc = generarAdelanto(reciboArmado, montoAdelanto);
            reciboArmado.getComprobantesGenerados().add(cc);
            listadoComprobantesGenerados.add(cc);
        }
        reciboGuardar.setDeviceId(deviceId);
        reciboGuardar.setIdMovil(idMovil);
        //creo entrega
        Date fechaRegistro = Calendar.getInstance().getTime();
        reciboGuardar.setFechaRegistroMovil(fechaRegistro);

        /*if (PreferenciaBo.getInstance().getPreferencia().isReciboProvisorio()) {
            _reciboRepository.create(reciboGuardar);
        } else {
            _reciboRepository.update(reciboGuardar);
        }*/
        distribuirTotalReciboEntreDocsImputadosSinActualizarBd(reciboGuardar, totalPagos);
        controloPagosNegativos(reciboGuardar);
        Location location = registrarUbicacionGeografica(context, idMovil);

        this._reciboRepository.createReciboTransaction(reciboGuardar, location,
                PreferenciaBo.getInstance().getPreferencia().isReciboProvisorio(), listadoComprobantesGenerados);
        return true;
    }


    private Location registrarUbicacionGeografica(Context context, String idMovil) throws Exception {
        //Consulto en tabla Empresa por sn_localizacion y además valido el rango de envío de localización
        if (_empresaBo.isRegistrarLocalizacion() && ComparatorDateTime.validarRangoHorarioEnvioLocalizacion()) {
            GPSManagement gps = new GPSManagement(context.getApplicationContext(), idMovil, UbicacionGeografica.OPERACION_COBRANZA);
            gps.getLocation(false);
            return gps.get_location();
        } else {
            return null;
        }
    }

    private void controloPagosNegativos(final Recibo reciboGuardar) {
        int index = 0;
        while (index < reciboGuardar.getDetalles().size()) {
            ReciboDetalle reciboDetalle = reciboGuardar.getDetalles().get(index);
            if (reciboDetalle.getImportePagado() > 0) {
                index++;
            } else {
                reciboGuardar.getDetalles().remove(index);
            }
        }
    }

    private void distribuirTotalReciboEntreDocsImputadosSinActualizarBd(final Recibo reciboGuardar, double totalPagos) {

        double montoDisponible = totalPagos + reciboGuardar.calcularTotalCredito();//trae los comprobantes que restan
        double montoFactura = reciboGuardar.getTotalFactura();//trae la suma de todos los comprobantes que suman

        for (ReciboDetalle reciboDetalle : reciboGuardar.getDetalles()) {
            reciboDetalle.setRecibo(reciboGuardar);
            // Asigno el saldo que el documento tiene disponible en el movil (Tarea #2529)
            double saldoCtaCte = Matematica.restarPorcentaje(reciboDetalle.getCuentaCorriente().calcularSaldo(), reciboDetalle.getTaDtoRecibo());
            double saldoTruncado = Matematica.Round(saldoCtaCte, 2);
            reciboDetalle.getCuentaCorriente().setSaldoMovil(saldoTruncado);
            // #12186
            reciboDetalle.setSaldoMovil(saldoTruncado);

            //calculo cuanto pago para cada documento en cuenta corriente
            int signo = reciboDetalle.getCuentaCorriente().getSigno();
            double saldo = Matematica.restarPorcentaje(reciboDetalle.getCuentaCorriente().calcularSaldo(), reciboDetalle.getTaDtoRecibo());

            double totalPagado;

            if (signo < 0) {//Notas de creditos y anticipos se fija si el comprobante suma
                //Verifico cuanto puedo ocupar del credito
                saldo = saldo * signo;
                if (saldo >= montoFactura) {
                    totalPagado = montoFactura;
                    montoFactura = 0d;
                } else {
                    totalPagado = saldo;
                    montoFactura -= totalPagado;
                }

            } else {    //si el comprobante resta (ant, Nc)
                totalPagado = (montoDisponible >= saldo) ? saldo : montoDisponible;
                montoDisponible -= totalPagado;
            }
            if (totalPagado > 0) {
                //registro el pago en el recibo y marco para actualizar en la bd
                reciboDetalle.setImportePagado(Matematica.Round(totalPagado, 2));
                reciboDetalle.setSeActualizaEnBd(true);
                //actualizo el saldo en la cuenta corriente y marco para actualizar en la bd
                CuentaCorriente cuentaCorriente = reciboDetalle.getCuentaCorriente();
                cuentaCorriente.setTotalPagado(reciboDetalle.getCuentaCorriente().getTotalPagado() + totalPagado);
            }
        }
    }

    public List<Integer> getPuntosVenta(Vendedor vendedor, long sucCliente) throws Exception {
        if (PreferenciaBo.getInstance().getPreferencia().isReciboProvisorio()) {

            return getPuntosVentaRecibo(sucCliente);
        } else {
            return getPuntosVentaRecibo(vendedor);
        }
    }

    private List<Integer> getPuntosVentaRecibo(Vendedor vendedor) throws Exception {
        List<Integer> puntosVenta = _reciboRepository.getPuntosVentaRecibo(vendedor);
        return puntosVenta;
    }

    /**
     * @return punto de venta del recibo, caso para el que el recibo es provisorio
     * @throws
     */
    private List<Integer> getPuntosVentaRecibo(long sucCliente) throws Exception {
        List<Integer> puntosVenta = _reciboRepository.getPuntosVentaRecibo(sucCliente);
        return puntosVenta;
    }

    public synchronized int enviar(Recibo recibo, Context context) throws Exception {
        // Envío el recibo
        ReciboWs reciboWs = new ReciboWs(context);
        int resultWs = reciboWs.send(recibo);
        if (!(resultWs == CodeResult.RESULT_VENDEDOR_INVALID)) {
            //Consulto en tabla Empresa por sn_localizacion
            if (_empresaBo.isRegistrarLocalizacion()) {

                // Recupero la ubicación geográfica
                UbicacionGeografica ubicacionGeograficaRecibo = _ubicacionGeograficaRepository.recoveryByIdMovil(recibo.getIdMovil());

                // Envío la ubicación geográfica
                if (ubicacionGeograficaRecibo != null) {
                    if (ubicacionGeograficaRecibo.getFechaPosicionMovil() != null) {

                        UbicacionGeograficaWs ubicacionGeograficaWs = new UbicacionGeograficaWs(context);
                        ubicacionGeograficaWs.send(ubicacionGeograficaRecibo);
                        //Actualizo fecha de sincronizacion ubicacion geográfica
                        _ubicacionGeograficaRepository.updateFechaSincronizacion(ubicacionGeograficaRecibo.getIdLegajo(), ubicacionGeograficaRecibo.getFechaPosicionMovil());
                    }
                }
            }
            // Por ahora se dejo esto asi por temas de compatibilidad pero habria que sacar afuera del hilo
            _movimientoRepository.updateFechaSincronizacion(recibo);
        }
        tratarResultado(resultWs, recibo.getId());
        return resultWs;

    }

    public void tratarResultado(int resultado, PkRecibo idRecibo) throws Exception {
        _reciboRepository.updateResultEnvio(idRecibo, resultado);
    }

    public long getSiguienteNumeroRecibo(int idPuntoVenta) throws Exception {
        if (PreferenciaBo.getInstance().getPreferencia().isReciboProvisorio()) {
            DocumentoBo bo = new DocumentoBo(_repoFactory);
            return bo.recoveryNumeroReciboProv(idPuntoVenta);
        }
        long nroRecibo = _reciboRepository.getSiguienteNumeroRecibo(idPuntoVenta);
        long nroReciboValidado = getNumeroReciboValidado(idPuntoVenta, nroRecibo);
        return nroReciboValidado;
    }

    public int tipoImpresionRecibo(int idPuntoVenta) throws Exception {
        /* return 	0: No imprime
         ***			1: Imprime completo (valor por defecto)
         ***			2: Sin Nombre Empresa
         ***/
        DocumentoBo bo = new DocumentoBo(_repoFactory);

        if (PreferenciaBo.getInstance().getPreferencia().isReciboProvisorio()) {
            return bo.recoveryDocumentoProv(idPuntoVenta).getTiImpresionMovil();
        } else {
            return bo.recoveryById("RC", "X", idPuntoVenta).getTiImpresionMovil();
        }

    }

    private long getNumeroReciboValidado(int idPuntoVenta, long idNumeroRecibo) throws Exception {
        long ultimoIdNumeroRecibo = PreferenciaBo.getInstance().getPreferencia().getIdUltimoRecibo();
        if (idNumeroRecibo > ultimoIdNumeroRecibo) {
            return idNumeroRecibo;
        } else {
            // Marco como imputados los recibos con numeros menores e iguales al ultimoIdNumeroRecibo
            _reciboRepository.updateEstadoImputado(idPuntoVenta, ultimoIdNumeroRecibo);
            return _reciboRepository.getSiguienteNumeroRecibo(idPuntoVenta);
        }
    }

    public List<Recibo> recoveryNoEnviados() throws Exception {
        List<Recibo> recibos = _reciboRepository.recoveryNoEnviados();
        for (Recibo recibo : recibos) {
            recibo = recoveryRecibo(recibo);
        }
        return recibos;
    }

    public List<Recibo> recoveryNoRendidos() throws Exception {
        List<Recibo> recibos = _reciboRepository.recoveryNoRendidos();
        for (Recibo recibo : recibos) {
            recibo = recoveryRecibo(recibo);
        }
        return recibos;
    }

    public List<Recibo> recoveryAll() throws Exception {
        return _reciboRepository.recoveryAll();
    }

    private CuentaCorriente generarAdelanto(Recibo recibo, double monto) throws Exception {

        CuentaCorrienteBo cuentaCorrienteBo = new CuentaCorrienteBo(_repoFactory);

        CuentaCorriente cuentaCorriente = new CuentaCorriente();
        PkCuentaCorriente id = new PkCuentaCorriente();
        id.setCuota(1);
        id.setIdLetra("X");
        id.setIdDocumento(CuentaCorriente.DOCUMENTO_ANTICIPO);
        long numeroAdelanto = cuentaCorrienteBo.getNumeroAdelanto();
        id.setIdNumeroDocumento(numeroAdelanto);
        id.setPuntoVenta(PreferenciaBo.getInstance().getPreferencia().getIdPuntoVentaPorDefecto());

        cuentaCorriente.setCliente(recibo.getCliente());
        cuentaCorriente.setVendedor(recibo.getVendedor());
        cuentaCorriente.setId(id);
        cuentaCorriente.setTotalCuota(monto);
        cuentaCorriente.setTotalPagado(0);
        cuentaCorriente.setTotalNotaCredito(0);
        cuentaCorriente.setSigno(-1); //resta a la cuenta corriente
        CondicionVentaBo condicionVentaBo = new CondicionVentaBo(_repoFactory);
        //uso la condicion de venta contado, por usar alguna nomas mi gente
        CondicionVenta condicionVenta = condicionVentaBo.getCondicionVentaContado();
        cuentaCorriente.setCondicionVenta(condicionVenta);
        //fecha de today
        Date fecha = Calendar.getInstance().getTime();
        cuentaCorriente.setFechaVenta(fecha);

        //Seteo el id_movil del recibo al DocumentoCuenta
        cuentaCorriente.setIdMovil(recibo.getIdMovil());
        //_cuentaCorrienteRepository.create(cuentaCorriente, recibo);

        return cuentaCorriente;
    }

    public Recibo recoveryRecibo(Recibo recibo) throws Exception {

        List<ReciboDetalle> detalles = _reciboDetalleRepository.recoveryByRecibo(recibo);
        recibo.setDetalles(detalles);
        Entrega entrega = recibo.getEntrega();

        List<Cheque> cheques = _chequeRepository.recoveryByEntrega(entrega);
        recibo.getEntrega().setCheques(cheques);

        List<PagoEfectivo> pagosEfectivo = _pagoEfectivoRepository.recoveryByEntrega(entrega);
        recibo.getEntrega().setPagosEfectivo(pagosEfectivo);

        List<Retencion> retenciones = _retencionRepository.recoveryByEntrega(entrega);
        recibo.getEntrega().setRetenciones(retenciones);

        List<Deposito> depositos = _depositoRepository.recoveryByEntrega(entrega);
        recibo.getEntrega().setDepositos(depositos);

        List<CuentaCorriente> comprobantesGenerados = _cuentaCorrienteRepository.recoveryByRecibo(recibo);
        recibo.setComprobantesGenerados(comprobantesGenerados);

        return recibo;
    }

    public List<Recibo> filtrarRecibos(List<Recibo> recibosFiltrar, Context context, int posicionFiltro) {

        List<Recibo> recibosFiltrados = new ArrayList<>();

        for (int index = 0; index < recibosFiltrar.size(); index++) {
            Recibo recibo = recibosFiltrar.get(index);
            Movimiento movimientoRecibo = null;
            try {
                movimientoRecibo = _movimientoRepository.recoveryByIdMovil(recibo.getIdMovil());
            } catch (Exception e) {

            }

            Date fechaSincronizacionRecibo = null;

            if (movimientoRecibo != null) {
                fechaSincronizacionRecibo = movimientoRecibo.getFechaSincronizacion();
            } else {
                fechaSincronizacionRecibo = Calendar.getInstance().getTime();
            }

            if ((posicionFiltro == FrmListadoRecibo.POSITION_FILTRO_TODOS)
                    || (posicionFiltro == FrmListadoRecibo.POSITION_FILTRO_ENVIADOS && fechaSincronizacionRecibo != null)
                    || (posicionFiltro == FrmListadoRecibo.POSITION_FILTRO_NO_ENVIADOS && fechaSincronizacionRecibo == null)
            ) {
                recibosFiltrados.add(recibo);
            }
        }

        return recibosFiltrados;

    }

}
