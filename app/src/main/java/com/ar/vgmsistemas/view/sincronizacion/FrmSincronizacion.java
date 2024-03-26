package com.ar.vgmsistemas.view.sincronizacion;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDiskIOException;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.AuditoriaGpsBo;
import com.ar.vgmsistemas.bo.EmpresaBo;
import com.ar.vgmsistemas.bo.MovimientoBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.SincronizacionBo;
import com.ar.vgmsistemas.bo.UbicacionGeograficaBo;
import com.ar.vgmsistemas.dropbox.DropboxClientFactory;
import com.ar.vgmsistemas.dropbox.DropboxManager;
import com.ar.vgmsistemas.dropbox.PicassoClient;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.gps.GPSService;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.task.sincronizacion.BuscarConexionTask.BuscarConexionListener;
import com.ar.vgmsistemas.task.sincronizacion.SincronizacionTask;
import com.ar.vgmsistemas.task.sincronizacion.SincronizacionTask.SincronizacionListener;
import com.ar.vgmsistemas.utils.CategoriaRecursoHumano;
import com.ar.vgmsistemas.utils.ComparatorDateTime;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.ItemMenuNames;
import com.ar.vgmsistemas.view.dialogs.ProgressDialogFragment;
import com.ar.vgmsistemas.view.dialogs.ProgressDialogFragment.CancelProgressListener;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;
import com.ar.vgmsistemas.view.menu.MenuManager;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


public class FrmSincronizacion extends BaseNavigationFragment implements CancelProgressListener {

    private Button btnDescarga;
    private Button btnEnviar;
    private Button btnDescargaImg;
    private TextView txtUrlServidor;
    private TextView txtPedidosEnviados;
    private TextView txtPedidosSinEnviar;
    private TextView txtPedidosAnulados;
    private TextView txtPedidosAnuladosEnviados;
    private TextView txtPedidosAnuladosSinEnviar;
    private TextView txtRecibosEnviados;
    private TextView txtRecibosSinEnviar;
    private TextView txtNoPedidosEnviados;
    private TextView txtNoPedidosSinEnviar;
    private TextView txtFechaUltimaDescarga;
    private TextView txtHojasDetalleEnviadas;
    private TextView txtHojasDetalleNoEnviadas;
    private TextView txtClientesSinEnviar;
    private TextView txtClientesEnviados;
    private TextView txtModoConexion;
    /*/private TextView txtUbicacionesGeograficasSinEnviar;*/
    //private TextView txtUbicacionesGeograficasEnviadas;
    private TextView txtEgresosSinEnviar;
    private TextView txtEgresosEnviados;
    //*/private TextView txtEntregasSinEnviar;*/
    //private TextView txtEntregasEnviadas;

    /*Labels*/
    private TextView txtPedidoLabelID;
    private TextView txtPedidoAnuladoLabelID;
    private TextView txtHojaDetalleLabelID;
    private TextView txtReciboLabelID;
    private TextView txtNoAtencionLabelID;
    private TextView txtUbicGeograficaLabelID;
    private TextView txtEgresoLabelID;


    DropboxManager dropboxManager;
    private static final String TAG = FrmSincronizacion.class.getCanonicalName();
    public static final int DESCARGA = 0;
    public static final int ENVIO = 1;
    private int modo;
    private SincronizacionTask task;
    private ProgressDialogFragment progressDialogFragment;
    SimpleDialogFragment simpleDialogFragment;

    private String TokenDropbox;
    private String FolderDropbox;

    private boolean isRotationEnabled = false;

    //BO's
    private MovimientoBo _movimientoBo;
    private EmpresaBo _empresaBo;
    private AuditoriaGpsBo _audAuditoriaGpsBo;
    private UbicacionGeograficaBo _ubicacionBo;


    // DAO
    private RepositoryFactory _repoFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.lyt_sincronizacion, container, false);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVars();
        checkConexion();

    }

    private void checkCatalogo() {
        String snCatalogo = "";
        try {
            if (_empresaBo.recoveryEmpresa() != null) {
                snCatalogo = _empresaBo.recoveryEmpresa().getSnCatalogo();
                TokenDropbox = _empresaBo.recoveryEmpresa().getDropboxToken();
                FolderDropbox = _empresaBo.recoveryEmpresa().getDropboxAppName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (snCatalogo == null || snCatalogo.equals("") || snCatalogo.equals("N")) {
            btnDescargaImg.setVisibility(View.GONE);
        } else {
            btnDescargaImg.setVisibility(View.VISIBLE);
        }

    }

    private void initVars() {
        dropboxManager = new DropboxManager(getContext());
        this._repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        this._movimientoBo = new MovimientoBo(this._repoFactory);
        this._empresaBo = new EmpresaBo(_repoFactory);
        this._audAuditoriaGpsBo = new AuditoriaGpsBo(_repoFactory);
        this._ubicacionBo = new UbicacionGeograficaBo(_repoFactory);

        try {
            isRotationEnabled = Settings.System.getInt(getContext().getContentResolver(), Settings.System.ACCELEROMETER_ROTATION) == 1;
        } catch (Exception e) {
        }

        //Reabro la conexion para evitar que siga tomando la vieja
        try {
            _repoFactory.reOpenConnection();
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "Toma la Vieja", e);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initComponents();
        try {
            loadData();
        } catch (Exception e) {

        }
    }

    private SincronizacionListener getListenerSincronizacion() {
        SincronizacionListener listener = new SincronizacionListener() {

            @Override
            public void onError(String error) {

                progressDialogFragment.dismiss();
                String msj = "";
                if (error != null && !error.equals(ErrorManager.ErrorVendedorNoValido)) {
                    msj = (modo == DESCARGA) ? getActivity().getString(R.string.msjErrorDescarga) : error;
                } else {
                    msj = getActivity().getString(R.string.msjSincronizacionVendedorNoValido);
                }
                simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, ErrorManager.INFORMACION, msj);
                getFragmentManager().beginTransaction().add(simpleDialogFragment, "tag").commitAllowingStateLoss();
                setAutoOrientationEnabled(getContext(), isRotationEnabled);
            }

            @Override
            public void onDone() {
                progressDialogFragment.dismiss();
                String msj = (modo == DESCARGA) ? getActivity().getString(R.string.msjSincronizacionExitosa) : getActivity().getString(R.string.msjEnvioExitoso);
                Toast.makeText(getActivity().getApplicationContext(), msj, Toast.LENGTH_LONG).show();//cambio karen
                loadData();
                if (modo == DESCARGA) {
                    PreferenciaBo.getInstance().getPreferencia(getActivity().getApplicationContext()).setFechaUltimaDescarga(Calendar.getInstance().getTime());
                    PreferenciaBo.getInstance().savePreference(getActivity().getApplicationContext());
                    try {
                        reiniciarServicioGps();
                    } catch (Exception e) {
                        ErrorManager.manageException(TAG, "Reiniciar Servicio GPS", e);
                    }
                }
                setAutoOrientationEnabled(getContext(), isRotationEnabled);

            }
        };
        return listener;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (FolderDropbox != null && TokenDropbox != null && !FolderDropbox.equals("") && !TokenDropbox.equals("")) {
            DropboxClientFactory.init(FolderDropbox, TokenDropbox);
            PicassoClient.init(getActivity().getApplicationContext(), DropboxClientFactory.getClient());
        }
        loadData();
    }

    private void chequearFechaLimiteActualizacion() {
        Preferencia preferencia = PreferenciaBo.getInstance().getPreferencia(getActivity());
        Date fechaLimite = null;
        try {
            if (preferencia.getFechaLimiteActualizacion() != null &&
                    !preferencia.getFechaLimiteActualizacion().equalsIgnoreCase("")) {
                fechaLimite = Formatter.convertToDate2(preferencia.getFechaLimiteActualizacion());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            if (preferencia.getVersion() > getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionCode && fechaLimite != null) {
                if (fechaLimite.before(Calendar.getInstance().getTime())) {
                    SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK,
                            getString(R.string.msjFechaLimiteActExpirada), getString(R.string.msjFechaLimiteActExpiradaTitulo), new SimpleDialogFragment.OkListener() {
                                @Override
                                public void onOkSelected() {
                                    Fragment fragmentDetalleArticulo = MenuManager.getFragment(ItemMenuNames.POS_ACTUALIZAR);
                                    getNavigationMenu().addFragment(fragmentDetalleArticulo, ItemMenuNames.STRING_ACTUALIZAR);
                                }
                            });
                    simpleDialogFragment.show(getFragmentManager(), "");
                } else {
                    SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK,
                            getString(R.string.msjFechaLimiteActualizarAntesDe) + " " + preferencia.getFechaLimiteActualizacion(), getString(R.string.msjFechaLimiteActExpiradaTitulo), new SimpleDialogFragment.OkListener() {
                                @Override
                                public void onOkSelected() {
                                    btnDescargaClick();
                                }
                            });
                    simpleDialogFragment.show(getFragmentManager(), "");
                }
            } else {
                btnDescargaClick();
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        this.btnDescarga = (Button) getView().findViewById(R.id.btnDescargaLocal);
        this.btnDescarga.setOnClickListener(v -> chequearFechaLimiteActualizacion());//donde v es un View

        this.btnEnviar = (Button) getView().findViewById(R.id.btnEnviar);
        this.btnEnviar.setOnClickListener(v -> btnEnviarOnClick(v));//donde v es un View

        this.btnDescargaImg = (Button) getView().findViewById(R.id.btnDescargarImagenes);
        this.btnDescargaImg.setOnClickListener(v -> btnDescargaImgOnClick(v));//donde v es un View

        checkCatalogo();

        try {
            if (CategoriaRecursoHumano.isRepartidor(_repoFactory)) {
                getView().findViewById(R.id.llPedidosAnulados).setVisibility(View.GONE);
            } else {
                getView().findViewById(R.id.llPedidosAnuladosEnviados).setVisibility(TextView.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.txtUrlServidor = (TextView) getView().findViewById(R.id.lblUrlServidor);
        this.txtFechaUltimaDescarga = (TextView) getView().findViewById(R.id.lblFechaUltimaDescargaValue);
        this.txtPedidosEnviados = (TextView) getView().findViewById(R.id.lblPedidosEnviadosValue);
        this.txtPedidosSinEnviar = (TextView) getView().findViewById(R.id.lblPedidosSinEnviarValue);
        this.txtPedidosAnulados = (TextView) getView().findViewById(R.id.lblPedidosAnuladosValue);
        this.txtPedidosAnuladosEnviados = (TextView) getView().findViewById(R.id.lblPedidosAnuladosEnviadosValue);
        this.txtPedidosAnuladosSinEnviar = (TextView) getView().findViewById(R.id.lblPedidosAnuladosSinEnviarValue);
        this.txtRecibosEnviados = (TextView) getView().findViewById(R.id.lblRecibosEnviadosValue);
        this.txtRecibosSinEnviar = (TextView) getView().findViewById(R.id.lblRecibosSinEnviarValue);
        this.txtNoPedidosEnviados = (TextView) getView().findViewById(R.id.lblNoPedidosEnviadosValue);
        this.txtNoPedidosSinEnviar = (TextView) getView().findViewById(R.id.lblNoPedidosSinEnviarValue);
        this.txtClientesSinEnviar = (TextView) getView().findViewById(R.id.lblClientesSinEnviarValue);
        this.txtClientesEnviados = (TextView) getView().findViewById(R.id.lblClientesEnviadosValue);
        this.txtModoConexion = (TextView) getView().findViewById(R.id.txtModoConexion);
        /*/this.txtUbicacionesGeograficasSinEnviar = (TextView)getView().findViewById(R.id.lblUbicacionesSinEnviarValue);*/
        /*/this.txtUbicacionesGeograficasEnviadas = (TextView) getView().findViewById(R.id.lblUbicacionesEnviadasValue);*/
        txtHojasDetalleEnviadas = (TextView) getView().findViewById(R.id.tvHojasEnviadas);
        txtHojasDetalleNoEnviadas = (TextView) getView().findViewById(R.id.tvHojasSinEnviar);
        txtEgresosEnviados = (TextView) getView().findViewById(R.id.lblEgresosEnviadosValue);
        txtEgresosSinEnviar = (TextView) getView().findViewById(R.id.lblEgresosSinEnviarValue);
        /*/txtEntregasEnviadas = (TextView)getView().findViewById(R.id.lblEntregasEnviadasValue);*/
        /*/txtEntregasSinEnviar = (TextView)getView().findViewById(R.id.lblEntregasSinEnviarValue);*/

        /*Labels*/
        this.txtPedidoLabelID = (TextView) getView().findViewById(R.id.lblPedidosID);
        this.txtPedidoAnuladoLabelID = (TextView) getView().findViewById(R.id.lblPedidosAnuladosID);
        this.txtHojaDetalleLabelID = (TextView) getView().findViewById(R.id.lblHojaDetalleID);
        this.txtReciboLabelID = (TextView) getView().findViewById(R.id.lblRecibosID);
        this.txtNoAtencionLabelID = (TextView) getView().findViewById(R.id.lblNoAtencionesID);
        this.txtUbicGeograficaLabelID = (TextView) getView().findViewById(R.id.lblUbicacionesModifID);
        this.txtEgresoLabelID = (TextView) getView().findViewById(R.id.lblEgresosID);
    }

    private void btnDescargaImgOnClick(View v) {
        setAutoOrientationEnabled(getContext(), false);
        String msj = getActivity().getString(R.string.descargandoImagenes);
        progressDialogFragment = ProgressDialogFragment.newInstance(msj);
        progressDialogFragment.show(getFragmentManager(), "Descargando..");

        dropboxManager.downloadAllImages(new DropboxManager.DownloadImageListener() {

            @Override
            public void onDone(boolean downloadError) {
                progressDialogFragment.dismiss();
                String msj;
                if (!downloadError) {
                    msj = getActivity().getString(R.string.msjDescargaImagenesExitosa);
                } else {
                    msj = getActivity().getString(R.string.msjDescargaImagenesFallida);
                }
                Toast.makeText(getActivity().getApplicationContext(), msj, Toast.LENGTH_LONG).show();
            }

            @Override
            public void refreshLoader(long size, long ok) {
                Log.v("Prueba", "refresh: " + size + " - " + ok);
            }
        });
    }

    private void loadData() {

        String modoConexion;
        if (PreferenciaBo.getInstance().getPreferencia(getActivity().getApplicationContext()).isSincronizacionLocal()) {
            modoConexion = getString(R.string.msjConexionLocal);
        } else {
            modoConexion = getString(R.string.msjConexionInternet);
        }
        this.txtModoConexion.setText(modoConexion);
        this.txtUrlServidor.setText(PreferenciaBo.getInstance().getPreferencia(getActivity().getApplicationContext()).getUrlServidorActiva());
        String fechaUltimaDescarga = Formatter.formatDateTime(
                PreferenciaBo.getInstance().getPreferencia(getActivity().getApplicationContext()).getFechaUltimaDescarga());
        this.txtFechaUltimaDescarga.setText(fechaUltimaDescarga);


        try {
            int pedidosEnviados = _movimientoBo.getCantidadPedidosEnviados();
            int pedidosSinEnviar = _movimientoBo.getCantidadPedidosSinEnviar();
            int pedidosAnulados = _movimientoBo.getCantidadPedidosAnulados();
            int pedidosAnuladosEnviados = _movimientoBo.getCantidadPedidosAnuladosEnviados();
            int pedidosAnuladosSinEnviar = _movimientoBo.getCantidadPedidosAnuladosSinEnviar();
            int recibosEnviados = _movimientoBo.getCantidadRecibosEnviados();
            int recibosSinEnviar = _movimientoBo.getCantidadRecibosSinEnviar();
            int noPedidosEnviados = _movimientoBo.getCantidadNoPedidosEnviados();
            int noPedidosSinEnviar = _movimientoBo.getCantidadNoPedidosSinEnviar();
            int clientesSinEnviar = _movimientoBo.getCantidadClientesNoEnviados();
            int clientesEnviados = _movimientoBo.getCantidadClientesEnviados();
            int ubicacionesGeograficasSinEnviar = _movimientoBo.getCantidadUbicacionesGeograficasSinEnviar();
            //int ubicacionesGeograficasEnviadas 		= _movimientoBo.getCantidadUbicacionesGeograficasEnviadas();
            int hojasDetalleEnviadas = _movimientoBo.getCantidadHojasDetalleEnviadas();
            int hojasDetalleSinEnviar = _movimientoBo.getCantidadHojasDetalleSinEnviar();
            int entregasEnviadas = _movimientoBo.getCantidadEntregasEnviadas();
            int entregasSinEnviar = _movimientoBo.getCantidadEntregasSinEnviar();
            int egresosEnviados = _movimientoBo.getCantidadEgresosEnviados();
            int egresosSinEnviar = _movimientoBo.getCantidadEgresosSinEnviar();
            int auditoriasGpsSinEnviar = _audAuditoriaGpsBo.recoveryAEnviar().size();
            this.btnEnviar.setEnabled((auditoriasGpsSinEnviar +
                    /*pedidosAnuladosSinEnviar+*/
                    pedidosSinEnviar +
                    noPedidosSinEnviar +
                    clientesSinEnviar +
                    recibosSinEnviar +
                    ubicacionesGeograficasSinEnviar +
                    hojasDetalleSinEnviar +
                    entregasSinEnviar +
                    egresosSinEnviar) > 0);
            this.txtPedidosEnviados.setText(String.valueOf(pedidosEnviados));
            this.txtPedidosSinEnviar.setText(String.valueOf(pedidosSinEnviar));
            this.txtPedidosAnulados.setText(String.valueOf(pedidosAnulados));
            this.txtPedidosAnuladosEnviados.setText(String.valueOf(pedidosAnuladosEnviados));
            this.txtPedidosAnuladosSinEnviar.setText(String.valueOf(pedidosAnuladosSinEnviar));
            this.txtRecibosEnviados.setText(String.valueOf(recibosEnviados));
            this.txtRecibosSinEnviar.setText(String.valueOf(recibosSinEnviar));
            this.txtNoPedidosEnviados.setText(String.valueOf(noPedidosEnviados));
            this.txtNoPedidosSinEnviar.setText(String.valueOf(noPedidosSinEnviar));
            this.txtClientesEnviados.setText(String.valueOf(clientesEnviados));
            this.txtClientesSinEnviar.setText(String.valueOf(clientesSinEnviar));
            /*/this.txtUbicacionesGeograficasSinEnviar.setText(String.valueOf(ubicacionesGeograficasSinEnviar));*/
            /*/this.txtUbicacionesGeograficasEnviadas.setText(String.valueOf(ubicacionesGeograficasEnviadas));*/
            txtHojasDetalleEnviadas.setText(String.valueOf(hojasDetalleEnviadas));
            txtHojasDetalleNoEnviadas.setText(String.valueOf(hojasDetalleSinEnviar));
            txtEgresosEnviados.setText(String.valueOf(egresosEnviados));
            txtEgresosSinEnviar.setText(String.valueOf(egresosSinEnviar));
            /*/txtEntregasEnviadas.setText(String.valueOf(entregasEnviadas));*/
            /*/txtEntregasSinEnviar.setText(String.valueOf(entregasSinEnviar));*/

            /*Cambio color label en caso de que haya pendientes de envio*/
            txtPedidoLabelID.setTextColor(ContextCompat.getColor(getContext(), (pedidosSinEnviar > 0) ? R.color.vgm_color : R.color.base_text_color));
            txtPedidoAnuladoLabelID.setTextColor(ContextCompat.getColor(getContext(), (pedidosAnuladosSinEnviar > 0) ? R.color.vgm_color : R.color.base_text_color));
            txtHojaDetalleLabelID.setTextColor(ContextCompat.getColor(getContext(), (hojasDetalleSinEnviar > 0) ? R.color.vgm_color : R.color.base_text_color));
            txtReciboLabelID.setTextColor(ContextCompat.getColor(getContext(), (recibosSinEnviar > 0) ? R.color.vgm_color : R.color.base_text_color));
            txtNoAtencionLabelID.setTextColor(ContextCompat.getColor(getContext(), (noPedidosSinEnviar > 0) ? R.color.vgm_color : R.color.base_text_color));
            txtUbicGeograficaLabelID.setTextColor(ContextCompat.getColor(getContext(), (clientesSinEnviar > 0) ? R.color.vgm_color : R.color.base_text_color));
            txtEgresoLabelID.setTextColor(ContextCompat.getColor(getContext(), (egresosSinEnviar > 0) ? R.color.vgm_color : R.color.base_text_color));

        } catch (SQLiteDiskIOException sqlE) {
            btnDescarga.setEnabled(true);
            btnEnviar.setEnabled(false);
            String zero = "0";
            this.txtPedidosEnviados.setText(zero);
            this.txtPedidosSinEnviar.setText(zero);
            this.txtPedidosAnulados.setText(zero);
            this.txtPedidosAnuladosEnviados.setText(zero);
            this.txtPedidosAnuladosSinEnviar.setText(zero);
            this.txtRecibosEnviados.setText(zero);
            this.txtRecibosSinEnviar.setText(zero);
            this.txtNoPedidosEnviados.setText(zero);
            this.txtNoPedidosSinEnviar.setText(zero);
            this.txtClientesEnviados.setText(zero);
            this.txtClientesSinEnviar.setText(zero);
            /*/this.txtUbicacionesGeograficasSinEnviar.setText(zero);*/
            /*/this.txtUbicacionesGeograficasEnviadas.setText(zero);*/
            /*/this.txtEntregasSinEnviar.setText(zero);*/
            /*//this.txtEntregasEnviadas.setText(zero);*/
            this.txtEgresosSinEnviar.setText(zero);
            this.txtEgresosEnviados.setText(zero);
        } catch (Exception e) {
            e.printStackTrace();
            this.btnEnviar.setEnabled(false);
            ErrorManager.manageException(TAG, "loadData", e);
        }
    }


    private void btnEnviarOnClick(View v) {
        sincronizar(ENVIO);
    }

    private void btnDescargaClick() {
        getActivity().stopService(new Intent(getActivity(), GPSService.class));
        sincronizar(DESCARGA);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.mn_sincronizacion, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mniSincronizacion:

                Fragment fragment = MenuManager.getFragment(ItemMenuNames.POS_ESTADO_CONEXION);
                getNavigationMenu().addFragment(fragment, ItemMenuNames.STRING_ESTADO_CONEXION);
                return true;
            case R.id.mniBuscarConexion:
                checkConexion();
                loadData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void setAutoOrientationEnabled(Context context, boolean enabled) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, enabled ? 1 : 0);
    }

    protected void sincronizar(int modo) {
        this.modo = modo;
        setAutoOrientationEnabled(getContext(), false);
        String msj = (modo == DESCARGA) ? getActivity().getString(R.string.sincronizando) : getActivity().getString(R.string.enviando);
        progressDialogFragment = ProgressDialogFragment.newInstance(msj);
        progressDialogFragment.show(getFragmentManager
                (), "Sincronizando");
        task = new SincronizacionTask(getActivity().getApplicationContext(), modo, getListenerSincronizacion());
        task.execute((Void) null);
        try {
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            String error = e.toString();
        }
    }

    protected void checkConexion() {
        try {
            setAutoOrientationEnabled(getContext(), false);
            progressDialogFragment = ProgressDialogFragment.newInstance(getActivity().getString(R.string.tituloBuscandoConexion));
            progressDialogFragment.show(getFragmentManager(), "myDialogFragment");
            SincronizacionBo sincronizacionBo = new SincronizacionBo(getContext(), _repoFactory);
            sincronizacionBo.isUrlActiva(getActivity().getApplicationContext(), getBuscarConexionListener());
        } catch (IllegalStateException i) {
            i.printStackTrace();
        }
    }

    private void reiniciarServicioGps() throws Exception {
        //Reiniciar el servicio
        getActivity().stopService(new Intent(getActivity(), GPSService.class));
        //Consulto en tabla Empresas por si se aplica la funcionalidad y valido que este dentro del rango horario de trabajo
        if (_empresaBo.isRegistrarLocalizacion() && ComparatorDateTime.validarRangoHorarioEnvioLocalizacion()) {
            getActivity().startService(new Intent(getActivity(), GPSService.class));
        }
    }

    private BuscarConexionListener getBuscarConexionListener() {
        BuscarConexionListener listener = new BuscarConexionListener() {

            @Override
            public void onError(String error) {
                progressDialogFragment.dismiss();
                simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, getString(R.string.msjConexionFallida), getString(R.string.sincronizando), new SimpleDialogFragment.OkListener() {

                    @Override
                    public void onOkSelected() {

                    }
                });
                //getFragmentManager().beginTransaction().add(simpleDialogFragment, "tag").commitAllowingStateLoss();
                getParentFragmentManager().beginTransaction().add(simpleDialogFragment, "tag").commitAllowingStateLoss();
                setAutoOrientationEnabled(getContext(), isRotationEnabled);
                btnDescarga.setEnabled(false);
                btnEnviar.setEnabled(false);
            }

            @Override
            public void onDone() {
                if (isAdded() && getActivity() != null) {
                    progressDialogFragment.dismiss();
                    btnDescarga.setEnabled(true);
                    setAutoOrientationEnabled(getContext(), isRotationEnabled);
                }
            }
        };
        return listener;
    }

    @Override
    public void onCancelProgress() {

    }
}