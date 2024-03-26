package com.ar.vgmsistemas.view.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog.Builder;

import com.ar.vgmsistemas.R;

import java.util.List;

public class SpinnerDialogFragment extends BaseDialogFragment {
    public static final String KEY_MSJ_SPINNER = "msj_spinner";
    private SpinnerDialogListener mListener;
    private List<String> mElements;
    private int posSelected;

    public static SpinnerDialogFragment newInstance(String mensaje, String msjSpinner, SpinnerDialogListener listener, List<String> elements) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_MESSAGE, mensaje);
        bundle.putString(KEY_MSJ_SPINNER, msjSpinner);
        SpinnerDialogFragment dialogFragment = new SpinnerDialogFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.mListener = listener;
        dialogFragment.mElements = elements;
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Builder builder = new Builder(getActivity());
        View view = View.inflate(getActivity(), R.layout.lyt_spinner_dialog, null);
        Spinner spinner = view.findViewById(R.id.spnDialog);//(Spinner)
        TextView tvMsjSpinner = view.findViewById(R.id.tvMessageSpinner);//(TextView)

        /*No hago Visible lo de cod autorizacion por comercio*/
        TextView mTextCodAutCCReparto = view.findViewById(R.id.tvMsgCodigoAutorizaCCReparto);//(TextView)
        EditText mEditTextCodAutCCReparto = view.findViewById(R.id.txtCodigoAutorizaCCReparto);//(EditText)
        mTextCodAutCCReparto.setVisibility(View.INVISIBLE);
        mEditTextCodAutCCReparto.setVisibility(View.INVISIBLE);

        tvMsjSpinner.setText(getArguments().getString(KEY_MSJ_SPINNER));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mElements);

        spinner.setAdapter(adapter);

        builder.setMessage(getArguments().getString(KEY_MESSAGE));
        builder.setView(view);
        builder.setPositiveButton(R.string.aceptar, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                mListener.onAccepted(posSelected);

            }
        });
        return builder.show();
    }

    public interface SpinnerDialogListener {
        void onAccepted(int posSelected);
    }
}
