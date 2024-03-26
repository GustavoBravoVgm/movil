package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.entity.LineaIntegradoMercaderia;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.entity.key.PkVenta;
import com.ar.vgmsistemas.entity.key.PkVentaDetalle;

import java.util.List;


public interface IVentaDetalleRepository extends
        IGenericRepository<VentaDetalle, PkVentaDetalle> {

    public long recoveryIdSecuencia(PkVenta id) throws Exception;

    public List<VentaDetalle> recoveryByVenta(Venta venta) throws Exception;

    public void anularVentasDetalleByVenta(Venta venta) throws Exception;

    public void deleteVentasDetalleByVenta(Venta venta) throws Exception;

    public List<List<VentaDetalle>> recoveryByVenta(List<Venta> ventas) throws Exception;

    //public List<LineaIntegradoMercaderia> getLineasIntegrado() throws Exception;
    public List<LineaIntegradoMercaderia> getLineasIntegrado(int ti_empresa ,String documento) throws Exception;

    public void updateXDevolucion(VentaDetalle vd) throws Exception;

    List<VentaDetalle> recoveryArtComponentesCombo(VentaDetalle vd) throws Exception;
}
