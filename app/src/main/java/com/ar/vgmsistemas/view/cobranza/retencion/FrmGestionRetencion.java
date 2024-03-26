package com.ar.vgmsistemas.view.cobranza.retencion;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.Retencion;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.cobranza.FrmGestionEntrega;
import com.ar.vgmsistemas.view.cobranza.PagosFragment;

import java.util.Iterator;

public class FrmGestionRetencion extends FrmGestionEntrega {
    private static final int DIALOG_OPCIONES_LINEA = 0;
    private static final int DIALOG_ELIMINAR = 1;

    private TextView txtTotal;
    private ListView lstRetencion;
    //private Recibo _recibo;
    private RetencionAdapter _adapter;
    private Retencion _retencionSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        setContentView(R.layout.lyt_retencion);
        setActionBarTitle(R.string.lblListadoRetenciones);
        initComponents();
        loadData();

        if (getEntrega().getRetenciones().size() == 0) {
            agregarRetencion(bundle);
        }
    }

    @Override
    public double getMonto() {
        // TODO Auto-generated method stub
        return getMontoFactura() - getTotalPay();
    }


    private void initComponents() {
        //txtTotal
        this.txtTotal = (TextView) findViewById(R.id.lblTotalValue);

        //lstMoneda
        this.lstRetencion = (ListView) findViewById(R.id.lstRetencion);
        this.lstRetencion.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lstRetencionOnItemClick(parent, view, position, id);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.mn_retencion, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mni_agregar_retencion:
                agregarRetencion(getIntent().getExtras());
                break;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_AGREGAR_RETENCION && resultCode == RESULT_OK) {
            Retencion retencion = (Retencion) data.getSerializableExtra("retencion");
            if (existeRetencion(getEntrega().getRetenciones().listIterator(), retencion)) {
                getEntrega().getRetenciones().add(retencion);
                addPay(retencion.getImporte());
                this._adapter.notifyDataSetChanged();
                actualizarTotales();
            } else {
                com.ar.vgmsistemas.view.AlertDialog alert = new com.ar.vgmsistemas.view.AlertDialog(this, getString(R.string.tituloError), ErrorManager.numeroDeRetencionYaExiste);
                alert.show();
            }
        } else if (resultCode == RESULT_CANCELED && getEntrega().getRetenciones().size() == 0) {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra(PagosFragment.EXTRA_ENTREGA, getEntrega());
            setResult(RESULT_OK, intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(PagosFragment.EXTRA_ENTREGA, getEntrega());
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    private Dialog getDialogEliminarLinea() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.msjQuitarItem)
                .setCancelable(false)
                .setPositiveButton(R.string.si, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getEntrega().getRetenciones().remove(_retencionSeleccionada);
                        removePay(_retencionSeleccionada.getImporte());
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

    private boolean existeRetencion(Iterator<Retencion> listRetenciones, Retencion retencion) {
        while (listRetenciones.hasNext()) {
            Retencion retencion1 = listRetenciones.next();
            if (retencion1.getId().getIdLetra().equals(retencion.getId().getIdLetra())
                    && retencion1.getId().getIdDocumento().equals(retencion.getId().getIdDocumento())
                    && retencion1.getId().getIdNumeroDocumento() == retencion.getId().getIdNumeroDocumento()
                    && retencion1.getId().getPuntoVenta() == retencion.getId().getPuntoVenta()) {
                return false;
            }
        }
        return true;
    }

    private void menuOpcionesLineaOnClick(DialogInterface dialog, int index) {
        switch (index) {
            case 0:
                showDialog(DIALOG_ELIMINAR);
                break;
        }
    }

    public void lstRetencionOnItemClick(AdapterView<?> parent, View view, int position, long id) {
        this._retencionSeleccionada = _adapter.getItem(position);
        showDialog(DIALOG_OPCIONES_LINEA);
    }

    private void loadData() {
        this._adapter = new RetencionAdapter(this, getEntrega().getRetenciones());
        lstRetencion.setAdapter(this._adapter);
    }

    private void actualizarLineas() {
        this._adapter.notifyDataSetChanged();
        actualizarTotales();
    }

    private void actualizarTotales() {
        txtTotal.setText(Formatter.formatMoney(getEntrega().calcularTotalRetenciones()));
    }

}
