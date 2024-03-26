package com.ar.vgmsistemas.view.reparto.hojas.resumenCobranza;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatDialog;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.HojaBo;
import com.ar.vgmsistemas.entity.Cheque;
import com.ar.vgmsistemas.entity.Hoja;
import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.view.dialogs.BaseDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class VerTotalesDialogFragment extends BaseDialogFragment {
    private ChequeListadoHojasAdapter chequeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        View itemView = View.inflate(getActivity(), R.layout.lyt_sucursal_cobranza_adapter, null);
        ViewHolder viewHolder = new ViewHolder(itemView);
        loadData(viewHolder);

        Builder builder = new Builder(getActivity());
        builder.setView(itemView);
        builder.setTitle("RESUMEN DE COBRANZA");
        return builder.show();
    }

    public void loadData(ViewHolder holder) {
        List<Hoja> hojas = new ArrayList<>();
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        HojaBo hojaBo = new HojaBo(repoFactory);
        List<Cheque> cheques = new ArrayList<>();
        double TotalEfectivo = 0d;
        double TotalCheques = 0d;
        double TotalDeposito = 0d;
        double TotalRetenciones = 0d;
        double TotalPagos = 0d;
        double TotalCtaCte = 0d;
        double TotalNC = 0d;
        double TotalAnulado = 0d;
        try {
            hojas = hojaBo.recoveryAllEntities();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Hoja hoja : hojas) {
            if (hoja.getIsRendida().equals(Hoja.MOVIL)
                //hoja.getIsRendida().equals(Hoja.APROBADA) ||
                //hoja.getIsRendida().equals(Hoja.PRERENDIDO)
            ) {
                List<HojaDetalle> hojaDetalles = new ArrayList<>();
                try {
                    hojaDetalles = hojaBo.recoveryDetallesConEntrega(hoja.getIdSucursal(), hoja.getIdHoja());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                for (HojaDetalle hojaDetalle : hojaDetalles) {
                    TotalEfectivo += hojaDetalle.getEntrega().getPrEfectivoEntrega();
                    TotalCheques += hojaDetalle.getEntrega().getPrChequesEntrega();
                    TotalDeposito += hojaDetalle.getEntrega().getPrDepositoEntrega();
                    TotalRetenciones += hojaDetalle.getEntrega().getPrRetencionesEntrega();
                    cheques.addAll(hojaDetalle.getEntrega().getCheques());
                }
                try {
                    hojaDetalles = hojaBo.recoveryDetalles(hoja.getIdSucursal(), hoja.getIdHoja());
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                TotalPagos += HojaBo.getCdoDetalles(hojaDetalles);
                TotalCtaCte += HojaBo.getCtaCteDetalles(hojaDetalles);
                TotalNC += HojaBo.getCreditoDetalles(hojaDetalles);
                TotalAnulado += HojaBo.getAnulado(hojaDetalles);
                //cheques.addAll(hojaBo.getCheques(hojaDetalles));
            }
        }
        //AHORA CARGO LA LISTA DE CHEQUES
        chequeAdapter = new ChequeListadoHojasAdapter(getActivity(), cheques);
        holder.lvLstCheques.setAdapter(chequeAdapter);
        //holder.tvTotal.setText(String.valueOf(TotalEfectivo+TotalCheques+TotalDeposito+TotalRetenciones));
        holder.tvTotal.setText(String.valueOf(TotalPagos));
        holder.tvCdo.setText(String.valueOf(TotalEfectivo));
        holder.tvDeposito.setText(String.valueOf(TotalDeposito));
        holder.tvCheques.setText(String.valueOf(TotalCheques));
        holder.tvRetenciones.setText(String.valueOf(TotalRetenciones));
        holder.tvEgresos.setText(String.valueOf(0f));
        holder.tvCtaCte.setText(String.valueOf(TotalCtaCte));
        holder.tvCreditoGen.setText(String.valueOf(TotalNC));
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
            lvLstCheques = (ListView) itemView.findViewById(R.id.lstCheques);
        }
    }

}

