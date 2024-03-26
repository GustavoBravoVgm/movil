package com.ar.vgmsistemas.utils;


import android.content.Context;

import com.ar.vgmsistemas.view.AlertDialog;

import java.util.logging.Level;

public class ErrorManager {
    private static ErrorManager INSTANCE;

    public ErrorManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ErrorManager();
        }
        return INSTANCE;
    }

    public static void manageException(String sourceClass, String sourceMethod, Exception ex, Context context) {
        Logging.getInstance().logp(Level.SEVERE, sourceClass, sourceMethod, ex.getMessage(), ex);
        AlertDialog alert = new AlertDialog(context, "Error", ex.getMessage());
        alert.show();
    }

    public static void manageException(String sourceClass, String sourceMethod, Exception ex) {
        Logging.getInstance().logp(Level.SEVERE, sourceClass, sourceMethod, ex.getMessage(), ex);
    }

    public static void manageException(
            String sourceClass, String sourceMethod, Exception ex, Context context,
            String title, String message) {
        Logging.getInstance().logp(Level.SEVERE, sourceClass, sourceMethod, ex.getMessage(), ex);
        AlertDialog alert = new AlertDialog(context, title, message);
        alert.show();
    }

    public static void manageException(
            String sourceClass, String sourceMethod, Exception ex, Context context,
            int titleId, int messageId) {
        Logging.getInstance().logp(Level.SEVERE, sourceClass, sourceMethod, ex.getMessage(), ex);
        AlertDialog alert = new AlertDialog(context, titleId, messageId);
        alert.show();
    }

    public static final String ClienteSinAtributoBDD = "El cliente seleccionado no tiene asignado algun atributo en la base de datos";
    public static final String SetearListaDefectoCliente = "setear lista por defecto cliente";
    public static final String DocumentoNoSeleccionado = "No se ha seleccionado ningun documento";
    public static final String DocumentoPagarNoSeleccionado = "No se ha seleccionado ningun documento a pagar";
    public static final String PedidoSinLineas = "El pedido no tiene articulos cargados.";
    public static final String GuardarRecibo = "Error al guardar el recibo";
    public static final String FaltaSeleccionArticulo = "No se ha seleccionado ningun articulo.";
    public static final String SeleccionarBanco = "Debe seleccionar un banco.";
    public static final String SeleccionarPuntoVenta = "Debe ingresar el Punto de Venta.";
    public static final String SeleccionarNumero = "Debe ingresar el Numero.";
    public static final String IngresarSucursal = "Debe ingresar la sucursal.";
    public static final String SeleccionarProveedor = "Debe seleccionar un banco.";
    public static final String IngresarNumero = "Debe ingresar el numero.";
    public static final String NumeroMaxCifras = "El Numero no puede tener mas de 8 cifras.";
    public static final String ConceptoMaxCaracteres = "El Concepto no puede tener mas de 60 caracteres.";
    public static final String EgresoExistente = "Este egreso ya existe o algun dato ingresado es incorrecto.";
    public static final String ErrorAlbuscarEgreso = "Se produjo un error al guardar el egreso.";
    public static final String IngresarNumeroCuenta = "Debe ingresar el numero de cuenta.";
    public static final String IngresarNumeroCuentaGrande = "El numero de cuenta que esta ingresando es muy grande. Verifique";
    public static final String ImporteInvalido = "El importe ingresado no es valido.";
    public static final String numeroDeRetencionYaExiste = "Ya existe una retencion con el numero ingresado. Intente con otro numero";
    public static final String CantidadInvalida = "La cantidad ingresada debe ser valida";
    public static final String IngresarPuntoVenta = "Debe ingresar el punto de venta.";
    public static final String RangoHorarioEnvioInvalido = "El rango horario para el envio configurado es invalido";
    public static final String ConexionServidor = "Error al conectar con el servidor";
    public static final String ConexionServidorFTP = "Error al conectar con el servidor ftp";
    public static final String ArchivoBDnoExiste = "No se encontro el archivo de base de datos";
    public static final String CodigoAutorizacionInvalido = "El codigo ingresado es invalido o ya ha sido utilizado";
    public static final String ErrorLimiteSaldoSuperado = "El pedido ha superado el limite de disponibilidad del cliente que es de: $";
    public static final String LimiteDisponibilidadModificar = ", modifique el pedido o cambie la condición de venta.";
    //public static final String ErrorConexion = "La conexion ha fallado, verifique su conexion e intente mas tarde.";
    // Mensaje Anterior: "No se pudo conectar al servidor. Verifique su conexion a Internet"
    public static final String ErrorConexion = "#3425: No se pudo conectar, no hay vinculo con el servidor";
    public static final String ErrorLogin = "Usuario o clave incorrecto, por favor vuelva a intentar";
    public static final String MotivosCreditoNoConfigurados = "No estan configurados los motivos de credito.";

    public static final String ErrorAccesoSdCard = "Error al acceder a la tarjeta de memoria.";
    public static final String ErrorConexionServidor = "No se pudo conectar al servidor. Puede estar mal configurado el movil o estar fuera de servicio el servidor";
    public static final String ErrorTarjetaSDLlena = "Memoria llena. No hay espacio suficiente para sincronizar, por favor verifique";
    public static final String ErrorPedidosDeshabilitados = "No es posible enviar los datos, se encuentra desactivado el envio de pedidos. Pongase en contacto con su empresa";
    public static final String ErrorVendedorNoValido = "No es posible realizar mas acciones, pongase en contacto con su empresa";
    public static final String ErrorVinculoConServidor = "#4902. Se ha perdido el vinculo con el servidor o se ha superado el limite de tiempo de espera";

    public static final String ErrorConexionDatos = "No se encontraron conexiones de 3G ni Wifi.";
    public static final String LoginInvalido = "El usuario o contrasena no son validos";
    public static final String LoginDiferente = "El usuario no corresponde a este telefono";
    public static final String ErrorAccesoBd = "Error al conectarse a la base de datos. Es necesario descargar los datos.";
    public static final String CodigoVendedorNoIngresado = "No se ha ingresado el codigo del vendedor.";
    public static final String CodigoCantidadNoIngresada = "No se ha ingresado la cantidad del producto.";
    public static final String UsuarioFTPNoIngresado = "No se ha ingresado el usuario FTP.";
    public static final String UsuarioFTPIncorrecto = "No se pudo conectar. Verifique el usuario FTP.";
    public static final String SeleccionarLocalidad = "Debe seleccionar una localidad.";
    public static final String CargarPrecioCompra = "Debe cargar el subtotal.";
    public static final String ErrorEnvioDatosServidor = "No se pudo enviar los datos al servidor.";
    public static final String ErrorDescargaDatosServidor = "Error en la descarga de datos. Se trabajaro con una version anterior.";
    public static final String ErrorSinCobranza = "#23927 No es posible realizar la cobranza. No existen talonarios de recibos o el documento no esta configurado.";
    public static final String FacturasVencidas = "No se puede registrar un pedido porque el cliente presenta facturas vencidas en cuenta corriente o ha superado el limite de credito.";
    public static final String ListaPrecioNoDisponible = "La lista de precio no esta disponible para este cliente";
    public static final String ListaPrecioDocumentoNoDisponible = "La lista de precio del documento no esta disponible para el documento seleccionado";
    public static final String ErrorAlRecuperarLaListaPrecio = "Error #1449: No se pudo recuperar la lista de precio por defecto del cliente o la misma no esta disponible para el movil";
    public static final String SoporteDeBluetooth = "El telefono no soporta bluetooth";
    public static final String AlertGPS = "Debe tener encendido el Gps para poder utilizar la aplicacion";
    public static final String ErrorModoAcceso = "Error en el Modo de Acceso";
    public static final String NoUbicacion = "No se pudo determinar la ubicacion. Presione el boton gps en la parte superior derecha y espere hasta visualizar el marcador azul.";
    public static final String IngresarNumerosEnteros = "Debe ingresar numeros enteros";
    public static final String NoArticuloSeleccionado = "No se ha seleccionado ningun articulo.";
    public static final String MaxAccomSuperadoCA = "Se supero la cantidad maxima de acciones comerciales para el articulo. Se requiere autorizacion para aplicar el descuento.";
    public static final String MaxAccomSuperadoSD = "Se supero la cantidad maxima de acciones comerciales para el articulo. No se aplicara el descuento.";
    public static final String NoUnidadIngresada = "No se han ingresado las unidades.";
    public static final String PorcentajeDescuentoInvalido = "El porcentaje de descuento no es valido.";
    public static final String VentaNoPermitida = "No esta permitida la condicion de venta para este cliente";
    public static final String EvaluarLimiteLineasPedido = "Error al evaluar el limite de lineas por pedido";
    public static final String RecuperarLineas = "Error al recuperar limite de lineas por pedido. Verifique configuracion de Documento en el celular";
    public static final String ValorNoEncontrado = "No se encuentran configurado los valores por defecto. Debe seleccionarlos en la cabecera.";
    public static final String ErrorConfiguracionDocumento = "No se pudo recuperar el limite de lineas por pedido. Verifique la configuracion de documento en el movil";
    public static final String ErrorReciboVacio = "El recibo no tiene comprobantes asociados o pagos";
    public static final String ErrorClienteConAtributoFaltante = "Error #2916. Al cliente le faltan atributos en la base de datos";
    public static final String ErrorMontoMinimoNoAlcanzadao = "Error #2986. No se ha alcanzado el monto minimo que es de $";
    public static final String ErrorEvaluarMontoMinimoFactura = "Error #2987. Error al evaluar configuracion de monto minimo de factura";
    public static final String ErrorEvaluarMontoMinimoDescuento = "Error #2994. Error al evaluar configuracion de monto minimo para realizar descuentos";
    public static final String ErrorMontoMinimoDescuentoNoAlcanzado = "Error #2995. No se ha alcanzado el monto minimo para aplicar descuentos que es de $";
    public static final String ErrorPorcentajeMaximoArticulosCriticos = "Error #3050. El pedido que desea grabar supera el porcentaje de articulos criticos";
    public static final String ErrorPedidoEnvioPosterior = "Hay pedidos con fecha de envio al servidor posterior a la fecha actual";
    public static final String ErrorDescargarBaseDeDatos = "Error al descargar la base de datos. Se ha perdido la conexion a internet. Por favor intente de nuevo";
    public static final String ErrorBackUp = "No se pudo hacer backup al sincronizar por espacio insuficiente en tarjeta SD";
    public static final String ErrorBorrarZip = "No existe el archivo preventa.zip";
    public static final String ErrorClientesArticulos = "Error #3424. Base de datos daniada. Debe sincronizar de nuevo para acceder a los datos";
    public static final String ErrorAccederTablaArticulos = "Error #3442: No se pudieron recuperar los articulos. Debe sincronizar para tener los datos";
    public static final String ErrorAccederALosDatos = "Error #3443: No se pudieron recuperar los datos. Debe sincronizar.";
    public static final String ErrorCodigoAutorizacionAccionComercial = "Ingrese un codigo de autorizacion comercial correcto.";

    public static final String ErrorEnviarDatosAlActualizarFTP = "Error. No se pudo descargar las configuraciones porque no se han podido enviar los datos pendientes";
    public static final String ErrorChequeRepetido = "Se ha registrado un cheque repetido en el recibo. Verifique";
    public static final String ErrorComboNoVigente = "El Combo no esta vigente en la lista de precio seleccionada";
    public static final String ErrorListaNoValida = "Listas seleccionadas no son validas";
    public static final String ErrorDocumentoNoValido = "Documento no valido";
    public static final String ErrorNumeroRecibo = "Numero de recibo invalido o no disponible";
    public static final String ErrorNumeroTropaFaltante = "Es necesario ingresar el numero de tropa de algunas lineas de la venta";
    public static final String ErrorSetearPreciosDetalle = "Error. No se pudieron asignar los precios del detalle";
    public static final String ErrorGenerarVenta = "Error. No se pudo generar la venta con objetivos de venta";
    public static final String ErrorNroTropa = "Ingrese el numero de tropa";
    public static final String ErrorTimeout = "Error por expiracion del tiempo de espera al servidor. Por favor intente de nuevo.";

    public static final String ERROR = "Error";
    public static final String ERROR_NO_KILOGRAMOS = "Ingrese los kilogramos";
    public static final String ERROR_NO_PRECIO_KILOGRAMOS = "Ingrese el precio por kilo";
    public static final String INFORMACION = "Informacion";

    public static final String NoPoseePromocionesDisponibles = "No posee promociones disponibles para este articulo.";

    public static final String ErrorHojasDetalles = "Al menos un comprobante en las hojas detalle pendientes fue cambiado de hoja, por favor verifique con la administracion.";

    public static final String ERROR_ACCESO_BD_CONFIG_BASICA = "Error en acceso a la base de datos movil.";
}
