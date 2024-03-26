package com.ar.vgmsistemas.view.cobranza.cuentacorriente;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ChequeBo;
import com.ar.vgmsistemas.entity.Cheque;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.cobranza.cheque.ChequeAdapter;
import com.ar.vgmsistemas.view.dialogs.BaseDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment.DialogListener;

import java.util.List;

public class DlgMenuVerCheques extends BaseDialogFragment {
    private static String EXTRA_CLIENTE = "EXTRA_CLIENTE";

    private ListView listView;
    private ChequeAdapter chequeAdapter;
    private List<Cheque> cheques;
    private Cliente cliente;
    private TextView txtTotal;

    public static DlgMenuVerCheques newInstance(Cliente cliente) {
        //mListener = listener;
        DlgMenuVerCheques dialog = new DlgMenuVerCheques();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_CLIENTE, cliente);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.lyt_cheques, null);
        cliente = (Cliente) getArguments().getSerializable(EXTRA_CLIENTE);
        alertDialogBuilder.setView(view);
        initComponents(view);
        loadData();
        return alertDialogBuilder.show();
    }

    private void loadData() {
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        ChequeBo chequeBo = new ChequeBo(repoFactory);
        try {
            cheques = chequeBo.getChequesByCliente(cliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
        chequeAdapter = new ChequeAdapter(getActivity(), cheques);
        listView.setAdapter(chequeAdapter);
        double total = 0d;
        if (cheques != null) {
            for (Cheque cheque : cheques) {
                total += cheque.getImporte();
            }
        }
        txtTotal.setText(Formatter.formatMoney(total));
    }

    private void initComponents(View view) {
        this.listView = (ListView) view.findViewById(R.id.lstCheques);
        this.txtTotal = (TextView) view.findViewById(R.id.lblTotalValue);

    }


    public interface OkListener extends DialogListener {
        void onOkSelected();
    }
}
