package com.ar.vgmsistemas.printer;

import android.content.Context;

import com.ar.vgmsistemas.bo.DocumentoBo;
import com.ar.vgmsistemas.bo.VentaBo;
import com.ar.vgmsistemas.entity.Documento;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.entity.key.PkDocumento;
import com.ar.vgmsistemas.printer.util.UtilPrinter;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.Formatter;

import java.util.List;

/**
 * Created by Ema on 21/10/2016.
 */
public class PedidoPrinter extends SuperPrinter {
    public void print(Venta venta, Context context) throws Exception {
        super.print(context);
        //para imprimir se comenta super.print();, se descomenta lo siguiente y se cambia ps por ps
        /*
        widthPrinter = 32;
        EmpresaBo empresaBo = new EmpresaBo();
        empresaDto = empresaBo.recoveryEmpresa();
        empresa = empresaDto.getNombreEmpresa().trim();*/

        cabecera(venta);

        RepositoryFactory repo = RepositoryFactory.getRepositoryFactory(context, RepositoryFactory.ROOM);
        DocumentoBo documentoBo = new DocumentoBo(repo);
        PkDocumento pkDocumento = new PkDocumento();
        pkDocumento.setIdDocumento(venta.getId().getIdDocumento());
        pkDocumento.setIdLetra(venta.getId().getIdLetra());
        pkDocumento.setPuntoVenta(venta.getId().getPuntoVenta());
        Documento documento = null;
        try {
            documento = documentoBo.recoveryById(pkDocumento);
        } catch (Exception e) {
            e.printStackTrace();
        }

        comprobante(venta, documento);
        pie(venta, documento);
        Thread.sleep(5000);
        /*para prueba comentar desde aca*/
        ps.flush();
        os.flush();

        ps.close();
        os.close();
        socket.close();/*para prueba comentar hasta aca*/
    }

    private void cabecera(Venta venta) {
        //System.out.println();
        ps.println();

        List<String> listRengNombreEmpresa = dividirString(empresa);
        for (String renglon : listRengNombreEmpresa) {
            //System.out.println(UtilPrinter.center(renglon, widthPrinter));
            ps.println(UtilPrinter.center(renglon, widthPrinter));
        }
        String noValidoComoFactura = "Documento no valido como factura";
        //System.out.println(noValidoComoFactura);
        ps.println(noValidoComoFactura);
        String fechaRegistro = Formatter.formatDateTimeMinutes(venta.getFechaRegistro());
        //System.out.println("Fecha: " + fechaRegistro);
        ps.println("Fecha: " + fechaRegistro);
        //System.out.println();
        ps.println();
        //System.out.println("Comprobante: " + venta.getId().toString());
        ps.println("Comprobante: " + venta.getId().toString());
        //System.out.println("Cliente: " + venta.getCliente().getId().toString());
        ps.println("Cliente: " + venta.getCliente().getId().toString());
        //System.out.println();
        ps.println();
    }

    private String generarLinea() {
        String linea = "";
        while (linea.length() < widthPrinter) {
            linea = linea + "_";
        }
        return linea;
    }

    private void comprobante(Venta venta, Documento documento) {
        //System.out.println("DESCRIPCION");
        ps.println("DESCRIPCION");
        //System.out.println(UtilPrinter.getCabecera(UtilPrinter.CABECERA_UNIDADES_PRUNITARIO_SUBTOTAL, widthPrinter, snReciboMovilDto));
        ps.println(UtilPrinter.getCabecera(UtilPrinter.CABECERA_UNIDADES_PRUNITARIO_SUBTOTAL, widthPrinter, snReciboMovilDto));

        for (VentaDetalle v : venta.getDetalles()) {
            if (!v.getIdCombo().equals("")) {
                v.getArticulo().setDescripcion("(*) " + v.getArticulo().getDescripcion());
            }
            imprimirDetalle(v);
        }
        //System.out.println();
        ps.println();
    }

    private void imprimirDetalle(VentaDetalle v) {
        String descArticulo = v.getArticulo().getDescripcion();
        if (empresaDto.getSnImprimirIdEmpresa() != null && empresaDto.getSnImprimirIdEmpresa().equals("S")) {
            descArticulo = v.getArticulo().getCodigoEmpresa() + " " + v.getArticulo().getDescripcion();
        }
        if (descArticulo.length() > widthPrinter)
            descArticulo = descArticulo.substring(0, widthPrinter);
        descArticulo = reemplazarCaracteresEspeciales(descArticulo);
        //System.out.println(descArticulo);
        ps.println(descArticulo);
        /*INICIO: antes de la tarea #49769*/
        //String line = UtilPrinter.armarLinea(v.getCantidad()+"",
        //        Formatter.formatMoneyTwo(v.getPrecioUnitarioSinDescuento()),
        //        UtilPrinter.getSeparationCabecera(UtilPrinter.CABECERA_UNIDADES_PRUNITARIO_SUBTOTAL, widthPrinter));
        //line = UtilPrinter.alignTextRight(line, Formatter.formatMoneyTwo(v.getImporteTotal()), widthPrinter);
        /*FIN:antes de la tarea #49769*/
        double cantPedido = v.getCantidad();
        double prUnitarioXArticulo = v.getPrecioUnitarioSinIva() + v.getPrecioIvaUnitario() + v.getPrecioImpuestoInterno();
        double prTotalXArticulo = v.getCantidad() * (v.getPrecioUnitarioSinIva() + v.getPrecioIvaUnitario() + v.getPrecioImpuestoInterno());

        String line = UtilPrinter.armarLinea(cantPedido + "",
                Formatter.formatMoneyTwo(prUnitarioXArticulo),
                UtilPrinter.getSeparationCabecera(UtilPrinter.CABECERA_UNIDADES_PRUNITARIO_SUBTOTAL, widthPrinter));
        line = UtilPrinter.alignTextRight(line, Formatter.formatMoneyTwo(prTotalXArticulo), widthPrinter);
        //System.out.println(line);
        ps.println(line);
    }

    private static final String ORIGINAL
            = "ÁáÉéÍíÓóÚúÑñÜü";
    private static final String REPLACEMENT
            = "AaEeIiOoUuNnUu";

    private String reemplazarCaracteresEspeciales(String str) {
        if (str == null) {
            return null;
        }
        char[] array = str.toCharArray();
        for (int index = 0; index < array.length; index++) {
            int pos = ORIGINAL.indexOf(array[index]);
            if (pos > -1) {
                array[index] = REPLACEMENT.charAt(pos);
            }
        }
        return new String(array);
    }

    private void pie(Venta venta, Documento documento) {
        /*DocumentoBo documentoBo = new DocumentoBo();
        PkDocumento pkDocumento = new PkDocumento();
        pkDocumento.setIdDocumento(venta.getId().getIdDocumento());
        pkDocumento.setIdLetra(venta.getId().getIdLetra());
        pkDocumento.setPuntoVenta(venta.getId().getPuntoVenta());
        Documento documento= null;
        try {
            documento = documentoBo.recoveryById(pkDocumento);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        if (documento.getSnImprimirImpMovil().equals("S")) {
            double descuentoLineas = VentaBo.obtenerTotalDescuentosEnVenta(venta);
            String lineDtoLine = UtilPrinter.alignTextRight("", "Dto.Lineas: " + rellenar(Formatter.formatMoneyTwo(descuentoLineas)), widthPrinter);
            //System.out.println(lineDtoLine);
            ps.println(lineDtoLine);

            double totalPorArticulo = 0f;
            for (VentaDetalle vd : venta.getDetalles()) {
                totalPorArticulo += (vd.getImporteTotal());
            }
            String lineSubtotal = UtilPrinter.alignTextRight("", "Subt s/imp: " + rellenar(Formatter.formatMoneyTwo(totalPorArticulo)), widthPrinter);
            //System.out.println(lineSubtotal);
            ps.println(lineSubtotal);

            double descuentoPedido = totalPorArticulo * venta.getTasaDescuentoCondicionVenta();
            String lineDtoPedido = UtilPrinter.alignTextRight("", "Dto.Pedido: " + rellenar(Formatter.formatMoneyTwo(descuentoPedido)), widthPrinter);
            //System.out.println(lineDtoPedido);
            ps.println(lineDtoPedido);

            double totalImpuestos = VentaBo.obtenerTotalImpuestos(venta);
            String lineImpuestos = UtilPrinter.alignTextRight("", "Impuestos: " + rellenar(Formatter.formatMoneyTwo(totalImpuestos)), widthPrinter);
            //System.out.println(lineImpuestos);
            ps.println(lineImpuestos);
        }
        String lineTotal = UtilPrinter.alignTextRight("", "Total: " + rellenar(Formatter.formatMoneyTwo(venta.getTotal())), widthPrinter);
        //System.out.println(lineTotal);
        ps.println(lineTotal);

        //System.out.println();//System.out.println();
        ps.println();
        ps.println();
        //System.out.println(generarLinea());
        ps.println(generarLinea());
        String firma = "  Firma";
        String aclaracion = "Aclaracion  ";
        String line = UtilPrinter.alignTextRight(firma, aclaracion, widthPrinter);
        //System.out.println(line);
        ps.println(line);
        //System.out.println();//System.out.println();
        ps.println();
        ps.println();
    }

    private String rellenar(String valor) {
        while (valor.length() < 12) {
            valor = " " + valor;
        }
        return valor;
    }
}
