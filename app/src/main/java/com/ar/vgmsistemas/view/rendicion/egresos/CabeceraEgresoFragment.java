package com.ar.vgmsistemas.view.rendicion.egresos;

import static com.ar.vgmsistemas.view.rendicion.egresos.FrmEgreso.ALTA;
import static com.ar.vgmsistemas.view.rendicion.egresos.FrmEgreso.CONSULTA;
import static com.ar.vgmsistemas.view.rendicion.egresos.FrmEgreso.MODIFICACION;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.DocumentoBo;
import com.ar.vgmsistemas.bo.PlanCuentaBo;
import com.ar.vgmsistemas.bo.ProveedorBo;
import com.ar.vgmsistemas.bo.RendicionBo;
import com.ar.vgmsistemas.entity.Compra;
import com.ar.vgmsistemas.entity.Documento;
import com.ar.vgmsistemas.entity.PlanCuenta;
import com.ar.vgmsistemas.entity.Proveedor;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.Formatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CabeceraEgresoFragment extends Fragment {
    private static int INDEX_DETALLE = 1;
    private static final int DATE_DIALOG = 0;
    private static final int CAMBIAR_LETRA_DIALOG = 1;
    //private Activity mActivity;
    private Compra compra;
    private boolean argumentosLeidos = false;
    private boolean limpiarInputs = false;
    private FrmEgreso mFrmEgreso;
    //private Spinner cmbSucursalProveedor;
    private Spinner cmbProveedor;
    private Spinner cmbCuenta;
    private Spinner cmbTipoDocumento;
    private Spinner cmbLetra;

    private EditText txtPuntoVenta;
    private EditText txtNumero;
    private EditText txtFechaComprobante;
    private EditText txtSubtotal;
    private EditText txtExento;
    private EditText txtConcepto;

    private TextView txtCuit;

    private int _modo;

    List<Documento> documentos = new ArrayList<>();

    //DAO
    private RepositoryFactory _repoFactory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFrmEgreso = (FrmEgreso) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.lyt_egreso_cabecera, null);
        initComponents(view);
        loadData();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.mn_lista_recibos, menu);
    }

    public void loadData() {
        Bundle b = getArguments();
        if (b != null && !isArgumentosLeidos()) {
            this.compra = (Compra) b.getSerializable(Compra.EXTRA_COMPRA);
            this._modo = b.getInt(Compra.EXTRA_MODO, ALTA);
            setArgumentosLeidos(true);
        }
        _repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        final ProveedorBo proveedorBo = new ProveedorBo(_repoFactory);
        final DocumentoBo documentoBo = new DocumentoBo(_repoFactory);
        final PlanCuentaBo planCuentaBo = new PlanCuentaBo(_repoFactory);
        List<Proveedor> proveedores = new ArrayList<>();
        List<String> tipoDocumentos = new ArrayList<>();
        List<PlanCuenta> planesCuentas = new ArrayList<>();
        if (_modo == ALTA || _modo == MODIFICACION) {
            try {
                proveedores = proveedorBo.recoveryProveedoresTipoGasto();
                //sucursales = sucursalBo.recoveryAll();
                documentos = documentoBo.recoveryDocumentosEgreso();
                for (Documento documento : documentos) {
                    tipoDocumentos.add(documento.getId().getIdDocumento() + " - " + documento.getDescripcion());
                }
                planesCuentas = planCuentaBo.recoveryForEgreso();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else { //
            try {

                proveedores.add(proveedorBo.recoveryProveedorById(compra.getId().getIdProveedor()));

                String idFcnc = compra.getId().getIdFcncnd();
                String idLetra = compra.getIdLetra();
                documentos.add(documentoBo.recoveryDocumentoEgresoByIdfcndEIdLetra(idFcnc, idLetra));
                for (Documento documento : documentos) {
                    tipoDocumentos.add(documento.getId().getIdDocumento() + " - " + documento.getDescripcion());
                }

                if (compra.getIdPlancta() != null)
                    planesCuentas.add(planCuentaBo.recoveryById(compra.getIdPlancta()));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        ArrayAdapter<Proveedor> adapterProveedores = new ArrayAdapter<>(getContext(),
                R.layout.simple_spinner_item, proveedores);
        cmbProveedor.setAdapter(adapterProveedores);

        final List<PlanCuenta> finalPlanesCuentas = planesCuentas;
        cmbProveedor.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Proveedor proveedor = ((Proveedor) cmbProveedor.getAdapter().getItem(position));
                    txtCuit.setText(proveedor.getNuCuit());
                    PlanCuenta planCuenta = null;
                    if (mFrmEgreso.getProveedorAnteriorSeleccionado() != null
                            && mFrmEgreso.getProveedorAnteriorSeleccionado().equals(proveedor)) {
                        planCuenta = planCuentaBo.recoveryById(compra.getIdPlancta());
                    } else {
                        try {
                            planCuenta = planCuentaBo.recoveryById(proveedor.getIdPlancta());
                            compra.getId().setIdProveedor(proveedor.getIdProveedor());
                            compra.setNuCuit(proveedor.getNuCuit());
                            compra.setIdPlancta(planCuenta.getId());
                            compra.setIdSucProv(proveedor.getIdSucursal());
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getContext().getApplicationContext(), "El plan de cuenta del proveedor no se encuentra", Toast.LENGTH_SHORT).show();
                        }
                    }
                    cmbCuenta.setSelection(finalPlanesCuentas.indexOf(planCuenta));
                    mFrmEgreso.setProveedorAnteriorSeleccionado(proveedor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<PlanCuenta> adapterPlanesCuenta = new ArrayAdapter<>(getContext(),
                R.layout.simple_spinner_item, planesCuentas);
        cmbCuenta.setAdapter(adapterPlanesCuenta);
        cmbCuenta.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    PlanCuenta planCuenta = ((PlanCuenta) cmbCuenta.getAdapter().getItem(position));
                    compra.setIdPlancta(planCuenta.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> adapterTipoDocumentos = new ArrayAdapter<>(getContext(),
                R.layout.simple_spinner_item, tipoDocumentos);
        cmbTipoDocumento.setAdapter(adapterTipoDocumentos);
        if (compra.getId().getIdFcncnd() != null) {
            try {
                //Documento documento = documentoBo.recoveryDocumentoEgresoByIdfcndEIdLetra(compra.getId().getIdFcncnd(), compra.getIdLetra());
                int index = -1;
                do {
                    index++;
                } while (index < documentos.size() &&
                        (!documentos.get(index).getId().getIdDocumento().equals(compra.getId().getIdFcncnd())));
                //int index = documentos.indexOf(documento);
                if (index < documentos.size()) {
                    cmbTipoDocumento.setSelection(index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.cmbTipoDocumento.setOnItemSelectedListener(new OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Documento documento = documentos.get(position);
                compra.getId().setIdFcncnd(documento.getId().getIdDocumento());
                try {
                    documento = documentoBo.recoveryDocumentoEgresoByIdfcndEIdLetra(compra.getId().getIdFcncnd(), compra.getIdLetra());
                    if (documento.isLegal()) {
                        habilitarImpuestos();
                    } else {
                        deshabilitarImpuestos();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    documento = documentos.get(position);
                }

                List<String> letras = new ArrayList<>();
                try {
                    letras = documentoBo.getLetrasByIdFcnc(documento.getId().getIdDocumento());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ArrayAdapter<String> adapterLetras = new ArrayAdapter<>(getContext(),
                        R.layout.simple_spinner_item, letras);
                cmbLetra.setAdapter(adapterLetras);
                if (compra.getIdLetra() != null) {
                    cmbLetra.setSelection(adapterLetras.getPosition(compra.getIdLetra()));
                }
                cmbLetra.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            String letra = ((String) cmbLetra.getAdapter().getItem(position));
                            compra.setIdLetra(letra);
                            Documento documento = documentoBo.recoveryDocumentoEgresoByIdfcndEIdLetra(compra.getId().getIdFcncnd(), compra.getIdLetra());
                            if (documento.isLegal()) {
                                habilitarImpuestos();
                            } else {
                                if (compra.getComprasImpuestos().isEmpty()) {
                                    deshabilitarImpuestos();
                                } else {
                                    showDialog(CAMBIAR_LETRA_DIALOG);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        this.txtFechaComprobante.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });
        if (_modo == ALTA) {
            Calendar calendar = Calendar.getInstance();
            String fechaActual = Formatter.formatDate(calendar.getTime());
            this.txtFechaComprobante.setText(fechaActual);
            compra.setFeFactura(calendar.getTime());
        }

        txtExento.setText(compra.getPrExento() + "");
        txtExento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                double exento = 0d;
                if (!s.toString().equals("")) {
                    exento = Double.parseDouble(s.toString());
                }
                compra.setPrExento(exento);
                RendicionBo.sumarTotalesCompra(compra);
                mFrmEgreso.actualizarTotales();
            }
        });
        if (compra.getDeConcepto() != null) {
            txtConcepto.setText(compra.getDeConcepto());
        }
        txtConcepto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                compra.setDeConcepto(s.toString());
            }
        });
        if (compra.getId().getIdPuntoVenta() != null) {
            txtPuntoVenta.setText(compra.getId().getIdPuntoVenta().toString());
        }
        txtPuntoVenta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int idPtovta = 0;
                if (!s.toString().equals("")) {
                    idPtovta = Integer.parseInt(s.toString());
                }
                compra.getId().setIdPuntoVenta(idPtovta);
            }
        });

        if (compra.getId().getIdNumero() > 0) {
            txtNumero.setText(compra.getId().getIdNumero() + "");
        }
        txtNumero.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int idNumero = 0;
                if (!s.toString().equals("")) {
                    idNumero = Integer.parseInt(s.toString());
                }
                compra.getId().setIdNumero(idNumero);
            }
        });

        txtSubtotal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (txtSubtotal.hasFocus()) {
                    double subtotal = 0d;
                    if (!s.toString().equals("")) {
                        subtotal = Double.parseDouble(s.toString());
                    }
                    compra.setPrSubtotal(subtotal);
                    RendicionBo.sumarTotalesCompra(compra);
                    mFrmEgreso.actualizarTotales();
                }
            }
        });
        String prSubtString = String.valueOf(compra.getPrSubtotal());
        txtSubtotal.setText(prSubtString, TextView.BufferType.EDITABLE);

        if (_modo == MODIFICACION || _modo == CONSULTA) { //No puedo modificar los campos que son clave
            //cmbSucursalProveedor.setEnabled(false);
            cmbProveedor.setEnabled(false);
            cmbTipoDocumento.setEnabled(false);
            txtNumero.setEnabled(false);
            txtPuntoVenta.setEnabled(false);
            String fechaFactura = Formatter.formatDate(compra.getFeFactura());
            txtFechaComprobante.setText(fechaFactura);
        }

        if (_modo == CONSULTA) {
            cmbCuenta.setEnabled(false);
            cmbLetra.setEnabled(false);
            txtConcepto.setEnabled(false);
        }
    }

    private void habilitarImpuestos() {
        txtSubtotal.setEnabled(false);
        txtExento.setEnabled(false);
        /* mFrmEgreso.getTabHost().getTabWidget().getChildTabViewAt(INDEX_DETALLE).setEnabled(true);
        mFrmEgreso.getTabHost().getTabWidget().getChildTabViewAt(INDEX_DETALLE)
                .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.base_background_activity_color));*/
        //llamo al metodo de FrmEgreso
        mFrmEgreso.setEnableTabWidget(true, INDEX_DETALLE);
    }

    private void deshabilitarImpuestos() {
        txtSubtotal.setEnabled(true);
        txtExento.setEnabled(true);
        /*mFrmEgreso.getTabHost().getTabWidget().getChildTabViewAt(INDEX_DETALLE).setEnabled(false);
        mFrmEgreso.getTabHost().getTabWidget().getChildTabViewAt(INDEX_DETALLE)
                .setBackgroundColor(ContextCompat.getColor(getContext(), R.color.background_gray));*/
        //llamo al metodo de FrmEgreso
        mFrmEgreso.setEnableTabWidget(false, INDEX_DETALLE);
    }

    private void initComponents(View view) {
        //this.cmbSucursalProveedor = (Spinner) view.findViewById(R.id.cmbSucProveedor);
        this.cmbProveedor = (Spinner) view.findViewById(R.id.cmbProveedor);
        this.cmbCuenta = (Spinner) view.findViewById(R.id.cmbCuenta);
        this.cmbLetra = (Spinner) view.findViewById(R.id.cmbLetra);
        this.cmbTipoDocumento = (Spinner) view.findViewById(R.id.cmbTipoDocumento);
        this.txtCuit = (TextView) view.findViewById(R.id.txtCuit);
        this.txtPuntoVenta = (EditText) view.findViewById(R.id.txtPtoVta);
        this.txtNumero = (EditText) view.findViewById(R.id.txtNumero);
        this.txtSubtotal = (EditText) view.findViewById(R.id.idSubtotalManual);
        this.txtExento = (EditText) view.findViewById(R.id.idExentoManual);
        this.txtConcepto = (EditText) view.findViewById(R.id.txtConcepto);
        this.txtFechaComprobante = (EditText) view.findViewById(R.id.txtFechaComprobante);
        this.txtFechaComprobante.setKeyListener(null);
    }

    private void showDialog(int typeDialog) {
        switch (typeDialog) {
            case DATE_DIALOG: {
                Calendar cal = Calendar.getInstance();
                cal.setTime(compra.getFeFactura());

                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialogFecha = new DatePickerDialog(getContext(), new OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txtFechaOnDateSet(view, year, monthOfYear, dayOfMonth);
                    }
                }, year, month, day);
                dialogFecha.show();
                break;
            }
            case CAMBIAR_LETRA_DIALOG: {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.tituloCambiarLetraEgreso);
                builder.setMessage(R.string.msjCambiarLetraEgreso);
                builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deshabilitarImpuestos();
                        compra.getComprasImpuestos().removeAll(compra.getComprasImpuestos());
                        txtSubtotal.setText("0.0");
                        compra.setPrSubtotal(0d);
                        RendicionBo.sumarTotalesCompra(compra);
                        mFrmEgreso.actualizarTotales();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    public void txtFechaOnDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);

        Date fechaFactura = cal.getTime();
        compra.setFeFactura(fechaFactura);
        compra.setFeIva(fechaFactura);
        txtFechaComprobante.setText(Formatter.formatDate(fechaFactura));
    }

    @Override
    public void onResume() {
        super.onResume();
        ProveedorBo proveedorBo = new ProveedorBo(_repoFactory);
        try {
            Proveedor proveedor = proveedorBo.recoveryProveedorById(compra.getId().getIdProveedor());
            mFrmEgreso.setProveedorAnteriorSeleccionado(proveedor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isLimpiarInputs()) {
            limpiarImputs();
            setLimpiarInputs(false);
        }
    }

    public boolean isArgumentosLeidos() {
        return argumentosLeidos;
    }

    public void setArgumentosLeidos(boolean argumentosLeidos) {
        this.argumentosLeidos = argumentosLeidos;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public boolean isLimpiarInputs() {
        return limpiarInputs;
    }

    public void setLimpiarInputs(boolean limpiarInputs) {
        this.limpiarInputs = limpiarInputs;
    }

    public void limpiarImputs() {
        //this.cmbSucursalProveedor.setSelection(0);
        this.cmbProveedor.setSelection(0);
        this.cmbLetra.setSelection(0);
        this.cmbTipoDocumento.setSelection(0);
        this.txtPuntoVenta.setText("");
        this.txtNumero.setText("");
        this.txtSubtotal.setText("0");
        this.txtExento.setText("0");
        this.txtConcepto.setText("");
    }
}
