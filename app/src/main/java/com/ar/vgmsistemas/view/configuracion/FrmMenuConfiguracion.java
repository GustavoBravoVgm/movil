package com.ar.vgmsistemas.view.configuracion;

import android.content.Intent;
import android.os.Bundle;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.view.FrmLogin;
import com.ar.vgmsistemas.view.ListBaseActivity;
import com.ar.vgmsistemas.view.extend.MenuItem;
import com.ar.vgmsistemas.view.extend.MenuItemAdapter;
import com.ar.vgmsistemas.view.sincronizacion.FrmEstadoConexion;

import java.util.ArrayList;
import java.util.List;

public class FrmMenuConfiguracion extends ListBaseActivity {

    //Modos
    //private static final int MODO_ADMINISTRACION_LOGIN = 0;
    public static final int MODO_CONFIGURACION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();
    }

    private void menuConfiguracionOnItemClick(int position) {
        switch (position) {
            case 0:
                Intent frmConfiguracionEstandar = new Intent(this, FrmConfiguracionEstandar.class);
                startActivity(frmConfiguracionEstandar);
                break;
            case 1:

                //Solicito Clave, que es el numero de version instalada

                Intent frmLogin = new Intent(this, FrmLogin.class);
                frmLogin.putExtra("Modo", MODO_CONFIGURACION);
                startActivity(frmLogin);
                break;

            case 2:
                Intent intent = new Intent(this, FrmEstadoConexion.class);
                startActivity(intent);
                break;
        }
    }


    private void initComponents() {
        //MenuItems
        List<MenuItem> menues = new ArrayList<>();
        menues.add(new MenuItem("Configuracion basica", R.drawable.ic_config));
        menues.add(new MenuItem("Configuracion avanzada", R.drawable.ic_config));
        menues.add(new MenuItem("Ver estado de la conexion", R.drawable.ic_config));

        setListAdapter(new MenuItemAdapter(this, R.layout.lyt_menu_principal_item, menues/*, configuracion*/));

        //ListViewOnItemClick
        this.getListView().setOnItemClickListener((parent, view, position, id) -> menuConfiguracionOnItemClick(position));
    }
}
