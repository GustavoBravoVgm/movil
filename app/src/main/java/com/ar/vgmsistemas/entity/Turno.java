package com.ar.vgmsistemas.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Turno {
    public static final String TURNO_MANIANA = "M";
    public static final String TURNO_TARDE = "T";
    public static final String TURNO_NOCHE = "N";
    public static final String SIN_TURNO = "S";

    public static final String MANIANA = "Mañana";
    public static final String TARDE = "Tarde";
    public static final String NOCHE = "Noche";
    public static final String NO_TURNO = "Sin Turno";

    public static final String[] turnosList = {NO_TURNO, MANIANA, TARDE, NOCHE};

    /**
     * Dada una posición te devuelve a que corresponde
     * 0 = S (Sin turno);
     * 1 = M (Turno mañana);
     * 2 = T (Turno tarde);
     * 3 = N (Turno noche);
     */
    public static final Map<Integer, String> turnosMap;

    static {
        Map<Integer, String> aMap = new HashMap<>();
        aMap.put(0, SIN_TURNO);
        aMap.put(1, TURNO_MANIANA);
        aMap.put(2, TURNO_TARDE);
        aMap.put(3, TURNO_NOCHE);
        turnosMap = Collections.unmodifiableMap(aMap);
    }

    /**
     * Dado un tipo de turno te devuelve la posición:
     * S (Sin turno) = 0 ;
     * M (Turno mañana) = 1 ;
     * T (Turno tarde) = 2 ;
     * N (Turno noche) = 3 ;
     */
    public static final Map<String, Integer> turnosMapInverse;

    static {
        Map<String, Integer> aMap = new HashMap<>();
        aMap.put(SIN_TURNO, 0);
        aMap.put(TURNO_MANIANA, 1);
        aMap.put(TURNO_TARDE, 2);
        aMap.put(TURNO_NOCHE, 3);
        turnosMapInverse = Collections.unmodifiableMap(aMap);
    }

}
