package com.ar.vgmsistemas.view.cliente;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;

public class FrmDetalleCliente extends BaseNavigationFragment {
    public static final String KEY_CLIENTE = "cliente";
    private TextView txtRazonSocial;
    private TextView txtDomicilio;
    private TextView txtLocalidad;
    private TextView txtTelefono;
    private TextView txtCuit;
    private TextView txtCategoriaFiscal;
    private TextView txtCondicionVenta;
    private Cliente mCliente;

    public static FrmDetalleCliente newInstance(Cliente cliente) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_CLIENTE, cliente);

        FrmDetalleCliente frmDetalleCliente = new FrmDetalleCliente();
        frmDetalleCliente.setArguments(bundle);
        return frmDetalleCliente;
    }

    private void initViews(View view) {

        // txtRazonSocial
        this.txtRazonSocial = (TextView) view.findViewById(R.id.txtRazonSocial);
        this.txtRazonSocial.setKeyListener(null);

        // txtDomicilio
        this.txtDomicilio = (TextView) view.findViewById(R.id.txtDomicilio);
        this.txtDomicilio.setKeyListener(null);

        // txtLocalidad
        this.txtLocalidad = (TextView) view.findViewById(R.id.txtLocalidad);
        this.txtLocalidad.setKeyListener(null);

        // txtLocalidad
        this.txtTelefono = (TextView) view.findViewById(R.id.txtTelefono);
        this.txtTelefono.setKeyListener(null);

        // txtCuit
        this.txtCuit = (TextView) view.findViewById(R.id.txtCuit);
        this.txtCuit.setKeyListener(null);

        // txtCategoriaFiscal
        this.txtCategoriaFiscal = (TextView) view.findViewById(R.id.txtCategoriaFiscal);
        this.txtCategoriaFiscal.setKeyListener(null);

        // txtCondicionVenta
        this.txtCondicionVenta = (TextView) view.findViewById(R.id.txtCondicionVenta);
        this.txtCondicionVenta.setKeyListener(null);

        txtRazonSocial.setText(mCliente.getRazonSocial());
        txtDomicilio.setText(mCliente.getDomicilio());
        txtLocalidad.setText(mCliente.getLocalidad().getDescripcion());
        txtTelefono.setText(mCliente.getTelefono());
        txtCuit.setText(mCliente.getCuit());
        txtCategoriaFiscal.setText(mCliente.getCategoriaFiscal().getDescripcion());
        txtCondicionVenta.setText(mCliente.getCondicionVenta().getDescripcion());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyt_cliente_detalles, null);
        initViews(view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCliente = (Cliente) getArguments().getSerializable(KEY_CLIENTE);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

}
