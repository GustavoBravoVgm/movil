package com.ar.vgmsistemas.view.cobranza;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ConfigViewPagerReciboAdapter extends FragmentStateAdapter {
    //arreglo de fragment que voy a meter dentro del viewpager
    ArrayList<Fragment> listadoFragment = new ArrayList<>();
    private Bundle _frmReciboBundle;


    public ConfigViewPagerReciboAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, Bundle frmReciboBundle) {
        super(fragmentManager, lifecycle);
        this._frmReciboBundle = frmReciboBundle;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        //devuelvo el fragment segun su posicion en el arreglo
        switch (position) {
            case 0:
                CabeceraReciboFragment cabeceraReciboFragment = (CabeceraReciboFragment) listadoFragment.get(position);
                cabeceraReciboFragment.setArguments(this._frmReciboBundle);
                return cabeceraReciboFragment;
            case 1:
                DetalleReciboFragment detalleReciboFragment = (DetalleReciboFragment) listadoFragment.get(position);
                detalleReciboFragment.setArguments(this._frmReciboBundle);
                return detalleReciboFragment;
            case 2:
                ReciboPagosFragment reciboPagosFragment = (ReciboPagosFragment) listadoFragment.get(position);
                reciboPagosFragment.setArguments(this._frmReciboBundle);
                return reciboPagosFragment;
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
