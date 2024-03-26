package com.ar.vgmsistemas.view.reparto.hojas.listadoHojas;

import static com.ar.vgmsistemas.task.LoadResumenCobranzaTask.CargarResumenCobranzaListener;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatDialog;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.HojaBo;
import com.ar.vgmsistemas.bo.HojaDetalleBo;
import com.ar.vgmsistemas.bo.RendicionBo;
import com.ar.vgmsistemas.entity.Cheque;
import com.ar.vgmsistemas.entity.Compra;
import com.ar.vgmsistemas.entity.Hoja;
import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.task.LoadResumenCobranzaTask;
import com.ar.vgmsistemas.view.dialogs.BaseDialogFragment;
import com.ar.vgmsistemas.view.dialogs.ProgressDialogFragment;
import com.ar.vgmsistemas.view.reparto.hojas.resumenCobranza.ChequeListadoHojasAdapter;

import java.util.ArrayList;
import java.util.List;

public class imprimirResumenDialogFragment extends BaseDialogFragment {
    private List<Hoja> mHojas;
    private ChequeListadoHojasAdapter chequeAdapter;
    private HojaDetalleBo _hojaDetalleBo;
    private ProgressDialogFragment progressDialogFragment;
    private LoadResumenCobranzaTask taskCargandoResumenCobranza;
    private ViewHolder mViewHolder;

    //TOTALES DEL RESUMEN DE COBRANZA
    List<Cheque> mCheques;
    List<Compra> mEgresos = new ArrayList<>();
    double TotalEfectivo;
    double TotalCheques;
    double TotalDeposito;
    double TotalEgresos;
    double TotalRetenciones;
    double TotalPagos;
    double TotalCtaCte;
    double TotalNC;
    double TotalAnulado;

    RepositoryFactory _repoFactory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this._repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        this._hojaDetalleBo = new HojaDetalleBo(_repoFactory);

    }

    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        View itemView = View.inflate(getActivity(), R.layout.lyt_sucursal_cobranza_adapter, null);
        mViewHolder = new ViewHolder(itemView);
        try {
            taskCargandoResumenCobranza = new LoadResumenCobranzaTask(getActualizarConexionListener(), getActivity());
            taskCargandoResumenCobranza.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Builder builder = new Builder(getActivity());
        builder.setView(itemView);
        builder.setTitle("RESUMEN DE COBRANZA");
        return builder.show();
    }

    public void setHojas(List<Hoja> hojas) {
        mHojas = hojas;
    }

    public void updateHolder() {
        chequeAdapter = new ChequeListadoHojasAdapter(getActivity(), mCheques);
        ViewHolder holder = mViewHolder;
        holder.lvLstCheques.setAdapter(chequeAdapter);
        holder.tvTotal.setText(String.valueOf(TotalPagos));
        holder.tvCdo.setText(String.valueOf(TotalEfectivo));
        holder.tvDeposito.setText(String.valueOf(TotalDeposito));
        if (TotalCheques <= 0) {
            holder.tvDetalleCheques.setVisibility(View.INVISIBLE);
        }
        holder.tvCheques.setText(String.valueOf(TotalCheques));
        holder.tvRetenciones.setText(String.valueOf(TotalRetenciones));
        holder.tvEgresos.setText(String.valueOf(TotalEgresos));
        holder.tvCtaCte.setText(String.valueOf(TotalCtaCte));
        holder.tvCreditoGen.setText(String.valueOf(TotalNC));

    }

    private CargarResumenCobranzaListener getActualizarConexionListener() {
        CargarResumenCobranzaListener listener = new CargarResumenCobranzaListener() {

            @Override
            public void onError(String error) {
            }

            @Override
            public void onDone() {
                updateHolder();
            }

            @Override
            public void loadData() {
                List<Hoja> hojas;
                HojaBo hojaBo = new HojaBo(_repoFactory);
                RendicionBo rendicionBo = new RendicionBo(_repoFactory);
                //INICIALIZADOR DE VALORES
                mCheques = new ArrayList<>();
                mEgresos = new ArrayList<>();
                TotalEfectivo = 0;
                TotalDeposito = 0;
                TotalCheques = 0;
                TotalEgresos = 0;
                TotalRetenciones = 0;
                TotalPagos = 0;
                TotalCtaCte = 0;
                TotalNC = 0;
                TotalAnulado = 0;

                hojas = mHojas;
                for (Hoja hoja : hojas) {
                    List<HojaDetalle> hojaDetalles = new ArrayList<>();
                    try {
                        hojaDetalles = hojaBo.recoveryDetalles(hoja.getIdSucursal(), hoja.getIdHoja());
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    for (HojaDetalle hojaDe : hojaDetalles) {

                        HojaDetalle hojaDetalle = null;
                        try {
                            hojaDetalle = _hojaDetalleBo.recoveryByIDConEntrega(hojaDe.getId());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (hojaDetalle != null) {
                            TotalEfectivo += hojaDetalle.getEntrega().getPrEfectivoEntrega();
                            TotalCheques += hojaDetalle.getEntrega().getPrChequesEntrega();
                            TotalDeposito += hojaDetalle.getEntrega().getPrDepositoEntrega();
                            TotalRetenciones += hojaDetalle.getEntrega().getPrRetencionesEntrega();
                            mCheques.addAll(hojaDetalle.getEntrega().getCheques());
                        }
                    }
                    try {
                        hojaDetalles = hojaBo.recoveryChequesByHoja(hoja.getIdSucursal(), hoja.getIdHoja());
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                    }
                    for (HojaDetalle hojaDetalle : hojaDetalles) {
                        mCheques.addAll(hojaDetalle.getEntrega().getCheques());
                    }
                    try {
                        hojaDetalles = hojaBo.recoveryDetalles(hoja.getIdSucursal(), hoja.getIdHoja());
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    try {
                        mEgresos = rendicionBo.recoveryEgresosByHoja(hoja.getIdHoja(), hoja.getIdSucursal());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    for (Compra egreso : mEgresos) {
                        TotalEgresos += egreso.getPrSubtotal();
                    }
                    TotalPagos += HojaBo.getCdoDetalles(hojaDetalles);
                    TotalCtaCte += HojaBo.getCtaCteDetalles(hojaDetalles);
                    TotalNC += HojaBo.getCreditoDetalles(hojaDetalles);
                    TotalAnulado += HojaBo.getAnulado(hojaDetalles);
                }
            }

        };
        return listener;
    }

    static class ViewHolder {
        TextView tvTotal;
        TextView tvCdo;
        TextView tvDeposito;
        TextView tvCheques;
        TextView tvRetenciones;
        TextView tvEgresos;
        TextView tvCtaCte;
        TextView tvCreditoGen;
        TextView tvDetalleCheques;
        ListView lvLstCheques;

        public ViewHolder(View itemView) {
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvCdo = itemView.findViewById(R.id.tvCdo);
            tvDeposito = itemView.findViewById(R.id.tvDeposito);
            tvCheques = itemView.findViewById(R.id.tvCheques);
            tvRetenciones = itemView.findViewById(R.id.tvRetenciones);
            tvEgresos = itemView.findViewById(R.id.tvEgresos);
            tvCtaCte = itemView.findViewById(R.id.tvCtaCte);
            tvCreditoGen = itemView.findViewById(R.id.tvCreditoGen);
            tvDetalleCheques = itemView.findViewById(R.id.tvDetalleCheques);
            lvLstCheques = (ListView) itemView.findViewById(R.id.lstCheques);
        }
    }

}

