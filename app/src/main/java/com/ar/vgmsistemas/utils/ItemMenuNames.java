package com.ar.vgmsistemas.utils;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.ItemMenu;
import com.ar.vgmsistemas.entity.VisibleItemMenu;

import java.util.Arrays;
import java.util.List;

public class ItemMenuNames {

    //Menu
    public static final int POS_CLIENTES = 1;
    public static final int POS_CUENTA_CORRIENTE = 2;
    public static final int POS_ARTICULOS = 3;
    public static final int POS_REPARTO = 4;
    public static final int POS_INFORM_Y_ESTADISTICAS = 5;
    public static final int POS_LISTADO_NO_ATENCION = 6;
    public static final int POS_LISTADO_PEDIDOS = 7;
    public static final int POS_VENTAS_ACUMULADAS = 8;
    public static final int POS_LISTADO_RECIBOS = 9;
    public static final int POS_LISTADO_EGRESOS = 10;
    public static final int POS_LISTADO_ENTREGAS = 11;
    public static final int POS_SINCRONIZACION = 12;
    public static final int POS_CONFIUGURACION = 13;
    public static final int POS_CONFIUGURACION_BASICA = 14;
    public static final int POS_CONFIUGURACION_AVANZADA = 15;
    public static final int POS_ESTADO_CONEXION = 16;
    public static final int POS_ACERCA_DE = 17;
    public static final int POS_INTEGRADO_MERCADERIA = 18;
    public static final int POS_LISTADO_PEDIDOS_REPARTO = 19;

    public static final int POS_DETALLE_ARTICULO = 20;
    public static final int POS_PRECIO_ARTICULO = 21;

    public static final int POS_VER_PEDIDOS = 22;
    public static final int POS_VER_VENTAS = 23;
    public static final int POS_VER_DETALLES = 24;
    public static final int POS_VER_OBJETIVOS = 25;

    public static final int POS_ACTUALIZAR = 26;
    public static final int POS_REPARTO_HOJAS = 27;

    public static final int POS_LISTADO_HOJAS = 28;

    public static final int POS_MAP_FRAGMENT = 25;


    public static final String STRING_CLIENTE = "Clientes";
    public static final String STRING_CUENTA_CORRIENTE = "Cuentas Corrientes";

    public static final String STRING_ARTICULOS = "Articulos";
    public static final String STRING_REPARTO = "Reparto";
    public static final String STRING_INTEGRADO_MERCADERIA = "Integrado de mercaderia";
    public static final String STRING_HOJAS = "Hojas de reparto";
    public static final String STRING_LISTADO_HOJAS = "Listado de hojas";

    public static final String STRING_INFORME_Y_ESTADISTICAS = "Informes y estadisticas";
    public static final String STRING_LISTADO_NO_ATENCION = "Listados de No Atenciones";
    public static final String STRING_LISTADOS_PEDIDOS = "Listado de Pedidos";
    public static final String STRING_VENTAS_ACUMULADAS = "Ventas Acumuladas";
    public static final String STRING_LISTADO_RECIBOS = "Listados de Recibos";
    public static final String STRING_LISTADO_EGRESOS = "Listados de Egresos";
    public static final String STRING_LISTADO_ENTREGAS = "Listados de Entregas";
    public static final String STRING_OBJETIVOS = "Objetivos";
    public static final String STRING_SINCRONIZACION = "Sincronizacion";
    public static final String STRING_CONFIGURACION = "Configuracion";
    public static final String STRING_CONFIGURACION_BASICA = "Configuracion Basica";
    public static final String STRING_CONFIGURACION_AVANZADA = "Configuración Avanzada";
    public static final String STRING_ESTADO_CONEXION = "Estado de la conexion";
    public static final String STRING_ACERCA_DE = "Acerca de";

    public static final String STRING_DETALLE_ARTICULO = "Detalle del articulo";
    public static final String STRING_PRECIO_ARTICULO = "Precios del articulo";

    public static final String STRING_PEDIDOS_CLIENTE = "Listado de pedidos";
    public static final String STRING_VENTAS_CLIENTE = "Listado de ventas";
    public static final String STRING_DETALLE_CLIENTE = "Detalle del Cliente";

    public static final String STRING_AGREGAR_LINEA = "Agregar linea";
    public static final String STRING_REGISTRAR_PEDIDO = "Pedido";
    public static final String STRING_VER_CLIENTE_EN_MAPA = "Ubicacion del cliente";

    public static final String STRING_ACTUALIZAR = "Actualizar";
    public static final String STRING_VENTAS_POR_ARTICULOS = "Ventas por articulos";


    //ITEMS--------------------------
    public static final VisibleItemMenu ITEM_CLIENTE = new VisibleItemMenu(STRING_CLIENTE, null, R.drawable.ic_cliente, R.drawable.ic_cliente_b, VisibleItemMenu.FRAGMENT, POS_CLIENTES, null);
    public static final VisibleItemMenu ITEM_ARTICULOS = new VisibleItemMenu(STRING_ARTICULOS, null, R.drawable.ic_articulos, R.drawable.ic_articulos_b, VisibleItemMenu.FRAGMENT, POS_ARTICULOS, null);
    public static final VisibleItemMenu ITEM_CUENTA_CORRIENTE = new VisibleItemMenu(STRING_CUENTA_CORRIENTE, null, R.drawable.cuenta_corriente, R.drawable.cuenta_corriente_b, VisibleItemMenu.FRAGMENT, POS_CUENTA_CORRIENTE, null);


    public static final VisibleItemMenu ITEM_REPARTO_HOJAS = new VisibleItemMenu(STRING_HOJAS, null, R.drawable.childitem, R.drawable.childitembco, VisibleItemMenu.FRAGMENT, POS_REPARTO_HOJAS, POS_REPARTO_HOJAS);
    public static final VisibleItemMenu ITEM_LISTADO_PEDIDOS_REPARTO = new VisibleItemMenu(STRING_LISTADOS_PEDIDOS, null, R.drawable.childitem, R.drawable.childitembco, VisibleItemMenu.FRAGMENT, POS_LISTADO_PEDIDOS_REPARTO, POS_REPARTO);
    public static final VisibleItemMenu ITEM_INTEGRADO_MERCADERIA = new VisibleItemMenu(STRING_INTEGRADO_MERCADERIA, null, R.drawable.childitem, R.drawable.childitembco, VisibleItemMenu.FRAGMENT, POS_INTEGRADO_MERCADERIA, POS_REPARTO);
    public static final VisibleItemMenu ITEM_LISTADO_HOJAS = new VisibleItemMenu(STRING_LISTADO_HOJAS, null, R.drawable.childitem, R.drawable.childitembco, VisibleItemMenu.FRAGMENT, POS_LISTADO_HOJAS, POS_REPARTO);

    public static VisibleItemMenu[] listReparto = {ITEM_REPARTO_HOJAS, ITEM_LISTADO_PEDIDOS_REPARTO, ITEM_INTEGRADO_MERCADERIA, ITEM_LISTADO_HOJAS};
    public static final VisibleItemMenu ITEM_REPARTO = new VisibleItemMenu(STRING_REPARTO, listReparto, R.drawable.reparto, R.drawable.reparto_b, VisibleItemMenu.FRAGMENT, POS_REPARTO, null);


    //SUB ITEMS INFORMES Y ESTADISTICAS
    public static final VisibleItemMenu ITEM_LISTADO_NO_ATENCION = new VisibleItemMenu(STRING_LISTADO_NO_ATENCION, null, R.drawable.childitem, R.drawable.childitembco, VisibleItemMenu.FRAGMENT, POS_LISTADO_NO_ATENCION, POS_INFORM_Y_ESTADISTICAS);
    public static final VisibleItemMenu ITEM_LISTADO_PEDIDOS = new VisibleItemMenu(STRING_LISTADOS_PEDIDOS, null, R.drawable.childitem, R.drawable.childitembco, VisibleItemMenu.FRAGMENT, POS_LISTADO_PEDIDOS, POS_INFORM_Y_ESTADISTICAS);
    public static final VisibleItemMenu ITEM_VENTAS_ACUMULADAS = new VisibleItemMenu(STRING_VENTAS_ACUMULADAS, null, R.drawable.childitem, R.drawable.childitembco, VisibleItemMenu.FRAGMENT, POS_VENTAS_ACUMULADAS, POS_INFORM_Y_ESTADISTICAS);
    public static final VisibleItemMenu ITEM_LISTADO_RECIBOS = new VisibleItemMenu(STRING_LISTADO_RECIBOS, null, R.drawable.childitem, R.drawable.childitembco, VisibleItemMenu.FRAGMENT, POS_LISTADO_RECIBOS, POS_INFORM_Y_ESTADISTICAS);
    public static final VisibleItemMenu ITEM_OBJETIVOS = new VisibleItemMenu(STRING_OBJETIVOS, null, R.drawable.childitem, R.drawable.childitembco, VisibleItemMenu.FRAGMENT, POS_VER_OBJETIVOS, POS_INFORM_Y_ESTADISTICAS);

    public static VisibleItemMenu[] listItemsInformes = {ITEM_LISTADO_NO_ATENCION, ITEM_LISTADO_PEDIDOS, ITEM_VENTAS_ACUMULADAS, ITEM_LISTADO_RECIBOS, /*ITEM_LISTADO_EGRESOS, ITEM_LISTADO_ENTREGAS,*/ ITEM_OBJETIVOS};

    public static final VisibleItemMenu ITEM_INFORME_Y_ESTADISTICAS = new VisibleItemMenu(STRING_INFORME_Y_ESTADISTICAS, listItemsInformes, R.drawable.estadistica, R.drawable.estadistica_b, VisibleItemMenu.FRAGMENT, POS_INFORM_Y_ESTADISTICAS, null);

    public static final VisibleItemMenu ITEM_SINCRONIZACION = new VisibleItemMenu(STRING_SINCRONIZACION, null, R.drawable.ic_sincronizacion, R.drawable.ic_sincronizacion_b, VisibleItemMenu.FRAGMENT, POS_SINCRONIZACION, null);
    //SUB ITEMS CONFIGURACION
    public static VisibleItemMenu ITEM_CONFIGURACION_BASICA = new VisibleItemMenu(STRING_CONFIGURACION_BASICA, null, R.drawable.childitem, R.drawable.childitembco, VisibleItemMenu.FRAGMENT, POS_CONFIUGURACION_BASICA, POS_CONFIUGURACION);
    public static VisibleItemMenu ITEM_CONFIGURACION_AVANZADA = new VisibleItemMenu(STRING_CONFIGURACION_AVANZADA, null, R.drawable.childitem, R.drawable.childitembco, VisibleItemMenu.ACTIVITY, POS_CONFIUGURACION_AVANZADA, POS_CONFIUGURACION);
    public static VisibleItemMenu ITEM_VER_ESTADO_CONEXION = new VisibleItemMenu(STRING_ESTADO_CONEXION, null, R.drawable.childitem, R.drawable.childitembco, VisibleItemMenu.FRAGMENT, POS_ESTADO_CONEXION, POS_CONFIUGURACION);

    public static VisibleItemMenu[] listConfiguracion = {ITEM_CONFIGURACION_BASICA, ITEM_CONFIGURACION_AVANZADA, ITEM_VER_ESTADO_CONEXION};

    public static final VisibleItemMenu ITEM_CONFIGURACION = new VisibleItemMenu(STRING_CONFIGURACION, listConfiguracion, R.drawable.ic_config, R.drawable.ic_config_b, VisibleItemMenu.FRAGMENT, POS_CONFIUGURACION, null);

    public static final VisibleItemMenu ITEM_ACERCA_DE = new VisibleItemMenu(STRING_ACERCA_DE, null, R.drawable.acerca_de, R.drawable.acerca_de_b, VisibleItemMenu.FRAGMENT, POS_ACERCA_DE, null);

    public static final ItemMenu ITEM_DETALLE_ARTICULO = new ItemMenu(STRING_DETALLE_ARTICULO, POS_DETALLE_ARTICULO, VisibleItemMenu.FRAGMENT, true);
    public static final ItemMenu ITEM_PRECIO_ARTICULO = new ItemMenu(STRING_PRECIO_ARTICULO, POS_PRECIO_ARTICULO, ItemMenu.FRAGMENT, true);
    public static final ItemMenu ITEM_ACTUALIZAR = new ItemMenu(STRING_ACTUALIZAR, POS_ACTUALIZAR, ItemMenu.FRAGMENT, true);

    public static final ItemMenu ITEM_VER_PEDIDOS = new ItemMenu(STRING_PEDIDOS_CLIENTE, POS_VER_PEDIDOS, ItemMenu.FRAGMENT, true);
    public static final ItemMenu ITEM_VER_VENTAS = new ItemMenu(STRING_VENTAS_CLIENTE, POS_VER_VENTAS, ItemMenu.FRAGMENT, true);
    public static final ItemMenu ITEM_VER_DETALLES = new ItemMenu(STRING_DETALLE_CLIENTE, POS_VER_DETALLES, ItemMenu.FRAGMENT, true);

    public static List<VisibleItemMenu> listVisibleItemMenus = Arrays.asList(
            ITEM_CLIENTE,
            ITEM_CUENTA_CORRIENTE,
            ITEM_REPARTO,
            //ITEM_RENDICION,
            ITEM_ARTICULOS,
            ITEM_INFORME_Y_ESTADISTICAS,

            ITEM_SINCRONIZACION,
            ITEM_CONFIGURACION,

            ITEM_ACERCA_DE);
}
