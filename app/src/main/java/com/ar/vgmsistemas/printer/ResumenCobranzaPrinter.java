package com.ar.vgmsistemas.printer;

import android.content.Context;
import android.util.Log;

import com.ar.vgmsistemas.entity.Cheque;
import com.ar.vgmsistemas.entity.CuentaCorriente;
import com.ar.vgmsistemas.entity.Deposito;
import com.ar.vgmsistemas.entity.Recibo;
import com.ar.vgmsistemas.entity.ReciboDetalle;
import com.ar.vgmsistemas.entity.Retencion;
import com.ar.vgmsistemas.printer.util.UtilPrinter;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.Matematica;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ResumenCobranzaPrinter extends /*Printer<Recibo>*/SuperPrinter {
    //private Empresa empresaDto;
    //private String empresa;
    //private String printerName;
    //private int widthPrinter;
    //private boolean snReciboMovilDto;
    public void print(List<Recibo> recibos) throws Exception {
        super.print(_context);
        Recibo reciboFirst = recibos.get(0);
        cabecera(ps, reciboFirst);

        double total = 0d;
        boolean snReciboEfectivo = false;
        boolean snReciboCheques = false;
        boolean snReciboRetenciones = false;
        boolean snReciboDeposito = false;
        printCabeceraRecibos(ps);
        for (Recibo recibo : recibos) {
            String line;
            double totalRecibo = recibo.getTotal();
            total = total + totalRecibo;
            String idRecibo = String.valueOf(recibo.getId().getIdRecibo());
            String sTotalRecibo = Formatter.formatNumber(totalRecibo, "#,##0.00");
            line = UtilPrinter.alignTextRight(idRecibo, sTotalRecibo, widthPrinter);
            ps.println(line);
            if (recibo.getEntrega().getPrEfectivoEntrega() > 0) {
                snReciboEfectivo = true;
            }
            if (recibo.getEntrega().getCheques().size() > 0) {
                snReciboCheques = true;
            }
            if (recibo.getEntrega().getRetenciones().size() > 0) {
                snReciboRetenciones = true;
            }
            if (recibo.getEntrega().getDepositos().size() > 0) {
                snReciboDeposito = true;
            }
        }
        ps.println();
        String sTotal = "TOTAL A RENDIR: $" + Formatter.formatNumber(total, "#,##0.00");
        ps.println(UtilPrinter.alignTextRight(sTotal, widthPrinter));
        ps.println();
        ps.println();

        if (snReciboEfectivo) {
            printCabeceraEfectivo(ps, recibos.get(0));
            double totEfectivo = 0d;
            for (Recibo recibo : recibos) {
                if (recibo.getEntrega().getPrEfectivoEntrega() > 0) {
                    totEfectivo = totEfectivo + recibo.getEntrega().getPrEfectivoEntrega();
                }
            }
            //entregaMoneda(ps, recibo.getEntrega().getPrEfectivoEntrega());
            printTotalEfectivo(ps, totEfectivo);
        }
        if (snReciboCheques) {
            printCabeceraCheques(ps, recibos.get(0));
            double totCheques = 0d;
            for (Recibo recibo : recibos) {
                if (recibo.getEntrega().getCheques().size() > 0) {
                    entregaCheque(ps, recibo);
                    totCheques = totCheques + recibo.getEntrega().calcularTotalEntregasCheque();
                }
            }
            printTotalCheques(ps, totCheques);
        }
        if (snReciboRetenciones) {
            printCabeceraRetenciones(ps, recibos.get(0));
            double totRetenciones = 0d;
            for (Recibo recibo : recibos) {
                if (recibo.getEntrega().getRetenciones().size() > 0) {
                    entregaRetencion(ps, recibo);
                    totRetenciones = totRetenciones + recibo.getEntrega().calcularTotalRetenciones();
                }
            }
            printTotalRetenciones(ps, totRetenciones);
        }

        if (snReciboDeposito) {
            printCabeceraDepositos(ps, recibos.get(0));
            double totDepositos = 0d;
            for (Recibo recibo : recibos) {
                if (recibo.getEntrega().getDepositos().size() > 0) {
                    entregaDepositos(ps, recibo);
                    totDepositos = totDepositos + recibo.getEntrega().calcularTotalEntregasDeposito();
                }
            }
            printTotalDepositos(ps, totDepositos);
        }
        ps.println();
        ps.printLine();
        String empresaString = "Por EMPRESA " + empresa;
        List<String> listPie = dividirString(empresaString);
        //String pie = UtilPrinter.center(empresaString, widthPrinter);
        for (String renglon : listPie) {
            ps.println(renglon);
        }
        ps.println();
        ps.println();
        Thread.sleep(5000);
        ps.flush();
        os.flush();

        ps.close();
        os.close();
        socket.close();
    }

    private void cabecera(BasePrintStream ps, Recibo recibo) {
        ps.println();
        List<String> listRengNombreEmpresa = dividirString(empresa);
        //String nombreEmpresa = UtilPrinter.center(empresa, widthPrinter);
        String comprobante = UtilPrinter.center("RESUMEN DE RECIBOS", widthPrinter);
        String letra = UtilPrinter.center(" ", widthPrinter);
        String noValidoComoFactura = UtilPrinter.center("Documento no valido como factura", widthPrinter);
        for (String renglon : listRengNombreEmpresa) {
            ps.println(renglon);
            Log.v("Fenibi", renglon);

        }

        ps.println(comprobante);
        ps.println(letra);
        ps.println(noValidoComoFactura);
        ps.println();
        Log.v("Fenibi", comprobante);
        Log.v("Fenibi", letra);
        Log.v("Fenibi", noValidoComoFactura);
        /*
         * String fecha = Formatter.formatDate(recibo.getFechaMovil());
         * ps.append("Fecha: ").append(fecha); ps.printlnCustom(widthPrinter);
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String fechaActual = Formatter.formatDateTimeMinutes(Calendar.getInstance().getTime());
        //String fechaActual = sdf.format(Calendar.getInstance().getTime());
        ps.println("Fecha: " + fechaActual);
        ps.println("Vendedor: " + String.valueOf(recibo.getVendedor().getId()) + "-" + String.valueOf(recibo.getVendedor().getNombre()) + " " + String.valueOf(recibo.getVendedor().getApellido()));
        String puntoVenta = ("PtoVta: ").concat(String.valueOf(recibo.getId().getIdPuntoVenta()));
        //String numero = ("Numero:").concat(String.valueOf(recibo.getId().getIdRecibo()));
        // String lineaIdRecibo = UtilPrinter.alignTextRight(numero, puntoVenta,
        // widthPrinter);
        //ps.println(lineaIdRecibo);
        ps.println(puntoVenta);
        Log.v("Fenibi", noValidoComoFactura);
        //ps.println(numero);
        //String idCliente = recibo.getCliente().getId().toString();
        // ps.append("Cliente: ").append("1223");
        //ps.println("Cliente: " + idCliente);
        ps.println();
        //ps.println(recibo.getCliente().getRazonSocial());
        //ps.println();
    }

    private void comprobante(BasePrintStream ps, Recibo recibo) {
        if (recibo.getDetalles().size() > 0) {
            ps.println("Comprobantes imputados");
            ps.println(UtilPrinter.getCabecera(UtilPrinter.CABECERA_NUMERO_FECHA_IMPORTE, widthPrinter, snReciboMovilDto));
            if (snReciboMovilDto) {
                ps.println(UtilPrinter.getCabecera(UtilPrinter.CABECERA_SALDO_DTO_PAGO, widthPrinter, snReciboMovilDto));
            }
            for (ReciboDetalle reciboDetalle : recibo.getDetalles()) {
                CuentaCorriente cuentaCorriente = reciboDetalle.getCuentaCorriente();
                String fecha = UtilPrinter.formatDate(cuentaCorriente.getFechaVenta());
                String comprobante = cuentaCorriente.getIdAsString();
                String line = UtilPrinter.armarLinea(comprobante, fecha,
                        UtilPrinter.getSeparationCabecera(UtilPrinter.CABECERA_NUMERO_FECHA_IMPORTE, widthPrinter));
                double importe = reciboDetalle.getImportePagado() * cuentaCorriente.getSigno();
                String sImporte = Formatter.formatNumber(importe, Formatter.FORMAT_MONEY);

                double saldoAntesDeImputarPago = reciboDetalle.getCuentaCorriente().calcularSaldo() + reciboDetalle.getImportePagado();
                double descuento = Matematica.calcularPorcentaje(saldoAntesDeImputarPago, reciboDetalle.getTaDtoRecibo());

                String lineaDescuento = null;
                if (snReciboMovilDto) {
                    String saldoFormateado = Formatter.formatNumber(saldoAntesDeImputarPago, Formatter.FORMAT_MONEY);
                    lineaDescuento = UtilPrinter.armarLinea(saldoFormateado, /*reciboDetalle.getTaDtoRecibo() + "% " +*/ Formatter.formatNumber(descuento, Formatter.FORMAT_MONEY), UtilPrinter.getSeparationCabecera(UtilPrinter.CABECERA_NUMERO_FECHA_IMPORTE, widthPrinter));
                    lineaDescuento = UtilPrinter.alignTextRight(lineaDescuento, sImporte, widthPrinter);
                } else {
                    //si no tiene habilitado descuento en recibos pongo el pago a la derecha en la primer linea
                    line = UtilPrinter.alignTextRight(line, sImporte, widthPrinter);
                }
                ps.println(line);
                if (lineaDescuento != null) {
                    ps.println(lineaDescuento);
                }


                if (cuentaCorriente.calcularSaldo() - descuento > 0.1) {
                    ps.println("Pago a cuenta: " + cuentaCorriente.getId().getIdNumeroDocumento());
                }

            }
            ps.println();
            double totalDocumentosImputados = recibo.obtenerTotalReciboDetalle();
            String sTotalDocumentosImputados = "TOTAL IMPUTADOS: $"
                    + Formatter.formatNumber(totalDocumentosImputados, "#,##0.00");
            ps.println(UtilPrinter.alignTextRight(sTotalDocumentosImputados, widthPrinter));
        }

        if (recibo.getComprobantesGenerados().size() > 0) {
            ps.println("Comprobantes generados");
            ps.println(UtilPrinter.getCabecera(UtilPrinter.CABECERA_TIPO_FECHA_IMPORTE, widthPrinter, snReciboMovilDto));
            for (CuentaCorriente cuentaCorriente : recibo.getComprobantesGenerados()) {
                String fecha = UtilPrinter.formatDate(cuentaCorriente.getFechaVenta());
                String comprobante = "Anticipo";
                String line = UtilPrinter.armarLinea(comprobante, fecha,
                        UtilPrinter.getSeparationCabecera(UtilPrinter.CABECERA_TIPO_FECHA_IMPORTE, widthPrinter));
                double importe = cuentaCorriente.getTotalCuota();
                String sImporte = Formatter.formatNumber(importe, "#,##0.00");
                line = UtilPrinter.alignTextRight(line, sImporte, widthPrinter);
                ps.println(line);
            }
            ps.println();
            double totalComprobantesGenerados = recibo.getTotalDocumentosGenerados();
            String sTotalDocumentosGenerados = "TOTAL ANTICIPO: $"
                    + Formatter.formatNumber(totalComprobantesGenerados, "#,##0.00");
            ps.println(UtilPrinter.alignTextRight(sTotalDocumentosGenerados, widthPrinter));
        }

        ps.println();
        // double totalComprobante = recibo.getTotal();
        // String sTotal = "TOTAL COMPROBANTE: $" +
        // Formatter.formatNumber(totalComprobante, "#,##0.00");
        // ps.println(UtilPrinter.alignTextRight(sTotal, "", widthPrinter));

        ps.printLine();
    } // NO SE USA POR EL MOMENTO LA IMPRESION DE LOS COMPROBANTES ASOCIADOS EN ESTE RESUMEN

    private void printCabeceraRecibos(BasePrintStream ps) {
        ps.println("Detalle de Recibos");
        ps.println(UtilPrinter.getCabecera(UtilPrinter.CABECERA_RECIBO_IMPORTE, widthPrinter, snReciboMovilDto));
        ps.println();
    }

    /*
    private void detalleRecibo(BasePrintStream ps, double efectivo) {
        String line;
        String idRecibo = recibo.getId();
        double importe = recibo.getTotal();
        String sImporte = Formatter.formatNumber(importe, "#,##0.00");
        line = UtilPrinter.alignTextRight(idRecibo, sImporte, widthPrinter);
        ps.println(line);
        ps.println();
    }
    */
    private void entregaMoneda(BasePrintStream ps, double efectivo) {
        String line;
        String tipo = "PESOS";
        double importe = efectivo;
        String sImporte = Formatter.formatNumber(importe, "#,##0.00");
        double importeMoneda = efectivo;
        String sImporteMoneda = Formatter.formatNumber(importeMoneda, "#,##0.00");
        String sCotizacion = "1.0";
        line = UtilPrinter.armarLinea(tipo, sCotizacion,
                UtilPrinter.getSeparationCabecera(UtilPrinter.CABECERA_TIPO_COT_IMPORTE_IMPORTE, widthPrinter));
        line = UtilPrinter.alignTextRight(line, sImporteMoneda, UtilPrinter.getSecondSeparationTipoCot(widthPrinter));
        line = UtilPrinter.alignTextRight(line, sImporte, widthPrinter);
        ps.println(line);
        ps.println();
    }


    private void printCabeceraEfectivo(BasePrintStream ps, Recibo recibo) {
        ps.println("Recibos en pesos a rendir");
        //ps.println(UtilPrinter.getCabecera(UtilPrinter.CABECERA_TIPO_COT_IMPORTE_IMPORTE, widthPrinter, snReciboMovilDto));
        ps.println();
    }

    private void printTotalEfectivo(BasePrintStream ps, double total) {
        String sTotal = "TOTAL EN EFECTIVO: $" + Formatter.formatNumber(total, "#,##0.00");
        ps.println(UtilPrinter.alignTextRight(sTotal, widthPrinter));
        ps.printLine();
    }

    private void entregaCheque(BasePrintStream ps, Recibo recibo) {
        for (Cheque cheque : recibo.getEntrega().getCheques()) {
            String banco = cheque.getBanco().getDenominacion();
            long numeroCheque = cheque.getId().getNumeroCheque();
            String sNumeroCheque = String.valueOf(numeroCheque);
            String fecha = UtilPrinter.formatDate(cheque.getFechaChequeMovil());
            double importe = cheque.getImporte();
            String sImporte = Formatter.formatNumber(importe, "#,##0.00");
            // String line =UtilPrinter.armarLinea(banco, sNumeroCheque, 8);
            String line = UtilPrinter.armarLinea(banco, fecha,
                    UtilPrinter.getSeparationCabecera(UtilPrinter.CABECERA_BANCO_FECHA_IMPORTE, widthPrinter));
            // line = UtilPrinter.alignTextRight(fecha, line, 22);
            line = UtilPrinter.alignTextRight(line, sImporte, widthPrinter);
            ps.println(line);
            if (banco.length() > 12) {
                ps.println(banco.substring(12));
            }
            ps.println("Nro de cheque: " + sNumeroCheque);
        }
        ps.println();

    }

    private void printCabeceraCheques(BasePrintStream ps, Recibo recibo) {
        ps.println("Recibos en cheque");
        ps.println(UtilPrinter.getCabecera(UtilPrinter.CABECERA_BANCO_FECHA_IMPORTE, widthPrinter, snReciboMovilDto));
    }

    private void printTotalCheques(BasePrintStream ps, double total) {
        String sTotal = "TOTAL CHEQUES: $" + Formatter.formatNumber(total, "#,##0.00");
        ps.println(UtilPrinter.alignTextRight(sTotal, widthPrinter));
        ps.printLine();
    }

    private void entregaRetencion(BasePrintStream ps, Recibo recibo) {
        for (Retencion retencion : recibo.getEntrega().getRetenciones()) {
            String tipo = retencion.getPlanCuenta().getDescripcion();
            String fecha = UtilPrinter.formatDate(retencion.getFechaMovil());
            double importe = retencion.getImporte();
            String sImporte = Formatter.formatNumber(importe, "#,##0.00");
            String line = UtilPrinter.armarLinea(tipo, fecha,
                    UtilPrinter.getSeparationCabecera(UtilPrinter.CABECERA_TIPO_FECHA_IMPORTE, widthPrinter));
            line = UtilPrinter.alignTextRight(line, sImporte, widthPrinter);
            ps.println(line);
            int separacion = UtilPrinter.getSeparationCabecera(UtilPrinter.CABECERA_TIPO_FECHA_IMPORTE, widthPrinter);
            if (tipo.length() > separacion - 1) {
                ps.println(tipo.substring(separacion - 1));
            }
        }
    }

    private void printCabeceraRetenciones(BasePrintStream ps, Recibo recibo) {
        ps.println("Recibos en retenciones");
        ps.println(UtilPrinter.getCabecera(UtilPrinter.CABECERA_TIPO_FECHA_IMPORTE, widthPrinter, snReciboMovilDto));
    }

    private void printTotalRetenciones(BasePrintStream ps, double total) {
        String sTotal = "TOTAL RETENCIONES: $" + Formatter.formatNumber(total, "#,##0.00");
        ps.println(UtilPrinter.alignTextRight(sTotal, widthPrinter));
        ps.printLine();
    }

    private void entregaDepositos(BasePrintStream ps, Recibo recibo) {
        for (Deposito deposito : recibo.getEntrega().getDepositos()) {
            String banco = deposito.getBanco().getDenominacion();
            String fecha = UtilPrinter.formatDate(deposito.getFechaDepositoMovil());
            double importe = deposito.getImporte();
            String sImporte = Formatter.formatNumber(importe, "#,##0.00");
            String line = UtilPrinter.armarLinea(banco, fecha,
                    UtilPrinter.getSeparationCabecera(UtilPrinter.CABECERA_BANCO_FECHA_IMPORTE, widthPrinter));
            line = UtilPrinter.alignTextRight(line, sImporte, widthPrinter);
            ps.println(line);
            int separacion = UtilPrinter.getSeparationCabecera(UtilPrinter.CABECERA_TIPO_FECHA_IMPORTE, widthPrinter);
            if (banco.length() > separacion - 1) {
                ps.println(banco.substring(separacion - 1));
            }
            ps.println("Operacion: " + deposito.getNumeroComprobante());
            ps.println("Cant. cheques:" + deposito.getCantidadCheques());

        }
        ps.println();
    }

    private void printCabeceraDepositos(BasePrintStream ps, Recibo recibo) {
        ps.println("Recibos en deposito");
        ps.println(UtilPrinter.getCabecera(UtilPrinter.CABECERA_BANCO_FECHA_IMPORTE, widthPrinter, snReciboMovilDto));
    }

    private void printTotalDepositos(BasePrintStream ps, double total) {
        String sTotal = "TOTAL DEPOSITOS: $" + Formatter.formatNumber(total, "#,##0.00");
        ps.println(UtilPrinter.alignTextRight(sTotal, widthPrinter));
        ps.printLine();
    }

}
