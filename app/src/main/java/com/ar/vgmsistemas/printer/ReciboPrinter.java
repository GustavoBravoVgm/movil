package com.ar.vgmsistemas.printer;

import android.content.Context;

import com.ar.vgmsistemas.entity.Cheque;
import com.ar.vgmsistemas.entity.CuentaCorriente;
import com.ar.vgmsistemas.entity.Deposito;
import com.ar.vgmsistemas.entity.PagoEfectivo;
import com.ar.vgmsistemas.entity.Recibo;
import com.ar.vgmsistemas.entity.ReciboDetalle;
import com.ar.vgmsistemas.entity.Retencion;
import com.ar.vgmsistemas.printer.util.UtilPrinter;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.Matematica;

import java.util.List;

//import org.apache.commons.net.io.Util;

public class ReciboPrinter extends /*Printer<Recibo>*/SuperPrinter {
    //private Empresa empresaDto;
    //private String empresa;
    //private String printerName;
    //private int widthPrinter;
    //private boolean snReciboMovilDto;

    public void print(Recibo recibo) throws Exception {
        if (recibo.getTipoImpresionRecibo() == 0) {
            return;
        }
        super.print(_context);

        for (int i = 0; i < 2; i++) {
            cabecera(ps, recibo);

            comprobante(ps, recibo);
            if (recibo.getEntrega().getEntregasEfectivo().size() > 0) {
                entregaMoneda(ps, recibo);
            }
            if (recibo.getEntrega().getCheques().size() > 0) {
                entregaCheque(ps, recibo);
            }
            if (recibo.getEntrega().getRetenciones().size() > 0) {
                entregaRetencion(ps, recibo);
            }
            if (recibo.getEntrega().getDepositos().size() > 0) {
                entregaDepositos(ps, recibo);
            }

            double total = recibo.getTotal();
            String sTotal = "TOTAL RECIBO: $" + Formatter.formatNumber(total, "#,##0.00");
            ps.println(UtilPrinter.alignTextRight(sTotal, widthPrinter));
            /*ps.println();
            ps.println();
            ps.println(); se comenta para ahorrar hoja*/
            ps.printLine();
            if (recibo.getTipoImpresionRecibo() != 2) {
                String empresaString = "Por EMPRESA " + empresa;
                List<String> listPie = dividirString(empresaString);
                //String pie = UtilPrinter.center(empresaString, widthPrinter);
                //si el tipo de impresion de recibo es = 2 entonces no imprimir el nombre de la empresa
                for (String renglon : listPie) {
                    ps.println(renglon);
                }
            }
            ps.println();
            ps.println();
        }
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
        String comprobante = UtilPrinter.center("RECIBO", widthPrinter);
        String letra = UtilPrinter.center("X", widthPrinter);
        String noValidoComoFactura = UtilPrinter.center("Documento no valido como factura", widthPrinter);
        //si el tipo de impresion de recibo es = 2 entonces no imprimir el nombre de la empresa
        if (recibo.getTipoImpresionRecibo() != 2) {
            for (String renglon : listRengNombreEmpresa) {
                ps.println(renglon);
            }
        }
        ps.println(comprobante);
        ps.println(letra);
        ps.println(noValidoComoFactura);
        ps.println();
        /*
         * String fecha = Formatter.formatDate(recibo.getFechaMovil());
         * ps.append("Fecha: ").append(fecha); ps.printlnCustom(widthPrinter);
         */
        String fechaRegistro = Formatter.formatDateTimeMinutes(recibo.getFechaRegistroMovil());
        ps.println("Fecha: " + fechaRegistro);

        /*ps.println(); se comenta para ahorrar hoja*/
        String puntoVenta = ("PtoVta: ").concat(String.valueOf(recibo.getId().getIdPuntoVenta()));
        String numero = ("Numero:").concat(String.valueOf(recibo.getId().getIdRecibo()));
        // String lineaIdRecibo = UtilPrinter.alignTextRight(numero, puntoVenta,
        // widthPrinter);
        //ps.println(lineaIdRecibo);
        ps.println(puntoVenta);
        ps.println(numero);
        String idCliente = recibo.getCliente().getId().toString();
        // ps.append("Cliente: ").append("1223");
        ps.println("Cliente: " + idCliente);
        /*ps.println();se comenta para ahorrar hoja*/
        ps.println(recibo.getCliente().getRazonSocial());
        ps.println();
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
            /*ps.println(); para ahorrar hoja*/
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
            /*ps.println(); se comenta para ahorrar hoja*/
            double totalComprobantesGenerados = recibo.getTotalDocumentosGenerados();
            String sTotalDocumentosGenerados = "TOTAL ANTICIPO: $"
                    + Formatter.formatNumber(totalComprobantesGenerados, "#,##0.00");
            ps.println(UtilPrinter.alignTextRight(sTotalDocumentosGenerados, widthPrinter));
        }

        /*ps.println(); se comenta para ahorrar hoja*/
        // double totalComprobante = recibo.getTotal();
        // String sTotal = "TOTAL COMPROBANTE: $" +
        // Formatter.formatNumber(totalComprobante, "#,##0.00");
        // ps.println(UtilPrinter.alignTextRight(sTotal, "", widthPrinter));

        ps.printLine();
    }

    private void entregaMoneda(BasePrintStream ps, Recibo recibo) {
        ps.println("Recibo en pesos");
        ps.println(UtilPrinter.getCabecera(UtilPrinter.CABECERA_TIPO_COT_IMPORTE_IMPORTE, widthPrinter, snReciboMovilDto));
        String line;

        for (PagoEfectivo pagoEfectivo : recibo.getEntrega().getEntregasEfectivo()) {
            String tipo = pagoEfectivo.getTipoMoneda().getDescripcion();

            double importe = pagoEfectivo.getImporteMonedaCorriente();
            String sImporte = Formatter.formatNumber(importe, "#,##0.00");

            double importeMoneda = pagoEfectivo.getImporteMoneda();
            String sImporteMoneda = Formatter.formatNumber(importeMoneda, "#,##0.00");

            if (!pagoEfectivo.getTipoMoneda().isMonedaCurso()) {
                double cotizacion = pagoEfectivo.getCotizacion();
                // String sCotizacion =
                // UtilPrinter.alignTextRight(String.valueOf(cotizacion), "",
                // 11);

                String sCotizacion = String.valueOf(cotizacion);

                line = UtilPrinter.armarLinea(tipo, sCotizacion,
                        UtilPrinter.getSeparationCabecera(UtilPrinter.CABECERA_TIPO_COT_IMPORTE_IMPORTE, widthPrinter));
            } else {
                line = tipo;
            }
            line = UtilPrinter.alignTextRight(line, sImporteMoneda, UtilPrinter.getSecondSeparationTipoCot(widthPrinter));
            line = UtilPrinter.alignTextRight(line, sImporte, widthPrinter);
            ps.println(line);
        }
        /*ps.println(); para ahorrar hoja*/
        double total = recibo.getEntrega().calcularTotalPagosEfectivo();
        String stotal = "TOTAL EFECTIVO: $" + Formatter.formatNumber(total, "#,##0.00");
        ps.println(UtilPrinter.alignTextRight(stotal, widthPrinter));
        ps.printLine();
    }

    private void entregaCheque(BasePrintStream ps, Recibo recibo) {
        ps.println("Recibo en cheque");
        ps.println(UtilPrinter.getCabecera(UtilPrinter.CABECERA_BANCO_FECHA_IMPORTE, widthPrinter, snReciboMovilDto));
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
        /*ps.println(); para ahorrar hoja*/
        double total = recibo.getEntrega().calcularTotalEntregasCheque();
        String stotal = "TOTAL CHEQUE: $" + Formatter.formatNumber(total, "#,##0.00");
        ps.println(UtilPrinter.alignTextRight(stotal, widthPrinter));
        ps.printLine();
    }

    private void entregaRetencion(BasePrintStream ps, Recibo recibo) {
        ps.println("Recibo en retencion");
        ps.println(UtilPrinter.getCabecera(UtilPrinter.CABECERA_TIPO_FECHA_IMPORTE, widthPrinter, snReciboMovilDto));
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
        double total = recibo.getEntrega().calcularTotalRetenciones();
        String stotal = "TOTAL RETENCION: $" + Formatter.formatNumber(total, "#,##0.00");
        ps.println(UtilPrinter.alignTextRight(stotal, widthPrinter));
        ps.printLine();
    }

    private void entregaDepositos(BasePrintStream ps, Recibo recibo) {
        ps.println("Recibo en deposito");
        ps.println(UtilPrinter.getCabecera(UtilPrinter.CABECERA_BANCO_FECHA_IMPORTE, widthPrinter, snReciboMovilDto));
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
        /*ps.println(); para ahorrar hoja*/
        double total = recibo.getEntrega().calcularTotalEntregasDeposito();
        String stotal = "TOTAL DEPOSITO: $" + Formatter.formatNumber(total, "#,##0.00");
        ps.println(UtilPrinter.alignTextRight(stotal, widthPrinter));
        ps.printLine();
    }

}
