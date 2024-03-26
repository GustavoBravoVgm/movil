package com.ar.vgmsistemas.entity;

import java.util.ArrayList;
import java.util.List;


public class Valor {


    private List<ValorHardcodeado> valoresHardcodeados = new ArrayList<>();

    public final static String BILLETE_DOS_MIL = "x 2000";

    public final static String BILLETE_MIL = "x 1000";

    public final static String BILLETE_QUINIENTOS = "x 500";

    public final static String BILLETE_DOSCIENTOS = "x 200";

    public final static String BILLETE_CIEN = "x 100";

    public final static String BILLETE_CINCUENTA = "x 50";

    public final static String BILLETE_VEINTE = "x 20";

    public final static String BILLETE_DIEZ = "x 10";

    public final static String BILLETE_CINCO = "x 5";

    public final static String BILLETE_DOS = "x 2";


    public final static String MONEDAS = "Monedas";

    public final static String TICKETS = "Tickets";

    public final static String BANCO = "Banco";

    public final static String OTROS = "Otros";

    public Valor() {

        ValorHardcodeado valorHardcodeado = new ValorHardcodeado();
        valorHardcodeado.setDescripcion(BILLETE_QUINIENTOS);
        valorHardcodeado.setValor(500);
        valorHardcodeado.setCalculable(true);
        valoresHardcodeados.add(valorHardcodeado);

        ValorHardcodeado valorHardcodeado1 = new ValorHardcodeado();
        valorHardcodeado1.setDescripcion(BILLETE_DOSCIENTOS);
        valorHardcodeado1.setValor(200);
        valorHardcodeado1.setCalculable(true);
        valoresHardcodeados.add(valorHardcodeado1);

        ValorHardcodeado valorHardcodeado2 = new ValorHardcodeado();
        valorHardcodeado2.setDescripcion(BILLETE_CIEN);
        valorHardcodeado2.setValor(100);
        valorHardcodeado2.setCalculable(true);
        valoresHardcodeados.add(valorHardcodeado2);

        ValorHardcodeado valorHardcodeado3 = new ValorHardcodeado();
        valorHardcodeado3.setDescripcion(BILLETE_CINCUENTA);
        valorHardcodeado3.setValor(50);
        valorHardcodeado3.setCalculable(true);
        valoresHardcodeados.add(valorHardcodeado3);

        ValorHardcodeado valorHardcodeado4 = new ValorHardcodeado();
        valorHardcodeado4.setDescripcion(BILLETE_VEINTE);
        valorHardcodeado4.setValor(20);
        valorHardcodeado4.setCalculable(true);
        valoresHardcodeados.add(valorHardcodeado4);

        ValorHardcodeado valorHardcodeado5 = new ValorHardcodeado();
        valorHardcodeado5.setDescripcion(BILLETE_DIEZ);
        valorHardcodeado5.setValor(10);
        valorHardcodeado5.setCalculable(true);
        valoresHardcodeados.add(valorHardcodeado5);

        ValorHardcodeado valorHardcodeado6 = new ValorHardcodeado();
        valorHardcodeado6.setDescripcion(BILLETE_CINCO);
        valorHardcodeado6.setValor(5);
        valorHardcodeado6.setCalculable(true);
        valoresHardcodeados.add(valorHardcodeado6);

        ValorHardcodeado valorHardcodeado7 = new ValorHardcodeado();
        valorHardcodeado7.setDescripcion(BILLETE_DOS);
        valorHardcodeado7.setValor(2);
        valorHardcodeado7.setCalculable(true);
        valoresHardcodeados.add(valorHardcodeado7);

        ValorHardcodeado valorHardcodeado9 = new ValorHardcodeado();
        valorHardcodeado9.setDescripcion(MONEDAS);
        valoresHardcodeados.add(valorHardcodeado9);

        ValorHardcodeado valorHardcodeado10 = new ValorHardcodeado();
        valorHardcodeado10.setDescripcion(TICKETS);
        valoresHardcodeados.add(valorHardcodeado10);

        ValorHardcodeado valorHardcodeado11 = new ValorHardcodeado();
        valorHardcodeado11.setDescripcion(BANCO);
        valoresHardcodeados.add(valorHardcodeado11);

        ValorHardcodeado valorHardcodeado12 = new ValorHardcodeado();
        valorHardcodeado12.setDescripcion(OTROS);
        valoresHardcodeados.add(valorHardcodeado12);

        ValorHardcodeado valorHardcodeado13 = new ValorHardcodeado();
        valorHardcodeado13.setDescripcion(BILLETE_MIL);
        valorHardcodeado13.setValor(1000);
        valorHardcodeado13.setCalculable(true);
        valoresHardcodeados.add(valorHardcodeado13);

        ValorHardcodeado valorHardcodeado14 = new ValorHardcodeado();
        valorHardcodeado14.setDescripcion(BILLETE_DOS_MIL);
        valorHardcodeado14.setValor(2000);
        valorHardcodeado14.setCalculable(true);
        valoresHardcodeados.add(valorHardcodeado14);
    }

    public List<ValorHardcodeado> getValoresHardcodeados() {
        return valoresHardcodeados;
    }

    public void setValoresHardcodeados(List<ValorHardcodeado> valoresHardcodeados) {
        this.valoresHardcodeados = valoresHardcodeados;
    }
}
