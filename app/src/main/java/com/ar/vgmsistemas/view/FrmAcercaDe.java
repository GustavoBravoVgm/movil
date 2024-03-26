package com.ar.vgmsistemas.view;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.utils.ItemMenuNames;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;
import com.ar.vgmsistemas.view.menu.MenuManager;
import com.ar.vgmsistemas.view.menu.NavigationMenu;

public class FrmAcercaDe extends BaseNavigationFragment {
    private Button btnActualizar;
    private TextView textEnlaceWeb;
    private ImageButton btnFacebookLike;
    private ImageButton btnTwitterFollow;
    private ImageButton btnPlusOne;
    private TextView textVersion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.lyt_acerca_de, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        if (getNavigationMenu().getHome() == NavigationMenu.HOME_ACTUALIZACION) showActualizar();
    }

    @Override
    public void onStart() {
        super.onStart();
        initComponents();

        textEnlaceWeb.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getResources().getString(R.string.lblWeb)));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onPause() {
        getFragmentManager().saveFragmentInstanceState(this);
        super.onPause();
    }

    private void initComponents() {
        this.textEnlaceWeb = (TextView) getView().findViewById(R.id.txtEnlaceWeb);
        this.textVersion = (TextView) getView().findViewById(R.id.txtVersion);
        String version = "";
        try {
            version = getString(R.string.lblVersion) + " " + getActivity().getApplicationContext().getPackageManager().getPackageInfo(getActivity().getApplicationContext().getPackageName(), 0).versionName;
        } catch (NameNotFoundException Nnfe) {
            version = "No se pudo recuperar la version";
        }
        textVersion.setText(version);

        this.btnActualizar = (Button) getView().findViewById(R.id.btnActualizar1);
        this.btnActualizar.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showActualizar();
            }
        });

        this.btnFacebookLike = (ImageButton) getView().findViewById(R.id.ibtnFacebookLike1);
        this.btnFacebookLike.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.facebook.com/112842992120359"));
                startActivity(intent);
                //Intent a=new Intent(getApplicationContext(),WebActivity.class);
                //startActivity(a);
            }
        });

        this.btnTwitterFollow = (ImageButton) getView().findViewById(R.id.ibtnTwitterFollow1);
        this.btnTwitterFollow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://twitter.com/vgmsistemas"));
                startActivity(intent);
            }
        });

        this.btnPlusOne = (ImageButton) getView().findViewById(R.id.ibtnPlusOne1);
        this.btnPlusOne.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://plus.google.com/u/0/105526258688971018301"));
                startActivity(intent);
            }
        });
    }

    private void showActualizar() {
        Fragment fragmentDetalleArticulo = MenuManager.getFragment(ItemMenuNames.POS_ACTUALIZAR);
        getNavigationMenu().addFragment(fragmentDetalleArticulo, ItemMenuNames.STRING_ACTUALIZAR);
		/*getNavigationMenu().addFragment(fragmentDetalleArticulo, ItemMenuNames.STRING_DETALLE_ARTICULO);
		Intent actualizar = new Intent(getActivity(), FrmActualizar.class);
		startActivity(actualizar);*/
    }

}
