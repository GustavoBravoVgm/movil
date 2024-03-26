package com.ar.vgmsistemas.view.dialogs;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatDialog;

public class SingleChoiceDialogFragment extends BaseDialogFragment {


    protected SingleChoiceListener mListener;

    public static SingleChoiceDialogFragment newInstance(String[] opciones, int posSelected, String title, SingleChoiceListener listener) {

        Bundle bundle = new Bundle();
        bundle.putStringArray(KEY_OPCIONES, opciones);
        bundle.putInt(KEY_POS_SELECTED, posSelected);
        bundle.putString(KEY_TITLE, title);
        SingleChoiceDialogFragment choiceDialogFragment = new SingleChoiceDialogFragment();
        choiceDialogFragment.mListener = listener;
        choiceDialogFragment.setArguments(bundle);
        return choiceDialogFragment;
    }

    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        mOpciones = getArguments().getStringArray(KEY_OPCIONES);
        mTitle = getArguments().getString(KEY_TITLE);
        mPosSelected = getArguments().getInt(KEY_POS_SELECTED);
        Builder builder = new Builder(getActivity());
        builder.setSingleChoiceItems(mOpciones, mPosSelected, new OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int pos) {
                mPosSelected = pos;

            }
        });
        builder.setTitle(mTitle);
        builder.setPositiveButton("Aceptar", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mListener.onAcceptItem(mPosSelected);
                dismiss();
            }
        });
        return builder.show();
    }

    public interface SingleChoiceListener {

        void onAcceptItem(int pos);
    }

}
