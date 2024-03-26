package com.ar.vgmsistemas.view.cobranza;

import static com.ar.vgmsistemas.view.informes.FrmListadoVenta.EXTRA_CLIENTE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.Entrega;
import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.entity.Recibo;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.cobranza.cheque.FrmGestionCheques;
import com.ar.vgmsistemas.view.cobranza.deposito.FrmGestionDeposito;
import com.ar.vgmsistemas.view.cobranza.pagoEfectivo.FrmGestionPagosEfectivo;
import com.ar.vgmsistemas.view.cobranza.retencion.FrmGestionRetencion;
import com.ar.vgmsistemas.view.reparto.hojas.detalles.FrmDetalleFacturaReparto;

public abstract class PagosFragment extends Fragment implements FrmDetalleFacturaReparto.ChangeFooterListener {

    private TextView txtEfectivo;
    private TextView txtCheque;
    private TextView txtRetencion;
    private TextView txtDeposito;
    private Button btnAgregarCheque;
    private Button btnAgregarRetencion;
    private Button btnAgregarEfectivo;
    private Button btnAgregarDeposito;
    //private Recibo _recibo;
    private static final int ACTIVITY_AGREGAR_CHEQUE = 0;
    private static final int ACTIVITY_AGREGAR_EFECTIVO = 1;
    private static final int ACTIVITY_AGREGAR_RETENCION = 2;
    private static final int ACTIVITY_AGREGAR_DEPOSITO = 3;

    public static final String EXTRA_TYPE = "type";
    public static final String EXTRA_ENTREGA = "entrega";
    public static final String EXTRA_MONTO_MAXIMO = "monto_maximo";
    public static final String EXTRA_MONTO = "monto_factura";
    public static final String EXTRA_MAX_CONTROL = "maxControl";
    public static final String EXTRA_IS_HOJA_DETALLE_ENVIADA = "isEnviada";

    public static final String EXTRA_ENTITY = "entity";
    public static final int TYPE_RECIBO = 1;
    public static final int TYPE_HOJA_DETALLE = 2;

    private boolean controlMax = false;
    private Entrega mEntrega;
    boolean snActivateButtons = true;
    private Double mMontoFactura;
    private Cliente cliente;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        Object object = b.getSerializable(EXTRA_ENTITY);
        if (object instanceof Recibo)
            cliente = ((Recibo) object).getCliente();
        else if (object instanceof HojaDetalle)
            cliente = ((HojaDetalle) object).getCliente();
        else if (object instanceof Entrega)
            mEntrega = ((Entrega) object);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyt_pago, null);
        initComponents(view);
        loadData();
        return view;
    }

    private void loadData() {
        this.actualizarTotalCheques();
        this.actualizarTotalEntregasMoneda();
        this.actualizarTotalRetenciones();
        this.actualizarTotalDepositos();
    }

    private void initComponents(View view) {
        //txtEfectivo
        txtEfectivo = (TextView) view.findViewById(R.id.txtEfectivo);

        //txtCheque
        txtCheque = (TextView) view.findViewById(R.id.txtCheque);

        //txtRetencion
        txtRetencion = (TextView) view.findViewById(R.id.txtRetencion);

        //txtDeposito
        txtDeposito = (TextView) view.findViewById(R.id.txtDeposito);

        //btnAgregarCheque
        btnAgregarCheque = (Button) view.findViewById(R.id.btnAgregarCheque);
        btnAgregarCheque.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btnAgregarChequeOnClick(v);
            }
        });
        btnAgregarCheque.setEnabled(snActivateButtons);

        //btnAgregarRetencion
        btnAgregarRetencion = (Button) view.findViewById(R.id.btnAgregarRetencion);
        btnAgregarRetencion.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btnAgregarRetencionOnClick(v);
            }
        });
        btnAgregarRetencion.setEnabled(snActivateButtons);

        //btnAgregarMonedaExtranjera
        btnAgregarEfectivo = (Button) view.findViewById(R.id.btnAgregarMonedaExtranjera);
        btnAgregarEfectivo.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btnAgregarEfectivoOnClick(v);
            }
        });
        btnAgregarEfectivo.setEnabled(snActivateButtons);

        //btnAgregarDeposito
        btnAgregarDeposito = (Button) view.findViewById(R.id.btnAgregarDeposito);
        btnAgregarDeposito.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btnAgregarDepositoOnClick(v);
            }
        });
        btnAgregarDeposito.setEnabled(snActivateButtons);
    }

    /*
     *EVENTOS
     */

    private void btnAgregarChequeOnClick(View v) {
        Intent frmGestionCheques = new Intent(getActivity(), FrmGestionCheques.class);
        frmGestionCheques.putExtra(EXTRA_ENTREGA, mEntrega);
        frmGestionCheques.putExtra(EXTRA_MONTO_MAXIMO, getMontoMaximo() - mEntrega.obtenerTotalPagos());
        frmGestionCheques.putExtra(EXTRA_MAX_CONTROL, isControlMax());
        startActivityForResult(frmGestionCheques, ACTIVITY_AGREGAR_CHEQUE);
    }

    private void btnAgregarRetencionOnClick(View v) {
        Intent frmGestionRetencion = new Intent(getActivity(), FrmGestionRetencion.class);
        frmGestionRetencion.putExtra(EXTRA_ENTREGA, mEntrega);
        frmGestionRetencion.putExtra(EXTRA_MONTO_MAXIMO, getMontoMaximo() - mEntrega.obtenerTotalPagos());
        frmGestionRetencion.putExtra(EXTRA_MAX_CONTROL, isControlMax());
        frmGestionRetencion.putExtra(EXTRA_CLIENTE, cliente);
        startActivityForResult(frmGestionRetencion, ACTIVITY_AGREGAR_RETENCION);
    }

    private void btnAgregarEfectivoOnClick(View v) {
        Intent frmGestionMoneda = new Intent(getActivity(), FrmGestionPagosEfectivo.class);
        frmGestionMoneda.putExtra(EXTRA_ENTREGA, mEntrega);
        frmGestionMoneda.putExtra(EXTRA_MONTO_MAXIMO, getMontoMaximo() - mEntrega.obtenerTotalPagos());
        frmGestionMoneda.putExtra(EXTRA_MAX_CONTROL, isControlMax());
        startActivityForResult(frmGestionMoneda, ACTIVITY_AGREGAR_EFECTIVO);
    }

    private void btnAgregarDepositoOnClick(View v) {
        Intent frmGestionDeposito = new Intent(getActivity(), FrmGestionDeposito.class);
        frmGestionDeposito.putExtra(EXTRA_ENTREGA, mEntrega);
        frmGestionDeposito.putExtra(EXTRA_MONTO_MAXIMO, getMontoMaximo() - mEntrega.obtenerTotalPagos());
        frmGestionDeposito.putExtra(EXTRA_MAX_CONTROL, isControlMax());
        startActivityForResult(frmGestionDeposito, ACTIVITY_AGREGAR_DEPOSITO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_AGREGAR_CHEQUE && resultCode == Activity.RESULT_OK) {
            mEntrega = (Entrega) data.getExtras().getSerializable(PagosFragment.EXTRA_ENTREGA);
            mEntrega.setCheques(mEntrega.getCheques());
            actualizarTotalCheques();
        }

        if (requestCode == ACTIVITY_AGREGAR_EFECTIVO && resultCode == Activity.RESULT_OK) {
            mEntrega = (Entrega) data.getExtras().getSerializable(PagosFragment.EXTRA_ENTREGA);
            mEntrega.setPagosEfectivo(mEntrega.getEntregasEfectivo());
            actualizarTotalEntregasMoneda();
        }

        if (requestCode == ACTIVITY_AGREGAR_RETENCION && resultCode == Activity.RESULT_OK) {
            mEntrega = (Entrega) data.getExtras().getSerializable(PagosFragment.EXTRA_ENTREGA);
            mEntrega.setRetenciones(mEntrega.getRetenciones());
            actualizarTotalRetenciones();
        }

        if (requestCode == ACTIVITY_AGREGAR_DEPOSITO && resultCode == Activity.RESULT_OK) {
            mEntrega = (Entrega) data.getExtras().getSerializable(PagosFragment.EXTRA_ENTREGA);
            mEntrega.setDepositos(mEntrega.getDepositos());
            actualizarTotalDepositos();
        }
    }

    private void actualizarTotalCheques() {
        this.txtCheque.setText(Formatter.formatMoney(mEntrega.calcularTotalEntregasCheque()));
    }

    private void actualizarTotalEntregasMoneda() {
        this.txtEfectivo.setText(Formatter.formatMoney(mEntrega.calcularTotalPagosEfectivo()));
    }

    private void actualizarTotalRetenciones() {
        this.txtRetencion.setText(Formatter.formatMoney(mEntrega.calcularTotalRetenciones()));
    }

    private void actualizarTotalDepositos() {
        this.txtDeposito.setText(Formatter.formatMoney(mEntrega.calcularTotalEntregasDeposito()));
    }

    public boolean isControlMax() {
        return controlMax;
    }

    public void setControlMax(boolean controlMax) {
        this.controlMax = controlMax;

    }

    protected Entrega getEntrega() {
        return mEntrega;
    }

    protected void setEntrega(Entrega entrega) {
        mEntrega = entrega;
    }

    protected void setIsEnviada(boolean isEnviada) {
        snActivateButtons = !isEnviada;
    }

    // para pagos base se utiliza como maximo el monto de la factura, para hojas por ejemplo se utiliza otro calculo para saber el monto maximo a pagar.
    public double getMontoMaximo() {
        return getMontoFactura();
    }

    public double getMontoFactura() {

        return mMontoFactura;
    }

    @Override
    public void onChange(double totalFactura) {
        setMontoFactura(totalFactura);

    }

    public void setMontoFactura(double monto) {
        mMontoFactura = monto;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_MONTO, mMontoFactura);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null)
            mMontoFactura = (double) savedInstanceState.getDouble(EXTRA_MONTO);
    }
}
