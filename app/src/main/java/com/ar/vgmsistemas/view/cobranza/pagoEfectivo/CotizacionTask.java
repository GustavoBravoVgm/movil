package com.ar.vgmsistemas.view.cobranza.pagoEfectivo;

import android.content.Context;
import android.os.AsyncTask;

import com.ar.vgmsistemas.entity.TipoMoneda;
import com.ar.vgmsistemas.ws.TipoMonedaWs;

public class CotizacionTask extends AsyncTask<Void, Void, Void> {

    private FrmAgregarPagoEfectivo frmAgregarPagoEfectivo;
    private TipoMoneda tipoMoneda;
    private double cotizacion;
    private Context mContext;

    public CotizacionTask(FrmAgregarPagoEfectivo frmAgregarPagoEfectivo, TipoMoneda tipoMoneda, Context context) {
        attach(frmAgregarPagoEfectivo);
        this.tipoMoneda = tipoMoneda;
        this.mContext = context;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        TipoMonedaWs tipoMonedaWs = new TipoMonedaWs(mContext);
        try {
            cotizacion = tipoMonedaWs.recoveryCotizacion(tipoMoneda.getId());
            //TODO actualizar la cotizacion
        } catch (Exception e) {
            cotizacion = tipoMoneda.getCotizacion();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... unused) {
    }

    @Override
    protected void onPostExecute(Void unused) {
        frmAgregarPagoEfectivo.setCotizacion(cotizacion);
        detach();
    }

    void detach() {
        frmAgregarPagoEfectivo = null;
    }

    void attach(FrmAgregarPagoEfectivo frmAgregarPagoEfectivo) {
        this.frmAgregarPagoEfectivo = frmAgregarPagoEfectivo;
    }

}
