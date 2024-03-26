package com.ar.vgmsistemas.utils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/* La cantidad de elementos de los arreglos que recibe el constructor deben ser del mismo tamaño y ordenada*/

public class ArmarMapping {
    private int[] _clave;
    private String[] _valores;
    private Map<Integer, String> opcionesDialogos;

    public ArmarMapping(int[] clave, String[] valores) {
        _clave = clave;
        _valores = valores;
        initMap();
    }


    public void initMap() {
        {
            Map<Integer, String> aMap = new TreeMap<>();
            for (int i = 0; i < _clave.length; i++) {
                aMap.put(_clave[i], _valores[i]);
            }
            opcionesDialogos = Collections.unmodifiableMap(aMap);
        }
    }


    public CharSequence[] armarCharSecuence(List<Integer> listValidos) {
        CharSequence[] listaValidada = new CharSequence[listValidos.size()];
        for (int i = 0; i < listValidos.size(); i++) {
            listaValidada[i] = opcionesDialogos.get(listValidos.get(i));
        }
        return listaValidada;
    }

    public String[] armarStringSecuence(List<Integer> listValidos) {
        String[] listaValidada = new String[listValidos.size()];
        for (int i = 0; i < listValidos.size(); i++) {
            listaValidada[i] = opcionesDialogos.get(listValidos.get(i));
        }
        return listaValidada;
    }
}
