package com.ar.vgmsistemas.view.cobranza;

import static com.ar.vgmsistemas.view.informes.FrmListadoVenta.EXTRA_CLIENTE;

import android.content.Intent;
import android.os.Bundle;

import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.Entrega;
import com.ar.vgmsistemas.view.BaseActivity;
import com.ar.vgmsistemas.view.cobranza.cheque.FrmAgregarCheque;
import com.ar.vgmsistemas.view.cobranza.deposito.FrmAgregarDeposito;
import com.ar.vgmsistemas.view.cobranza.pagoEfectivo.FrmAgregarPagoEfectivo;
import com.ar.vgmsistemas.view.cobranza.retencion.FrmAgregarRetencion;
import com.ar.vgmsistemas.view.reparto.hojas.detalles.HojaPagosFragment;

public abstract class FrmGestionEntrega extends BaseActivity {

    private static final int ACTIVITY_AGREGAR_ENTREGA_MONEDA = 0;
    protected static final int ACTIVITY_AGREGAR_CHEQUE = ACTIVITY_AGREGAR_ENTREGA_MONEDA + 1;
    protected static final int ACTIVITY_AGREGAR_RETENCION = ACTIVITY_AGREGAR_CHEQUE + 1;
    protected static final int ACTIVITY_AGREGAR_DEPOSITO = ACTIVITY_AGREGAR_RETENCION + 1;
    protected static final int TYPE_EFECTIVO = 0;
    protected static final int TYPE_CHEQUE = 1;
    protected static final int TYPE_RETENCION = 2;
    protected static final int TYPE_DEPOSITO = 3;
    //protected static final String EXTRA_RECIBO = "recibo";
    private Double mMontoFactura;

    protected boolean maxControl;
    private double currentPay;
    // private Recibo _recibo;
    private Entrega mEntrega;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        mEntrega = (Entrega) b.getSerializable(PagosFragment.EXTRA_ENTREGA);
        mMontoFactura = getIntent().getExtras().getDouble(PagosFragment.EXTRA_MONTO_MAXIMO);
        maxControl = getIntent().getExtras().getBoolean(PagosFragment.EXTRA_MAX_CONTROL);

    }

    public abstract double getMonto();

    public double getMontoFactura() {
        return mMontoFactura;
    }

    protected Entrega getEntrega() {
        return mEntrega;
    }

    public double getTotalPay() {
        return currentPay;
    }

    public void removePay(double pay) {
        currentPay -= pay;
    }

    public void addPay(double pay) {
        currentPay += pay;
    }

    protected void agregarRetencion(Bundle bundle) {
        Intent intent = null;
        int requestCode = 0;
        intent = new Intent(this, FrmAgregarRetencion.class);
        Bundle b = getIntent().getExtras();
        Cliente cliente = (Cliente) b.getSerializable(EXTRA_CLIENTE);
        intent.putExtra(EXTRA_CLIENTE, cliente);
        requestCode = ACTIVITY_AGREGAR_RETENCION;

        intent.putExtra(HojaPagosFragment.EXTRA_MONTO_MAXIMO, getMonto());
        intent.putExtra(PagosFragment.EXTRA_MAX_CONTROL, getIntent().getExtras().getBoolean(PagosFragment.EXTRA_MAX_CONTROL));

        startActivityForResult(intent, requestCode);
    }

    protected void agregarEntrega(int type) {
        Intent intent = null;
        int requestCode = 0;
        double montoPagos = 0;

        switch (type) {
            case TYPE_EFECTIVO:
                intent = new Intent(this, FrmAgregarPagoEfectivo.class);
                requestCode = ACTIVITY_AGREGAR_ENTREGA_MONEDA;
                montoPagos = mEntrega.calcularTotalPagosEfectivo();
                break;
            case TYPE_CHEQUE:
                intent = new Intent(this, FrmAgregarCheque.class);
                requestCode = ACTIVITY_AGREGAR_CHEQUE;
                montoPagos = mEntrega.calcularTotalEntregasCheque();
                break;
            case TYPE_DEPOSITO:
                intent = new Intent(this, FrmAgregarDeposito.class);
                requestCode = ACTIVITY_AGREGAR_DEPOSITO;
                montoPagos = mEntrega.calcularTotalEntregasDeposito();
                break;
        }

        intent.putExtra(HojaPagosFragment.EXTRA_MONTO_MAXIMO, getMonto());
        intent.putExtra(PagosFragment.EXTRA_MAX_CONTROL, getIntent().getExtras().getBoolean(PagosFragment.EXTRA_MAX_CONTROL));

        startActivityForResult(intent, requestCode);

    }

}
