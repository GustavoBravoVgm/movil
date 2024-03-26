package com.ar.vgmsistemas.view.reparto.hojas.detalles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.BaseFragment;

public class CabeceraFacturaHojaFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HojaDetalle hojaDetalle = (HojaDetalle) getArguments().getSerializable(FrmDetalleFacturaReparto.KEY_HOJA_DETALLE);
        View view = View.inflate(getContext(), R.layout.lyt_cabecera_venta_hoja, null);
        TextView tvDoc = view.findViewById(R.id.tvDoc);
        TextView tvLetra = view.findViewById(R.id.tvLetra);
        TextView tvPtoVta = view.findViewById(R.id.tvPtoVta);
        TextView tvNum = view.findViewById(R.id.tvNum);
        TextView tvCliente = view.findViewById(R.id.tvCliente);
        TextView tvCuit = view.findViewById(R.id.tvCuit);
        TextView tvCondVta = view.findViewById(R.id.tvCondVta);
        TextView tvCatFiscal = view.findViewById(R.id.tvCategoriaFiscal);
        TextView tvFeVenta = view.findViewById(R.id.tvFechaVenta);

        tvDoc.setText(hojaDetalle.getId().getIdFcnc());
        tvLetra.setText(hojaDetalle.getId().getIdTipoab());
        tvPtoVta.setText(String.valueOf(hojaDetalle.getId().getIdPtovta()));
        tvNum.setText(String.valueOf(hojaDetalle.getId().getIdNumdoc()));

        tvCliente.setText(hojaDetalle.getCliente().getRazonSocial());
        tvCuit.setText(hojaDetalle.getCliente().getCuit());
        tvCondVta.setText(String.valueOf(hojaDetalle.getCondicionVenta()));
        tvCatFiscal.setText(hojaDetalle.getCliente().getCategoriaFiscal().getDescripcion());
        tvFeVenta.setText(Formatter.formatDate(hojaDetalle.getFeVenta()));

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }
}
