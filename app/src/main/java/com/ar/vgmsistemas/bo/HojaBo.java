package com.ar.vgmsistemas.bo;

import android.content.Context;

import com.ar.vgmsistemas.entity.Cheque;
import com.ar.vgmsistemas.entity.Hoja;
import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.repository.IHojaDetalleRepository;
import com.ar.vgmsistemas.repository.IHojaRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.ArrayList;
import java.util.List;

public class HojaBo implements IEntityBo<Hoja> {

    private final IHojaRepository _hojaRepository;
    private final IHojaDetalleRepository _hojaDetalleRepository;
    private RepositoryFactory _repoFactory;

    public HojaBo(RepositoryFactory repoFactory) {
        this._repoFactory = repoFactory;
        this._hojaRepository = repoFactory.getHojaRepository();
        this._hojaDetalleRepository = repoFactory.getHojaDetalleRepository();
    }

    public List<Hoja> recoveryAllEntities() throws Exception {
        return _hojaRepository.recoveryAll();
    }

    public static double getCdoDetalles(List<HojaDetalle> detalles) {
        double total = 0d;
        for (HojaDetalle detalle : detalles) {
            total += detalle.getPrPagado() * (detalle.getDocumento().getFuncionTipoDocumento() == 2 ? -1 : 1);
        }
        return total;
    }

    public static double getCtaCteDetalles(List<HojaDetalle> detalles) {
        double total = 0d;
        for (HojaDetalle detalle : detalles) {
            if (detalle.getTiEstado() != null && detalle.getTiEstado().equals(HojaDetalle.CUENTA_CORRIENTE)) {
                total += (detalle.getPrTotal() - detalle.getPrNotaCredito() - detalle.getPrPagado()) * (detalle.getDocumento().getFuncionTipoDocumento() == 2 ? -1 : 1);
            }
        }
        return total;
    }

    public static double getCreditoDetalles(List<HojaDetalle> detalles) {
        double total = 0d;

        for (HojaDetalle detalle : detalles) {
            if (detalle.getTiEstado() == null || !detalle.getTiEstado().equals(HojaDetalle.ANULADO)) {
                total += detalle.getPrNotaCredito() * (detalle.getDocumento().getFuncionTipoDocumento() == 2 ? -1 : 1);
            }
        }
        return total;
    }

    public static double getPendiente(List<HojaDetalle> detalles) {
        return getTotalState(detalles, HojaDetalle.PENDIENTE);
    }

    public static double getAnulado(List<HojaDetalle> detalles) {
        return getTotalState(detalles, HojaDetalle.ANULADO);
    }

    private static double getTotalState(List<HojaDetalle> detalles, String state) {
        double total = 0d;
        for (HojaDetalle detalle : detalles) {
            if (detalle.getTiEstado() != null && detalle.getTiEstado().equals(state)) {
                switch (state) {
                    case HojaDetalle.NC:
                        if (detalle.getPrTotal() != detalle.getPrNotaCredito())
                            total += detalle.getPrNotaCredito();
                        break;
                    case HojaDetalle.ANULADO:
                        if (detalle.getPrTotal() == detalle.getPrNotaCredito())
                            total += detalle.getPrNotaCredito() * (detalle.getDocumento().getFuncionTipoDocumento() == 2 ? -1 : 1);
                        break;
                    case HojaDetalle.CONTADO:
                        total += detalle.getPrPagado() * (detalle.getDocumento().getFuncionTipoDocumento() == 2 ? -1 : 1);
                        break;
                    case HojaDetalle.CUENTA_CORRIENTE:
                        total += detalle.getPrPagado() - detalle.getPrNotaCredito() * (detalle.getDocumento().getFuncionTipoDocumento() == 2 ? -1 : 1);
                        break;
                }
                total += detalle.getPrTotal();
            }

        }
        return total;
    }

    /**
     * incrementa el valor de no entregado de la hoja
     */
    public void setNoEntregado(Hoja hoja, HojaDetalle detalle, Context context) throws Exception {
        HojaDetalleBo bo = new HojaDetalleBo(hoja.getIdSucursal(), hoja.getIdHoja(), _repoFactory);
        bo.setNoEntregado(detalle);
        _hojaRepository.updateTotalNoEntregado(hoja.getIdSucursal(), hoja.getIdHoja(), hoja.getPrPendiente() + detalle.getPrTotal());
        HojaDetalleBo hojaDetalleBo = new HojaDetalleBo(hoja.getIdSucursal(), hoja.getIdHoja(), _repoFactory);
        hojaDetalleBo.guardarHojaDetalle(null, detalle, context);
    }

    public String getSnRendida(int idSucursal, int idHoja) throws Exception {
        return _hojaRepository.getSnRendida(idSucursal, idHoja);
    }

    public List<HojaDetalle> recoveryDetalles(int idSucursal, int idHoja) throws Exception {
        // Se que esto esta mal, y cuando se le asigne dos hojas de diferentes sucursales pero con el mismo id, va a andar mal.
        // Lo voy a hablar con Franco para corregirlo en la siguiente entrega. Fede
        return _hojaDetalleRepository.recoveryByHoja(idSucursal, idHoja);
    }

    //Este metodo te mapea con la tabla entrega para traer los totales
    public List<HojaDetalle> recoveryDetallesConEntrega(int idSucursal, int idHoja) throws Exception {
        // Se que esto esta mal, y cuando se le asigne dos hojas de diferentes sucursales pero con el mismo id, va a andar mal.
        // Lo voy a hablar con Franco para corregirlo en la siguiente entrega. Fede
        return _hojaDetalleRepository.recoveryByHojaConEntrega(idSucursal, idHoja);
    }

    public List<HojaDetalle> recoveryChequesByHoja(int idSucursal, int idHoja) throws Exception {
        // Se que esto esta mal, y cuando se le asigne dos hojas de diferentes sucursales pero con el mismo id, va a andar mal.
        // Lo voy a hablar con Franco para corregirlo en la siguiente entrega. Fede
        return _hojaDetalleRepository.recoveryChequesByHoja(idSucursal, idHoja);
    }

    public double getPrEfectivo(List<HojaDetalle> hojaDetalles) {
        double prEfectivo = 0d;
        for (HojaDetalle detalle : hojaDetalles) {
            prEfectivo += detalle.getEntrega().getPrEfectivoEntrega();
        }
        return prEfectivo;
    }

    public List<Cheque> getCheques(List<HojaDetalle> hojaDetalles) {
        List<Cheque> cheques = new ArrayList<>();
        for (HojaDetalle detalle : hojaDetalles) {
            cheques.addAll(detalle.getEntrega().getCheques());
        }
        return cheques;
    }


}
