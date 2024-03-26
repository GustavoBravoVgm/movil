package com.ar.vgmsistemas.entity;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.ar.vgmsistemas.view.menu.NavigationMenu;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

@Root
public class Preferencia {

    // TODO REFACTORIZAR TODO ESTO

    public static final String PSW_DESBLOQUEAR = "vgmsistemas127";
    public static final String STRING_HTTP = "http://";


    @Element(name = "nombreEmpresa")
    private String nombreEmpresa;
    @Element(name = "isValidVendedor")
    private boolean isValidVendedor = true;
    @Element(name = "tipoEmpresa")
    private int tipoEmpresa;
    @Element(name = "idCategoria")
    private int idCategoria = 1;
    @Element(name = "idTipoDocumentoPorDefecto")
    private String _idTipoDocumentoPorDefecto = "";// = "PD";
    private String idTipoDocumentoRecibo;
    private int idPuntoVentaPorDefectoRecibo;

    @Element(name = "idPuntoVentaPorDefecto")
    private int _idPuntoVentaPorDefecto;// = 1;
    @Element(name = "idRepartidorPorDefecto")
    private int _idRepartidorPorDefecto;// = 1;
    @Element(name = "idVendedor")
    private int idVendedor;// = 1; // 23646 a
    @Element(name = "tipoDatoIdArticulo", required = false)
    private String tipoDatoIdArticulo;
    @Element(name = "categoriasEmpRepartidor")
    private int categoriasEmpRepartidor;
    @Element(name = "intervaloSincronizacionAutomatica")
    private int _intervaloSincronizacionAutomatica = 300; // 5 minutos
    private int _intervaloEnvioLogs = 1200; // 20 minutos
    private int _intervaloActualizacionApk = 999999;
    @Element(name = "urlRemotaServidor")
    private String _urlRemotaServidor = STRING_HTTP;// =
    // "http://192.168.0.33:8480/preventaweb";
    @Element(name = "urlRemotaServidor2")
    private String _urlRemotaServidor2 = STRING_HTTP;
    private String _urlServidorActiva;
    @Element(name = "urlLocalServidor")
    private String _urlLocalServidor = STRING_HTTP;
    @Element(name = "ordenPreferidoCCCliente")
    private int _ordenPreferidoCCCliente = RAZON_SOCIAL;
    @Element(name = "urlFtp")
    private String _urlFtp = "vgmsistemas.no-ip.biz";
    @Element(name = "ftpUserName")
    private String ftpUserName;// = "jotabectes";
    @Element(name = "ftpFolder")
    private String ftpFolder = "/vgmgema";
    private String ftpLogFolder = ftpFolder + "/logs";
    private String ftpApkFolder = ftpFolder + "/preventamovil";
    @Element(name = "ftpPassword")
    private String ftpPassword = "vgmsistemas127";
    @Element(name = "pathSistemaPreferido")

    //public static Context _context;

    public static String pathSistemaPreferido;// = Environment.getExternalStorageDirectory() + "";

    // Este tiene que ser static corneta
    private static String folderVgmgema = "/vgmgema/";

    private static String folderResguardo = "/resguardoVersion/";
    private static String pathImages = "vgmgema/images";
    //@Element(name = "pathDB")
    private static String _pathDB = "preventa.sqlite";
    //@Element(name = "pathDBZip")
    private static String _pathDBZip = "preventa.zip";
    private int homeNavigationByPreferencia = NavigationMenu.NO_INICIAR;

    public static String _fileVersionInfo = "versionInfo.xml";
    public static String _filePreferencias = "preferencias.xml";
    public static String _preventaApk = "PreventaMovil.apk";
    public static String _preventaSqlite = "Preventa.sqlite";
    public static String _fileVersionInfoVendedores = "versionInfoVendedores.json";

    @Element(name = "intervaloBorradoBck")
    private static int _intervaloBorradoBck = 15;
    private static int _borradoBckXCantidad = 10;

    public static final int BORRADO_POR_INTERVALO = 0;
    public static final int BORRADO_POR_CANTIDAD = 1;
    private static int borradoBckupPreferido = BORRADO_POR_INTERVALO;

    public static final int BD_EN_MEMORIA_INTERNA = 0;
    public static final int BD_EN_TARJETA_SD_1 = 1;
    public static final int BD_EN_TARJETA_SD_2 = 2;

    @Element(name = "lugarAlmacenamPreferido")
    private static int lugarAlmacenamientoBDPreferido = BD_EN_MEMORIA_INTERNA;

    public static final int NOTIFICATION_ERROR_DESCARGA_STOCK = 123123;
    public static final int NOTIFICATION_ACTUALIZACION = 123124;

    public static final int RAZON_SOCIAL = 0;
    public static final int DOMICILIO = 1;
    public static final int ORDEN_VISITA = 2;
    public static final int CODIGO = 5;
    public static final int DESCRIPCION = 6;

    public static final int CC_SALDO_ASC = 7;
    public static final int CC_SALDO_DESC = 8;
    // PREFERENCIAS ARTICULOS
    public static final int ARTICULO_CODIGO = 0;
    public static final int ARTICULO_DESCRIPCION = 1;
    public static final int ARTICULO_CANTIDAD = 2;

    // public static final int SUBRUBRO = 7;
    public static final int FECHA = 3;
    public static final int MOTIVO = 4;

    public static final int DIA_VISITA_DOMINGO = 1;
    public static final int DIA_VISITA_LUNES = 2;
    public static final int DIA_VISITA_MARTES = 3;
    public static final int DIA_VISITA_MIERCOLES = 4;
    public static final int DIA_VISITA_JUEVES = 5;
    public static final int DIA_VISITA_VIERNES = 6;
    public static final int DIA_VISITA_SABADO = 7;
    public static final int DIA_VISITA_TODOS = 8;
    public static final int FILTRO_CLIENTES_VISITADOS = 0;
    public static final int FILTRO_CLIENTES_SIN_VISITAS = 1;
    public static final int FILTRO_CLIENTES_TODOS = 2;
    public static final int FILTRO_LOCALIDAD_CLIENTE_TODOS = -1;
    public static final int FILTRO_SUCURSAL_CLIENTE_TODAS = -1;
    public static final int FILTRO_ENVIADOS = 0;
    public static final int FILTRO_NO_ENVIADAS = 1;
    public static final int FILTRO_TODOS = 2;
    public static final int FILTRO_ANULADOS = 3;

    private int _filtroVentasEnviadas = FILTRO_TODOS;
    private int _filtroNoAtencionEnviadas = FILTRO_TODOS;
    private int _filtroRecibosEnviados = FILTRO_TODOS;
    private int _filtroEgresosEnviados = FILTRO_TODOS;
    private String _filtroTipoDocumento = "";

    @Element(name = "filtroPreferidoCliente")
    private int _filtroPreferidoCliente = FILTRO_CLIENTES_TODOS;
    @Element(name = "busquedaPreferidaCliente")
    private int _busquedaPreferidaCliente = RAZON_SOCIAL;
    @Element(name = "ordenPreferidoCliente")
    private int _ordenPreferidoCliente = RAZON_SOCIAL;
    @Element(name = "ordenPreferidoArticulo")
    private int _ordenPreferidoArticulo = ARTICULO_DESCRIPCION;
    @Element(name = "busquedaPreferidaArticulo")
    private int _busquedaPreferidaArticulo = ARTICULO_DESCRIPCION;
    @Element(name = "ordenPreferidoNoPedido")
    private int _ordenPreferidoNoPedido = FECHA;
    @Element(name = "envioPedidoAutomatico")
    private boolean _envioPedidoAutomatico = true;
    @Element(name = "envioReciboAutomatico")
    private boolean _envioReciboAutomatico = true;
    @Element(name = "envioHojaDetalleAutomatico")
    private boolean _envioHojaDetalleAutomatico = true;
    @Element(name = "envioEgresoAutomatico")
    private boolean _envioEgresoAutomatico = true;
    @Element(name = "envioEntregaAutomatico")
    private boolean _envioEntregaAutomatico = true;
    @Element(name = "descargaStockAutomatico")
    private boolean _descargaStockAutomatico = true;

    @Element(name = "busquedaPorListaArticulo")
    private boolean _busquedaPorListaArticulo = false;

    @Element(name = "formatoFecha")
    private String _formatoFecha = "dd/MM/yyyy";
    @Element(name = "ultimoBackup")
    private String _pathDbBackup = "";
    @Element(name = "adminUser")
    private String _adminUser = "23646";
    @Element(name = "adminPassword")
    private String _adminPassword = "a";
    @Element(name = "filtroLocalidadCliente")
    private int _filtroLocalidadCliente = FILTRO_LOCALIDAD_CLIENTE_TODOS;
    @Element(name = "filtroSucursalCliente")
    private int filtroSucursalCliente = FILTRO_SUCURSAL_CLIENTE_TODAS;
    @Element(name = "version")
    private int _version = 0;
    @Element(name = "fechaUltimaSincronizacion")
    private Date fechaUltimaDescarga;

    @Element(name = "fechaLimiteActualizacion")
    private String fechaLimiteActualizacion = "";
    @Element(name = "sincronizacionLocal")
    private boolean _sincronizacionLocal;

    private int _diaVisita;
    // guardamos aca el filtro utilizado en la busqueda de articulos
    // para que en la proxima busqueda no tenga que volver a escribirlo
    private static String _textoFiltroArticulo;
    private static Articulo _ultimoArticuloSeleccionado;

    // TIMEOUT
    @Element(name = "timeOutAlto")
    private int timeOutAlto = 360000; // 6 minutos
    @Element(name = "timeOutMedio")
    private int timeOutMedio = 120000; // 2 minutos
    @Element(name = "timeOutBajo")
    private int timeOutBajo = 30000; // 30 segundos
    @Element(name = "timeOutToken")
    private int timeOutToken = 10000; // 10 segundos

    // Envio de pedidos según franja horaria, por defecto, deshabilitado
    @Element(name = "isEnvioPedidosPorFranja")
    private boolean isEnvioPedidosPorFranja = false;
    // IS MULTI EMPRESA
    private boolean isMultiEmpresa = false;
    private boolean isManejoTurno = false;
    //MANEJA NUMERACION RECIBO
    private boolean isReciboProvisorio;
    // Rango horario de env�o
    // Inicio
    @Element(name = "horaInicioEnvio")
    private int horaInicioEnvio = 0;
    @Element(name = "minutosInicioEnvio")
    private int minutosInicioEnvio = 0;
    // Fin
    @Element(name = "horaFinEnvio")
    private int horaFinEnvio = 0;
    @Element(name = "minutosFinEnvio")
    private int minutosFinEnvio = 0;

    // Cambiar punto de venta en la cabecera de venta
    @Element(name = "cambiarPtoVta")
    private boolean _cambiarPtoVta = true;

    // Para el filtrado de artículos
    private static int _busquedaRecordadaSubrubro = 0; // Corresponde a todos
    // los artículos, no
    // filtro
    private static int _listaPrecioRecordada = 0; // Corresponde a la lista
    // Lista
    private static int _ultimaPosicionArticuloLista = 0; // Primera posición

    // Localización Geográfica
    @Element(name = "distanciaMinimaEntreLocalizacion")
    private long DISTANCIA_MINIMA_ENTRE_LOCALIZACION = 0; // 10 meters
    @Element(name = "intervaloTiempoEntreLocalizaciones")
    private long INTERVALO_DE_TIEMPO_ENTRE_LOCALIZACION = 180000; // En milisegundos

    // Config. Envío de localizaciones
    @Element(name = "horaEntradaManana")
    private Date horaEntradaManana;
    @Element(name = "horaSalidaManana")
    private Date horaSalidaManana;
    @Element(name = "horaEntradaTarde")
    private Date horaEntradaTarde;
    @Element(name = "horaSalidaTarde")
    private Date horaSalidaTarde;

    // Ultima ubicacion registrada
    @Element(name = "ultimaLatitud")
    private String ultimaLatitud = "0";
    @Element(name = "ultimaLongitud")
    private String ultimaLongitud = "0";
    @Element(name = "fechaUltimaUbicacion")
    private Date fechaUltimaUbicacion;
    private String isControlLimiteDisponibilidad = "N";
    @Element(name = "sugerenciaSincronizarInicio")
    private boolean sugerenciaSincronizarInicio = true;
    private String contrasena;
    private boolean recordarContrasena = false;
    // Configuraciones Empresa - Tarea #3036
    @Element(name = "isCobranzaEstricta")
    private String isCobranzaEstricta = "S";
    @Element(name = "isDescuento")
    private String isDescuento = "N";
    @Element(name = "isRegistrarLocalizacion")
    private String isRegistrarLocalizacion = "N";
    @Element(name = "porcentajeArticulosCriticos")
    private double porcentajeArticulosCriticos = 1;
    @Element(name = "montoMinimoFactura")
    private double montoMinimoFactura = 0;
    @Element(name = "montoMinimoDescuentoFactura")
    private double montoMinimoDescuentoFactura = 0;
    @Element(name = "segundosTolerancia")
    private int segundosTolerancia;

    // Ultimo recibo generado
    @Element(name = "idPuntoVentaUltimoRecibo")
    private int _idPuntoVentaUltimoRecibo;
    @Element(name = "idUltimoRecibo")
    private long _idUltimoRecibo;

    @Element(name = "snMovilReciboDto")
    private String snMovilReciboDto = "N";

    @Element(name = "snControlaReciboBn")
    private String snControlaReciboBn = "N";

    public Preferencia() {

        Calendar calendar = Calendar.getInstance();
        if (fechaUltimaUbicacion == null) {
            fechaUltimaUbicacion = calendar.getTime();
        }
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        fechaUltimaDescarga = calendar.getTime();

        this._diaVisita = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        horaEntradaManana = calendar.getTime();
        horaSalidaManana = calendar.getTime();
        horaEntradaTarde = calendar.getTime();
        horaSalidaTarde = calendar.getTime();
    }

    public boolean isValidVendedor() {
        return isValidVendedor;
    }

    public static void crearDirectorioAplicacion() {
        try {
            // Creo el directorio de la aplicacion si no existe
            File fileSistema = new File(getPathSistema());
            if (!fileSistema.exists()) {
                boolean carpetaCreada = fileSistema.mkdir();

                if (carpetaCreada) {
                    Log.d("Carpeta", "Carpeta creada");
                } else {
                    Log.e("Carpeta", "Error");
                }
            }

            File fileResguardoVersion = new File(getPathResguardoVersion());
            if (!fileResguardoVersion.exists()) {
                fileResguardoVersion.mkdirs();
            }
        } catch (Exception se) {
            se.printStackTrace();
        }
    }


    public static void crearDirectorioAplicacion(Context context) {
        try {
            // Creo el directorio de la aplicacion si no existe
            String path, pathResguardo;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                path = context.getExternalFilesDir(null) + "" + getFolderVgmgema();
                pathResguardo = path + getFolderResguardo();
            } else {
                path = Environment.getExternalStorageDirectory() + "" + getFolderVgmgema();
                pathResguardo =  path + getFolderResguardo();
            }

            File fileSistema = new File(path);
            if (!fileSistema.exists()) {
                boolean carpetaCreada = fileSistema.mkdir();
                /*if (carpetaCreada) {
                    setPathSistemaPreferido(path);
                    Log.d("Carpeta", "Carpeta creada");
                } else {
                    Log.e("Carpeta", "Error");
                }*/
            }

            File fileResguardoVersion = new File(pathResguardo);//File(getPathResguardoVersion());
            if (!fileResguardoVersion.exists()) {
                fileResguardoVersion.mkdirs();
            }
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    public void setValidVendedor(boolean isValidVendedor) {
        this.isValidVendedor = isValidVendedor;
    }

    public int getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(int tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public boolean isMultiEmpresa() {
        return isMultiEmpresa;
    }

    public boolean isManejoTurno() {
        return isManejoTurno;
    }

    public void setManejoTurno(boolean isManejoTurno) {
        this.isManejoTurno = isManejoTurno;
    }

    public boolean isReciboProvisorio() {
        return isReciboProvisorio;
    }

    public void setReciboProvisorio(boolean isReciboProvisorio) {
        this.isReciboProvisorio = isReciboProvisorio;
    }

    public void setMultiEmpresa(boolean isMultiEmpresa) {
        this.isMultiEmpresa = isMultiEmpresa;
    }

    public void setBusquedaPreferidaCliente(int _busquedaPreferida) {
        this._busquedaPreferidaCliente = _busquedaPreferida;
    }

    public int getBusquedaPreferidaCliente() {
        return this._busquedaPreferidaCliente;
    }

    public void setOrdenPreferidoCliente(int _ordenPreferidoCliente) {
        this._ordenPreferidoCliente = _ordenPreferidoCliente;
    }

    public int getOrdenPreferidoCliente() {
        return this._ordenPreferidoCliente;
    }

    public void setBusquedaPreferidaArticulo(int _busquedaPreferidaArticulo) {
        this._busquedaPreferidaArticulo = _busquedaPreferidaArticulo;
    }

    public int getBusquedaPreferidaArticulo() {
        return this._busquedaPreferidaArticulo;
    }

    public void setOrdenPreferidoArticulo(int _ordenPreferidoArticulo) {
        this._ordenPreferidoArticulo = _ordenPreferidoArticulo;
    }

    public int getOrdenPreferidoArticulo() {
        return this._ordenPreferidoArticulo;
    }

    /**
     * @return the ordenPreferidoNoPedido
     */
    public int getOrdenPreferidoNoPedido() {
        return this._ordenPreferidoNoPedido;
    }

    /**
     * @param ordenPreferidoNoPedido the ordenPreferidoNoPedido to set
     */
    public void setOrdenPreferidoNoPedido(int ordenPreferidoNoPedido) {
        this._ordenPreferidoNoPedido = ordenPreferidoNoPedido;
    }

    public void setDiaVisita(int diaVisita) {
        this._diaVisita = diaVisita;
    }

    public int getDiaVisita() {
        return this._diaVisita;
    }

    public void setUrlRemotaServidor(String urlRemotaServidor) {
        this._urlRemotaServidor = (urlRemotaServidor != null) ? urlRemotaServidor.trim() : "";
    }

    public String getUrlRemotaServidor() {
        return this._urlRemotaServidor;
    }

	/*public void setPathDB(String pathDB) {
		Preferencia._pathDB = pathDB;
	}*/

    public static String getPathDB() {
        return getPathSistema() + _pathDB;
    }

	/*public void setPathDBZip(String pathDBZip) {
		Preferencia._pathDBZip = pathDBZip;
	}*/

    public static String getPathDBZip() {
        return getPathSistema() + _pathDBZip;
    }

    public void setEnvioPedidoAutomatico(boolean envioPedidoAutomatico) {
        _envioPedidoAutomatico = envioPedidoAutomatico;
    }

    public boolean isEnvioPedidoAutomatico() {
        return _envioPedidoAutomatico;
    }

    public void setDescargaStockAutomatico(boolean descargaStockAutomatico) {
        _descargaStockAutomatico = descargaStockAutomatico;
    }

    public boolean isDescargaStockAutomatico() {
        return _descargaStockAutomatico;
    }

    public boolean isBusquedaPorListaArticulo() {
        return _busquedaPorListaArticulo;
    }

    public void setBusquedaPorListaArticulo(boolean busquedaPorListaArticulo) {
        this._busquedaPorListaArticulo = busquedaPorListaArticulo;
    }

    public void setFormatoFecha(String formatoFecha) {
        this._formatoFecha = formatoFecha;
    }

    public String getFormatoFecha() {
        return _formatoFecha;
    }

    public void setPathDbBackup(String ultimoBackup) {
        this._pathDbBackup = ultimoBackup;
    }

    public String getPathDbBackup() {
        return _pathDbBackup;
    }

    public static String getFolderVgmgema() {
        return folderVgmgema;
    }
    public static String getFolderVgmgemaAndPathDB() {
        return folderVgmgema + _pathDB ;
    }

    public static String getFolderResguardo() {
        return folderResguardo;
    }

    public static String getPathSistema() {
        return pathSistemaPreferido + getFolderVgmgema();
    }

    public static String getPathResguardoVersion() {
        return getPathSistema() + getFolderResguardo();
    }

    /*public static void setPathResguardoVersion(String pathResguardoVersion) {
        Preferencia.pathResguardoVersion = pathResguardoVersion;
    }*/

    /*public void setPathSistema(String pathSistema) {
        Preferencia.pathSistema = pathSistema;
    }*/

    public void setAdminUser(String adminUser) {
        this._adminUser = adminUser;
    }

    public String getAdminUser() {
        return _adminUser;
    }

    public void setAdminPassword(String adminPassword) {
        this._adminPassword = adminPassword;
    }

    public String getAdminPassword() {
        return _adminPassword;
    }

    public void setFiltroPreferidoCliente(int filtroPreferidoCliente) {
        this._filtroPreferidoCliente = filtroPreferidoCliente;
    }

    public int getFiltroPreferidoCliente() {
        return _filtroPreferidoCliente;
    }

    public void setFiltroVentasEnviadas(int filtroVentasEnviadas) {
        this._filtroVentasEnviadas = filtroVentasEnviadas;
    }

    public int getFiltroVentasEnviadas() {
        return _filtroVentasEnviadas;
    }

    public void setFiltroTipoDocumento(String filtroTipoDocumento) {
        this._filtroTipoDocumento = filtroTipoDocumento;
    }

    public String getFiltroTipoDocumento() {
        return _filtroTipoDocumento;
    }

    public void setFiltroNoAtencionEnviadas(int filtroNoAtencionEnviadas) {
        this._filtroNoAtencionEnviadas = filtroNoAtencionEnviadas;
    }

    public int getFiltroNoAtencionEnviadas() {
        return _filtroNoAtencionEnviadas;
    }

    public void setIntervaloSincronizacionAutomatica(int intervalo) {
        this._intervaloSincronizacionAutomatica = intervalo;
    }

    public int getIntervaloSincronizacionAutomatica() {
        return _intervaloSincronizacionAutomatica;
    }

    public void setFiltroLocalidadCliente(int idLocalidad) {
        this._filtroLocalidadCliente = idLocalidad;
    }

    public int getFiltroLocalidadCliente() {
        return _filtroLocalidadCliente;
    }

    public void setIdTipoDocumentoPorDefecto(String idTipoDocumentoPorDefecto) {
        this._idTipoDocumentoPorDefecto = idTipoDocumentoPorDefecto;
    }

    public String getIdTipoDocumentoPorDefecto() {
        return _idTipoDocumentoPorDefecto;
    }

    public void setUrlFtp(String urlFtp) {
        this._urlFtp = (urlFtp != null) ? urlFtp.trim() : "vgmsistemas.no-ip.biz";
    }

    public String getUrlFtp() {
        return _urlFtp;
    }

    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = (ftpUserName != null) ? ftpUserName.trim() : "";
    }

    public String getFtpUserName() {
        return ftpUserName;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public static String getPathSistemaPreferido() {
        return pathSistemaPreferido;
    }

    public static void setPathSistemaPreferido(String _pathSistemaPreferido) {
        pathSistemaPreferido = _pathSistemaPreferido;
    }

    public void setIntervaloEnvioLogs(int intervaloEnvioLogs) {
        this._intervaloEnvioLogs = intervaloEnvioLogs;
    }

    public int getIntervaloEnvioLogs() {
        return _intervaloEnvioLogs;
    }

    public void setIntervaloActualizacionApk(int intervaloActualizacionApk) {
        this._intervaloActualizacionApk = intervaloActualizacionApk;
    }

    public int getIntervaloActualizacionApk() {
        return _intervaloActualizacionApk;
    }

    public void setVersion(int version) {
        this._version = version;
    }

    public int getVersion() {
        return _version;
    }

    public Date getFechaUltimaDescarga() {
        return this.fechaUltimaDescarga;
    }

    public void setFechaUltimaDescarga(Date fechaUltimaSincronizacion) {
        this.fechaUltimaDescarga = fechaUltimaSincronizacion;
    }

    public String getFechaLimiteActualizacion() {
        return fechaLimiteActualizacion;
    }

    public void setFechaLimiteActualizacion(String fechaLimiteActualizacion) {
        this.fechaLimiteActualizacion = fechaLimiteActualizacion;
    }

    public void setIdRepartidorPorDefecto(int idRepartidorPorDefecto) {
        _idRepartidorPorDefecto = idRepartidorPorDefecto;
    }

    public int getIdRepartidorPorDefecto() {
        return _idRepartidorPorDefecto;
    }

    public void setIdPuntoVentaPorDefecto(int idPuntoVentaPorDefecto) {
        this._idPuntoVentaPorDefecto = idPuntoVentaPorDefecto;
    }

    public int getIdPuntoVentaPorDefecto() {
        return _idPuntoVentaPorDefecto;
    }

    public String getUrlLocalServidor() {
        return this._urlLocalServidor;
    }

    public void setUrlLocalServidor(String urlLocalServidor) {
        this._urlLocalServidor = (urlLocalServidor != null) ? urlLocalServidor.trim() : "";
    }

    public void setSincronizacionLocal(boolean sincronizacionLocal) {
        this._sincronizacionLocal = sincronizacionLocal;
    }

    public boolean isSincronizacionLocal() {
        return this._sincronizacionLocal;
    }

    public void setFtpFolder(String ftpFolder) {
        this.ftpFolder = (ftpFolder != null) ? ftpFolder.trim() : "/vgmgema";
    }

    public String getFtpFolder() {
        return ftpFolder;
    }

    public static void setTextoFiltroArticulo(String textoFiltroArticulo) {
        _textoFiltroArticulo = textoFiltroArticulo;
    }

    public static String getTextoFiltroArticulo() {
        return _textoFiltroArticulo;
    }

    public static void setUltimoArticuloSeleccionado(Articulo ultimoArticuloSeleccionado) {
        _ultimoArticuloSeleccionado = ultimoArticuloSeleccionado;
    }

    public static Articulo getUltimoArticuloSeleccionado() {
        return _ultimoArticuloSeleccionado;
    }

    /**
     * @return the urlRemotaServidor2
     */
    public String getUrlRemotaServidor2() {
        return this._urlRemotaServidor2;
    }

    /**
     * @param urlRemotaServidor2 the urlRemotaServidor2 to set
     */
    public void setUrlRemotaServidor2(String urlRemotaServidor2) {
        this._urlRemotaServidor2 = (urlRemotaServidor2 != null) ? urlRemotaServidor2.trim() : "urlRemotaServidor2";
    }

    /**
     * @return the urlServidorActiva
     */
    public String getUrlServidorActiva() {
        return this._urlServidorActiva;
    }

    /**
     * @param urlServidorActiva the urlServidorActiva to set
     */
    public void setUrlServidorActiva(String urlServidorActiva) {
        this._urlServidorActiva = urlServidorActiva;
    }

    /**
     * @return the idVendedor
     */
    public int getIdVendedor() {
        return idVendedor;
    }

    /**
     * @param idVendedor the idVendedor to set
     */
    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    /**
     * @param tipoDatoIdArticulo the tipoDatoIdArticulo to set
     */
    public void setTipoDatoIdArticulo(String tipoDatoIdArticulo) {
        this.tipoDatoIdArticulo = tipoDatoIdArticulo;
    }

    /**
     * @return the tipoDatoIdArticulo
     */
    public String getTipoDatoIdArticulo() {
        return tipoDatoIdArticulo;
    }

    public void setIntervaloBorradoBck(int _intervaloBorradoBck) {
        Preferencia._intervaloBorradoBck = _intervaloBorradoBck;
    }

    public static int getIntervaloBorradoBck() {
        return Preferencia._intervaloBorradoBck;
    }

    public int getTimeOutAlto() {
        return timeOutAlto;
    }

    public void setTimeOutAlto(int timeOutAlto) {
        this.timeOutAlto = timeOutAlto;
    }

    public int getTimeOutMedio() {
        return timeOutMedio;
    }

    public void setTimeOutMedio(int timeOutMedio) {
        this.timeOutMedio = timeOutMedio;
    }

    public int getTimeOutBajo() {
        return timeOutBajo;
    }

    public void setTimeOutBajo(int timeOutBajo) {
        this.timeOutBajo = timeOutBajo;
    }

    // ENVIO DE PEDIDOS POR FRANJA HORARIA
    public boolean getIsEnvioPedidosPorFranja() {
        return isEnvioPedidosPorFranja;
    }

    public void setIsEnvioPedidosPorFranja(boolean isEnvioPedidosPorFranja) {
        this.isEnvioPedidosPorFranja = isEnvioPedidosPorFranja;
    }

    public int getHoraInicioEnvio() {
        return horaInicioEnvio;
    }

    public void setHoraInicioEnvio(int horaInicioEnvio) {
        this.horaInicioEnvio = horaInicioEnvio;
    }

    public int getMinutosInicioEnvio() {
        return minutosInicioEnvio;
    }

    public void setMinutosInicioEnvio(int minutosInicioEnvio) {
        this.minutosInicioEnvio = minutosInicioEnvio;
    }

    public int getHoraFinEnvio() {
        return horaFinEnvio;
    }

    public void setHoraFinEnvio(int horaFinEnvio) {
        this.horaFinEnvio = horaFinEnvio;
    }

    public int getMinutosFinEnvio() {
        return minutosFinEnvio;
    }

    public void setMinutosFinEnvio(int minutosFinEnvio) {
        this.minutosFinEnvio = minutosFinEnvio;
    }

    public static int getBusquedaRecordadaSubrubro() {
        return _busquedaRecordadaSubrubro;
    }

    public static void setBusquedaRecordadaSubrubro(int _busquedaRecordadaSubrubro) {
        Preferencia._busquedaRecordadaSubrubro = _busquedaRecordadaSubrubro;
    }

    public static int getListaPrecioRecordada() {
        return _listaPrecioRecordada;
    }

    public static void setListaPrecioRecordada(int _listaPrecioRecordada) {
        Preferencia._listaPrecioRecordada = _listaPrecioRecordada;
    }

    public static int getUltimaPosicionArticuloLista() {
        return _ultimaPosicionArticuloLista;
    }

    public static void setUltimaPosicionArticuloLista(int _ultimaPosicionArticuloLista) {
        Preferencia._ultimaPosicionArticuloLista = _ultimaPosicionArticuloLista;
    }

    public long getDistanciaMinimaLocalizacion() {
        return DISTANCIA_MINIMA_ENTRE_LOCALIZACION;
    }

    public void setDistanciaMinimaLocalizacion(long distanciaMinimaEntreLocalizacion) {
        this.DISTANCIA_MINIMA_ENTRE_LOCALIZACION = distanciaMinimaEntreLocalizacion;
    }

    public long getIntervaloTiempoLocalizacion() {
        return INTERVALO_DE_TIEMPO_ENTRE_LOCALIZACION;
    }

    public void setIntervaloTiempoLocalizacion(long intervaloDeTiempoEntreLocalizacion) {
        this.INTERVALO_DE_TIEMPO_ENTRE_LOCALIZACION = intervaloDeTiempoEntreLocalizacion;
    }

    public String getUltimaLatitud() {
        return ultimaLatitud;
    }

    public void setUltimaLatitud(String ultimaLatitud) {
        this.ultimaLatitud = ultimaLatitud;
    }

    public String getUltimaLongitud() {
        return ultimaLongitud;
    }

    public void setUltimaLongitud(String ultimaLongitud) {
        this.ultimaLongitud = ultimaLongitud;
    }

    public boolean isSugerenciaSincronizarInicio() {
        return sugerenciaSincronizarInicio;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isRecordarContrasena() {
        return recordarContrasena;
    }

    public void setRecordarContrasena(boolean recordarContrasena) {
        this.recordarContrasena = recordarContrasena;
    }

    public void setSugerenciaSincronizarInicio(boolean sugerenciaSincronizarInicio) {
        this.sugerenciaSincronizarInicio = sugerenciaSincronizarInicio;
    }

    public String getIsCobranzaEstricta() {
        return isCobranzaEstricta;
    }

    public void setIsCobranzaEstricta(String isCobranzaEstricta) {
        this.isCobranzaEstricta = isCobranzaEstricta;
    }

    public String getIsControlLimiteDisponibilidad() {
        return isControlLimiteDisponibilidad;
    }

    public void setIsControlLimiteDisponibilidad(String isControlLimiteDisponibilidad) {
        this.isControlLimiteDisponibilidad = isControlLimiteDisponibilidad;
    }

    public String getIsDescuento() {
        return isDescuento;
    }

    public void setIsDescuento(String isDescuento) {
        this.isDescuento = isDescuento;
    }

    public String getIsRegistrarLocalizacion() {
        return isRegistrarLocalizacion;
    }

    public void setIsRegistrarLocalizacion(String isRegistrarLocalizacion) {
        this.isRegistrarLocalizacion = isRegistrarLocalizacion;
    }

    public double getPorcentajeArticulosCriticos() {
        return porcentajeArticulosCriticos;
    }

    public void setPorcentajeArticulosCriticos(double porcentajeArticulosCriticos) {
        this.porcentajeArticulosCriticos = porcentajeArticulosCriticos;
    }

    public double getMontoMinimoFactura() {
        return montoMinimoFactura;
    }

    public void setMontoMinimoFactura(double montoMinimoFactura) {
        this.montoMinimoFactura = montoMinimoFactura;
    }

    public double getMontoMinimoDescuentoFactura() {
        return montoMinimoDescuentoFactura;
    }

    public void setMontoMinimoDescuentoFactura(double montoMinimoDescuentoFactura) {
        this.montoMinimoDescuentoFactura = montoMinimoDescuentoFactura;
    }

    public int getIdPuntoVentaUltimoRecibo() {
        return _idPuntoVentaUltimoRecibo;
    }

    public void setIdPuntoVentaUltimoRecibo(int _idPuntoVentaUltimoRecibo) {
        this._idPuntoVentaUltimoRecibo = _idPuntoVentaUltimoRecibo;
    }

    public long getIdUltimoRecibo() {
        return _idUltimoRecibo;
    }

    public void setIdUltimoRecibo(long _idUltimoRecibo) {
        this._idUltimoRecibo = _idUltimoRecibo;
    }

    public Date getFechaUltimaUbicacion() {
        return fechaUltimaUbicacion;
    }

    public void setFechaUltimaUbicacion(Date fechaUltimaUbicacion) {
        this.fechaUltimaUbicacion = fechaUltimaUbicacion;
    }

    public Date getHoraEntradaManana() {
        return horaEntradaManana;
    }

    public void setHoraEntradaManana(Date horaEntradaManana) {
        this.horaEntradaManana = horaEntradaManana;
    }

    public Date getHoraSalidaManana() {
        return horaSalidaManana;
    }

    public void setHoraSalidaManana(Date horaSalidaManana) {
        this.horaSalidaManana = horaSalidaManana;
    }

    public Date getHoraEntradaTarde() {
        return horaEntradaTarde;
    }

    public void setHoraEntradaTarde(Date horaEntradaTarde) {
        this.horaEntradaTarde = horaEntradaTarde;
    }

    public Date getHoraSalidaTarde() {
        return horaSalidaTarde;
    }

    public void setHoraSalidaTarde(Date horaSalidaTarde) {
        this.horaSalidaTarde = horaSalidaTarde;
    }

    public int getSegundosTolerancia() {
        return segundosTolerancia;
    }

    public void setSegundosTolerancia(int segundosTolerancia) {
        this.segundosTolerancia = segundosTolerancia;
    }

    public String getFtpLogFolder() {
        return ftpLogFolder;
    }

    public void setFtpLogFolder(String ftpLogFolder) {
        this.ftpLogFolder = ftpLogFolder;
    }

    public String getFtpApkFolder() {
        return ftpApkFolder;
    }

    public void setFtpApkFolder(String ftpApkFolder) {
        this.ftpApkFolder = ftpApkFolder;
    }

    public boolean isEnvioReciboAutomatico() {
        return _envioReciboAutomatico;
    }

    public void setEnvioReciboAutomatico(boolean envioReciboAutomatico) {
        this._envioReciboAutomatico = envioReciboAutomatico;
    }

    public int getFiltroRecibosEnviados() {
        return _filtroRecibosEnviados;
    }

    public void setFiltroRecibosEnviados(int filtroRecibosEnviados) {
        this._filtroRecibosEnviados = filtroRecibosEnviados;
    }

    public int getFiltroEgresosEnviados() {
        return _filtroEgresosEnviados;
    }

    public void setFiltroEgresosEnviados(int _filtroEgresosEnviados) {
        this._filtroEgresosEnviados = _filtroEgresosEnviados;
    }

    public int getIdCategoria() {
        return idCategoria;
        // return CategoriaRecursoHumano.REPARTIDOR;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public static int getBorradoBckXCantidad() {
        return _borradoBckXCantidad;
    }

    public void setBorradoBckXCantidad(int _borradoBckXCantidad) {
        this._borradoBckXCantidad = _borradoBckXCantidad;
    }

    public static int getBorradoBckupPreferido() {
        return borradoBckupPreferido;
    }

    public void setBorradoBckupPreferido(int borradoBckupPreferido) {
        this.borradoBckupPreferido = borradoBckupPreferido;
    }

    public int getLugarAlmacenamientoBD() {
        return lugarAlmacenamientoBDPreferido;
    }

    public void setLugarAlmacenamientoBD(int lugarAlmacenamientoBD) {
        Preferencia.lugarAlmacenamientoBDPreferido = lugarAlmacenamientoBD;
    }

    public int getHomeNavigationByPreferencia() {
        return homeNavigationByPreferencia;
    }

    public void setHomeNavigationByPreferencia(int homeNavigationByPreferencia) {
        this.homeNavigationByPreferencia = homeNavigationByPreferencia;
    }

    public int getOrdenPreferidoCCCliente() {
        return _ordenPreferidoCCCliente;

    }

    public void setOrdenPreferidoCCCliente(int _ordenPreferidoCCCliente) {

        this._ordenPreferidoCCCliente = _ordenPreferidoCCCliente;

    }

    public String getNombreEmpresa() {
        if (nombreEmpresa == null)
            return "";
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getIdTipoDocumentoRecibo() {
        return idTipoDocumentoRecibo;
    }

    public int getIdPuntoVentaPorDefectoRecibo() {
        return idPuntoVentaPorDefectoRecibo;
    }

    public void setIdPuntoVentaPorDefectoRecibo(int idPuntoVentaPorDefectoRecibo) {
        this.idPuntoVentaPorDefectoRecibo = idPuntoVentaPorDefectoRecibo;
    }

    public void setIdTipoDocumentoRecibo(String idTipoDocumentoRecibo) {
        this.idTipoDocumentoRecibo = idTipoDocumentoRecibo;
    }

    public String getSnMovilReciboDto() {
        return snMovilReciboDto;
    }

    public void setSnMovilReciboDto(String snMovilReciboDto) {
        this.snMovilReciboDto = snMovilReciboDto;
    }

    public int getFiltroSucursalCliente() {
        return filtroSucursalCliente;
    }

    public void setFiltroSucursalCliente(int filtroSucursalCliente) {
        this.filtroSucursalCliente = filtroSucursalCliente;
    }

    public boolean isEnvioHojaDetalleAutomatico() {
        return _envioHojaDetalleAutomatico;
    }

    public void setEnvioHojaDetalleAutomatico(boolean _envioHojaDetalleAutomatico) {
        this._envioHojaDetalleAutomatico = _envioHojaDetalleAutomatico;
    }

    public boolean isEnvioEgresoAutomatico() {
        return _envioEgresoAutomatico;
    }

    public void setEnvioEgresoAutomatico(boolean _envioEgresoAutomatico) {
        this._envioEgresoAutomatico = _envioEgresoAutomatico;
    }

    public boolean isEnvioEntregaAutomatico() {
        return _envioEntregaAutomatico;
    }

    public void setEnvioEntregaAutomatico(boolean _envioEntregaAutomatico) {
        this._envioEntregaAutomatico = _envioEntregaAutomatico;
    }

    public int getCategoriasEmpRepartidor() {
        return categoriasEmpRepartidor;
    }

    public void setCategoriasEmpRepartidor(int categoriasEmpRepartidor) {
        this.categoriasEmpRepartidor = categoriasEmpRepartidor;
    }

    public int getTimeOutToken() {
        return timeOutToken;
    }

    public void setTimeOutToken(int timeOutToken) {
        this.timeOutToken = timeOutToken;
    }

    public static String getPathImages() {
        return pathImages;
    }

    public static void setPathImages(String pathImages) {
        Preferencia.pathImages = pathImages;
    }

    public boolean isCambiarPtoVta() {
        return _cambiarPtoVta;
    }

    public void setCambiarPtoVta(boolean _cambiarPtoVta) {
        this._cambiarPtoVta = _cambiarPtoVta;
    }

    public String getSnControlaReciboBn() {
        return snControlaReciboBn;
    }

    public void setSnControlaReciboBn(String snControlaReciboBn) {
        this.snControlaReciboBn = snControlaReciboBn;
    }
}
