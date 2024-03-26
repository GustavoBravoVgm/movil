package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Movimiento;
import com.ar.vgmsistemas.entity.UbicacionGeografica;
import com.ar.vgmsistemas.repository.IMovimientoRepository;
import com.ar.vgmsistemas.repository.IUbicacionGeograficaRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.Date;
import java.util.List;

public class UbicacionGeograficaBo {
    private static final String ERROR_AL_CREAR_UBICACION_GEOGRAFICA = "Error al crear la ubicación geográfica";
    private static final String ERROR_AL_ACTUALIZAR_FECHA_SINCRONIZACION = "Error al actualizar la fecha de sincronización de la ubicación geográfica";

    private final IUbicacionGeograficaRepository _ubicacionGeograficaRepository;
    private final IMovimientoRepository _movimientoRepository;


    public UbicacionGeograficaBo(RepositoryFactory repoFactory) {
        this._movimientoRepository = repoFactory.getMovimientoRepository();
        this._ubicacionGeograficaRepository = repoFactory.getUbicacionGeograficaRepository();

    }

    public boolean create(UbicacionGeografica ubicacionGeografica) throws Exception {
        try {
            _ubicacionGeograficaRepository.create(ubicacionGeografica);
            return true;
        } catch (Exception e) {
            throw new Exception(ERROR_AL_CREAR_UBICACION_GEOGRAFICA);
        }
    }

    /**
     * Actualiza en Preferencias.xml la ultima ubicacion
     *
     * @param ubicacionGeografica
     */
    public void updateUltimaUbicacion(UbicacionGeografica ubicacionGeografica) {
        PreferenciaBo.getInstance().getPreferencia().setUltimaLatitud(String.valueOf(ubicacionGeografica.getLatitud()));
        PreferenciaBo.getInstance().getPreferencia().setUltimaLongitud(String.valueOf(ubicacionGeografica.getLongitud()));
        // TODO Actualizar la fecha
        Date fechaPosicion = ubicacionGeografica.getFechaPosicionMovil();
        PreferenciaBo.getInstance().getPreferencia().setFechaUltimaUbicacion(fechaPosicion);
    }

    public UbicacionGeografica recoveryByIdMovil(String idMovil) throws Exception {
        return _ubicacionGeograficaRepository.recoveryByIdMovil(idMovil);
    }

    public boolean updateFechaSincronizacion(UbicacionGeografica ubicacionGeografica) throws Exception {
        try {
            _ubicacionGeograficaRepository.updateFechaSincronizacion(ubicacionGeografica.getIdLegajo(), ubicacionGeografica.getFechaPosicionMovil());
            return true;
        } catch (Exception e) {
            throw new Exception(ERROR_AL_ACTUALIZAR_FECHA_SINCRONIZACION);
        }
    }

    public List<UbicacionGeografica> recoveryNoEnviados() throws Exception {
        return _ubicacionGeograficaRepository.recoveryNoEnviados();
    }

    public void marcarUbicacionesComoEnviadas() throws Exception {
        _ubicacionGeograficaRepository.marcarUbicacionesComoEnviadas();

    }

    public void reenvioUbicaciones() throws Exception {
        List<Movimiento> movimientos = _movimientoRepository.getMovimientosPendientes();
        String ventas = "ventas";
        for (int i = 0; i < movimientos.size(); i++) {
            if (movimientos.get(i).getTabla().equals(ventas)) {
                _ubicacionGeograficaRepository.reenvioUbicaciones(movimientos.get(i).getIdMovil());
            }
        }
    }
}
