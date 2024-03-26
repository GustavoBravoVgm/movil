package com.ar.vgmsistemas.repository;

import android.location.Location;

import com.ar.vgmsistemas.database.dao.entity.ReciboBd;
import com.ar.vgmsistemas.entity.CuentaCorriente;
import com.ar.vgmsistemas.entity.Entrega;
import com.ar.vgmsistemas.entity.Recibo;
import com.ar.vgmsistemas.entity.Vendedor;
import com.ar.vgmsistemas.entity.key.PkRecibo;

import java.util.List;

public interface IReciboRepository extends IGenericRepository<Recibo, PkRecibo> {
    List<Integer> getPuntosVentaRecibo(Vendedor idVendedor) throws Exception;

    List<Integer> getPuntosVentaRecibo(long sucCliente) throws Exception;

    int getSiguienteNumeroRecibo(int idPuntoVenta) throws Exception;

    List<Recibo> recoveryNoEnviados() throws Exception;

    List<Recibo> recoveryNoRendidos() throws Exception;

    boolean validateNumeroRecibo(long numeroRecibo) throws Exception;

    void updateEstadoImputado(int idPuntoVenta, long idNumeroRecibo) throws Exception;

    void updateResultEnvio(PkRecibo idRecibo, int resultado) throws Exception;

    Recibo mappingToDto(ReciboBd reciboBd) throws Exception;

    void createReciboTransaction(Recibo recibo, Location location, boolean isReciboProvisorio,
                                 List<CuentaCorriente> listadoComprobantesGenerados) throws Exception;
}
