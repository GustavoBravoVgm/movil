package com.ar.vgmsistemas.view.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.MenuProfileBo;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.VisibleItemMenu;
import com.ar.vgmsistemas.utils.ItemMenuNames;

import java.util.List;

public class NavigationMenu extends AppCompatActivity {
    public static final String EXTRA_HOME = "home";
    public static final int NO_INICIAR = -1;
    public static final int HOME_CLIENTE = 0;
    public static final int HOME_SINCRONIZACION = 1;
    public static final int HOME_ACTUALIZACION = 2;

    private DrawerLayout mDrawerLayout;
    private ExpandableListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private ExpandableAdapter mItemMenuAdapter;
    private VisibleItemMenu currentItemSelected;
    private VisibleItemMenu currentItemShowed;
    private ProgressBar mProgressBar;
    private NavigationListener mListener;
    private int home;
    private boolean cerrarApp = false;

    public Fragment mFragment;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_navigation_menu);
        Bundle b = this.getIntent().getExtras();

        home = b.getInt(EXTRA_HOME, HOME_CLIENTE);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_navigation);
        mDrawerLayout.setFocusableInTouchMode(false);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_fragment);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.abrir, R.string.cancelar) {
            @Override
            public void onDrawerClosed(View drawerView) {
                cerrarApp = false;
                super.onDrawerClosed(drawerView);
                showContent(currentItemSelected);
                if (mListener != null) {
                    mListener.onDrawerClosed();
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setTitle(getResources().getString(R.string.app_name));
                if (mListener != null) {
                    mListener.onDrawerOpened();
                }
            }

        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerList = (ExpandableListView) findViewById(R.id.elv_options_menu);
        mDrawerList.setGroupIndicator(null);
        MenuProfileBo bo = new MenuProfileBo();
        List<VisibleItemMenu> opciones = bo.getValidosMenuPrincipal(this);
        mItemMenuAdapter = new ExpandableAdapter(this, opciones);
        mDrawerList.setAdapter(mItemMenuAdapter);

        mDrawerList.setOnGroupClickListener((expandableListView, view, groupPosition, rowId) -> {
            cerrarApp = false;
            mItemMenuAdapter.setGroupSelected(groupPosition);
            if (!mItemMenuAdapter.getGroup(groupPosition).haveSubItems()) {
                currentItemSelected = mItemMenuAdapter.getGroup(groupPosition);
                closeNavigation();
                mItemMenuAdapter.setPosGroupSelected(groupPosition);
            }

            return false;
        });
        mDrawerList.setOnChildClickListener((parent, view, groupPosition, childPosition, rowId) -> {
            cerrarApp = false;
            currentItemSelected = mItemMenuAdapter.getChild(groupPosition, childPosition);
            closeNavigation();
            mItemMenuAdapter.setPosChildSelected(childPosition, groupPosition);
            return false;
        });
        if (savedInstanceState == null) {
            switchHome();
        }

    }

    private void closeNavigation() {
        new Handler().postDelayed(() -> mDrawerLayout.closeDrawer(mDrawerList), 200);
    }

    private void switchHome() {
        switch (home) {
            case HOME_SINCRONIZACION:
                setHomeFragment(ItemMenuNames.ITEM_SINCRONIZACION);
                break;
            case HOME_ACTUALIZACION:
                setHomeFragment(ItemMenuNames.ITEM_ACERCA_DE);
                break;
            case HOME_CLIENTE:
            default:
                setHomeFragment(ItemMenuNames.ITEM_CLIENTE);
                break;
        }
    }

    @Override
    protected void onResume() {
        if (PreferenciaBo.getInstance().getPreferencia().getHomeNavigationByPreferencia() != NO_INICIAR) {
            if (currentItemShowed != ItemMenuNames.ITEM_SINCRONIZACION) {
                home = PreferenciaBo.getInstance().getPreferencia().getHomeNavigationByPreferencia();
                PreferenciaBo.getInstance().getPreferencia().setHomeNavigationByPreferencia(NO_INICIAR);
                setHomeFragment(ItemMenuNames.ITEM_SINCRONIZACION);
            }
        }
        currentItemSelected = currentItemShowed;
        super.onResume();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
        if (currentItemShowed != null) {
            currentItemSelected = currentItemShowed;
            setTitle(currentItemShowed.getItemName());
        }
    }

    public void setHomeFragment(VisibleItemMenu itemMenu) {
        currentItemShowed = currentItemSelected = itemMenu;
        Fragment fragment = MenuManager.getFragment(currentItemShowed.getPosItem());
        instanciateFragment(fragment, false);
        setTitle(currentItemShowed.getItemName());
        mItemMenuAdapter.setPosGroupSelected(mItemMenuAdapter.getPosGroup(currentItemSelected));
    }

    private void showFragment() {
        try {
            if (!currentItemSelected.haveSubItems()) {
                if (currentItemShowed == null || currentItemSelected.getPosItem() != currentItemShowed.getPosItem()) {
                    currentItemShowed = currentItemSelected;
                    Fragment fragment = MenuManager.getFragment(currentItemSelected.getPosItem());
                    mFragment = fragment;
                    instanciateFragment(fragment, false);
                }
                setTitle(currentItemSelected.getItemName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showContent(VisibleItemMenu itemMenu) {
        if (itemMenu != null)
            if (itemMenu.getType() == VisibleItemMenu.ACTIVITY) {
                showActivity();
            } else {
                showFragment();
            }
    }

    private void showActivity() {
        Intent intent = MenuManager.getIntent(currentItemSelected.getPosItem(), this);
        startActivity(intent);
    }

    /**
     * @param fragment = fragment a instanciar
     * @param title    = titulo actionBar
     */
    @SuppressLint("NewApi")
    public void addFragment(Fragment fragment, String title) {
        instanciateFragment(fragment, true);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        setTitle(title);
    }

    private void instanciateFragment(Fragment fragment, boolean add) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        if (add) {
            ft.add(R.id.fl_content, fragment);
            ft.addToBackStack("asd");
        } else {
            ft.replace(R.id.fl_content, fragment, "nose");
//			ft.addToBackStack("as");
        }
        if (fragment instanceof NavigationListener) {
            mListener = (NavigationListener) fragment;
        } else {
            mListener = null;
        }
        ft.commitAllowingStateLoss();
    }

    /**
     * por defecto reemplaza el fragment cuando lo agrega ademas necesita el tag
     * para poder agregarlo a la pila
     */

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            if (currentItemShowed != null)
                setTitle(currentItemShowed.getItemName());
        } else {
            if (!mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.openDrawer(mDrawerList);
            } else {
                if (cerrarApp) {
                    finish();
                } else {
                    Toast.makeText(this, getString(R.string.msjSalirApp), Toast.LENGTH_SHORT).show();
                    cerrarApp = true;
                }
            }
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        changeVisibilityMenuOptions(menu, !drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    public void changeVisibilityMenuOptions(Menu menu, boolean visible) {
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(visible);
        }
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            // si apretó el home button y ademas tiene un fragment que no es parte del menu no abro el navigation, sino que hago un back en el stack
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
                if (currentItemShowed != null) setTitle(currentItemShowed.getItemName());
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                return true;
            }
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
        super.setTitle(title);
    }

    public void setVisibilityProgress(int visibility) {
        mProgressBar.setVisibility(visibility);
    }

    public interface NavigationListener {
        void onDrawerOpened();

        void onDrawerClosed();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        PreferenciaBo.getInstance().savePreference(this);
    }

    public int getHome() {
        return home;
    }

}
