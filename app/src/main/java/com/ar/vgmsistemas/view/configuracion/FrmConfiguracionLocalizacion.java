package com.ar.vgmsistemas.view.configuracion;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.EmpresaBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.gps.GPSService;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ComparatorDateTime;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.google.android.material.textfield.TextInputEditText;

import java.time.ZoneId;
import java.util.Locale;

public class FrmConfiguracionLocalizacion extends Fragment {
    private TextInputEditText txtIntervaloTiempoLocalizacion;
    private TextInputEditText txtDistanciaMinimaLocalizacion;

    //Configuracion de rangos de envío de localizaciones
    private TextInputEditText txtHoraEntradaManiana;
    private TextInputEditText txtHoraSalidaManiana;
    private TextInputEditText txtHoraEntradaTarde;
    private TextInputEditText txtHoraSalidaTarde;

    private EmpresaBo _empresaBo;

    // DAO
    public RepositoryFactory _repoFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyt_configuracion_localizacion, container, false);
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
        loadData();
    }

    private void initComponents(View view) {

        //txtIntervaloTiempoLocalizacion
        this.txtIntervaloTiempoLocalizacion = view.findViewById(R.id.txtIntervaloTiempoLocalizacion);
        //txtDistanciaMinimaLocalizacion
        this.txtDistanciaMinimaLocalizacion = view.findViewById(R.id.txtDistanciaMinimaLocalizacion);
        //txtHoraEntradaManiana
        this.txtHoraEntradaManiana = view.findViewById(R.id.txtHorarioInicioEnvioM);
        this.txtHoraEntradaManiana.setKeyListener(null);
        //txtHastaManiana
        this.txtHoraSalidaManiana = view.findViewById(R.id.txtHorarioFinEnvioM);
        this.txtHoraSalidaManiana.setKeyListener(null);
        //txtDesdeTarde
        this.txtHoraEntradaTarde = view.findViewById(R.id.txtHorarioInicioEnvioT);
        this.txtHoraEntradaTarde.setKeyListener(null);
        //txtHastaTarde
        this.txtHoraSalidaTarde = view.findViewById(R.id.txtHorarioFinEnvioT);
        this.txtHoraSalidaTarde.setKeyListener(null);

    }


    private void loadData() {
        this._repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);

        //Obtenemos y seteamos valores de preferencias

        long intervaloTiempoLocalizacion = PreferenciaBo.getInstance().getPreferencia(getActivity()).getIntervaloTiempoLocalizacion();
        long intervaloTiempoLocalizacionASetear = intervaloTiempoLocalizacion / 1000;
        this.txtIntervaloTiempoLocalizacion.setText(String.valueOf(intervaloTiempoLocalizacionASetear));

        long distanciaMinimaLocalizacion = PreferenciaBo.getInstance().getPreferencia(getActivity()).getDistanciaMinimaLocalizacion();
        this.txtDistanciaMinimaLocalizacion.setText(String.valueOf(distanciaMinimaLocalizacion));

        //Valores a mostrar en los editText
        int horaEntradaManiana = PreferenciaBo.getInstance().getPreferencia(getActivity()).getHoraEntradaManana().getHours();
        int minutosEntradaManiana = PreferenciaBo.getInstance().getPreferencia(getActivity()).getHoraEntradaManana().getMinutes();

        int horaSalidaManiana = PreferenciaBo.getInstance().getPreferencia(getActivity()).getHoraSalidaManana().getHours();
        int minutosSalidaManiana = PreferenciaBo.getInstance().getPreferencia(getActivity()).getHoraSalidaManana().getMinutes();

        int horaEntradaTarde = PreferenciaBo.getInstance().getPreferencia(getActivity()).getHoraEntradaTarde().getHours();
        int minutosEntradaTarde = PreferenciaBo.getInstance().getPreferencia(getActivity()).getHoraEntradaTarde().getMinutes();

        int horaSalidaTarde = PreferenciaBo.getInstance().getPreferencia(getActivity()).getHoraSalidaTarde().getHours();
        int minutosSalidaTarde = PreferenciaBo.getInstance().getPreferencia(getActivity()).getHoraSalidaTarde().getMinutes();

        //Valores a mostrar en los editText de horarios

        String horaMinutosEntradaManiana = String.format(Locale.US, "%02d:%02d", horaEntradaManiana, minutosEntradaManiana);
        String horaMinutosSalidaManiana = String.format(Locale.US, "%02d:%02d", horaSalidaManiana, minutosSalidaManiana);
        String horaMinutosEntradaTarde = String.format(Locale.US, "%02d:%02d", horaEntradaTarde, minutosEntradaTarde);
        String horaMinutosSalidaTarde = String.format(Locale.US, "%02d:%02d", horaSalidaTarde, minutosSalidaTarde);

        this.txtHoraEntradaManiana.setText(horaMinutosEntradaManiana);
        this.txtHoraSalidaManiana.setText(horaMinutosSalidaManiana);
        this.txtHoraEntradaTarde.setText(horaMinutosEntradaTarde);
        this.txtHoraSalidaTarde.setText(horaMinutosSalidaTarde);

        this._empresaBo = new EmpresaBo(this._repoFactory);

    }


    @Override
    public void onPause() {
        super.onPause();
        setValues();
        try {
            reiniciarServicioGps();
        } catch (Exception e) {
            ErrorManager.manageException("FrmConfiguracionLocalizacion", "onKeyDown", e, getActivity(), "Error", "Error al reiniciar el servicio de GPS");
        }
    }


    private void setValues() {

        long intervaloTiempoLocalizacion = Long.parseLong(this.txtIntervaloTiempoLocalizacion.getText().toString());
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setIntervaloTiempoLocalizacion(intervaloTiempoLocalizacion * 1000);

        long distanciaMinimaLocalizacion = Long.parseLong(this.txtDistanciaMinimaLocalizacion.getText().toString());
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setDistanciaMinimaLocalizacion(distanciaMinimaLocalizacion);

    }

    private void reiniciarServicioGps() throws Exception {
        boolean isRegistrarLocalizacion = false;
        //Reiniciar el servicio
        if (getActivity() != null) {
            getActivity().stopService(new Intent(getActivity(), GPSService.class));
        }
        // En caso de que tenga base de datos verifico la variable de configuracion

        if (_repoFactory.dataBaseExists()) {

            if (_empresaBo.existsTableEmpresas()) {
                isRegistrarLocalizacion = _empresaBo.isRegistrarLocalizacion();
            }
        }

        //Consulto en tabla Empresas por si se aplica la funcionalidad y valido que este dentro del rango horario de trabajo
        if (isRegistrarLocalizacion && ComparatorDateTime.validarRangoHorarioEnvioLocalizacion()) {
            getActivity().startService(new Intent(getActivity(), GPSService.class));
        }
    }

}
