package com.ar.vgmsistemas.view.nopedido;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.EmpresaBo;
import com.ar.vgmsistemas.bo.NoPedidoBo;
import com.ar.vgmsistemas.bo.VendedorBo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.MotivoNoPedido;
import com.ar.vgmsistemas.entity.NoPedido;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.AlertDialog;
import com.ar.vgmsistemas.view.BaseActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

public class FrmGestionNoPedido extends BaseActivity {

    private Cliente _cliente;
    private Spinner cmbMotivo;
    private List<MotivoNoPedido> _motivos;
    private TextInputEditText txtObservacion;
    private TextInputEditText txtFecha;
    private TextInputEditText txtCliente;
    private NoPedidoBo _noPedidoBo;
    private RepositoryFactory _repoFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_no_pedido);
        setActionBarTitle(R.string.lblNoAtencion);
        Bundle b = this.getIntent().getExtras();
        _cliente = (Cliente) b.getSerializable("cliente");
        initComponents();
        initVar();
        loadData();
    }

    private void btnCancelarOnClick(View v) {
        finish();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    private void btnAceptarOnClick(View v) {
        //Toast.makeText(this, "Guardando", Toast.LENGTH_LONG).show();
        NoPedido noPedido = new NoPedido();
        noPedido.setCliente(_cliente);
        int indexMotivo = cmbMotivo.getSelectedItemPosition();
        noPedido.setMotivoNoPedido(_motivos.get(indexMotivo));
        noPedido.setObservacion(txtObservacion.getText().toString());

        try {
            noPedido.setFechaNoPedido(Formatter.convertToDateTime(txtFecha.getText().toString(), "dd/MM/yyyy HH:mm:ss"));
        } catch (ParseException e2) {
            AlertDialog alertDialog = new AlertDialog(this, "Error", "Error al convertir la fecha");
            alertDialog.show();
        }

        noPedido.setVendedor(VendedorBo.getVendedor());
        //Seteo la fecha de registro movil
        noPedido.setFechaRegistroMovil(Calendar.getInstance().getTime());

        EmpresaBo empresaBo = new EmpresaBo(_repoFactory);
        boolean prendido = false;
        try {
            prendido = empresaBo.gpsValido(getApplicationContext());
        } catch (Exception e) {
            ErrorManager.manageException("FrmGestionNoPedido", "btnAceptarOnClick", e);
            AlertDialog alertDialog = new AlertDialog(this, "Error", "Error al comunicarse con el proveedor de GPS");
            alertDialog.show();
        }

        if (prendido) {
            Toast.makeText(this, "Guardando", Toast.LENGTH_LONG).show();
            try {
                _noPedidoBo.create(noPedido, this);
                this.finish();
            } catch (Exception e) {
                ErrorManager.manageException("FrmGestionNoPedido", "btnAceptarOnClick", e, this);
                this.finish();
            }

        } else {
            notificarGpsApagado();
        }

    }

    private void notificarGpsApagado() {
        ErrorManager.manageException("DlgBldrOpcionesCliente", "dialogoOpcionesClienteOnClick",
                new Exception(), this, R.string.tituloErrorGps, R.string.msjErrorGpsApagadoNoAtencion);
    }

    private void initComponents() {
        // TextView fecha(TextView)
        txtFecha = findViewById(R.id.txtFechaNoPedido);
        txtFecha.setKeyListener(null);
        txtFecha.setFocusable(false);

        //TextView Cliente(EditText)
        txtCliente = findViewById(R.id.txtComercio);
        txtCliente.setKeyListener(null);
        txtCliente.setFocusable(false);

        //EditText observacion(EditText)
        txtObservacion = findViewById(R.id.txtObservacion);

        //spinner motivos(Spinner)
        cmbMotivo = findViewById(R.id.cmbMotivoNoPedido);

        //Button aceptar (Button)
        MaterialButton btnAceptar = findViewById(R.id.btnAceptarNoPedido);
        btnAceptar.setOnClickListener(this::btnAceptarOnClick);

        //Button cancelar(Button)
        MaterialButton btnCancelar = findViewById(R.id.btnCancelarNoPedido);
        btnCancelar.setOnClickListener(v -> btnCancelarOnClick(v));

    }

    private void initVar() {
        this._repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
        _noPedidoBo = new NoPedidoBo(this._repoFactory);
    }

    private void loadData() {
        String fecha = Formatter.formatDateTime(Calendar.getInstance().getTime());
        txtFecha.setText(fecha);
        txtCliente.setText(_cliente.getRazonSocial());
        try {
            _motivos = _noPedidoBo.recoveryAllMotivo();
            ArrayAdapter<MotivoNoPedido> adapter = new ArrayAdapter<>(
                    this, R.layout.simple_spinner_item, _motivos);
            cmbMotivo.setAdapter(adapter);

        } catch (Exception e) {
            ErrorManager.manageException("FrmGestionNoPedido", "loadData", e, this);
        }
    }

}
