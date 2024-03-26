package com.ar.vgmsistemas.view.configuracion;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ConfigViewPagerAdapter extends FragmentStateAdapter {
    //arreglo de fragment que voy a meter dentro del viewpager
    ArrayList<Fragment> listadoFragment = new ArrayList<>();

    public ConfigViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        //devuelvo el fragment segun su posicion en el arreglo
        return listadoFragment.get(position);
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
