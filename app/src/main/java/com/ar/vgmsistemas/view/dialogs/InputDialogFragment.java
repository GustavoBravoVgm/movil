package com.ar.vgmsistemas.view.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatDialog;

import com.ar.vgmsistemas.R;

public class InputDialogFragment extends BaseDialogFragment {
    protected int mType;
    protected Builder mBuilder;
    private DialogListener mListener;
    private AppCompatDialog mDialog;
    private static boolean haveMsj;
    private static Context mContext;
    private static String mTextInput;
    private EditText input;
    private int myInputType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt(KEY_TYPE_DIALOG);
        mTitle = getArguments().getString(KEY_TITLE_DIALOG);
        mMessage = getArguments().getString(KEY_MESSAGE_DIALOG);
        myInputType = getArguments().getInt(KEY_INPUT_TYPE);
        mBuilder = new Builder(getActivity());
        input = new EditText(mContext);

    }

    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {

        if (mType == TYPE_OK || mType == TYPE_OK_CANCEL) {
            mBuilder.setPositiveButton(R.string.aceptar, new OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    if (mListener != null && mListener instanceof OkListener) {

                        ((OkListener) mListener).onOkSelected(input.getText().toString().trim());
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
        input.setText(mTextInput);
        input.setRawInputType(myInputType);
        mBuilder.setView(input);
        mDialog = mBuilder.show();
        return mDialog;
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }

    }

    public static InputDialogFragment newInstance(Context context, String textInput, int type, String title, String msjDialog, int inputType, DialogListener listener) {
        haveMsj = true;
        mTextInput = textInput;
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE_DIALOG, type);
        bundle.putString(KEY_MESSAGE_DIALOG, msjDialog);
        bundle.putString(KEY_TITLE_DIALOG, title);
        bundle.putInt(KEY_INPUT_TYPE, inputType);
        mContext = context;
        InputDialogFragment dialogFragment = new InputDialogFragment();
        dialogFragment.mListener = listener;
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    public abstract interface DialogListener {
    }

    public interface OkListener extends DialogListener {
        void onOkSelected(String textInput);
    }

    public interface OkCancelListener extends OkListener {
        void onCancelSelected();
    }
}
