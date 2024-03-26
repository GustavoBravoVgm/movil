package com.ar.vgmsistemas.view.nopedido;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.NoPedido;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.informes.FrmListadoNoPedido;
import com.ar.vgmsistemas.view.menu.ListBaseNavigationFragment;
import com.google.android.material.textfield.TextInputEditText;

public class FrmDetalleNoPedido extends ListBaseNavigationFragment {

    private TextView lblCodigo;
    private TextView txtCliente;
    private TextInputEditText txtMotivo;
    private TextInputEditText txtFecha;
    private TextInputEditText txtObservacion;
    private NoPedido _noPedido;

    public static FrmDetalleNoPedido newInstance(NoPedido noPedido) {
        Bundle b = new Bundle();
        b.putSerializable(FrmListadoNoPedido.KEY_NO_PEDIDO, noPedido);
        FrmDetalleNoPedido frmDetalleNoPedido = new FrmDetalleNoPedido();
        frmDetalleNoPedido.setArguments(b);
        return frmDetalleNoPedido;
    }

    private void initComponents() {
        //TextView codigo(TextView)
        lblCodigo = getView().findViewById(R.id.lblCodigoClienteDetalleNoPedido);

        //TextView cliente
        txtCliente = getView().findViewById(R.id.txtClienteDetalleNoPedido);
        txtCliente.setKeyListener(null);

        //TextView fecha(TextView)
        txtFecha = getView().findViewById(R.id.txtFechaDetalleNoPedido);
        txtFecha.setKeyListener(null);

        //TextView motivo(TextView)
        txtMotivo = getView().findViewById(R.id.txtMotivoDetalleNoPedido);
        txtMotivo.setKeyListener(null);

        //TextView observacion(TextView)
        txtObservacion = getView().findViewById(R.id.txtObservacionDetalleNoPedido);
        txtObservacion.setKeyListener(null);
    }

    private void loadData() {
        lblCodigo.setText(_noPedido.getCliente().getId().toString());
        txtCliente.setText(_noPedido.getCliente().getRazonSocial());

        txtFecha.setText(Formatter.formatDateTime(_noPedido.getFechaNoPedido()));
        txtMotivo.setText(_noPedido.getMotivoNoPedido().getDescripcion());
        txtObservacion.setText(_noPedido.getObservacion());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyt_no_pedido_detalle, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle b = getArguments();
        _noPedido = (NoPedido) b.getSerializable("noPedido");
        initComponents();
        loadData();

    }
}
