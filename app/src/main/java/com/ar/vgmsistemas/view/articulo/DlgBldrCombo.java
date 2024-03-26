package com.ar.vgmsistemas.view.articulo;

import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ArticuloBo;
import com.ar.vgmsistemas.bo.PromocionBo;
import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.ListaPrecio;
import com.ar.vgmsistemas.entity.PromocionDetalle;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.utils.Formatter;
import com.ar.vgmsistemas.view.BaseActivity;

import java.util.List;

public class DlgBldrCombo extends BaseActivity {
    private static final String TAG = DlgBldrCombo.class.getCanonicalName();
    private Articulo _promocion;
    private TextView lblPrecioCombo;
    private TextView lblPrecioNormalCombo;
    private TextView lblPorcentajeAhorro;
    //private TextView lblPrecioIVA;
    private TextView lblPrecioImpInterno;
    private ListView lstItemsCombo;
    private ListaPrecio _listaPrecioXDefecto;
    private Context _context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.lyt_promocion_detalle);
        Bundle b = getIntent().getExtras();
        _promocion = (Articulo) b.getSerializable("promocion");
        _listaPrecioXDefecto = (ListaPrecio) b.getSerializable("listaPrecioXDefecto");
        initComponents();
        loadData();
    }

    private void loadData() {
        RepositoryFactory repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
        PromocionBo promocionBo = new PromocionBo(repoFactory);
        ArticuloBo articuloBo = new ArticuloBo(repoFactory);
        List<PromocionDetalle> itemsCombo;

        this.setTitle(_promocion.getDescripcion());
        try {
            // items de la promocion
            itemsCombo = promocionBo.recoveryPromocionItems(_promocion);
            PromocionDetalleAdapter adapter = new PromocionDetalleAdapter(this, itemsCombo);
            this.lstItemsCombo.setAdapter(adapter);

            double precioCombo = 0;
            double precioNormalCombo = 0;
            double prImpInterno = 0;


            for (PromocionDetalle item : itemsCombo) {
                Articulo articulo = item.getArticulo();
                double precioNormal = articuloBo.getPrecio(this._listaPrecioXDefecto, articulo);
                item.setPrecioNormal(precioNormal);

                // Acumulo el precio del item, pero tomando la lista por
                // defecto del cliente. Este acumulado va a ser el precio
                // normal del combo.
                //precioNormalCombo += item.getCantidadRequerida() * precioNormal;
                precioNormalCombo += item.getCantidadComboComun() * precioNormal;
                // Acumulo el precio del item en el combo, este acumulado va
                // a ser el precio del combo
                precioCombo += item.getCantidadComboComun() * item.getPrecio()
                        * (1 + item.getArticulo().getTasaIva());

                //prIVA = prIVA + articulo.getTasaIva() * precioNormal * item.getCantidadComboComun();

                prImpInterno = prImpInterno + articulo.getTasaImpuestoInterno() * item.getCantidadComboComun() * item.getPrecio();
            }
            if (_promocion.getSnKit().equalsIgnoreCase("S") && _promocion.isPromocion()) {
                precioCombo = _promocion.getListaPreciosDetalle().get(0).getPrecioFinal();
            }
            this.lblPrecioCombo.setText(Formatter.formatMoney(precioCombo));

            double porcentajeAhorro = 1 - precioCombo / precioNormalCombo;
            this.lblPrecioNormalCombo.setText(Formatter
                    .formatMoney(precioNormalCombo));
            this.lblPorcentajeAhorro.setText(Formatter
                    .formatPercent(porcentajeAhorro));

            //this.lblPrecioIVA.setText(Formatter.formatMoney((prIVA)));
            this.lblPrecioImpInterno.setText(Formatter.formatMoney(prImpInterno));

        } catch (Exception e) {
            ErrorManager.manageException(TAG, "loadData", e, this._context);
        }

    }

    private void initComponents() {
        // lblPrecioCombo
        this.lblPrecioCombo = findViewById(R.id.lblPrecioComboValue);//(TextView)

        //lblPorcentajeAhorro
        this.lblPorcentajeAhorro = findViewById(R.id.lblPorcentajeAhorroValue);//(TextView)

        // lblPrecioCombo
        this.lblPrecioNormalCombo = findViewById(R.id.lblPrecioNormalValue);//(TextView)

        //lblIVA
        //this.lblPrecioIVA = (TextView) findViewById(R.id.lblIVAValue);

        //lblPrecioImpInterno
        this.lblPrecioImpInterno = findViewById(R.id.lblImpInternoValue);//(TextView)

        // lstDetalleCombo
        this.lstItemsCombo = findViewById(R.id.lstDetalleCombo);//(ListView)
    }


}
