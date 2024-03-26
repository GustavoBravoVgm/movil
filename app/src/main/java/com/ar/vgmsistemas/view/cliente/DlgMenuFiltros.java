package com.ar.vgmsistemas.view.cliente;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.LocalidadBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.SucursalBo;
import com.ar.vgmsistemas.entity.Localidad;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.entity.Sucursal;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.view.dialogs.BaseDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment.DialogListener;

import java.util.List;

public class DlgMenuFiltros extends BaseDialogFragment {

    private Spinner cmbLocalidades;
    private Spinner cmbSucursales;
    private RadioButton rbVisitados;
    private RadioButton rbNoVisitados;
    private RadioButton rbTodos;
    private Button btnAceptar;
    private Button btnCancelar;
    private static DialogListener mListener;
    private boolean showEstado = true;
    private static final String EXTRA_SHOW_ESTADO = "EXTRA_SHOW_ESTADO";

    //BD
    private RepositoryFactory _repoFactory;

    public static DlgMenuFiltros newInstance(DialogListener listener) {
        mListener = listener;
        DlgMenuFiltros dialog = new DlgMenuFiltros();
        return dialog;
    }

    public static DlgMenuFiltros newInstance(DialogListener listener, boolean showEstado) {
        mListener = listener;
        DlgMenuFiltros dialog = new DlgMenuFiltros();
        Bundle b = new Bundle();
        b.putBoolean(EXTRA_SHOW_ESTADO, showEstado);
        dialog.setArguments(b);
        return dialog;
    }

    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.lyt_filtros_cliente, null);
        Bundle b = getArguments();
        if (b != null)
            showEstado = getArguments().getBoolean(EXTRA_SHOW_ESTADO, true);
        alertDialogBuilder.setView(view);
        initComponents(view);
        loadLocalidades();
        loadSucursales();
        alertDialogBuilder.setTitle("Filtros");
        return alertDialogBuilder.show();
    }

    private void initComponents(View view) {
        if (!showEstado) {
            LinearLayout layoutEstado = (LinearLayout) view.findViewById(R.id.idLinearEstado);
            layoutEstado.setVisibility(View.GONE);
        }
        this.cmbSucursales = (Spinner) view.findViewById(R.id.cmbSucCliente);
        cmbSucursales.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Sucursal sucursal = (Sucursal) arg0.getItemAtPosition(arg2);
                PreferenciaBo.getInstance().getPreferencia(getActivity()).setFiltroSucursalCliente(sucursal.getIdSucursal());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        // cmbLocalidades
        this.cmbLocalidades = (Spinner) view.findViewById(R.id.cmbLocalidad);
        cmbLocalidades.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Localidad localidad = (Localidad) arg0.getItemAtPosition(arg2);
                PreferenciaBo.getInstance().getPreferencia(getActivity()).setFiltroLocalidadCliente(localidad.getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        // rbEnviados
        this.rbVisitados = (RadioButton) view.findViewById(R.id.rbVisitados);

        // rbNoEnviados
        this.rbNoVisitados = (RadioButton) view.findViewById(R.id.rbNoVisitados);

        // rbTodos
        this.rbTodos = (RadioButton) view.findViewById(R.id.rbTodos);

        int filtroPreferido = PreferenciaBo.getInstance().getPreferencia(getActivity()).getFiltroPreferidoCliente();
        int idLocalidad = PreferenciaBo.getInstance().getPreferencia(getActivity()).getFiltroLocalidadCliente();
        rbNoVisitados.setChecked(filtroPreferido == Preferencia.FILTRO_CLIENTES_SIN_VISITAS);
        rbVisitados.setChecked(filtroPreferido == Preferencia.FILTRO_CLIENTES_VISITADOS);
        rbTodos.setChecked(filtroPreferido == Preferencia.FILTRO_CLIENTES_TODOS);

        // btnAceptar
        this.btnAceptar = (Button) view.findViewById(R.id.btnAceptar);
        btnAceptar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (mListener instanceof OkListener) {
                    ((OkListener) mListener).onOkSelected();
                } else if (mListener == null) {
                    dismiss();
                }
                dismiss();
                btnAceptarOnClick();
            }
        });

        // btnCancelar
        this.btnCancelar = (Button) view.findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                if (mListener instanceof OkListener) {

                }
                btnCancelarOnClick();
            }
        });

        //inicio bd
        this._repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);

    }

    protected void btnCancelarOnClick() {
        this.dismiss();
    }

    protected void btnAceptarOnClick() {
        // medio malo hasta que saber como se hace el grupo de radio buttons
        boolean visitados = this.rbVisitados.isChecked();
        boolean noVisitados = this.rbNoVisitados.isChecked();

        int filtroPreferido = Preferencia.FILTRO_CLIENTES_TODOS;
        if (visitados)
            filtroPreferido = Preferencia.FILTRO_CLIENTES_VISITADOS;
        else if (noVisitados)
            filtroPreferido = Preferencia.FILTRO_CLIENTES_SIN_VISITAS;


        PreferenciaBo.getInstance().getPreferencia(getActivity()).setFiltroPreferidoCliente(filtroPreferido);
        this.dismiss();

    }

    private void loadLocalidades() {
        LocalidadBo localidadBo = new LocalidadBo(_repoFactory);
        try {
            List<Localidad> localidades = localidadBo.recoveryAll();
            Localidad localidad = new Localidad();
            localidad.setCodigoPostal(-1);
            localidad.setDescripcion("Todas");
            localidad.setId(-1);
            localidades.add(0, localidad);
            ArrayAdapter<Localidad> adapter = new ArrayAdapter<>(getActivity(), R.layout.simple_spinner_item,
                    localidades);
            this.cmbLocalidades.setAdapter(adapter);

            int idLocalidadFiltro = PreferenciaBo.getInstance().getPreferencia(getActivity()).getFiltroLocalidadCliente();

            int size = localidades.size();
            for (int i = 0; i < size; i++) {
                localidad = (Localidad) cmbLocalidades.getItemAtPosition(i);
                if (localidad.getId() == idLocalidadFiltro) {
                    this.cmbLocalidades.setSelection(i);
                    break;
                }
            }
        } catch (Exception e) {
            ErrorManager.manageException("FrmGestionCliente", "loadClientes", e, getActivity());
        }
    }

    private void loadSucursales() {
        try {
            SucursalBo sucursalBo = new SucursalBo(_repoFactory);
            List<Sucursal> sucursales = sucursalBo.recoveryAll();
            ArrayAdapter<Sucursal> adapter = new ArrayAdapter<>(getActivity(), R.layout.simple_spinner_item,
                    sucursales);
            this.cmbSucursales.setAdapter(adapter);

            long idSucursalFiltro = PreferenciaBo.getInstance().getPreferencia(getActivity()).getFiltroSucursalCliente();
            for (int i = 0; i < sucursales.size(); i++) {
                Sucursal sucursal = (Sucursal) cmbSucursales.getItemAtPosition(i);
                if (sucursal.getIdSucursal() == idSucursalFiltro) {
                    this.cmbSucursales.setSelection(i);
                    break;
                }
            }
        } catch (Exception e) {
            ErrorManager.manageException("FrmGestionCliente", "loadSucursales", e, getActivity());
        }
    }

    public interface OkListener extends DialogListener {
        void onOkSelected();
    }
}
