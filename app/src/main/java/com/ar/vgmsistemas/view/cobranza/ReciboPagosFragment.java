package com.ar.vgmsistemas.view.cobranza;

import android.content.Intent;
import android.os.Bundle;

import com.ar.vgmsistemas.entity.Entrega;
import com.ar.vgmsistemas.entity.Recibo;

public class ReciboPagosFragment extends PagosFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        Recibo recibo = ((Recibo) b.getSerializable(EXTRA_ENTITY));

        Entrega entrega = recibo.getEntrega();
        setEntrega(entrega);
        setMontoFactura(recibo.obtenerTotalDocumentosImputados());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        double total = getEntrega().obtenerTotalPagos();

        Recibo recibo = ((Recibo) getArguments().getSerializable(EXTRA_ENTITY));
        recibo.setEntrega(getEntrega());
        recibo.setTotal(total);
    }
}
