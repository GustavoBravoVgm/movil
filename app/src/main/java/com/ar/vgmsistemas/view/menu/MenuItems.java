package com.ar.vgmsistemas.view.menu;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MenuItems {

    public static final String ITEM_CLIENTES = "Clientes";
    public static final String ITEM_ARTICULOS = "Articulos";
    public static final String ITEM_REPARTO = "Reparto";
    public static final String ITEM_RENDICION = "Rendicion";
    public static final String ITEM_ESTADISTICA = "Estadistica";
    public static final String ITEM_SINCRONIZAR = "Sincronizar";
    public static final String ITEM_CONFIGURACION = "Configuraciones";
    public static final String ITEM_ACERCA_DE = "Acerca De";
    public static final String ITEM_SALIR = "Salir";

    public static final int POS_CLIENTES = 1;
    public static final int POS_ARTICULOS = 2;
    public static final int POS_REPARTO = 3;
    public static final int POS_RENDICION = 4;
    public static final int POS_ESTADISTICA = 5;
    public static final int POS_SINCRONIZAR = 6;
    public static final int POS_CONFIGURACION = 7;
    public static final int POS_ACERCA_DE = 8;
    public static final int POS_SALIR = 9;

    private static final Map<String, Integer> itemPosMap;

    static {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put(ITEM_CLIENTES, POS_CLIENTES);
        map.put(ITEM_ARTICULOS, POS_ARTICULOS);
        map.put(ITEM_REPARTO, POS_REPARTO);
        map.put(ITEM_RENDICION, POS_RENDICION);
        map.put(ITEM_ESTADISTICA, POS_ESTADISTICA);
        map.put(ITEM_SINCRONIZAR, POS_SINCRONIZAR);
        map.put(ITEM_CONFIGURACION, POS_CONFIGURACION);
        map.put(ITEM_ACERCA_DE, POS_ACERCA_DE);
        map.put(ITEM_SALIR, POS_SALIR);

        itemPosMap = Collections.unmodifiableMap(map);
    }

    public static int getPosItem(String item) {
        return itemPosMap.get(item);
    }


    public static boolean haveSubItems(int posMenu) {
        switch (posMenu) {
            case POS_REPARTO:
                return true;
            case POS_ESTADISTICA:
                return true;
            case POS_CONFIGURACION:
                return true;
            default:
                return false;
        }
    }
}
