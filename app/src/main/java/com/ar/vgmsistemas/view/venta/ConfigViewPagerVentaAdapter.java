package com.ar.vgmsistemas.view.venta;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ConfigViewPagerVentaAdapter extends FragmentStateAdapter {
    //arreglo de fragment que voy a meter dentro del viewpager
    ArrayList<Fragment> listadoFragment = new ArrayList<>();
    private Bundle _frmVentaBundle;

    public ConfigViewPagerVentaAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Bundle frmVentaBundle) {
        super(fragmentManager, lifecycle);
        this._frmVentaBundle = frmVentaBundle;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {//new CabeceraVentaFragment() DetalleVentaFragment()
        //devuelvo el fragment segun su posicion en el arreglo
        switch (position) {
            case 0:
                CabeceraVentaFragment cabeceraVentaFragment = (CabeceraVentaFragment) listadoFragment.get(position);
                cabeceraVentaFragment.setArguments(this._frmVentaBundle);
                return cabeceraVentaFragment;
            case 1:
                DetalleVentaFragment detalleVentaFragment = (DetalleVentaFragment) listadoFragment.get(position);
                detalleVentaFragment.setArguments(this._frmVentaBundle);
                return detalleVentaFragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return listadoFragment.size();
    }

    //metodo para agregar fragments al listado
    public void addFragment(Fragment miFragment) {
        this.listadoFragment.add(miFragment);
    }
}