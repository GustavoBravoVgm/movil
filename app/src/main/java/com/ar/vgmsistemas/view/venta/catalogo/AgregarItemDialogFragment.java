package com.ar.vgmsistemas.view.venta.catalogo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.AccionesComDetalleBo;
import com.ar.vgmsistemas.bo.ListaPrecioDetalleBo;
import com.ar.vgmsistemas.entity.AccionesComDetalle;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.ListaPrecioDetalle;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.view.dialogs.BaseDialogFragment;

import java.util.List;
import java.util.Locale;

public class AgregarItemDialogFragment extends BaseDialogFragment {
    View itemView;
    TextView txtDescripcionArticulo;
    EditText txtBultosAVender;
    EditText txtUnidadesAVender;
    TextView txtPrecioLista;
    TextView txtDtoEmpresa;
    TextView txtDtoProveedor;
    TextView txtPrecioFinalU;
    TextView txtUnidadesTotales;
    TextView txtTotalLinea;
    Button btnAceptar;
    Button btnCancelar;
    View.OnClickListener onClickListenerAceptar;
    ListaPrecioDetalle listaPrecioDetalle;
    Cliente cliente;

    ListaPrecioDetalleBo listaPrecioDetalleBo;
    List<ListaPrecioDetalle> listaPrecioDetalleList;
    AccionesComDetalleBo accionesComDetalleBo;
    //BD
    RepositoryFactory _repoFactory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        listaPrecioDetalleBo = new ListaPrecioDetalleBo(_repoFactory);
        accionesComDetalleBo = new AccionesComDetalleBo(_repoFactory);
    }

    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        itemView = View.inflate(getActivity(), R.layout.lyt_agregar_catalogo, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(itemView);
        return builder.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        initComponents();
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btnAceptar.setOnClickListener(getOnClickListenerAceptar());
        String descripcion = this.getListaPrecioDetalle().getArticulo().getDescripcion();
        txtDescripcionArticulo.setText(descripcion);
        ListaPrecioDetalle listaPrecioDetalle = null;
        txtBultosAVender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                refreshData();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtBultosAVender.getText() == null) {
                    txtBultosAVender.setText("0");
                }
                refreshData();
            }
        });
        txtUnidadesAVender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                refreshData();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txtUnidadesAVender.getText() == null) {
                    txtUnidadesAVender.setText("0");
                }
                refreshData();
            }
        });
        try {
            listaPrecioDetalleList = listaPrecioDetalleBo.recoveryByArticuloAndLista(
                    this.getListaPrecioDetalle().getListaPrecio().getId(),
                    this.getListaPrecioDetalle().getArticulo().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        refreshData();
    }

    public View.OnClickListener getOnClickListenerAceptar() {
        return onClickListenerAceptar;
    }

    public void setOnClickListenerAceptar(View.OnClickListener onClickListenerAceptar) {
        this.onClickListenerAceptar = onClickListenerAceptar;
    }

    public EditText getTxtBultosAVender() {
        return txtBultosAVender;
    }

    public void setTxtBultosAVender(EditText txtBultosAVender) {
        this.txtBultosAVender = txtBultosAVender;
    }

    public EditText getTxtUnidadesAVender() {
        return txtUnidadesAVender;
    }

    public void setTxtUnidadesAVender(EditText txtUnidadesAVender) {
        this.txtUnidadesAVender = txtUnidadesAVender;
    }

    public ListaPrecioDetalle getListaPrecioDetalle() {
        return listaPrecioDetalle;
    }

    public void setListaPrecioDetalle(ListaPrecioDetalle listaPrecioDetalle) {
        this.listaPrecioDetalle = listaPrecioDetalle;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    private void initComponents() {
        txtDescripcionArticulo = itemView.findViewById(R.id.lblDescripcionArticulo);
        txtBultosAVender = itemView.findViewById(R.id.txtBultosAVender);
        txtUnidadesAVender = itemView.findViewById(R.id.txtUnidadesAVender);
        txtPrecioLista = itemView.findViewById(R.id.txtPrecioLista);
        txtDtoEmpresa = itemView.findViewById(R.id.txtDtoEmpresa);
        txtDtoProveedor = itemView.findViewById(R.id.txtDtoProveedor);
        txtPrecioFinalU = itemView.findViewById(R.id.txtPrecioFinalU);
        txtUnidadesTotales = itemView.findViewById(R.id.txtUnidadesTotales);
        txtTotalLinea = itemView.findViewById(R.id.txtTotalLinea);
        btnAceptar = itemView.findViewById(R.id.btnAceptar);
        btnCancelar = itemView.findViewById(R.id.btnCancelar);


    }

    @SuppressLint("SetTextI18n")
    private void refreshData() {
        long bultos = 0;
        if (!txtBultosAVender.getText().toString().equals(""))
            bultos = Long.parseLong(txtBultosAVender.getText().toString());

        long unidades = 0;
        if (!txtUnidadesAVender.getText().toString().equals(""))
            unidades = Long.parseLong(txtUnidadesAVender.getText().toString());

        double cantidad = this.getListaPrecioDetalle().getArticulo().getUnidadPorBulto() *
                bultos + unidades;

        txtUnidadesTotales.setText("x" + cantidad + " U.");
        ListaPrecioDetalle listaPrecioDetalle = null;

        for (ListaPrecioDetalle listaPrecioDetalle1 : listaPrecioDetalleList) {
            if ((listaPrecioDetalle1.getId().getCaArticuloDesde() <= cantidad &&
                    listaPrecioDetalle1.getId().getCaArticuloHasta() >= cantidad) ||
                    (listaPrecioDetalle1.getId().getCaArticuloDesde() == 0 &&
                            listaPrecioDetalle1.getId().getCaArticuloHasta() == 0)) {
                listaPrecioDetalle = listaPrecioDetalle1;
                break;
            }
        }

        double precio = listaPrecioDetalle != null ? listaPrecioDetalle.getPrecioFinal() : 0d;
        String prListaStr = "$" + String.format(Locale.GERMAN, "%.2f", precio);
        txtPrecioLista.setText(prListaStr);

        VentaDetalle ventaDetalle = new VentaDetalle();
        ventaDetalle.setArticulo(this.getListaPrecioDetalle().getArticulo());
        ventaDetalle.setUnidades(unidades);
        ventaDetalle.setBultos((int) bultos);
        ventaDetalle.setCantidad(cantidad);

        AccionesComDetalle accionesComDetalle = null;
        try {
            accionesComDetalle = accionesComDetalleBo.getAccionesComDetalle(this.getCliente(),
                    ventaDetalle, this.getListaPrecioDetalle().getListaPrecio(), "P");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (accionesComDetalle != null) {
            String dtoProvStr = String.format(Locale.GERMAN, "%.2f", accionesComDetalle.getTaDto() * 100) + "%";
            txtDtoProveedor.setText(dtoProvStr);
            precio = precio * (1 + accionesComDetalle.getTaDto());
        } else {
            txtDtoProveedor.setText("- 0.00 %");
        }

        accionesComDetalle = null;

        try {
            accionesComDetalle = accionesComDetalleBo.getAccionesComDetalle(this.getCliente(),
                    ventaDetalle, this.getListaPrecioDetalle().getListaPrecio(), "E");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (accionesComDetalle != null) {
            String dtoEmpresaStr = String.format(Locale.GERMAN, "%.2f", accionesComDetalle.getTaDto() * 100) + "%";
            txtDtoEmpresa.setText(dtoEmpresaStr);
            precio = precio * (1 + accionesComDetalle.getTaDto());
        } else {
            txtDtoEmpresa.setText("- 0.00%");
        }
        String prFinalUStr = "$" + String.format(Locale.GERMAN, "%.2f", precio);
        txtPrecioFinalU.setText(prFinalUStr);
        String totalLineaStr = "$" + String.format(Locale.GERMAN, "%.2f", precio * cantidad);
        txtTotalLinea.setText(totalLineaStr);
    }
}
