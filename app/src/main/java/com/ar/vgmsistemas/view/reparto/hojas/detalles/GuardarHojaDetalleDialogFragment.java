package com.ar.vgmsistemas.view.reparto.hojas.detalles;

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

import java.util.ArrayList;
import java.util.List;

public class GuardarHojaDetalleDialogFragment extends BaseDialogFragment {

    public static final String KEY_HOJA_DETALLE = "key_hoja_detalle";
    private HojaDetalle mDetalle;
    private List<MotivoCredito> mMotivosCreditos;
    private DevolucionOkListener mListener;
    private MotivoCreditoBo creditoBo;

    public static GuardarHojaDetalleDialogFragment newInstance(HojaDetalle detalle, DevolucionOkListener listener) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_HOJA_DETALLE, detalle);

        GuardarHojaDetalleDialogFragment devolucionDialogFragment = new GuardarHojaDetalleDialogFragment();
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
        mDetalle = (HojaDetalle) getArguments().getSerializable(KEY_HOJA_DETALLE);

        View view = View.inflate(getActivity(), R.layout.lyt_picker, null);
        final TextView tvTotal = view.findViewById(R.id.tvTotal);//(TextView)
        final TextView tvPago = view.findViewById(R.id.tvCdo);//(TextView)
        final TextView tvCtaCte = view.findViewById(R.id.tvCtaCte);//(TextView)
        final TextView tvCredito = view.findViewById(R.id.tvCredito);//(TextView)
        final Spinner spinner = view.findViewById(R.id.spnMotivoCredito);//(Spinner)
        view.findViewById(R.id.llMotivoCredito).setVisibility(mDetalle.getPrNotaCredito() > 0 ? View.VISIBLE : View.GONE);

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

        tvTotal.setText(Formatter.formatMoney(mDetalle.getPrTotal()));
        tvPago.setText(Formatter.formatMoney(mDetalle.getPrPagado()));
        tvCtaCte.setText(Formatter.formatMoney(HojaDetalleBo.getEnCuentaCorriente(mDetalle)));
        tvCredito.setText(Formatter.formatMoney(mDetalle.getPrNotaCredito()));
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
