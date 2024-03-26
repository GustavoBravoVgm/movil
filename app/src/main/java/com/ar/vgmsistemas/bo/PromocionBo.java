package com.ar.vgmsistemas.bo;

import android.content.Context;
import android.text.Editable;

import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.ListaPrecio;
import com.ar.vgmsistemas.entity.ListaPrecioDetalle;
import com.ar.vgmsistemas.entity.PromocionDetalle;
import com.ar.vgmsistemas.entity.PromocionRequisito;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.repository.IPromocionRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.Formatter;

import java.util.List;


public class PromocionBo {
    private final RepositoryFactory _repoFactory;
    private final IPromocionRepository _promocionRepository;

    public PromocionBo(RepositoryFactory repositoryFactory) {
        this._repoFactory = repositoryFactory;
        this._promocionRepository = repositoryFactory.getPromocionRepository();
    }

    public List<PromocionDetalle> recoveryPromocionItems(Articulo articulo) throws Exception {
        return _promocionRepository.recoveryItems(articulo);
    }

    public List<PromocionDetalle> recoveryItemsByItem(Articulo articuloItem) throws Exception {
        return _promocionRepository.recoveryItemsByItem(articuloItem);
    }

    public static String getCantidadRequerida(long cantArti, Editable cantCombo) {
        return getCantidadRequerida(cantArti, cantCombo.toString());
    }

    public static String getCantidadRequerida(long cantArti, String cantCombo) {
        return getCantidadRequerida(cantArti, Formatter.parseInt(cantCombo));
    }

    public static String getCantidadRequerida(long cantArti, int cantCombo) {
        return String.valueOf(getCantidadRequeridaInt(cantArti, cantCombo));
    }

    public static int getCantidadRequeridaInt(long cantArti, int cantCombo) {
        return (int) (cantArti * cantCombo);
    }

    /**
     * rellena las promociones detalle a partir de las ventas detalle, solo llena las promociones detalles correspondientes.
     * Es decir les setea las unidades, bultos y cantidades correspondientes.
     */
    public static void getPromocionDetalles(List<VentaDetalle> detalles, List<PromocionDetalle> promocionesDetalle) {
        for (VentaDetalle ventaDetalle : detalles) {
            for (PromocionDetalle promocionDetalle : promocionesDetalle) {
                if (ventaDetalle.getArticulo().getId() == promocionDetalle.getArticulo().getId()) {
                    promocionDetalle.setCantidad(ventaDetalle.getCantidad());
                    promocionDetalle.setBultos(ventaDetalle.getBultos());
                    promocionDetalle.setUnidades(ventaDetalle.getUnidades());
                    continue;
                }
            }
        }

    }

    public static boolean esCabeceraComboEspecial(long idArticulo, ListaPrecio listaPrecio, Context context) throws Exception {
        PromocionRequisito promocionRequisito = null;
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(context, RepositoryFactory.ROOM);
        if (listaPrecio.getTipoLista() == ListaPrecio.TIPO_LISTA_COMBOS_ESPECIALES) {
            PromocionRequisitoBo bo = new PromocionRequisitoBo(repoFactory);

            try {
                promocionRequisito = bo.getRequisito(idArticulo);
            } catch (Exception exception) {
                return false;
            }

        }
        return promocionRequisito != null;
    }

    public static boolean esDetalleComboEspecial(Articulo articulo, ListaPrecio listaPrecio, Context context) throws Exception {
        if (listaPrecio.getTipoLista() == ListaPrecio.TIPO_LISTA_COMBOS_ESPECIALES) {
            RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(context, RepositoryFactory.ROOM);
            IPromocionRepository promocionRepository = repoFactory != null ? repoFactory.getPromocionRepository() : null;
            return (promocionRepository.recoveryItems(articulo).size() > 0);
        }
        return false;
    }

    public void setearImpuestoInternoCabeceraCombo(Articulo cabeceraCombo, ListaPrecioDetalle listaPrecioDetalle) {
        double precioImpuestoInterno = 0f;
        List<PromocionDetalle> articulosPromociones = null;
        try {
            articulosPromociones = _promocionRepository.recoveryItems(cabeceraCombo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (PromocionDetalle articuloPromocion : articulosPromociones) {
            double precioArticuloEnCombo = articuloPromocion.getPrecio();
            double unidadesDelArticuloEnCombo = articuloPromocion.getCantidadComboComun();
            double tasaImpuestoInterno = 0;
            try {
                tasaImpuestoInterno = articuloPromocion.getArticulo().getTasaImpuestoInterno();
            } catch (Exception e) {
                e.printStackTrace();
            }

            precioImpuestoInterno = precioImpuestoInterno +
                    (precioArticuloEnCombo * unidadesDelArticuloEnCombo * tasaImpuestoInterno);
        }
        cabeceraCombo.setImpuestoInterno(precioImpuestoInterno);

        double tasaImpuestoInterno = 0d;
        if (listaPrecioDetalle != null) {
            tasaImpuestoInterno = precioImpuestoInterno / listaPrecioDetalle.getPrecioSinIva();
        }
        cabeceraCombo.setTasaImpuestoInterno(tasaImpuestoInterno);
    }

    public static boolean esComboEspecialTipoDescuento(long idArticulo, ListaPrecio listaPrecio, Context context) throws Exception {
        PromocionRequisito promocionRequisito = null;
        if (listaPrecio.getTipoLista() == ListaPrecio.TIPO_LISTA_COMBOS_ESPECIALES) {
            RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(context, RepositoryFactory.ROOM);
            PromocionRequisitoBo bo = new PromocionRequisitoBo(repoFactory);

            try {
                promocionRequisito = bo.getRequisito(idArticulo);

            } catch (Exception exception) {
                return false;
            }

        }
        /*Tipo Requisito = 1 es por descuento y 2 por Regalo o sin Cargo*/
        return (promocionRequisito.getTipoRequisito() == 1);
    }
}
