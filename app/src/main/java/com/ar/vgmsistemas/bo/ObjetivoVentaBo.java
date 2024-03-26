package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.ObjetivoVenta;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.repository.IObjetivoVentaRepository;
import com.ar.vgmsistemas.repository.IVentaDetalleRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class ObjetivoVentaBo {

    private final IObjetivoVentaRepository _objetivoVentaRepository;
    private final IVentaDetalleRepository _ventaDetalleRepository;
    public final static int SIGNO_SUMA = 1;
    public final static int SIGNO_RESTA = -1;

    public ObjetivoVentaBo(RepositoryFactory repoFactory) {
        this._objetivoVentaRepository = repoFactory != null ? repoFactory.getObjetivoVentaRepository() : null;
        this._ventaDetalleRepository = repoFactory != null ? repoFactory.getVentaDetalleRepository() : null;
    }

    public List<ObjetivoVenta> recoveryObjetivosVentasByCliente(Cliente cliente) throws Exception {
        return _objetivoVentaRepository.recoveryByCliente(cliente);
    }

    public List<ObjetivoVenta> recoveryObjetivosVentasNoCumplidosByCliente(Cliente cliente) throws Exception {
        return _objetivoVentaRepository.recoveryNoCumplidosByCliente(cliente);
    }

    public int getCantidadNoCumplidosCliente(Cliente cliente) throws Exception {
        return _objetivoVentaRepository.getCantNoCumplidosByCliente(cliente);
    }

    public void updateCantidadVendida(VentaDetalle ventaDetalle, Cliente cliente) throws Exception {
        _objetivoVentaRepository.updateCantidadVendida(ventaDetalle, cliente, SIGNO_SUMA);
    }

    public void modificarCantidadVendida(Venta venta, int signo) throws Exception {
        List<VentaDetalle> ventasDetalle = _ventaDetalleRepository.recoveryByVenta(venta);
        for (int i = 0; i < ventasDetalle.size(); i++) {
            _objetivoVentaRepository.updateCantidadVendida(ventasDetalle.get(i), venta.getCliente(), signo);
        }
    }

}
