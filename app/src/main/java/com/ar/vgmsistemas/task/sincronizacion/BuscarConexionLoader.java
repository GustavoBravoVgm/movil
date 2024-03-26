package com.ar.vgmsistemas.task.sincronizacion;

import android.content.Context;

import com.ar.vgmsistemas.bo.SincronizacionBo;
import com.ar.vgmsistemas.repository.RepositoryFactory;

public class BuscarConexionLoader extends BaseAsyncTaskLoader<Integer> {
    private Integer result;
    private Context mContext;

    public BuscarConexionLoader(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public Integer loadInBackground() {
        boolean done;
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(mContext, RepositoryFactory.ROOM);
        SincronizacionBo sincronizacionBo = new SincronizacionBo(mContext, repoFactory);
        done = sincronizacionBo.isUrlActiva();
        result = (done) ? RESULT_OK : RESULT_ERROR;
        return result;
    }

}
