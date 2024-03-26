package com.ar.vgmsistemas.view.cobranza;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.ActionMode.Callback;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.CuentaCorrienteBo;
import com.ar.vgmsistemas.bo.EmpresaBo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.CuentaCorriente;
import com.ar.vgmsistemas.entity.Empresa;
import com.ar.vgmsistemas.entity.Recibo;
import com.ar.vgmsistemas.entity.ReciboDetalle;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.AlertDialog;
import com.ar.vgmsistemas.view.BaseActivity;
import com.ar.vgmsistemas.view.cobranza.cuentacorriente.DlgMenuVerCheques;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class FrmCuentaCorriente extends BaseActivity {

    private TextView lblCliente;
    private TextView lblLimiteCreditoValue;
    private TextView lblCreditoUtilizadoValue;
    private TextView lblCreditoDisponibleValue;
    private TextView lblSaldoTotal;
    private TextView lblSaldoVencido;
    private TextView lblTotalSeleccionado;
    private Cliente _cliente;
    private ListView lstDetalle;
    private CuentaCorrienteAdapter _adapter;
    private List<CuentaCorriente> _cuentasCorrientes;
    private static final String TAG = FrmCuentaCorriente.class.getCanonicalName();
    private static final int DIALOG_NINGUN_DOCUMENTO_IMPUTADO = 0;
    private static final int DIALOG_NINGUN_DOCUMENTO_IMPUTADO_A_PAGAR = 1;
    private ActionMode mActionMode;
    private MenuItem itemSelectAll;
    private SparseBooleanArray mSelectedItems;
    // DAO
    private RepositoryFactory _repoFactory;

    private Empresa _empresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_cuenta_corriente);
        setActionBarTitle(R.string.mnCuentaCorriente);
        Bundle b = getIntent().getExtras();
        this._cliente = (Cliente) b.getSerializable("cliente");
        initComponents();
        loadData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mSelectedItems = _adapter.getSelectedItems();
        loadData();
        _adapter.setSelectedItems(mSelectedItems);
        setearMontoSeleccionado();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mn_cuenta_corriente, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        EmpresaBo empresaBo = new EmpresaBo(_repoFactory);
        this._empresa = null;
        try {
            this._empresa = empresaBo.recoveryEmpresa();
            if (!this._empresa.isAnticipoHabilitado())
                menu.findItem(R.id.mni_generar_anticipo).setEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private Dialog getDialogNingunDocumentoImputado() {
        Builder builder = new Builder(this);
        builder.setTitle("Error");
        builder.setMessage(ErrorManager.DocumentoNoSeleccionado);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    private Dialog getDialogNingunDocumentoImputadoPagar() {
        Builder builder = new Builder(this);
        builder.setTitle("Error");
        builder.setMessage(ErrorManager.DocumentoPagarNoSeleccionado);
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DIALOG_NINGUN_DOCUMENTO_IMPUTADO:
                dialog = getDialogNingunDocumentoImputado();
                break;
            case DIALOG_NINGUN_DOCUMENTO_IMPUTADO_A_PAGAR:
                dialog = getDialogNingunDocumentoImputadoPagar();
                break;
        }
        return dialog;
    }

    private void promptMonto() {
        Builder alert = new Builder(this);
        alert.setTitle(R.string.tituloPagarMonto);
        alert.setMessage(R.string.msjIngresarImporte);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        alert.setView(input);
        alert.setPositiveButton(R.string.btnAceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String sImporte = input.getText().toString();
                pagarImporte(sImporte);
            }
        });
        alert.setNegativeButton(R.string.btnCancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    private void notificoSeleccionComprobantes() {
        Builder alert = new Builder(this);
        alert.setTitle(R.string.tituloGenerarAnticipo);
        alert.setMessage(R.string.msjErrorGenerarAnticipo);
        alert.setPositiveButton(R.string.btnAceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    private void pagarImporte(String sImporte) {
        try {
            double importe = Double.parseDouble(sImporte);
            CuentaCorrienteBo cuentaCorrienteBo = new CuentaCorrienteBo(_repoFactory);
            List<CuentaCorriente> documentosImputados = cuentaCorrienteBo.getCuentasCorrientesAImputar(_cuentasCorrientes,
                    importe);
            if (this._empresa.getSnControlaReciboBn().equalsIgnoreCase("S")) {
                if (sonDocumentosMismoTipoBN(documentosImputados)) {
                    pagarCuentasCorrientesImputadas(documentosImputados);
                } else {
                    notificoProblemaPagoImportePorComprobantesConDistintoBN();
                }
            } else {
                pagarCuentasCorrientesImputadas(documentosImputados);
            }
        } catch (NumberFormatException e) {
            AlertDialog alert = new AlertDialog(this, getString(R.string.tituloError), ErrorManager.ImporteInvalido);
            alert.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        List<CuentaCorriente> documentosImputados = null;
        switch (item.getItemId()) {
            case R.id.mni_generar_anticipo:
                documentosImputados = _adapter.getCheckedItems();
                if (documentosImputados.size() > 0) {
                    notificoSeleccionComprobantes();
                } else {
                    imputarRecibo(documentosImputados);
                }
                break;
            case R.id.mni_pagar_todas:
                documentosImputados = this._cuentasCorrientes;
                if (this._empresa.getSnControlaReciboBn().equalsIgnoreCase("S")) {
                    if (sonDocumentosMismoTipoBN(documentosImputados)) {
                        pagarCuentasCorrientesImputadas(documentosImputados);
                    } else {
                        notificoSeleccionComprobantesConDistintoBN();
                    }
                } else {
                    pagarCuentasCorrientesImputadas(documentosImputados);
                }
                break;
            case R.id.mni_pagar_monto:
                if (this._empresa.getSnControlaReciboBn().equalsIgnoreCase("S")) {
                    notificoProblemaPagoImportePorComprobantesConDistintoBN();
                } else {
                    promptMonto();
                }
                break;
            case R.id.mni_ver_cheques:
                DlgMenuVerCheques dlgMenuVerCheques = DlgMenuVerCheques.newInstance(_cliente);
                dlgMenuVerCheques.show(getSupportFragmentManager(), "");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void pagarCuentasCorrientesImputadas(List<CuentaCorriente> documentosImputados) {

        boolean valido = verificarDocumentos(documentosImputados);
        if (valido) {
            imputarRecibo(documentosImputados);
        } else {
            showDialog(DIALOG_NINGUN_DOCUMENTO_IMPUTADO_A_PAGAR);
        }
    }

    private void imputarRecibo(List<CuentaCorriente> documentosImputados) {
        Intent intent = new Intent(this, FrmRecibo.class);
        Collections.sort(documentosImputados, new ComparatorCuentaCorriente());
        // Crear las lineas de detalle para el recibo
        Iterator<CuentaCorriente> iterator = documentosImputados.iterator();
        List<ReciboDetalle> detalles = new ArrayList<>();

        while (iterator.hasNext()) {
            CuentaCorriente cc = iterator.next();
            ReciboDetalle rd = new ReciboDetalle();
            rd.setCuentaCorriente(cc);
            detalles.add(rd);
        }

        // Instanciar Recibo
        Recibo recibo = new Recibo();
        recibo.setFechaMovil(Calendar.getInstance().getTime());
        recibo.setCliente(this._cliente);
        recibo.setDetalles(detalles);

        intent.putExtra("recibo", recibo);

        startActivity(intent);
    }

    private boolean verificarDocumentos(List<CuentaCorriente> documentosImputados) {
        if (documentosImputados.size() == 0) {
            return false;
        }
        // verifico que haya algun comprobante a pagar
        boolean valido = false;
        int index = 0;
        while (index < documentosImputados.size() && !valido) {
            valido = documentosImputados.get(index).getSigno() > 0;
            index++;
        }
        return valido;
    }

    private void loadData() {
        this._repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
        String clienteStr = this._cliente.getRazonSocial() + "  " + _cliente.getId();
        this.lblCliente.setText(clienteStr);
        double limiteCredito = _cliente.getLimiteCredito();
        this.lblLimiteCreditoValue.setText(Formatter.formatMoney(limiteCredito));
        double limiteDisponibilidad = _cliente.getLimiteDisponibilidad();
        this.lblCreditoDisponibleValue.setText(Formatter.formatMoney(limiteDisponibilidad));
        double creditoUtilizado = limiteCredito - limiteDisponibilidad;
        this.lblCreditoUtilizadoValue.setText(Formatter.formatMoney(creditoUtilizado));

        CuentaCorrienteBo cuentaCorrienteBo = new CuentaCorrienteBo(_repoFactory);
        try {
            _cuentasCorrientes = cuentaCorrienteBo.recoveryByCliente(this._cliente);
            this._adapter = new CuentaCorrienteAdapter(this, R.layout.lyt_cuenta_corriente_item, _cuentasCorrientes);
            this.lstDetalle.setAdapter(this._adapter);
            EmpresaBo empresaBo = new EmpresaBo(_repoFactory);
            double total = cuentaCorrienteBo.getTotalSaldo(this._cliente, empresaBo.recoveryEmpresa().getSnClienteUnico());
            double totalVencido = cuentaCorrienteBo.getTotalSaldoVencido(this._cliente, empresaBo.recoveryEmpresa().getSnClienteUnico());
            this.lblSaldoTotal.setText(Formatter.formatMoney(total));
            this.lblSaldoVencido.setText(Formatter.formatMoney(totalVencido));

        } catch (Exception ex) {
            ErrorManager.manageException(TAG, "loadData", ex, this);

        }
    }

    private void initComponents() {
        // lblCliente(TextView)
        this.lblCliente = findViewById(R.id.lblCliente);

        // lblLimiteCredito(TextView)
        this.lblLimiteCreditoValue = findViewById(R.id.lblLimiteCreditoValue);

        //lblCreditoUtilizado(TextView)
        this.lblCreditoUtilizadoValue = findViewById(R.id.lblCreditoUtilizadoValue);

        // lblCreditoDisponible(TextView)
        this.lblCreditoDisponibleValue = findViewById(R.id.lblCreditoDisponibleValue);

        // lblTotalSaldo(TextView)
        this.lblSaldoTotal = findViewById(R.id.lblSaldoTotalValue);

        // lblSaldoVencido(TextView)
        this.lblSaldoVencido = findViewById(R.id.lblSaldoVencidoValue);

        // lstDetalle(ListView)
        this.lstDetalle = findViewById(R.id.lstDetalles);
        this.lstDetalle.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View parent, int position, long id) {
                onLisItemCheck(position);
            }
        });

        // lblTotalSeleccionado
        this.lblTotalSeleccionado = findViewById(R.id.lblTotalSelectValue);

    }

    private void onLisItemCheck(int position) {
        _adapter.toggleSelection(position);
        if (_adapter.getSelectedCount() > 0 && mActionMode == null) {
            mActionMode = startSupportActionMode(new ActionModeCallback(this));
        } else if (!(_adapter.getSelectedCount() > 0) && mActionMode != null) {
            mActionMode.finish();

        }
        if (mActionMode != null) {
            String msj = (_adapter.getSelectedCount() > 1) ? " seleccionadas" : " seleccionada";
            mActionMode.setTitle(_adapter.getSelectedCount() + msj);
            Drawable icon = (_adapter.isSelectAll()) ? getResources().getDrawable(R.drawable.ic_select_all_selected) : getResources()
                    .getDrawable(R.drawable.ic_select_all_unselected);
            itemSelectAll.setIcon(icon);
        }
        setearMontoSeleccionado();
    }

    private void setearMontoSeleccionado() {
        double saldo = 0.00d;
        for (CuentaCorriente ctaCte : _adapter.getSelected()) {
            saldo = saldo + ctaCte.calcularSaldo();
        }
        this.lblTotalSeleccionado.setText(Formatter.formatMoney(saldo));
    }

    private class ActionModeCallback implements Callback {
        private Context mContext;

        public ActionModeCallback(Context context) {
            mContext = context;
        }

        @Override
        public boolean onActionItemClicked(ActionMode action, MenuItem arg1) {

            SparseBooleanArray selected = _adapter.getSelectedItems();
            StringBuilder message = new StringBuilder();
            for (int i = 0; i < selected.size(); i++) {
                if (selected.valueAt(i)) {
                    CuentaCorriente selectedItem = _adapter.getItem(selected.keyAt(i));
                    message.append(selectedItem + "\n");
                }
            }
            Toast.makeText(mContext, message.toString(), Toast.LENGTH_LONG).show();

            // close action mode
            action.finish();
            return false;
        }

        @Override
        public boolean onCreateActionMode(ActionMode action, Menu menu) {
            action.getMenuInflater().inflate(R.menu.menu_select_items_cuenta_corriente, menu);
            itemSelectAll = menu.findItem(R.id.mni_select_all);
            itemSelectAll.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    _adapter.selectAllViews();
                    Drawable icon = (_adapter.isSelectAll()) ? getResources().getDrawable(R.drawable.ic_select_all_selected)
                            : getResources().getDrawable(R.drawable.ic_select_all_unselected);
                    itemSelectAll.setIcon(icon);
                    mActionMode.setTitle(_adapter.getSelectedCount() + " seleccionadas");
                    return true;
                }
            });
            MenuItem itemPagar = menu.findItem(R.id.mni_pagar_seleccionadas);
            itemPagar.setOnMenuItemClickListener(new OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    List<CuentaCorriente> documentosImputados = _adapter.getSelected();
                    if (_empresa.getSnControlaReciboBn().equalsIgnoreCase("S")) {
                        if (sonDocumentosMismoTipoBN(documentosImputados)) {
                            pagarCuentasCorrientesImputadas(documentosImputados);
                        } else {
                            notificoSeleccionComprobantesConDistintoBN();
                        }
                    } else {
                        pagarCuentasCorrientesImputadas(documentosImputados);
                    }
                    mActionMode.finish();
                    return true;
                }
            });
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode action) {
            _adapter.removeSelection();
            mActionMode = null;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
            // TODO Auto-generated method stub
            return false;
        }

    }


    private boolean sonDocumentosMismoTipoBN(List<CuentaCorriente> documentosImputados) {
        if (documentosImputados.size() == 0) {
            return true;
        }
        // verifico que haya algun comprobante a pagar
        boolean valido = false;
        int index = 0;
        int tipoBlanco = 0; //para sumar comprobantes blancos
        int tipoAvion = 0; //para sumar comprobantes avion
        while (index < documentosImputados.size() && !valido) {
            if (_empresa.getDocumentoAnticipo() == null) return false;
            if (!documentosImputados.get(index).getDocumento().getId().getIdDocumento().equalsIgnoreCase(_empresa.getDocumentoAnticipo())) {
                if (documentosImputados.get(index).getDocumento().getTipoBlancoNegro() != null) {
                    if (documentosImputados.get(index).getDocumento().getTipoBlancoNegro().equalsIgnoreCase("S")) {
                        tipoBlanco++;
                    } else {
                        tipoAvion++;
                    }
                } else {/*Si es null ti_bn y no aplica iva sumo tipoN, sino sumo tipoB*/
                    if (documentosImputados.get(index).getDocumento().getTipoBlancoNegro() == null &&
                            documentosImputados.get(index).getDocumento().getTiAplicaIva() == 0) {
                        tipoAvion++;
                    } else {
                        tipoBlanco++;
                    }
                }
            }
            index++;
        }
        valido = ((tipoBlanco > 0 && tipoAvion == 0) || (tipoAvion > 0 && tipoBlanco == 0));

        return valido;
    }

    private void notificoSeleccionComprobantesConDistintoBN() {
        Builder alert = new Builder(this);
        alert.setTitle(R.string.msjTituloControlReciboBN);
        alert.setMessage(R.string.msjErrorControlReciboBN);
        alert.setPositiveButton(R.string.btnAceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    private void notificoProblemaPagoImportePorComprobantesConDistintoBN() {
        Builder alert = new Builder(this);
        alert.setTitle(R.string.msjTituloControlReciboBN);
        alert.setMessage(R.string.msjErrorPagoImporteControlReciboBN);
        alert.setPositiveButton(R.string.btnAceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

}
