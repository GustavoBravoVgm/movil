package com.ar.vgmsistemas.view.configuracion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.MovimientoBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.Preferencia;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.FileManager;
import com.ar.vgmsistemas.utils.SdSpaceManager;
import com.ar.vgmsistemas.utils.StorageUtils;
import com.ar.vgmsistemas.view.FrmLogin;
import com.ar.vgmsistemas.view.dialogs.ProgressDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.ar.vgmsistemas.view.menu.NavigationMenu;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FrmAdministracionBaseDatos extends Fragment {

    //Formato de Fecha
    private TextInputEditText txtFormatoFecha;

    //Carpeta de la base de datos
    //private String rutaSistema;

    //Intervalo de limpieza
    //private EditText txtRutaSistema;

    //Intervalo de limpieza
    private TextInputEditText txtBorradoBckXIntervalo;

    //limpieza x cantidad
    private TextInputEditText txtBorradoBckXCantidad;

    //Eliminar Base de Datos
    private MaterialButton btnEliminarBD;

    private RadioGroup rdgGrupo;
    private Spinner spnUbicacionBD;
    private int rdgSelection;

    private int ubicacion = 0;

    private final String TAG_ADMINISTRACION_BD = "Administracion_Bd";

    // DAO
    private RepositoryFactory _repoFactory;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        rdgSelection = PreferenciaBo.getInstance().getPreferencia(getActivity()).getBorradoBckupPreferido();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.lyt_administracion_base_de_datos, null);
        initViews(view);
        Bundle b = getArguments();
        if (b != null)
            ubicacion = b.getInt(FrmLogin.EXTRA_UBICACION, FrmLogin.EXTRA_UBICACION_INICIO);
        else ubicacion = 1;
        loadData();
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        setValues();
    }


    private void loadData() {
        this._repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        String formatoFecha = PreferenciaBo.getInstance().getPreferencia(getActivity()).getFormatoFecha();
        this.txtFormatoFecha.setText(formatoFecha);

        int intervaloBorradoBck = PreferenciaBo.getInstance().getPreferencia(getActivity()).getIntervaloBorradoBck();
        this.txtBorradoBckXIntervalo.setText(String.valueOf(intervaloBorradoBck));

        int borradoBckXCantidad = PreferenciaBo.getInstance().getPreferencia(getActivity()).getBorradoBckXCantidad();
        this.txtBorradoBckXCantidad.setText(String.valueOf(borradoBckXCantidad));

        rdgSelection = (rdgSelection == 0) ? R.id.rdbIntervalo : rdgSelection;
        this.rdgGrupo.check(rdgSelection);

    }


    @Override
    public void onPause() {
        super.onPause();
        setValues();
    }

    private void setValues() {
        String formatoFecha = this.txtFormatoFecha.getText().toString();
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setFormatoFecha(formatoFecha);

        //PreferenciaBo.getInstance().getPreferencia(getActivity()).setPathSistema(rutaSistema);

        int intervaloBorradoBck = Integer.parseInt(this.txtBorradoBckXIntervalo.getText().toString());
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setIntervaloBorradoBck(intervaloBorradoBck);

        int borradoBckXCantidad = Integer.parseInt(this.txtBorradoBckXCantidad.getText().toString());
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setBorradoBckXCantidad(borradoBckXCantidad);


    }

    private void initViews(final View view) {

        // txtIntervaloSincronizacion(EditText)
        this.txtBorradoBckXIntervalo = view.findViewById(R.id.txtIntervaloBorrado);

        this.txtBorradoBckXCantidad = view.findViewById(R.id.txtBorradoXCantidad);

        // txtFormatoFecha(EditText)
        this.txtFormatoFecha = view.findViewById(R.id.txtFormatoFecha);

        //btnEliminarBD(Button)
        this.btnEliminarBD = view.findViewById(R.id.btnEliminarBD);
        this.btnEliminarBD.setOnClickListener(v -> validarDatosPendientes());
        //radio group rdgGrupo(RadioGroup)
        this.rdgGrupo = view.findViewById(R.id.rdgOpcionBackup);
        this.rdgGrupo.setOnCheckedChangeListener((group, checkedId) -> selectBorrado(checkedId));

        //lista de memorias
        final List<StorageUtils.StorageInfo> listaDeAlmacenamiento = StorageUtils.getStorageList(getActivity().getApplicationContext());
        StorageUtils.StorageInfo lugarDeAlmacenamiento;

        List<String> listaCombo = new ArrayList<>();
        for (int i = 0; i < listaDeAlmacenamiento.size(); i++) {
            lugarDeAlmacenamiento = listaDeAlmacenamiento.get(i);
            String pathAlm = lugarDeAlmacenamiento.path;
            String nombreAlm = lugarDeAlmacenamiento.getDisplayName();
            long espacioLibreMb = SdSpaceManager.getSecondaryExternalAvailableSpaceInMB(pathAlm);

            String opcionAlmacenamiento = nombreAlm + " " + espacioLibreMb + " Mb disponible";
            listaCombo.add(opcionAlmacenamiento);
        }

        //spinner para elegir memoria
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, listaCombo);
        this.spnUbicacionBD = (Spinner) view.findViewById(R.id.cmbOpcionUbicacionBD);
        this.spnUbicacionBD.setAdapter(adapter);

        final int pos = PreferenciaBo.getInstance().getPreferencia(getActivity()).getLugarAlmacenamientoBD();
        spnUbicacionBD.setSelection(pos);

        spnUbicacionBD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK_CANCEL,
                        getString(R.string.moverBD) + listaDeAlmacenamiento.get(position).getDisplayName() + getString(R.string.cerrarAplicacion), getString(R.string.moverBDTitulo), new SimpleDialogFragment.OkCancelListener() {
                            @Override
                            public void onCancelSelected() {
                                spnUbicacionBD.setSelection(pos);
                            }

                            @Override
                            public void onOkSelected() {
                                final ProgressDialogFragment progress = ProgressDialogFragment.newInstance("Copiando, por favor espere");

                                Thread t = new Thread() {
                                    public void run() {
                                        progress.show(getActivity().getSupportFragmentManager(), "");
                                        File dirFuente = new File(Preferencia.getPathSistemaPreferido() + Preferencia.getFolderVgmgema());
                                        File disDestino = new File(listaDeAlmacenamiento.get(position).path + Preferencia.getFolderVgmgema());
                                        PreferenciaBo.getInstance().getPreferencia(getActivity()).setPathSistemaPreferido(listaDeAlmacenamiento.get(position).path);
                                        PreferenciaBo.getInstance().getPreferencia(getActivity()).setLugarAlmacenamientoBD(position);
                                        PreferenciaBo.getInstance().savePreference(getActivity().getApplicationContext());
                                        try {
                                            FileManager.copyDirectoryOneLocationToAnotherLocation(dirFuente, disDestino);
                                            FileManager.deleteRecursive(dirFuente);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        progress.dismiss();
                                        getActivity().finish();
                                        Intent i = getActivity().getPackageManager()
                                                .getLaunchIntentForPackage(getActivity().getPackageName());
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);
                                    }
                                };
                                t.start();
                            }
                        });
                String pathSistPref = Preferencia.getPathSistemaPreferido();
                String fuente = pathSistPref + Preferencia.getFolderVgmgema();
                String destino = (listaDeAlmacenamiento.get(position).path + Preferencia.getFolderVgmgema());
                if (!fuente.equals(destino)) {
                    simpleDialogFragment.show(getFragmentManager(), "");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void selectBorrado(int pos) {
        switch (pos) {
            case R.id.rdbIntervalo:
                rdgSelection = Preferencia.BORRADO_POR_INTERVALO;
                selectXIntervalo(true);
                break;
            case R.id.rdbCantidad:
                rdgSelection = Preferencia.BORRADO_POR_CANTIDAD;
                selectXIntervalo(false);
                break;

        }
        PreferenciaBo.getInstance().getPreferencia(getActivity()).setBorradoBckupPreferido(pos);

    }

    private void selectXIntervalo(boolean isSelect) {
        txtBorradoBckXIntervalo.setEnabled(isSelect);
        txtBorradoBckXCantidad.setEnabled(!isSelect);
        if (isSelect) txtBorradoBckXIntervalo.requestFocus();
        else
            txtBorradoBckXCantidad.requestFocus();

    }

    private void validarDatosPendientes() {

        //Valido si tiene Pedidos / Recibos / NA pendientes
        MovimientoBo movimientoBo = new MovimientoBo(_repoFactory);
        int cantPedidosPendientes = 0;
        int cantRecibosPendientes = 0;
        int cantNoPedidosPendientes = 0;
        try {
            cantPedidosPendientes = movimientoBo.getCantidadPedidosSinEnviar();
        } catch (Exception e) {
            e.getMessage();
        }
        try {
            cantRecibosPendientes = movimientoBo.getCantidadRecibosSinEnviar();
        } catch (Exception e) {
            e.getMessage();
        }
        try {
            cantNoPedidosPendientes = movimientoBo.getCantidadNoPedidosSinEnviar();
        } catch (Exception e) {
            e.getMessage();
        }

        if ((cantPedidosPendientes == 0) &&
                (cantRecibosPendientes == 0) &&
                (cantNoPedidosPendientes == 0)) {
            //No tengo nada pendiente de enviar
            eliminarBd();

        } else {
            //Tengo datos pendientes. Llamo a la otra ventana
            String mensaje = getString(R.string.pedidosPendientes) + " " + cantPedidosPendientes + "\n" +
                    getString(R.string.recibosPendientes) + " " + cantRecibosPendientes + "\n" +
                    getString(R.string.noAtencionesPendientes) + " " + cantNoPedidosPendientes + "\n" + "\n" +
                    getString(R.string.eliminacionBd);
            SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK_CANCEL, mensaje, getString(R.string.datosPendientes), new SimpleDialogFragment.OkCancelListener() {

                @Override
                public void onOkSelected() {
                    eliminarBd();
                }

                @Override
                public void onCancelSelected() {
                }
            });
            simpleDialogFragment.show(getFragmentManager(), TAG_ADMINISTRACION_BD);
        }


    }

    private void eliminarBd() {
        //Hago un backup antes de eliminar la BD
        try {
            _repoFactory.backupDb();
            _repoFactory.closeConnection();


        } catch (Exception e) {
            e.getMessage();
        }
        //Elimino la BD
        String pathDb = Preferencia.getPathDB();
        File file = new File(pathDb);
        if (file.exists()) {
            file.delete();
            if (!file.exists()) {
                getDialogEliminarBD();
            }
        } else {
            getDialogArchivoInexistente();
        }
    }


    private void getDialogEliminarBD() {
        SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK_CANCEL, getString(R.string.eliminacionArchivoBDExitosa), getString(R.string.tituloEliminacionBDExitosa), new SimpleDialogFragment.OkCancelListener() {

            @Override
            public void onOkSelected() {
                sincronizacion();

            }

            @Override
            public void onCancelSelected() {
            }
        });
        simpleDialogFragment.show(getFragmentManager(), TAG_ADMINISTRACION_BD);
    }

    private void getDialogArchivoInexistente() {
        SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK_CANCEL, getString(R.string.tituloError), ErrorManager.ArchivoBDnoExiste, new SimpleDialogFragment.OkListener() {

            @Override
            public void onOkSelected() {
            }
        });
        simpleDialogFragment.show(getFragmentManager(), TAG_ADMINISTRACION_BD);
    }

    private void sincronizacion() {
        if (ubicacion == FrmLogin.EXTRA_UBICACION_MENU) {
            PreferenciaBo.getInstance().getPreferencia().setHomeNavigationByPreferencia(NavigationMenu.HOME_CLIENTE);
        } else if (ubicacion == FrmLogin.EXTRA_UBICACION_INICIO) {
            Intent intent = new Intent(getActivity(), NavigationMenu.class);
            intent.putExtra(NavigationMenu.EXTRA_HOME, NavigationMenu.HOME_SINCRONIZACION);
            startActivity(intent);
        }
        getActivity().finish();
    }

}
