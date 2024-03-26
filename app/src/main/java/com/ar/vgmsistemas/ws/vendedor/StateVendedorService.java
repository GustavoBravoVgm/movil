package com.ar.vgmsistemas.ws.vendedor;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

public class StateVendedorService extends IntentService {

    public static final String NAME_SERVICE = "com.ar.vgmsistemas.ws.LoginService";
    public static final String EXTRA_RECEIVER = "receiver_extra";
    public static final String EXTRA_ID_VENDEDOR = "id_vendedor";
    public static final String RESULT_LOGIN = "result_login";
    public static final int RESULT_OK = 1;
    public static final int RESULT_ERROR = 0;

    public StateVendedorService() {
        super(NAME_SERVICE);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final ResultReceiver receiver = intent.getParcelableExtra(EXTRA_RECEIVER);
        int idVendedor = intent.getIntExtra(EXTRA_ID_VENDEDOR, 0);
        VendedorWs loginWs = new VendedorWs(StateVendedorService.this);
        try {
            boolean isValid = loginWs.isValidVendedor(idVendedor);
            Bundle data = new Bundle();
            data.putBoolean(RESULT_LOGIN, isValid);
            receiver.send(StateVendedorService.RESULT_OK, data);
        } catch (Exception e) {
            receiver.send(StateVendedorService.RESULT_ERROR, null);
            e.printStackTrace();
        }


    }


}
