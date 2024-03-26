package com.ar.vgmsistemas.view.reparto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.view.ListBaseActivity;
import com.ar.vgmsistemas.view.extend.MenuItem;
import com.ar.vgmsistemas.view.extend.MenuItemAdapter;
import com.ar.vgmsistemas.view.informes.FrmListadoVenta;

import java.util.ArrayList;
import java.util.List;

public class FrmMenuReparto extends ListBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();
    }

    private void initComponents() {
        List<MenuItem> menues = new ArrayList<MenuItem>();
        menues.add(new MenuItem(this.getString(R.string.mnPedidos), R.drawable.pedidosreparto));
        menues.add(new MenuItem(this.getString(R.string.mnIntegradoDeMercaderia), R.drawable.ic_articulos));

        setListAdapter(new MenuItemAdapter(this, R.layout.lyt_menu_principal_item, menues));

        // ListViewOnClickListener
        this.getListView().setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuPrincipalOnItemClick(parent, view, position, id);
            }
        });
    }

    public void menuPrincipalOnItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Intent listadoPedido = new Intent(this, FrmListadoVenta.class);
                listadoPedido.putExtra(FrmListadoVenta.EXTRA_MODO_VISTA, FrmListadoVenta.MODO_REPARTO);
                startActivity(listadoPedido);
                break;
            case 1:
                Intent articulosIntegrados = new Intent(this, FrmIntegradoMercaderia.class);
                startActivity(articulosIntegrados);
                break;


        }
    }
}
