package com.ar.vgmsistemas.view.venta.combo;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog.Builder;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.DialogFragment;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.PromocionBo;
import com.ar.vgmsistemas.bo.PromocionRequisitoBo;
import com.ar.vgmsistemas.bo.VentaDetalleBo;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.PromocionDetalle;
import com.ar.vgmsistemas.entity.PromocionRequisito;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.utils.KeyboardUtils;
import com.ar.vgmsistemas.view.ListItemAdapter;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class CargaComboDialogFragment extends DialogFragment {
    public static final String ARTICULO = "articulo";
    public static final String CANTIDAD = "cantidad";
    public static final String LISTENER = "listener";
    public static final String VENTAS_DETALLE = "ventaDetalle";
    private static final String MODE = "mode";

    public static final int EDICION = 1;
    public static final int ALTA = 0;
    public static ComboListener mListener;

    private PromocionRequisito mRequisito;
    private int mMode;
    private int totalRequerido;
    private boolean comboValid = false;
    private EditText etCantidad;
    private TextView tvTotalRequerido;
    private TextView tvTotal;
    private Button mButtonAceptar;
    private Button mButtonCancelar;
    private ComboDetalleAdapter mAdapter;
    private VentaDetalle mVentaDetalle;
    private AppCompatDialog mDialog;

    public static CargaComboDialogFragment newInstance(String cant, Articulo articulo, ComboListener listener) {
        CargaComboDialogFragment cargaComboDialogFragment = new CargaComboDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARTICULO, articulo);
        bundle.putInt(CANTIDAD, Integer.parseInt(cant));
        bundle.putInt(MODE, ALTA);

        cargaComboDialogFragment.setArguments(bundle);
        cargaComboDialogFragment.mListener = listener;
        return cargaComboDialogFragment;
    }

    public static CargaComboDialogFragment newInstance(VentaDetalle detalle, ComboListener listener) {
        CargaComboDialogFragment cargaComboDialogFragment = new CargaComboDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(VENTAS_DETALLE, detalle);

        bundle.putInt(MODE, EDICION);

        cargaComboDialogFragment.setArguments(bundle);
        cargaComboDialogFragment.mListener = listener;
        return cargaComboDialogFragment;
    }

    protected long getRequisito() {
        return mRequisito.getCantArticulos() * Integer.valueOf(etCantidad.getText().toString());
    }

    @Override
    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        int cant = 0;
        mMode = getArguments().getInt(MODE);
        if (mMode == EDICION)
            mVentaDetalle = (VentaDetalle) getArguments().getSerializable(VENTAS_DETALLE);

        View view = View.inflate(getActivity(), R.layout.lyt_combo_especial, null);
        builder.setView(view);

        Articulo articulo = (mMode == ALTA) ? articulo = (Articulo) getArguments().getSerializable(ARTICULO)
                : mVentaDetalle.getArticulo();
        List<PromocionDetalle> promocionDetalle = null;
        //BD
        RepositoryFactory _repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        PromocionBo promocionBo = new PromocionBo(_repoFactory);
        PromocionRequisitoBo requisitoBo = new PromocionRequisitoBo(_repoFactory);
        try {

            promocionDetalle = promocionBo.recoveryItemsByItem(articulo);
            mRequisito = requisitoBo.getRequisito(articulo.getId());
            if (mMode == EDICION) {

                cant = (int) mVentaDetalle.getCantidad();

                PromocionBo.getPromocionDetalles(mVentaDetalle.getDetalleCombo(), promocionDetalle);
            } else {
                cant = getArguments().getInt(CANTIDAD);

            }

            totalRequerido = PromocionBo.getCantidadRequeridaInt(mRequisito.getCantArticulos(), cant);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        etCantidad = (EditText) view.findViewById(R.id.etCantidadCombo);
        etCantidad.setText(String.valueOf(cant));
        tvTotalRequerido = (TextView) view.findViewById(R.id.tvRequerido);
        tvTotal = (TextView) view.findViewById(R.id.tvTotal);
        tvTotal.setText("0");
        etCantidad.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    KeyboardUtils.hideKeyboard(v, getActivity());
                }

            }
        });
        etCantidad.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String totalReq = PromocionBo.getCantidadRequerida(mRequisito.getCantArticulos(), s.toString());
                totalRequerido = Integer.valueOf(totalReq);
                tvTotalRequerido.setText(totalReq);

                checkButtonAceptar();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
        tvTotalRequerido.setText(PromocionBo.getCantidadRequerida(mRequisito.getCantArticulos(), cant));
        mButtonAceptar = (Button) view.findViewById(R.id.btnAceptar);
        mButtonAceptar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                SimpleDialogFragment dialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK,
                        "Desea guardar los cambios?", "Confirmación", new SimpleDialogFragment.OkListener() {

                            @Override
                            public void onOkSelected() {

                                mListener.onComboAccepted(mAdapter.getItemsCombo(),
                                        Integer.valueOf(etCantidad.getText().toString()));

                            }
                        });
                dialogFragment.show(getChildFragmentManager(), "");

            }
        });
        mButtonCancelar = (Button) view.findViewById(R.id.btnCancelar);
        mButtonCancelar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                SimpleDialogFragment dialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK,
                        "Esta seguro que desear salir?", "Atencion", new SimpleDialogFragment.OkListener() {

                            @Override
                            public void onOkSelected() {

                                mListener.onDismissDialog();

                                mDialog.dismiss();

                            }
                        });
                dialogFragment.show(getChildFragmentManager(), "salir");

            }
        });
        TextView tvCabecera = (TextView) view.findViewById(R.id.tvCabeceraPromo);
        tvCabecera.setText(articulo.getDescripcion());

        TextView tvUnidades = (TextView) view.findViewById(R.id.tvUnidades);
        tvUnidades.setText(String.valueOf(mRequisito.getCantUnidades()));
        TextView tvCantidad = (TextView) view.findViewById(R.id.tvCantidades);
        tvCantidad.setText(String.valueOf(mRequisito.getCantArticulos()));
        TextView tvBultos = (TextView) view.findViewById(R.id.tvBultos);
        tvBultos.setText(String.valueOf(mRequisito.getCantBultos()));

        ListView lvDetalle = (ListView) view.findViewById(R.id.lvDetalleCombo);

        mAdapter = new ComboDetalleAdapter(getActivity(), R.layout.lyt_linea_combo, promocionDetalle);
        lvDetalle.setAdapter(mAdapter);
        lvDetalle.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                KeyboardUtils.hideKeyboard(arg0, getActivity());
                return false;
            }
        });
        checkButtonAceptar();
        tvTotal.setText(String.valueOf(mAdapter.getCantidades()));
        mDialog = builder.show();
        mDialog.setCancelable(false);
        return mDialog;
    }

    protected void checkButtonAceptar() {
        mButtonAceptar.setEnabled(mAdapter.getCantidades() == totalRequerido && totalRequerido > 0);
    }

    private class ComboDetalleAdapter extends ListItemAdapter<PromocionDetalle> {

        public ComboDetalleAdapter(Context context, int resource, List<PromocionDetalle> objects) {
            super(context, resource, objects);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(getContext(), R.layout.lyt_linea_combo, null);
                holder.tvNombreArticulo = (TextView) convertView.findViewById(R.id.tvArticulo);
                holder.etUnidades = (EditText) convertView.findViewById(R.id.etUnidades);
                holder.etBultos = (EditText) convertView.findViewById(R.id.etBultos);
                holder.tvStock = (TextView) convertView.findViewById(R.id.tvStock);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final PromocionDetalle detalle = getItem(position);
            holder.tvNombreArticulo.setText(detalle.getArticulo().getDescripcion());
            holder.tvStock.setText(String.valueOf(detalle.getArticulo().getStock()));

            if (holder.etUnidadesWhatcher != null) {
                holder.etUnidades.removeTextChangedListener(holder.etUnidadesWhatcher);
            }
            holder.etUnidades.setText(String.valueOf((int) detalle.getUnidades()));

            holder.etUnidadesWhatcher = new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable s) {
                    detalle.setUnidades(Formatter.parseInt(s.toString()));
                    tvTotal.setText(String.valueOf(getCantidades()));
                    checkButtonAceptar();
                }
            };
            holder.etUnidades.addTextChangedListener(holder.etUnidadesWhatcher);
            if (holder.etBultosWatcher != null) {
                holder.etBultos.removeTextChangedListener(holder.etBultosWatcher);
            }
            holder.etBultos.setText(String.valueOf(detalle.getBultos()));

            holder.etBultosWatcher = new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable s) {
                    detalle.setBultos(Formatter.parseInt(s.toString()));
                    tvTotal.setText(String.valueOf(getCantidades()));
                    checkButtonAceptar();
                }
            };
            holder.etBultos.addTextChangedListener(holder.etBultosWatcher);
            return convertView;
        }

        private class ViewHolder {
            TextView tvNombreArticulo;
            EditText etUnidades;
            EditText etBultos;
            TextView tvStock;
            TextWatcher etUnidadesWhatcher;
            TextWatcher etBultosWatcher;

        }

        public int getCantidades() {
            int cantidad = 0;
            for (PromocionDetalle detalle : items) {
                cantidad += detalle.getCantidad();
            }
            return cantidad;
        }

        public ArrayList<VentaDetalle> getItemsCombo() {
            ArrayList<PromocionDetalle> itemsCombo = new ArrayList<>();
            for (PromocionDetalle detalle : items) {
                if (detalle.getCantidad() > 0) {

                    itemsCombo.add(detalle);
                }
            }
            return VentaDetalleBo.parseVentaDetalles(itemsCombo);
        }

        @Override
        public Filter getFilter() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void sort() {
            // TODO Auto-generated method stub

        }

    }

    public boolean isComboValid() {
        return comboValid;
    }

    private void setIsComboValid(boolean valid) {
        comboValid = valid;
    }

    public interface ComboListener {
        void onComboAccepted(ArrayList<VentaDetalle> listVentasDetalles, int cant);

        void onDismissDialog();
    }

}
