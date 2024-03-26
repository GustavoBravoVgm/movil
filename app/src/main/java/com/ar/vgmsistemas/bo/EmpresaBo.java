package com.ar.vgmsistemas.bo;

import android.content.Context;

import com.ar.vgmsistemas.entity.Empresa;
import com.ar.vgmsistemas.entity.RecursoHumano;
import com.ar.vgmsistemas.gps.UtilGps;
import com.ar.vgmsistemas.repository.IEmpresaRepository;
import com.ar.vgmsistemas.repository.IRecursoHumanoRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.Date;
import java.util.List;

public class EmpresaBo {

    private final IEmpresaRepository _empresaRepository;

    private final IRecursoHumanoRepository _recursoHumanoRepository;

    public EmpresaBo(RepositoryFactory repoFactory) {
        _empresaRepository = repoFactory != null ? repoFactory.getEmpresaRepository() : null;
        _recursoHumanoRepository = repoFactory != null ? repoFactory.getRecursoHumanoRepository() : null;
    }

    public List<Empresa> recoveryAll() throws Exception {
        return _empresaRepository.recoveryAll();
    }

    public Empresa recoveryEmpresa() throws Exception {
        Empresa empresa = new Empresa();
        List<Empresa> empresas = this.recoveryAll();
        for (int i = 0; i < empresas.size(); i++) {
            empresa = empresas.get(i);
        }
        return empresa;
    }

    /**
     * Guarda en preferencias.xml las configuraciones de la tabla Empresas
     *
     * @throws Exception lanza una exception general
     */
    public void updateConfiguracionesPreferencia(Context context) throws Exception {
        Empresa empresa = recoveryEmpresa();
        String tipoCobranza = empresa.getTipoCobranza();
        String isDescuento = empresa.getIsDescuentoActivo();
        String isControlLimiteDisp = (empresa.isControlLimiteDisp()) ? "S" : "N";
        String isRegistrarLocalizacion = "N";
        if (empresa.isRegistrarLocalizacion()) {
            isRegistrarLocalizacion = "S";
        }
        double porcentajeArticulosCriticos = empresa.getTasaMaximaArticulosCriticos();
        double montoMinimoFactura = empresa.getMontoMinimoFactura();
        double montoMinimoDescuentoFactura = empresa.getMontoACubrirParaDescuento();

        RecursoHumano recursoHumanoVendedor = _recursoHumanoRepository.recoveryByID(PreferenciaBo.getInstance().getPreferencia().getIdVendedor());
        Date horaEntradaManana = recursoHumanoVendedor.getHoraEntradaManana();
        Date horaSalidaManana = recursoHumanoVendedor.getHoraSalidaManana();
        Date horaEntradaTarde = recursoHumanoVendedor.getHoraEntradaTarde();
        Date horaSalidaTarde = recursoHumanoVendedor.getHoraSalidaTarde();
        int categoria = recursoHumanoVendedor.getCategoria();
        boolean isValidVendedor = recursoHumanoVendedor.getIsActivo();
        boolean isMultiEmpresa = empresa.isMultiEmpresa();
        Integer tipoEmpresa = empresa.getTipoEmpresa();
        boolean isManejoTurno = empresa.getIsManejoTurno();
        boolean isReciboProvisorio = empresa.isReciboProvisorio();

        //Obtenidos los valores, los guardo en Preferencias
        PreferenciaBo.getInstance().getPreferencia().setIsCobranzaEstricta(tipoCobranza);
        PreferenciaBo.getInstance().getPreferencia().setIsDescuento(isDescuento);
        PreferenciaBo.getInstance().getPreferencia().setIsControlLimiteDisponibilidad(isControlLimiteDisp);
        PreferenciaBo.getInstance().getPreferencia().setIsRegistrarLocalizacion(isRegistrarLocalizacion);
        PreferenciaBo.getInstance().getPreferencia().setPorcentajeArticulosCriticos(porcentajeArticulosCriticos);
        PreferenciaBo.getInstance().getPreferencia().setMontoMinimoFactura(montoMinimoFactura);
        PreferenciaBo.getInstance().getPreferencia().setMontoMinimoDescuentoFactura(montoMinimoDescuentoFactura);
        PreferenciaBo.getInstance().getPreferencia().setSegundosTolerancia(empresa.getSegundosTolerancia());
        PreferenciaBo.getInstance().getPreferencia().setHoraEntradaManana(horaEntradaManana);
        PreferenciaBo.getInstance().getPreferencia().setHoraSalidaManana(horaSalidaManana);
        PreferenciaBo.getInstance().getPreferencia().setHoraEntradaTarde(horaEntradaTarde);
        PreferenciaBo.getInstance().getPreferencia().setHoraSalidaTarde(horaSalidaTarde);
        PreferenciaBo.getInstance().getPreferencia().setValidVendedor(isValidVendedor);
        PreferenciaBo.getInstance().getPreferencia().setMultiEmpresa(isMultiEmpresa);
        PreferenciaBo.getInstance().getPreferencia().setTipoEmpresa(tipoEmpresa);
        PreferenciaBo.getInstance().getPreferencia().setIdCategoria(categoria);
        PreferenciaBo.getInstance().getPreferencia().setManejoTurno(isManejoTurno);
        PreferenciaBo.getInstance().getPreferencia().setNombreEmpresa(recoveryEmpresa().getNombreEmpresa());
        PreferenciaBo.getInstance().getPreferencia().setReciboProvisorio(isReciboProvisorio);
        PreferenciaBo.getInstance().getPreferencia().setSnMovilReciboDto(empresa.getSnMovilReciboDto());
        PreferenciaBo.getInstance().getPreferencia().setCategoriasEmpRepartidor(empresa.getCategoriasEmpRepartidor());
        PreferenciaBo.getInstance().savePreference(context);
    }

    public boolean isRegistrarLocalizacion() throws Exception {
        //Tarea #3198
        String si = "S";
        return PreferenciaBo.getInstance().getPreferencia().getIsRegistrarLocalizacion().equals(si);
    }

    public Double montoMinimoFactura() {
        //Tarea #3198
        return PreferenciaBo.getInstance().getPreferencia().getMontoMinimoFactura();
    }

    public Double montoACubrirParaDescuento() {
        //Tarea #3198
        return PreferenciaBo.getInstance().getPreferencia().getMontoMinimoDescuentoFactura();
    }

    public boolean gpsValido(Context context) throws Exception {
        boolean valido = false;
        if (isRegistrarLocalizacion() && !UtilGps.validateGpsStatus(context)) {
            valido = false;
        } else if
        ((isRegistrarLocalizacion() && UtilGps.validateGpsStatus(context))
                        || (!isRegistrarLocalizacion())) {
            valido = true;
        }
        return valido;
    }

    public boolean existsTableEmpresas() throws Exception {
        return _empresaRepository.existsTable();
    }
}
