package com.ar.vgmsistemas.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.ClienteBo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.ErrorManager;
import com.ar.vgmsistemas.view.dialogs.SimpleDialogFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapPane extends BaseFragment implements OnMapReadyCallback {

    private final static String CLIENTE = "Cliente";

    private GoogleMap mGoogleMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private SupportMapFragment mapFragment;
    private final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private Cliente _cliente;
    private Location _location;
    private static final String TAG = MapPane.class.getCanonicalName();

    //BD
    private RepositoryFactory _repoFactory;

    protected LocationManager locationManager;

    public static MapPane newInstance(Cliente cliente) {
        Bundle b = new Bundle();
        b.putSerializable(CLIENTE, cliente);
        MapPane mapPane = new MapPane();
        mapPane.setArguments(b);
        return mapPane;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        this._cliente = (Cliente) b.getSerializable(CLIENTE);
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(getContext(), "No esta habilitado el GPS, debe habilitarlo para obtener posiciones", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        //inflo el diseño del mapa
        View view = inflater.inflate(R.layout.lyt_map_pane, container, false);

        //obtener una instancia de FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        //inicializar fragmento del mapa
        mapFragment.getMapAsync(this);

        //boton guardar
        Button btnGuardar = view.findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(v -> btnGuardarOnClick());
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void btnGuardarOnClick() {
        try {
            if (_location != null) {
                if (_repoFactory == null) {
                    _repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
                }
                ClienteBo clienteBo = new ClienteBo(_repoFactory);
                this._cliente.setLatitud(_location.getLatitude());
                this._cliente.setLongitud(_location.getLongitude());
                clienteBo.update(this._cliente);
                Toast toast = Toast.makeText(getActivity(), R.string.msjUbicacionClienteGuardada, Toast.LENGTH_LONG);
                toast.show();
            } else {
                SimpleDialogFragment simpleDialog = SimpleDialogFragment.newInstance(SimpleDialogFragment.TYPE_OK, getString(R.string.msjGuardarUbicacion), ErrorManager.NoUbicacion);
                simpleDialog.show(getParentFragmentManager(), "");
            }
        } catch (Exception e) {
            ErrorManager.manageException(TAG, "btnGuardarOnClick", e, getActivity());
        }

    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mGoogleMap = googleMap;
        final LatLng puntoCliente = new LatLng(_cliente.getLatitud(), _cliente.getLongitud());
        if (mGoogleMap != null) {
            mGoogleMap.addMarker(new MarkerOptions().position(puntoCliente).title(CLIENTE));

            // Habilitar la capa de "Mi ubicación" en el mapa
            if (ActivityCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(requireContext(),
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_PERMISSION_REQUEST_CODE);
                requestPermissions();
            }
            //habilito icono de ubicación actual
            mGoogleMap.setMyLocationEnabled(true);
            // Habilitar la orientación del mapa
            mGoogleMap.getUiSettings().setMapToolbarEnabled(true);
            mGoogleMap.getUiSettings().setRotateGesturesEnabled(true);

            // Habilitar la brújula en el mapa
            mGoogleMap.getUiSettings().setCompassEnabled(true);

            //establezco la posición del cliente en caso que tenga una asignada
            if (_cliente.getLatitud() != 0 && _cliente.getLongitud() != 0) {
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(puntoCliente, 15));
            }

            // Agregar un listener al botón "Mi ubicación"
            mGoogleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    // Llamar a la función para centrar el mapa en la ubicación actual
                    getDeviceLocation();
                    return false;
                }
            });
        }
    }

    private void getDeviceLocation() {
        try {
            // Obtener la última ubicación conocida del dispositivo
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                // Mover la cámara del mapa a la ubicación actual del dispositivo
                                LatLng currentLocation = new LatLng(location.getLatitude(),
                                        location.getLongitude());
                                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f));
                                _location = location;
                            } else {
                                // Si la ubicación es nula, mostrar un mensaje de error
                                Toast.makeText(requireContext(), "No se pudo obtener la ubicación actual",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso de ubicación concedido, inicializar el mapa nuevamente
                mapFragment.getMapAsync(this);
            } else {
                // Permiso de ubicación denegado, mostrar un mensaje de error
                Toast.makeText(requireContext(), "Permiso de ubicación denegado",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Método para solicitar permisos
    private void requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Mostrar explicación al usuario si los permisos fueron rechazados anteriormente
            new AlertDialog.Builder(requireContext())
                    .setTitle("Permisos necesarios")
                    .setMessage("Para usar la función de ubicación, necesitamos permisos de ubicación.")
                    .setPositiveButton("Ok", (dialog, which) -> {
                        // Solicitar permisos
                        ActivityCompat.requestPermissions(requireActivity(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        } else {
            // Solicitar permisos
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


}
