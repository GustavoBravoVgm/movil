package com.ar.vgmsistemas.view.reparto.hojas.detalles.Multiples;

import android.content.Intent;
import android.os.Bundle;

import com.ar.vgmsistemas.entity.Entrega;
import com.ar.vgmsistemas.entity.HojaDetalle;

import java.util.List;

public class PagoMultiplesHojasFragment extends PagoMultipleFragment {

    //public static final String EXTRA_ENTREGA = "entrega";
    private Entrega mEntrega;
    private List<HojaDetalle> mHojasDetalles;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setControlMax(true);
        mHojasDetalles = ((List<HojaDetalle>) getArguments().getSerializable(EXTRA_ENTITY));
        mEntrega = mHojasDetalles.get(0).getEntrega();
        boolean isEnviada = getArguments().getBoolean(EXTRA_IS_HOJA_DETALLE_ENVIADA);
        setIsEnviada(isEnviada);
        setEntrega(mEntrega);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        ((FrmDetallesFacturasReparto) getActivity()).updateEntrega(getEntrega());
    }

    @Override
    public double getMontoMaximo() {
        double montoFactura = getMontoFactura();
        double montoNC = 0d;
        List<HojaDetalle> hojasDetalle = ((FrmDetallesFacturasReparto) getActivity()).getHojaDetalle();
        for (HojaDetalle hojDet : hojasDetalle) {
            montoNC += hojDet.getPrNotaCredito();
        }
        double total = montoFactura - montoNC;
        return total;
    }
}
