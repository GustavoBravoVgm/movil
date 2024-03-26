package com.ar.vgmsistemas.view.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatDialog;

import com.ar.vgmsistemas.R;

public class SimpleDialogFragment extends BaseDialogFragment {

    public static final String KEY_TYPE_DIALOG = "key_type_dialog";
    public static final String KEY_MESSAGE_DIALOG = "key_message_dialog";
    public static final String KEY_TITLE_DIALOG = "key_title_dialog";

    public static final int TYPE_OK = 1;
    public static final int TYPE_OK_CANCEL = 2;
    public static final int TYPE_CANCEL = 3;

    private int mType;
    protected Builder mBuilder;
    private DialogListener mListener;
    private AppCompatDialog mDialog;
    private static boolean haveMsj;

    public static SimpleDialogFragment newInstance(int type, String title) {
        haveMsj = false;

        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE_DIALOG, type);
        bundle.putString(KEY_TITLE_DIALOG, title);

        SimpleDialogFragment baseDialogFragment = new SimpleDialogFragment();
        baseDialogFragment.setArguments(bundle);
        return baseDialogFragment;
    }

    public static SimpleDialogFragment newInstance(int type, String title, String message) {
        haveMsj = true;

        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE_DIALOG, type);
        bundle.putString(KEY_TITLE_DIALOG, title);
        bundle.putString(KEY_MESSAGE_DIALOG, message);

        SimpleDialogFragment baseDialogFragment = new SimpleDialogFragment();
        baseDialogFragment.setArguments(bundle);
        return baseDialogFragment;
    }

    public static SimpleDialogFragment newInstance(int type, String title, OkCancelListener listener) {
        haveMsj = false;


        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE_DIALOG, type);
        bundle.putString(KEY_TITLE_DIALOG, title);

        SimpleDialogFragment dialogFragment = new SimpleDialogFragment();
        dialogFragment.mListener = listener;
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    public static SimpleDialogFragment newInstance(int type, String msj, String title, DialogListener listener) {
        return getDialog(type, listener, msj, title);
    }

    public static SimpleDialogFragment newInstance(int type, Context context, int msjId, int title, DialogListener listener) {
        String msjString = context.getResources().getString(msjId);
        String titleString = context.getResources().getString(title);
        return getDialog(type, listener, msjString, titleString);
    }

    private static SimpleDialogFragment getDialog(int type, DialogListener listener, String msj, String title) {
        haveMsj = true;


        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE_DIALOG, type);
        bundle.putString(KEY_MESSAGE_DIALOG, msj);
        bundle.putString(KEY_TITLE_DIALOG, title);

        SimpleDialogFragment baseDialogFragment = new SimpleDialogFragment();
        baseDialogFragment.mListener = listener;
        baseDialogFragment.setArguments(bundle);
        return baseDialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt(KEY_TYPE_DIALOG);
        mTitle = getArguments().getString(KEY_TITLE_DIALOG);
        mMessage = getArguments().getString(KEY_MESSAGE_DIALOG);
        mBuilder = new Builder(getActivity());
    }

    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {

        if (mType == TYPE_OK || mType == TYPE_OK_CANCEL) {
            mBuilder.setPositiveButton(R.string.aceptar, new OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    if (mListener != null && mListener instanceof OkListener) {
                        ((OkListener) mListener).onOkSelected();
                    }
                    dismiss();
                }
            });

        }
        if (mType == TYPE_CANCEL || mType == TYPE_OK_CANCEL) {
            mBuilder.setNegativeButton(R.string.cancelar, new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (mListener != null && mListener instanceof OkCancelListener) {
                        ((OkCancelListener) mListener).onCancelSelected();
                    } else if (mListener == null) {
                        dismiss();
                    }

                }
            });
        }
        if (haveMsj) mBuilder.setMessage(mMessage);
        mBuilder.setTitle(mTitle);
        mDialog = mBuilder.show();
        return mDialog;
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }

    }

    public abstract interface DialogListener {
    }


    public interface OkListener extends DialogListener {
        void onOkSelected();
    }

    public interface OkCancelListener extends OkListener {
        void onCancelSelected();
    }


}
