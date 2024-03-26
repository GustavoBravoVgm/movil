package com.ar.vgmsistemas.view.reparto.hojas.detalles.Multiples;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.view.BaseFragment;
import com.ar.vgmsistemas.view.reparto.hojas.detalles.CabeceraVentaAdapter;

import java.util.List;

public class CabecerasFacturasHojaFragment extends BaseFragment {
    ListView ListadoCabeceras;
    List<HojaDetalle> hojasDetalles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        List<HojaDetalle> hojasDetalles = (List<HojaDetalle>) getArguments().getSerializable(FrmDetallesFacturasReparto.KEY_HOJA_DETALLE);
        View view = View.inflate(getContext(), R.
                layout.lyt_cabecera_ver_detalles_hojas, null);

        ListadoCabeceras = view.findViewById(R.id.ListadoCabecerasVenta);
        CabeceraVentaAdapter adapter = new CabeceraVentaAdapter(getContext(), hojasDetalles);
        ListadoCabeceras.setAdapter(adapter);
        //Cargo los datos del cliente
        TextView tvCliente = (TextView) view.findViewById(R.id.tvCliente);
        TextView tvCuit = (TextView) view.findViewById(R.id.tvCuit);
        TextView tvCondVta = (TextView) view.findViewById(R.id.tvCondVta);
        TextView tvCatFiscal = (TextView) view.findViewById(R.id.tvCategoriaFiscal);

        tvCliente.setText(hojasDetalles.get(0).getCliente().getRazonSocial());
        tvCuit.setText(hojasDetalles.get(0).getCliente().getCuit());
        tvCondVta.setText(String.valueOf(hojasDetalles.get(0).getCondicionVenta()));
        tvCatFiscal.setText(hojasDetalles.get(0).getCliente().getCategoriaFiscal().getDescripcion());

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
