package com.ar.vgmsistemas.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ar.vgmsistemas.BuildConfig;
import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.MenuBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.CategoriaRecursoHumano;
import com.ar.vgmsistemas.utils.TipoEmpresaCode;

public class BaseActivity extends AppCompatActivity {
    protected ActionBar mActionBar;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        super.onCreate(savedInstanceState);
        mActionBar = getSupportActionBar();
        try {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (BuildConfig.DEBUG) {
            //LeakCanary.install(getApplication());
        }
    }

    public int getTipoEmpresa() {
        return PreferenciaBo.getInstance().getPreferencia().getTipoEmpresa();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.im_multiEmpresa) {
            MenuBo.changeToOtherApp(getApplicationContext());
            return false;
        }
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return MenuBo.inflateBaseMenuOptions(this, menu, getMenuInflater());
    }

    public void setActionBarTitle(int res) {
        getSupportActionBar().setTitle(getResources().getString(res));
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public boolean isNormalEmpresa() {
        return TipoEmpresaCode.isNormal(getTipoEmpresa());
    }

    public boolean isVendedor() {
        return CategoriaRecursoHumano.isVendedorDeHacienda();
    }

    public boolean isRepartidor() {
        try {
            return CategoriaRecursoHumano.isRepartidor(RepositoryFactory.getRepositoryFactory(
                               getApplicationContext(), RepositoryFactory.ROOM));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

    public Fragment getFragment() {
        return mFragment;
    }

}
