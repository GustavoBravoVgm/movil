package com.ar.vgmsistemas.view.rendicion.entregas;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.Entrega;
import com.ar.vgmsistemas.entity.Valor;
import com.ar.vgmsistemas.entity.ValorHardcodeado;
import com.ar.vgmsistemas.view.cobranza.PagosFragment;
import com.ar.vgmsistemas.view.cobranza.cheque.FrmGestionCheques;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;

import java.util.ArrayList;
import java.util.List;

public class FrmEntrega extends BaseNavigationFragment {
    EntregaAdapter adapter;
    ListView listadoEntregas;
    public String EXTRA_ENTREGA = "EXTRA_ENTREGA";
    Entrega mEntrega;
    Spinner spinnerValor;
    EditText tvCantidad;
    EditText tvTotal;
    Button buttonAdd;
    int ACTIVITY_AGREGAR_CHEQUE = 1;
    Valor valor = new Valor();
    KeyListener keyListenerTotal;
    List<ValorHardcodeado> valores;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.lyt_frm_entrega, container, false);
        initComponents(view);
        loadData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void loadData() {
        ArrayAdapter<ValorHardcodeado> adapterValores = new ArrayAdapter<>(getContext(),
                R.layout.simple_spinner_item, valor.getValoresHardcodeados());
        spinnerValor.setAdapter(adapterValores);
        valores = new ArrayList<>();
        adapter = new EntregaAdapter(getActivity(), R.layout.lyt_entrega_item, valores);
        listadoEntregas.setAdapter(adapter);
    }

    public void initComponents(View view) {
        spinnerValor = (Spinner) view.findViewById(R.id.spnValor);
        spinnerValor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ValorHardcodeado valorHardcodeado = (ValorHardcodeado) spinnerValor.getSelectedItem();
                if (valorHardcodeado.getValor() > 0) {
                    tvCantidad.setVisibility(View.VISIBLE);
                    tvTotal.setKeyListener(null);
                } else {
                    tvCantidad.setVisibility(View.INVISIBLE);
                    tvTotal.setKeyListener(keyListenerTotal);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tvCantidad = (EditText) view.findViewById(R.id.tvCantidad);
        tvCantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(""))
                    tvTotal.setText(0 + "");
                else {
                    String total = Float.parseFloat(s.toString()) * ((ValorHardcodeado) spinnerValor.getSelectedItem()).getValor() + "";
                    tvTotal.setText(total);
                }
            }
        });
        tvTotal = (EditText) view.findViewById(R.id.tvTotal);
        if (keyListenerTotal == null)
            keyListenerTotal = tvTotal.getKeyListener();
        buttonAdd = (Button) view.findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ValorHardcodeado valor = (ValorHardcodeado) spinnerValor.getSelectedItem();
                if (valores.contains(valor)) {
                    SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK_CANCEL, getString(R.string.atencion), getString(R.string.valorYaAgregado)).show(getFragmentManager(), "");
                } else {
                    valor.setCantidad(Integer.parseInt(tvCantidad.getText().toString()));
                    valor.setImporte(Float.parseFloat(tvTotal.getText().toString()));
                    valores.add(valor);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        listadoEntregas = (ListView) view.findViewById(R.id.listadoEntregas);
        listadoEntregas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK_CANCEL, getString(R.string.confirmarEliminar), new SimpleDialogFragment.OkCancelListener() {
                    @Override
                    public void onCancelSelected() {

                    }

                    @Override
                    public void onOkSelected() {
                        valores.remove(spinnerValor.getSelectedItemPosition());
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.mn_entrega, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.mni_agregar_cheque: {
                Intent frmGestionCheques = new Intent(getActivity(), FrmGestionCheques.class);
                mEntrega = new Entrega();
                frmGestionCheques.putExtra(PagosFragment.EXTRA_ENTREGA, mEntrega);
                startActivityForResult(frmGestionCheques, ACTIVITY_AGREGAR_CHEQUE);
                return false;
            }
            case R.id.mni_egresos:
            case R.id.mni_vales: {
                return false;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_AGREGAR_CHEQUE) {

        }
    }
}
