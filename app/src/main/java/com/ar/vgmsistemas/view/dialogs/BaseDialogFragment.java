package com.ar.vgmsistemas.view.dialogs;

import androidx.fragment.app.DialogFragment;

public abstract class BaseDialogFragment extends DialogFragment {
    protected static final String KEY_MESSAGE = "key_message";
    protected static final String KEY_TITLE = "key_title";
    protected static final String KEY_OPCIONES = "key_opciones";
    protected static final String KEY_POS_SELECTED = "key_pos_selected";
//	protected static final String KEY_LISTENER = "key_listener";

    public static final String KEY_TYPE_DIALOG = "key_type_dialog";
    public static final String KEY_MESSAGE_DIALOG = "key_message_dialog";
    public static final String KEY_TITLE_DIALOG = "key_title_dialog";
    public static final String KEY_INPUT_TYPE = "key_input_type";

    public static final int TYPE_OK = 1;
    public static final int TYPE_OK_CANCEL = 2;
    public static final int TYPE_CANCEL = 3;

    protected String mMessage;
    protected String mTitle;
    protected String[] mOpciones;
    protected int mPosSelected;

}
