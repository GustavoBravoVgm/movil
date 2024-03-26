package com.ar.vgmsistemas.view.objetivoVenta;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ArticuloBo;
import com.ar.vgmsistemas.bo.DescuentoProveedorBo;
import com.ar.vgmsistemas.bo.DocumentoBo;
import com.ar.vgmsistemas.bo.EmpresaBo;
import com.ar.vgmsistemas.bo.ObjetivoVentaBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.bo.VentaBo;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.Documento;
import com.ar.vgmsistemas.entity.Empresa;
import com.ar.vgmsistemas.entity.ListaPrecioDetalle;
import com.ar.vgmsistemas.entity.ObjetivoVenta;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.entity.key.PkDocumento;
import com.ar.vgmsistemas.entity.key.PkVenta;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.view.AlertDialog;
import com.ar.vgmsistemas.view.BaseActivity;
import com.ar.vgmsistemas.view.venta.DetalleVentaFragment;
import com.ar.vgmsistemas.view.venta.FrmVenta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FrmGestionObjetivoVenta extends BaseActivity {

    private static final String TAG = null;
    private final static int TIPO_LISTA_BASE = 1;

    public static int ORIGEN_OPCIONES_CLIENTE = 1;
    public static int ORIGEN_AGREGAR_LINEA = 2;

    private ObjetivoVentaAdapter _adapter;
    private ListView _lstObjetivoVentas;
    private View footerView;
    private TextView lblCanalValue;
    // BO
    private ObjetivoVentaBo _objetivoVentaBo;
    private ArticuloBo _articuloBo;
    private EmpresaBo _empresaBo;
    private DescuentoProveedorBo _descuentoProveedorBo;

    private Venta _venta;
    private Empresa _empresa;
    private int _origen;
    private Cliente _cliente;

    //BD
    private RepositoryFactory _repoFactory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_objetivo_venta);
        setActionBarTitle(R.string.lblObjetivoVenta);
        Object data = getLastNonConfigurationInstance();
        if (data != null) {
            _adapter = (ObjetivoVentaAdapter) data;
        }
        Bundle b = this.getIntent().getExtras();
        this._venta = (Venta) b.getSerializable("venta");
        this._origen = (Integer) b.getSerializable("origen");
        _cliente = (Cliente) b.getSerializable(FrmVenta.EXTRA_CLIENT);
        initComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initVar();
        loadObjetivoVentas(_venta.getCliente());
    }

    private void initVar() {
        _repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
        _objetivoVentaBo = new ObjetivoVentaBo(_repoFactory);
        _articuloBo = new ArticuloBo(_repoFactory);
        _empresaBo = new EmpresaBo(_repoFactory);
        _descuentoProveedorBo = new DescuentoProveedorBo(_repoFactory);
        try {
            _empresa = _empresaBo.recoveryEmpresa();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.lblCanalValue.setText(_venta.getCliente().getDescripcionRubro());
    }

    private void initComponents() {
        this._lstObjetivoVentas = (ListView) findViewById(R.id.lstObjetivoDetalle);

        // btnAceptar
        Button btnAceptar = (Button) findViewById(R.id.btnAgregarLinea);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnAceptarOnClick();
            }
        });

        // btnCancelar
        Button btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnCancelarOnClick();
            }
        });
        lblCanalValue = (TextView) findViewById(R.id.lblCanalValue);
    }

    @Override
    public Object getLastNonConfigurationInstance() {
        // TODO Auto-generated method stub
        return this._adapter;

    }

    private void loadObjetivoVentas(Cliente cliente) {
        try {
            if (_adapter == null || _adapter.getCount() == 0) {
                List<ObjetivoVenta> objetivoVentas = _objetivoVentaBo.recoveryObjetivosVentasNoCumplidosByCliente(cliente);

                List<ObjetivoVenta> objetivoVentasLista = new ArrayList<ObjetivoVenta>();
                for (Iterator<ObjetivoVenta> iterator = objetivoVentas.iterator(); iterator.hasNext(); ) {
                    ObjetivoVenta objetivoVenta = (ObjetivoVenta) iterator.next();
                    Cliente clienteObj = objetivoVenta.getCliente();
                    clienteObj.setListaPrecio(cliente.getListaPrecio());
                    objetivoVenta.setCliente(clienteObj);
                    objetivoVentasLista.add(objetivoVenta);
                }
                this._adapter = new ObjetivoVentaAdapter(this, R.layout.lyt_objetivo_venta_item, objetivoVentasLista, _empresa);
            }
            this._lstObjetivoVentas.setAdapter(_adapter);
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "loadObjetivoVentas", e, this);
        }
    }

    private void generarVenta() {
        asignarId();
        try {
            if (!this._adapter.isEmpty()) {
                for (int i = 0; i < this._adapter.getCount(); i++) {
                    ObjetivoVenta objetivoVenta = this._adapter.getItem(i);
                    tratarObjetivoSeleccionado(objetivoVenta);
                }
            }
            _venta.setCondicionVenta(_venta.getCliente().getCondicionVenta());
            // _venta.setTasaDescuento(_venta.getCondicionVenta().getTasaDescuento());
            _venta.setTasaDescuentoCondicionVenta(_venta.getCondicionVenta().getTasaDescuento());
            VentaBo.actualizarTotales(_venta);
        } catch (Exception e) {
            tratarErrorGenerarVenta();
        }
    }

    private void tratarErrorGenerarVenta() {
        AlertDialog dialog = new AlertDialog(this, getString(R.string.error), ErrorManager.ErrorGenerarVenta);
        dialog.setPositiveButton(R.string.btnAceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                FrmGestionObjetivoVenta.this.finish();
            }
        });
        dialog.show();
    }

    private void tratarObjetivoSeleccionado(ObjetivoVenta objetivoVenta) throws Exception {
        if ((objetivoVenta.getCantidadAVender() > 0) | (objetivoVenta.getBultosAVender() > 0)) {
            VentaDetalle ventaDetalle = new VentaDetalle();
            ventaDetalle.setBultos(objetivoVenta.getBultosAVender());
            ventaDetalle.setUnidades(objetivoVenta.getCantidadAVender());
            ventaDetalle.setArticulo(objetivoVenta.getArticulo());
            ventaDetalle.setListaPrecio(this._venta.getCliente().getListaPrecio());
            double cantidad = objetivoVenta.getBultosAVender() * objetivoVenta.getArticulo().getUnidadPorBulto()
                    + objetivoVenta.getCantidadAVender();
            ventaDetalle.setCantidad(cantidad);
            Articulo articulo = ventaDetalle.getArticulo();
            if (articulo != null) {
                setearPrecios(objetivoVenta, ventaDetalle, articulo);
            }
            _venta.getDetalles().add(ventaDetalle);
        }
    }

    private void setearPrecios(ObjetivoVenta objetivoVenta, VentaDetalle ventaDetalle, Articulo articulo) throws Exception {
        List<ListaPrecioDetalle> listasPrecioDetalle = _articuloBo.recoveryListaPrecioToMobile(articulo);
        articulo.setListaPreciosDetalle(listasPrecioDetalle);
        int j = 0;
        boolean encontro = false;
        while (j < articulo.getListaPreciosDetalle().size() && !encontro) {
            encontro = articulo.getListaPreciosDetalle().get(j).getListaPrecio().equals(_venta.getCliente().getListaPrecio());
            if (!encontro) {
                j++;
            }
        }
        VentaBo.setearPreciosDetalle(_venta.getCliente(), ventaDetalle, articulo.getListaPreciosDetalle().get(j), getApplicationContext());
        double tasaDescuento = objetivoVenta.getTasaDescuento() * (-1) / 100;
        ventaDetalle.setTasaDescuento(tasaDescuento);
        if (tasaDescuento > 0) {
            _venta.setTieneDescuento(true);
        }
        double precioUnitarioSinIva = ventaDetalle.getPrecioUnitarioSinIva();
        double tasaIva = articulo.getTasaIva();
        double precioIvaUnitario = precioUnitarioSinIva * tasaIva;
        ventaDetalle.setPrecioIvaUnitario(precioIvaUnitario);
    }

    private void asignarId() {
        if (_venta.getId() == null) {
            PkVenta id = new PkVenta();
            this._venta.setId(id);

            String letra = VentaBo.recoveryLetraComprobante(this._venta.getCliente());
            id.setIdLetra(letra);

            String idTipoDocumento = "";
            idTipoDocumento = PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).getIdTipoDocumentoPorDefecto();
            id.setIdDocumento(idTipoDocumento);

            int puntoVenta = PreferenciaBo.getInstance().getPreferencia(getApplicationContext()).getIdPuntoVentaPorDefecto();
            this._venta.getId().setPuntoVenta(puntoVenta);

            DocumentoBo documentoBo = new DocumentoBo(_repoFactory);
            long numeroDocumento;

            try {
                numeroDocumento = documentoBo.recoveryNumeroDocumento(idTipoDocumento, letra, puntoVenta);
                this._venta.getId().setIdNumeroDocumento(numeroDocumento);
                PkDocumento idDoc = new PkDocumento();
                idDoc.setIdDocumento(_venta.getId().getIdDocumento());
                idDoc.setIdLetra(_venta.getId().getIdLetra());
                idDoc.setPuntoVenta(_venta.getId().getPuntoVenta());
                Documento documento = documentoBo.recoveryById(idDoc);
                _venta.setDocumento(documento);

            } catch (Exception e) {
                ErrorManager.manageException("FrmEgreso", "initValues", e);
            }
        }
    }

    public void btnAceptarOnClick() {
        generarVenta();
        if (_origen == ORIGEN_OPCIONES_CLIENTE) {
            Intent intent = new Intent(this, FrmVenta.class);
            intent.putExtra("venta", _venta);
            intent.putExtra("modo", FrmVenta.ALTA);
            intent.putExtra(FrmVenta.EXTRA_CLIENT, _cliente);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, DetalleVentaFragment.class);
            intent.putExtra("ventaObjetivos", _venta);
            intent.putExtra("modo", DetalleVentaFragment.MODO_ALTA);
            setResult(RESULT_OK, intent);
        }
        this.finish();
    }

    public void btnCancelarOnClick() {
        if (_origen == ORIGEN_OPCIONES_CLIENTE) {
            Intent intent = new Intent(this, FrmVenta.class);
            intent.putExtra("venta", _venta);
            intent.putExtra("modo", FrmVenta.ALTA);
            intent.putExtra(FrmVenta.EXTRA_CLIENT, _cliente);
            startActivity(intent);
        }
        this.finish();
    }

}
