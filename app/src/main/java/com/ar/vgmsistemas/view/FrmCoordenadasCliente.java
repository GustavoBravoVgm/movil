package com.ar.vgmsistemas.view;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ClienteBo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.gps.GPSManagement;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;


public class FrmCoordenadasCliente extends BaseActivity {

    private TextView txtLatitud;
    private TextView txtLongitud;

    private Cliente cliente;
    private Location localizacionObtenida = null;

    private final int DIALOG_GUARDAR_UBICACION = 0;

    //BD
    private RepositoryFactory _repoFactory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_obtener_coordenadas);
        setActionBarTitle(R.string.coordenadas);
        Bundle b = this.getIntent().getExtras();
        cliente = (Cliente) b.getSerializable("cliente");

        Object data = getLastNonConfigurationInstance();
        if (data != null) {
            cliente = (Cliente) data;
        }

        initComponents();
        initVar();
    }

    private void initVar() {
        _repoFactory = RepositoryFactory.getRepositoryFactory(getApplicationContext(), RepositoryFactory.ROOM);
        //Llamar a GPSManagement para obtener la ubicacion
        GPSManagement gpsManagement = new GPSManagement(getApplicationContext());
        localizacionObtenida = gpsManagement.getDatosUbicaciones();
        if (localizacionObtenida == null) {
            //Mensaje de error al obtener las coordenadas del cliente
            com.ar.vgmsistemas.view.AlertDialog alert =
                    new com.ar.vgmsistemas.view.AlertDialog(this, getString(R.string.error), this.getString(R.string.msjErrorCoordenadasCliente));
            //new AlertDialog(this, getString(R.string.error),  this.getString(R.string.msjErrorCoordenadasCliente));
            //cambio kren
            alert.setPositiveButton(R.string.btnAceptar, new OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    finish();
                }
            });
            alert.show();
        } else {
            // Seteo las coordenadas del cliente
            // Muestro los valores
            Double latitud = localizacionObtenida.getLatitude();
            Double longitud = localizacionObtenida.getLongitude();

            this.txtLatitud.setText(latitud.toString());
            this.txtLongitud.setText(longitud.toString());

            cliente.setLatitud(latitud);
            cliente.setLongitud(longitud);
        }
    }

    private void initComponents() {
        this.txtLatitud = (TextView) findViewById(R.id.lblLatitudValue);
        this.txtLongitud = (TextView) findViewById(R.id.lblLongitudValue);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showDialog(DIALOG_GUARDAR_UBICACION);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        showDialog(DIALOG_GUARDAR_UBICACION);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DIALOG_GUARDAR_UBICACION:
                dialog = getDialogGuardarUbicacion();
                break;
        }
        return dialog;
    }

    private AlertDialog getDialogGuardarUbicacion() {
        Builder builder = new Builder(this);
        builder.setTitle(R.string.tituloGuardarLocalizacionCliente);
        builder.setMessage(R.string.msjGuardarLocalizacionCliente);
        builder.setPositiveButton(R.string.si, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                guardarLocalizacionCliente();
            }
        });
        builder.setNegativeButton(R.string.no, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        return dialog;
    }

    private void guardarLocalizacionCliente() {
        if (localizacionObtenida != null) {
            if (_repoFactory == null) _repoFactory = RepositoryFactory.getRepositoryFactory(
                    getApplicationContext(), RepositoryFactory.ROOM);
            ClienteBo clienteBo = new ClienteBo(_repoFactory);
            try {
                this.cliente.setLatitud(localizacionObtenida.getLatitude());
                this.cliente.setLongitud(localizacionObtenida.getLongitude());
                clienteBo.update(cliente);
                Toast.makeText(this, "Coordenadas del cliente guardadas", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                ErrorManager.manageException("FrmCoordenadasCliente", "Update", e, this, "Error", "Error al actualizar coordenadas del cliente");
            }
        }
    }
}
