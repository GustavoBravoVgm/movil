package com.ar.vgmsistemas.bo;

import android.content.Context;

import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.ListaPrecioDetalle;
import com.ar.vgmsistemas.entity.ListaVentas;
import com.ar.vgmsistemas.entity.PromocionDetalle;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.repository.IListaPrecioDetalleRepository;
import com.ar.vgmsistemas.repository.IVentaDetalleRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.CategoriaRecursoHumano;
import com.ar.vgmsistemas.utils.TipoEmpresaCode;

import java.util.ArrayList;
import java.util.List;

public class VentaDetalleBo {
    private final IVentaDetalleRepository _ventaDetalleRepository;
    private final IListaPrecioDetalleRepository _listaPrecioDetalleRepository;
    private static RepositoryFactory _repoFactory;

    public VentaDetalleBo(RepositoryFactory repoFactory) {
        _repoFactory = repoFactory;
        this._listaPrecioDetalleRepository = repoFactory.getListaPrecioDetalleRepository();
        this._ventaDetalleRepository = repoFactory.getVentaDetalleRepository();
    }

    public static boolean existsAllNumeroTropa(List<VentaDetalle> ventaDetalles) {
        boolean isValid = true;
        for (VentaDetalle detalle : ventaDetalles) {
            if (detalle.getNumeroTropa() == 0) {
                detalle.setValid(false);
                return false;
            }
        }
        return isValid;
    }

    public static void checkNumerosTropas(List<VentaDetalle> detalles) {
        if (PreferenciaBo.getInstance().getPreferencia().getTipoEmpresa() == TipoEmpresaCode.TYPE_HACIENDA
                && PreferenciaBo.getInstance().getPreferencia().getIdCategoria() == CategoriaRecursoHumano.REPARTIDOR) {
            for (VentaDetalle detalle : detalles) {
                if (detalle.getNumeroTropa() == 0) {
                    detalle.setValid(false);
                }
            }
        }

    }

    public static void setListValids(List<VentaDetalle> detalles, List<Boolean> listValids) {
        if (PreferenciaBo.getInstance().getPreferencia().getTipoEmpresa() == TipoEmpresaCode.TYPE_NORMAL) {
            int i = 0;
            for (VentaDetalle detalle : detalles) {
                detalle.setValid(listValids.get(i));
                i++;
            }
        }
    }

    public static boolean allVentasPedidoHaveNumTropa(List<VentaDetalle> detalles) {
        if (PreferenciaBo.getInstance().getPreferencia().getTipoEmpresa() == TipoEmpresaCode.TYPE_HACIENDA
                && CategoriaRecursoHumano.isRepartidorDeHacienda()) {
            for (VentaDetalle detalle : detalles) {
                if (detalle.getNumeroTropa() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param detalles de los que se quiere calcular el subtotal
     * @return el subtotal de las lineas que se pasaron como parámetro
     */
    public double getSubtotal(List<VentaDetalle> detalles) {
        double subtotal = 0;
        ArticuloBo articuloBo = new ArticuloBo(_repoFactory);
        for (VentaDetalle detalle : detalles) {
            subtotal += articuloBo.getSubtotal(detalle);
        }
        return subtotal;
    }

    public double getSubtotalKilos(double caArticulosKilos, double prKiloUnitario) {
        return caArticulosKilos * prKiloUnitario;
    }

    public static void remove(List<VentaDetalle> listDetalles, VentaDetalle detalle) {
        int i = 0;
        for (VentaDetalle currentDetalle : listDetalles) {
            if (detalle.equals(currentDetalle)) {
                listDetalles.remove(i);
                break;
            }
            i++;
        }
    }

    public static List<VentaDetalle> completeVentasDetalles(List<VentaDetalle> ventaDetalles, Cliente cliente,
                                                            ListaPrecioDetalle listaPrecio,
                                                            Context context) throws Exception {
        ListaPrecioDetalleBo listaPrecioDetalleBo = new ListaPrecioDetalleBo(_repoFactory);
        List<VentaDetalle> detalles = new ArrayList<>();
        for (VentaDetalle ventaDetalle : ventaDetalles) {
            ventaDetalle.setIdPromoTemporal(listaPrecio.getId().getIdArticulo());
            Articulo articulo = ventaDetalle.getArticulo();
            ListaPrecioDetalle listaPrecioDetalleArticulo = listaPrecioDetalleBo.recoveryById(articulo.getId(), listaPrecio.getListaPrecio().getId());

            ventaDetalle.setListaPrecio(listaPrecio.getListaPrecio());
            listaPrecioDetalleArticulo.setListaPrecio(listaPrecio.getListaPrecio());
            VentaBo.setearPreciosDetalle(cliente, ventaDetalle, listaPrecioDetalleArticulo, context);
            detalles.add(ventaDetalle);

        }
        return detalles;
    }

    public ListaPrecioDetalle getListaPrecioDetalle(VentaDetalle ventaDetalle) {
        List<ListaPrecioDetalle> listaPrecioDetalles = null;
        if (ventaDetalle.getArticulo().getListaPreciosDetalle().size() < 1) {
            try {
                listaPrecioDetalles = _listaPrecioDetalleRepository.recoveryByArticulo(ventaDetalle.getArticulo().getId());
                ventaDetalle.getArticulo().setListaPreciosDetalle(listaPrecioDetalles);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            listaPrecioDetalles = ventaDetalle.getArticulo().getListaPreciosDetalle();
        }
        for (ListaPrecioDetalle listaPrecioDetalle : listaPrecioDetalles) {
            if (listaPrecioDetalle.getId().getIdLista() == ventaDetalle.getListaPrecio().getId())
                return listaPrecioDetalle;
        }
        return null;
    }

    public void create(Venta venta) throws Exception {
        for (VentaDetalle vd : venta.getDetalles()) {
            if (vd.getDetalleCombo() != null) {
                for (VentaDetalle vdc : vd.getDetalleCombo()) {
                    _ventaDetalleRepository.create(vdc);
                }
                _ventaDetalleRepository.create(vd);
            }
        }
    }

    public void updateXDevolucion(Venta venta) throws Exception {
        for (VentaDetalle vd : venta.getDetalles()) {
            if (vd.getDetalleCombo() != null) {
                for (VentaDetalle vdc : vd.getDetalleCombo()) {
                    _ventaDetalleRepository.updateXDevolucion(vdc);
                }
            }
            _ventaDetalleRepository.updateXDevolucion(vd);
        }
    }

    public static ArrayList<VentaDetalle> parseVentaDetalles(List<PromocionDetalle> promociones) {
        ArrayList<VentaDetalle> detalles = new ArrayList<>();
        for (PromocionDetalle promocionDetalle : promociones) {
            VentaDetalle ventaDetalle = new VentaDetalle();
            ventaDetalle.setArticulo(promocionDetalle.getArticulo());
            ventaDetalle.setUnidades(promocionDetalle.getCantidad());

            detalles.add(ventaDetalle);
        }
        return detalles;
    }

    /**
     * devuelve una lista de ventas detalles con ventas detalles dentro si es
     * que hay combos, solo sirve para mostrar en el detalle del pedido.
     *
     * @param detalles
     */
    public static List<VentaDetalle> checkVentasDetallesCombos(List<VentaDetalle> detalles) {
        List<VentaDetalle> ventaDetalles = new ArrayList<>();
        for (VentaDetalle detalle : detalles) {
            if (detalle.getIdCombo().equals("")) {// solo agrego las  ventas detalles comunes y cabeceras de combo
                if (detalle.getDetalleCombo().size() > 0) {
                    detalle.getDetalleCombo().clear();
                }
                for (VentaDetalle detalleCombo : detalles) {
                    if (detalle.getId() != null && detalleCombo.getIdCombo().equals(detalle.getId().toString())) {
                        detalle.getDetalleCombo().add(detalleCombo);
                    }
                }

                ventaDetalles.add(detalle);
            }
        }
        return ventaDetalles;
    }

    /**
     * @param detalles
     * @return retorna un listado de ventas detalles, si tiene combos especiales los agrega al listado principal
     */
    public static List<VentaDetalle> checkCombosEspeciales(List<VentaDetalle> detalles) {
        List<VentaDetalle> ventaDetalles = new ArrayList<>();
        for (VentaDetalle detalle : detalles) {
            ventaDetalles.add(detalle);
            if (detalle.isCabeceraPromo()) {
                for (VentaDetalle ventaDetalleCombo : detalle.getDetalleCombo()) {
                    ventaDetalles.add(ventaDetalleCombo);
                }
            }
        }
        return ventaDetalles;
    }

    public static void checkCombosEspeciales(ListaVentas ventas) {
        for (Venta venta : ventas.getVentas()) {
            List<VentaDetalle> ventaDetalles = new ArrayList<>();
            for (VentaDetalle detalle : venta.getDetalles()) {
                ventaDetalles.add(detalle);
                if (detalle.isCabeceraPromo()) {
                    for (VentaDetalle ventaDetalleCombo : detalle.getDetalleCombo()) {
                        ventaDetalles.add(ventaDetalleCombo);
                    }
                }
            }
            venta.setDetalles(ventaDetalles);
        }
    }

    public List<VentaDetalle> recoveryDetallesCombo(VentaDetalle vd) {
        List<VentaDetalle> detalles = new ArrayList<>();
        try {
            detalles = _ventaDetalleRepository.recoveryArtComponentesCombo(vd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detalles;
    }
}
