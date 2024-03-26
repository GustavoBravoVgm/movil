package com.ar.vgmsistemas.view.informes;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.EmpresaBo;
import com.ar.vgmsistemas.bo.VentaBo;
import com.ar.vgmsistemas.entity.Documento;
import com.ar.vgmsistemas.entity.Empresa;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.dialogs.OptionsDialogFragment;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;
import com.ar.vgmsistemas.view.venta.FrmVenta;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FrmVentasAcumuladas extends BaseNavigationFragment {
    private static final int DIALOG_FECHA_DESDE = 0;
    private static final int DIALOG_FECHA_HASTA = 1;
    private static final String TAG_VENTAS_X_VENDEDOR = "VentasXVendedor";

    private EditText txtFechaDesde;
    private EditText txtFechaHasta;
    private TextView lblTotalSImpValue;
    private TextView lblTotalValue;
    private ListView lstVentas;
    Empresa empresa;
    //private static final int DIALOG_OPCIONES_VENTA = 2;


    private Date fechaDesde;
    private Date fechaHasta;
    private ListadoVentaAdapter _adapter;
    private List<Venta> _ventas;
    private Venta itemSeleccionado;

    private RepositoryFactory _repoFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyt_ventas_x_vendedor, container, false);
        initComponents(view);
        initVar(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        itemSeleccionado = (Venta) getLastNonConfigurationInstance();
        actualizarVentas();
    }


    private void getDialogSeleccionarFecha(int tipoFecha) {
        final int tipo = tipoFecha;
        Date fecha;
        if (tipoFecha == DIALOG_FECHA_DESDE)
            fecha = this.fechaDesde;
        else
            fecha = this.fechaHasta;

        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialogFecha = new DatePickerDialog(getActivity(), (view, year1, monthOfYear, dayOfMonth) -> {
            Calendar cal1 = Calendar.getInstance();
            cal1.set(year1, monthOfYear, dayOfMonth);

            if (tipo == DIALOG_FECHA_DESDE) {
                fechaDesde = cal1.getTime();
                String fechaDesdeString = Formatter.formatDate(fechaDesde);
                txtFechaDesde.setText(fechaDesdeString);
            } else {
                fechaHasta = cal1.getTime();
                String fechaHastaString = Formatter.formatDate(fechaHasta);
                txtFechaHasta.setText(fechaHastaString);
            }
            actualizarVentas();
        }, year, month, day);

        dialogFecha.show();
    }

    private void actualizarVentas() {
        VentaBo ventaBo = new VentaBo(_repoFactory);
        try {
            this._ventas = ventaBo.recoveryVentas(this.fechaDesde, this.fechaHasta);
            this._adapter.setVentas(_ventas);
            this._adapter.notifyDataSetChanged();
            actualizarTotales();
        } catch (Exception e) {
            Toast toast = Toast.makeText(getActivity(), ErrorManager.ErrorAccederALosDatos, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void actualizarTotales() {
        Iterator<Venta> iterator = this._ventas.iterator();
        double totalVentas = 0d;
        double totalSinImp = 0d;
        while (iterator.hasNext()) {
            Venta venta = iterator.next();
            int tipoFuncion = venta.getDocumento().getFuncionTipoDocumento();
            int signo = 1;
            if (tipoFuncion == Documento.FUNCION_CREDITO) {
                signo = -1;
            }

            if (venta.getDocumento() != null && venta.getDocumento().isLegal() || (empresa.getSnSumIvaReporteMovil().equals("N") && venta.getDocumento().getTiAplicaIva() == 0)) {
                totalSinImp += venta.getSubtotal() * signo;
            } else {
                totalSinImp += venta.getTotal() * signo;
            }
            totalVentas += venta.getTotal() * signo;
        }
        lblTotalValue.setText(Formatter.formatMoney(totalVentas));
        lblTotalSImpValue.setText(Formatter.formatMoney(totalSinImp));
    }

    private void initComponents( View view) {
        _repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        EmpresaBo empresaBo = new EmpresaBo(_repoFactory);
        try {
            empresa = empresaBo.recoveryEmpresa();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //txtFechaDesde
        this.txtFechaDesde = view.findViewById(R.id.txtFechaDesde);//(EditText)
        this.txtFechaDesde.setKeyListener(null);
        this.txtFechaDesde.setOnClickListener(v -> getDialogSeleccionarFecha(DIALOG_FECHA_DESDE));

        //txtFechaHasta
        this.txtFechaHasta = view.findViewById(R.id.txtFechaHasta);//(EditText)
        this.txtFechaHasta.setKeyListener(null);
        this.txtFechaHasta.setOnClickListener(v -> getDialogSeleccionarFecha(DIALOG_FECHA_HASTA));

        //lstVentas
        this.lstVentas = view.findViewById(R.id.lstVentas);//(ListView)
        this.lstVentas.setOnItemClickListener((parent, view1, position, id) -> lstOnItemClick(parent, position));
    }

    private void initVar(View view) {
        //lblTotalValue
        this.lblTotalValue = view.findViewById(R.id.lblTotalValue); //(TextView)
        this.lblTotalSImpValue = view.findViewById(R.id.lblTotalSubSImpVtaValue);

        //fechaDesde, por defecto el primer dia del mes actual
        Calendar calendarDesde = Calendar.getInstance();
        calendarDesde.set(Calendar.DAY_OF_MONTH, 1);
        this.fechaDesde = calendarDesde.getTime();//Calendar.getInstance().getTime();

        //fechaHasta, por defecto el primer dia del mes siguiente
        calendarDesde.add(Calendar.MONTH, 1);
        this.fechaHasta = calendarDesde.getTime();

        String sFechaDesde = Formatter.formatDate(this.fechaDesde);
        String sFechaHasta = Formatter.formatDate(this.fechaHasta);

        this.txtFechaDesde.setText(sFechaDesde);
        this.txtFechaHasta.setText(sFechaHasta);

        this._ventas = new ArrayList<>();
        this._adapter = new ListadoVentaAdapter(getActivity(), this._ventas, this.empresa);
        this.lstVentas.setAdapter(this._adapter);
    }

    private void lstOnItemClick(AdapterView<?> adapter, int position) {
        itemSeleccionado = (Venta) adapter.getItemAtPosition(position);
        getDialogOpciones();
    }

    private void getDialogOpciones() {
        String[] items = new String[]{"Ver detalles"};
        OptionsDialogFragment optionsDialogFragment = OptionsDialogFragment.newInstance(items, pos -> dlgOpcionesOnClick(pos));
        optionsDialogFragment.show(getFragmentManager(), TAG_VENTAS_X_VENDEDOR);
    }

    private void dlgOpcionesOnClick(int position) {
        switch (position) {
            case 0: //Ver detalles
                mostrarPedido();
                break;
            case 1: //Cancelar pedido
                deletePedido();
                break;
        }
    }

    private void mostrarPedido() {
        Intent intent = new Intent(getActivity(), FrmVenta.class);
        Venta venta;//inicializa null
        VentaBo ventaBo = new VentaBo(_repoFactory);
        int consulta = 2;
        try {
            venta = ventaBo.recoveryById(itemSeleccionado.getId());
            intent.putExtra("venta", venta);
            intent.putExtra("modo", consulta);
            if (venta == null) {
                Toast.makeText(getActivity(), getString(R.string.msjClienteDeshabilitado), Toast.LENGTH_LONG).show();
            } else {
                startActivity(intent);
            }
        } catch (Exception e) {
            ErrorManager.manageException("FrmListadoVenta", "mostrarPedido", e, getActivity());
        }
    }

    private void deletePedido() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.msjCancelarPedido)
                .setCancelable(false)
                .setPositiveButton("Si", (dialog, id) -> onDeletePedido())
                .setNegativeButton("No", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void onDeletePedido() {
        Venta venta;
        VentaBo ventaBo = new VentaBo(_repoFactory);
        try {
            venta = ventaBo.recoveryById(itemSeleccionado.getId());
            ventaBo.delete(venta, getContext());
            getActivity().finish();
        } catch (Exception e) {
            ErrorManager.manageException("FrmListadoVenta", "onDeletePedido", e, getActivity());
        }
    }

    public Object getLastNonConfigurationInstance() {
        // TODO Auto-generated method stub
        return itemSeleccionado;
    }

}
