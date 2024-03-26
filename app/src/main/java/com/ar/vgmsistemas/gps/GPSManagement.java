package com.ar.vgmsistemas.gps;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.UbicacionGeograficaBo;
import com.ar.vgmsistemas.entity.UbicacionGeografica;
import com.ar.vgmsistemas.helper.EstadoProveedor;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.ws.UbicacionGeograficaWs;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.Calendar;
import java.util.Date;

//TODO REFACTORIZAR ESTO

public class GPSManagement extends Service implements LocationListener {
    private static final String TAG = "GPSManagement";
    private static final long MIN_TIME_BW_UPDATES = 0; //10 segundos //1 Segundo = 1000 milisegundos
    public static final String ACTION_VGM_GPS_MSJ = "com.ar.vgmsistemas.GPS_MSJ";

    private final Context mContext;
    boolean isGPSEnabled = false; // Estado del GPS  
    boolean isNetworkEnabled = false; // Estado de la red WIFI o red de telefonía
    boolean isFusedEnabled = false; // Estado de la API Fused de Google Play Services
    boolean canGetLocation = false; // Indica si se pudo obtener la localización geográfica


    Location _location;
    double _latitude;
    double _longitude;
    String _idMovil;
    String _tipoOperacion;


    protected LocationManager locationManager;
    private static final int REQUEST_LOCATION_PERMISSION = 123;

    UbicacionGeograficaBo ubicacionGeograficaBo;
    UbicacionGeografica ubicacionGeografica; //Para guardar la ubicación geográfica

    RepositoryFactory _repoFactory;

    //proveedor Fused
    private FusedLocationProviderClient fusedLocationClient;


    public GPSManagement(Context context, String idMovil, String tipoOperacion) {
        this.mContext = context;
        _repoFactory = RepositoryFactory.getRepositoryFactory(context, RepositoryFactory.ROOM);
        ubicacionGeograficaBo = new UbicacionGeograficaBo(_repoFactory);
        this._idMovil = idMovil;
        this._tipoOperacion = tipoOperacion;
        ubicacionGeografica = new UbicacionGeografica();
    }

    public GPSManagement(Context context) {
        this.mContext = context;
        ubicacionGeograficaBo = new UbicacionGeograficaBo(_repoFactory);
        ubicacionGeografica = new UbicacionGeografica();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Obtener el servicio de ubicacion
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        //verificar permisos de ubicación
        // No se tienen permisos de ubicación, no se puede continuar
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //detener la obtención de la ubicacion cuando el servicio se detiene
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        //se llama cuando la ubicación cambia
    }

    @Override
    public void onProviderDisabled(String provider) {
        //se llama cuando el proveedor de ubicación se deshabilita
    }

    @Override
    public void onProviderEnabled(String provider) {
        //se llama cuando el proveedor de ubicación se habilita
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //se llama cuando el estado del proveedor de ubicación cambia
    }

    @Override
    public IBinder onBind(Intent intent) {
        //Este servicio no proporciona un enlace, devolvemos null
        return null;
    }


    /**
     * Método para efectuar la captura de coordenadas
     *
     * @param isEnviar: determina si se va a enviar o no la ubicación geografica capturada
     * @return Location
     */
    public Location getLocation(boolean isEnviar) {

        try {
            //Obtener el servicio de ubicacion
            if (locationManager == null) {
                locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            }

            //// Iniciar la obtención de la ubicación
            // ESTADO DEL GPS
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // ESTADO DE WIFI O 3G
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            //ESTADO FUSED
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                isFusedEnabled = locationManager.isProviderEnabled(LocationManager.FUSED_PROVIDER);
            }

            if (!isGPSEnabled && !isNetworkEnabled && !isFusedEnabled) {
                //Proveedores inactivos
                tratarNingunProveedorEncendido();
            } else {
                //GPS y/o Network activos
                tratarAlgunProveedorEncendido(isGPSEnabled, isEnviar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return _location;
    }

    private void tratarAlgunProveedorEncendido(boolean gpsEncendido, boolean isEnviar) throws Exception {
        this.canGetLocation = true;
        boolean capturado = false;
        //Determino el mejor proveedor entre GPS y RED
        String bestProvider = getBestProvider();
        //Localizo según el mejor proveedor
        switch (bestProvider) {
            case LocationManager.GPS_PROVIDER:
                capturado = localizoPorGPS(isEnviar);
                break;
            case LocationManager.NETWORK_PROVIDER:
                capturado = localizoPorRed(gpsEncendido, isEnviar);
                break;
            case LocationManager.FUSED_PROVIDER:
                capturado = localizoPorFused(isEnviar);
                break;
        }
        if (!capturado) {
            /*new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext, mContext.getString(R.string.msjNungunProveedorFunciona), Toast.LENGTH_SHORT).show();
                }
            });*/
            // Emitir mensaje
            Intent mensajeIntent = new Intent(ACTION_VGM_GPS_MSJ);
            mensajeIntent.putExtra("mensaje", "No fue posible establecer ubicación geográfica. Ningún proveedor activo.");
            LocalBroadcastManager.getInstance(this).sendBroadcast(mensajeIntent);
        }
    }

    private void tratarNingunProveedorEncendido() throws Exception {
        //Solo notifico de que el GPS esta apagado, con eso es suficiente
        localizoPorGPS(true);
    }

    /**
     * Método utilizado en ObtenerCoordenadas en el menu de Cliente
     *
     * @return Location
     */
    public Location getDatosUbicaciones() {

        Location location = null;

        try {

            locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            // Obtenemos el estado del GPS
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // Obtenemos el estado del WIFI o de la red de telefonía
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGPSEnabled) {
                String bestProvider = getBestProvider();
                //Localización por el mejor proveedor
                location = obtenerCoordenadas(bestProvider);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Utilizado en getDatosUbicaciones() de esta clase
     *
     * @param provider del proveedor de GPS
     * @return Location
     */
    private Location obtenerCoordenadas(String provider) {

        Location location = null;

        registerLocationUpdates(provider);

        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                location = locationManager.getLastKnownLocation(provider);
            } else {
                Toast.makeText(mContext, "No se otorgaron los permisos necesarios", Toast.LENGTH_SHORT).show();
            }


        }
        return location;
    }

    /**
     * Obtiene el mejor proveedor en función de los parámetros, en este caso alta precision
     *
     * @return String con el nombre del mejor proveedor en cuanto a precisión
     */
    private String getBestProvider() {
        Criteria requerimiento = new Criteria();
        requerimiento.setAccuracy(Criteria.ACCURACY_FINE);
        //Obtengo el proveedor que me brinde la mayor precisión y que está activado
        return locationManager.getBestProvider(requerimiento, true);
    }

    private void registerLocationUpdates(String proveedorARegistrar) {
        try {
            locationManager.requestLocationUpdates(
                    proveedorARegistrar,
                    MIN_TIME_BW_UPDATES,
                    PreferenciaBo.getInstance().getPreferencia(this).getDistanciaMinimaLocalizacion(),
                    this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

    //Utilizado cuando el proveedor es GPS
    private boolean localizoPorGPS(boolean isEnviar) throws Exception {
        boolean isLocalizoPorGPS = false;
        if (isGPSEnabled) {
            registerLocationUpdates(LocationManager.GPS_PROVIDER);
            Log.d("GPS Enabled", "GPS Enabled");
            if (locationManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (mContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && mContext.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                        builder.setMessage("Esta aplicación requiere permisos de ubicación para funcionar correctamente. ¿Desea otorgar los permisos ahora?")
                                .setTitle("Solicitud de permisos")
                                .setPositiveButton("Sí", (dialog, id) -> requestLocationPermission((Activity) getApplicationContext()))
                                .setNegativeButton("No", (dialog, id) -> {
                                    // Mostrar un mensaje o realizar alguna acción adicional
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
                _location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if (_location != null) {
                    //LOCALIZO POR GPS
                    _latitude = _location.getLatitude();
                    _longitude = _location.getLongitude();
                    guardarLocalizacion(isEnviar, EstadoProveedor.ACTIVO_GPS);
                    isLocalizoPorGPS = true;
                } else {
                    //NO PUDO LOCALIZAR POR GPS
                    noPudoLocalizarProveedor(isEnviar, EstadoProveedor.NO_PUDO_CAPTURAR_POR_GPS);
                }
            } else {
                //GPS APAGADO EN EL TELEFONO
                noPudoLocalizarProveedor(isEnviar, EstadoProveedor.GPS_APAGADO);
            }
        }
        return isLocalizoPorGPS;
    }

    //Utilizado cuando el proveedor sea Fused
    private boolean localizoPorFused(boolean isEnviar) throws Exception {
        boolean isLocalizoPorFused = false;
        if (isFusedEnabled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                registerLocationUpdates(LocationManager.FUSED_PROVIDER);
            }
            Log.d("Fused Enabled", "Fused Enabled");

            if (locationManager != null) {
                // Iniciar la captura de pantalla.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (mContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            mContext.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        //hacer algo cuando no esta validado los permisos
                    }
                }
                if (fusedLocationClient == null) {
                    //Obtengo el servicio de GooglePlayServices para proveedor fused
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
                }
                _location = locationManager.getLastKnownLocation(LocationManager.FUSED_PROVIDER);
                if (_location != null) {
                    //LOCALIZO POR GPS
                    _latitude = _location.getLatitude();
                    _longitude = _location.getLongitude();
                    guardarLocalizacion(isEnviar, EstadoProveedor.ACTIVO_GPS);
                    isLocalizoPorFused = true;
                } else {
                    //NO PUDO LOCALIZAR POR GPS
                    noPudoLocalizarProveedor(isEnviar, EstadoProveedor.NO_PUDO_CAPTURAR_POR_GPS);
                }

            } else {
                //GPS APAGADO EN EL TELEFONO
                noPudoLocalizarProveedor(isEnviar, EstadoProveedor.GPS_APAGADO);
            }
        }
        return isLocalizoPorFused;
    }

    // Método para solicitar permisos de ubicación
    private void requestLocationPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkLocationPermission(activity)) {
                // Si no tienes permisos, solicítalos
                activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            }
        }
    }

    // Método para verificar si tienes permisos de ubicación
    private boolean checkLocationPermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    //Utilizado en esta clase, cuando el proveedor es Network
    private boolean localizoPorRed(boolean gpsEncendido, boolean isEnviar) throws Exception {

        boolean isLocalizoPorRed = false;

        if (isNetworkEnabled) {

            registerLocationUpdates(LocationManager.NETWORK_PROVIDER);

            Log.d("Network", "Network");

            if (locationManager != null) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (mContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && mContext.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                        builder.setMessage("Esta aplicación requiere permisos de ubicación para funcionar correctamente. ¿Desea otorgar los permisos ahora?")
                                .setTitle("Solicitud de permisos")
                                .setPositiveButton("Sí", (dialog, id) -> requestLocationPermission((Activity) getApplicationContext()))
                                .setNegativeButton("No", (dialog, id) -> {
                                    // Mostrar un mensaje o realizar alguna acción adicional
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }

                _location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                String estadoProveedor = EstadoProveedor.ACTIVO_RED;

                if (_location != null) {

                    //LOCALIZO POR RED

                    _latitude = _location.getLatitude();
                    _longitude = _location.getLongitude();

                    if (!gpsEncendido) {

                        //LOCALIZO POR RED PERO NOTIFICO QUE EL GPS ESTA APAGADO

                        estadoProveedor = EstadoProveedor.ACTIVO_RED_GPS_APAGADO;
                    }

                    guardarLocalizacion(isEnviar, estadoProveedor);

                    isLocalizoPorRed = true;

                } else {

                    //NO SE PUDO LOCALIZAR POR RED

                    if (gpsEncendido) {

                        estadoProveedor = EstadoProveedor.NO_PUDO_CAPTURAR_POR_RED;

                    } else {

                        //NO SE PUDO LOCALIZAR POR RED Y EL GPS ESTA APAGADO
                        estadoProveedor = EstadoProveedor.NO_PUDO_CAPTURAR_POR_RED_GPS_APAGADO;
                    }

                    noPudoLocalizarProveedor(isEnviar, estadoProveedor);

                }

            } else {

                //NO PUDO LOCALIZAR POR LA RED PORQUE NO TIENE INTERNET EL TELEFONO
                noPudoLocalizarProveedor(isEnviar, EstadoProveedor.SIN_INTERNET);
            }
        }

        return isLocalizoPorRed;
    }

    private void noPudoLocalizarProveedor(boolean isEnviar, String estadoProveedor) throws Exception {
        double latString = Double.parseDouble(PreferenciaBo.getInstance().getPreferencia(getApplication()).getUltimaLatitud());
        double lngString = Double.parseDouble(PreferenciaBo.getInstance().getPreferencia(getApplication()).getUltimaLongitud());
        if ((latString != 0f) && (lngString != 0f)) {
            _latitude = latString;
            _longitude = lngString;
            guardarLocalizacion(isEnviar, estadoProveedor);
        }
    }

    /**
     * Graba en el SQLITE la UG y envía si isEnviar es true
     *
     * @param isEnviar - determina si hay que enviar por ws la UG
     *                 enumProveedor - determina el enum al que corresponde para asignar el mensaje
     * @throws Exception devuelve cualquier excepcion
     */
    private void guardarLocalizacion(boolean isEnviar, String proveedor) throws Exception {
        if (isRegistrarUbicacion()) {
            final UbicacionGeografica ubicacionNueva = new UbicacionGeografica();
            String idVendedorStr = String.valueOf(PreferenciaBo.getInstance().getPreferencia().getIdVendedor());
            int idVendedor = Integer.parseInt(idVendedorStr);
            ubicacionNueva.setIdLegajo(idVendedor);
            ubicacionNueva.setFechaPosicionMovil(Calendar.getInstance().getTime());

            //Latitud y Longitud
            String latitudSt = String.valueOf(_latitude);
            String longitudSt = String.valueOf(_longitude);
            Double latitud = Double.valueOf(latitudSt);
            Double longitud = Double.valueOf(longitudSt);
            ubicacionNueva.setLatitud(latitud);
            ubicacionNueva.setLongitud(longitud);

            //Precisión, velocidad y altitud
            if (proveedor.equals(EstadoProveedor.ACTIVO_GPS) ||
                    proveedor.equals(EstadoProveedor.ACTIVO_RED) ||
                    proveedor.equals(EstadoProveedor.ACTIVO_FUSED) ||
                    proveedor.equals(EstadoProveedor.ACTIVO_RED_GPS_APAGADO)) {

                ubicacionNueva.setPrecision(Double.valueOf(String.valueOf(_location.getAccuracy())));
                ubicacionNueva.setVelocidad(Double.valueOf(String.valueOf(_location.getSpeed())));
                ubicacionNueva.setAltitud(Double.valueOf(String.valueOf(_location.getAltitude())));
            }

            if (_idMovil != null) {
                ubicacionNueva.setIdMovil(_idMovil);
            } else {
                generarIdMovilUG(ubicacionNueva);//Si es null genero y seteo el idMovil
            }

            if (_tipoOperacion != null) {
                ubicacionNueva.setTipoOperacion(_tipoOperacion);
            } else {
                ubicacionNueva.setTipoOperacion(UbicacionGeografica.OPERACION_ENVIO_PERIODICO);
            }

            String imei = UtilGps.getIMEI(mContext);
            ubicacionNueva.setDeviceId(imei);

            //Estado del Proveedor
            ubicacionNueva.setEstadoProveedor(proveedor);

            boolean isGuardado = ubicacionGeograficaBo.create(ubicacionNueva);
            // Despues que creo la ubicacion en la base de datos seteo en Preferencia como ultima ubicacion
            ubicacionGeograficaBo.updateUltimaUbicacion(ubicacionNueva);

            //Seteo a la variable
            this.ubicacionGeografica = ubicacionNueva;

            if (isGuardado && isEnviar) {
                enviar(ubicacionNueva);
            }
        }
    }

    private void enviar(final UbicacionGeografica ubicacionNueva) {
        //Hilo y llamar al Web Service
        Thread t = new Thread() {
            public void run() {
                try {
                    enviarUbicacionGeografica(ubicacionNueva);
                } catch (Exception e) {
                    //No se trata la exception porque el servicio va a intentar despues
                }
            }
        };
        t.start();
    }

    private boolean isRegistrarUbicacion() {
        boolean isRegistrarUbicacion = true;

        if (isEnvioPeriodico(_tipoOperacion)) {
            if (isMismaUbicacion()) {
                if (superaSegundosTolerancia()) {
                    isRegistrarUbicacion = true;// Env periodico, misma ubicacion y supera la tolerancia
                } else {
                    isRegistrarUbicacion = false;// Env periodico, misma ubicacion y no supera la tolerancia
                }
            } else {
                isRegistrarUbicacion = true;//Si no es la misma ubicacion registro
            }
        } else {
            isRegistrarUbicacion = true;//Si no es envio periodico registro siempre
        }
        return isRegistrarUbicacion;
    }

    private boolean isEnvioPeriodico(String tipoOperacion) {
        boolean isEnvioPeriodico = false;
        if (tipoOperacion == null) {
            isEnvioPeriodico = true;
        }
        return isEnvioPeriodico;
    }

    private boolean isMismaUbicacion() {
        String ultimaLatitudSt = PreferenciaBo.getInstance().getPreferencia().getUltimaLatitud();
        String ultimaLongitudSt = PreferenciaBo.getInstance().getPreferencia().getUltimaLongitud();
        double ultimaLatitud = Double.parseDouble(ultimaLatitudSt);
        double ultimaLongitud = Double.parseDouble(ultimaLongitudSt);
        boolean isMismaUbicacion = ((ultimaLatitud - _latitude) < 0.0001) && ((ultimaLongitud - _longitude) < 0.0001);
        return isMismaUbicacion;
    }

    private boolean superaSegundosTolerancia() {
        boolean superaSegundosTolerancia = false;
        Date fechaUltimaUbicacion = PreferenciaBo.getInstance().getPreferencia().getFechaUltimaUbicacion();
        Date fechaHoy = Calendar.getInstance().getTime();
        int segundosTolerancia = PreferenciaBo.getInstance().getPreferencia().getSegundosTolerancia();

        long diferenciaMils = fechaHoy.getTime() - fechaUltimaUbicacion.getTime();
        long diferenciaSeg = diferenciaMils / 1000;
        //long minutos = segundos / 60;
        if (diferenciaSeg > segundosTolerancia) {
            superaSegundosTolerancia = true;
        }
        return superaSegundosTolerancia;
    }

    private void generarIdMovilUG(UbicacionGeografica ubicacionGeografica) {
        // Genero el idMovil de la Ubicación Geográfica
        String fechaPosicion = Formatter.formatDateTimeToString(ubicacionGeografica.getFechaPosicionMovil());
        String idMovil = ubicacionGeografica.getIdLegajo() + " - " + fechaPosicion + " - UG";
        ubicacionGeografica.setIdMovil(idMovil);
    }

    public synchronized void enviarUbicacionGeografica(UbicacionGeografica ubicacionGeografica) throws Exception {
        UbicacionGeograficaWs ubicacionGeograficaWs = new UbicacionGeograficaWs(getApplicationContext());
        ubicacionGeograficaWs.send(ubicacionGeografica);
        //Actualizo fecha de sincronizacion de la ubicacion geográfica
        UbicacionGeograficaBo ubicacionGeograficaBo = new UbicacionGeograficaBo(_repoFactory);
        ubicacionGeograficaBo.updateFechaSincronizacion(ubicacionGeografica);
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    public void stopUsingGPS() {
        if (locationManager != null) {
            /*locationManager.removeUpdates(GPSManagement.this);*/
        }
    }

    /**
     * Function to get latitude
     */
    public double getLatitude() {
        if (_location != null) {
            _latitude = _location.getLatitude();
        }
        return _latitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (_location != null) {
            _longitude = _location.getLongitude();
        }
        return _longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());

        // Setting Dialog Title
        alertDialog.setTitle("Configuración del GPS");

        // Setting Dialog Message
        alertDialog.setMessage("El GPS no puede obtener la ubicación geográfica. Deseas ir al menu de configuraciones?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Configuraciones", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                getApplicationContext().startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public UbicacionGeografica getUbicacionGeografica() {
        return ubicacionGeografica;
    }

    public void setUbicacionGeografica(UbicacionGeografica ubicacionGeografica) {
        this.ubicacionGeografica = ubicacionGeografica;
    }

    public Location get_location() {
        return _location;
    }

    public void set_location(Location _location) {
        this._location = _location;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}