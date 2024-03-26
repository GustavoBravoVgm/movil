package com.ar.vgmsistemas.utils;

public class Matematica {
    public static final int cantDecimales = 3;

    public static double Round(double numero, int digitos) {
        int cifras = (int) Math.pow(10, digitos);
        numero = numero * cifras;
        long tmp = Math.round(numero);
        return (double) tmp / cifras;
    }

    public static double Trunc(double numero, int digitos) {
        String aux = String.valueOf(numero);
        if (aux.contains(",")) {
            int posicionComa = aux.indexOf(',');
            aux.substring(0, posicionComa + digitos);
            return Float.valueOf(aux);
        } else return numero;
    }

    public static double restarPorcentaje(double numero, double porciento) {
        return ((100 - porciento) / 100) * numero;
    }

    public static double calcularPorcentaje(double numero, double porciento) {
        return numero * porciento / 100;
    }
}
