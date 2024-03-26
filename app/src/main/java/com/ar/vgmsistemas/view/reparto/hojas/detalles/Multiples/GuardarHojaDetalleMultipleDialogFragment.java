package com.ar.vgmsistemas.view.reparto.hojas.detalles.Multiples;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatDialog;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.HojaDetalleBo;
import com.ar.vgmsistemas.bo.MotivoCreditoBo;
import com.ar.vgmsistemas.entity.HojaDetalle;
import com.ar.vgmsistemas.entity.MotivoCredito;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.dialogs.BaseDialogFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GuardarHojaDetalleMultipleDialogFragment extends BaseDialogFragment {

    public static final String KEY_HOJA_DETALLE = "key_hoja_detalle";
    private List<HojaDetalle> mDetalle;
    private int posSelected = 0;
    private List<MotivoCredito> mMotivosCreditos;
    private DevolucionOkListener mListener;
    private MotivoCreditoBo creditoBo;

    public static GuardarHojaDetalleMultipleDialogFragment newInstance(List<HojaDetalle> detalle, DevolucionOkListener listener) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_HOJA_DETALLE, (Serializable) detalle);

        GuardarHojaDetalleMultipleDialogFragment devolucionDialogFragment = new GuardarHojaDetalleMultipleDialogFragment();
        devolucionDialogFragment.setListener(listener);
        devolucionDialogFragment.setArguments(bundle);

        return devolucionDialogFragment;
    }

    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        creditoBo = new MotivoCreditoBo(repoFactory);

        try {
            mMotivosCreditos = creditoBo.recoveryAll();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            mMotivosCreditos = new ArrayList<>();
            mMotivosCreditos.add(MotivoCreditoBo.getEmptyMotivo());
            e.printStackTrace();
        }
        List<String> strings = MotivoCreditoBo.parseToListArray(mMotivosCreditos);

        Builder builder = new Builder(getActivity());
        mDetalle = (List<HojaDetalle>) getArguments().getSerializable(KEY_HOJA_DETALLE);

        View view = View.inflate(getActivity(), R.layout.lyt_picker, null);
        final TextView tvTotal = (TextView) view.findViewById(R.id.tvTotal);
        final TextView tvPago = (TextView) view.findViewById(R.id.tvCdo);
        final TextView tvCtaCte = (TextView) view.findViewById(R.id.tvCtaCte);
        final TextView tvCredito = (TextView) view.findViewById(R.id.tvCredito);
        final Spinner spinner = (Spinner) view.findViewById(R.id.spnMotivoCredito);
        view.findViewById(R.id.llMotivoCredito).setVisibility(mDetalle.get(0).getPrNotaCredito() > 0 ? View.VISIBLE : View.GONE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, strings) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                if (position % 2 == 0) { // we're on an even row
                    view.setBackgroundColor(getResources().getColor(R.color.white));
                } else {
                    view.setBackgroundColor(getResources().getColor(R.color.background_gray));
                }
                return view;
            }
        };
        spinner.setAdapter(adapter);
        double totalFacturas = 0;
        double totalPagos = 0;
        double totalCtaCte = 0;
        double totalCredito = 0;
        for (HojaDetalle hojaDetalle : mDetalle) {
            totalFacturas += hojaDetalle.getPrTotal() * (hojaDetalle.getDocumento().getFuncionTipoDocumento() == 2 ? -1 : 1);
            totalPagos += hojaDetalle.getPrPagado() * (hojaDetalle.getDocumento().getFuncionTipoDocumento() == 2 ? -1 : 1);
            totalCtaCte += HojaDetalleBo.getEnCuentaCorriente(hojaDetalle) * (hojaDetalle.getDocumento().getFuncionTipoDocumento() == 2 ? -1 : 1);
            totalCredito += hojaDetalle.getPrNotaCredito() * (hojaDetalle.getDocumento().getFuncionTipoDocumento() == 2 ? -1 : 1);
        }
        tvTotal.setText(Formatter.formatMoney(totalFacturas));
        tvPago.setText(Formatter.formatMoney(totalPagos));
        tvCtaCte.setText(Formatter.formatMoney(totalCtaCte));
        tvCredito.setText(Formatter.formatMoney(totalCredito));
        builder.setView(view);

        builder.setTitle("Resumen del pago");
        builder.setPositiveButton("Guardar", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onOkSelected(mMotivosCreditos.get(spinner.getSelectedItemPosition()));

            }
        });
        builder.setNegativeButton("Cancelar", new OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                dismiss();

            }
        });

        return builder.show();
    }


    private void setListener(DevolucionOkListener listener) {
        mListener = listener;
    }

    public interface DevolucionOkListener {
        void onOkSelected(MotivoCredito motivoCredito);

    }

}
