
package com.ar.vgmsistemas.view.venta.combo;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;

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
import com.ar.vgmsistemas.view.BaseFragment;
import com.ar.vgmsistemas.view.ListItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class CargaComboEspecialFragment extends BaseFragment {

    public static final String ARTICULO = "articulo";
    public static final String CANTIDAD = "cantidad";
    public static final String LISTENER = "listener";
    public static final String VENTAS_DETALLE = "ventaDetalle";
    private static final String MODE = "mode";

    private static final int EDICION = 1;
    private static final int ALTA = 0;

    private PromocionRequisito mRequisito;
    private int mMode;
    //private Button mBtnAceptar;
    private int totalRequerido;
    private boolean comboValid = false;
    private EditText etCantidad;
    private TextView tvTotalRequerido;
    private TextView tvTotal;
    private ComboDetalleAdapter mAdapter;
    private VentaDetalle mVentaDetalle;


    public static CargaComboEspecialFragment newInstance(String cant, Articulo articulo) {
        CargaComboEspecialFragment cargaComboDialogFragment = new CargaComboEspecialFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARTICULO, articulo);
        bundle.putInt(CANTIDAD, Integer.valueOf(cant));
        bundle.putInt(MODE, ALTA);
        cargaComboDialogFragment.setArguments(bundle);

        return cargaComboDialogFragment;
    }

    public static CargaComboEspecialFragment newInstance(VentaDetalle detalle) {
        CargaComboEspecialFragment cargaComboEspecialFragment = new CargaComboEspecialFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(VENTAS_DETALLE, detalle);
        bundle.putInt(MODE, EDICION);
        cargaComboEspecialFragment.setArguments(bundle);
        return cargaComboEspecialFragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int cant = 0;
        mMode = getArguments().getInt(MODE);
        if (mMode == EDICION)
            mVentaDetalle = (VentaDetalle) getArguments().getSerializable(VENTAS_DETALLE);

        View view = View.inflate(getActivity(), R.layout.lyt_combo_especial, null);

        Articulo articulo = (mMode == ALTA) ? articulo = (Articulo) getArguments().getSerializable(ARTICULO) : mVentaDetalle.getArticulo();
        List<PromocionDetalle> promocionDetalle = null;
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        PromocionBo bo = new PromocionBo(repoFactory);
        PromocionRequisitoBo requisitoBo = new PromocionRequisitoBo(repoFactory);
        try {
            mRequisito = requisitoBo.getRequisito(articulo.getId());
            promocionDetalle = bo.recoveryPromocionItems(articulo);
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
//		mBtnAceptar = (Button) view.findViewById(R.id.btnAceptar);
        tvTotalRequerido.setText(PromocionBo.getCantidadRequerida(mRequisito.getCantArticulos(), cant));

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
        tvTotal.setText(String.valueOf(mAdapter.getCantidades()));
        return view;
    }

    public ArrayList<VentaDetalle> getPromociones() {
        return mAdapter.getItemsCombo();
    }

    public boolean isEditMode() {
        return mMode == EDICION;
    }

    protected void checkButtonAceptar() {
        setIsComboValid(mAdapter.getCantidades() >= totalRequerido && totalRequerido > 0);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    protected long getRequisito() {
        return mRequisito.getCantArticulos() * Integer.valueOf(etCantidad.getText().toString());
    }

    private class ComboDetalleAdapter extends ListItemAdapter<PromocionDetalle> {

        public ComboDetalleAdapter(Context context, int resource, List<PromocionDetalle> objects) {
            super(context, resource, objects);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
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
        void onComboAccepted(ArrayList<PromocionDetalle> listPromociones);

    }
}
