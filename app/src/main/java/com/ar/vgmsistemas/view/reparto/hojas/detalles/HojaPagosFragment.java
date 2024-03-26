package com.ar.vgmsistemas.view.reparto.hojas.detalles;

import android.content.Intent;
import android.os.Bundle;

import com.ar.vgmsistemas.entity.Entrega;
import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.view.cobranza.PagosFragment;

public class HojaPagosFragment extends PagosFragment {

    //public static final String EXTRA_ENTREGA = "entrega";
    private Entrega mEntrega;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setControlMax(true);
        mEntrega = ((HojaDetalle) getArguments().getSerializable(EXTRA_ENTITY)).getEntrega();

        boolean isEnviada = getArguments().getBoolean(EXTRA_IS_HOJA_DETALLE_ENVIADA);
        setIsEnviada(isEnviada);
        setEntrega(mEntrega);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        ((FrmDetalleFacturaReparto) getActivity()).updateEntrega(getEntrega());
    }

    @Override
    public double getMontoMaximo() {
        return getMontoFactura() - ((FrmDetalleFacturaReparto) getActivity()).getHojaDetalle().getPrNotaCredito();

    }
}
