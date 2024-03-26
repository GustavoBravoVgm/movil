package com.ar.vgmsistemas.ws.vendedor;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import com.ar.vgmsistemas.bo.VendedorBo;

public class StateVendedorResultReceiver extends ResultReceiver {
    public StateVendedorResultReceiver(Handler handler) {
        super(handler);

    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultCode == StateVendedorService.RESULT_OK) {
            boolean result = resultData.getBoolean(StateVendedorService.RESULT_LOGIN);
            VendedorBo.setValidVendedor(result);
        }

        super.onReceiveResult(resultCode, resultData);
    }


}
