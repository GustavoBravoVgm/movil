package com.ar.vgmsistemas.view.configuracion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.task.TestConnection;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class FrmConfiguracionConexion extends Fragment {
    private TextInputEditText txtUrlRemotaServidor;
    private TextInputEditText txtUrlRemotaServidor2;
    private TextInputEditText txtUrlLocalServidor;
    private TextInputEditText txtUrlFtp;
    private TextInputEditText txtFtpFolder;
    private TextInputEditText txtFtpUserName;
    private TextInputEditText txtFtpPassword;
    private TextInputEditText txtIntervalo;
    private MaterialButton btnTestConexionLocalServidor;
    private MaterialButton btnTestConexionRemotaServidor;
    private MaterialButton btnTestConexionRemotaServidor2;
    private MaterialButton btnTestConexionFtp;
    //private Button btnActualizaConfConexion;
    private TextInputEditText txtTimeOutAlto;
    private TextInputEditText txtTimeOutMedio;
    private TextInputEditText txtTimeOutBajo;
    private static final String EMPTY_STRING = "";
    //private static final String TAG_FRM_CONF_CONEXION = "FrmConfigConex";
    //private ProgressDialogFragment progressDialogFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyt_configuracion_conexion, container, false);
        initComponents(view);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        setValues();
    }

    @Override
    public void onStart() {
        super.onStart();
        /*initComponents();*/
        loadData();
    }

    private void initComponents(View view) {
        // txtIntervaloSincronizacion
        this.txtIntervalo = view.findViewById(R.id.txtInter);
        // txtUrlLocalServidor
        this.txtUrlLocalServidor = view.findViewById(R.id.txtUrlLocalServidor);
        // txtUrlRemotaServidor
        this.txtUrlRemotaServidor = view.findViewById(R.id.txtUrlRemotaServidor);
        // txtUrlRemotaServidor2
        this.txtUrlRemotaServidor2 = view.findViewById(R.id.txtUrlRemotaServidor2);
        // txtUrlFtp
        this.txtUrlFtp = view.findViewById(R.id.txtUrlFtp);
        //txtFtpFolder
        this.txtFtpFolder = view.findViewById(R.id.txtFtpFolder);
        // txtFtpUserName
        this.txtFtpUserName = view.findViewById(R.id.txtFtpUserName);
        // txtFtpPassword
        this.txtFtpPassword = view.findViewById(R.id.txtFtpPassword);
        // btnTestConexionLocalServidor
        this.btnTestConexionLocalServidor = view.findViewById(R.id.btnTestConexionLocalServidorCnf);
        this.btnTestConexionLocalServidor.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btnTestConexionLocalServidorOnClick();
            }
        });
        // btnTestConexionRemotaServidor
        this.btnTestConexionRemotaServidor = view.findViewById(R.id.btnTestConexionRemotaServidor);
        this.btnTestConexionRemotaServidor.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btnTestConexionRemotaServidorOnClick();
            }
        });
        // btnTestConexionRemotaServidor2
        this.btnTestConexionRemotaServidor2 = view.findViewById(R.id.btnTestConexionRemotaServidor2);
        this.btnTestConexionRemotaServidor2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btnTestConexionRemotaServidor2OnClick();
            }
        });
        // btnTestConexionFtp
        this.btnTestConexionFtp = view.findViewById(R.id.btnTestConexionFtp);
        this.btnTestConexionFtp.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                probarConexionFTP();
            }
        });

        int timeOutAlto = PreferenciaBo.getInstance().getPreferencia(getActivity()).getTimeOutAlto();
        this.txtTimeOutAlto = view.findViewById(R.id.txtTimeOutAlto);
        this.txtTimeOutAlto.setOnFocusChangeListener(new DftTextOnFocusListener(String.valueOf(timeOutAlto / 1000)));

        //txtTimeOutMEDIO
        int timeOutMedio = PreferenciaBo.getInstance().getPreferencia(getActivity()).getTimeOutMedio();
        this.txtTimeOutMedio = view.findViewById(R.id.txtTimeOutMedio);
        this.txtTimeOutMedio.setOnFocusChangeListener(new DftTextOnFocusListener(String.valueOf(timeOutMedio / 1000)));

        //txtTimeOutBAJO
        int timeOutBajo = PreferenciaBo.getInstance().getPreferencia(getActivity()).getTimeOutBajo();
        this.txtTimeOutBajo = view.findViewById(R.id.txtTimeOutBajo);
        this.txtTimeOutBajo.setOnFocusChangeListener(new DftTextOnFocusListener(String.valueOf(timeOutBajo / 1000)));
    }

    private void loadData() {
        String urlLocalServidor = PreferenciaBo.getInstance().getPreferencia(getActivity()).getUrlLocalServidor();
        this.txtUrlLocalServidor.setText(urlLocalServidor);
        String urlRemotaServidor = PreferenciaBo.getInstance().getPreferencia(getActivity()).getUrlRemotaServidor();
        this.txtUrlRemotaServidor.setText(urlRemotaServidor);
        String urlRemotaServidor2 = PreferenciaBo.getInstance().getPreferencia(getActivity()).getUrlRemotaServidor2();
        this.txtUrlRemotaServidor2.setText(urlRemotaServidor2);
        String urlFtp = PreferenciaBo.getInstance().getPreferencia(getActivity()).getUrlFtp();
        this.txtUrlFtp.setText(urlFtp);
        String ftpFolder = PreferenciaBo.getInstance().getPreferencia(getActivity()).getFtpFolder();
        this.txtFtpFolder.setText(ftpFolder);
        String ftpUserName = PreferenciaBo.getInstance().getPreferencia(getActivity()).getFtpUserName();
        this.txtFtpUserName.setText(ftpUserName);
        String ftpPassword = PreferenciaBo.getInstance().getPreferencia(getActivity()).getFtpPassword();
        this.txtFtpPassword.setText(ftpPassword);
        int intervaloSincronizacion = PreferenciaBo.getInstance().getPreferencia(getActivity()).getIntervaloSincronizacionAutomatica();
        this.txtIntervalo.setText(String.valueOf(intervaloSincronizacion));
        //Timeouts
        int timeOutAlto = PreferenciaBo.getInstance().getPreferencia(getActivity()).getTimeOutAlto();
        int timeOutAltoSetear = timeOutAlto / 1000;
        txtTimeOutAlto.setText(String.valueOf(timeOutAltoSetear));
        int timeOutMedio = PreferenciaBo.getInstance().getPreferencia(getActivity()).getTimeOutMedio();
        int timeOutMedioSetear = timeOutMedio / 1000;
        txtTimeOutMedio.setText(String.valueOf(timeOutMedioSetear));
        int timeOutBajo = PreferenciaBo.getInstance().getPreferencia(getActivity()).getTimeOutBajo();
        int timeOutBajoSetear = timeOutBajo / 1000;
        txtTimeOutBajo.setText(String.valueOf(timeOutBajoSetear));
    }

    @Override
    public void onPause() {
        super.onPause();
        setValues();
    }


    private void setValues() {
        String urlRemotaServidor = this.txtUrlRemotaServidor.getText().toString();
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setUrlRemotaServidor(urlRemotaServidor);

        String urlRemotaServidor2 = this.txtUrlRemotaServidor2.getText().toString();
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setUrlRemotaServidor2(urlRemotaServidor2);

        String urlLocalServidor = this.txtUrlLocalServidor.getText().toString();
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setUrlLocalServidor(urlLocalServidor);

        String urlFtp = this.txtUrlFtp.getText().toString();
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setUrlFtp(urlFtp);

        String ftpFolder = this.txtFtpFolder.getText().toString();
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setFtpFolder(ftpFolder);

        String ftpUserName = this.txtFtpUserName.getText().toString();
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setFtpUserName(ftpUserName);

        String ftpPassword = this.txtFtpPassword.getText().toString();
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setFtpPassword(ftpPassword);

        int intervaloSincronizacion = Integer.parseInt(this.txtIntervalo.getText().toString());
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setIntervaloSincronizacionAutomatica(intervaloSincronizacion);

        // Verificar que no sea ""
        String sTimeOutAlto = txtTimeOutAlto.getText().toString();
        if (!sTimeOutAlto.equals(EMPTY_STRING)) {
            int timeOutAlto = Integer.parseInt(sTimeOutAlto);
            int timeOutAltoSetear = timeOutAlto * 1000;
            PreferenciaBo.getInstance().getPreferencia(getActivity()).setTimeOutAlto(timeOutAltoSetear);
        }

        String sTimeOutMedio = txtTimeOutMedio.getText().toString();
        if (!sTimeOutMedio.equals(EMPTY_STRING)) {
            int timeOutMedio = Integer.parseInt(sTimeOutMedio);
            int timeOutMedioSetear = timeOutMedio * 1000;
            PreferenciaBo.getInstance().getPreferencia(getActivity()).setTimeOutMedio(timeOutMedioSetear);
        }

        String sTimeOutBajo = txtTimeOutBajo.getText().toString();
        if (!sTimeOutBajo.equals(EMPTY_STRING)) {
            int timeOutBajo = Integer.parseInt(sTimeOutBajo);
            int timeOutBajoSetear = timeOutBajo * 1000;
            PreferenciaBo.getInstance().getPreferencia(getActivity()).setTimeOutBajo(timeOutBajoSetear);
        }
    }

    private void btnTestConexionLocalServidorOnClick() {
        testConexion(this.txtUrlLocalServidor, TestConnection.SERVER_CONNECTION);

    }


    private void btnTestConexionRemotaServidorOnClick() {
        testConexion(this.txtUrlRemotaServidor, TestConnection.SERVER_CONNECTION);

    }

    private void btnTestConexionRemotaServidor2OnClick() {
        testConexion(this.txtUrlRemotaServidor2, TestConnection.SERVER_CONNECTION);
    }

    private void probarConexionFTP() {
        testConexion(this.txtFtpUserName, TestConnection.FTP_CONNECTION);
    }

    String mensajeResult = "";

    private void testConexion(EditText txtUrl, int typeConnection) {
        String url = txtUrl.getText().toString();

        TestConnection testConnection = new TestConnection(typeConnection, getActivity(), url, url, new TestConnection.TestConnectionListener() {
            @Override
            public void onDone(boolean isConnected) {
                if (isConnected) {
                    mensajeResult = getString(R.string.msjConexionExitosa);
                } else {
                    mensajeResult = getString(R.string.msjErrorConectarServidor);
                }
                SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, getString(R.string.tituloProbarConexion), mensajeResult);
                requireActivity().getSupportFragmentManager().beginTransaction().add(simpleDialogFragment, "tag").commitAllowingStateLoss();
            }

            @Override
            public void onError(String error) {

            }
        });
        testConnection.execute();
    }

    private class DftTextOnFocusListener implements OnFocusChangeListener {
        private String defaultText;

        public DftTextOnFocusListener(String defaultText) {
            this.defaultText = defaultText;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (v instanceof EditText) {
                EditText focusedEditText = (EditText) v;
                // handle obtaining focus
                if (!hasFocus) {
                    // Cuando pierde el foco, si el EditText es EMPTY_STRING le setea el valor por defecto
                    if (focusedEditText.getText().toString().equals(EMPTY_STRING) ||
                            focusedEditText.getText().toString().equals("0")) {
                        focusedEditText.setText(defaultText);
                    }
                }
            }
        }
    }

}
