package com.ar.vgmsistemas.view.dialogs;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatDialog;

import com.ar.vgmsistemas.R;

public class OptionsDialogFragment extends BaseDialogFragment {
    public static final String TAG = "optionsDialogFragment";
    private static final String TITLE_SINGLE_CHOICE = "Seleccionar accion";
    protected MultipleChoiceListener mListener;

    public static OptionsDialogFragment newInstance(String[] items, MultipleChoiceListener listener) {
        OptionsDialogFragment choiceDialogFragment = new OptionsDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putStringArray(KEY_OPCIONES, items);
        choiceDialogFragment.setArguments(bundle);
        choiceDialogFragment.mListener = listener;
        return choiceDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mOpciones = getArguments().getStringArray(KEY_OPCIONES);

    }

    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        builder.setItems(mOpciones, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    mListener.onItemSelected(which);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        builder.setNegativeButton(R.string.cancelar, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();

            }
        });
        builder.setTitle(TITLE_SINGLE_CHOICE);

        return builder.show();
    }

    public interface MultipleChoiceListener {
        void onItemSelected(int pos) throws Exception;
    }

}
