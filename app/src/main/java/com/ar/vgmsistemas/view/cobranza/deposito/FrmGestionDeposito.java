package com.ar.vgmsistemas.view.cobranza.deposito;

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
import com.ar.vgmsistemas.entity.Deposito;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.cobranza.FrmGestionEntrega;
import com.ar.vgmsistemas.view.cobranza.PagosFragment;

public class FrmGestionDeposito extends FrmGestionEntrega {
    private static final int DIALOG_OPCIONES_LINEA = 0;
    private static final int DIALOG_ELIMINAR = 1;

    private TextView txtTotal;
    private ListView lstDeposito;
    private DepositoAdapter adapter;
    private Deposito depositoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_deposito);
        setActionBarTitle(R.string.lblListadoDepositos);

        initComponents();
        loadData();

        if (getEntrega().getDepositos().size() == 0) {
            agregarEntrega(FrmGestionEntrega.TYPE_DEPOSITO);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.mn_deposito, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mni_agregar_deposito:
                agregarEntrega(FrmGestionEntrega.TYPE_DEPOSITO);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(PagosFragment.EXTRA_ENTREGA, getEntrega());
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DIALOG_ELIMINAR:
                dialog = getDialogEliminarLinea();
                break;
            case DIALOG_OPCIONES_LINEA:
                dialog = getDialogOpcionesLinea();
            default:
                break;
        }
        return dialog;
    }

    private void loadData() {
        this.adapter = new DepositoAdapter(this, getEntrega().getDepositos());
        lstDeposito.setAdapter(this.adapter);
    }

    private void initComponents() {
        //txtTotal
        this.txtTotal = (TextView) findViewById(R.id.lblTotalValue);


        //lstDeposito
        this.lstDeposito = (ListView) findViewById(R.id.lstDeposito);
        loadData();
        this.lstDeposito.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lstDepositoOnItemClick(parent, view, position, id);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_AGREGAR_DEPOSITO && resultCode == RESULT_OK) {
            Deposito deposito = (Deposito) data.getSerializableExtra("entrega");
            getEntrega().getDepositos().add(deposito);
            addPay(deposito.getImporte());
            this.adapter.notifyDataSetChanged();
            actualizarTotales();
        } else if (resultCode == RESULT_CANCELED && getEntrega().getDepositos().size() == 0) {
            finish();
        }
    }

    public void lstDepositoOnItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.depositoSeleccionado = adapter.getItem(position);
        showDialog(DIALOG_OPCIONES_LINEA);
    }

    private void actualizarTotales() {
        txtTotal.setText(Formatter.formatMoney(getEntrega().calcularTotalEntregasDeposito()));
    }

    private Dialog getDialogEliminarLinea() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.msjQuitarItem)
                .setCancelable(false)
                .setPositiveButton(R.string.si, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getEntrega().getDepositos().remove(depositoSeleccionado);
                        removePay(depositoSeleccionado.getImporte());
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
        this.adapter.notifyDataSetChanged();
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
        // TODO Auto-generated method stub
        return getMontoFactura() - getTotalPay();
    }
}
