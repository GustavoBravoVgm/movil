package com.ar.vgmsistemas.view.cobranza;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.CuentaCorrienteBo;
import com.ar.vgmsistemas.bo.EmpresaBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.Empresa;
import com.ar.vgmsistemas.entity.MotivoAutorizacion;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.cobranza.MotivosAutorizacionDialogFragment.MotivoAutorizacionListener;

@SuppressLint("NewApi")
public class FrmResumenCuentaCorriente extends AppCompatActivity {

    private static final int DIALOG_CODIGO_AUTORIZACION_INVALIDO = 0;

    public static final String MOTIVO = "motivo";
    public static final String OBSERVACION = "observacion";
    public static final int RESULT_CON_MOTIVO = 325;

    private TextView txtSaldoVencido;
    private TextView txtSaldoTotal;
    private TextView txtLimiteDisponibilidad;
    private TextView txtLimiteCredito;
    private Button btnVerDetallesCuentaCorriente;
    private Button btnAutorizar;
    private Button btnCancelar;
    private Cliente cliente;

    private RepositoryFactory _repoFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Resumen cuenta corriente");
        setContentView(R.layout.lyt_resumen_cuenta_corriente);
        getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

        Bundle b = getIntent().getExtras();
        this.cliente = (Cliente) b.getSerializable("cliente");

        initComponents();
        loadData();
    }

    private void btnVerDetallesOnclick() {
        Intent intent = new Intent(this, FrmCuentaCorriente.class);
        intent.putExtra("cliente", cliente);
        startActivity(intent);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DIALOG_CODIGO_AUTORIZACION_INVALIDO:
                dialog = getDialogCodigoAutorizacionInvalido();
                break;
        }
        return dialog;
    }

    private Dialog getDialogCodigoAutorizacionInvalido() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloError);
        builder.setMessage(ErrorManager.CodigoAutorizacionInvalido);
        builder.setPositiveButton(R.string.btnAceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    private void promptCodigoAutorizacion() {

        Builder alert = new Builder(this);
        alert.setTitle(R.string.tituloAutorizar);
        alert.setMessage(R.string.msjIngreseCodigoAutorizacion);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setView(input);
        alert.setPositiveButton(R.string.btnAceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                promptCodigoAutorizacionOnClick(input);
            }
        });
        alert.setNegativeButton(R.string.btnCancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    private void promptCodigoAutorizacionOnClick(EditText input) {
        String codigoAutorizacion = input.getText().toString();
        CuentaCorrienteBo cuentaCorrienteBo = new CuentaCorrienteBo(_repoFactory);
        if (cuentaCorrienteBo.validarCodigoAutorizacion(codigoAutorizacion)) {
            Bundle b = new Bundle();
            b.putString("codigoAutorizacion", codigoAutorizacion);
            Intent intent = new Intent();
            intent.putExtras(b);
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else {
            showDialog(DIALOG_CODIGO_AUTORIZACION_INVALIDO);
        }
    }

    private void btnAutorizarOnClick() {
        if (PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).getIsCobranzaEstricta().equals(Empresa.SI)) {
            this.promptCodigoAutorizacion();
        } else {
            showDialogMotivoAutorizacion();
        }

    }

    private void showDialogMotivoAutorizacion() {
        MotivosAutorizacionDialogFragment autorizacionDialogFragment = MotivosAutorizacionDialogFragment.newInstance(MotivoAutorizacion.TI_AUTORIZACION_CUENTA_CORRIENTE, new MotivoAutorizacionListener() {
            @Override
            public void onAccept(MotivoAutorizacion autorizacion, String observacion) {

                Intent intentResult = new Intent();
                intentResult.putExtra(MOTIVO, autorizacion);
                intentResult.putExtra(OBSERVACION, observacion);
                setResult(RESULT_CON_MOTIVO, intentResult);
                finish();
            }
        });
        autorizacionDialogFragment.show(getSupportFragmentManager(), "dialog");
    }

    private void btnCancelarOnClick() {
        setResult(RESULT_CANCELED);

        this.finish();
    }

    private void loadData() {
        this._repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
        CuentaCorrienteBo cuentaCorrienteBo = new CuentaCorrienteBo(_repoFactory);
        try {
            EmpresaBo empresaBo = new EmpresaBo(_repoFactory);
            double saldoVencido = cuentaCorrienteBo.getTotalSaldoVencido(cliente, empresaBo.recoveryEmpresa().getSnClienteUnico());
            double saldoTotal = cuentaCorrienteBo.getTotalSaldo(cliente, empresaBo.recoveryEmpresa().getSnClienteUnico());
            double limiteDisponibilidad = cliente.getLimiteDisponibilidad();
            double limiteCredito = cliente.getLimiteCredito();

            this.txtSaldoVencido.setText(Formatter.formatMoney(saldoVencido));
            this.txtSaldoTotal.setText(Formatter.formatMoney(saldoTotal));
            this.txtLimiteDisponibilidad.setText(Formatter.formatMoney(limiteDisponibilidad));
            this.txtLimiteCredito.setText(Formatter.formatMoney(limiteCredito));
        } catch (Exception e) {
        }

    }

    private void initComponents() {
        //txtSaldoVencido
        this.txtSaldoVencido = (TextView) findViewById(R.id.lblSaldoVencidoValue);

        //txtSaldoTotal
        this.txtSaldoTotal = (TextView) findViewById(R.id.lblSaldoTotalValue);

        //txtLimiteDisponibilidad
        this.txtLimiteDisponibilidad = (TextView) findViewById(R.id.lblLimiteDisponibilidadValue);

        //txtLimiteCredito
        this.txtLimiteCredito = (TextView) findViewById(R.id.lblLimiteCreditoValue);

        //btnVerDetallesCuentaCorriente
        this.btnVerDetallesCuentaCorriente = (Button) findViewById(R.id.btnDetalleCuentaCorriente);
        this.btnVerDetallesCuentaCorriente.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnVerDetallesOnclick();
            }
        });

        //btnAutorizar
        this.btnAutorizar = (Button) findViewById(R.id.btnAutorizar);
        this.btnAutorizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnAutorizarOnClick();
            }
        });

        //btnCancelar
        this.btnCancelar = (Button) findViewById(R.id.btnCancelar);
        this.btnCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                btnCancelarOnClick();
            }
        });
    }

}
