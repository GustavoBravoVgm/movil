package com.ar.vgmsistemas.bo;

import android.content.Context;
import android.location.Location;

import com.ar.vgmsistemas.entity.MotivoNoPedido;
import com.ar.vgmsistemas.entity.Movimiento;
import com.ar.vgmsistemas.entity.NoPedido;
import com.ar.vgmsistemas.entity.UbicacionGeografica;
import com.ar.vgmsistemas.gps.GPSManagement;
import com.ar.vgmsistemas.repository.IMotivoNoPedidoRepository;
import com.ar.vgmsistemas.repository.IMovimientoRepository;
import com.ar.vgmsistemas.repository.INoPedidoRepository;
import com.ar.vgmsistemas.repository.IUbicacionGeograficaRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.CodeResult;
import com.ar.vgmsistemas.utils.ComparatorDateTime;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.AlertDialog;
import com.ar.vgmsistemas.ws.NoPedidoWs;
import com.ar.vgmsistemas.ws.UbicacionGeograficaWs;

import java.text.SimpleDateFormat;
import java.util.List;

public class NoPedidoBo {
    private final INoPedidoRepository _noPedidoRepository;
    private final IMovimientoRepository _movimientoRepository;
    private final IUbicacionGeograficaRepository _ubicacionGeograficaRepository;
    private final IMotivoNoPedidoRepository _motivoNoPedidoRepository;
    private final EmpresaBo _empresaBo;
    private final RepositoryFactory _repoFactory;

    public NoPedidoBo(RepositoryFactory repoFactory) {
        this._repoFactory = repoFactory;
        this._noPedidoRepository = repoFactory.getNoPedidoRepository();
        this._movimientoRepository = repoFactory.getMovimientoRepository();
        this._ubicacionGeograficaRepository = repoFactory.getUbicacionGeograficaRepository();
        this._motivoNoPedidoRepository = repoFactory.getMotivoNoPedidoRepository();
        this._empresaBo = new EmpresaBo(repoFactory);
    }

    public int send(NoPedido noPedido, Context context) throws Exception {
        NoPedidoWs noPedidoWs = new NoPedidoWs(context);
        return noPedidoWs.send(noPedido);
    }

    public void create(final NoPedido noPedido, final Context context) throws Exception {

        boolean isGuardado = false;
        try {
            String idVendedor = Formatter.formatNumber(noPedido.getVendedor().getId(), "000");

            //Formato de fecha con hora y minutos
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            String fecha = sdf.format(noPedido.getFechaNoPedido());
            String documento = "NP";
            String letra = "X";
            String puntoVenta = "XXXX";
            String numeroDocumento = Formatter.formatNumber(noPedido.getId(), "00000000");
            String idMovil = idVendedor + "-" + fecha + "-" + documento + "-" + letra + "-" + puntoVenta + "-" + numeroDocumento;
            noPedido.setIdMovil(idMovil);

            //Obtengo la localizacion
            Location location = registrarUbicacionGeografica(context, idMovil);

            this._noPedidoRepository.createNoPedidoTransaction(noPedido, location);

            isGuardado = true;

        } catch (Exception e) {
            ErrorManager.manageException("NoPedidoBo", "create", e);

            AlertDialog alert = new AlertDialog(null, "Error", e.getMessage());
            alert.show();
        }

        //Si se guardo el No pedido -> intento enviarlo al servidor automaticamente
        if (isGuardado) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        enviarNoPedido(noPedido, context);
                    } catch (Exception e) {
                        //No se trata la exception porque el servicio va a intentar despues
                    }
                }
            };
            t.start();
        }

    }

    private Location registrarUbicacionGeografica(Context context, String idMovil) throws Exception {

        //Consulto en tabla Empresa por sn_localizacion y ademas valido el rango horario configurado
        if (_empresaBo.isRegistrarLocalizacion() && ComparatorDateTime.validarRangoHorarioEnvioLocalizacion()) {
            GPSManagement gps = new GPSManagement(context.getApplicationContext(), idMovil, UbicacionGeografica.OPERACION_NO_ATENCION);
            gps.getLocation(false);
            return gps.get_location();
        } else {
            return null;
        }
    }

    private void registrarMovimiento(final NoPedido noPedido, Location location) throws Exception {

        Movimiento movimiento = new Movimiento();
        movimiento.setTabla(NoPedido.TABLE);
        movimiento.setIdMovil(noPedido.getIdMovil());
        movimiento.setTipo(Movimiento.ALTA);

        //Seteo la location
        if (location != null) {
            movimiento.setLocation(location);
        }

        //Cliente
        movimiento.setIdSucursal(noPedido.getCliente().getId().getIdSucursal());
        movimiento.setIdCliente(noPedido.getCliente().getId().getIdCliente());
        movimiento.setIdComercio(noPedido.getCliente().getId().getIdComercio());

        MovimientoBo movimientoBo = new MovimientoBo(_repoFactory);
        movimientoBo.create(movimiento);

    }

    public List<NoPedido> recoveryAll() throws Exception {
        return _noPedidoRepository.recoveryAll();
    }

    public List<MotivoNoPedido> recoveryAllMotivo() throws Exception {
        return _motivoNoPedidoRepository.recoveryAll();
    }

    public void delete(NoPedido noPedido) throws Exception {
        try {
            String idMovil = noPedido.getIdMovil();
            MovimientoBo movimientoBo = new MovimientoBo(_repoFactory);

            //si el fue enviado, cancela en el servidor
            Movimiento movimientoAlta = movimientoBo.getMovimiento(idMovil);
            movimientoBo.delete(movimientoAlta);

            _noPedidoRepository.delete(noPedido);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public int getCantidadNoPedidosSinEnviar() throws Exception {
        return this._noPedidoRepository.getCantidadPedidosSinEnviar();
    }

    public List<NoPedido> recoveryNoEnviadas() throws Exception {
        return this._noPedidoRepository.recoveryPedidosSinEnviar();
    }

    public synchronized int enviarNoPedido(NoPedido noPedido, Context context) throws Exception {

        // Mando la no atencion por el web service
        NoPedidoWs noPedidoWs = new NoPedidoWs(context);
        int retorno = noPedidoWs.send(noPedido);
        if (retorno == CodeResult.RESULT_OK) {
            //Consulto en tabla Empresa por sn_localizacion
            if (_empresaBo.isRegistrarLocalizacion()) {

                // Recupero la ubicacion geografica del no pedido
                UbicacionGeografica ubicacionGeograficaNoPedido = _ubicacionGeograficaRepository.recoveryByIdMovil(noPedido.getIdMovil());

                // Envio la ubicacion geografica al servidor
                if (ubicacionGeograficaNoPedido != null) {
                    if (ubicacionGeograficaNoPedido.getFechaPosicionMovil() != null) {
                        UbicacionGeograficaWs ubicacionGeograficaWs = new UbicacionGeograficaWs(context);
                        ubicacionGeograficaWs.send(ubicacionGeograficaNoPedido);
                        //Actualizo fecha de sincronizacion en Ubicacion geografica
                        _ubicacionGeograficaRepository.updateFechaSincronizacion(ubicacionGeograficaNoPedido.getIdLegajo(), ubicacionGeograficaNoPedido.getFechaPosicionMovil());
                    }
                }

            }
            // Actualizo fecha de sincronizacion en movimientos
            _movimientoRepository.updateFechaSincronizacion(noPedido);
        }


        return retorno;
    }

}
