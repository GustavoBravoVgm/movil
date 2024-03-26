package com.ar.vgmsistemas.view.configuracion;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.utils.ComparatorDateTime;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FrmConfiguracionRegistroPedidos extends Fragment {

    private TextInputEditText txtTipoDocumento;
    private TextInputEditText txtPuntoVenta;
    private TextInputEditText txtTipoDocumentoRecibo;
    private TextInputEditText txtPuntoVentaRecibo;
    private TextInputEditText txtRepartidor;
    private TextInputEditText txtVendedor;
    private Spinner cmbTipoCodigoEmpresa;
    private String tipoDatoArticulo = "";

    //Configuracion envio de pedidos
    private RadioButton rbEnvioTodoElDia;
    private RadioButton rbEnvioRango;
    private TextInputLayout lblDesdeEnvio;
    private TextInputLayout lblHastaEnvio;
    private TextInputEditText txtDesdeEnvio;
    private TextInputEditText txtHastaEnvio;
    private CheckBox chkCambiarPtoVta;

    //Datos para mostrar en el EditText
    private int horaInicio;
    private int minutosInicio;
    private int horaFin;
    private int minutosFin;

    //Variables static para los TimePickers
    private static final int DIALOG_DESDE = 0;
    private static final int DIALOG_HASTA = 1;

    private static final String FRM_CONF_REG_PED = "Configuracion de registro de pedidos";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyt_configuracion_de_registro_de_pedidos, container, false);
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

    private void loadData() {

        String idTipoDocumento = PreferenciaBo.getInstance().getPreferencia(getActivity()).getIdTipoDocumentoPorDefecto();
        txtTipoDocumento.setText(idTipoDocumento);

        int idPuntoVenta = PreferenciaBo.getInstance().getPreferencia(getActivity()).getIdPuntoVentaPorDefecto();
        txtPuntoVenta.setText(String.valueOf(idPuntoVenta));

        String idTipoDocumentoRecibo = PreferenciaBo.getInstance().getPreferencia(getActivity()).getIdTipoDocumentoRecibo();
        txtTipoDocumentoRecibo.setText(idTipoDocumentoRecibo);

        int idPuntoVentaRecibo = PreferenciaBo.getInstance().getPreferencia(getActivity()).getIdPuntoVentaPorDefectoRecibo();
        txtPuntoVentaRecibo.setText(String.valueOf(idPuntoVentaRecibo));

        int idRepartidor = PreferenciaBo.getInstance().getPreferencia(getActivity()).getIdRepartidorPorDefecto();
        txtRepartidor.setText(String.valueOf(idRepartidor));

        int idVendedor = PreferenciaBo.getInstance().getPreferencia(getActivity()).getIdVendedor();
        txtVendedor.setText(String.valueOf(idVendedor));

        String tipoCodigoEmpresa = PreferenciaBo.getInstance().getPreferencia(getActivity()).getTipoDatoIdArticulo();

        //cargo el spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.tipoDatoIdEmpresa,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbTipoCodigoEmpresa.setAdapter(adapter);

        //le seteo el valor cargado en preferencias.xml
        int spinnerPosition;
        SpinnerAdapter spinnerAdapter = cmbTipoCodigoEmpresa.getAdapter();
        if (spinnerAdapter != null) {
            for (int i = 0; i < spinnerAdapter.getCount(); i++) {
                String valor = spinnerAdapter.getItem(i).toString();
                if (valor.equalsIgnoreCase(tipoCodigoEmpresa)) {
                    spinnerPosition = i;
                    cmbTipoCodigoEmpresa.setSelection(spinnerPosition);
                    break;
                }
            }
        }

        //Inicializo las variables

        String horaInicio = String.valueOf(PreferenciaBo.getInstance().getPreferencia(getActivity()).getHoraInicioEnvio());
        String minutosInicio = String.valueOf(PreferenciaBo.getInstance().getPreferencia(getActivity()).getMinutosInicioEnvio());
        String horaFin = String.valueOf(PreferenciaBo.getInstance().getPreferencia(getActivity()).getHoraFinEnvio());
        String minutosFin = String.valueOf(PreferenciaBo.getInstance().getPreferencia(getActivity()).getMinutosFinEnvio());
        String horaMinutosInicio = horaInicio + " : " + minutosInicio;
        String horaMinutosFin = horaFin + " : " + minutosFin;

        //Seteo la Configuracion de envio de pedidos
        if (!PreferenciaBo.getInstance().getPreferencia(getActivity()).getIsEnvioPedidosPorFranja()) {
            //Habilito el envio de pedidos para todo el dia
            this.rbEnvioTodoElDia.setChecked(true);
        } else {
            //Habilito el envio de pedidos dentro del rango de horas definido
            this.rbEnvioRango.setChecked(true);
            this.txtDesdeEnvio.setText(horaMinutosInicio);
            this.lblDesdeEnvio.setEnabled(true);
            this.txtDesdeEnvio.setEnabled(true);
            this.txtHastaEnvio.setText(horaMinutosFin);
            this.lblHastaEnvio.setEnabled(true);
            this.txtHastaEnvio.setEnabled(true);
        }
        //cargo cambio de ptovta
        this.chkCambiarPtoVta.setChecked(PreferenciaBo.getInstance().getPreferencia(getActivity()).isCambiarPtoVta());

    }

    @Override
    public void onPause() {
        super.onPause();
        //Valido la configuracion de envio de pedidos
        if (validarConfiguracionEnvioPedidos()) {
            setValues();
        } else {
            //Rango de fechas inválido
            SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, getString(R.string.rangoInvalido));
            simpleDialogFragment.show(requireActivity().getSupportFragmentManager(), FRM_CONF_REG_PED);
        }

    }


    private boolean validarConfiguracionEnvioPedidos() {

        boolean isCorrecto = false;

        if (PreferenciaBo.getInstance().getPreferencia().getIsEnvioPedidosPorFranja()) {

            if ((this.txtDesdeEnvio.getText().length() != 0) && (this.txtHastaEnvio.getText().length() != 0)) {
                //Valido el rango de fechas

                //Armo los Strings de comparación
                String horaMinutosInicio = horaInicio + ":" + minutosInicio;

                String horaMinutosFin = horaFin + ":" + minutosFin;

                //Comparo las fechas
                if (ComparatorDateTime.compareTimeRange(horaMinutosInicio, horaMinutosFin)) {
                    //Rango valido - devuelvo True
                    isCorrecto = true;
                }
            }

        } else {
            //Como no esta habilitado el envío por rango de fechas, devuelvo true
            isCorrecto = true;
        }

        return isCorrecto;
    }

    private String convertirAMayuscula(String input) {
        StringBuilder salida = new StringBuilder();
        char caracter;
        for (int i = 0; i < input.length(); i++) {
            caracter = input.charAt(i);
            if (caracter != ' ') { //quito espacios en blanco
                if (Character.isLowerCase(caracter)) {
                    caracter = (char) (caracter - 'a' + 'A');
                }
                salida.append(caracter);
            }
        }
        return salida.toString();
    }

    private void setValues() {

        String idTipoDocumento = convertirAMayuscula(txtTipoDocumento.getText().toString());
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setIdTipoDocumentoPorDefecto(idTipoDocumento);

        String idTipoDocumentoRecibo = convertirAMayuscula(txtTipoDocumentoRecibo.getText().toString().toUpperCase());

        PreferenciaBo.getInstance().getPreferencia(getActivity()).setIdTipoDocumentoRecibo(idTipoDocumentoRecibo);

        String pvRecibo = txtPuntoVentaRecibo.getText().toString();
        if (!(pvRecibo.equals(""))) {
            int sPuntoVentaRecibo = Integer.parseInt(pvRecibo);
            PreferenciaBo.getInstance().getPreferencia(getActivity()).setIdPuntoVentaPorDefectoRecibo(sPuntoVentaRecibo);
        }


        String sPuntoVenta = txtPuntoVenta.getText().toString();
        if (!sPuntoVenta.equals("")) {
            int idPuntoVenta = Integer.parseInt(sPuntoVenta);
            PreferenciaBo.getInstance().getPreferencia(getActivity()).setIdPuntoVentaPorDefecto(idPuntoVenta);
        }

        String sRepartidor = txtRepartidor.getText().toString();
        if (!sRepartidor.equals("")) {
            int idRepartidor = Integer.parseInt(sRepartidor);
            PreferenciaBo.getInstance().getPreferencia(getActivity()).setIdRepartidorPorDefecto(idRepartidor);
        }

        String sVendedor = txtVendedor.getText().toString();
        if (!sVendedor.equals("")) {
            int idVendedor = Integer.parseInt(sVendedor);
            PreferenciaBo.getInstance().getPreferencia(getActivity()).setIdVendedor(idVendedor);
        }

        PreferenciaBo.getInstance().getPreferencia(getActivity()).setTipoDatoIdArticulo(this.tipoDatoArticulo);

        //Seteo el rango de horas en caso de serlo

        if (this.rbEnvioRango.isChecked()) {
            //Guardo los valores seteados

            //Hora y minutos de inicio
            PreferenciaBo.getInstance().getPreferencia(getActivity()).setHoraInicioEnvio(horaInicio);
            PreferenciaBo.getInstance().getPreferencia(getActivity()).setMinutosInicioEnvio(minutosInicio);
            //Hora y minutos de fin
            PreferenciaBo.getInstance().getPreferencia(getActivity()).setHoraFinEnvio(horaFin);
            PreferenciaBo.getInstance().getPreferencia(getActivity()).setMinutosFinEnvio(minutosFin);

            //Habilito el ingreso de pedidos por rango de fechas
            PreferenciaBo.getInstance().getPreferencia(getActivity()).setIsEnvioPedidosPorFranja(true);

        } else {
            //Deshabilito el envío de pedidos por el rango de fechas
            PreferenciaBo.getInstance().getPreferencia(getActivity()).setIsEnvioPedidosPorFranja(false);
        }

        //seteo cambio de pto vta
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setCambiarPtoVta(this.chkCambiarPtoVta.isChecked());
    }


    private void initComponents(View view) {

        //txtTipoDocumento (EditText)
        this.txtTipoDocumento = view.findViewById(R.id.txtTipoDocumento);

        //cmbPuntoVenta (EditText)
        this.txtPuntoVenta = view.findViewById(R.id.txtPuntVenta);

        //txtTipoDocumentoRecibo (EditText)
        this.txtTipoDocumentoRecibo = view.findViewById(R.id.etDocRecibo);

        //cmbPuntoVentaRecibo (EditText)
        this.txtPuntoVentaRecibo = view.findViewById(R.id.etPtoVtaRecibo);


        //cmbPuntoRepartidor(EditText)
        this.txtRepartidor = view.findViewById(R.id.txtRepartidor);

        //txtVendedor(EditText)
        this.txtVendedor = view.findViewById(R.id.txtVendedor);

        //cmbTipoCodigoEmpresa(Spinner)
        this.cmbTipoCodigoEmpresa = view.findViewById(R.id.cmbTipoCodigoEmpresa);
        this.cmbTipoCodigoEmpresa.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
                cmbTipoCodigoEmpresaOnItemSelected(adapter, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //rbEnvioPedidos(TextView)
        this.lblDesdeEnvio = view.findViewById(R.id.lblDesde);
        this.lblHastaEnvio = view.findViewById(R.id.lblHasta);

        //Seteo horas y minutos actuales para los TimePickers

        //final Calendar c = Calendar.getInstance();
        horaInicio = PreferenciaBo.getInstance().getPreferencia(getActivity()).getHoraInicioEnvio();//= c.getTime().getHours();
        minutosInicio = PreferenciaBo.getInstance().getPreferencia(getActivity()).getMinutosInicioEnvio();//= c.getTime().getMinutes();

        horaFin = PreferenciaBo.getInstance().getPreferencia(getActivity()).getHoraFinEnvio(); //= c.getTime().getHours();
        minutosFin = PreferenciaBo.getInstance().getPreferencia(getActivity()).getMinutosFinEnvio();//= c.getTime().getMinutes();

        //txtDesdeEnvio(EditText)
        this.txtDesdeEnvio = view.findViewById(R.id.txtHorarioInicioEnvio);
        this.txtDesdeEnvio.setKeyListener(null);
        this.txtDesdeEnvio.setOnClickListener(v -> touchRangoDesde());

        //txtHastaEnvio(EditText)
        this.txtHastaEnvio = view.findViewById(R.id.txtHorarioFinEnvio);
        this.txtHastaEnvio.setKeyListener(null);
        this.txtHastaEnvio.setOnClickListener(v -> touchRangoHasta());

        //rbEnvioTodoElDia(RadioButton)
        this.rbEnvioTodoElDia = view.findViewById(R.id.rbTodoElDia);
        this.rbEnvioTodoElDia.setOnClickListener(v -> touchEnvioTodoDia());

        //rbEnvioRango(RadioButton)
        this.rbEnvioRango = view.findViewById(R.id.rbRangoHorario);
        this.rbEnvioRango.setOnClickListener(v -> touchEnvioRango());

        //cambio ptovta(CheckBox)
        this.chkCambiarPtoVta = view.findViewById(R.id.chkCambiarPtoVta);
    }

    private void touchEnvioTodoDia() {
        //Deshabilito los lbl y txt Desde / Hasta
        this.lblDesdeEnvio.setEnabled(false);
        this.txtDesdeEnvio.setEnabled(false);
        this.txtDesdeEnvio.setKeyListener(null);
        this.lblHastaEnvio.setEnabled(false);
        this.txtHastaEnvio.setEnabled(false);
        this.txtHastaEnvio.setKeyListener(null);

        PreferenciaBo.getInstance().getPreferencia(getActivity()).setIsEnvioPedidosPorFranja(false);
    }

    private void touchEnvioRango() {
        //Habilito los lbl y txt Desde / hasta
        this.lblDesdeEnvio.setEnabled(true);
        this.txtDesdeEnvio.setEnabled(true);
        this.lblHastaEnvio.setEnabled(true);
        this.txtHastaEnvio.setEnabled(true);

        PreferenciaBo.getInstance().getPreferencia(getActivity()).setIsEnvioPedidosPorFranja(true);
    }

    private void touchRangoDesde() {
        //Abro el Dialog timePicker
        //Seteo el valor de hora y minutos en el editText
        getActivity().showDialog(DIALOG_DESDE);
    }

    private void touchRangoHasta() {
        //Abro el Dialog timePicker
        //Seteo el valor de hora y minutos en el editText
        getActivity().showDialog(DIALOG_HASTA);
    }


    //Método Callback - aca vuelve cuando retorno desde el timepicker DESDE
    private TimePickerDialog.OnTimeSetListener mTimeSetListenerDESDE =
            new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    //Seteo las variables
                    horaInicio = hourOfDay;
                    minutosInicio = minute;

                    //Actualizo el EditText DESDE
                    String horaMinutosInicio = hourOfDay + " : " + minute;
                    txtDesdeEnvio.setText(horaMinutosInicio);
                }
            };

    //Método Callback - aca vuelve cuando retorno desde el timepicker HASTA
    private TimePickerDialog.OnTimeSetListener mTimeSetListenerHASTA =
            new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    //Seteo las variables
                    horaFin = hourOfDay;
                    minutosFin = minute;

                    //ACTUALIZO el EditText HASTA
                    String horaMinutosFin = hourOfDay + " : " + minute;
                    txtHastaEnvio.setText(horaMinutosFin);
                }
            };

    private void cmbTipoCodigoEmpresaOnItemSelected(AdapterView<?> adapter, int position) {
        if (position >= 0) {
            this.tipoDatoArticulo = (String) adapter.getSelectedItem();
        }
    }

}
