package com.ar.vgmsistemas.bo;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.repository.filesystem.PreferenciaRepositoryImpl;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.PermissionUtils;

public class PreferenciaBo {
    private static final String KEY_VENDEDOR_ESTATE = "isVendedorValido";
    private static final String KEY_IS_MULTI_EMPRESA = "isMultiEmpresa";
    private static final String KEY_TIPO_EMPRESA = "tipoEmpresa";
    private static final String KEY_IS_MANEJO_TURNO = "isManejoTurno";
    private static final String KEY_ID_CATEGORIA = "key_id_categoria";
    private static final String KEY_ORDEN_PREFERIDO_CLIENTE = "ordenPreferidoCliente";
    private static final String KEY_ORDEN_PREFERIDO_ARTICULO = "ordenPreferidoArticulo";
    private static final String KEY_ORDEN_PREFERIDO_NO_PEDIDO = "ordenPreferidoNoPedido";
    private static final String KEY_IS_CONTROL_LIMITE_DISP = "isControlLimiteDisp";
    private static final String KEY_IS_RECORDAR_CONTRASENIA = "isRecordarContrasenia";
    private static final String KEY_CONTRASENIA = "keyContrasenia";
    private static final String KEY_ORDEN_PREFERIDO_CCCLIENTE = "ordenPreferidoCCCliente";
    private static final String KEY_NOMBRE_EMPRESA = "nombreEmpresa";
    private static final String KEY_IS_RECIBO_PROVISORIO = "isReciboProvisorio";
    private static final String KEY_ID_PTO_VTA_RECIBO = "idPtoVtaRecibo";
    private static final String KEY_ID_DOC_RECIBO = "idRecibo";
    private static final String KEY_SN_MOVIL_RECIBO_DTO = "snMovilReciboDto";
    private static final String KEY_CATEGORIAS_EMPLEADOS_REPARTIDOR = "categoriasEmpRepartidor";
    private static final String KEY_FECHA_LIMITE_ACTUALIZACION = "fechaLimiteAct";
    private static final String KEY_VERSION = "version";
    private static final String KEY_PATH_PREFERIDO = "pathSistemaPreferido";
    private static final String KEY_LUGAR_PREFERIDO = "lugarAlmacenamPreferido";

    private static final String KEY_ULTIMO_BACKUP = "ultimoBackup";

    private static Preferencia preferencia;
    private static final String ARCHIVO_PREFERENCIA = "vgmgema";
    SharedPreferences preferences;
    private static final PreferenciaBo instance = new PreferenciaBo();

    PreferenciaBo() {

    }

    public static final PreferenciaBo getInstance() {
        return instance;
    }

    public Preferencia getPreferencia(Context context) {
        if (preferencia == null) {
            loadPreference(context);
        }
        return preferencia;
    }

    public Preferencia getPreferencia() {
        return preferencia;
    }

    public void loadPreferenciaFromFile() {
        PreferenciaRepositoryImpl preferenciaDao = new PreferenciaRepositoryImpl();
        try {
            preferencia = preferenciaDao.loadPreferencias(Preferencia.getPathSistema());
            preferencia.setUrlServidorActiva(preferencia.getUrlRemotaServidor());
        } catch (Exception e) {
        }
    }

    public void savePreferencia(Context context) {
        if (preferencia != null) {
            if (PermissionUtils.checkPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Tiene el permiso
                PreferenciaRepositoryImpl preferenciaDao = new PreferenciaRepositoryImpl();
                preferenciaDao.savePreferencias(preferencia);
            } else {
                // No tiene el permiso
                Toast.makeText(context, "No hay permiso para guardar configuración", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void saveConfiguracionesConexionPreferencia(Context context, Preferencia preferencia) throws Exception {
        PreferenciaBo.getInstance().getPreferencia(context.getApplicationContext())
                .setUrlRemotaServidor(preferencia.getUrlRemotaServidor());
        PreferenciaBo.getInstance().getPreferencia(context.getApplicationContext())
                .setUrlRemotaServidor2(preferencia.getUrlRemotaServidor2());
        PreferenciaBo.getInstance().getPreferencia(context.getApplicationContext())
                .setUrlLocalServidor(preferencia.getUrlLocalServidor());
        if (preferencia.getIdTipoDocumentoPorDefecto() != null &&
                !preferencia.getIdTipoDocumentoPorDefecto().equals("")) {
            PreferenciaBo.getInstance().getPreferencia(context.getApplicationContext())
                    .setIdTipoDocumentoPorDefecto(preferencia.getIdTipoDocumentoPorDefecto());
            PreferenciaBo.getInstance().getPreferencia(context.getApplicationContext())
                    .setIdPuntoVentaPorDefecto(preferencia.getIdPuntoVentaPorDefecto());
            PreferenciaBo.getInstance().getPreferencia(context.getApplicationContext())
                    .setIdRepartidorPorDefecto(preferencia.getIdRepartidorPorDefecto());
            PreferenciaBo.getInstance().getPreferencia(context.getApplicationContext())
                    .setTipoDatoIdArticulo(preferencia.getTipoDatoIdArticulo());
        }
        PreferenciaBo.getInstance().savePreference(context.getApplicationContext());
    }

    public void loadPreference(Context context) {
        //preferences = context.getSharedPreferences(ARCHIVO_PREFERENCIA, Activity.MODE_PRIVATE);
        preferences = context.getSharedPreferences(ARCHIVO_PREFERENCIA, Context.MODE_PRIVATE);
        preferencia = new Preferencia();
        String adminPassword = preferences.getString("adminPassword", preferencia.getAdminPassword());
        preferencia.setAdminPassword(adminPassword);
        String pathSistemaGuardado = preferencia.getPathSistemaPreferido();
        String pathSistemaPreferido;
        if (pathSistemaGuardado == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Preferencia.setPathSistemaPreferido(context.getExternalFilesDir(null) + "");
            } else {
                Preferencia.setPathSistemaPreferido(Environment.getExternalStorageDirectory() + "");
            }
        }
        pathSistemaPreferido = preferences.getString(KEY_PATH_PREFERIDO, preferencia.getPathSistemaPreferido());
        preferencia.setPathSistemaPreferido(pathSistemaPreferido);
        preferencia.setLugarAlmacenamientoBD(preferences.getInt(KEY_LUGAR_PREFERIDO, preferencia.getLugarAlmacenamientoBD()));
        String nombreEmpresa = preferences.getString(KEY_NOMBRE_EMPRESA, preferencia.getNombreEmpresa());
        preferencia.setNombreEmpresa(nombreEmpresa);
        String adminUser = preferences.getString("adminUser", preferencia.getAdminUser());
        preferencia.setAdminUser(adminUser);
        int busquedaPreferidaArticulo = preferences.getInt("busquedaPreferidaArticulo",
                preferencia.getBusquedaPreferidaArticulo());
        preferencia.setBusquedaPreferidaArticulo(busquedaPreferidaArticulo);
        int ordenPreferidoNoPedido = preferences.getInt(KEY_ORDEN_PREFERIDO_NO_PEDIDO, preferencia.getOrdenPreferidoNoPedido());
        preferencia.setOrdenPreferidoNoPedido(ordenPreferidoNoPedido);
        int ordenPreferidoArticulo = preferences.getInt(KEY_ORDEN_PREFERIDO_ARTICULO, preferencia.getOrdenPreferidoArticulo());
        preferencia.setOrdenPreferidoArticulo(ordenPreferidoArticulo);
        int busquedaPreferidaCliente = preferences.getInt("busquedaPreferidaCliente", preferencia.getBusquedaPreferidaCliente());
        preferencia.setBusquedaPreferidaCliente(busquedaPreferidaCliente);
        int ordenPreferidoCCCliente = preferences.getInt(KEY_ORDEN_PREFERIDO_CCCLIENTE, preferencia.getOrdenPreferidoCCCliente());
        preferencia.setOrdenPreferidoCCCliente(ordenPreferidoCCCliente);
        boolean descargaStockAutomatico = preferences.getBoolean("descargaStockAutomatico",
                preferencia.isDescargaStockAutomatico());
        preferencia.setDescargaStockAutomatico(descargaStockAutomatico);
        boolean envioPedidoAutomatico = preferences.getBoolean("envioPedidoAutomatico", preferencia.isEnvioPedidoAutomatico());
        preferencia.setEnvioPedidoAutomatico(envioPedidoAutomatico);
        boolean envioHojaDetalleAutomatico = preferences.getBoolean("envioHojaDetalleAutomatico", preferencia.isEnvioHojaDetalleAutomatico());
        preferencia.setEnvioHojaDetalleAutomatico(envioHojaDetalleAutomatico);

        boolean envioEgresoAutomatico = preferences.getBoolean("envioEgresoAutomatico", preferencia.isEnvioEgresoAutomatico());
        preferencia.setEnvioEgresoAutomatico(envioEgresoAutomatico);
        boolean envioEntregaAutomatico = preferences.getBoolean("envioEntregaAutomatico", preferencia.isEnvioEntregaAutomatico());
        preferencia.setEnvioEntregaAutomatico(envioEntregaAutomatico);

        boolean isBusquedaPorListaArticulo = preferences.getBoolean("busquedaPorListaArticulo",
                preferencia.isBusquedaPorListaArticulo());
        preferencia.setBusquedaPorListaArticulo(isBusquedaPorListaArticulo);

        String fechaUltimaDescarga = preferences.getString("fechaUltimaSincronizacion",
                Formatter.formatDateTime(preferencia.getFechaUltimaDescarga()));
        try {
            preferencia.setFechaUltimaDescarga(Formatter.convertToDateTime(fechaUltimaDescarga));
        } catch (Exception ex) {
        }

        String fechaLimiteActualizacion = preferences.getString(KEY_FECHA_LIMITE_ACTUALIZACION,
                preferencia.getFechaLimiteActualizacion());
        preferencia.setFechaLimiteActualizacion(fechaLimiteActualizacion);


        String ftpFolder = preferences.getString("ftpFolder", preferencia.getFtpFolder());
        preferencia.setFtpFolder(ftpFolder);
        String ftpPassword = preferences.getString("ftpPassword", preferencia.getFtpPassword());
        preferencia.setFtpPassword(ftpPassword);
        String ftpUserName = preferences.getString("ftpUserName", preferencia.getFtpUserName());
        preferencia.setFtpUserName(ftpUserName);
        int idPuntoVentaPorDefecto = preferences.getInt("idPuntoVentaPorDefecto", preferencia.getIdPuntoVentaPorDefecto());
        preferencia.setIdPuntoVentaPorDefecto(idPuntoVentaPorDefecto);
        String idTipoDocumentoPorDefecto = preferences.getString("idTipoDocumentoPorDefecto",
                preferencia.getIdTipoDocumentoPorDefecto());
        preferencia.setIdTipoDocumentoPorDefecto(idTipoDocumentoPorDefecto);
        int idPuntoVentaRecibo = preferences.getInt(KEY_ID_PTO_VTA_RECIBO, preferencia.getIdPuntoVentaPorDefectoRecibo());
        preferencia.setIdPuntoVentaPorDefectoRecibo(idPuntoVentaRecibo);
        String idTipoDocumentoRecibo = preferences.getString(KEY_ID_DOC_RECIBO, preferencia.getIdTipoDocumentoRecibo());
        preferencia.setIdTipoDocumentoRecibo(idTipoDocumentoRecibo);

        int intervaloActualizacionApk = preferences.getInt("intervaloActualizacionApk",
                preferencia.getIntervaloActualizacionApk());
        preferencia.setIntervaloActualizacionApk(intervaloActualizacionApk);
        int intervaloEnvioLogs = preferences.getInt("intervaloEnvioLogs", preferencia.getIntervaloEnvioLogs());
        preferencia.setIntervaloEnvioLogs(intervaloEnvioLogs);
        int intervaloSincronizacionAutomatica = preferences.getInt("intervaloSincronizacionAutomatica",
                preferencia.getIntervaloSincronizacionAutomatica());
        preferencia.setIntervaloSincronizacionAutomatica(intervaloSincronizacionAutomatica);
        int intervaloBorradoBck = preferences.getInt("intervaloBorradoBck", Preferencia.getIntervaloBorradoBck());
        preferencia.setIntervaloBorradoBck(intervaloBorradoBck);
        int borradoBckXCantidad = preferences.getInt("borradoBckXCantidad", Preferencia.getIntervaloBorradoBck());
        preferencia.setBorradoBckXCantidad(borradoBckXCantidad);

        String urlFtp = preferences.getString("urlFtp", preferencia.getUrlFtp());
        preferencia.setUrlFtp(urlFtp);
        String urlLocalServidor = preferences.getString("urlLocalServidor", preferencia.getUrlLocalServidor());
        preferencia.setUrlLocalServidor(urlLocalServidor);
        String urlRemotaServidor = preferences.getString("urlRemotaServidor", preferencia.getUrlRemotaServidor());
        preferencia.setUrlRemotaServidor(urlRemotaServidor);
        String urlRemotaServidor2 = preferences.getString("urlRemotaServidor2", preferencia.getUrlRemotaServidor2());
        preferencia.setUrlRemotaServidor2(urlRemotaServidor2);
        int version = 0;
        try {
            version = preferences.getInt(KEY_VERSION, preferencia.getVersion());
        } catch (ClassCastException c) {
        }
        preferencia.setVersion(version);
        int idRepartidorPorDefecto = preferences.getInt("idRepartidorPorDefecto", preferencia.getIdRepartidorPorDefecto());
        preferencia.setIdRepartidorPorDefecto(idRepartidorPorDefecto);
        preferencia.setUrlServidorActiva(preferencia.getUrlRemotaServidor());
        String formatoFecha = preferences.getString("formatoFecha", preferencia.getFormatoFecha());
        preferencia.setFormatoFecha(formatoFecha);
        int idVendedor = preferences.getInt("idVendedor", preferencia.getIdVendedor());
        preferencia.setIdVendedor(idVendedor);
        String tipoDatoIdArticulo = preferences.getString("tipoDatoIdArticulo", preferencia.getTipoDatoIdArticulo());
        preferencia.setTipoDatoIdArticulo(tipoDatoIdArticulo);

        int timeOutAlto = preferences.getInt("timeOutAlto", preferencia.getTimeOutAlto());
        preferencia.setTimeOutAlto(timeOutAlto);
        int timeOutMedio = preferences.getInt("timeOutMedio", preferencia.getTimeOutMedio());
        preferencia.setTimeOutMedio(timeOutMedio);
        int timeOutBajo = preferences.getInt("timeOutBajo", preferencia.getTimeOutBajo());
        preferencia.setTimeOutBajo(timeOutBajo);

        // Envio de Pedidos por Franja Horaria
        boolean isEnvioPorFranjaHoraria = preferences.getBoolean("isEnvioPedidosPorFranja",
                preferencia.getIsEnvioPedidosPorFranja());
        preferencia.setIsEnvioPedidosPorFranja(isEnvioPorFranjaHoraria);
        int horaInicioEnvio = preferences.getInt("horaInicioEnvio", preferencia.getHoraInicioEnvio());
        preferencia.setHoraInicioEnvio(horaInicioEnvio);
        int minutosInicioEnvio = preferences.getInt("minutosInicioEnvio", preferencia.getMinutosInicioEnvio());
        preferencia.setMinutosInicioEnvio(minutosInicioEnvio);
        int horaFinEnvio = preferences.getInt("horaFinEnvio", preferencia.getHoraFinEnvio());
        preferencia.setHoraFinEnvio(horaFinEnvio);
        int minutosFinEnvio = preferences.getInt("minutosFinEnvio", preferencia.getMinutosFinEnvio());
        preferencia.setMinutosFinEnvio(minutosFinEnvio);
        long distanciaMinimaEntreLocalizacion = preferences.getLong("distanciaMinimaEntreLocalizacion",
                preferencia.getDistanciaMinimaLocalizacion());
        preferencia.setDistanciaMinimaLocalizacion(distanciaMinimaEntreLocalizacion);
        long intervaloTiempoEntreLocalizacion = preferences.getLong("intervaloTiempoEntreLocalizaciones",
                preferencia.getIntervaloTiempoLocalizacion());
        preferencia.setIntervaloTiempoLocalizacion(intervaloTiempoEntreLocalizacion);
        int ordenPreferidoCliente = preferences.getInt(KEY_ORDEN_PREFERIDO_CLIENTE, preferencia.getOrdenPreferidoCliente());
        preferencia.setOrdenPreferidoCliente(ordenPreferidoCliente);

        // Envío de Localizaciones
        String horaEntradaManana = preferences.getString("horaEntradaManana",
                Formatter.formatDateTime(preferencia.getHoraEntradaManana()));
        try {
            preferencia.setHoraEntradaManana(Formatter.convertToDateTime(horaEntradaManana));
        } catch (Exception ex) {
        }

        String horaSalidaManana = preferences.getString("horaSalidaManana",
                Formatter.formatDateTime(preferencia.getHoraSalidaManana()));
        try {
            preferencia.setHoraSalidaManana(Formatter.convertToDateTime(horaSalidaManana));
        } catch (Exception ex) {
        }

        String horaEntradaTarde = preferences.getString("horaEntradaTarde",
                Formatter.formatDateTime(preferencia.getHoraEntradaTarde()));
        try {
            preferencia.setHoraEntradaTarde(Formatter.convertToDateTime(horaEntradaTarde));
        } catch (Exception ex) {
        }

        String horaSalidaTarde = preferences.getString("horaSalidaTarde",
                Formatter.formatDateTime(preferencia.getHoraSalidaTarde()));
        try {
            preferencia.setHoraSalidaTarde(Formatter.convertToDateTime(horaSalidaTarde));
        } catch (Exception ex) {
        }

        // Ultima ubicacion registrada
        String ultimaLatitud = preferences.getString("ultimaLatitud", preferencia.getUltimaLatitud());
        preferencia.setUltimaLatitud(ultimaLatitud);
        String ultimaLongitud = preferences.getString("ultimaLongitud", preferencia.getUltimaLongitud());
        preferencia.setUltimaLongitud(ultimaLongitud);

        String isControlLimiteDisp = preferences.getString(KEY_IS_CONTROL_LIMITE_DISP,
                preferencia.getIsControlLimiteDisponibilidad());
        preferencia.setIsControlLimiteDisponibilidad(isControlLimiteDisp);
        String fechaUltimaUbicacion = preferences.getString("fechaUltimaUbicacion",
                Formatter.formatDateTime(preferencia.getFechaUltimaUbicacion()));
        try {
            preferencia.setFechaUltimaUbicacion(Formatter.convertToDateTime(fechaUltimaUbicacion));
        } catch (Exception ex) {
        }

        boolean sugerenciaSincronizacion = preferences.getBoolean("sugerenciaSincronizarInicio",
                preferencia.isSugerenciaSincronizarInicio());
        preferencia.setSugerenciaSincronizarInicio(sugerenciaSincronizacion);
        boolean isRecordarContrasenia = preferences.getBoolean(KEY_IS_RECORDAR_CONTRASENIA, preferencia.isRecordarContrasena());
        preferencia.setRecordarContrasena(isRecordarContrasenia);
        String contrasenia = preferences.getString(KEY_CONTRASENIA, preferencia.getContrasena());
        preferencia.setContrasena(contrasenia);
        // Configuraciones Empresa - Tarea #3036
        String isCobranzaEstricta = preferences.getString("isCobranzaEstricta", preferencia.getIsCobranzaEstricta());
        preferencia.setIsCobranzaEstricta(isCobranzaEstricta);
        String isDescuento = preferences.getString("isDescuento", preferencia.getIsDescuento());
        preferencia.setIsDescuento(isDescuento);
        String isRegistrarLocalizacion = preferences.getString("isRegistrarLocalizacion",
                preferencia.getIsRegistrarLocalizacion());
        preferencia.setIsRegistrarLocalizacion(isRegistrarLocalizacion);
        double porcentajeArticulosCriticos = preferences.getFloat("porcentajeArticulosCriticos",
                (float) preferencia.getPorcentajeArticulosCriticos());
        preferencia.setPorcentajeArticulosCriticos(porcentajeArticulosCriticos);
        double montoMinimoFactura = preferences.getFloat("montoMinimoFactura",
                (float) preferencia.getMontoMinimoFactura());
        preferencia.setMontoMinimoFactura(montoMinimoFactura);
        double montoMinimoDescuentoFactura = preferences.getFloat("montoMinimoDescuentoFactura",
                (float) preferencia.getMontoMinimoDescuentoFactura());
        preferencia.setMontoMinimoDescuentoFactura(montoMinimoDescuentoFactura);
        int segundosTolerancia = preferences.getInt("segundosTolerancia", preferencia.getSegundosTolerancia());
        preferencia.setSegundosTolerancia(segundosTolerancia);
        boolean isVendedorValido = preferences.getBoolean(KEY_VENDEDOR_ESTATE, preferencia.isValidVendedor());
        preferencia.setValidVendedor(isVendedorValido);
        boolean isMultiEmpresa = preferences.getBoolean(KEY_IS_MULTI_EMPRESA, preferencia.isMultiEmpresa());
        int tipoEmpresa = preferences.getInt(KEY_TIPO_EMPRESA, preferencia.getTipoEmpresa());
        boolean isManejoTurno = preferences.getBoolean(KEY_IS_MANEJO_TURNO, preferencia.isManejoTurno());
        preferencia.setManejoTurno(isManejoTurno);
        boolean isReciboProvisorio = preferences.getBoolean(KEY_IS_RECIBO_PROVISORIO, preferencia.isReciboProvisorio());
        preferencia.setReciboProvisorio(isReciboProvisorio);
        preferencia.setTipoEmpresa(tipoEmpresa);
        preferencia.setMultiEmpresa(isMultiEmpresa);
        int idCategoriaVendedor = preferences.getInt(KEY_ID_CATEGORIA, preferencia.getIdCategoria());
        preferencia.setIdCategoria(idCategoriaVendedor);
        // Ultimo recibo generado
        preferencia.setIdPuntoVentaUltimoRecibo(preferences.getInt("idPuntoVentaUltimoRecibo", preferencia.getIdPuntoVentaUltimoRecibo()));
        preferencia.setIdUltimoRecibo(preferences.getLong("idUltimoRecibo", preferencia.getIdUltimoRecibo()));
        preferencia.setBorradoBckupPreferido(preferences.getInt("borradoBckupPreferido", preferencia.getBorradoBckupPreferido()));
        preferencia.setLugarAlmacenamientoBD(preferences.getInt("lugarAlmacenamientoBD", preferencia.getLugarAlmacenamientoBD()));

        // Configuración de envío de recibos
        boolean isEnvioReciboAutomatico = preferences.getBoolean("envioReciboAutomatico", preferencia.isEnvioReciboAutomatico());
        preferencia.setEnvioReciboAutomatico(isEnvioReciboAutomatico);

        String snMovilReciboDto = preferences.getString(KEY_SN_MOVIL_RECIBO_DTO, preferencia.getSnMovilReciboDto());
        preferencia.setSnMovilReciboDto(snMovilReciboDto);

        int categoriasEmpRepartidor = preferences.getInt(KEY_CATEGORIAS_EMPLEADOS_REPARTIDOR, preferencia.getCategoriasEmpRepartidor());
        preferencia.setCategoriasEmpRepartidor(categoriasEmpRepartidor);

        String pathDbBackup = preferences.getString(KEY_ULTIMO_BACKUP, preferencia.getPathDbBackup());
        preferencia.setPathDbBackup(pathDbBackup);

        boolean cambiarPtoVta = preferences.getBoolean("cambiarPtoVta", preferencia.isCambiarPtoVta());
        preferencia.setCambiarPtoVta(cambiarPtoVta);
    }

    public void savePreference(Context context) {
        if (preferencia != null) {
            //preferences = context.getSharedPreferences(ARCHIVO_PREFERENCIA, Activity.MODE_PRIVATE);
            preferences = context.getSharedPreferences(ARCHIVO_PREFERENCIA, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("adminPassword", preferencia.getAdminPassword());
            editor.putString("adminUser", preferencia.getAdminUser());
            editor.putInt(KEY_ORDEN_PREFERIDO_CLIENTE, preferencia.getOrdenPreferidoCliente());
            editor.putInt(KEY_ORDEN_PREFERIDO_NO_PEDIDO, preferencia.getOrdenPreferidoNoPedido());
            editor.putInt("busquedaPreferidaArticulo", preferencia.getBusquedaPreferidaArticulo());
            editor.putInt(KEY_ORDEN_PREFERIDO_ARTICULO, preferencia.getOrdenPreferidoArticulo());
            editor.putInt("busquedaPreferidaCliente", preferencia.getBusquedaPreferidaCliente());
            editor.putInt(KEY_ORDEN_PREFERIDO_CCCLIENTE, preferencia.getOrdenPreferidoCCCliente());
            editor.putBoolean("descargaStockAutomatico", preferencia.isDescargaStockAutomatico());
            editor.putBoolean("busquedaPorListaArticulo", preferencia.isBusquedaPorListaArticulo());
            editor.putBoolean("envioPedidoAutomatico", preferencia.isEnvioPedidoAutomatico());
            editor.putBoolean("envioHojaDetalleAutomatico", preferencia.isEnvioHojaDetalleAutomatico());
            editor.putBoolean("envioEntregaAutomatico", preferencia.isEnvioEntregaAutomatico());
            editor.putBoolean("envioEgresoAutomatico", preferencia.isEnvioEgresoAutomatico());
            editor.putString("fechaUltimaSincronizacion", Formatter.formatDateTime(preferencia.getFechaUltimaDescarga()));
            editor.putString(KEY_FECHA_LIMITE_ACTUALIZACION, preferencia.getFechaLimiteActualizacion());
            editor.putString("ftpFolder", preferencia.getFtpFolder());
            editor.putString("ftpPassword", preferencia.getFtpPassword());
            editor.putString("ftpUserName", preferencia.getFtpUserName());
            editor.putInt("intervaloActualizacionApk", preferencia.getIntervaloActualizacionApk());
            editor.putInt("intervaloEnvioLogs", preferencia.getIntervaloEnvioLogs());
            editor.putInt("intervaloSincronizacionAutomatica", preferencia.getIntervaloSincronizacionAutomatica());
            editor.putInt("intervaloBorradoBck", Preferencia.getIntervaloBorradoBck());
            editor.putInt("borradoBckXCantidad", Preferencia.getBorradoBckXCantidad());
            editor.putInt("borradoBckupPreferido", preferencia.getBorradoBckupPreferido());
            String pathSistemaPref;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                pathSistemaPref = context.getExternalFilesDir(null) + "";
            } else {
                pathSistemaPref = Environment.getExternalStorageDirectory() + "";
            }
            editor.putString(KEY_PATH_PREFERIDO, pathSistemaPref);
            editor.putInt(KEY_LUGAR_PREFERIDO, preferencia.getLugarAlmacenamientoBD());

            editor.putString("urlFtp", preferencia.getUrlFtp());
            editor.putString("urlLocalServidor", preferencia.getUrlLocalServidor());
            editor.putString("urlRemotaServidor", preferencia.getUrlRemotaServidor());
            editor.putString("urlRemotaServidor2", preferencia.getUrlRemotaServidor2());
            editor.putInt(KEY_VERSION, preferencia.getVersion());
            editor.putString("formatoFecha", preferencia.getFormatoFecha());
            editor.putString("idTipoDocumentoPorDefecto", preferencia.getIdTipoDocumentoPorDefecto());
            editor.putInt("idPuntoVentaPorDefecto", preferencia.getIdPuntoVentaPorDefecto());
            editor.putInt(KEY_ID_PTO_VTA_RECIBO, preferencia.getIdPuntoVentaPorDefectoRecibo());
            editor.putString(KEY_ID_DOC_RECIBO, preferencia.getIdTipoDocumentoRecibo());
            editor.putInt("idRepartidorPorDefecto", preferencia.getIdRepartidorPorDefecto());
            editor.putInt("idVendedor", preferencia.getIdVendedor());
            editor.putString("tipoDatoIdArticulo", preferencia.getTipoDatoIdArticulo());
            editor.putInt("timeOutAlto", preferencia.getTimeOutAlto());
            editor.putInt("timeOutMedio", preferencia.getTimeOutMedio());
            editor.putInt("timeOutBajo", preferencia.getTimeOutBajo());
            editor.putBoolean("isEnvioPedidosPorFranja", preferencia.getIsEnvioPedidosPorFranja());
            editor.putInt("horaInicioEnvio", preferencia.getHoraInicioEnvio());
            editor.putInt("minutosInicioEnvio", preferencia.getMinutosInicioEnvio());
            editor.putInt("horaFinEnvio", preferencia.getHoraFinEnvio());
            editor.putInt("minutosFinEnvio", preferencia.getMinutosFinEnvio());
            editor.putLong("distanciaMinimaEntreLocalizacion", preferencia.getDistanciaMinimaLocalizacion());
            editor.putLong("intervaloTiempoEntreLocalizaciones", preferencia.getIntervaloTiempoLocalizacion());
            editor.putBoolean(KEY_VENDEDOR_ESTATE, preferencia.isValidVendedor());
            editor.putBoolean(KEY_IS_MULTI_EMPRESA, preferencia.isMultiEmpresa());
            editor.putInt(KEY_ID_CATEGORIA, preferencia.getIdCategoria());
            editor.putInt(KEY_TIPO_EMPRESA, preferencia.getTipoEmpresa());
            editor.putBoolean(KEY_IS_MANEJO_TURNO, preferencia.isManejoTurno());
            editor.putBoolean(KEY_IS_RECIBO_PROVISORIO, preferencia.isReciboProvisorio());

            // Envío de localizaciones

            editor.putString(KEY_IS_CONTROL_LIMITE_DISP, preferencia.getIsControlLimiteDisponibilidad());
            editor.putString("horaEntradaManana", Formatter.formatDateTime(preferencia.getHoraEntradaManana()));
            editor.putString("horaSalidaManana", Formatter.formatDateTime(preferencia.getHoraSalidaManana()));
            editor.putString("horaEntradaTarde", Formatter.formatDateTime(preferencia.getHoraEntradaTarde()));
            editor.putString("horaSalidaTarde", Formatter.formatDateTime(preferencia.getHoraSalidaTarde()));

            // Ultima ubicacion
            editor.putString("ultimaLatitud", preferencia.getUltimaLatitud());
            editor.putString("ultimaLongitud", preferencia.getUltimaLongitud());
            editor.putString("fechaUltimaUbicacion", Formatter.formatDateTime(preferencia.getFechaUltimaUbicacion()));

            editor.putBoolean("sugerenciaSincronizarInicio", preferencia.isSugerenciaSincronizarInicio());
            editor.putBoolean(KEY_IS_RECORDAR_CONTRASENIA, preferencia.isRecordarContrasena());

            editor.putString(KEY_CONTRASENIA, preferencia.getContrasena());
            // Configuraciones Tabla Empresas - Tarea #3036
            editor.putString("isCobranzaEstricta", preferencia.getIsCobranzaEstricta());
            editor.putString("isDescuento", preferencia.getIsDescuento());
            editor.putString("isRegistrarLocalizacion", preferencia.getIsRegistrarLocalizacion());
            editor.putFloat("porcentajeArticulosCriticos", (float) preferencia.getPorcentajeArticulosCriticos());
            editor.putFloat("montoMinimoFactura", (float) preferencia.getMontoMinimoFactura());
            editor.putFloat("montoMinimoDescuentoFactura", (float) preferencia.getMontoMinimoDescuentoFactura());
            editor.putInt("segundosTolerancia", preferencia.getSegundosTolerancia());
            // ultimo recibo
            editor.putInt("idPuntoVentaUltimoRecibo", preferencia.getIdPuntoVentaUltimoRecibo());
            editor.putLong("idUltimoRecibo", preferencia.getIdUltimoRecibo());
            // Envío de recibos
            editor.putBoolean("envioReciboAutomatico", preferencia.isEnvioReciboAutomatico());
            editor.putString(KEY_NOMBRE_EMPRESA, preferencia.getNombreEmpresa());
            editor.putString(KEY_SN_MOVIL_RECIBO_DTO, preferencia.getSnMovilReciboDto());
            editor.putInt(KEY_CATEGORIAS_EMPLEADOS_REPARTIDOR, preferencia.getCategoriasEmpRepartidor());
            editor.putString(KEY_ULTIMO_BACKUP, preferencia.getPathDbBackup());
            //cambio ptovta
            editor.putBoolean("cambiarPtoVta", preferencia.isCambiarPtoVta());
            editor.apply();
            try {
                savePreferencia(context);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
