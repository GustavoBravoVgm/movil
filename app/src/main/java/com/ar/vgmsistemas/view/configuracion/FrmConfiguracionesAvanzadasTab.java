package com.ar.vgmsistemas.view.configuracion;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.EmpresaBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.VersionInfoVendedoresList;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.task.sincronizacion.ActualizacionConfiguracionesTask;
import com.ar.vgmsistemas.task.sincronizacion.ActualizacionConfiguracionesTask.ActualizarConfiguracionListener;
import com.ar.vgmsistemas.utils.ItemMenuNames;
import com.ar.vgmsistemas.utils.UpdateManager;
import com.ar.vgmsistemas.view.BaseActivity;
import com.ar.vgmsistemas.view.dialogs.ProgressDialogFragment;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class FrmConfiguracionesAvanzadasTab extends BaseActivity {

    //private FragmentTabHost mTabHost;
    private final String TAG_ADMINISTRACION_BD = "administracion_bd";
    private final String TAG_CONFIGURACION_CONEXION = "configuracion_conexion";
    private final String TAG_CONFIGURACION_REGISTRO_PEDIDOS = "configuracion_reg_ped";
    private final String TAG_CONFIGURACION_LOCALIZACION = "configuracion_localizacion";
    private ProgressDialogFragment progressDialogFragment;

    //DAO
    private RepositoryFactory _repoFactory;

    //implementación con tablelayout y viewpager2
    ConfigViewPagerAdapter mViewAdapterConfig;
    ViewPager2 mViewPagerConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_configuraciones_avanzadas);
        setActionBarTitle(ItemMenuNames.STRING_CONFIGURACION_AVANZADA);

        this._repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);

        //implementación con viewpager
        mViewPagerConfig = findViewById(R.id.pagerConfig);
        mViewAdapterConfig = new ConfigViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        //agrego los fragments que se van  a usar:
        mViewAdapterConfig.addFragment(new FrmConfiguracionConexion()); //fragment de configuracion avanzada
        mViewAdapterConfig.addFragment(new FrmConfiguracionRegistroPedidos());//fragment de configuracion de documento
        mViewAdapterConfig.addFragment(new FrmAdministracionBaseDatos());//fragment de configuracion base datos
        if (PreferenciaBo.getInstance() != null) {
            if (isRegistrarLocalizacion()) {
                mViewAdapterConfig.addFragment(new FrmConfiguracionLocalizacion());//fragment de configuracion geolocalizacion
            }
        }
        //seteo el adapter al viewpager
        mViewPagerConfig.setAdapter(mViewAdapterConfig);
        //obtengo el tabLayout
        TabLayout mTabLayoutConfig = findViewById(R.id.tabLayoutConfig);
        //determino los iconos que voy a usar por tabs
        int[] iconosDeConfig = new int[]{
                R.drawable.ic_perm_scan_wifi_grey600_48dp,// pos:0
                R.drawable.ic_assignment_grey600_48dp,// pos:1
                R.drawable.bd,// pos:2
                R.drawable.ic_location_on_grey600_36dp,// pos:3
        };
        //creo un mediator para gestionar el tab con el viewpager
        new TabLayoutMediator(mTabLayoutConfig, mViewPagerConfig, ((tab, position) -> tab.setIcon(iconosDeConfig[position]))).attach();

        mTabLayoutConfig.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tab.setTag(TAG_CONFIGURACION_CONEXION);
                    case 1:
                        tab.setTag(TAG_CONFIGURACION_REGISTRO_PEDIDOS);
                    case 2:
                        tab.setTag(TAG_ADMINISTRACION_BD);
                    case 3:
                        tab.setTag(TAG_CONFIGURACION_LOCALIZACION);
                    default:
                        tab.setTag(TAG_CONFIGURACION_CONEXION);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Fragment fragment;
                switch (tab.getPosition()) {
                    case 0:
                        tab.setTag(TAG_CONFIGURACION_CONEXION);
                        fragment = getSupportFragmentManager().findFragmentByTag(TAG_CONFIGURACION_CONEXION);
                        if (fragment != null) fragment.onPause();
                    case 1:
                        tab.setTag(TAG_CONFIGURACION_REGISTRO_PEDIDOS);
                        fragment = getSupportFragmentManager().findFragmentByTag(TAG_CONFIGURACION_CONEXION);
                        if (fragment != null) fragment.onPause();
                    case 2:
                        tab.setTag(TAG_ADMINISTRACION_BD);
                        fragment = getSupportFragmentManager().findFragmentByTag(TAG_CONFIGURACION_CONEXION);
                        if (fragment != null) fragment.onPause();
                    case 3:
                        tab.setTag(TAG_CONFIGURACION_LOCALIZACION);
                        fragment = getSupportFragmentManager().findFragmentByTag(TAG_CONFIGURACION_CONEXION);
                        if (fragment != null) fragment.onPause();
                    default:
                        tab.setTag(TAG_CONFIGURACION_CONEXION);
                        fragment = getSupportFragmentManager().findFragmentByTag(TAG_CONFIGURACION_CONEXION);
                        if (fragment != null) fragment.onPause();
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void mostrarToast(Context context, String msj) {
        // Inflar el layout personalizado de Snackbar
        View toastLayout = LayoutInflater.from(this).inflate(R.layout.layout_snackbar, null);

        // Obtener referencias a las vistas dentro del layout personalizado
        ImageView iconView = toastLayout.findViewById(R.id.icon);
        TextView textView = toastLayout.findViewById(R.id.text);

        // Establecer el texto y el icono
        textView.setText(msj);
        iconView.setImageResource(R.drawable.app_icon_newtransp); // Cambia esto al recurso de tu icono

        // Crear y mostrar el Toast personalizado
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastLayout);
        toast.show();
    }

    private boolean isRegistrarLocalizacion() {
        boolean isRegistrarLocalizacion = false;
        EmpresaBo _empresaBo = new EmpresaBo(_repoFactory);
        if (_repoFactory.dataBaseExists()) {
            try {
                if (_empresaBo.existsTableEmpresas()) {
                    isRegistrarLocalizacion = _empresaBo.isRegistrarLocalizacion();
                }
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return isRegistrarLocalizacion;
    }

    private void traerConfiguraciones() {
        //mTabHost.setCurrentTabByTag(TAG_CONFIGURACION_CONEXION);
        UpdateManager updateManager = new UpdateManager(getApplicationContext());
        progressDialogFragment = ProgressDialogFragment.newInstance(getString(R.string.msjDescargarConfiguraciones));
        progressDialogFragment.show(getSupportFragmentManager(), "progressDialog");
        try {
            updateManager.resguardarVersion();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ActualizacionConfiguracionesTask task = new ActualizacionConfiguracionesTask(getApplicationContext(), ActualizacionConfiguracionesTask.CONFIGURACION_AVANZADA, getActualizarConfiguracionListener(getApplicationContext()));
        task.execute((Void) null);
    }

    private ActualizarConfiguracionListener getActualizarConfiguracionListener(final Context context) {
        return new ActualizarConfiguracionListener() {

            @Override
            public void onError(String error, boolean restaurarApk) {
                progressDialogFragment.dismiss();
                SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, error, getString(R.string.tituloError),
                        (SimpleDialogFragment.OkListener) () -> {

                        });
                getSupportFragmentManager().beginTransaction().add(simpleDialogFragment, "tag").commitAllowingStateLoss();
            }

            @Override
            public void onDone(VersionInfoVendedoresList listadoVendedoresInfo) {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_CONFIGURACION_CONEXION);
                if (fragment != null) fragment.onStart();
                progressDialogFragment.dismiss();
                Toast.makeText(context, getString(R.string.msjConfiguracionesActualizadas), Toast.LENGTH_SHORT).show();
            }
        };
    }


    @Override
    protected void onStop() {
        super.onStop();
        PreferenciaBo.getInstance().savePreference(this);
        Toast toast = Toast.makeText(this, "Configuraciones guardadas", Toast.LENGTH_LONG);
        toast.show();
        //mostrarToast(this, getString(R.string.msjConfiguracionesGuardadas));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mn_actualizar_configuraciones_conexion, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_CONFIGURACION_CONEXION);
        if (fragment == null)
            getSupportFragmentManager().findFragmentByTag(TAG_CONFIGURACION_REGISTRO_PEDIDOS);
        if (fragment == null) getSupportFragmentManager().findFragmentByTag(TAG_ADMINISTRACION_BD);
        if (fragment == null)
            getSupportFragmentManager().findFragmentByTag(TAG_CONFIGURACION_LOCALIZACION);

        if (fragment != null) fragment.onPause();
        PreferenciaBo.getInstance().savePreference(this);
        if (item.getItemId() == R.id.mni_actualizar) {
            SimpleDialogFragment simpleDialogFragment = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK,
                    getString(R.string.msjConfirmacionActualizacionConfig), getString(R.string.tituloConfirmar),
                    (SimpleDialogFragment.OkListener) this::traerConfiguraciones);
            simpleDialogFragment.show(getSupportFragmentManager(), "");
        }

        return super.onOptionsItemSelected(item);
    }
}
