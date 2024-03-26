package com.ar.vgmsistemas.view.cobranza;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.Recibo;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.BaseActivity;
import com.ar.vgmsistemas.view.cobranza.cheque.FrmGestionCheques;
import com.ar.vgmsistemas.view.cobranza.deposito.FrmGestionDeposito;
import com.ar.vgmsistemas.view.cobranza.pagoEfectivo.FrmGestionPagosEfectivo;
import com.ar.vgmsistemas.view.cobranza.retencion.FrmGestionRetencion;

public class FrmPagos extends BaseActivity {

    private TextView txtEfectivo;
    private TextView txtCheque;
    private TextView txtRetencion;
    private TextView txtDeposito;
    private Button btnAgregarCheque;
    private Button btnAgregarRetencion;
    private Button btnAgregarEfectivo;
    private Button btnAgregarDeposito;
    private Recibo _recibo;
    private static final int ACTIVITY_AGREGAR_CHEQUE = 0;
    private static final int ACTIVITY_AGREGAR_EFECTIVO = 1;
    private static final int ACTIVITY_AGREGAR_RETENCION = 2;
    private static final int ACTIVITY_AGREGAR_DEPOSITO = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_pago);

        Bundle b = getIntent().getExtras();
        this._recibo = (Recibo) b.getSerializable("recibo");

        initComponents();
        loadData();
    }

    private void loadData() {
        this.actualizarTotalCheques();
        this.actualizarTotalEntregasMoneda();
        this.actualizarTotalRetenciones();
        this.actualizarTotalDepositos();
    }

    private void initComponents() {
        //txtEfectivo
        txtEfectivo = (TextView) findViewById(R.id.txtEfectivo);

        //txtCheque
        txtCheque = (TextView) findViewById(R.id.txtCheque);

        //txtRetencion
        txtRetencion = (TextView) findViewById(R.id.txtRetencion);

        //txtDeposito
        txtDeposito = (TextView) findViewById(R.id.txtDeposito);

        //btnAgregarCheque
        btnAgregarCheque = (Button) findViewById(R.id.btnAgregarCheque);
        btnAgregarCheque.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btnAgregarChequeOnClick(v);
            }
        });

        //btnAgregarRetencion
        btnAgregarRetencion = (Button) findViewById(R.id.btnAgregarRetencion);
        btnAgregarRetencion.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btnAgregarRetencionOnClick(v);
            }
        });

        //btnAgregarMonedaExtranjera
        btnAgregarEfectivo = (Button) findViewById(R.id.btnAgregarMonedaExtranjera);
        btnAgregarEfectivo.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btnAgregarEfectivoOnClick(v);
            }
        });

        //btnAgregarDeposito
        btnAgregarDeposito = (Button) findViewById(R.id.btnAgregarDeposito);
        btnAgregarDeposito.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btnAgregarDepositoOnClick(v);
            }
        });
    }

    /*
     *EVENTOS
     */

    private void btnAgregarChequeOnClick(View v) {
        Intent frmGestionCheques = new Intent(this, FrmGestionCheques.class);
        frmGestionCheques.putExtra("recibo", this._recibo);
        startActivityForResult(frmGestionCheques, ACTIVITY_AGREGAR_CHEQUE);
    }

    private void btnAgregarRetencionOnClick(View v) {
        Intent frmGestionRetencion = new Intent(this, FrmGestionRetencion.class);
        frmGestionRetencion.putExtra("recibo", this._recibo);
        startActivityForResult(frmGestionRetencion, ACTIVITY_AGREGAR_RETENCION);
    }

    private void btnAgregarEfectivoOnClick(View v) {
        Intent frmGestionMoneda = new Intent(this, FrmGestionPagosEfectivo.class);
        frmGestionMoneda.putExtra("recibo", this._recibo);
        startActivityForResult(frmGestionMoneda, ACTIVITY_AGREGAR_EFECTIVO);
    }

    private void btnAgregarDepositoOnClick(View v) {
        Intent frmGestionDeposito = new Intent(this, FrmGestionDeposito.class);
        frmGestionDeposito.putExtra("recibo", this._recibo);
        startActivityForResult(frmGestionDeposito, ACTIVITY_AGREGAR_DEPOSITO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_AGREGAR_CHEQUE && resultCode == RESULT_OK) {
            Recibo recibo = (Recibo) data.getExtras().getSerializable("recibo");
            this._recibo.getEntrega().setCheques(recibo.getEntrega().getCheques());
            actualizarTotalCheques();
        }

        if (requestCode == ACTIVITY_AGREGAR_EFECTIVO && resultCode == RESULT_OK) {
            Recibo recibo = (Recibo) data.getExtras().getSerializable("recibo");
            this._recibo.getEntrega().setPagosEfectivo(recibo.getEntrega().getEntregasEfectivo());
            actualizarTotalEntregasMoneda();
        }

        if (requestCode == ACTIVITY_AGREGAR_RETENCION && resultCode == RESULT_OK) {
            Recibo recibo = (Recibo) data.getExtras().getSerializable("recibo");
            this._recibo.getEntrega().setRetenciones(recibo.getEntrega().getRetenciones());
            actualizarTotalRetenciones();
        }

        if (requestCode == ACTIVITY_AGREGAR_DEPOSITO && resultCode == RESULT_OK) {
            Recibo recibo = (Recibo) data.getExtras().getSerializable("recibo");
            this._recibo.getEntrega().setDepositos((recibo.getEntrega().getDepositos()));
            actualizarTotalDepositos();
        }
        double total = this._recibo.getEntrega().obtenerTotalPagos();
        this._recibo.setTotal(total);
        this._recibo.notifyObservers();
    }

    private void actualizarTotalCheques() {
        this.txtCheque.setText(Formatter.formatMoney(this._recibo.getEntrega().calcularTotalEntregasCheque()));
    }

    private void actualizarTotalEntregasMoneda() {
        this.txtEfectivo.setText(Formatter.formatMoney(this._recibo.getEntrega().calcularTotalPagosEfectivo()));
    }

    private void actualizarTotalRetenciones() {
        this.txtRetencion.setText(Formatter.formatMoney(this._recibo.getEntrega().calcularTotalRetenciones()));
    }

    private void actualizarTotalDepositos() {
        this.txtDeposito.setText(Formatter.formatMoney(this._recibo.getEntrega().calcularTotalEntregasDeposito()));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return false;
        else
            return super.onKeyDown(keyCode, event);
    }

}
