package com.ar.vgmsistemas.view.cobranza;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.Recibo;
import com.ar.vgmsistemas.entity.ReciboDetalle;
import com.ar.vgmsistemas.view.dialogs.InputDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;

public class DetalleReciboFragment extends Fragment {

    private FrmRecibo mFrmRecibo;
    private ListView mListView;
    private ReciboDetalle _lineaSeleccionada;
    private ReciboDetalleAdapter _adapter;
    private final int DIALOG_OPCIONES_LINEA = 0;
    private final int DIALOG_ELIMINAR = 1;
    private Recibo _recibo;
    boolean snMovilReciboDto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFrmRecibo = (FrmRecibo) getActivity();
        Bundle b = getArguments();
        this._recibo = (Recibo) b.getSerializable(FrmRecibo.EXTRA_RECIBO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyt_one_listview, null);
        mListView = (ListView) view.findViewById(R.id.listview);
        initComponents();
        loadData();
        return view;
    }

    private void loadData() {
        snMovilReciboDto = PreferenciaBo.getInstance().getPreferencia().getSnMovilReciboDto().equals("S");
        _adapter = new ReciboDetalleAdapter(mFrmRecibo, R.layout.lyt_cuenta_corriente_item, this._recibo.getDetalles(), snMovilReciboDto);
        mListView.setAdapter(_adapter);
    }

    private void initComponents() {
        //lstCuentasCorrientes
        mListView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lstCuentasCorrientesOnItemClick(parent, view, position, id);
            }
        });
    }

    public void lstCuentasCorrientesOnItemClick(AdapterView<?> parent, View view, int position, long id) {
        _lineaSeleccionada = _adapter.getItem(position);
        showDialog(DIALOG_OPCIONES_LINEA);
    }

    protected void showDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DIALOG_OPCIONES_LINEA: {
                if (!snMovilReciboDto || _lineaSeleccionada.getCuentaCorriente().getSigno() < 0) {
                    dialog = eliminarLinea();
                    dialog.show();
                } else {
                    InputDialogFragment inputDialogFragment = InputDialogFragment.newInstance(getActivity(), _lineaSeleccionada.getTaDtoRecibo() + "", SimpleDialogFragment.TYPE_OK_CANCEL, "", getString(R.string.lblDescuento), InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL, new InputDialogFragment.OkCancelListener() {
                        @Override
                        public void onOkSelected(String textInput) {
                            try {
                                _lineaSeleccionada.setTaDtoRecibo(Float.parseFloat(textInput));
                                actualizarLineas();
                            } catch (NumberFormatException nfe) {
                                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.lblNumeroIncorrecto), Toast.LENGTH_LONG).show();//cambio karen
                                _lineaSeleccionada.setTaDtoRecibo(0);
                            }
                        }

                        @Override
                        public void onCancelSelected() {
                        }
                    });
                    inputDialogFragment.show(getParentFragmentManager(), "");
                }

            }
            break;
            default:
                break;
        }
    }

    private void actualizarLineas() {
        this._adapter.notifyDataSetChanged();
        mFrmRecibo.actualizarTotales();
    }

    private Dialog eliminarLinea() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mFrmRecibo);
        builder.setMessage(R.string.msjQuitarDocumentoSeleccionado)
                .setCancelable(false)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        _recibo.getDetalles().remove(_lineaSeleccionada);
                        actualizarLineas();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        return alert;
    }

}
