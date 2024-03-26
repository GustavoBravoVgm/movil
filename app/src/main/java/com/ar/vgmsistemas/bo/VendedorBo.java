package com.ar.vgmsistemas.bo;

import android.content.Context;

import com.ar.vgmsistemas.entity.CantidadesMovimiento;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.SecuenciaRuteo;
import com.ar.vgmsistemas.entity.Vendedor;
import com.ar.vgmsistemas.repository.ISecuenciaRuteoRepository;
import com.ar.vgmsistemas.repository.IVendedorRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.utils.Formatter;

import java.util.Date;
import java.util.List;

public class VendedorBo {

    private final IVendedorRepository _vendedorRepository;
    private final ISecuenciaRuteoRepository _secuenciaRuteoRepository;
    private final RepositoryFactory _repoFactory;

    private static Vendedor _vendedor;

    public VendedorBo(RepositoryFactory repoFactory) {
        this._repoFactory = repoFactory;
        this._vendedorRepository = repoFactory.getVendedorRepository();
        this._secuenciaRuteoRepository = repoFactory.getSecuenciaRuteoRepository();
    }

    public LoginEnum recoveryByLogin(String nombreUsuario, String password, Context context) throws Exception {
        //Usuario y Contrasenia de Administrador
        String adminUser = PreferenciaBo.getInstance().getPreferencia().getAdminUser();
        String adminPassword = String.valueOf(getPasswordAdmin(context));


        if (nombreUsuario.equals(adminUser) && password.equals(adminPassword)) {
            //ADMINISTRADOR
            return LoginEnum.ADMINISTRADOR;
        } else {
            if (validateVendedor(nombreUsuario)) {
                _vendedor = _vendedorRepository.recoveryByLogin(nombreUsuario, password);
                if (_vendedor != null && _vendedor.getIdVendedor() > 0) {
                    return LoginEnum.CORRECTO;
                } else {
                    return LoginEnum.INCORRECTO;
                }
            } else {
                return LoginEnum.DIFERENTE;
            }
        }
    }

    private int getPasswordAdmin(Context context) throws Exception {
        int password = 0;
        String passwordAdmin = String.valueOf(
                context.getPackageManager().
                        getPackageInfo(context.getPackageName(), 0).versionCode);

        for (int i = 0; i < passwordAdmin.length(); i++) {

            password += Integer.parseInt(
                    String.valueOf(passwordAdmin.charAt(i)));
        }
        Date myDate = new Date();
        String sFechaAdmin = Formatter.formatDate(myDate);
        int iPassAdmin = 0;
        for (int i = 0; i < sFechaAdmin.length(); i++) {
            String mCaracter = String.valueOf(sFechaAdmin.charAt(i));
            if (!String.valueOf(sFechaAdmin.charAt(i)).equals("/")) {
                iPassAdmin = iPassAdmin +
                        Integer.parseInt(
                                String.valueOf(sFechaAdmin.charAt(i)));
            }
        }
        sFechaAdmin = String.valueOf(iPassAdmin);
        String sPassword = String.valueOf(password);
        String sPassAdmin = sPassword + sFechaAdmin;
        password = Integer.parseInt(sPassAdmin);
        return password;
    }

    public List<SecuenciaRuteo> recoveryRuteoPorDia(int dia) throws Exception {
        int idLocalidad = PreferenciaBo.getInstance().getPreferencia().getFiltroLocalidadCliente();
        int idSucursalCte = PreferenciaBo.getInstance().getPreferencia().getFiltroSucursalCliente();

        List<SecuenciaRuteo> rutas = _secuenciaRuteoRepository.recoveryRutasByDia(dia, idLocalidad, idSucursalCte);

        MovimientoBo movimientoBo = new MovimientoBo(_repoFactory);
        List<CantidadesMovimiento> cantidadesMovimiento = movimientoBo.recoveryCantidadesClientes();

        if (cantidadesMovimiento != null && !cantidadesMovimiento.isEmpty()) {
            //Recorro la lista de movimientos y seteo para los clientes que corresponda
            for (int i = 0; i < cantidadesMovimiento.size(); i++) {

                int idSucursal = cantidadesMovimiento.get(i).getIdSucursal();
                int idCliente = cantidadesMovimiento.get(i).getIdCliente();
                int idComercio = cantidadesMovimiento.get(i).getIdComercio();

                for (int j = 0; j < rutas.size(); j++) {
                    Cliente cliente = rutas.get(j).getCliente();
                    if (cliente.getId().getIdSucursal() == idSucursal &&
                            cliente.getId().getIdCliente() == idCliente &&
                            cliente.getId().getIdComercio() == idComercio) {

                        cliente.setCantidadVentas(cantidadesMovimiento.get(i).getCantidadPedidos());
                        cliente.setCantidadNoPedidos(cantidadesMovimiento.get(i).getCantidadNoAtenciones());

                        break;
                    }
                }
            }
        }
        return rutas;
    }

    /**
     * @return the vendedor
     */
    public static Vendedor getVendedor() {
        int idVendedor = PreferenciaBo.getInstance().getPreferencia().getIdVendedor();
        if (_vendedor == null) {
            _vendedor = new Vendedor();
        }
        _vendedor.setId(idVendedor);
        _vendedor.setIdVendedor(idVendedor);
        return _vendedor;
    }

    public static boolean validateVendedor(String idVendedor) {
        long vendedor = PreferenciaBo.getInstance().getPreferencia().getIdVendedor();
        return idVendedor.equals(vendedor + "");
    }

    public static void setValidVendedor(boolean isValid) {
        PreferenciaBo.getInstance().getPreferencia().setValidVendedor(isValid);
    }

    /**
     * @return devuelve si el vendedor actual es valido
     */
    public static boolean isValidVendedor() {
        return PreferenciaBo.getInstance().getPreferencia().isValidVendedor();
    }

    public Vendedor recoveryById(int idVendedor) {
        Vendedor vendedor = null;
        try {
            vendedor = _vendedorRepository.recoveryByID(idVendedor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vendedor;
    }
}
