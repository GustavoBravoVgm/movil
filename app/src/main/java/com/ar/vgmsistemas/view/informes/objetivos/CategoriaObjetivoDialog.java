package com.ar.vgmsistemas.view.informes.objetivos;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatDialog;

import com.ar.vgmsistemas.view.dialogs.BaseDialogFragment;

public class CategoriaObjetivoDialog extends BaseDialogFragment {
    public ObjetivoSelectListener listener;
    private int posSelected;

    public static CategoriaObjetivoDialog newInstance(ObjetivoSelectListener listener) {
        CategoriaObjetivoDialog dialogFragment = new CategoriaObjetivoDialog();
        dialogFragment.listener = listener;

        return dialogFragment;
    }

    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {

        Builder builder = new Builder(getActivity());

        builder.setPositiveButton("Aceptar", new OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                listener.onFilterOk(posSelected);

            }
        });
        builder.setNegativeButton("Cancel", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();// TODO Auto-generated method stub

            }
        });
        CharSequence[] items = {"Cobertura", "General", "Todos"};
        builder.setSingleChoiceItems(items, 2, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                posSelected = which;

            }
        });
        return builder.show();
    }

    public interface ObjetivoSelectListener {
        void onFilterOk(int categoriaSeleccionada);
    }

}
