package com.ar.vgmsistemas.view.venta.catalogo;


import static com.ar.vgmsistemas.view.informes.FrmVentasXArticulo.KEY_CLIENTES;
import static com.ar.vgmsistemas.view.informes.FrmVentasXArticulo.KEY_VENTA;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.SearchView.OnQueryTextListener;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ListaPrecioBo;
import com.ar.vgmsistemas.bo.ListaPrecioDetalleBo;
import com.ar.vgmsistemas.bo.MarcaBo;
import com.ar.vgmsistemas.bo.RubroBo;
import com.ar.vgmsistemas.bo.SubrubroBo;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.ListaPrecio;
import com.ar.vgmsistemas.entity.ListaPrecioDetalle;
import com.ar.vgmsistemas.entity.Marca;
import com.ar.vgmsistemas.entity.Rubro;
import com.ar.vgmsistemas.entity.Subrubro;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.entity.VentaDetalle;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ColorUtils;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.view.BaseActivity;
import com.ar.vgmsistemas.view.venta.FrmVenta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


public class CatalogoActivity extends BaseActivity {

    private MarcaBo marcaBo;

    RecyclerView recyclerListadoArticulo;
    CatalogoAdapter catalogoAdapter;
    RecyclerView.LayoutManager layoutManager;
    Cliente cliente;

    private final int DIALOG_RUBRO = 0;
    List<Rubro> rubros;
    String[] rubrosStr;
    String rubroFiltro = "Todos";
    int idRubroSeleccionado;
    Hashtable<String, Rubro> htableRubro;

    private final int DIALOG_SUBRUBRO = 1;
    List<Subrubro> subrubros;
    String[] subrubrosStr;
    String subrubroFiltro = "Todos";
    int idSubrubroSeleccionado;
    Hashtable<String, Subrubro> htableSubrubro;

    private final int DIALOG_MARCA = 2;
    List<Marca> marcas;
    String[] marcasStr;
    String marcaFiltro = "Todos";
    int idMarcaSeleccionado;
    Hashtable<String, Marca> htableMarca;

    private SearchView mSearchView;
    private String textBusqueda = "-1";

    private final int DIALOG_LISTA = 5;
    List<ListaPrecio> listasPrecios;
    String[] listaPreciosStr;
    String listasPreciosFiltro = "";
    int idListaPrecioSeleccionado;
    Hashtable<String, ListaPrecio> htableListaPrecios;


    private Venta mVenta;
    private static ListaPrecio _listaPrecioXDefecto;

    AgregarItemDialogFragment dialogCantidad;
    private final int DIALOG_CANTIDAD = 3;
    private final int DIALOG_CODIGO_CANTIDAD_NO_INGRESADA = 4;
    public final static int ALTA = 0;
    private boolean ORIGEN_BUSCAR = false;
    List<VentaDetalle> ventaDetalles;

    public static String filtrosCatalogo = "-1";
    public static long idArticuloUltimoSeleccionado;

    //DAO
    RepositoryFactory _repoFactory;

    public Hashtable<String, Subrubro> getHtableSubrubro() {
        return htableSubrubro;
    }

    public void setHtableSubrubro(Hashtable<String, Subrubro> htableSubrubro) {
        this.htableSubrubro = htableSubrubro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Venta getmVenta() {
        return mVenta;
    }

    public void setmVenta(Venta mVenta) {
        this.mVenta = mVenta;
    }

    public CatalogoAdapter getCatalogoAdapter() {
        return catalogoAdapter;
    }

    public String getTextBusqueda() {
        if (textBusqueda == null || textBusqueda.equals("")) {
            return "-1";
        }
        return textBusqueda;
    }

    public void setTextBusqueda(String textBusqueda) {
        this.textBusqueda = textBusqueda;
    }

    public Hashtable<String, Marca> getHtableMarca() {
        return htableMarca;
    }

    public void setHtableMarca(Hashtable<String, Marca> htableMarca) {
        this.htableMarca = htableMarca;
    }

    public Hashtable<String, ListaPrecio> getHtableListaPrecios() {
        return htableListaPrecios;
    }

    public void setHtableListaPrecios(Hashtable<String, ListaPrecio> htableListaPrecios) {
        this.htableListaPrecios = htableListaPrecios;
    }

    public void setCatalogoAdapter(CatalogoAdapter catalogoAdapter) {
        this.catalogoAdapter = catalogoAdapter;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public Hashtable<String, Rubro> getHtableRubro() {
        return htableRubro;
    }

    public void setHtableRubro(Hashtable<String, Rubro> htableRubro) {
        this.htableRubro = htableRubro;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.lyt_catalogo_activity);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            this.setCliente((Cliente) bundle.getSerializable(KEY_CLIENTES));
            this.setmVenta((Venta) bundle.getSerializable(KEY_VENTA));
        }
        initComponent();
        try {
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mn_catalogo, menu);

        MenuItem item = menu.findItem(R.id.mni_search_articulo_catalogo);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);
        mSearchView.setQueryHint(getResources().getString(R.string.mnDescripcion));

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchView.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String arg0) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onQueryTextChange(String arg0) {
                setTextBusqueda(arg0);
                generarFiltro();
                return true;
            }
        });

        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    protected AppCompatDialog onCreateDialog(int id) {
        AppCompatDialog dialog = null;
        switch (id) {
            case DIALOG_RUBRO:
                dialog = getDialogRubro();
                break;
            case DIALOG_SUBRUBRO:
                dialog = getDialogSubrubro();
                break;
            case DIALOG_MARCA:
                dialog = getDialogMarca();
                break;
            case DIALOG_CODIGO_CANTIDAD_NO_INGRESADA:
                dialog = getCantidadNoIngresada();
                break;
            case DIALOG_LISTA:
                dialog = getDialogLista();
                break;
        }

        return dialog;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mni_rubro:
                dlgOpcionesFiltroOnClick("Rubro");
                return true;
            case R.id.mni_subrubro:
                dlgOpcionesFiltroOnClick("Subrubro");
                return true;
            case R.id.mni_marca:
                dlgOpcionesFiltroOnClick("Marca");
                return true;
            case R.id.mni_listasCatalogo:
                dlgOpcionesFiltroOnClick("Lista");
            case R.id.mni_reset_filtros:
                resetFiltros();
                return true;
            case android.R.id.home:
                startActivity(new Intent(getBaseContext(), FrmVenta.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
                return true;
        }
        return true;
    }

    private void initComponent() {
        recyclerListadoArticulo = findViewById(R.id.recyclerListadoArticulo);
    }

    private void loadData() throws Exception {

        _repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
        this.marcaBo = new MarcaBo(_repoFactory);

        if (this._listaPrecioXDefecto == null) {
            //busco la lista de precio configurada en el comprobante si es que esta configurado sino tomo el del cliente
            if (this.getmVenta() != null && this.getmVenta().getDocumento() != null &&
                    this.getmVenta().getDocumento().getIdListaDefault() != null && this.getmVenta().getDocumento().getIdListaDefault() > 0) {
                try {
                    ListaPrecioBo listaPrecioBo = new ListaPrecioBo(_repoFactory);
                    this._listaPrecioXDefecto = listaPrecioBo.recoveryById(mVenta.getDocumento().getIdListaDefault());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                this._listaPrecioXDefecto = this.getCliente().getListaPrecio();
            }
        }
        //busco si hay que cambiar el color de fondo
        if (this.getmVenta() != null && this.getmVenta().getDocumento() != null &&
                this.getmVenta().getDocumento().getTiModifVisualMovil() != null &&
                (!this.getmVenta().getDocumento().getTiModifVisualMovil().equals("NO"))) {
            try {
                Window window = getWindow();
                ActionBar actBar = getSupportActionBar();
                String confDocumento = this.getmVenta().getDocumento().getTiModifVisualMovil();
                int color = 0;
                //COLOR_VERDE("CV"),COLOR_AZUL("CA"), COLOR_AMARILLO("CY"), COLOR_NARANJA("CN");
                switch (confDocumento) {
                    case "CV":
                        color = getResources().getColor(R.color.color_verde);
                        break;
                    case "CA":
                        color = getResources().getColor(R.color.color_azul);
                        break;
                    case "CY":
                        color = getResources().getColor(R.color.color_amarillo);
                        break;
                    case "CN":
                        color = getResources().getColor(R.color.color_naranja);
                        break;
                    default:
                        color = getResources().getColor(R.color.action_bar);
                        break;
                }
                ColorUtils.cambioColor(window, actBar, ColorUtils.TipoCambio.CAMBIO_COLOR, color);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        cargarAdapter();
    }

    private AppCompatDialog getCantidadNoIngresada() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.tituloError);
        builder.setMessage(ErrorManager.CodigoCantidadNoIngresada);
        builder.setPositiveButton(R.string.btnAceptar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    private AppCompatDialog getDialogMarca() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.tituloSeleccionarFiltro);
        builder.setSingleChoiceItems(marcasStr, Arrays.binarySearch(marcasStr, marcaFiltro), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                idMarcaSeleccionado = which;
            }
        });
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog
                        marcaFiltro = marcasStr[idMarcaSeleccionado];
                        generarFiltro();
                        removeDialog(DIALOG_MARCA);

                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        removeDialog(DIALOG_MARCA);
                    }
                });

        return builder.create(); //Devuelve un AlertDialog
    }

    private AppCompatDialog getDialogRubro() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.tituloSeleccionarFiltro);
        builder.setSingleChoiceItems(rubrosStr, Arrays.binarySearch(rubrosStr, rubroFiltro), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                idRubroSeleccionado = which;
            }
        });
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog
                        rubroFiltro = rubrosStr[idRubroSeleccionado];
                        generarFiltro();
                        removeDialog(DIALOG_RUBRO);

                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        removeDialog(DIALOG_RUBRO);
                    }
                });

        return builder.create(); //Devuelve un AlertDialog
    }

    private AppCompatDialog getDialogSubrubro() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.tituloSeleccionarFiltro);
        builder.setSingleChoiceItems(subrubrosStr, Arrays.binarySearch(subrubrosStr, subrubroFiltro), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                idSubrubroSeleccionado = which;
            }
        });
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        subrubroFiltro = subrubrosStr[idSubrubroSeleccionado];
                        generarFiltro();
                        removeDialog(DIALOG_SUBRUBRO);
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        removeDialog(DIALOG_SUBRUBRO);
                    }
                });

        return builder.create(); //DevuelveAlertDialog
    }

    private AppCompatDialog getDialogLista() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.tituloSeleccionarLista);
        builder.setSingleChoiceItems(listaPreciosStr, Arrays.binarySearch(listaPreciosStr, listasPreciosFiltro), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                idListaPrecioSeleccionado = which;
            }
        });
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        listasPreciosFiltro = listaPreciosStr[idListaPrecioSeleccionado];
                        cambiarLista();
                        removeDialog(DIALOG_LISTA);
                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        removeDialog(DIALOG_LISTA);
                    }
                });

        return builder.create(); //DevuelveAlertDialog
    }

    private void generarFiltro() {
        // El formato va a ser negocio,rubro,subrubro,marca. Cuando no tenga va a estar vacio.
        String filtro;
        String negocioS = "-1";
        String rubroS = "-1";
        String subrubroS = "-1";
        String marcaS = "-1";
        // Arranco con el filtro vacio
        if (subrubroFiltro.equals("Todos")) {
            if (!rubroFiltro.equals("Todos")) {
                // Mantengo el filtro vacio si tanto el rubro con el subrubro es todos
                Rubro rubro = this.getHtableRubro().get(rubroFiltro);
                negocioS = Long.toString(rubro.getId().getIdNegocio());
                rubroS = Long.toString(rubro.getId().getIdRubro());
            }
        } else {
            //saco los datos del segmento a partir del subrubro
            Subrubro subrubro = this.getHtableSubrubro().get(subrubroFiltro);
            negocioS = Long.toString(subrubro.getId().getIdNegocio());
            rubroS = Long.toString(subrubro.getId().getIdRubro());
            subrubroS = Long.toString(subrubro.getId().getIdSubrubro());
        }
        if (!marcaFiltro.equals("Todos")) {
            Marca marca = this.getHtableMarca().get(marcaFiltro);
            marcaS = Long.toString(marca.getId());
        }
        filtro = negocioS + "," + rubroS + "," + subrubroS + "," + marcaS + "," + getTextBusqueda();
        filtrosCatalogo = filtro;//resguardo filtro para cuando vuelve a entrar al catalogo
        this.getCatalogoAdapter().getFilter().filter(filtro);
    }

    private void cambiarLista() {
        try {
            cargarAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetFiltros() {
        rubroFiltro = getResources().getString(R.string.todos);// "Todos"
        subrubroFiltro = getResources().getString(R.string.todos);// "Todos"
        marcaFiltro = getResources().getString(R.string.todos);// "Todos"
        textBusqueda = "-1";
        filtrosCatalogo = "-1";//seteo para dejar filtro original
        idArticuloUltimoSeleccionado = 0;//seteo que ningun articulo fue seleccionado
        this.getCatalogoAdapter().getFilter().filter("");
    }

    private void dlgOpcionesFiltroOnClick(String tipo) {
        List<String> strings = new ArrayList<>();
        int i;
        switch (tipo) {
            case "Rubro":
                i = 0;
                RubroBo rubroBo = new RubroBo(_repoFactory);
                try {
                    rubros = rubroBo.recoveryAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Hashtable<String, Rubro> htable1 = new Hashtable();
                for (Rubro rubro : rubros) {
                    htable1.put(rubro.getId().getIdRubro() + "-" + rubro.getDescripcion(), rubro);
                    strings.add(rubro.getId().getIdRubro() + "-" + rubro.getDescripcion());
                }
                this.setHtableRubro(htable1);
                String[] rubrosString = new String[strings.size() + 1];

                for (String s : strings) {
                    rubrosString[i] = s;
                    i++;
                }
                rubrosString[i] = "Todos";
                rubrosStr = rubrosString;
                showDialog(DIALOG_RUBRO);
                break;
            case "Subrubro":
                i = 0;
                SubrubroBo subrubroBo = new SubrubroBo(_repoFactory);
                if (rubroFiltro.equals("Todos")) {
                    try {
                        subrubros = subrubroBo.recoveryAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        subrubros = subrubroBo.recoveryByRubro(htableRubro.get(rubroFiltro));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                Hashtable<String, Subrubro> htableSubrubro = new Hashtable();
                for (Subrubro subrubro : subrubros) {
                    htableSubrubro.put(Long.toString(subrubro.getId().getIdRubro()) + "-" +
                            subrubro.getDescripcion(), subrubro);
                    strings.add(Long.toString(subrubro.getId().getIdRubro()) + "-" +
                            subrubro.getDescripcion());
                }
                this.setHtableSubrubro(htableSubrubro);
                String[] subrubrosString = new String[strings.size() + 1];
                for (String s : strings) {
                    subrubrosString[i] = s;
                    i++;
                }
                subrubrosString[i] = "Todos";
                subrubrosStr = subrubrosString;
                showDialog(DIALOG_SUBRUBRO);
                break;
            case "Marca":
                i = 0;
                try {
                    marcas = marcaBo.recoveryAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Hashtable<String, Marca> htableMarca = new Hashtable();
                for (Marca marca : marcas) {
                    htableMarca.put(Long.toString(marca.getId()) + "-" +
                            marca.getDescripcion(), marca);
                    strings.add(Long.toString(marca.getId()) + "-" +
                            marca.getDescripcion());
                }
                this.setHtableMarca(htableMarca);
                String[] marcasString = new String[strings.size() + 1];
                for (String s : strings) {
                    marcasString[i] = s;
                    i++;
                }
                marcasString[i] = "Todos";
                marcasStr = marcasString;
                showDialog(DIALOG_MARCA);
                break;
            case "Lista":
                i = 0;
                ListaPrecioBo listaPrecioBo = new ListaPrecioBo(_repoFactory);
                List<ListaPrecio> listaPrecios = new ArrayList<>();
                try {
                    listaPrecios = listaPrecioBo.recoveryAllSeleccionable();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Hashtable<String, ListaPrecio> hTableLista = new Hashtable();
                for (ListaPrecio listaPrecio : listaPrecios) {
                    hTableLista.put(listaPrecio.getId() + "-" + listaPrecio.getDescripcion(), listaPrecio);
                    strings.add(listaPrecio.getId() + "-" + listaPrecio.getDescripcion());
                }
                this.setHtableListaPrecios(hTableLista);
                String[] listaPreciosString = new String[strings.size()];

                for (String s : strings) {
                    listaPreciosString[i] = s;
                    i++;
                }
                listaPreciosStr = listaPreciosString;
                showDialog(DIALOG_LISTA);
                break;
        }
    }

    private void showDialogImagenGrande(ListaPrecioDetalle item) {
//        BigImageDialogFragment dialogFragment = new BigImageDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putString("item", String.valueOf(item.getArticulo().getId()));
        // set Fragment class Arguments
        BigImageDialogFragment dialogFragment = new BigImageDialogFragment();
        dialogFragment.setArguments(bundle);

        dialogFragment.show(getSupportFragmentManager(), "dialog");
    }

    private void showDialogPromociones(ListaPrecioDetalle item) {
        PromocionesDialogFragment dialogFragment = new PromocionesDialogFragment();
        dialogFragment.setListaPrecioDetalle(item);
        dialogFragment.setCliente(getCliente());
        dialogFragment.show(getSupportFragmentManager(), "dialog");
    }

    private void showDialogCantidad(final ListaPrecioDetalle item) {
        final AgregarItemDialogFragment dialogCantidad = new AgregarItemDialogFragment();
        dialogCantidad.setOnClickListenerAceptar(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long bultos = 0;
                long unidades = 0;

                if (validarDatos()) {
                    if (!String.valueOf(dialogCantidad.getTxtBultosAVender().getText()).equals(""))
                        bultos = Long.valueOf(String.valueOf(dialogCantidad.getTxtBultosAVender().getText()));
                    if (!String.valueOf(dialogCantidad.getTxtUnidadesAVender().getText()).equals(""))
                        unidades = Long.valueOf(String.valueOf(dialogCantidad.getTxtUnidadesAVender().getText()));
                    //resguardo el ultimo item seleccionado
                    idArticuloUltimoSeleccionado = item.getArticulo().getId();
                    promptAgregarProductoOnClick(bultos, unidades, item);
                }
            }

            private boolean validarDatos() {
                boolean valido = false;
                if (((dialogCantidad.getTxtBultosAVender().getText()).toString().equals("") ||
                        dialogCantidad.getTxtBultosAVender().getText().toString().equals("0")) &&
                        (dialogCantidad.getTxtUnidadesAVender().getText().toString().equals("") ||
                                dialogCantidad.getTxtUnidadesAVender().getText().toString().equals("0"))) {
                    com.ar.vgmsistemas.view.AlertDialog alert = new com.ar.vgmsistemas.view.AlertDialog(CatalogoActivity.this, getString(R.string.tituloError), ErrorManager.CantidadInvalida);
                    alert.show();
                } else {
                    valido = true;
                }
                return valido;
            }
        });
        dialogCantidad.setCliente(this.getCliente());
        dialogCantidad.setListaPrecioDetalle(item);
        dialogCantidad.show(getSupportFragmentManager(), "tag");
    }

    private boolean validarDatos() {
        boolean valido = false;
        if (((dialogCantidad.getTxtBultosAVender().getText()).equals("") ||
                dialogCantidad.getTxtBultosAVender().getText().equals(0)) &&
                (dialogCantidad.getTxtBultosAVender().getText().equals("") ||
                        dialogCantidad.getTxtBultosAVender().getText().equals(0)) &&
                (Double.parseDouble(dialogCantidad.getTxtBultosAVender().getText().toString()) <= 0 ||
                        Double.parseDouble(dialogCantidad.getTxtBultosAVender().getText().toString()) <= 0)) {
            com.ar.vgmsistemas.view.AlertDialog alert = new com.ar.vgmsistemas.view.AlertDialog(this, getString(R.string.tituloError), ErrorManager.ImporteInvalido);
            alert.show();
        } else {
            valido = true;
        }
        return valido;
    }

    private void promptAgregarProductoOnClick(long caBultos, long caUnidades, ListaPrecioDetalle item) {
        VentaDetalle ventaDetalle = new VentaDetalle();
        Articulo articulo = item.getArticulo();
        ventaDetalle.setArticulo(articulo);
        ventaDetalle.setUnidades(caUnidades);
        ventaDetalle.setBultos((int) (caBultos));
        ventaDetalle.setListaPrecio(item.getListaPrecio());
        Intent intent = new Intent(this, FrmVenta.class);
        intent.putExtra("linea", ventaDetalle);
        intent.putExtra("lineaPrecioDetalle", item);
        intent.putExtra("modo", ALTA);
        intent.putExtra("origen", ORIGEN_BUSCAR);
        intent.putExtra("catalogo", true);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void cargarAdapter() throws Exception {
        if (!this.listasPreciosFiltro.equals("")) {
            _listaPrecioXDefecto = this.getHtableListaPrecios().get(listasPreciosFiltro);
        }
        ListaPrecioDetalleBo listaPrecioDetalleBo = new ListaPrecioDetalleBo(_repoFactory);
        List<ListaPrecioDetalle> listaPrecioDetalleArticulos = listaPrecioDetalleBo.recoveryByListaPrecio(_listaPrecioXDefecto);
        List<ListaPrecioDetalle> listaPrecioDetallePromo = listaPrecioDetalleBo.recoveryPromocionesYCombos();
        List<ListaPrecioDetalle> union = new ArrayList<>();
        union.addAll(listaPrecioDetalleArticulos);
        union.addAll(listaPrecioDetallePromo);

        //agrego las cantidades ya cargadas.
        if (mVenta != null && mVenta.getDetalles() != null && mVenta.getDetalles().size() > 0) {
            //map para sumarizar cantidad pedida
            Map<Integer, Double> vDetSinRepetidos = new HashMap<>();
            for (VentaDetalle vd : mVenta.getDetalles()) {
                Integer idArt = vd.getArticulo().getId();
                Double cantArt = vd.getCantidad();
                Double cantCargada = vDetSinRepetidos.get(vd.getArticulo().getId()) == null ? 0d : vDetSinRepetidos.get(vd.getArticulo().getId());
                vDetSinRepetidos.put(idArt, cantCargada + cantArt);
            }
            //asigno cantidad pedida sumarizada por articulo
            for (ListaPrecioDetalle lpd : union) {
                lpd.setCantidadPedida(0d);
                for (Map.Entry<Integer, Double> entry : vDetSinRepetidos.entrySet()) {
                    if (lpd.getId().getIdArticulo() == entry.getKey()) {
                        lpd.setCantidadPedida(entry.getValue());
                    }
                }
            }
        }
        //ordeno por nombre de articulo
        Collections.sort(union, new Comparator<ListaPrecioDetalle>() {
            @Override
            public int compare(ListaPrecioDetalle lsPrDet1, ListaPrecioDetalle lsPrDet2) {
                return lsPrDet1.getArticulo().getDescripcion().compareToIgnoreCase(lsPrDet2.getArticulo().getDescripcion());
            }
        });
        ventaDetalles = new ArrayList<>();

        this.setCatalogoAdapter(null);
        this.setCatalogoAdapter(new CatalogoAdapter(getApplicationContext(), union, new CatalogoAdapter.ItemClickListener() {
            @Override
            public void onItemClickItem(ListaPrecioDetalle item) {
                showDialogCantidad(item);
            }

            @Override
            public void onItemClickPromociones(ListaPrecioDetalle item) {
                showDialogPromociones(item);
            }

            @Override
            public void onItemClickImagen(ListaPrecioDetalle item) {
                showDialogImagenGrande(item);
            }


        }, getCliente()));
        this.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.recyclerListadoArticulo.setAdapter(this.getCatalogoAdapter());
        this.recyclerListadoArticulo.setLayoutManager(this.getLayoutManager());
        //aplico filtros
        if (!filtrosCatalogo.equals("-1")) {
            this.getCatalogoAdapter().getFilter().filter(filtrosCatalogo);
        }

        //Posiciono dentro listado
        if (idArticuloUltimoSeleccionado > 0) {
            int contadorPosicion = -1;
            int posicion = 0;
            for (ListaPrecioDetalle lpd : this.getCatalogoAdapter().getListaPrecioDetalles()) {
                contadorPosicion++;
                if (lpd.getArticulo().getId() == idArticuloUltimoSeleccionado) {
                    posicion = contadorPosicion;
                    break;
                }
            }
            this.recyclerListadoArticulo.scrollToPosition(posicion);
        }

        listasPreciosFiltro = _listaPrecioXDefecto.getId() + "-" + _listaPrecioXDefecto.getDescripcion();

    }

}