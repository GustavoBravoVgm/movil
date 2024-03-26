package com.ar.vgmsistemas.view.rendicion.egresos;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.ActionMode.Callback;
import androidx.fragment.app.Fragment;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.RendicionBo;
import com.ar.vgmsistemas.entity.Compra;
import com.ar.vgmsistemas.entity.ComprasImpuestos;

public class DetalleEgresoFragment extends Fragment {

    // Modos
    private int modo;
    public static final int MODO_ALTA = 0;
    public static final int MODO_EDITAR = 1;
    public static final int MODO_CONSULTA = 2;

    // Dialogs
    private final int DIALOG_OPCIONES_LINEA = 0;
    private final int DIALOG_ELIMINAR = 1;
    private static final String TAG = DetalleEgresoFragment.class.getCanonicalName();

    private ListView lstLineaImpuesto;
    private LineaImpuestoAdapter adapter;
    private FrmEgreso mFrmEgreso;
    private ComprasImpuestos lineaSeleccionada;
    private Compra compra;
    private ActionMode mActionMode;
    private boolean argumentosLeidos = false;


    private ImageButton mButtonAgregar;
    private MenuItem itemSelectAll;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFrmEgreso = (FrmEgreso) getActivity();
        loadData();
    }

    private void loadData() {
        Bundle b = getArguments();
        if (b != null && !isArgumentosLeidos()) {
            compra = (Compra) b.getSerializable(Compra.EXTRA_COMPRA);
            modo = b.getInt(Compra.EXTRA_MODO, MODO_ALTA);
            setArgumentosLeidos(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //mFrmEgreso.actualizarTotales(comprasImpuestos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.lyt_detalle_impuestos, null);
        lstLineaImpuesto = (ListView) view.findViewById(R.id.lstLineaImpuesto);
        lstLineaImpuesto.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lstLineaImpuestoOnItemClick(parent, view, position, id);
            }
        });
        mButtonAgregar = (ImageButton) view.findViewById(R.id.btnAgregar);
        int visibility;
        try {
            visibility = (modo == MODO_CONSULTA) ? View.GONE : View.VISIBLE;
            mButtonAgregar.setVisibility(visibility);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mButtonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DlgAgregarImpuesto.newInstance(new DlgAgregarImpuesto.OkListener() {
                    @Override
                    public void onOkSelected(ComprasImpuestos ci) {
                        compra.getComprasImpuestos().add(ci);
                        RendicionBo.sumarTotalesCompra(compra);
                        adapter.notifyDataSetChanged();
                        mFrmEgreso.actualizarTotales();
                    }
                }).show(getFragmentManager(), "");
            }
        });
        adapter = new LineaImpuestoAdapter(getContext(), R.layout.lyt_venta_detalle_item, compra.getComprasImpuestos(), compra);

        lstLineaImpuesto.setAdapter(adapter);

        registerForContextMenu(lstLineaImpuesto);
        return view;
    }

    public void lstLineaImpuestoOnItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mActionMode == null) {
            lineaSeleccionada = adapter.getItem(position);
            if (modo != MODO_CONSULTA) {
                showDialog(DIALOG_OPCIONES_LINEA);
            }
        } else {
        }
    }


    private Dialog getDialogOpcionesLinea() {
        String eliminar = this.getString(R.string.eliminar);

        final CharSequence[] items = {eliminar};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(this.getString(R.string.tituloSeleccionarAccion));
        builder.setNegativeButton(R.string.btnCancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                menuOpcionesLineaOnClick(dialog, which);
            }
        });
        AlertDialog alert = builder.create();
        return alert;
    }

    private void menuOpcionesLineaOnClick(DialogInterface dialog, int index) {
        switch (index) {
            case 0:
                showDialog(DIALOG_ELIMINAR);
                break;
        }
    }

    public void showDialog(int typeDialog) {
        Dialog dialog = null;
        switch (typeDialog) {
            case DIALOG_ELIMINAR:
                dialog = eliminarLinea();
                dialog.show();
                break;
            case DIALOG_OPCIONES_LINEA:
                dialog = getDialogOpcionesLinea();
                dialog.show();
                break;

        }
    }

    private Dialog eliminarLinea() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setMessage("Desea eliminar la linea seleccionada?").setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        compra.setPrSubtotal(compra.getPrSubtotal() - lineaSeleccionada.getPrImpGravado());
                        adapter.remove(lineaSeleccionada);
                        RendicionBo.sumarTotalesCompra(compra);
                        mFrmEgreso.actualizarTotales();
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        android.app.AlertDialog alert = builder.create();
        return alert;
    }

    private class ActionModeCallback implements Callback {
        private Context mContext;

        public ActionModeCallback(Context context) {
            mContext = context;
        }

        @Override
        public boolean onActionItemClicked(ActionMode action, MenuItem arg1) {

            SparseBooleanArray selected = adapter.getSelectedItems();
            StringBuilder message = new StringBuilder();
            for (int i = 0; i < selected.size(); i++) {
                if (selected.valueAt(i)) {
                    ComprasImpuestos selectedItem = adapter.getItem(selected.keyAt(i));
                    message.append(selectedItem + "\n");
                }
            }
            Toast.makeText(mContext, message.toString(), Toast.LENGTH_LONG).show();

            // close action mode
            action.finish();
            return false;
        }

        @Override
        public boolean onCreateActionMode(ActionMode action, Menu menu) {
            action.getMenuInflater().inflate(R.menu.menu_select_items_detalle, menu);
            final MenuItem itemDelete = menu.findItem(R.id.mni_delete);
            itemDelete.setOnMenuItemClickListener(new OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return true;
                }
            });
            itemSelectAll = menu.findItem(R.id.mni_select_all);
            itemSelectAll.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    adapter.selectAllViews();
                    Drawable icon = (adapter.isSelectAll()) ? getResources().getDrawable(R.drawable.ic_select_all_selected) : getResources().getDrawable(R.drawable.ic_select_all_unselected);
                    itemSelectAll.setIcon(icon);
                    mActionMode.setTitle(String.valueOf(adapter.getSelectedCount()) + " seleccionadas");
                    return true;
                }
            });
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode action) {
            adapter.removeSelection();
            int position = 1;//0 cabecera, 1 detalle,
            mFrmEgreso.setEnableTabWidget(true, position);
            mButtonAgregar.setVisibility(View.VISIBLE);
            mActionMode = null;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
            return false;
        }

    }

    public boolean isArgumentosLeidos() {
        return argumentosLeidos;
    }

    public void setArgumentosLeidos(boolean argumentosLeidos) {
        this.argumentosLeidos = argumentosLeidos;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public void limpiarLista() {
        adapter.clear();
    }
}
