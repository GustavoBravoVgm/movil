package com.ar.vgmsistemas.bo;

import android.content.Context;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.VendedorObjetivo;
import com.ar.vgmsistemas.entity.VendedorObjetivoDetalle;
import com.ar.vgmsistemas.repository.IVendedorObjetivoDetalleRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.List;

public class VendedorObjetivoDetalleBo {
    private final IVendedorObjetivoDetalleRepository _vendedorObjetivoDetalleRepository;

    public VendedorObjetivoDetalleBo(RepositoryFactory repoFactory) {
        this._vendedorObjetivoDetalleRepository = repoFactory.getVendedorObjetivoDetalleRepository();
    }

    public List<VendedorObjetivoDetalle> recoveryByObjetivo(VendedorObjetivo objetivo) throws Exception {
        return _vendedorObjetivoDetalleRepository.recoveryDetalles(objetivo);
    }

    public static Integer getProgress(VendedorObjetivoDetalle detalle, int categoriaObjetivo) {
        return detalle.getCaLograda().intValue();
    }

    public static boolean isCompleted(VendedorObjetivoDetalle detalle, int categoria) {
        return detalle.getCaLograda() >= detalle.getObjetivo(categoria);

    }

    public static int getColorLogrado(VendedorObjetivoDetalle detalle, int categoriaObjetivo, Context context) {

        if ((VendedorObjetivoDetalleBo.isCompleted(detalle, categoriaObjetivo))) {
            return context.getResources().getColor(R.color.green);
        } else {
            if (detalle.getCaLograda() >= (detalle.getObjetivo(categoriaObjetivo) / 2)) {
                return context.getResources().getColor(R.color.yellow);
            } else {
                return context.getResources().getColor(R.color.orange);
            }
        }
    }
}
