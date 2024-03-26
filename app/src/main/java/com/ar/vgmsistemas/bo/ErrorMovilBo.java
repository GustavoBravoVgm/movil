package com.ar.vgmsistemas.bo;

import android.content.Context;

import com.ar.vgmsistemas.entity.ErrorMovil;
import com.ar.vgmsistemas.entity.Movimiento;
import com.ar.vgmsistemas.repository.IErrorRepository;
import com.ar.vgmsistemas.repository.IMovimientoRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.CodeResult;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.ws.ErrorWs;

import java.text.SimpleDateFormat;
import java.util.List;

public class ErrorMovilBo {
    private final IErrorRepository _errorRepository;
    private final IMovimientoRepository _movimientoRepository;
    private RepositoryFactory _repoFactory;

    public ErrorMovilBo(RepositoryFactory repoFactory) {
        this._repoFactory = repoFactory;
        this._errorRepository = repoFactory.getErrorRepository();
        this._movimientoRepository = repoFactory.getMovimientoRepository();
    }

    public List<ErrorMovil> recoveryAEnviar() throws Exception {
        return _errorRepository.recoveryAEnviar();
    }

    public synchronized void enviarError(ErrorMovil errorMovil, Context context) throws Exception {
        ErrorWs errorWs = new ErrorWs(context);
        int result = errorWs.send(errorMovil);
        if (result == CodeResult.RESULT_OK) {
            _movimientoRepository.updateFechaSincronizacion(errorMovil);
        }
    }

    public void create(final ErrorMovil errorMovil, final Context context) throws Exception {

        boolean isGuardado = false;

        try {

            //Id
            int id = _errorRepository.create(errorMovil);
            errorMovil.setId(id);

            //Generando el idMovil
            String idVendedor = Formatter.formatNumber(PreferenciaBo.getInstance().getPreferencia().getIdVendedor(), "000");

            //Formato de fecha con hora y minutos
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            String fecha = sdf.format(errorMovil.getFechaRegistro());

            String idMovil = "Error - " + idVendedor + "-" + fecha + "-" + errorMovil.getId();
            errorMovil.setIdMovil(idMovil);

            //Update del NoPedido
            _errorRepository.update(errorMovil);

            //Registro el movimiento
            registrarMovimiento(errorMovil);

            isGuardado = true;

        } catch (Exception e) {
            //Hago Rollback
        }

        //Si se guardo el No pedido -> intento enviarlo al servidor automaticamente
        if (isGuardado) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        enviarError(errorMovil, context);
                    } catch (Exception e) {
                        //No se trata la exception porque el servicio va a intentar despues
                    }
                }
            };
            t.start();
        }
    }

    private void registrarMovimiento(final ErrorMovil errorMovil) throws Exception {
        Movimiento movimiento = new Movimiento();
        movimiento.setTabla(ErrorMovil.TABLE);
        movimiento.setIdMovil(errorMovil.getIdMovil());
        movimiento.setTipo(Movimiento.ALTA);

        MovimientoBo movimientoBo = new MovimientoBo(_repoFactory);
        movimientoBo.create(movimiento);
    }
}
