package com.ar.vgmsistemas.bo;

import android.content.Context;

import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.CondicionVenta;
import com.ar.vgmsistemas.entity.Movimiento;
import com.ar.vgmsistemas.entity.Repartidor;
import com.ar.vgmsistemas.entity.Vendedor;
import com.ar.vgmsistemas.entity.key.PkCliente;
import com.ar.vgmsistemas.repository.IClienteRepository;
import com.ar.vgmsistemas.repository.ICuentaCorrienteRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.ws.ClienteWs;

import java.util.ArrayList;
import java.util.List;

public class ClienteBo {
    private static final int AUMENTA_DISPONIBILIDAD = 1;
    private static final int DISMINUYE_DISPONIBILIDAD = 2;

    private static Cliente _clienteSeleccionado;

    private final IClienteRepository _clienteRepository;
    private final ICuentaCorrienteRepository _cuentaCorrienteRepository;

    //BD
    RepositoryFactory _repoFactory;

    public ClienteBo(RepositoryFactory repoFactory) {
        _repoFactory = repoFactory;
        _clienteRepository = _repoFactory.getClienteRepository();
        _cuentaCorrienteRepository = _repoFactory.getCuentaCorrienteRepository();
    }

    public List<Cliente> recoveryAll() throws Exception {
        return _clienteRepository.recoveryAll();
    }

    public List<Cliente> recoverySaldoClientes() throws Exception {
        EmpresaBo empresaBo = new EmpresaBo(_repoFactory);
        List<Cliente> clientes = _clienteRepository.recoveryAllGroupByCteSuc(-1, -1);
        List<Cliente> clientesFiltrados = new ArrayList<>();
        for (Cliente cliente : clientes) {
            double totalSaldoCuentaCorriente = _cuentaCorrienteRepository.getTotalSaldo(cliente, empresaBo.recoveryEmpresa().getSnClienteUnico());
            if (totalSaldoCuentaCorriente != 0) {
                cliente.setTotalSaldoCuentaCorriente(totalSaldoCuentaCorriente);
                clientesFiltrados.add(cliente);
            }
        }
        return clientesFiltrados;
    }

    public Cliente recoveryById(PkCliente id) throws Exception {
        Cliente cliente = _clienteRepository.recoveryByID(id);
        if (cliente != null) {
            CondicionDirscBo condicionDirscBo = new CondicionDirscBo(_repoFactory);
            cliente.setCondicionDirsc(condicionDirscBo.recoveryByCliente(id));
            CondicionRentaBo condicionRentaBo = new CondicionRentaBo(_repoFactory);
            cliente.setCondicionRenta(condicionRentaBo.recoveryByCliente(id));
            Repartidor repartidor;

            RepartidorBo repartidorBo = new RepartidorBo(_repoFactory);
            repartidor = repartidorBo.recoveryByCliente(cliente);

            if (repartidor == null) {
                repartidor = new Repartidor();
                repartidor.setId(PreferenciaBo.getInstance().getPreferencia().getIdRepartidorPorDefecto());
            }

            cliente.setRepartidor(repartidor);
        }
        return cliente;
    }

    public void update(Cliente cliente) throws Exception {
        cliente.setIdMovil(cliente.getId().toString());
        _clienteRepository.update(cliente);

        //Registro el movimiento para que se envie al servidor
        Movimiento movimiento = new Movimiento();
        movimiento.setTabla(Cliente.TABLE);
        movimiento.setIdMovil(cliente.getIdMovil());
        movimiento.setTipo(Movimiento.MODIFICACION);
        movimiento.setIdSucursal(cliente.getId().getIdSucursal());
        movimiento.setIdCliente(cliente.getId().getIdCliente());
        movimiento.setIdComercio(cliente.getId().getIdComercio());

        MovimientoBo movimientoBo = new MovimientoBo(_repoFactory);
        movimientoBo.create(movimiento);
    }


    /**
     * Actualizar limite disponibilidad del cliente, se controla que haya
     * control de limite de disponibildiad y que el tipo de condicion de venta
     * sea cuenta corriente
     *
     * @param cliente        = cliente a actualizar la disponibilidad
     * @param saldo          = se va a restar del limite de disponibilidad
     * @param condicionVenta = condicion de la venta
     * @throws Exception exception general
     */
    public void updateLimiteDisponibilidad(Cliente cliente, double saldo, CondicionVenta condicionVenta) throws Exception {
        CuentaCorrienteBo cuentaCorrienteBo = new CuentaCorrienteBo(_repoFactory);
        if (cuentaCorrienteBo.seControlaCredito(condicionVenta)) {
            double limiteDisponibilidad = cliente.getLimiteDisponibilidad() - saldo;
            cliente.setLimiteDisponibilidad(limiteDisponibilidad);
            _clienteRepository.updateLimiteDisonibilidad(cliente);
        }
    }

    /**
     * Metodo utilizado para cuando se modifica un pedido y se cambia el monto
     * del pedido o se cambia la condicion de venta, es necesario actualizar la
     * disponibilidad del cliente.
     *
     * @param cliente       = cliente de la venta
     * @param saldoNuevo    = nuevo saldo de la venta
     * @param saldoOriginal = saldo original de la venta
     * @throws Exception exception general
     */
    public void updateLimiteDisponibilidad(Cliente cliente, double saldoNuevo, double saldoOriginal,
                                           CondicionVenta condicionVentaOriginal, CondicionVenta condicionVentaNueva) throws Exception {
        CuentaCorrienteBo corrienteBo = new CuentaCorrienteBo(_repoFactory);


        if (corrienteBo.isControlLimiteDisponibilidad()) {
            int cambioDisponibilidad = 0;
            double saldo = 0d;
            //DETERMINO SI AUMENTO O DISMINUYO EL LIMITE DE DISPONIBILIDAD DEL CLIENTE-----------------------
            if (!condicionVentaOriginal.isCuentaCorriente()) {

                if (condicionVentaNueva.isCuentaCorriente()) {//CONTADO --> CUENTA CORRIENTE
                    saldo = saldoNuevo;
                    cambioDisponibilidad = DISMINUYE_DISPONIBILIDAD;
                }
            } else {
                if (!condicionVentaNueva.isCuentaCorriente()) { // CUENTA CORRIENTE --> CONTADO
                    saldo = saldoOriginal;
                    cambioDisponibilidad = AUMENTA_DISPONIBILIDAD;
                } else { //CUENTA CORRIENTE --> CUENTA CORRIENTE
                    if (saldoNuevo > saldoOriginal) { // NUEVO SALDO > SALDO ORIGINAL
                        saldo = saldoNuevo - saldoOriginal;
                        cambioDisponibilidad = DISMINUYE_DISPONIBILIDAD;
                    } else { // NUEVO SALDO < SALDO ORIGINAL
                        saldo = saldoOriginal - saldoNuevo;
                        cambioDisponibilidad = AUMENTA_DISPONIBILIDAD;
                    }
                }
            }
            //---------------------------------------------
            double limiteDisp;
            switch (cambioDisponibilidad) {
                case DISMINUYE_DISPONIBILIDAD:
                    limiteDisp = cliente.getLimiteDisponibilidad() - saldo;
                    cliente.setLimiteDisponibilidad(limiteDisp);
                    _clienteRepository.updateLimiteDisonibilidad(cliente);
                    break;

                case AUMENTA_DISPONIBILIDAD:
                    limiteDisp = cliente.getLimiteDisponibilidad() + saldo;
                    cliente.setLimiteDisponibilidad(limiteDisp);
                    _clienteRepository.updateLimiteDisonibilidad(cliente);
                    break;
            }
        }
    }

    public Vendedor recoveryVendedorCliente(Cliente cliente) throws Exception {
        int id_vendedor = _clienteRepository.recoveryVendedorCliente(cliente);
        Vendedor vendedor = new Vendedor();
        vendedor.setId(id_vendedor);
        return vendedor;
    }

    public List<Cliente> recoveryNoEnviados() throws Exception {
        return _clienteRepository.recoveryNoEnviados();
    }

    public int send(Cliente cliente, Context context) throws Exception {
        //_movimientoDao.updateFechaSincronizacion(cliente);
        ClienteWs clienteWs = new ClienteWs(context);
        return clienteWs.send(cliente);

    }

    public static void setClienteSeleccionado(Cliente clienteSeleccionado) {
        _clienteSeleccionado = clienteSeleccionado;
    }

    public static Cliente getClienteSeleccionado() {
        return _clienteSeleccionado;
    }

    public double getTotalSaldo(List<Cliente> listCuentaCorrientes) {
        double saldoTotal = 0d;
        for (Cliente cliente : listCuentaCorrientes) {
            saldoTotal += cliente.getTotalSaldoCuentaCorriente();
        }
        return saldoTotal;
    }

}
