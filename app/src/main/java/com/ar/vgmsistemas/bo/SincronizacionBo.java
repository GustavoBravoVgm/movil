package com.ar.vgmsistemas.bo;

import static android.content.Context.MODE_PRIVATE;
import static com.ar.vgmsistemas.ws.GenericWs.KEY_TOKEN;
import static com.ar.vgmsistemas.ws.GenericWs.PREFERENCIA;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.AuditoriaGps;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.Compra;
import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.entity.ListaHojaDetalle;
import com.ar.vgmsistemas.entity.ListaPkHojaDetalle;
import com.ar.vgmsistemas.entity.ListaVentas;
import com.ar.vgmsistemas.entity.NoPedido;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.Recibo;
import com.ar.vgmsistemas.entity.UbicacionGeografica;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.key.PkHojaDetalle;
import com.ar.vgmsistemas.entity.key.PkVenta;
import com.ar.vgmsistemas.exclusionmutua.Monitor;
import com.ar.vgmsistemas.repository.IMovimientoRepository;
import com.ar.vgmsistemas.repository.IReciboRepository;
import com.ar.vgmsistemas.repository.IRecursoHumanoRepository;
import com.ar.vgmsistemas.repository.IVentaDetalleRepository;
import com.ar.vgmsistemas.repository.IVentaRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.task.sincronizacion.BuscarConexionTask;
import com.ar.vgmsistemas.utils.CodeResult;
import com.ar.vgmsistemas.utils.ComparatorDateTime;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Exception.CustomExceptionFactory;
import com.ar.vgmsistemas.utils.Exception.CustomExceptionInvalidSeller;
import com.ar.vgmsistemas.utils.FileManager;
import com.ar.vgmsistemas.ws.AuditoriaGpsWs;
import com.ar.vgmsistemas.ws.ConnectionMannager;
import com.ar.vgmsistemas.ws.SincronizacionWs;
import com.ar.vgmsistemas.ws.UbicacionGeograficaWs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

public class SincronizacionBo extends Observable {

    private static final String TAG = SincronizacionBo.class.getCanonicalName();

    private final IMovimientoRepository _movimientoRepository;
    private final IVentaRepository _ventaRepository;
    private final IReciboRepository _reciboRepository;
    private final IRecursoHumanoRepository _recursoHumanoRepository;
    private final IVentaDetalleRepository _ventaDetalleRepository;

    private int pedidosEnviados;
    private int noPedidosEnviados;
    private Context _context;

    //Bo's
    private EntregaBo _entregaBo;
    private EmpresaBo _empresaBo;
    private UbicacionGeograficaBo _ubicacionGeograficaBo;
    //BD
    private final RepositoryFactory _repoFactory;

    public SincronizacionBo(Context context, RepositoryFactory repoFactory) {
        this._context = context;
        this._repoFactory = repoFactory;
        this._empresaBo = new EmpresaBo(_repoFactory);
        this._ubicacionGeograficaBo = new UbicacionGeograficaBo(_repoFactory);
        this._movimientoRepository = repoFactory.getMovimientoRepository();
        this._ventaRepository = repoFactory.getVentaRepository();
        this._reciboRepository = repoFactory.getReciboRepository();
        this._recursoHumanoRepository = repoFactory.getRecursoHumanoRepository();
        this._ventaDetalleRepository = repoFactory.getVentaDetalleRepository();

    }

    public boolean isDatosPendientesEnvio() throws Exception {
        return _movimientoRepository.isDatosPendientesEnvio();
    }

    public boolean descargarDatos() throws Exception {
        boolean isDatosDescargados = false;
        if (!FileManager.isSdPresent())
            throw new Exception(ErrorManager.ErrorAccesoSdCard);
        else {
            if (this._repoFactory.validateEspacioSDSincronizacion()) {
                ConnectionMannager cm = new ConnectionMannager(_context);
                if (!cm.isConnected())
                    throw new Exception(ErrorManager.ErrorConexionDatos);
                else if (!cm.isConexionActiva())
                    throw new Exception(ErrorManager.ErrorConexionServidor);
                else {
                    // Valido que tenga el archivo de base de datos
                    if (this._repoFactory.dataBaseExists()) {
                        // Valido que existan datos pendientes de envio
                        boolean datosPendientesEnvio = isDatosPendientesEnvio();
                        // Valido que existan pedidos posteriores
                        //boolean pedidosPosteriores = isPedidosEnvioPosterior();
                        if (datosPendientesEnvio) {
                            // Envio lo pendiente y descargo
                            boolean enviosOk = enviarDatos();
                            if (!enviosOk) {
                                throw new Exception(
                                        ErrorManager.ErrorEnvioDatosServidor
                                                + " "
                                                + ErrorManager.ErrorDescargaDatosServidor);
                            }
                            isDatosDescargados = gestionarDescargaDatos();

                        } else {
                            // No tiene el archivo de base de datos, realizo la
                            // descarga
                            isDatosDescargados = gestionarDescargaDatos();
                        }
                    } else {
                        // No tiene el archivo de base de datos, realizo la
                        // descarga
                        isDatosDescargados = gestionarDescargaDatos();
                    }
                }
            } else {
                throw new Exception(ErrorManager.ErrorTarjetaSDLlena);
            }

        }
        return isDatosDescargados;
    }

    private boolean gestionarDescargaDatos() throws Exception {
        boolean isDatosDescargados = false;
        Monitor.lock();
        try {
            this.descargarBaseDatos();
            // Actualiza la ultima fecha de sincronizacion
            PreferenciaBo.getInstance().getPreferencia(_context)
                    .setFechaUltimaDescarga(Calendar.getInstance().getTime());
            // Obtengo y actualizo el ultimo numero de recibo disponible
            int puntoVenta = PreferenciaBo.getInstance().getPreferencia(_context)
                    .getIdPuntoVentaPorDefecto();
            long numeroRecibo = _reciboRepository.getSiguienteNumeroRecibo(puntoVenta) - 1;
            PreferenciaBo.getInstance().getPreferencia(_context)
                    .setIdPuntoVentaUltimoRecibo(puntoVenta);
            PreferenciaBo.getInstance().getPreferencia(_context)
                    .setIdUltimoRecibo(numeroRecibo);
            isDatosDescargados = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Monitor.unlock();
        return isDatosDescargados;
    }

    private boolean isPedidosEnvioPosterior() throws Exception {
        boolean isPedidosEnvioPosterior = false;
        // Consulto en la base de datos por los pedidos posteriores
        // Primero valido que exista la tabla ventas
        // Consulto en la base de datos por los pedidos posteriores
        if (_ventaRepository.getCantidadVentasPosteriores() > 0) {
            isPedidosEnvioPosterior = true;
        }
        return isPedidosEnvioPosterior;
    }

    public boolean enviarDatos() throws Exception {
        boolean pedidosDeshabilitados = false;
        boolean enviosOk = false;
        boolean resultParcial;
        if (isConexionDatosDisponible()) {
            enviosOk = true;
            try {
                resultParcial = enviarPedidosPendientes();
                if (resultParcial != true) {
                    enviosOk = false;
                }
            } catch (Exception e) {
                if (e.getMessage().equals(ErrorManager.ErrorPedidosDeshabilitados)) {
                    pedidosDeshabilitados = true;
                }
                e.printStackTrace();
                enviosOk = false;
            }
            try {
                resultParcial = enviarNoPedidosPendientes();
                if (resultParcial != true) {
                    enviosOk = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                enviosOk = false;
            }
            try {
                resultParcial = enviarClientes();
                if (resultParcial != true) {
                    enviosOk = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                enviosOk = false;
            }
            try {
                resultParcial = enviarRecibosPendientes();
                if (resultParcial != true) {
                    enviosOk = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                enviosOk = false;
            }
            try {
                resultParcial = enviarUbicacionesGeograficasPendientes();
                if (resultParcial != true) {
                    enviosOk = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                enviosOk = false;
            }
            try {
            } catch (Exception e) {
                e.printStackTrace();
                //enviosOk = false;
            }

            try {
                resultParcial = enviarHojasDetallePendientes();
                if (resultParcial != true) {
                    enviosOk = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e.getMessage());
            }
            try {
                resultParcial = enviarHojasDetallesPendientes();
                if (resultParcial != true) {
                    enviosOk = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e.getMessage());
            }

            try {
                resultParcial = enviarEgresosPendientes();
                if (resultParcial != true) {
                    enviosOk = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                enviosOk = false;
            }
        }
        if (pedidosDeshabilitados) {
            throw new Exception(ErrorManager.ErrorPedidosDeshabilitados);
        }
        /*enviarErroresPendientes();*/
        return enviosOk;
    }

    /**
     * Valido tener acceso a la tarjeta SD y disponer de conectividad
     *
     * @return
     * @throws Exception
     */
    public boolean isConexionDatosDisponible() throws Exception {

        ConnectionMannager cm = new ConnectionMannager(_context);
        if (!FileManager.isSdPresent()) {
            throw new Exception(ErrorManager.ErrorAccesoSdCard);
        } else if (!cm.isConnected()) {
            throw new Exception(ErrorManager.ErrorConexionDatos);
        } else if (!cm.isConexionActiva()) {
            throw new Exception(ErrorManager.ErrorConexionServidor);
        } else {
            return true;
        }

    }

    public boolean enviarUbicacionesGeograficasPendientes() throws Exception {
        boolean envioOK = true;
        int result = CodeResult.RESULT_OK;
        // Consulto en tabla Empresa por sn_localizacion
        if (_empresaBo.isRegistrarLocalizacion()) {
            if (_movimientoRepository.getCantidadUbicacionesGeograficasSinEnviar() > 0) {

                List<UbicacionGeografica> ubicacionesSinEnviar = _ubicacionGeograficaBo.recoveryNoEnviados();
                Iterator<UbicacionGeografica> iterator = ubicacionesSinEnviar
                        .iterator();
                while (iterator.hasNext()) {
                    UbicacionGeografica ubicacionGeografica = (UbicacionGeografica) iterator
                            .next();
                    try {
                        // Llamar al web service para enviar
                        UbicacionGeograficaWs ubicacionWs = new UbicacionGeograficaWs(_context);
                        int resultParcial = ubicacionWs.send(ubicacionGeografica);
                        if (resultParcial == CodeResult.RESULT_OK) {
                            _ubicacionGeograficaBo
                                    .updateFechaSincronizacion(ubicacionGeografica);
                        }
                        if (result == CodeResult.RESULT_OK) {
                            result = resultParcial;
                        }
                    } catch (Exception ex) {
                        envioOK = false;
                        ErrorManager.manageException(TAG,
                                "enviarUbicacionesGeograficasPendientes", ex);
                    }
                }
            }
        }
        if (result != CodeResult.RESULT_OK) {
            envioOK = false;
        }
        return envioOK;
    }

    public boolean enviarClientes() throws Exception {
        boolean envioOK = true;
        int result = CodeResult.RESULT_OK;
        if (_movimientoRepository.getCantidadClientesSinEnviar() > 0) {
            ClienteBo clienteBo = new ClienteBo(_repoFactory);
            List<Cliente> clientes = clienteBo.recoveryNoEnviados();
            Iterator<Cliente> iterator = clientes.iterator();
            while (iterator.hasNext()) {
                Cliente cliente = (Cliente) iterator.next();
                try {
                    int resultParcial = clienteBo.send(cliente, _context);
                    if (resultParcial == CodeResult.RESULT_OK) {
                        _movimientoRepository.updateFechaSincronizacion(cliente);
                    }
                    if (result == CodeResult.RESULT_OK) {
                        result = resultParcial;
                    }
                } catch (Exception ex) {
                    envioOK = false;
                    ErrorManager.manageException(TAG, "enviarPedidosPendientes", ex);
                }
            }
        }
        if (result != CodeResult.RESULT_OK) {
            envioOK = false;
        }
        return envioOK;
    }

    public boolean enviarHojasDetallePendientes() throws Exception {
        boolean envioOK = true;
        int result = CodeResult.RESULT_OK;
        // Evaluo si hay pedidos pendientes de envio
        if (_movimientoRepository.getCantidadHojasDetalleSinEnviar() > 0) {
            try {
                HojaDetalleBo hojaDetalleBo = new HojaDetalleBo(_repoFactory);
                UbicacionGeograficaBo ubicacionGeograficaBo = new UbicacionGeograficaBo(_repoFactory);
                // Valido la hora/minutos actual este dentro del rango de fechas en
                // caso de habilitarlo
                //if (ComparatorDateTime.validarRangoHorarioEnvioPedidos()) {
                // En caso afirmativo, env?o los pedidos
                List<HojaDetalle> hojasDetalles = hojaDetalleBo.recoveryAEnviar();
                List<PkHojaDetalle> pkHojasDetalle = new ArrayList<>();
                ListaPkHojaDetalle listaPkHojaDetalle = new ListaPkHojaDetalle();
                for (int i = 0; i < hojasDetalles.size(); i++) {
                    pkHojasDetalle.add(hojasDetalles.get(i).getId());
                }
                listaPkHojaDetalle.setPkHojasDetalles(pkHojasDetalle);
                hojaDetalleBo.estanEnHoja(listaPkHojaDetalle, _context);
                // Si entro aca y no pudo recuperar ventas -> hay pedidos con
                // entrega posterior -> No le dejo sincronizar
                Iterator<HojaDetalle> iterator = hojasDetalles.iterator();
                while (iterator.hasNext()) {
                    HojaDetalle hojaDetalle = (HojaDetalle) iterator.next();
                    try {
                        PkVenta pkVenta = new PkVenta();
                        pkVenta.setIdDocumento(hojaDetalle.getId().getIdFcnc());
                        pkVenta.setIdLetra(hojaDetalle.getId().getIdTipoab());
                        pkVenta.setIdNumeroDocumento(hojaDetalle.getId().getIdNumdoc());
                        pkVenta.setPuntoVenta(hojaDetalle.getId().getIdPtovta());
                        hojaDetalle.setEntrega(hojaDetalleBo.recoveryEntregas(hojaDetalle.getEntrega()));

                        Venta credito = _ventaRepository.getCredito(pkVenta);
                        if (credito != null)
                            credito.setDetalles(_ventaDetalleRepository.recoveryByVenta(credito));
                        UbicacionGeografica ubicacion = ubicacionGeograficaBo.recoveryByIdMovil(hojaDetalle.getIdMovil());
                        HojaDetalleBo bo = new HojaDetalleBo(_repoFactory);
                        boolean prerendida = bo.isPrerendida(hojaDetalle.getId().getIdHoja());
                        int resultParcial = hojaDetalleBo.enviarHojaDetalle(hojaDetalle, credito, ubicacion, prerendida, _context);
                        if (result == CodeResult.RESULT_OK) {
                            result = resultParcial;
                        }
                        // Hago una pausa de 2000 ms (2 segundos) para el envio
                        // entre pedido y pedido
                        Thread.sleep(3000);

                    } catch (Exception ex) {
                        envioOK = false;
                        ErrorManager.manageException(TAG,
                                "enviarPedidosPendientes", ex);
                    }
                }
            } catch (Exception e) {
                result = CodeResult.ERROR_HOJA_CAMBIADA;
            }
        }
        if (result == CodeResult.RESULT_PEDIDOS_NO_AUTORIZADO) {
            throw new Exception(ErrorManager.ErrorPedidosDeshabilitados);
        }
        if (result == CodeResult.RESULT_VENDEDOR_INVALID) {
            throw new Exception(ErrorManager.ErrorVendedorNoValido);
        }
        if (result == CodeResult.ERROR_HOJA_CAMBIADA) {
            throw new Exception(ErrorManager.ErrorHojasDetalles);
        }
        if (result != CodeResult.RESULT_OK) {
            envioOK = false;
        }
        return envioOK;
    }

    //Esta funcion envia las hojas detalles que tienen una misma entrega
    public boolean enviarHojasDetallesPendientes() throws Exception {
        boolean envioOK = true;
        int result = CodeResult.RESULT_OK;
        if (_movimientoRepository.getCantidadHojasDetalleSinEnviar() > 0) {
            try {
                HojaDetalleBo hojaDetalleBo = new HojaDetalleBo(_repoFactory);
                UbicacionGeograficaBo ubicacionGeograficaBo = new UbicacionGeograficaBo(_repoFactory);
                //Esta funcion trae 1 hoja detalle de cada grupo de hojas Detalles que comparten una misma entrega y que ester pediente de envio
                List<HojaDetalle> hojasDetalles = hojaDetalleBo.recoveryAEnviarUnicaEntrega();
                List<PkHojaDetalle> pkHojasDetalle = new ArrayList<PkHojaDetalle>();
                ListaPkHojaDetalle listaPkHojaDetalle = new ListaPkHojaDetalle();
                for (int i = 0; i < hojasDetalles.size(); i++) {
                    pkHojasDetalle.add(hojasDetalles.get(i).getId());
                }
                listaPkHojaDetalle.setPkHojasDetalles(pkHojasDetalle);
                hojaDetalleBo.estanEnHoja(listaPkHojaDetalle, _context);
                //METODO MULTIPLE
                Iterator<HojaDetalle> iterator = hojasDetalles.iterator();
                while (iterator.hasNext()) {
                    HojaDetalle hojaDetalle = (HojaDetalle) iterator.next();
                    ListaHojaDetalle listaHojaDetalle = new ListaHojaDetalle();
                    List<HojaDetalle> hojaDetallesPorEntrega = new ArrayList<>();
                    listaHojaDetalle.setHojasDetalles(hojaDetallesPorEntrega);
                    ListaVentas listaCreditos = new ListaVentas();
                    List<Venta> creditos = new ArrayList<>();
                    listaCreditos.setVentas(creditos);

                    try {
                        List<HojaDetalle> hojasDetallesUnicaEntrega = hojaDetalleBo.recoveryByIdEntrega(hojaDetalle.getEntrega().getId());
                        for (HojaDetalle hojaDetalleUnicaEntrega : hojasDetallesUnicaEntrega) {
                            PkVenta pkVenta = new PkVenta();
                            pkVenta.setIdDocumento(hojaDetalleUnicaEntrega.getId().getIdFcnc());
                            pkVenta.setIdLetra(hojaDetalleUnicaEntrega.getId().getIdTipoab());
                            pkVenta.setIdNumeroDocumento(hojaDetalleUnicaEntrega.getId().getIdNumdoc());
                            pkVenta.setPuntoVenta(hojaDetalleUnicaEntrega.getId().getIdPtovta());
                            hojaDetalleUnicaEntrega.setEntrega(hojaDetalleBo.recoveryEntregas(hojaDetalleUnicaEntrega.getEntrega()));
                            Venta credito = _ventaRepository.getCredito(pkVenta);
                            if (credito != null) {
                                credito.setDetalles(_ventaDetalleRepository.recoveryByVenta(credito));
                                creditos.add(credito);
                            }
                            hojaDetallesPorEntrega.add(hojaDetalleUnicaEntrega);
                        }
                        UbicacionGeografica ubicacion = ubicacionGeograficaBo.recoveryByIdMovil(hojaDetalle.getIdMovil());
                        HojaDetalleBo bo = new HojaDetalleBo(_repoFactory);
                        boolean prerendida = bo.isPrerendida(hojaDetalle.getId().getIdHoja());
                        int resultParcial = hojaDetalleBo.enviarHojasDetalles(listaHojaDetalle, listaCreditos, ubicacion, prerendida, _context);
                        if (result == CodeResult.RESULT_OK) {
                            result = resultParcial;
                        }
                        Thread.sleep(3000);
                    } catch (Exception ex) {
                        envioOK = false;
                        ErrorManager.manageException(TAG, "enviarPedidosPendientes", ex);
                    }
                }
            } catch (Exception e) {
                result = CodeResult.ERROR_HOJA_CAMBIADA;
            }
        }
        if (result == CodeResult.RESULT_PEDIDOS_NO_AUTORIZADO) {
            throw new Exception(ErrorManager.ErrorPedidosDeshabilitados);
        }
        if (result == CodeResult.RESULT_VENDEDOR_INVALID) {
            throw new Exception(ErrorManager.ErrorVendedorNoValido);
        }
        if (result == CodeResult.ERROR_HOJA_CAMBIADA) {
            throw new Exception(ErrorManager.ErrorHojasDetalles);
        }
        if (result != CodeResult.RESULT_OK) {
            envioOK = false;
        }
        return envioOK;
    }

    public boolean enviarRecibosPendientes() throws Exception {
        boolean envioOK = true;
        int result = CodeResult.RESULT_OK;
        if (_movimientoRepository.getCantidadRecibosSinEnviar() > 0) {
            ReciboBo reciboBo = new ReciboBo(_repoFactory);
            List<Recibo> recibos = reciboBo.recoveryNoEnviados();
            Iterator<Recibo> iterator = recibos.iterator();
            while (iterator.hasNext()) {
                Recibo recibo = (Recibo) iterator.next();
                try {
                    // La ubicacion geografica del recibo la recupera dentro del
                    // metodo enviar
                    int resultParcial = reciboBo.enviar(recibo, _context);
                    if (result == CodeResult.RESULT_OK)
                        result = resultParcial;

                } catch (Exception ex) {
                    envioOK = false;
                    ErrorManager.manageException(TAG, "enviarRecibosPendientes", ex);
                }
            }
        }
        if (result == CodeResult.RESULT_VENDEDOR_INVALID) {
            throw new Exception(ErrorManager.ErrorVendedorNoValido);
        }
        if (result != CodeResult.RESULT_OK)
            envioOK = false;

        return envioOK;
    }

    public boolean enviarPedidosPendientes() throws Exception {
        boolean envioOK = true;
        int result = CodeResult.RESULT_OK;
        // Evaluo si hay pedidos pendientes de envio
        if (_movimientoRepository.getCantidadPedidosSinEnviar() > 0) {
            VentaBo ventaBo = new VentaBo(_repoFactory);
            // Valido la hora/minutos actual este dentro del rango de fechas en
            // caso de habilitarlo
            if (ComparatorDateTime.validarRangoHorarioEnvioPedidos()) {
                // En caso afirmativo, env?o los pedidos
                List<Venta> ventas = ventaBo.recoveryAEnviar(PreferenciaBo.getInstance().getPreferencia().getIdTipoDocumentoPorDefecto());
                // Si entro aca y no pudo recuperar ventas -> hay pedidos con
                // entrega posterior -> No le dejo sincronizar
                Iterator<Venta> iterator = ventas.iterator();
                while (iterator.hasNext()) {
                    Venta venta = (Venta) iterator.next();
                    try {
                        venta = ventaBo.recoveryById(venta.getId());
                        // ventaBo.enviarPedido(venta);

                        int resultParcial = ventaBo.enviarPedido(venta,
                                venta.getUbicacionGeografica(), _context);
                        if (result == CodeResult.RESULT_OK)
                            result = resultParcial;
                        // Hago una pausa de 2000 ms (2 segundos) para el envio
                        // entre pedido y pedido
                        Thread.sleep(3000);

                    } catch (Exception ex) {
                        envioOK = false;
                        ErrorManager.manageException(TAG, "enviarPedidosPendientes", ex);
                    }
                }
            } else {
                throw new Exception(_context.getString(R.string.msjFueraRangoHorarioEnvioPedidos));
            }
        }
        if (result == CodeResult.RESULT_PEDIDOS_NO_AUTORIZADO) {
            throw new Exception(ErrorManager.ErrorPedidosDeshabilitados);
        }
        if (result == CodeResult.RESULT_VENDEDOR_INVALID) {
            throw new Exception(ErrorManager.ErrorVendedorNoValido);
        }
        if (result != CodeResult.RESULT_OK)
            return false;

        return envioOK;
    }

    private boolean enviarAuditoriasGpsPendientes() throws Exception {
        boolean resultOk = true;
        AuditoriaGpsBo auditoriaGpsBo = new AuditoriaGpsBo(_repoFactory);
        AuditoriaGpsWs auditoriaGpsWs = new AuditoriaGpsWs(_context);
        List<AuditoriaGps> auditoriasGps = auditoriaGpsBo.recoveryAEnviar();
        for (AuditoriaGps aud : auditoriasGps) {
            int result = CodeResult.RESULT_ERROR;
            try {
                result = auditoriaGpsWs.send(aud);
                if (result != CodeResult.RESULT_OK)
                    resultOk = false;

            } catch (Exception e) {
                resultOk = false;
            }
            if (result == CodeResult.RESULT_OK) {
                _movimientoRepository.updateFechaSincronizacion(aud);
            }

        }
        return resultOk;
    }

    public boolean enviarNoPedidosPendientes() throws Exception {
        boolean envioOK = true;
        int result = CodeResult.RESULT_ERROR;
        if (_movimientoRepository.getCantidadNoPedidosSinEnviar() > 0) {
            NoPedidoBo noPedidoBo = new NoPedidoBo(_repoFactory);
            List<NoPedido> noPedidos = noPedidoBo.recoveryNoEnviadas();
            Iterator<NoPedido> iterator = noPedidos.iterator();
            while (iterator.hasNext()) {
                NoPedido noPedido = (NoPedido) iterator.next();
                {
                    try {
                        noPedido.setVendedor(VendedorBo.getVendedor());
                        // La ubicacion geográfica del no pedido la recupera
                        // dentro del metodo enviarNopedido
                        result = noPedidoBo.enviarNoPedido(noPedido, _context);
                        if (result != CodeResult.RESULT_OK) {
                            envioOK = false;
                        }

                    } catch (Exception ex) {
                        envioOK = false;
                        ErrorManager.manageException(TAG, "enviarNoPedidosPendientes", ex);
                    }
                }
            }
        }
        if (result == CodeResult.RESULT_VENDEDOR_INVALID) {
            throw new Exception(ErrorManager.ErrorVendedorNoValido);
        }

        return envioOK;
    }

    public boolean enviarEgresosPendientes() throws Exception {
        boolean envioOK = true;
        int result = CodeResult.RESULT_OK;
        if (_movimientoRepository.getCantidadEgresosSinEnviar() > 0) {
            RendicionBo rendicionBo = new RendicionBo(_repoFactory);
            List<Compra> egresos = rendicionBo.recoveryEgresosNoEnviados();
            Iterator<Compra> iterator = egresos.iterator();
            while (iterator.hasNext()) {
                Compra egreso = (Compra) iterator.next();
                try {
                    // La ubicacion geografica del recibo la recupera dentro del
                    // metodo enviar
                    result = rendicionBo.enviarEgreso(egreso, _context);
                    if (result != CodeResult.RESULT_OK) {
                        envioOK = false;
                    }

                } catch (Exception ex) {
                    envioOK = false;
                    ErrorManager.manageException(TAG, "enviarEgresosPendientes", ex);
                }
            }
        }
        if (result == CodeResult.RESULT_VENDEDOR_INVALID) {
            throw new Exception(ErrorManager.ErrorVendedorNoValido);
        }
        return envioOK;
    }

    private void descargarBaseDatos() throws Exception {
        boolean isBackUpOk = false;
        // Inicio sincronizacion
        try {
            String pathDbZip = Preferencia.getPathDBZip();
            String pathDb = Preferencia.getPathDB();
            String url = getUrlActiva();
            SincronizacionWs sincronizacionWs = new SincronizacionWs(_context);

            String hashPreventaSqliteServidor = sincronizacionWs.descargarDatos(url);
            //por tarea #48457 lo siguiente
            hashPreventaSqliteServidor = hashPreventaSqliteServidor.replace("\"", "");
            // Luego de esta linea significa que en el servidor se termino de armar
            // la BD del vendedor y comienza la transferencia a el celular
            // Descargo y copio zip que contiene archivo de base de datos
            // SEGURIDAD BD - Esto es necesario ya que sino no autentica y no
            // trae el bd
            if (!hashPreventaSqliteServidor.equals(CodeResult.RESULT_VENDEDOR_INVALID_S)) {

                //realizo el backup y borrado de bd antes de crear el archivo
                isBackUpOk = this._repoFactory.backupDb();
                PreferenciaBo preferenciaBo = new PreferenciaBo();
                preferenciaBo.savePreferencia(_context);
                boolean isBorradoOk = this._repoFactory.deleteZip();

                Authenticator.setDefault(new MyAuthenticator());
                sincronizacionWs.refreshToken();
                SharedPreferences preferences = _context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE);
                String token = preferences.getString(KEY_TOKEN, "");
                URL urlZipBd = new URL(url + "/bd/preventa-"
                        + VendedorBo.getVendedor().getId() + ".zip" + "?access_token=" + token);
                final InputStream input = urlZipBd.openStream();


                if (!isBackUpOk) {
                    Log.w(TAG, ErrorManager.ErrorBackUp);
                } else if (!isBorradoOk) {
                    Log.w(TAG, ErrorManager.ErrorBorrarZip);
                }

                //crea el archivo
                String pathSistema = PreferenciaBo.getInstance().getPreferencia().getPathSistema();
                File directory = new File(pathSistema);
                File[] contents = directory.listFiles();
                // Folder is empty
                if (contents == null) {
                    //Si está vacía la borro y la vuelvo a crear porque cuando borran los datos de la aplicacion
                    // la carpeta queda bloqueada
                    directory.delete();
                    directory.mkdirs();
                }

                final OutputStream output = new FileOutputStream(pathDbZip);

                final ReadableByteChannel inputChannel = Channels
                        .newChannel(input);
                final WritableByteChannel outputChannel = Channels
                        .newChannel(output);

                FileManager.fastChannelCopy(inputChannel, outputChannel);
                input.close();
                output.close();

                FileInputStream dbInputStream = new FileInputStream(pathDbZip);
                // Obtengo el hash del archivo descargado
                String sDigestMovil = getSDigest(dbInputStream);

                // Comparo el hash del servidor con el del movil
                if (!hashPreventaSqliteServidor.equals(sDigestMovil)) {
                    throw new Exception(ErrorManager.ErrorDescargarBaseDeDatos);
                } else {
                    // Descomprimo el archivo de base de datos sqlite
                    FileManager.unZip(pathDbZip, pathDb);
                    // Cerramos la conexi?n a la BD para re-abrila despues
                    this._repoFactory.reOpenConnection();
                    // Guardo en preferencias.xml (Tarea #3036)
                    _empresaBo.updateConfiguracionesPreferencia(this._context);
                }
            } else {
                throw new CustomExceptionInvalidSeller(ErrorManager.ErrorVendedorNoValido);
                //throw new Exception(ErrorManager.ErrorVendedorNoValido);

            }

        } catch (Exception e) {
            e.printStackTrace();
            if (!(e instanceof CustomExceptionFactory) && isBackUpOk) {
                this._repoFactory.restoreDb();
                // Por las dudas marco todo como enviado (Tarea #2696)
                //_movimientoDao.updateFechaSincronizacion();
                // Marco las ubicaciones geograficas como enviadas
                //_ubicacionGeograficaBo.marcarUbicacionesComoEnviadas();
                // Actualizo preferencias (Tarea #3198)
                _empresaBo.updateConfiguracionesPreferencia(this._context);
            } else {
                Monitor.unlock();
            }
            throw e;
        }
    }

    private String getSDigest(FileInputStream dbInputStream) throws Exception {
        MessageDigest digester = MessageDigest.getInstance("MD5");
        byte[] bytes = new byte[8192];
        int byteCount;
        while ((byteCount = dbInputStream.read(bytes)) > 0) {
            digester.update(bytes, 0, byteCount);
        }
        byte[] messageDigest = digester.digest();
        // Create Hex String
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < messageDigest.length; i++) {
            String h = Integer.toHexString(0xFF & messageDigest[i]);
            while (h.length() < 2)
                h = "0" + h;
            hexString.append(h);
        }
        String sDigest = hexString.toString();
        dbInputStream.close();
        return sDigest;
    }

    public void setPedidosEnviados(int pedidosEnviados) {
        this.pedidosEnviados = pedidosEnviados;
    }

    public int getPedidosEnviados() {
        return pedidosEnviados;
    }

    public void setNoPedidosEnviados(int noPedidosEnviados) {
        this.noPedidosEnviados = noPedidosEnviados;
    }

    public int getNoPedidosEnviados() {
        return noPedidosEnviados;
    }

    public boolean isUrlActiva() {
        ConnectionMannager cm = new ConnectionMannager(_context);
        return cm.isConexionActiva();
    }

    public String getUrlActiva() {
        isUrlActiva();
        if (PreferenciaBo.getInstance().getPreferencia(_context)
                .isSincronizacionLocal()) {
            return PreferenciaBo.getInstance().getPreferencia(_context)
                    .getUrlLocalServidor();
        } else {
            return PreferenciaBo.getInstance().getPreferencia(_context)
                    .getUrlServidorActiva();
        }
    }

    class MyAuthenticator extends Authenticator {

        protected PasswordAuthentication getPasswordAuthentication() {
            String username = "vgmpreventa";
            String password = "vgmpreventa";
            return new PasswordAuthentication(username, password.toCharArray());
        }
    }

    private boolean isUrlActiva = false;
    private int numeroUrlsProbadas = 0;
    private BuscarConexionTask.BuscarConexionListener listenerExterno;

    public void isUrlActiva(Context context, BuscarConexionTask.BuscarConexionListener listener) {
        this.listenerExterno = listener;
        isUrlActiva = false;
        numeroUrlsProbadas = 0;
        BuscarConexionTask taskBuscarConexionDireccion1 = new BuscarConexionTask(context, getBuscarConexionDireccionLocalListener(), PreferenciaBo.getInstance().getPreferencia(context).getUrlLocalServidor());
        taskBuscarConexionDireccion1.execute((Void) null);
        BuscarConexionTask taskBuscarConexionDireccion2 = new BuscarConexionTask(context, getBuscarConexionDireccionRemotaListener(), PreferenciaBo.getInstance().getPreferencia(context).getUrlRemotaServidor());
        taskBuscarConexionDireccion2.execute((Void) null);
        BuscarConexionTask taskBuscarConexionDireccion3 = new BuscarConexionTask(context, getBuscarConexionDireccionRemota2Listener(), PreferenciaBo.getInstance().getPreferencia(context).getUrlRemotaServidor2());
        taskBuscarConexionDireccion3.execute((Void) null);
    }

    private void onErrorBuscarConexion() {
        if (!isUrlActiva)
            numeroUrlsProbadas++;
        if (numeroUrlsProbadas == 3)//si fallan las 3 conexiones devuelvo error
            listenerExterno.onError(ErrorManager.ErrorConexion);
    }

    private void onDoneBuscarConexion(String url, boolean isUrlLocal) {
        if (!isUrlActiva) {
            isUrlActiva = true;
            PreferenciaBo.getInstance().getPreferencia(_context).setUrlServidorActiva(url);
            PreferenciaBo.getInstance().getPreferencia(_context).setSincronizacionLocal(isUrlLocal);
            listenerExterno.onDone();
        }
    }

    private BuscarConexionTask.BuscarConexionListener getBuscarConexionDireccionLocalListener() {
        BuscarConexionTask.BuscarConexionListener listener = new BuscarConexionTask.BuscarConexionListener() {
            @Override
            public void onError(String error) {
                onErrorBuscarConexion();
            }

            @Override
            public void onDone() {
                onDoneBuscarConexion(PreferenciaBo.getInstance().getPreferencia(_context).getUrlLocalServidor(), true);
            }
        };
        return listener;
    }

    private BuscarConexionTask.BuscarConexionListener getBuscarConexionDireccionRemotaListener() {
        BuscarConexionTask.BuscarConexionListener listener = new BuscarConexionTask.BuscarConexionListener() {
            @Override
            public void onError(String error) {
                onErrorBuscarConexion();
            }

            @Override
            public void onDone() {
                onDoneBuscarConexion(PreferenciaBo.getInstance().getPreferencia(_context).getUrlRemotaServidor(), false);
            }
        };
        return listener;
    }

    private BuscarConexionTask.BuscarConexionListener getBuscarConexionDireccionRemota2Listener() {
        BuscarConexionTask.BuscarConexionListener listener = new BuscarConexionTask.BuscarConexionListener() {
            @Override
            public void onError(String error) {
                onErrorBuscarConexion();
            }

            @Override
            public void onDone() {
                onDoneBuscarConexion(PreferenciaBo.getInstance().getPreferencia(_context).getUrlRemotaServidor2(), false);
            }
        };
        return listener;
    }

}
