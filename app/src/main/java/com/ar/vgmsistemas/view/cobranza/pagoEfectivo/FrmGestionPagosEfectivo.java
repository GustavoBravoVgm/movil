package com.ar.vgmsistemas.view.cobranza.pagoEfectivo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.PagoEfectivo;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.cobranza.FrmGestionEntrega;
import com.ar.vgmsistemas.view.cobranza.PagosFragment;

public class FrmGestionPagosEfectivo extends FrmGestionEntrega {
    private static final int DIALOG_OPCIONES_LINEA = 0;
    private static final int DIALOG_ELIMINAR = 1;
    private static final int ACTIVITY_AGREGAR_ENTREGA_MONEDA = 0;

    private TextView txtTotal;
    private ListView lstEntregasMoneda;
    private PagoEfectivoAdapter _adapter;
    private PagoEfectivo _entregaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_moneda);
        setActionBarTitle(R.string.lblListadoMonedas);

        initComponents();
        loadData();


        //Retorna falso cuando se gira la pantalla, para que no llame otra vez a 
        if (getEntrega().getEntregasEfectivo().size() == 0) {
            agregarEntrega(FrmGestionEntrega.TYPE_EFECTIVO);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.mn_moneda, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mni_agregar_moneda) {
            this.agregarEntrega(FrmGestionEntrega.TYPE_EFECTIVO);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DIALOG_ELIMINAR:
                dialog = eliminarLinea();
                break;
            case DIALOG_OPCIONES_LINEA:
                dialog = getDialogOpcionesLinea();
            default:
                break;
        }
        return dialog;
    }

    private void loadData() {
        this._adapter = new PagoEfectivoAdapter(this, getEntrega().getEntregasEfectivo());
        this.lstEntregasMoneda.setAdapter(this._adapter);
    }

    private void initComponents() {
        //txtTotal
        this.txtTotal = (TextView) findViewById(R.id.lblTotalValue);

        //lstMoneda
        this.lstEntregasMoneda = (ListView) findViewById(R.id.lstMoneda);
        this.lstEntregasMoneda.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lstEntregasMonedaOnItemClick(parent, view, position, id);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_AGREGAR_ENTREGA_MONEDA && resultCode == RESULT_OK) {
            PagoEfectivo entrega = (PagoEfectivo) data.getSerializableExtra("entrega");
            getEntrega().getEntregasEfectivo().add(entrega);
            addPay(entrega.getImporteMonedaCorriente());
            this.actualizarLineas();
        } else if (resultCode == RESULT_CANCELED && getEntrega().getEntregasEfectivo().size() == 0) {
            finish();
        }
    }

    private void actualizarTotales() {
        double total = 0;
        for (PagoEfectivo entregaMoneda : getEntrega().getEntregasEfectivo()) {
            total += entregaMoneda.getImporteMonedaCorriente();
        }
        this.txtTotal.setText(Formatter.formatMoney(total));
    }

    public void lstEntregasMonedaOnItemClick(AdapterView<?> parent, View view, int position, long id) {
        this._entregaSeleccionada = _adapter.getItem(position);
        showDialog(DIALOG_OPCIONES_LINEA);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(PagosFragment.EXTRA_ENTREGA, getEntrega());
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    private Dialog eliminarLinea() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.msjQuitarItem)
                .setCancelable(false)
                .setPositiveButton(R.string.si, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getEntrega().getEntregasEfectivo().remove(_entregaSeleccionada);
                        removePay(_entregaSeleccionada.getImporteMonedaCorriente());
                        actualizarLineas();
                    }
                })
                .setNegativeButton(R.string.no, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        return alert;
    }

    private void actualizarLineas() {
        this._adapter.notifyDataSetChanged();
        actualizarTotales();
    }

    private Dialog getDialogOpcionesLinea() {
        String eliminar = this.getString(R.string.eliminar);

        final CharSequence[] items = {eliminar};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.getString(R.string.tituloSeleccionarAccion));
        builder.setNegativeButton(R.string.btnCancelar, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setItems(items, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                menuOpcionesLineaOnClick(dialog, which);
            }
        });
        AlertDialog alert = builder.create();
        return alert;
    }

    private void menuOpcionesLineaOnClick(DialogInterface dialog, int index) {
        if (index == 0) {
            showDialog(DIALOG_ELIMINAR);
        }
    }

    @Override
    public double getMonto() {
//		 TODO Auto-generated method stub
        return getMontoFactura() - getTotalPay();
    }
}
