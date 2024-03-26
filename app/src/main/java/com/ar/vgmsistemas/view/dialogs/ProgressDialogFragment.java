package com.ar.vgmsistemas.view.dialogs;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.NonNull;

public class ProgressDialogFragment extends BaseDialogFragment {

    private static boolean isCancelable;
    protected CancelProgressListener mListener;

    public static ProgressDialogFragment newInstance(String msjProgress) {
        isCancelable = false;

        Bundle bundle = new Bundle();
        bundle.putString(KEY_MESSAGE, msjProgress);

        ProgressDialogFragment dialogFragment = new ProgressDialogFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.setCancelable(false);
        return dialogFragment;
    }

    public static ProgressDialogFragment newInstance(String sTitle, String msjProgress) {
        isCancelable = false;

        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, sTitle);
        bundle.putString(KEY_MESSAGE, msjProgress);

        ProgressDialogFragment dialogFragment = new ProgressDialogFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.setCancelable(false);
        return dialogFragment;
    }

    /**
     * ProgressDialog que puede ser cancelado, el listener sirve para devolver el callback de cuando se selecciona cancelar
     */
    public static ProgressDialogFragment newInstance(String msjProgress, CancelProgressListener listener) {

        isCancelable = true;

        Bundle bundle = new Bundle();
        bundle.putString(KEY_MESSAGE, msjProgress);

        ProgressDialogFragment dialogFragment = new ProgressDialogFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.mListener = listener;
        dialogFragment.setCancelable(false);
        return dialogFragment;
    }

    @Override
    public void dismiss() {
        // TODO Auto-generated method stub
        super.dismissAllowingStateLoss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mMessage = getArguments().getString(KEY_MESSAGE);
        mTitle = getArguments().getString(KEY_TITLE);
        ProgressDialog dialog = new ProgressDialog(getActivity());

        dialog.setMessage(mMessage);
        if (mTitle != null) dialog.setTitle(mTitle);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);

        if (isCancelable) {
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar ", (arg0, arg1) -> {
                dismiss();
                mListener.onCancelProgress();
            });

        }
        dialog.setOnKeyListener((dialog1, keyCode, event) -> {
            // when is true pretend we've processed it
            // if false pass on to be processed as normal
            return keyCode == KeyEvent.KEYCODE_BACK;
        });
        return dialog;
    }

    public interface CancelProgressListener {
        void onCancelProgress();
    }
}
