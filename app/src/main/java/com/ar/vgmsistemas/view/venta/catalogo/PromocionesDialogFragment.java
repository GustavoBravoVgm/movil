package com.ar.vgmsistemas.view.venta.catalogo;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TableLayout;

import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatDialog;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.AccionesComBo;
import com.ar.vgmsistemas.bo.AccionesComDetalleBo;
import com.ar.vgmsistemas.bo.HojaDetalleBo;
import com.ar.vgmsistemas.entity.AccionesCom;
import com.ar.vgmsistemas.entity.AccionesComDetalle;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.ListaPrecio;
import com.ar.vgmsistemas.entity.ListaPrecioDetalle;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.view.dialogs.BaseDialogFragment;

import java.util.List;

public class PromocionesDialogFragment extends BaseDialogFragment {
    private RepositoryFactory _repoFactory;
    private List<AccionesComDetalle> mAccionesComDetalle;
    private ItemPromoArtAdapter itemPromoArtAdapter;
    private Boolean mTienePromociones;
    private HojaDetalleBo _hojaDetalleBo;
    private ListaPrecioDetalle mListaPrecioDetalleSelected;
    private Cliente mCliente;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
    }

    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        View itemView = View.inflate(getActivity(), R.layout.lyt_promociones_articulo_dialog, null);
        ViewHolder viewHolder = new ViewHolder(itemView);
        try {
            loadData(viewHolder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Builder builder = new Builder(getActivity());

        if (mTienePromociones) {
            builder.setView(itemView);
        } else {
            builder.setTitle("Articulo sin promociones.");
            builder.setIcon(R.drawable.acerca_de);
            builder.setMessage(ErrorManager.NoPoseePromocionesDisponibles);
        }
        return builder.show();
    }

    public void setListaPrecioDetalle(ListaPrecioDetalle listaPrecioDetalle) {
        mListaPrecioDetalleSelected = listaPrecioDetalle;
    }

    public void setCliente(Cliente cliente) {
        mCliente = cliente;
    }

    public void loadData(ViewHolder holder) throws Exception {
        if (_repoFactory == null) {
            _repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        }
        this._hojaDetalleBo = new HojaDetalleBo(_repoFactory);
        AccionesComBo accionesComBo = new AccionesComBo(_repoFactory);
        AccionesComDetalleBo accionesComDetalleBo = new AccionesComDetalleBo(_repoFactory);
        Articulo articulo = mListaPrecioDetalleSelected.getArticulo();
        Cliente cliente = mCliente;
        ListaPrecio lista = mListaPrecioDetalleSelected.getListaPrecio();
        List<AccionesComDetalle> accionesComDetalleProveedor = null;
        List<AccionesComDetalle> accionesComDetalleEmpresa = null;
        try {
            accionesComDetalleProveedor = accionesComDetalleBo.getAllAccionesComDetalle(articulo, cliente, lista, AccionesCom.TI_ORIGEN_PROVEEDOR);
            accionesComDetalleEmpresa = accionesComDetalleBo.getAllAccionesComDetalle(articulo, cliente, lista, AccionesCom.TI_ORIGEN_EMPRESA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (accionesComDetalleProveedor.size() == 0 & accionesComDetalleEmpresa.size() == 0) {
            mTienePromociones = false;
        } else {
            if (accionesComDetalleProveedor.size() > 0) {
                ItemPromoArtAdapter itemPromoArtAdapterProv = new ItemPromoArtAdapter(getActivity(), accionesComDetalleProveedor);
                holder.lstPromoProveedor.setAdapter(itemPromoArtAdapterProv);
                holder.tblytDtoProv.setVisibility(View.VISIBLE);
                mTienePromociones = true;
            } else {
                holder.tblytDtoProv.removeAllViews();
            }
            if (accionesComDetalleEmpresa.size() > 0) {
                ItemPromoArtAdapter itemPromoArtAdapterEmp = new ItemPromoArtAdapter(getActivity(), accionesComDetalleEmpresa);
                holder.lstPromoEmpresa.setAdapter(itemPromoArtAdapterEmp);
                holder.tblytDtoEmpr.setVisibility(View.VISIBLE);
                mTienePromociones = true;
            } else {
                holder.tblytDtoEmpr.removeAllViews();
            }
        }
    }


    static class ViewHolder {
        ListView lstPromoProveedor;
        ListView lstPromoEmpresa;
        TableLayout tblytDtoProv;
        TableLayout tblytDtoEmpr;

        public ViewHolder(View itemView) {
            lstPromoProveedor = (ListView) itemView.findViewById(R.id.lstPromoProveedor);
            lstPromoEmpresa = (ListView) itemView.findViewById(R.id.lstPromoEmpresa);
            tblytDtoEmpr = (TableLayout) itemView.findViewById(R.id.tableLayoutDtoEmpr);
            tblytDtoProv = (TableLayout) itemView.findViewById(R.id.tableLayoutDtoProv);
        }
    }

}

