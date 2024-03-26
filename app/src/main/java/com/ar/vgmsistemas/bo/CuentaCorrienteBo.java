package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.CodigoAutorizacionCobranza;
import com.ar.vgmsistemas.entity.CondicionVenta;
import com.ar.vgmsistemas.entity.CuentaCorriente;
import com.ar.vgmsistemas.entity.Empresa;
import com.ar.vgmsistemas.entity.Venta;
import com.ar.vgmsistemas.repository.ICodigoAutorizacionCobranzaRepository;
import com.ar.vgmsistemas.repository.ICuentaCorrienteRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.ArrayList;
import java.util.List;

public class CuentaCorrienteBo {
    private static List<CuentaCorriente> _cuentasCorrientesImputadas;

    private final ICuentaCorrienteRepository _cuentaCorrienteRepository;
    private final ICodigoAutorizacionCobranzaRepository _codigoAutorizacionCobranzaRepository;

    //BD
    private RepositoryFactory _repoFactory;
    public CuentaCorrienteBo(RepositoryFactory repoFactory) {
        this._repoFactory = repoFactory;
        this._cuentaCorrienteRepository = _repoFactory.getCuentaCorrienteRepository();
        this._codigoAutorizacionCobranzaRepository = _repoFactory.getCodigoAutorizacionCobranzaRepository();
    }

    public List<CuentaCorriente> recoveryAll() throws Exception {
        return _cuentaCorrienteRepository.recoveryAll();
    }

    public List<CuentaCorriente> recoveryByCliente(Cliente cliente) throws Exception {
        return _cuentaCorrienteRepository.recoveryByCliente(cliente);
    }

    public double getTotalSaldo(Cliente cliente, String snClienteUnico) throws Exception {
        return _cuentaCorrienteRepository.getTotalSaldo(cliente, snClienteUnico);
    }

    public boolean tieneCredito(Venta venta, double saldoTotal) throws Exception {

        double limiteDisponibilidad = venta.getCliente().getLimiteDisponibilidad();
        if (seControlaCredito(venta.getCondicionVenta())) {
            return (!(saldoTotal > limiteDisponibilidad)) || isAutorizado(venta);
        }

        return true;
    }

    public boolean seControlaCredito(CondicionVenta condicionVenta) {
        return isControlLimiteDisponibilidad() && condicionVenta.isCuentaCorriente();
    }

    public boolean isControlLimiteDisponibilidad() {
        return PreferenciaBo.getInstance().getPreferencia().getIsControlLimiteDisponibilidad().equals("S");
    }

    public double getTotalSaldoVencido(Cliente cliente, String snClienteUnico) throws Exception {
        return _cuentaCorrienteRepository.getTotalSaldoVencido(cliente, snClienteUnico);
    }

    /*
     * Imputa las cuentas corriente a partir de un monto dado. Elige las cuentas
     * corrientes mas viejas primero.
     */
    public List<CuentaCorriente> getCuentasCorrientesAImputar(List<CuentaCorriente> cuentasCorrientes, double monto) {
        List<CuentaCorriente> cuentasImputadas = new ArrayList<>();
        double montoDisponible = monto;
        for (CuentaCorriente cc : cuentasCorrientes) {
            if (cc.getTotalCuota() <= montoDisponible || montoDisponible > 0) {
                cuentasImputadas.add(cc);
                montoDisponible -= cc.calcularSaldo();
            }
        }
        return cuentasImputadas;
    }

    public static List<CuentaCorriente> getCuentasCorrientesImputadas() {
        return _cuentasCorrientesImputadas;
    }

    public boolean isVentaCuentaCorrienteHabilitada(Cliente cliente) throws Exception {
        boolean ventaHabilitada;
        EmpresaBo empresaBo = new EmpresaBo(_repoFactory);
        Empresa empresa = empresaBo.recoveryEmpresa();
        double saldoVencido = getTotalSaldoVencido(cliente, empresa.getSnClienteUnico());
        double saldoTotal = getTotalSaldo(cliente, empresa.getSnClienteUnico());
        double limiteDisponibilidad = cliente.getLimiteDisponibilidad();

        if (isCobranzaEstricta()) {
            // Si es cobranza estricta, hago los controles
            if (saldoTotal > 0.0d) {
                ventaHabilitada = (saldoVencido <= 0.1d) && (limiteDisponibilidad > 0.0d || !isControlLimiteDisponibilidad());
            } else
                ventaHabilitada = limiteDisponibilidad >= 0.0d || !isControlLimiteDisponibilidad();
            return ventaHabilitada;
        } else {
            return true;
        }
    }

    public boolean isCobranzaEstricta() {
        // Tarea #3198
        String isCobranzaEstricta = PreferenciaBo.getInstance().getPreferencia().getIsCobranzaEstricta();
        return isCobranzaEstricta.equals(Empresa.SI) || isCobranzaEstricta.equals(Empresa.CON_MOTIVO);
    }

    public boolean validarCodigoAutorizacion(String codigo) {
        CodigoAutorizacionCobranza codigoAutorizacion = null;
        try {
            codigoAutorizacion = _codigoAutorizacionCobranzaRepository.recoveryByCodigo(codigo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Si no recupera el codigo significa que ese codigo no fue utilizado
        if (codigoAutorizacion == null) {
            // http://www.teklynx.com/mx/support/faq/faq_133.html
            int longitud = codigo.length();
            if (longitud < 6) {
                return false;
            }

            // Sume los dígitos en posiciones impares, ignorando el dígito
            // verificador.
            int sumaImpares = 0;
            int sumaPares = 0;
            for (int i = 0; i < codigo.length() - 1; i++) {
                int digitoActual = Integer.parseInt(codigo.substring(i, i + 1));
                if (i % 2 == 0) {
                    sumaPares += digitoActual;
                } else {
                    sumaImpares += digitoActual;
                }
            }
            sumaImpares *= 3;
            int sumaTotal = sumaPares + sumaImpares;
            int digitoVerificador = Integer.parseInt(codigo.substring(longitud - 1, longitud));
            return ((sumaTotal + digitoVerificador) % 10 == 0);
        } else {
            return false;
        }
    }

    public long getNumeroAdelanto() throws Exception {
        return _cuentaCorrienteRepository.getNumeroAdelanto();
    }

    public boolean isVentaAutorizada(Venta venta) throws Exception {
        return isAutorizado(venta) || isVentaCuentaCorrienteHabilitada(venta.getCliente());
    }

    public boolean isAutorizado(Venta venta) {
        return (venta.getCodigoAutorizacion() != null && !venta.getCodigoAutorizacion().equals("")) || venta.getMotivoAutorizacion() != null;
    }

    /**
     * Se determina si el comercio tiene código de autorizacion para el ingreso se ctacte al momento
     * de guardar la hoja detalle en ctacte
     */
    public boolean isCtrlCodCuentaCorriente(Cliente cliente) {
        return (cliente != null && !cliente.getCodAutCtaCte().equals(""));
    }

    public boolean mostrarMsjComprobanteAutorizado(CuentaCorriente vtaCtaCte) {
        boolean hayControl = false;
        if (vtaCtaCte != null && vtaCtaCte.getCliente() != null) {
            hayControl = isCtrlCodCuentaCorriente(vtaCtaCte.getCliente());
        }
        if (hayControl) {
            return (vtaCtaCte.getCodAutCtaCte() != null && !vtaCtaCte.getCodAutCtaCte().equals(""));
        }
        return false;
    }
}
