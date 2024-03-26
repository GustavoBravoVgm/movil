package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IEmpresaDao;
import com.ar.vgmsistemas.database.dao.entity.EmpresaBd;
import com.ar.vgmsistemas.entity.Empresa;
import com.ar.vgmsistemas.repository.IEmpresaRepository;

import java.util.ArrayList;
import java.util.List;

public class EmpresaRepositoryImpl implements IEmpresaRepository {
    // DAO
    private IEmpresaDao _empresaDao;

    public EmpresaRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._empresaDao = db.empresaDao();
        }
    }

    @Override
    public List<Empresa> recoveryAll() throws Exception {
        List<Empresa> empresas = new ArrayList<>();
        if (_empresaDao != null) {
            List<EmpresaBd> listadoEmpresas = _empresaDao.recoveryAll();
            if (!listadoEmpresas.isEmpty()) {
                for (EmpresaBd item : listadoEmpresas) {
                    empresas.add(mappingToDto(item));
                }
            }
        }
        return empresas;
    }

    @Override
    public void update(Empresa empresa) throws Exception {
    }

    @Override
    public void delete(Empresa entity) throws Exception {
    }

    @Override
    public void delete(Long id) throws Exception {
    }

    @Override
    public Long create(Empresa entity) throws Exception {
        return null;
    }

    @Override
    public Empresa recoveryByID(Long id) throws Exception {
        return null;
    }

    @Override
    public boolean existsTable() {
        return true;
    }

    public Empresa mappingToDto(EmpresaBd empresaBd) {
        Empresa empresa = new Empresa();
        empresa.setId(empresaBd.getId());
        empresa.setNombreEmpresa(empresaBd.getNombreEmpresa());
        empresa.setDocumentoAnticipo(empresaBd.getDocumentoAnticipo());
        empresa.setLetraAnticipo(empresaBd.getLetraAnticipo());
        empresa.setNumeroCuotaAnticipo(empresaBd.getNumeroCuotaAnticipo());
        empresa.setTipoCobranza(empresaBd.getTipoCobranza());
        empresa.setDocumentoEgreso(empresaBd.getDocumentoEgreso());
        empresa.setIsDescuentoActivo(empresaBd.getIsDescuentoActivo());
        empresa.setIdListaDefecto(empresaBd.getIdListaDefecto());
        empresa.setIdPostalDefecto(empresaBd.getIdPostalDefecto());
        empresa.setRegistrarLocalizacion(empresaBd.getSnRegistrarLocalizacion() != null && empresaBd.getSnRegistrarLocalizacion().equalsIgnoreCase(Empresa.SI));
        empresa.setMontoMinimoFactura(empresaBd.getMontoMinimoFactura());
        empresa.setMontoACubrirParaDescuento(empresaBd.getMontoACubrirParaDescuento());
        empresa.setTasaMaximaArticulosCriticos(empresaBd.getTasaMaximaArticulosCriticos());
        empresa.setSegundosTolerancia(empresaBd.getSegundosTolerancia());
        empresa.setTasaIvaNoCategorizado(empresaBd.getTasaIvaNoCategorizado());
        empresa.setMultiEmpresa(empresaBd.getSnMultiEmpresa() != null && empresaBd.getSnMultiEmpresa().equalsIgnoreCase(Empresa.SI));
        empresa.setTipoEmpresa(empresaBd.getTipoEmpresa());
        empresa.setManejoTurno(empresaBd.getSnManejoTurno() != null && empresaBd.getSnManejoTurno().equalsIgnoreCase(Empresa.SI));
        empresa.setControlLimiteDisp(empresaBd.getSnControlLimiteDisp() != null && empresaBd.getSnControlLimiteDisp().equalsIgnoreCase(Empresa.SI));
        empresa.setReciboProvisorio(empresaBd.getSnReciboProvisorios() != null && empresaBd.getSnReciboProvisorios().equalsIgnoreCase(Empresa.SI));
        empresa.setSnMovilReciboDto(empresaBd.getSnMovilReciboDto());
        empresa.setSnClienteUnico(empresaBd.getSnClienteUnico());
        empresa.setAnticipoHabilitado(empresaBd.isAnticipoHabilitado());
        empresa.setSnSumIvaReporteMovil(empresaBd.getSnSumIvaReporteMovil());
        empresa.setCategoriasEmpRepartidor(empresaBd.getCategoriasEmpRepartidor());
        empresa.setSnImprimirIdEmpresa(empresaBd.getSnImprimirIdEmpresa());
        empresa.setSnImpResumenCobranza(empresaBd.getSnImpResumenCobranza());
        empresa.setSnCatalogo(empresaBd.getSnCatalogo());
        empresa.setDropboxToken(empresaBd.getDropboxToken());
        empresa.setDropboxAppName(empresaBd.getDropboxAppName());
        empresa.setSnModifCdMovil(empresaBd.getSnModifCdMovil());
        empresa.setTiMetodoImpInt(empresaBd.getTiMetodoImpInt());
        empresa.setSnComboEspecialPorVendedor(empresaBd.getSnComboEspecialPorVendedor());
        empresa.setPrecioMargenToteranciaMovil(empresaBd.getPrecioMargenToteranciaMovil() == null
                                            ? 0.1D
                                            :empresaBd.getPrecioMargenToteranciaMovil());
        empresa.setSnControlaReciboBn(empresaBd.getSnControlaReciboBn() == null
                ? "N"
                : empresaBd.getSnControlaReciboBn());
        return empresa;
    }
}
