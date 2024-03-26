package com.ar.vgmsistemas.bo;

import static com.ar.vgmsistemas.entity.Valor.BANCO;
import static com.ar.vgmsistemas.entity.Valor.BILLETE_CIEN;
import static com.ar.vgmsistemas.entity.Valor.BILLETE_CINCO;
import static com.ar.vgmsistemas.entity.Valor.BILLETE_CINCUENTA;
import static com.ar.vgmsistemas.entity.Valor.BILLETE_DIEZ;
import static com.ar.vgmsistemas.entity.Valor.BILLETE_DOS;
import static com.ar.vgmsistemas.entity.Valor.BILLETE_DOSCIENTOS;
import static com.ar.vgmsistemas.entity.Valor.BILLETE_QUINIENTOS;
import static com.ar.vgmsistemas.entity.Valor.BILLETE_VEINTE;
import static com.ar.vgmsistemas.entity.Valor.MONEDAS;
import static com.ar.vgmsistemas.entity.Valor.OTROS;
import static com.ar.vgmsistemas.entity.Valor.TICKETS;

import com.ar.vgmsistemas.entity.Entrega;
import com.ar.vgmsistemas.entity.EntregaRendicion;
import com.ar.vgmsistemas.entity.ValorHardcodeado;
import com.ar.vgmsistemas.repository.IEntregaRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.ArrayList;
import java.util.List;

public class EntregaBo {

    private final IEntregaRepository _entregaRepository;

    public EntregaBo(RepositoryFactory repoFactory) {
        this._entregaRepository = repoFactory.getEntregaRepository();
    }

    public static List<ValorHardcodeado> entregaRendicionToList(EntregaRendicion entregaRendicion) {
        List<ValorHardcodeado> list = new ArrayList<>();
        if (entregaRendicion.getPrBillete8() > 0) {
            ValorHardcodeado valor = new ValorHardcodeado();
            valor.setValor(500);
            valor.setDescripcion(BILLETE_QUINIENTOS);
            valor.setCantidad(entregaRendicion.getCaBillete8());
            valor.setImporte(entregaRendicion.getPrBillete8());
            list.add(valor);
        }
        if (entregaRendicion.getPrBillete7() > 0) {
            ValorHardcodeado valor = new ValorHardcodeado();
            valor.setValor(200);
            valor.setDescripcion(BILLETE_DOSCIENTOS);
            valor.setCantidad(entregaRendicion.getCaBillete7());
            valor.setImporte(entregaRendicion.getPrBillete7());
            list.add(valor);
        }
        if (entregaRendicion.getPrBillete6() > 0) {
            ValorHardcodeado valor = new ValorHardcodeado();
            valor.setValor(100);
            valor.setDescripcion(BILLETE_CIEN);
            valor.setCantidad(entregaRendicion.getCaBillete6());
            valor.setImporte(entregaRendicion.getPrBillete6());
            list.add(valor);
        }
        if (entregaRendicion.getPrBillete5() > 0) {
            ValorHardcodeado valor = new ValorHardcodeado();
            valor.setValor(50);
            valor.setDescripcion(BILLETE_CINCUENTA);
            valor.setCantidad(entregaRendicion.getCaBillete5());
            valor.setImporte(entregaRendicion.getPrBillete5());
            list.add(valor);
        }
        if (entregaRendicion.getPrBillete4() > 0) {
            ValorHardcodeado valor = new ValorHardcodeado();
            valor.setValor(20);
            valor.setDescripcion(BILLETE_VEINTE);
            valor.setCantidad(entregaRendicion.getCaBillete4());
            valor.setImporte(entregaRendicion.getPrBillete4());
            list.add(valor);
        }
        if (entregaRendicion.getPrBillete3() > 0) {
            ValorHardcodeado valor = new ValorHardcodeado();
            valor.setValor(10);
            valor.setDescripcion(BILLETE_DIEZ);
            valor.setCantidad(entregaRendicion.getCaBillete3());
            valor.setImporte(entregaRendicion.getPrBillete3());
            list.add(valor);
        }
        if (entregaRendicion.getPrBillete2() > 0) {
            ValorHardcodeado valor = new ValorHardcodeado();
            valor.setValor(5);
            valor.setDescripcion(BILLETE_CINCO);
            valor.setCantidad(entregaRendicion.getCaBillete2());
            valor.setImporte(entregaRendicion.getPrBillete2());
            list.add(valor);
        }
        if (entregaRendicion.getPrBillete1() > 0) {
            ValorHardcodeado valor = new ValorHardcodeado();
            valor.setValor(2);
            valor.setDescripcion(BILLETE_DOS);
            valor.setCantidad(entregaRendicion.getCaBillete1());
            valor.setImporte(entregaRendicion.getPrBillete1());
            list.add(valor);
        }

        if (entregaRendicion.getPrTickets() > 0) {
            ValorHardcodeado valor = new ValorHardcodeado();
            valor.setDescripcion(TICKETS);
            valor.setImporte(entregaRendicion.getPrTickets());
            list.add(valor);
        }
        if (entregaRendicion.getPrMonedas() > 0) {
            ValorHardcodeado valor = new ValorHardcodeado();
            valor.setDescripcion(MONEDAS);
            valor.setImporte(entregaRendicion.getPrMonedas());
            list.add(valor);
        }
        if (entregaRendicion.getPrBanco() > 0) {
            ValorHardcodeado valor = new ValorHardcodeado();
            valor.setDescripcion(BANCO);
            valor.setImporte(entregaRendicion.getPrBanco());
            list.add(valor);
        }
        if (entregaRendicion.getPrOtros() > 0) {
            ValorHardcodeado valor = new ValorHardcodeado();
            valor.setDescripcion(OTROS);
            valor.setImporte(entregaRendicion.getPrOtros());
            list.add(valor);
        }
        return list;
    }

    public static EntregaRendicion listToEntregaRendicion(List<ValorHardcodeado> list) {
        EntregaRendicion entregaRendicion = new EntregaRendicion();
        for (ValorHardcodeado valorHardcodeado : list) {
            switch (valorHardcodeado.getDescripcion()) {
                case BILLETE_QUINIENTOS:
                    entregaRendicion.setCaBillete8(valorHardcodeado.getCantidad());
                    entregaRendicion.setPrBillete8(valorHardcodeado.getImporte());
                    break;
                case BILLETE_DOSCIENTOS:
                    entregaRendicion.setCaBillete7(valorHardcodeado.getCantidad());
                    entregaRendicion.setPrBillete7(valorHardcodeado.getImporte());
                    break;
                case BILLETE_CIEN:
                    entregaRendicion.setCaBillete6(valorHardcodeado.getCantidad());
                    entregaRendicion.setPrBillete6(valorHardcodeado.getImporte());
                    break;
                case BILLETE_CINCUENTA:
                    entregaRendicion.setCaBillete5(valorHardcodeado.getCantidad());
                    entregaRendicion.setPrBillete5(valorHardcodeado.getImporte());
                    break;
                case BILLETE_VEINTE:
                    entregaRendicion.setCaBillete4(valorHardcodeado.getCantidad());
                    entregaRendicion.setPrBillete4(valorHardcodeado.getImporte());
                    break;
                case BILLETE_DIEZ:
                    entregaRendicion.setCaBillete3(valorHardcodeado.getCantidad());
                    entregaRendicion.setPrBillete3(valorHardcodeado.getImporte());
                    break;
                case BILLETE_CINCO:
                    entregaRendicion.setCaBillete2(valorHardcodeado.getCantidad());
                    entregaRendicion.setPrBillete2(valorHardcodeado.getImporte());
                    break;
                case BILLETE_DOS:
                    entregaRendicion.setCaBillete1(valorHardcodeado.getCantidad());
                    entregaRendicion.setPrBillete1(valorHardcodeado.getImporte());
                    break;
                case MONEDAS:
                    entregaRendicion.setPrMonedas(valorHardcodeado.getImporte());
                    break;
                case TICKETS:
                    entregaRendicion.setPrTickets(valorHardcodeado.getImporte());
                    break;
                case BANCO:
                    entregaRendicion.setPrBanco(valorHardcodeado.getImporte());
                    break;
                case OTROS:
                    entregaRendicion.setPrOtros(valorHardcodeado.getImporte());
                    break;
            }
        }
        return entregaRendicion;
    }

    public Entrega recoveryByID(int id) throws Exception {
        return _entregaRepository.recoveryByID(id);
    }
}
