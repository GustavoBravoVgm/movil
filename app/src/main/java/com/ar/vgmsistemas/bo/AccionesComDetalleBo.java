package com.ar.vgmsistemas.bo;

import static com.ar.vgmsistemas.entity.ListaPrecio.TIPO_LISTA_BASE;
import static com.ar.vgmsistemas.entity.ListaPrecio.TIPO_LISTA_BASE_X_ART_LIBRE;
import static com.ar.vgmsistemas.entity.ListaPrecio.TIPO_LISTA_BASE_X_CANTIDAD;

import com.ar.vgmsistemas.entity.AccionesComDetalle;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.ListaPrecio;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.entity.key.PkAccionesComDetalle;
import com.ar.vgmsistemas.repository.IAccionesComDetalleRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;


public class AccionesComDetalleBo {
    private final IAccionesComDetalleRepository _accionesComDetalleRepository;

    public AccionesComDetalleBo(RepositoryFactory repository) {
        _accionesComDetalleRepository = repository.getAccionesComDetalleRepository();
    }

    public AccionesComDetalle getAccionesComDetalle(Cliente cliente, VentaDetalle ventaDetalle, ListaPrecio lista, String tiOrigen) {
        AccionesComDetalle accionesComDetalle = null;
        if (lista.getTipoLista() == TIPO_LISTA_BASE
                || lista.getTipoLista() == TIPO_LISTA_BASE_X_ART_LIBRE
                || lista.getTipoLista() == TIPO_LISTA_BASE_X_CANTIDAD) {
            //List<AccionesComDetalle> accionesComDetalles = accionesComDetalleDao.recoveryByClienteYOrigen(cliente.getId(), tiOrigen);
            try {
                accionesComDetalle = _accionesComDetalleRepository.recoveryAccionPorArticulo(ventaDetalle, cliente.getId(), tiOrigen);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (accionesComDetalle == null) {
                try {
                    accionesComDetalle = _accionesComDetalleRepository.recoveryAccionPorMarca(ventaDetalle, cliente.getId(), tiOrigen);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (accionesComDetalle == null) {
                try {
                    accionesComDetalle = _accionesComDetalleRepository.recoveryAccionPorSubrubro(ventaDetalle, cliente.getId(), tiOrigen);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (accionesComDetalle == null) {
                try {
                    accionesComDetalle = _accionesComDetalleRepository.recoveryAccionPorRubro(ventaDetalle, cliente.getId(), tiOrigen);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (accionesComDetalle == null) {
                try {
                    accionesComDetalle = _accionesComDetalleRepository.recoveryAccionPorNegocio(ventaDetalle, cliente.getId(), tiOrigen);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return accionesComDetalle;
    }

    public List<AccionesComDetalle> getAllAccionesComDetalle(Articulo articulo, Cliente cliente, ListaPrecio lista, String tiOrigen) {
        List<AccionesComDetalle> accionesComDetalle = null;
        if (lista.getTipoLista() == TIPO_LISTA_BASE
                || lista.getTipoLista() == TIPO_LISTA_BASE_X_ART_LIBRE
                || lista.getTipoLista() == TIPO_LISTA_BASE_X_CANTIDAD) {
            //List<AccionesComDetalle> accionesComDetalles = accionesComDetalleDao.recoveryByClienteYOrigen(cliente.getId(), tiOrigen);
            try {
                accionesComDetalle = _accionesComDetalleRepository.recoveryAllPorArticulo(articulo, cliente.getId(), tiOrigen);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if ((accionesComDetalle != null ? accionesComDetalle.size() : 0) == 0) {
                try {
                    accionesComDetalle = _accionesComDetalleRepository.recoveryAllPorMarca(articulo, cliente.getId(), tiOrigen);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if ((accionesComDetalle != null ? accionesComDetalle.size() : 0) == 0) {
                try {
                    accionesComDetalle = _accionesComDetalleRepository.recoveryAllPorSubrubro(articulo, cliente.getId(), tiOrigen);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if ((accionesComDetalle != null ? accionesComDetalle.size() : 0) == 0) {
                try {
                    accionesComDetalle = _accionesComDetalleRepository.recoveryAllPorRubro(articulo, cliente.getId(), tiOrigen);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if ((accionesComDetalle != null ? accionesComDetalle.size() : 0) == 0) {
                try {
                    accionesComDetalle = _accionesComDetalleRepository.recoveryAllPorNegocio(articulo, cliente.getId(), tiOrigen);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return accionesComDetalle;
    }


    public AccionesComDetalle recoveryById(PkAccionesComDetalle id) throws Exception {
        return _accionesComDetalleRepository.recoveryByID(id);
    }
}
