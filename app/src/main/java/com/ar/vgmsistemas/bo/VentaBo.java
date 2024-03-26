package com.ar.vgmsistemas.bo;

import static com.ar.vgmsistemas.entity.AccionesCom.TIPO_ACCION_ARTICULO;
import static com.ar.vgmsistemas.entity.AccionesCom.TI_ORIGEN_CONJUNTA;
import static com.ar.vgmsistemas.entity.AccionesCom.TI_ORIGEN_EMPRESA;
import static com.ar.vgmsistemas.entity.AccionesCom.TI_ORIGEN_PROVEEDOR;

import android.content.Context;
import android.location.Location;

import com.ar.vgmsistemas.entity.AccionesCom;
import com.ar.vgmsistemas.entity.AccionesComDetalle;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.CategoriaFiscal;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.CondicionRenta;
import com.ar.vgmsistemas.entity.DescuentoProveedor;
import com.ar.vgmsistemas.entity.Documento;
import com.ar.vgmsistemas.entity.LineaIntegradoMercaderia;
import com.ar.vgmsistemas.entity.ListaPrecio;
import com.ar.vgmsistemas.entity.ListaPrecioDetalle;
import com.ar.vgmsistemas.entity.Movimiento;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.PromocionDetalle;
import com.ar.vgmsistemas.entity.PromocionRequisito;
import com.ar.vgmsistemas.entity.RangoRentabilidad;
import com.ar.vgmsistemas.entity.Repartidor;
import com.ar.vgmsistemas.entity.Sucursal;
import com.ar.vgmsistemas.entity.UbicacionGeografica;
import com.ar.vgmsistemas.entity.Vendedor;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.entity.key.PkDocumento;
import com.ar.vgmsistemas.entity.key.PkListaPrecioDetalle;
import com.ar.vgmsistemas.entity.key.PkVenta;
import com.ar.vgmsistemas.entity.key.PkVentaDetalle;
import com.ar.vgmsistemas.gps.GPSManagement;
import com.ar.vgmsistemas.helper.TipoOperacion;
import com.ar.vgmsistemas.repository.IAccionesComDetalleRepository;
import com.ar.vgmsistemas.repository.IArticuloRepository;
import com.ar.vgmsistemas.repository.ICodigoAutorizacionCobranzaRepository;
import com.ar.vgmsistemas.repository.IDocumentoRepository;
import com.ar.vgmsistemas.repository.IMovimientoRepository;
import com.ar.vgmsistemas.repository.INoPedidoRepository;
import com.ar.vgmsistemas.repository.IRangoRentabilidadRepository;
import com.ar.vgmsistemas.repository.IUbicacionGeograficaRepository;
import com.ar.vgmsistemas.repository.IVendedorRepository;
import com.ar.vgmsistemas.repository.IVentaDetalleRepository;
import com.ar.vgmsistemas.repository.IVentaRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.CategoriaRecursoHumano;
import com.ar.vgmsistemas.utils.CodeResult;
import com.ar.vgmsistemas.utils.ComparatorDateTime;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.Matematica;
import com.ar.vgmsistemas.utils.TipoEmpresaCode;
import com.ar.vgmsistemas.ws.UbicacionGeograficaWs;
import com.ar.vgmsistemas.ws.VentaWs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class VentaBo {

    private static final String TAG = VentaBo.class.getCanonicalName();
    public final static int ANULAR_VENTA_MODO_ENVIAR = 1;
    public final static int ANULAR_VENTA_MODO_NO_ENVIAR = 2;
    public static double prIvaIAux = 0d;
    public static double prIva21Aux = 0d;
    public static double prIva105Aux = 0d;

    public final static boolean NC_DEVOLUCION = true;
    private final static boolean FC_DEVOLUCION = false;

    //Repo
    private final IVentaRepository _ventaRepository;
    private final IVentaDetalleRepository _ventaDetalleRepository;
    private final IArticuloRepository _articuloRepository;
    private final IAccionesComDetalleRepository _accionesComDetalleRepository;
    private final IMovimientoRepository _movimientoRepository;
    private final IRangoRentabilidadRepository _rangoRentabilidadRepository;
    private final IUbicacionGeograficaRepository _ubicacionGeograficaRepository;
    private final ICodigoAutorizacionCobranzaRepository _codigoAutorizacionCobranzaRepository;
    private final IDocumentoRepository _documentoRepository;
    private final IVendedorRepository _vendedorRepository;
    private final INoPedidoRepository _noPedidoRepository;

    //BO's
    private static EmpresaBo _empresaBo;
    private ObjetivoVentaBo _objetivoVentaBo;
    private DocumentoBo _documentoBo;
    private static DescuentoProveedorBo _descuentoProveedorBo;


    //BD
    private static RepositoryFactory _repoFactory;

    public VentaBo(RepositoryFactory repoFactory) {
        _repoFactory = repoFactory;
        this._empresaBo = new EmpresaBo(_repoFactory);
        this._objetivoVentaBo = new ObjetivoVentaBo(_repoFactory);
        this._documentoBo = new DocumentoBo(_repoFactory);
        this._descuentoProveedorBo = new DescuentoProveedorBo(_repoFactory);

        this._ventaRepository = repoFactory.getVentaRepository();
        this._ventaDetalleRepository = repoFactory.getVentaDetalleRepository();
        this._articuloRepository = repoFactory.getArticuloRepository();
        this._accionesComDetalleRepository = repoFactory.getAccionesComDetalleRepository();
        this._movimientoRepository = repoFactory.getMovimientoRepository();
        this._rangoRentabilidadRepository = repoFactory.getRangoRentabilidadRepository();
        this._ubicacionGeograficaRepository = repoFactory.getUbicacionGeograficaRepository();
        this._codigoAutorizacionCobranzaRepository = repoFactory.getCodigoAutorizacionCobranzaRepository();
        this._documentoRepository = repoFactory.getDocumentoRepository();
        this._vendedorRepository = repoFactory.getVendedorRepository();
        this._noPedidoRepository = repoFactory.getNoPedidoRepository();

    }

    public static double obtenerTotalDescuentosEnVenta(Venta _venta) {
        double totalDescuentos = 0d;
        Iterator<VentaDetalle> iterator = _venta.getDetalles().iterator();
        while (iterator.hasNext()) {
            VentaDetalle ventaDetalle = iterator.next();
            totalDescuentos = totalDescuentos +
                    ventaDetalle.getPrecioUnitarioSinDescuentoProveedor() * ventaDetalle.getTasaDescuentoProveedor() * ventaDetalle.getCantidad() +
                    ventaDetalle.getPrecioUnitarioSinDescuentoCliente() * ventaDetalle.getTasaDescuentoCliente() * ventaDetalle.getCantidad() +
                    ventaDetalle.getPrecioUnitarioSinDescuento() * ventaDetalle.getTasaDescuento() * ventaDetalle.getCantidad();
        }
        return totalDescuentos;
    }

    public static double obtenerTotalImpuestos(Venta venta) {

        return venta.getTotalIva21() + venta.getTotalRenta()
                + venta.getTotalDirsc()
                + venta.getTotalImpuestoInterno()
                + venta.getTotalIvaNoCategorizado();
    }

    public static void actualizarTotales(Venta venta) throws Exception {
        actualizarTotales(venta, false);
    }

    public static void actualizarTotales(Venta venta, boolean snDevolucion) throws Exception {

        class CalculoTotal {
            double subtotalPorArticulo = 0;
            double subTotal = 0;
            double totalIva21 = 0;
            double totalIva105 = 0;
            double totalIvaNoCategorizado = 0;
            double totalExento = 0;
            double total = 0;
            double totalDescuentos = 0;
            double montoTotalArtCriticos = 0;
            double totalIvaNoCa = 0;
            double precioBonificacion = 0;

            public void calcular(Venta venta, VentaDetalle linea) {
                double cantidad = linea.getCantidad();
                double precioUnitarioSinIva = Formatter.redondearDouble(linea.getPrecioUnitarioSinIva(), 3);// linea.getPrecioUnitarioSinIva();
                double precioIVAUnitario = Formatter.redondearDouble(linea.getPrecioIvaUnitario(), 3); //linea.getPrecioIvaUnitario();
                double tasaIva = linea.getArticulo().getTasaIva();
                /**/
                double precioUnitarioSinDescuento = linea.getPrecioUnitarioSinDescuento();
                double precioSinDtoProv = linea.getPrecioUnitarioSinDescuentoProveedor();
                double precioSinDtoClie = linea.getPrecioUnitarioSinDescuentoCliente();


                double uniSinIva = ((linea.getPrecioUnitarioSinDescuentoProveedor() * (1 + linea.getTasaDescuentoProveedor()))
                        * (1 + linea.getTasaDescuentoCliente())) * (1 + linea.getTasaDescuento());

                linea.getPrecioUnitarioSinDescuento();
                double totalSinIVa = Math.round((uniSinIva * cantidad) * 1000) / 1000.0d;

                double descuentoDetalle = obtenerTotalDescuentosDetalle(linea);

                if (Double.compare(tasaIva, 0.21d) == 0)
                    totalIva21 += cantidad * precioIVAUnitario;
                else if (Double.compare(tasaIva, 0.105d) == 0)
                    totalIva105 += cantidad * precioIVAUnitario;
                else
                    totalExento += cantidad * precioUnitarioSinIva;

                //subTotal += linea.getImporteTotal();
                subtotalPorArticulo += linea.getImporteTotal();
                totalDescuentos += descuentoDetalle;
                if (linea.getTasaDescuento() > 0) {
                    venta.setTieneDescuento(true);
                }
                if (linea.getArticulo().isCritico()) {
                    montoTotalArtCriticos += (linea.getCantidad() * linea.getPrecioUnitarioSinIva());
                }
            }
        }

        List<VentaDetalle> detalle = venta.getDetalles();
        Iterator<VentaDetalle> iterator = detalle.iterator();
        CalculoTotal calculoTotal = new CalculoTotal();

        while (iterator.hasNext()) {
            VentaDetalle linea = iterator.next();
            if (linea.isCabeceraPromo() && linea.getListaPrecio().getTipoLista() == ListaPrecio.TIPO_LISTA_COMBOS_ESPECIALES) {
                for (VentaDetalle ventaDetalle : linea.getDetalleCombo()) {
                    calculoTotal.calcular(venta, ventaDetalle);
                }
            } else {
                calculoTotal.calcular(venta, linea);
            }
        }
        /*
         * El pr_subtotal en tabla ventas tiene que tener sumado el pr_imp_interno si es B
         * no asi si es A. Sin embargo, en el movil NO se le suma
         */

        calculoTotal.precioBonificacion = (venta.getTasaDescuento() * calculoTotal.subtotalPorArticulo);
        venta.setPrecioBonificacion(calculoTotal.precioBonificacion);
        double totalConDescuento = calculoTotal.subtotalPorArticulo - calculoTotal.precioBonificacion;

        double precioBonificacionCondicionVenta = totalConDescuento * venta.getTasaDescuentoCondicionVenta();
        venta.setPrecioBonificacionCondicionVenta(precioBonificacionCondicionVenta);
        calculoTotal.subTotal = totalConDescuento - precioBonificacionCondicionVenta;

        double totalIva21ConDescuento = calculoTotal.totalIva21 * (1 - venta.getTasaDescuentoCondicionVenta()) * (1 - venta.getTasaDescuento());
        //agrego el mismo tratamiento del iva21 para el 10,5
        double totalIva105ConDescuento = calculoTotal.totalIva105 * (1 - venta.getTasaDescuentoCondicionVenta()) * (1 - venta.getTasaDescuento());


        venta.setTotalDescuentosDetalle(calculoTotal.totalDescuentos);
        venta.setTotalPorArticulo(calculoTotal.subtotalPorArticulo);
        venta.setSubtotal(calculoTotal.subTotal);
        venta.setTotalExento(calculoTotal.totalExento);
        venta.setTotalIva21(totalIva21ConDescuento);
        venta.setTotalIva105(totalIva105ConDescuento);

        if (venta.getCliente().getCategoriaFiscal().getId().equals(CategoriaFiscal.NO_CATEGORIZADO)) {
            double tasaIvaNoCategorizado = _empresaBo.recoveryEmpresa().getTasaIvaNoCategorizado();
            calculoTotal.totalIvaNoCa = (venta.getSubtotal() + venta.getTotalIva21() + venta.getTotalIva105()) * tasaIvaNoCategorizado;
        }

        calculoTotal.totalIvaNoCategorizado = venta.getTotalIva105() + calculoTotal.totalIvaNoCa;
        venta.setTotalIvaNoCategorizado(calculoTotal.totalIvaNoCategorizado);
        calculoTotal.total = calculoTotal.subTotal + venta.getTotalIva21() + calculoTotal.totalIvaNoCategorizado;
        venta.setTotal(calculoTotal.total);
        venta.setMontoTotalArticulosCriticos(calculoTotal.montoTotalArtCriticos);
        VentaBo.calcularImpuestos(venta, snDevolucion);
    }

    /**
     * @param linea
     * @return la suma de los descuentos proveedor, cliente y del movil para la linea
     */
    private static double obtenerTotalDescuentosDetalle(VentaDetalle linea) {
        //formateo todos a 3 decimales como trabaja venta_detalle
        double prDtoProv = Formatter.redondearDouble(linea.getPrecioUnitarioSinDescuentoProveedor() * linea.getTasaDescuentoProveedor() * linea.getCantidad(), 3);
        double prDtoClie = Formatter.redondearDouble(linea.getPrecioUnitarioSinDescuentoCliente() * linea.getTasaDescuentoCliente() * linea.getCantidad(), 3);
        double prDto = Formatter.redondearDouble(linea.getPrecioUnitarioSinDescuento() * linea.getTasaDescuento() * linea.getCantidad(), 3);
        double totalDescuento = prDtoProv + prDtoClie + prDto;
        return totalDescuento;
    }

    private static void calcularImpuestos(Venta venta, boolean snDevolucion) throws Exception {
        DocumentoBo documentoBo = new DocumentoBo(_repoFactory);
        PkDocumento idDoc = new PkDocumento();
        idDoc.setIdDocumento(venta.getId().getIdDocumento());
        idDoc.setIdLetra(venta.getId().getIdLetra());
        idDoc.setPuntoVenta(venta.getId().getPuntoVenta());
        Documento documento = documentoBo.recoveryById(idDoc);
        venta.setDocumento(documento);
        if (venta.getDocumento() != null) {
            boolean isLegal = (venta.getDocumento().isLegal());
            boolean aplicaDgr = (venta.getDocumento().getSnAplicaDgr() == null || venta.getDocumento().getSnAplicaDgr().equals("S"));
            double renta = 0d;
            double totalImpuestoInterno = 0d;
            double precioDirsc = 0d;
            String letra = "B";
            totalImpuestoInterno = calcularImpuestoInterno(venta, FC_DEVOLUCION);
            if (isLegal) {
                letra = VentaBo.recoveryLetraComprobante(venta.getCliente());
                if (aplicaDgr) {
                    renta = calcularPrecioRenta(totalImpuestoInterno, venta.getCliente(), venta.getId().getIdLetra(), venta.getSubtotal(), venta.getTotalIva21(), venta.getTotalIva105(), snDevolucion, venta.getTotalRenta());
                }
                precioDirsc = calcularPrecioDirsc(venta.getSubtotal(), venta);
            }
            venta.getId().setIdLetra(letra);
            venta.setTotalRenta(renta);
            venta.setTotalImpuestoInterno(totalImpuestoInterno);
            venta.setTotalDirsc(precioDirsc);
            double total = venta.getTotal() + renta + precioDirsc + totalImpuestoInterno;
            venta.setTotal(total);
            venta.setTotalPorArticulo(venta.getTotalPorArticulo());
            venta.setSubtotal(venta.getSubtotal());

            venta.notifyObservers();
        }
    }

    public static double calcularPrecioDirsc(double subtotal, Venta venta) {
        double precioDirsc = 0d;
        Cliente cliente = venta.getCliente();
        if (cliente.getCondicionDirsc() != null) {
            if (!cliente.getCategoriaFiscal().getId().equals("CF")) {
                precioDirsc = subtotal * cliente.getCondicionDirsc().getTasaImpuesto();
            }
        }
        return precioDirsc;
    }

    public static double calcularImpuestoInterno(Venta venta, boolean snDevolucion) throws Exception {
        Integer tiMetodoImpInt = _empresaBo.recoveryEmpresa().getTiMetodoImpInt();
        double totalImpuestoInterno = 0d;
        List<VentaDetalle> detalles = venta.getDetalles();
        for (Iterator iterator = detalles.iterator(); iterator.hasNext(); ) {
            VentaDetalle ventaDetalle = (VentaDetalle) iterator.next();
            if (ventaDetalle.isCabeceraPromo()) {
                for (VentaDetalle vd : ventaDetalle.getDetalleCombo()) {
                    if (snDevolucion) {
                        // Metodo por porcentaje
                        if (tiMetodoImpInt == 1) {
                            totalImpuestoInterno = totalImpuestoInterno + (vd.getPrecioUnitarioSinIva()
                                    * vd.getCantidadDevuelta()
                                    * vd.getTasaImpuestoInterno());
                            // Metodo por valor
                            // tiMetodoImpInt = 2
                        } else {
                            totalImpuestoInterno = totalImpuestoInterno + (vd.getPrecioImpuestoInterno()
                                    * vd.getCantidadDevuelta());
                        }

                    } else {
                        // Metodo por porcentaje
                        if (tiMetodoImpInt == 1) {
                            totalImpuestoInterno = totalImpuestoInterno +
                                    (vd.getPrecioUnitarioSinIva()
                                            * vd.getCantidad()
                                            * vd.getArticulo().getTasaImpuestoInterno());
                            // Metodo por valor
                            // tiMetodoImpInt = 2
                        } else {
                            totalImpuestoInterno = totalImpuestoInterno +
                                    (vd.getPrecioImpuestoInterno() * vd.getCantidad());
                        }
                    }
                }
            }
            //#8372 - Modificacion calculo impuesto interno
            if (snDevolucion) {
                // Metodo por porcentaje
                if (tiMetodoImpInt == 1) {
                    totalImpuestoInterno = totalImpuestoInterno +
                            (ventaDetalle.getPrecioUnitarioSinIva()
                                    * ventaDetalle.getCantidadDevuelta()
                                    * ventaDetalle.getTasaImpuestoInterno());
                    // Metodo por valor
                    // tiMetodoImpInt = 2
                } else {
                    totalImpuestoInterno = totalImpuestoInterno +
                            (ventaDetalle.getPrecioImpuestoInterno()
                                    * ventaDetalle.getCantidadDevuelta());
                }
            } else {
                // Metodo por porcentaje
                if (tiMetodoImpInt == 1) {
                    totalImpuestoInterno = totalImpuestoInterno +
                            (ventaDetalle.getPrecioUnitarioSinIva()
                                    * ventaDetalle.getCantidad()
                                    * ventaDetalle.getArticulo().getTasaImpuestoInterno());
                    // Metodo por valor
                    // tiMetodoImpInt = 2
                } else {
                    totalImpuestoInterno = totalImpuestoInterno +
                            (ventaDetalle.getPrecioImpuestoInterno()
                                    * ventaDetalle.getCantidad());
                }
            }
        }
        double totalImpuestoInternoConDtos = totalImpuestoInterno * (1 - venta.getTasaDescuento()) * (1 - venta.getTasaDescuentoCondicionVenta());
        return totalImpuestoInternoConDtos;
    }

    public static double calcularPrecioRenta(double precioImpuestoInterno, Cliente cliente, String letra, double subtotal, double totalIva21, double totalIva105, boolean snDevolucion, Double totalRentaVentaOriginal) {
        double precioRenta = 0d;

        CondicionRenta condicionRenta = cliente.getCondicionRenta();
        if (condicionRenta != null && (condicionRenta.getSnAplicaANc() == null || condicionRenta.getSnAplicaANc().equals("N")) && snDevolucion == true) {
            precioRenta = 0;
        } else if (condicionRenta != null) {
            if (condicionRenta.getTipoCalculo() != null) {
                //CORRIENTES
                if (condicionRenta.getTipoCalculo() == CondicionRenta.TIPO_CALCULO_CORRIENTES) {
                    if (letra.equals("A") ||
                            (letra.equals("B") && !cliente.getCategoriaFiscal().getId().equals("CF"))) {
                        precioRenta = (subtotal + totalIva21 + totalIva105 + precioImpuestoInterno) * cliente.getCondicionRenta().getTasaDgr();
                    }
                    // Si estoy grabando una Nota de Crédito y lo hago en base a una factura
                    // Si la factura no tiene importe en DGR en la Nota de Crédito tampoco debe tener
                    if (snDevolucion && totalRentaVentaOriginal == 0d) {
                        //por #49140
                        if (condicionRenta.getSnAplicaANc().equals("N") || condicionRenta.getSnAplicaANc() == null) {
                            precioRenta = 0d;
                        }
                    } else {//por #49140
                        if (snDevolucion && (condicionRenta.getSnAplicaANc().equals("N") || condicionRenta.getSnAplicaANc() == null)) {
                            precioRenta = 0d;
                        }
                    }

                }//CHACO
                else if (condicionRenta.getTipoCalculo() == CondicionRenta.TIPO_CALCULO_CHACO ||
                        condicionRenta.getTipoCalculo() == CondicionRenta.TIPO_CALCULO_POR_ARTICULO) {
                    if (letra.equals("A")) {
                        precioRenta = (subtotal + precioImpuestoInterno) * cliente.getCondicionRenta().getTasaDgr();
                    } else if (letra.equals("B") && !cliente.getCategoriaFiscal().getId().equals("CF")) {
                        precioRenta = (subtotal + totalIva21 + totalIva105 + precioImpuestoInterno) * cliente.getCondicionRenta().getTasaDgr();
                    }
                    // Si estoy grabando una Nota de Crédito y lo hago en base a una factura
                    // Si la factura no tiene importe en DGR en la Nota de Crédito tampoco debe tener
                    if (snDevolucion && totalRentaVentaOriginal == 0d) {
                        //por #49140
                        if (condicionRenta.getSnAplicaANc().equals("N") || condicionRenta.getSnAplicaANc() == null) {
                            precioRenta = 0d;
                        }
                    } else {//por #49140
                        if (snDevolucion && (condicionRenta.getSnAplicaANc().equals("N") || condicionRenta.getSnAplicaANc() == null)) {
                            precioRenta = 0d;
                        }
                    }
                }//MISIONES o FORMOSA
                else if ((condicionRenta.getTipoCalculo() == CondicionRenta.TIPO_CALCULO_MISIONES) ||
                        (condicionRenta.getTipoCalculo() == CondicionRenta.TIPO_CALCULO_FORMOSA)) {
                    if (letra.equals("A") || (letra.equals("B") && !cliente.getCategoriaFiscal().getId().equals("CF"))) {
                        precioRenta = calcularRentaTipoCalculoTres(subtotal, cliente, precioImpuestoInterno);
                        //precioRenta = (venta.getSubtotal() + precioImpuestoInterno) * cliente.getCondicionRenta().getTasaDgr();
                    }
                    // Si estoy grabando una Nota de Crédito y lo hago en base a una factura
                    // Si la factura no tiene importe en DGR en la Nota de Crédito tampoco debe tener
                    if (snDevolucion && totalRentaVentaOriginal == 0d) {
                        //por #49140
                        if (condicionRenta.getSnAplicaANc().equals("N") || condicionRenta.getSnAplicaANc() == null) {
                            precioRenta = 0d;
                        }
                    } else {//por #49140
                        if (snDevolucion && (condicionRenta.getSnAplicaANc().equals("N") || condicionRenta.getSnAplicaANc() == null)) {
                            precioRenta = 0d;
                        }
                    }
                    // En FORMOSA los créditos no calculan DGR.
                    if (condicionRenta.getTipoCalculo() == CondicionRenta.TIPO_CALCULO_FORMOSA) {
                        //por #49140
                        if (snDevolucion && (condicionRenta.getSnAplicaANc().equals("N") || condicionRenta.getSnAplicaANc() == null)) {
                            precioRenta = 0d;
                        }
                    }
                }
                /*#55006 si el pr_dgr calculado es menor o igual al monto minimo a retener, le asigno 0 al dgr, para IN y MT*/
                if (cliente.getCategoriaFiscal().getId().equalsIgnoreCase("IN") ||
                        cliente.getCategoriaFiscal().getId().equalsIgnoreCase("MT")) {
                    if (cliente.getCondicionRenta().getMontoMinimoARetener() != null &&
                            (precioRenta / 100.0d) <= cliente.getCondicionRenta().getMontoMinimoARetener()) { // divido por 100 xq eso devuelve
                        precioRenta = 0d;//<--pongo en 0 xq no paso el monto minimo a retener
                    }
                }
            }
        }
        return precioRenta / 100.0d;
    }

    private static double calcularRentaTipoCalculoTres(double subtotal, Cliente cliente, double precioImpuestoInterno) {
        double precioRenta = 0d;

        double montoMinimo = cliente.getCondicionRenta().getMontoMinimoImpuestoDgr();
        if (montoMinimo > 0d) {
            double montoVenta = cliente.getTotalVentaAcumulada();
            if ((montoVenta + subtotal + precioImpuestoInterno) >= montoMinimo) {
                if (montoVenta >= montoMinimo) {
                    // Supero el minimo y no es la primera vez en el mes
                    precioRenta = (subtotal + precioImpuestoInterno) * cliente.getCondicionRenta().getTasaDgr();
                } else {
                    // Supero el minimo por primera vez en el mes
                    precioRenta = (montoVenta + subtotal + precioImpuestoInterno - montoMinimo) * cliente.getCondicionRenta().getTasaDgr();
                }
            }
        } else { // No existe minimo
            precioRenta = (subtotal + precioImpuestoInterno) * cliente.getCondicionRenta().getTasaDgr();
        }
        return precioRenta;
    }

    public static String recoveryLetraComprobante(Cliente cliente) {
        Date fechaEnMovil = Calendar.getInstance().getTime();
        CategoriaFiscal categoriaFiscalCliente = cliente.getCategoriaFiscal();
        String letra = "B";
        if (categoriaFiscalCliente.getId() != null) {
            if (categoriaFiscalCliente.getId().equals("IN")) {
                letra = "A";
            }
        }
        if (categoriaFiscalCliente.getFechaVigenciaLetra() != null && categoriaFiscalCliente.getTipoComprobanteLetra() != null) {
            if (fechaEnMovil != null && categoriaFiscalCliente.getFechaVigenciaLetra().compareTo(fechaEnMovil) <= 0) {
                letra = categoriaFiscalCliente.getTipoComprobanteLetra();
            }
        }

        return letra;
    }


    public static void setearPreciosDetalle(Cliente cliente, VentaDetalle ventaDetalle, ListaPrecioDetalle listaPrecioArticulo,
                                            Context context) throws Exception {
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(context, RepositoryFactory.ROOM);
        AccionesComDetalleBo accionesComDetalleBo = new AccionesComDetalleBo(repoFactory);
        AccionesComBo accionesComBo = new AccionesComBo(repoFactory);
        ListaPrecioDetalle listaPrecioDetalleCabeceraComboEspecial = null;

        AccionesCom accionesCom;
        AccionesComDetalle accionesComDetalleEmpresa;
        AccionesComDetalle accionesComDetalleProveedor;
        AccionesComDetalle accionesComDetalleConjunta;

        accionesComDetalleEmpresa = accionesComDetalleBo.getAccionesComDetalle(cliente, ventaDetalle, listaPrecioArticulo.getListaPrecio(), TI_ORIGEN_EMPRESA);
        accionesComDetalleProveedor = accionesComDetalleBo.getAccionesComDetalle(cliente, ventaDetalle, listaPrecioArticulo.getListaPrecio(), TI_ORIGEN_PROVEEDOR);
        accionesComDetalleConjunta = accionesComDetalleBo.getAccionesComDetalle(cliente, ventaDetalle, listaPrecioArticulo.getListaPrecio(), TI_ORIGEN_CONJUNTA);

        /*tratar seteo de dto por ti_requisito = 1 en combos especiales*/
        boolean esComboTipoDescuento = false;
        if (ventaDetalle.getIdPromoTemporal() > 0) {
            esComboTipoDescuento = PromocionBo.esComboEspecialTipoDescuento(ventaDetalle.getIdPromoTemporal(),
                    ventaDetalle.getListaPrecio(), context);
            if (esComboTipoDescuento) {
                PkListaPrecioDetalle pkLPD = new PkListaPrecioDetalle();
                pkLPD.setIdArticulo(ventaDetalle.getIdPromoTemporal());
                pkLPD.setIdLista(ventaDetalle.getListaPrecio().getId());
                pkLPD.setCaArticuloDesde(0);
                pkLPD.setCaArticuloHasta(0);
                ListaPrecioDetalleBo lpdBo = new ListaPrecioDetalleBo(_repoFactory);
                listaPrecioDetalleCabeceraComboEspecial = lpdBo.recoveryById(pkLPD);
            }
        }

        if (accionesComDetalleEmpresa != null) {
            accionesCom = accionesComBo.recoveryById(accionesComDetalleEmpresa.getId().getIdAccionesCom());
            tratarAcciones(accionesCom, accionesComDetalleEmpresa, ventaDetalle, cliente);
        } else {
            if (esComboTipoDescuento && listaPrecioDetalleCabeceraComboEspecial != null) {
                ventaDetalle.setTasaDescuentoCliente(listaPrecioDetalleCabeceraComboEspecial.getTasaDtoCliente());
            } else {
                ventaDetalle.setTasaDescuentoCliente(0d);
            }
        }
        if (accionesComDetalleProveedor != null) {
            accionesCom = accionesComBo.recoveryById(accionesComDetalleProveedor.getId().getIdAccionesCom());
            tratarAcciones(accionesCom, accionesComDetalleProveedor, ventaDetalle, cliente);
        } else {
            if (esComboTipoDescuento && listaPrecioDetalleCabeceraComboEspecial != null) {
                ventaDetalle.setTasaDescuentoProveedor(listaPrecioDetalleCabeceraComboEspecial.getTasaDtoProveedor());
            } else {
                ventaDetalle.setTasaDescuentoProveedor(0d);
            }
        }

        if (accionesComDetalleConjunta != null) {
            accionesCom = accionesComBo.recoveryById(accionesComDetalleConjunta.getId().getIdAccionesCom());
            tratarAcciones(accionesCom, accionesComDetalleConjunta, ventaDetalle, cliente);
        }

        if (ventaDetalle.getTasaDescuentoProveedor() == 0d) {
            DescuentoProveedor descuentoProveedor = _descuentoProveedorBo.getDescuentoProveedor(cliente, ventaDetalle.getArticulo(), listaPrecioArticulo.getListaPrecio());
            if (descuentoProveedor != null) {
                ventaDetalle.setTasaDescuentoProveedor(descuentoProveedor.getTasaDescuento());
                ventaDetalle.setIdDescuentoProveedor(descuentoProveedor.getId().getIdDescuentoProveedor());
            }
        }
        if (ventaDetalle.getTasaDescuentoCliente() == 0d) {
            if (listaPrecioArticulo.getListaPrecio().getTipoLista() == ListaPrecio.TIPO_LISTA_BASE
                    || listaPrecioArticulo.getListaPrecio().getTipoLista() == ListaPrecio.TIPO_LISTA_BASE_X_CANTIDAD) {
                ventaDetalle.setTasaDescuentoCliente(cliente.getTasaDescuentoCliente());
            } else {
                ventaDetalle.setTasaDescuentoCliente(0f);
            }
        }

        double precioUnitarioSinDescuentoProveedor;
        boolean isListaLibreDeHacienda = ventaDetalle.getListaPrecio().getTipoLista() == ListaPrecio.TIPO_LISTA_LIBRE
                && TipoEmpresaCode.isHacienda();

        if (isListaLibreDeHacienda) {

            precioUnitarioSinDescuentoProveedor = (ventaDetalle.getPrKiloUnitario()
                    * ventaDetalle.getCaArticulosKilos()
                    * (1 + ventaDetalle.getTasaDescuento())
                    / (1 + ventaDetalle
                    .getArticulo().getTasaIva()))
            ;
        } else {
            precioUnitarioSinDescuentoProveedor = listaPrecioArticulo.getPrecioSinIva();
        }
        ventaDetalle.setPrecioUnitarioSinDescuentoProveedor(precioUnitarioSinDescuentoProveedor);

        if (isListaLibreDeHacienda) {
            ventaDetalle.setPrecioUnitarioSinDescuentoCliente(precioUnitarioSinDescuentoProveedor);
        } else {
            ventaDetalle.setPrecioUnitarioSinDescuentoCliente(ventaDetalle.getPrecioUnitarioSinDescuentoCliente());
        }
        ventaDetalle.setPrecioUnitarioSinDescuento(ventaDetalle.getPrecioUnitarioSinDescuento());

    }

    private static void tratarAcciones(AccionesCom accionesCom, AccionesComDetalle accionesComDetalle, VentaDetalle ventaDetalle, Cliente cliente) throws Exception {
        Double taDto = 0d;
        Double taDtoEmpresa = 0d;
        if (accionesCom.getIdTipoAcciones() != null && accionesCom.getIdTipoAcciones().equals(TIPO_ACCION_ARTICULO)) {
            //TODO consultar cantidades en linea
            SucursalBo sucursalBo = new SucursalBo(_repoFactory);
            Sucursal sucursal = sucursalBo.recoveryById(cliente.getId().getIdSucursal());
            if (sucursal != null) {
                double caMaxima;
                if (accionesComDetalle.getCaMaxima() > 0) {
                    caMaxima = accionesComDetalle.getCaMaxima();
                } else {
                    caMaxima = Double.MAX_VALUE;
                }
                if ((caMaxima - accionesComDetalle.getCaVendida() >= ventaDetalle.getCantidad()) ||  //Si hay cantidad disponible
                        (sucursal.getTiControlAccom() != null && !sucursal.getTiControlAccom().equals(Sucursal.TI_CONTROL_SACA_DESCUENTO))) { //o si no hay pero es tipo CA o NR aplico descuento
                    if (ventaDetalle.getCantidad() != 0 && ventaDetalle.getCantidad() % ventaDetalle.getArticulo().getUnidadPorBulto() == 0 && accionesComDetalle.getTaDtoBcerrado() != 0) {
                        taDto = accionesComDetalle.getTaDtoBcerrado(); //Bultos cerrados
                    } else {
                        taDto = accionesComDetalle.getTaDto();//Bultos abiertos
                        taDtoEmpresa = accionesComDetalle.getTaDtoEmpresa();
                    }
                    if (accionesCom.getTiOrigen() != null && accionesCom.getTiOrigen().equals(TI_ORIGEN_CONJUNTA)) {
                        ventaDetalle.setIdAccionEmp(accionesComDetalle.getId().getIdAccionesCom());
                        ventaDetalle.setIdAccionesCom(accionesComDetalle.getId().getIdAccionesCom());
                    } else if (accionesCom.getTiOrigen() != null && accionesCom.getTiOrigen().equals(TI_ORIGEN_EMPRESA)) {
                        ventaDetalle.setIdAccionEmp(accionesComDetalle.getId().getIdAccionesCom());
                    } else {
                        ventaDetalle.setIdAccionesCom(accionesComDetalle.getId().getIdAccionesCom());
                    }
                }
            }
        } else {
            taDto = accionesComDetalle.getTaDto();
            taDtoEmpresa = accionesComDetalle.getTaDtoEmpresa();
            if (accionesCom.getTiOrigen() != null && accionesCom.getTiOrigen().equals(TI_ORIGEN_CONJUNTA)) {
                ventaDetalle.setIdAccionEmp(accionesComDetalle.getId().getIdAccionesCom());
                ventaDetalle.setIdAccionesCom(accionesComDetalle.getId().getIdAccionesCom());
            } else if (accionesCom.getTiOrigen() != null && accionesCom.getTiOrigen().equals(TI_ORIGEN_EMPRESA)) {
                ventaDetalle.setIdAccionEmp(accionesComDetalle.getId().getIdAccionesCom());
            } else {
                ventaDetalle.setIdAccionesCom(accionesComDetalle.getId().getIdAccionesCom());
            }
        }

        if (accionesCom != null && accionesCom.getTiOrigen() != null && accionesCom.getTiOrigen().equals(TI_ORIGEN_CONJUNTA)) {
            ventaDetalle.setTasaDescuentoProveedor(taDto);
            ventaDetalle.setTasaDescuentoCliente(taDtoEmpresa);
        } else if (accionesCom != null && accionesCom.getTiOrigen() != null && accionesCom.getTiOrigen().equals(TI_ORIGEN_EMPRESA)) {
            ventaDetalle.setTasaDescuentoCliente(taDto);
        } else {
            ventaDetalle.setTasaDescuentoProveedor(taDto);
        }
    }

    private static AccionesComDetalle obtenerAccionPorRango(List<AccionesComDetalle> accionesComDetalles, VentaDetalle vd) {
        for (AccionesComDetalle ac : accionesComDetalles) {
            //limite superior = 0 significa infinito
            if (ac.getRgLimiteInf() <= vd.getCantidad() && (ac.getRgLimiteSup() > vd.getCantidad() || ac.getRgLimiteSup() == 0)) {
                return ac;
            }
        }
        //Si no calza en ningún rango debe ser que no se están usando los rangos, pero se encontraron 2 acciones que podrían aplicar, devuelvo la primera
        if (accionesComDetalles.size() > 0)
            return accionesComDetalles.get(0);
        else return null;
    }

    public static double calcularPrecioUnitarioSinIva(VentaDetalle _linea, int idCategoria, int modo, int tipoLista) {
        double precioUnitario = 0d;
        //---------------------LISTA LIBRE
        if (tipoLista == ListaPrecio.TIPO_LISTA_LIBRE) {
            if (CategoriaRecursoHumano.isVendedorDeHacienda()) {
                precioUnitario = (_linea.getPrKiloUnitario()
                        * _linea.getArticulo().getCantidadKilos()
                        * (1 + _linea.getTasaDescuento())
                        / (1 + _linea
                        .getArticulo().getTasaIva()));

            } else if (CategoriaRecursoHumano.isRepartidorDeHacienda()) {
                precioUnitario = _linea.getPrKiloUnitario()
                        * _linea.getCaArticulosKilos()
                        * (1 + _linea.getTasaDescuento())
                        / (1 + _linea
                        .getArticulo().getTasaIva()) / _linea.getCantidad()
                ;

            }
            //-----------------------------OTRA LISTA
        } else {
            precioUnitario = _linea.getPrecioUnitarioSinIva();
        }


        return precioUnitario;
    }

    public static double calcularSubtotal(Venta venta, boolean snDevolucion) throws Exception {
        double prSubartSinIva = calcularSubartSinIva(venta, snDevolucion);
        double prBonif = calcularPrBonif(prSubartSinIva, venta.getTasaDescuento());
        double subtotal = prSubartSinIva - prBonif - calcularPrBonifCvta(prSubartSinIva, prBonif, venta.getTasaDescuentoCondicionVenta());
        if (venta.getId().getIdLetra().equals("B")) {
            double totalImpuestoInternoAux = VentaBo.calcularImpuestoInterno(venta, NC_DEVOLUCION);
            subtotal += calcularPrTotImpInterno(totalImpuestoInternoAux, venta.getTasaDescuento());
        }
        return subtotal;
    }

    public static double calcularSubartSinIva(Venta venta, boolean devolucion) {
        prIvaIAux = 0d;
        prIva21Aux = 0d;
        prIva105Aux = 0d;

        double prSubart = 0f;
        for (VentaDetalle vd : venta.getDetalles()) {

            if (vd.getDetalleCombo() != null && vd.getDetalleCombo().size() > 0) {
                for (VentaDetalle vdc : vd.getDetalleCombo()) {
                    double prUnitarioSinIva = Matematica.Round(vdc.getPrecioUnitarioSinIva(), 3);
                    double cantidad;
                    if (devolucion) {
                        cantidad = vdc.getCantidadDevuelta();
                    } else {
                        cantidad = vdc.getCantidad();
                    }
                    prIvaIAux += cantidad * vdc.getPrecioIvaUnitario();
                    if (Double.compare(vdc.getArticulo().getTasaIva(), 0.21d) == 0)
                        prIva21Aux += cantidad * vdc.getPrecioUnitarioSinIva() * vdc.getArticulo().getTasaIva();
                    else if (Double.compare(vdc.getArticulo().getTasaIva(), 0.105d) == 0)
                        prIva105Aux += cantidad * vdc.getPrecioIvaUnitario();
                    prSubart += prUnitarioSinIva * cantidad;
                }
            } else {

                double prUnitarioSinIva = Matematica.Round(vd.getPrecioUnitarioSinIva(), 3);
                double cantidad;
                if (devolucion) {
                    cantidad = vd.getCantidadDevuelta();
                } else {
                    cantidad = vd.getCantidad();
                }
                prIvaIAux += cantidad * vd.getPrecioIvaUnitario();
                if (Double.compare(vd.getArticulo().getTasaIva(), 0.21d) == 0)
                    prIva21Aux += cantidad * vd.getPrecioUnitarioSinIva() * vd.getArticulo().getTasaIva();
                else if (Double.compare(vd.getArticulo().getTasaIva(), 0.105d) == 0)
                    prIva105Aux += cantidad * vd.getPrecioIvaUnitario();
                prSubart += prUnitarioSinIva * cantidad;
            }
        }
        return prSubart;
    }

    public static double calcularTotal(Venta venta, boolean snDevolucion) throws Exception {
        double totalImpuestoInternoAux = Matematica.Round(VentaBo.calcularImpuestoInterno(venta, NC_DEVOLUCION), 2);
        double subtotal = Matematica.Round(calcularSubtotal(venta, snDevolucion), 2);
        double prIvaI = calcularPrIvaI(venta.getTasaDescuento(), venta.getTasaDescuentoCondicionVenta());
        prIvaI = Matematica.Round(prIvaI, 2);
        double prIva105 = Matematica.Round(calcularPrIva105(venta.getTasaDescuento(), venta.getTasaDescuentoCondicionVenta()), 2);
        double prIva21 = Matematica.Round(calcularPrIva21(venta.getTasaDescuento(), venta.getTasaDescuentoCondicionVenta()), 2);
        double prTotImpInterno = Matematica.Round(calcularPrTotImpInterno(totalImpuestoInternoAux, venta.getTasaDescuento()), 2);
        double rentaOriginal = venta.getTotalRenta();
        double renta = Matematica.Round(VentaBo.calcularPrecioRenta(prTotImpInterno, venta.getCliente(), venta.getId().getIdLetra(), subtotal, prIva21, prIva105, snDevolucion, rentaOriginal), 2);
        double precioDirsc = Matematica.Round(VentaBo.calcularPrecioDirsc(subtotal, venta), 2);
        double prIvaNoCategorizado = Matematica.Round(calcularIvaNoCategorizado(venta.getCliente().getCategoriaFiscal().getId(), subtotal, prIva21, prIva105), 2);
        if (venta.getId().getIdLetra().equals("A")) { //Si es A no se suma el imp interno al subtotal
            return Matematica.Round(subtotal + precioDirsc + renta + prIvaI + prTotImpInterno + prIvaNoCategorizado, 2);
        } else { //Si es B el imp interno está incluido en el subtotal
            return Matematica.Round(subtotal + precioDirsc + renta + prIvaI + prIvaNoCategorizado, 2);
        }
    }

    public static double calcularIvaNoCategorizado(String idCategoriaFiscal, double subtotal, double totalIva21, double totalIva105) throws Exception {
        double totalIvaNoCa = 0d;
        if (idCategoriaFiscal.equals(CategoriaFiscal.NO_CATEGORIZADO)) {
            double tasaIvaNoCategorizado = _empresaBo.recoveryEmpresa().getTasaIvaNoCategorizado();
            totalIvaNoCa = (subtotal + totalIva21 + totalIva105) * tasaIvaNoCategorizado;
        }
        return totalIvaNoCa += totalIva105;
    }

    public static double calcularPrBonif(double prSubartSinIva, double tasaDescuento) {
        return prSubartSinIva * tasaDescuento;
    }

    public static double calcularPrBonifCvta(double prSubartSinIva, double prBonif, double tasaDescuentoCondVta) {
        return (prSubartSinIva - prBonif) * tasaDescuentoCondVta;
    }

    public static double calcularPrIvaI(double tasaDescuento, double tasaDescuentoCondVta) {
        return prIvaIAux * (1 - tasaDescuento) * (1 - tasaDescuentoCondVta);
    }

    public static double calcularPrIva105(double tasaDescuento, double tasaDescuentoCondVta) {
        return prIva105Aux * (1 - tasaDescuento) * (1 - tasaDescuentoCondVta);
    }

    public static double calcularPrIva21(double tasaDescuento, double tasaDescuentoCondVta) {
        return prIva21Aux * (1 - tasaDescuento) * (1 - tasaDescuentoCondVta);
    }

    public static double calcularPrTotImpInterno(double prTotImpInternoAux, double tasaDescuento) {
        return prTotImpInternoAux * (1 - tasaDescuento);
    }

    public static double getPrecioKgListaLibre(double cantidad, double caKilos, double precioUnitarioSinIva, double tasaIva) {
        double precioKgListaLibre = (((precioUnitarioSinIva * cantidad) / caKilos)) * (1 + tasaIva);
        return Matematica.Round(precioKgListaLibre, Matematica.cantDecimales);
    }

    public boolean create(final Venta venta, final Context context, boolean isFromPedido) throws Exception {
        boolean isGuardado;//inicializa en false
        UbicacionGeografica ubicacionGeografica = null;
        try {
            //Seteo la fecha de registro
            if (venta.getFechaRegistro() == null)
                venta.setFechaRegistro(Calendar.getInstance().getTime());
            if (!isFromPedido) {
                venta.setVendedor(VendedorBo.getVendedor());
            }
            if (!isFromPedido && CategoriaRecursoHumano.isRepartidor(_repoFactory)) { //GENERO UN REMITO DESDE CERO
                ClienteBo bo = new ClienteBo(_repoFactory);
                venta.setVendedor(bo.recoveryVendedorCliente(venta.getCliente()));
            }

            //Generando el idMovil
            String idMovil = generarIdMovil(venta, Movimiento.ALTA);
            venta.setIdMovil(idMovil);

            //Tasa de Renta
            double tasaRenta = 0d;
            if (venta.getCliente().getCondicionRenta() != null) {
                tasaRenta = venta.getCliente().getCondicionRenta().getTasaDgr();
            }
            venta.setTasaRenta(tasaRenta);

            //UbicacionGeografica
            Location location = null;
            //Consulto en tabla Empresa por sn_localizacion y ademas valido el rango horario de localizacion
            if (_empresaBo.isRegistrarLocalizacion() && ComparatorDateTime.validarRangoHorarioEnvioLocalizacion()) {
                GPSManagement gps = new GPSManagement(context.getApplicationContext(), idMovil, UbicacionGeografica.OPERACION_VENTA);
                gps.getLocation(false);
                ubicacionGeografica = gps.getUbicacionGeografica();
                location = gps.get_location();
            }

            isGuardado = _ventaRepository.createVentaTransaction(venta, location);
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "create", e);
            throw e;
        }

        if (isGuardado && PreferenciaBo.getInstance().getPreferencia().isEnvioPedidoAutomatico()) {
            //Hago el control de envio de pedidos con la fecha de entrega
            int isEnvioDiferido = ComparatorDateTime.compareDates(venta.getFechaEntrega(), Calendar.getInstance().getTime());

            if (ComparatorDateTime.validarRangoHorarioEnvioPedidos() &&
                    ((isEnvioDiferido == ComparatorDateTime.FECHA_ENVIO_MENOR)
                            || (isEnvioDiferido == ComparatorDateTime.FECHA_ENVIO_IGUAL))) {
                final UbicacionGeografica ubicacionGeograficaEnviar = ubicacionGeografica;
                //Si esta habilitado, envio el pedido
                Thread t = new Thread() {
                    public void run() {
                        try {
                            //enviarPedido(venta, ubicacionGeograficaEnviar, context);
                            SincronizacionBo sincronizacionBo = new SincronizacionBo(context, _repoFactory);
                            sincronizacionBo.enviarPedidosPendientes();
                        } catch (Exception e) {
                            //No se trata la exception porque el servicio va a intentar despues
                        }
                    }
                };
                t.start();
            }
        }
        return isGuardado;
    }

    public void eliminarCreditoFactura(String idDocumento, String idLetra, int puntoVenta, long idNumeroDocumento) throws Exception {
        PkVenta pkVenta = new PkVenta();
        pkVenta.setIdDocumento(idDocumento);
        pkVenta.setIdLetra(idLetra);
        pkVenta.setPuntoVenta(puntoVenta);
        pkVenta.setIdNumeroDocumento(idNumeroDocumento);

        Venta venta;
        venta = _ventaRepository.getCredito(pkVenta);
        if (venta != null) {
            _ventaDetalleRepository.deleteVentasDetalleByVenta(venta);
            _ventaRepository.delete(venta.getId());
        }
    }

    public void setearRemito(Venta venta) {
        venta.setIdPedidoDoc(venta.getId().getIdDocumento());
        venta.setIdPedidoNum(venta.getId().getIdNumeroDocumento());
        venta.setIdPedidoPtoVta(venta.getId().getPuntoVenta());
        venta.setIdPedidoTipoAb(venta.getId().getIdLetra());

    }

    public void generarRemito(Venta remito) throws Exception {
        DocumentoBo documentoBo = new DocumentoBo(_repoFactory);

        //OBTENGO LOS DATOS POR DEFECTO DEL REPARTIDOR PARA GENERAR EL REMITO
        String letra = remito.getId().getIdLetra();
        int ptoVta = PreferenciaBo.getInstance().getPreferencia().getIdPuntoVentaPorDefecto();
        String idTipoDocumento = PreferenciaBo.getInstance().getPreferencia().getIdTipoDocumentoPorDefecto();
        long numeroDocumentoRemito = documentoBo.recoveryNumeroDocumento(idTipoDocumento, letra, ptoVta);

        //SETEO LOS DATOS DEL REMITO
        remito.getId().setIdNumeroDocumento(numeroDocumentoRemito);
        remito.getId().setPuntoVenta(ptoVta);
        remito.getId().setIdLetra(letra);
        remito.getId().setIdDocumento(idTipoDocumento);
        try {
            _ventaRepository.setGenerado(remito.getIdPedidoDoc(), remito.getIdPedidoTipoAb(), remito.getIdPedidoPtoVta(), remito.getIdPedidoNum());
        } catch (Exception exception) {
            throw exception;
        }

    }

    private void registrarVentasDetalles(Venta venta) throws Exception {
        Iterator<VentaDetalle> iterator = venta.getDetalles().iterator();
        int secuencia = 1;
        while (iterator.hasNext()) {
            VentaDetalle ventaDetalle = iterator.next();
            createVentaDetalle(venta, ventaDetalle, secuencia);
            secuencia++;
            for (VentaDetalle detalle : ventaDetalle.getDetalleCombo()) {
                detalle.setIdCombo(ventaDetalle.getId().toString());
                createVentaDetalle(venta, detalle, secuencia);
                secuencia++;
            }
        }
    }

    private void createVentaDetalle(Venta venta, VentaDetalle ventaDetalle, int secuencia) throws Exception {
        PkVentaDetalle id = new PkVentaDetalle();
        id.setSecuencia(secuencia);
        id.setIdDocumento(venta.getId().getIdDocumento());
        id.setIdLetra(venta.getId().getIdLetra());
        id.setIdNumeroDocumento(venta.getId().getIdNumeroDocumento());
        id.setPuntoVenta(venta.getId().getPuntoVenta());
        ventaDetalle.setIdMovil(venta.getIdMovil());
        ventaDetalle.setId(id);

        _ventaDetalleRepository.create(ventaDetalle);
        ventaDetalle.setTipoOperacion(TipoOperacion.insert);

        _articuloRepository.updateStock(ventaDetalle);
        if (ventaDetalle.getIdAccionComDetalleEmp() != 0 || ventaDetalle.getIdAccionComDetalleProveedor() != 0) {
            _accionesComDetalleRepository.updateCaVendidaAccom(ventaDetalle);
        }
    }

    private void registrarMovimiento(Venta venta, String tipoMovimiento, Location location) throws Exception {
        //Registro el movimiento
        Movimiento movimiento = new Movimiento();
        movimiento.setTabla(Venta.TABLE);
        movimiento.setIdMovil(venta.getIdMovil());
        movimiento.setTipo(tipoMovimiento);
        //Location
        if (location != null) {
            movimiento.setLocation(location);
        }
        //Setear el cliente
        movimiento.setIdSucursal(venta.getCliente().getId().getIdSucursal());
        movimiento.setIdCliente(venta.getCliente().getId().getIdCliente());
        movimiento.setIdComercio(venta.getCliente().getId().getIdComercio());
        MovimientoBo movimientoBo = new MovimientoBo(_repoFactory);
        movimientoBo.create(movimiento);
    }

    private String generarIdMovil(Venta venta, String tipoMovimiento) {
        String idVendedor = Formatter.formatNumber(venta.getVendedor().getId(), "000");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String fecha = null;
        if (tipoMovimiento == Movimiento.ALTA) {
            fecha = sdf.format(venta.getFechaVenta());
        }
        if (tipoMovimiento == Movimiento.MODIFICACION) {
            fecha = sdf.format(Calendar.getInstance().getTime());
        }
        String documento = venta.getId().getIdDocumento();
        String letra = venta.getId().getIdLetra();
        String puntoVenta = Formatter.formatNumber(venta.getId().getPuntoVenta(), "0000");
        String numeroDocumento = Formatter.formatNumber(venta.getId().getIdNumeroDocumento(), "00000000");
        String idMovil = idVendedor + "-" + fecha + "-" + documento + "-" + letra + "-" + puntoVenta + "-" + numeroDocumento;
        return idMovil;
    }

    public List<Venta> recoveryByPeriodo(Date fechaDesde, Date fechaHasta, int idVendedor) throws Exception {
        return _ventaRepository.recoveryByPeriodo(fechaDesde, fechaHasta, idVendedor);
    }

    public void update(Venta venta, Context context) throws Exception {
        try {
            //resguardo el idMovilOriginal para actualizar movimiento original
            String idMovilOriginal = venta.getIdMovil();

            // Genero el nuevo idMovil para el nuevo movimiento
            String idMovil = generarIdMovil(venta, Movimiento.MODIFICACION);
            venta.setIdMovil(idMovil);

            //Registrar ubicacionGeografica
            Location location = null;

            //Consulto en tabla Empresa por sn_localizacion y ademas valido el rango horario configurado
            if (_empresaBo.isRegistrarLocalizacion() && ComparatorDateTime.validarRangoHorarioEnvioLocalizacion()) {
                GPSManagement gps = new GPSManagement(context.getApplicationContext(), idMovil, UbicacionGeografica.OPERACION_VENTA);
                location = gps.getLocation(false);
            }

            _ventaRepository.updateVentaTransaction(venta, location, idMovilOriginal);
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "update", e);
            throw new Exception(e);
        }
    }

    public synchronized int enviarVentaAnulada(Venta venta, Context context) throws Exception {
        //Anulo la Venta
        VentaWs ventaWs = new VentaWs(context);
        int result = ventaWs.anularVenta(venta.getIdMovil());

        if (result == CodeResult.RESULT_OK) {
            _movimientoRepository.updateFechaSincronizacion(venta);
        }
        return result;
    }

    public synchronized int enviarPedido(Venta venta, UbicacionGeografica ubicacionGeografica, Context context) throws Exception {
        //Envio la Venta
        VentaWs ventaWs = new VentaWs(context);
        venta.setDetalles(VentaDetalleBo.checkCombosEspeciales(venta.getDetalles()));
        int result = ventaWs.send(venta);
        //Envio la ubicacion geografica

        if (result == CodeResult.RESULT_OK) {
            if (_empresaBo.isRegistrarLocalizacion()) {
                if (ubicacionGeografica != null) {

                    if (ubicacionGeografica.getFechaPosicionMovil() != null) {
                        UbicacionGeograficaWs ubicacionGeograficaWs = new UbicacionGeograficaWs(context);
                        ubicacionGeograficaWs.send(ubicacionGeografica);
                        //Actualizo fecha de sincronizacion de la ubicacion geografica
                        _ubicacionGeograficaRepository.updateFechaSincronizacion(ubicacionGeografica.getIdLegajo(), ubicacionGeografica.getFechaPosicionMovil());
                    }
                }
            }
            _movimientoRepository.updateFechaSincronizacion(venta);
        }

        return result;
    }

    public List<Venta> recoveryByDocumento(String tipoDocumento) throws Exception {
        return _ventaRepository.recoveryByDocumento(tipoDocumento, IVentaRepository.ESTADO_TODAS);
    }

    public List<Venta> recoveryByRepartidor(int idRepartidor) throws Exception {
        return _ventaRepository.recoveryByRepartidor(idRepartidor);
    }

    public List<Venta> recoveryNoEnviadas(String tipoDocumento) throws Exception {
        return _ventaRepository.recoveryByDocumento(tipoDocumento, IVentaRepository.ESTADO_NO_ENVIADAS);
    }

    public List<Venta> recoveryAEnviar(String tipoDocumento) throws Exception {
        return _ventaRepository.recoveryByDocumento(tipoDocumento, IVentaRepository.ESTADO_A_ENVIAR);
    }

    public List<Venta> recoveryAnuladosNoEnviados(String tipoDocumento) throws Exception {
        return _ventaRepository.recoveryByDocumento(tipoDocumento, IVentaRepository.ESTADO_ANULADAS);
    }

    public List<Venta> recoveryByCliente(Cliente cliente, String tipoDocumento) throws Exception {
        return _ventaRepository.recoveryByCliente(cliente, tipoDocumento);
    }

    public Venta recoveryById(PkVenta id) throws Exception {
        Venta venta = _ventaRepository.recoveryByID(id);
        if (venta != null) {
            venta.setId(id);
            List<VentaDetalle> detalles = _ventaDetalleRepository.recoveryByVenta(venta);
            venta.setDetalles(detalles);
            ClienteBo clienteBo = new ClienteBo(_repoFactory);
            Cliente cliente = clienteBo.recoveryById(venta.getCliente().getId());
            venta.setCliente(cliente);

            PkDocumento pkDocumento = new PkDocumento();
            pkDocumento.setIdDocumento(venta.getId().getIdDocumento());
            pkDocumento.setIdLetra(venta.getId().getIdLetra());
            pkDocumento.setPuntoVenta(venta.getId().getPuntoVenta());
            Documento documento = _documentoBo.recoveryById(pkDocumento);
            venta.setDocumento(documento);

            if (!CategoriaRecursoHumano.isRepartidor(_repoFactory)) { //GENERO UN REMITO DESDE CERO
                venta.setVendedor(VendedorBo.getVendedor());
            }
            Repartidor repartidor;
            RepartidorBo repartidorBo = new RepartidorBo(_repoFactory);
            repartidor = repartidorBo.recoveryByVenta(venta);
            if (repartidor == null) {
                repartidor = new Repartidor();
                repartidor.setId(PreferenciaBo.getInstance().getPreferencia().getIdRepartidorPorDefecto());
            }
            venta.setRepartidor(repartidor);
        }
        return venta;
    }

    public Venta recoveryById(String idDocumento, String idLetra, int ptoVta, long idNumeroDocumento) throws Exception {
        PkVenta pkVenta = new PkVenta();
        pkVenta.setIdDocumento(idDocumento);
        pkVenta.setIdLetra(idLetra);
        pkVenta.setPuntoVenta(ptoVta);
        pkVenta.setIdNumeroDocumento(idNumeroDocumento);
        return recoveryById(pkVenta);
    }

    public Venta recoveryByIdEnReparto(String idDocumento, String idLetra, int ptoVta, long idNumeroDocumento) {
        Venta mVenta = null;
        try {
            PkVenta pkVenta = new PkVenta();
            pkVenta.setIdDocumento(idDocumento);
            pkVenta.setIdLetra(idLetra);
            pkVenta.setPuntoVenta(ptoVta);
            pkVenta.setIdNumeroDocumento(idNumeroDocumento);
            mVenta = recoveryById(pkVenta);
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
        return mVenta;
    }

    public void setVendedorPedido(Venta venta) {
        try {
            Vendedor vendedor;
            vendedor = getVendedorVenta(venta.getId());
            venta.setVendedor(vendedor);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private Vendedor getVendedorVenta(PkVenta pkVenta) throws Exception {
        return _vendedorRepository.getVendedorVenta(pkVenta);
    }

    public void delete(Venta venta, Context context) throws Exception {
        try {
            _ventaRepository.delete(venta);
            String idMovil = venta.getIdMovil();
            MovimientoBo movimientoBo = new MovimientoBo(_repoFactory);
            //si el fue enviado, cancela en el servidor
            Movimiento movimientoAlta = movimientoBo.getMovimiento(idMovil);
            if (movimientoAlta.getFechaSincronizacion() == null) {
                movimientoBo.delete(movimientoAlta);
            } else {
                VentaWs ventaWs = new VentaWs(context);
                ventaWs.delete(venta);
                movimientoBo.delete(movimientoAlta);
            }
            ClienteBo clienteBo = new ClienteBo(_repoFactory);
            clienteBo.updateLimiteDisponibilidad(venta.getCliente(), venta.getTotal() * -1, venta.getCondicionVenta());

        } catch (Exception e) {
            ErrorManager.manageException(TAG, "delete", e);
            throw new Exception(e);
        }
    }

    public void anularVenta(final Venta venta, int modo, final Context context) throws Exception {
        boolean isAnulada = false;
        try {
            /*_ventaRepository.delete(venta);
            _ventaDetalleRepository.anularVentasDetalleByVenta(venta);

            if (modo == ANULAR_VENTA_MODO_ENVIAR) {
                registrarMovimiento(venta, Movimiento.BAJA, null);
            }*/
            /*ClienteBo clienteBo = new ClienteBo(_repoFactory);
            clienteBo.updateLimiteDisponibilidad(venta.getCliente(), venta.getTotal() * -1, venta.getCondicionVenta());*/

            // Registro la fecha de anulacion del movimiento
            /*_movimientoRepository.updateFechaAnulacion(venta);*/
            /*_objetivoVentaBo.modificarCantidadVendida(venta, ObjetivoVentaBo.SIGNO_RESTA);*/

            isAnulada = this._ventaRepository.anularVentaTransaction(venta,null, modo);
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "anularVenta", e);
            throw new Exception(e);
        }

        if (isAnulada && PreferenciaBo.getInstance().getPreferencia().isEnvioPedidoAutomatico()) {
            //Hago el control de envio de pedidos con la fecha de entrega
            int isEnvioDiferido = ComparatorDateTime.compareDates(venta.getFechaEntrega(), Calendar.getInstance().getTime());

            if (ComparatorDateTime.validarRangoHorarioEnvioPedidos() &&
                    ((isEnvioDiferido == ComparatorDateTime.FECHA_ENVIO_MENOR)
                            || (isEnvioDiferido == ComparatorDateTime.FECHA_ENVIO_IGUAL))) {
                //Si esta habilitado, envio el pedido
                Thread t = new Thread() {
                    public void run() {
                        try {
                            enviarVentaAnulada(venta, context);
                        } catch (Exception e) {
                            //No se trata la exception porque el servicio va a intentar despues
                        }
                    }
                };
                t.start();
            }
        }
    }

    public List<List<VentaDetalle>> recoveryVentasDetalleByVenta(List<Venta> ventas) throws Exception {
        return _ventaDetalleRepository.recoveryByVenta(ventas);
    }

    public int getCantidadVentas(Cliente cliente) throws Exception {
        return _ventaRepository.getCantidadVentas(cliente);
    }

    public int getCantidadNoPedidos(Cliente cliente) throws Exception {
        return _noPedidoRepository.getCantidadNoPedidos(cliente);
    }

    public List<Venta> recoveryVentas(Date fechaDesde, Date fechaHasta) throws Exception {
        int idVendedor = VendedorBo.getVendedor().getId();
        return _ventaRepository.recoveryVentas(fechaDesde, fechaHasta, idVendedor);
    }

    public boolean isEnviado(Venta venta) throws Exception {
        return _ventaRepository.isEnviado(venta);
    }

    public boolean isGenerado(Venta venta) throws Exception {
        return _ventaRepository.isGenerado(venta);
    }

    public int getCantidadVentasPosteriores() throws Exception {
        return _ventaRepository.getCantidadVentasNoEnviadas(true);
    }

    public List<Venta> filtrarVentas(List<Venta> ventas, Context context, int patronFiltro) {

        List<Venta> ventasFiltradas = new ArrayList<Venta>();
        String snAnulo = "S";
        for (int i = 0; i < ventas.size(); i++) {
            if ((patronFiltro == Preferencia.FILTRO_TODOS)
                    || (patronFiltro == Preferencia.FILTRO_ENVIADOS && ventas.get(i).getFechaSincronizacion() != null)
                    || (patronFiltro == Preferencia.FILTRO_NO_ENVIADAS && ventas.get(i).getFechaSincronizacion() == null)
                    || (patronFiltro == Preferencia.FILTRO_ANULADOS && ventas.get(i).getAnulo().equals(snAnulo))) {

                String filtroTipoDocumento = PreferenciaBo.getInstance().getPreferencia(context).getFiltroTipoDocumento();
                if (filtroTipoDocumento == "" || ventas.get(i).getId().getIdDocumento().equals(filtroTipoDocumento))
                    ventasFiltradas.add(ventas.get(i));
            }
        }

        return ventasFiltradas;

    }

    public boolean documentoControlaRentabilidadPorSucursal(Venta _venta) {
        DocumentoBo documentoBo = new DocumentoBo(_repoFactory);
        PkDocumento pkDocumento = new PkDocumento();
        pkDocumento.setIdDocumento(_venta.getId().getIdDocumento());
        pkDocumento.setPuntoVenta(Integer.parseInt(_venta.getId().getPuntoVenta() + ""));
        pkDocumento.setIdLetra(_venta.getId().getIdLetra());
        Documento documento = null;
        try {
            documento = documentoBo.recoveryById(pkDocumento);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (documento.getSnPedidoRentable() != null &&
                (documento.getSnPedidoRentable().equals("S") || documento.getSnPedidoRentable().equals("T")));
    }

    public boolean documentoControlaRentabilidadPorProveedor(Venta _venta) {
        DocumentoBo documentoBo = new DocumentoBo(_repoFactory);
        PkDocumento pkDocumento = new PkDocumento();
        pkDocumento.setIdDocumento(_venta.getId().getIdDocumento());
        pkDocumento.setPuntoVenta(Integer.parseInt(_venta.getId().getPuntoVenta() + ""));
        pkDocumento.setIdLetra(_venta.getId().getIdLetra());
        Documento documento = null;
        try {
            documento = documentoBo.recoveryById(pkDocumento);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (documento.getSnPedidoRentable() != null &&
                (documento.getSnPedidoRentable().equals("P") || documento.getSnPedidoRentable().equals("T")));
    }

    public void isProductoRentable(VentaDetalle ventaDetalle, Venta venta, ListaPrecioDetalle listaPrecioDetalle) {
        //boolean esRentable = true;
        if (documentoControlaRentabilidadPorSucursal(venta) || documentoControlaRentabilidadPorProveedor(venta)) {
            int idVendedor = Integer.parseInt(String.valueOf(PreferenciaBo.getInstance().getPreferencia().getIdVendedor()));
            VendedorBo vendedorBo = new VendedorBo(_repoFactory);
            Vendedor vendedor = null;
            vendedor = vendedorBo.recoveryById(idVendedor);

            if (vendedor.getTiControlPedidoRentable() != null &&
                    !vendedor.getTiControlPedidoRentable().equals(Vendedor.TI_CONTROL_PR_NO_CONTROLA)) {

                VentaDetalleBo ventaDetalleBo = new VentaDetalleBo(_repoFactory);

                double margen = 0d;
                double costo = 0d;
                double ventaSinImpuestos = 0d;

                if (ventaDetalle.getArticulo().isPromocion()) {   //Combo comun
                    PromocionBo promocionBo = new PromocionBo(_repoFactory);
                    List<PromocionDetalle> detalles = new ArrayList<PromocionDetalle>();
                    try {
                        detalles = promocionBo.recoveryPromocionItems(ventaDetalle.getArticulo());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for (PromocionDetalle detalle : detalles) {
                        costo += detalle.getCantidadComboComun() * detalle.getArticulo().getPrecioCosto();
                    }
                    ventaSinImpuestos = ventaDetalle.getPrecioUnitarioSinIva();
                } else if (ventaDetalle.isCabeceraPromo()) {     //Combo especial
                    PromocionRequisitoBo promocionRequisitoBo = new PromocionRequisitoBo(_repoFactory);
                    Articulo regalo = null;
                    PromocionRequisito promocionRequisito = null;
                    //Acumulo el costo de los detalles
                    for (VentaDetalle detalle : ventaDetalle.getDetalleCombo()) {
                        costo += detalle.getCantidad() * detalle.getArticulo().getPrecioCosto();
                        ventaSinImpuestos += detalle.getPrecioUnitarioSinIva();
                    }
                    //Recupero el regalo
                    try {
                        promocionRequisito = promocionRequisitoBo.getRequisito(ventaDetalle.getArticulo().getId());
                        regalo = _articuloRepository.recoveryByID(promocionRequisito.getIdArticulos());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Tomo el primer regalo y sumo el costo
                    if (regalo != null && promocionRequisito != null) {
                        costo += regalo.getPrecioCosto() * promocionRequisito.getCaArticulosIngreso();
                    }
                } else {
                    costo = ventaDetalle.getArticulo().getPrecioCosto();
                    if (ventaDetalle.getPrecioUnitarioSinIva() != 0) {
                        ventaSinImpuestos = ventaDetalle.getPrecioUnitarioSinIva();
                    } else {
                        ventaSinImpuestos = listaPrecioDetalle.getPrecioSinIva();
                    }
                }

                if (costo != 0d) {
                    margen = (ventaSinImpuestos / costo) - 1;
                }

                if (documentoControlaRentabilidadPorSucursal(venta)) {
                    int idSucursal = venta.getCliente().getId().getIdSucursal();
                    List<RangoRentabilidad> rangosSucursal = new ArrayList<>();
                    try {
                        rangosSucursal = _rangoRentabilidadRepository.recoveryRangosBySucursal(idSucursal, venta.getCliente().getId());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for (RangoRentabilidad rango : rangosSucursal) {
                        if ((rango.getTaDesde() == null || margen >= rango.getTaDesde()) &&
                                (rango.getTaHasta() == null || margen < rango.getTaHasta())) {
                            if (rango.getSnRentable().equals("S")) {
                                listaPrecioDetalle.setRentableXEmpresa(true);
                            } else {
                                listaPrecioDetalle.setRentableXEmpresa(false);
                            }
                            listaPrecioDetalle.setNivelRentabilidadEmpresa(rango.getDescripcion());
                        }
                    }
                }

                if (documentoControlaRentabilidadPorProveedor(venta)) {
                    List<Integer> idProveedores = null;
                    try {
                        idProveedores = _rangoRentabilidadRepository.recoveryProveedoresPR();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    int idProveedor = ventaDetalle.getArticulo().getProveedor().getIdProveedor();
                    if (idProveedores.contains(idProveedor)) {
                        List<RangoRentabilidad> rangosProveedor = new ArrayList<>();
                        try {
                            rangosProveedor = _rangoRentabilidadRepository.recoveryRangosByPIdProveedor(idProveedor, venta.getCliente().getId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        for (RangoRentabilidad rango : rangosProveedor) {
                            if ((rango.getTaDesde() == null || margen >= rango.getTaDesde()) &&
                                    (rango.getTaHasta() == null || margen < rango.getTaHasta())) {
                                if (rango.getSnRentable().equals("S")) {
                                    listaPrecioDetalle.setRentableXProveedor(true);
                                } else {
                                    listaPrecioDetalle.setRentableXProveedor(false);
                                }
                                listaPrecioDetalle.setNivelRentabilidadProveedor(rango.getDescripcion());
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean isPedidoRentable(Venta venta) {
        boolean esRentable = true;
        venta.setNivelRentabilidadProveedor(null);
        int idVendedor = Integer.parseInt(String.valueOf(PreferenciaBo.getInstance().getPreferencia().getIdVendedor()));
        VendedorBo vendedorBo = new VendedorBo(_repoFactory);
        Vendedor vendedor;
        vendedor = vendedorBo.recoveryById(idVendedor);

        if (vendedor.getTiControlPedidoRentable() != null &&
                !vendedor.getTiControlPedidoRentable().equals(Vendedor.TI_CONTROL_PR_NO_CONTROLA)) {

            double margen = 0d;
            double costo = 0d;
            double ventaSinImpuestos = 0d;


            Map<Integer, Double> costosPorProveedor = new HashMap<>();
            Map<Integer, Double> ventaSinImpuestosPorProveedor = new HashMap<>();
            List<Integer> idProveedores = null;
            try {
                idProveedores = _rangoRentabilidadRepository.recoveryProveedoresPR();
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int idProveedor : idProveedores) {
                costosPorProveedor.put(idProveedor, 0d);
                ventaSinImpuestosPorProveedor.put(idProveedor, 0d);
            }

            PromocionBo promocionBo = new PromocionBo(_repoFactory);

            for (VentaDetalle ventaDetalle : venta.getDetalles()) {
                int idProveedorDetalle = ventaDetalle.getArticulo().getProveedor().getIdProveedor();
                if (ventaDetalle.getArticulo().isPromocion()) {   //Combo comun

                    List<PromocionDetalle> detalles = new ArrayList<>();
                    try {
                        detalles = promocionBo.recoveryPromocionItems(ventaDetalle.getArticulo());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for (PromocionDetalle detalle : detalles) {
                        costo += detalle.getCantidadComboComun() * detalle.getArticulo().getPrecioCosto();
                        if (idProveedores.contains(idProveedorDetalle)) {
                            costosPorProveedor.put(idProveedorDetalle,
                                    costosPorProveedor.get(idProveedorDetalle) +
                                            detalle.getCantidadComboComun() * detalle.getArticulo().getPrecioCosto());
                        }
                    }
                    ventaSinImpuestos += ventaDetalle.getCantidad() * ventaDetalle.getPrecioUnitarioSinIva();
                    if (idProveedores.contains(idProveedorDetalle)) {
                        ventaSinImpuestosPorProveedor.put(idProveedorDetalle,
                                ventaSinImpuestosPorProveedor.get(idProveedorDetalle) +
                                        ventaDetalle.getCantidad() * ventaDetalle.getPrecioUnitarioSinIva());
                    }

                } else if (ventaDetalle.isCabeceraPromo()) {     //Combo especial
                    PromocionRequisitoBo promocionRequisitoBo = new PromocionRequisitoBo(_repoFactory);
                    Articulo regalo = null;
                    PromocionRequisito promocionRequisito = null;
                    //Acumulo el costo de los detalles
                    for (VentaDetalle detalle : ventaDetalle.getDetalleCombo()) {
                        costo += detalle.getCantidad() * detalle.getArticulo().getPrecioCosto();
                        ventaSinImpuestos += detalle.getCantidad() * detalle.getPrecioUnitarioSinIva() * ventaDetalle.getCantidad();
                        if (idProveedores.contains(idProveedorDetalle)) {
                            costosPorProveedor.put(idProveedorDetalle,
                                    costosPorProveedor.get(idProveedorDetalle) +
                                            detalle.getCantidad() * detalle.getArticulo().getPrecioCosto());

                            ventaSinImpuestosPorProveedor.put(idProveedorDetalle,
                                    ventaSinImpuestosPorProveedor.get(idProveedorDetalle) +
                                            detalle.getCantidad() * detalle.getPrecioUnitarioSinIva() * ventaDetalle.getCantidad());
                        }
                    }
                    //Recupero el regalo
                    try {
                        promocionRequisito = promocionRequisitoBo.getRequisito(ventaDetalle.getArticulo().getId());
                        regalo = _articuloRepository.recoveryByID(promocionRequisito.getIdArticulos());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Tomo el primer regalo y sumo el costo
                    if (regalo != null && promocionRequisito != null) {
                        costo += regalo.getPrecioCosto() * promocionRequisito.getCaArticulosIngreso() * ventaDetalle.getCantidad();
                        if (idProveedores.contains(idProveedorDetalle)) {
                            costosPorProveedor.put(idProveedorDetalle,
                                    costosPorProveedor.get(idProveedorDetalle) +
                                            regalo.getPrecioCosto() * promocionRequisito.getCaArticulosIngreso() * ventaDetalle.getCantidad());
                        }
                    }
                } else {
                    costo += ventaDetalle.getCantidad() * ventaDetalle.getArticulo().getPrecioCosto();
                    ventaSinImpuestos += ventaDetalle.getCantidad() * ventaDetalle.getPrecioUnitarioSinIva();
                    if (idProveedores.contains(idProveedorDetalle)) {
                        costosPorProveedor.put(idProveedorDetalle,
                                costosPorProveedor.get(idProveedorDetalle) +
                                        ventaDetalle.getCantidad() * ventaDetalle.getArticulo().getPrecioCosto());

                        ventaSinImpuestosPorProveedor.put(idProveedorDetalle,
                                ventaSinImpuestosPorProveedor.get(idProveedorDetalle) +
                                        ventaDetalle.getCantidad() * ventaDetalle.getPrecioUnitarioSinIva());
                    }
                }
            }
            if (costo != 0d) {
                margen = (ventaSinImpuestos / costo) - 1;
            }
            if (documentoControlaRentabilidadPorSucursal(venta)) {
                int idSucursal = venta.getCliente().getId().getIdSucursal();
                List<RangoRentabilidad> rangosSucursal = new ArrayList<>();
                try {
                    rangosSucursal = _rangoRentabilidadRepository.recoveryRangosBySucursal(idSucursal, venta.getCliente().getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (RangoRentabilidad rango : rangosSucursal) {
                    if ((rango.getTaDesde() == null || margen >= rango.getTaDesde()) &&
                            (rango.getTaHasta() == null || margen < rango.getTaHasta())) {
                        if (rango.getSnRentable().equals("S")) {
                            venta.setRentableXEmpresa(true);
                        } else {
                            venta.setRentableXEmpresa(false);
                            esRentable = false;
                        }
                        venta.setNivelRentabilidadEmpresa(rango.getDescripcion());
                    }
                }
            }

            if (documentoControlaRentabilidadPorProveedor(venta)) {
                for (int idProveedor : idProveedores) {
                    List<RangoRentabilidad> rangosProveedor = new ArrayList<>();
                    double margenProveedor;
                    if (ventaSinImpuestosPorProveedor.get(idProveedor) > 0 && costosPorProveedor.get(idProveedor) > 0) {
                        margenProveedor = (ventaSinImpuestosPorProveedor.get(idProveedor) / costosPorProveedor.get(idProveedor)) - 1;
                        try {
                            rangosProveedor = _rangoRentabilidadRepository.recoveryRangosByPIdProveedor(idProveedor, venta.getCliente().getId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        for (RangoRentabilidad rango : rangosProveedor) {
                            if ((rango.getTaDesde() == null || margenProveedor >= rango.getTaDesde()) &&
                                    (rango.getTaHasta() == null || margenProveedor < rango.getTaHasta())) {
                                if (rango.getSnRentable().equals("S")) {
                                    for (VentaDetalle vd : venta.getDetalles()) {
                                        if (vd.getArticulo().getProveedor().getIdProveedor().equals(idProveedor)) {
                                            vd.getArticulo().getProveedor().setRentableEnPedido(true);
                                        }
                                    }
                                } else {
                                    for (VentaDetalle vd : venta.getDetalles()) {
                                        if (vd.getArticulo().getProveedor().getIdProveedor().equals(idProveedor)) {
                                            vd.getArticulo().getProveedor().setRentableEnPedido(false);
                                        }
                                    }
                                    venta.setRentableXProveedor(false);
                                    venta.setNivelRentabilidadProveedor(rango.getDescripcion());
                                    esRentable = false;
                                }
                                if (venta.getNivelRentabilidadProveedor() == null) {
                                    venta.setNivelRentabilidadProveedor(rango.getDescripcion());
                                }
                            }
                        }
                    }
                }
            }
        }
        return esRentable;
    }

    public void devolverTodo(Venta venta) {
        double total = 0d;
        double totalIva = 0d;
        for (VentaDetalle detalle : venta.getDetalles()) {
            if (detalle.isCabeceraPromo()) {
                for (VentaDetalle detallePromo : detalle.getDetalleCombo()) {
                    detallePromo.setBultosDevueltos(detallePromo.getBultos());
                    detallePromo.setUnidadesDevueltas(detallePromo.getUnidades());
                    total += (detallePromo.getPrecioUnitarioSinDescuento() * detallePromo.getCantidad()) / detalle.getCantidad();
                    totalIva += (detallePromo.getPrecioIvaUnitario()) / detalle.getCantidad();
                }
                detalle.setPrecioIvaUnitario(total);
                detalle.setPrecioUnitarioSinDescuentoProveedor(total);
                detalle.setPrecioUnitarioSinDescuentoCliente(total);
                detalle.setPrecioUnitarioSinDescuento(total);
                detalle.setPrecioIvaUnitario(totalIva);
            }

            detalle.setBultosDevueltos(detalle.getBultos());
            detalle.setUnidadesDevueltas(detalle.getUnidades());
        }
    }

    public List<LineaIntegradoMercaderia> getLineas() throws Exception {
        int ti_empresa = PreferenciaBo.getInstance().getPreferencia().getTipoEmpresa();
        String documento = PreferenciaBo.getInstance().getPreferencia().getIdTipoDocumentoPorDefecto();
        return _ventaDetalleRepository.getLineasIntegrado(ti_empresa, documento);
    }

    public boolean isCredito(String idDocumento, String idLetra, int puntoVenta, long idNumeroDocumento) throws Exception {
        PkVenta pkVenta = new PkVenta();
        pkVenta.setIdDocumento(idDocumento);
        pkVenta.setIdLetra(idLetra);
        pkVenta.setPuntoVenta(puntoVenta);
        pkVenta.setIdNumeroDocumento(idNumeroDocumento);

        Venta venta;
        venta = _ventaRepository.getCredito(pkVenta);
        if (venta != null) {
            return true;
        } else {
            return false;
        }
    }

    public int tipoImpresionVenta(String idDocumento, String idLetra, int puntoVenta) throws Exception {
        /* return 	0: No imprime
         ***		1: Imprime completo (valor por defecto)
         ***		2: Sin Nombre Empresa
         ***/
        return _documentoBo.recoveryById(idDocumento, idLetra, puntoVenta).getTiImpresionMovil();
    }
}
