package com.ar.vgmsistemas.view.menu;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.utils.ItemMenuNames;
import com.ar.vgmsistemas.view.FrmAcercaDe;
import com.ar.vgmsistemas.view.FrmActualizar;
import com.ar.vgmsistemas.view.FrmLogin;
import com.ar.vgmsistemas.view.articulo.FrmDetalleArticulo;
import com.ar.vgmsistemas.view.articulo.FrmGestionArticulo;
import com.ar.vgmsistemas.view.articulo.FrmPreciosXArticulo;
import com.ar.vgmsistemas.view.cliente.FrmGestionCliente;
import com.ar.vgmsistemas.view.cobranza.cuentacorriente.FrmGestionCuentaCorriente;
import com.ar.vgmsistemas.view.configuracion.FrmConfiguracionEstandar;
import com.ar.vgmsistemas.view.informes.FrmListadoNoPedido;
import com.ar.vgmsistemas.view.informes.FrmListadoRecibo;
import com.ar.vgmsistemas.view.informes.FrmListadoVenta;
import com.ar.vgmsistemas.view.informes.FrmVentasAcumuladas;
import com.ar.vgmsistemas.view.informes.objetivos.VendedorObjetivoFragment;
import com.ar.vgmsistemas.view.reparto.FrmIntegradoMercaderia;
import com.ar.vgmsistemas.view.reparto.hojas.FrmHojas;
import com.ar.vgmsistemas.view.reparto.hojas.listadoHojas.ListadoHojasFragment;
import com.ar.vgmsistemas.view.sincronizacion.FrmEstadoConexion;
import com.ar.vgmsistemas.view.sincronizacion.FrmSincronizacion;

public class MenuManager {

    public static Fragment getFragment(int posItem) {
        switch (posItem) {
            case ItemMenuNames.POS_ACERCA_DE:
                return new FrmAcercaDe();
            case ItemMenuNames.POS_CUENTA_CORRIENTE:
                return new FrmGestionCuentaCorriente();
            case ItemMenuNames.POS_ARTICULOS:
                return new FrmGestionArticulo();
            case ItemMenuNames.POS_INTEGRADO_MERCADERIA:
                return new FrmIntegradoMercaderia();
            case ItemMenuNames.POS_LISTADO_HOJAS:
                return new ListadoHojasFragment();
            case ItemMenuNames.POS_SINCRONIZACION:
                return new FrmSincronizacion();
            case ItemMenuNames.POS_LISTADO_NO_ATENCION:
                return new FrmListadoNoPedido();
            case ItemMenuNames.POS_LISTADO_RECIBOS:
                return new FrmListadoRecibo();
        /*case ItemMenuNames.POS_LISTADO_EGRESOS:
		    return new FrmListadoEgresos();
        case ItemMenuNames.POS_LISTADO_ENTREGAS:
		    return new FrmListadoEntregas();*/
            case ItemMenuNames.POS_VER_OBJETIVOS:
                return new VendedorObjetivoFragment();
            case ItemMenuNames.POS_LISTADO_PEDIDOS:
                return FrmListadoVenta.newInstance(PreferenciaBo.getInstance().getPreferencia().getIdTipoDocumentoPorDefecto());
            case ItemMenuNames.POS_VENTAS_ACUMULADAS:
                return new FrmVentasAcumuladas();
            case ItemMenuNames.POS_LISTADO_PEDIDOS_REPARTO:
                return FrmListadoVenta.newInstance(FrmListadoVenta.MODO_REPARTO);
            case ItemMenuNames.POS_CONFIUGURACION_BASICA:
                return new FrmConfiguracionEstandar();
            case ItemMenuNames.POS_ESTADO_CONEXION:
                return new FrmEstadoConexion();
            case ItemMenuNames.POS_DETALLE_ARTICULO:
                return new FrmDetalleArticulo();
            case ItemMenuNames.POS_PRECIO_ARTICULO:
                return new FrmPreciosXArticulo();
            case ItemMenuNames.POS_CLIENTES:
                return new FrmGestionCliente();
            case ItemMenuNames.POS_ACTUALIZAR:
                return new FrmActualizar();
            case ItemMenuNames.POS_REPARTO_HOJAS:
                return new FrmHojas();
		/*case ItemMenuNames.POS_CARGA_EGRESOS:
			return new FrmEgreso();
		case ItemMenuNames.POS_CARGA_ENTREGAS:
			return new FrmEntrega();*/
            default:
                return null;

            /*
             * case ItemMenuNames.POS_ARTICULOS: return new
             * FrmGestionArticulo();
             */
        }
        // return null;
    }

    public static Intent getIntent(int posItem, Context context) {
        switch (posItem) {
            case ItemMenuNames.POS_CONFIUGURACION_AVANZADA:
                Intent frmLogin = new Intent(context, FrmLogin.class);
                frmLogin.putExtra(FrmLogin.EXTRA_MODO, FrmLogin.MODO_CONFIGURACION);
                frmLogin.putExtra(FrmLogin.EXTRA_UBICACION, FrmLogin.EXTRA_UBICACION_MENU);
                return (frmLogin);
        }
        return null;
    }
}
