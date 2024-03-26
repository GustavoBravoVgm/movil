package com.ar.vgmsistemas.repository.implementation;

import com.ar.vgmsistemas.database.AppDataBase;
import com.ar.vgmsistemas.database.dao.IPlanCuentaDao;
import com.ar.vgmsistemas.database.dao.entity.PlanCuentaBd;
import com.ar.vgmsistemas.entity.Documento;
import com.ar.vgmsistemas.entity.PlanCuenta;
import com.ar.vgmsistemas.repository.IPlanCuentaRepository;

import java.util.ArrayList;
import java.util.List;

public class PlanCuentaRepositoryImpl implements IPlanCuentaRepository {
    private IPlanCuentaDao _planCuentaDao;

    public PlanCuentaRepositoryImpl(AppDataBase db) {
        if (db != null) {
            this._planCuentaDao = db.planCuentaDao();
        }
    }

    public List<PlanCuenta> recoveryByDocumento(Documento documento) throws Exception {
        List<PlanCuentaBd> listadoPlanCuentaBd = _planCuentaDao.recoveryByDocumento(documento.getCategoriaPlanCuenta());
        List<PlanCuenta> planesDeCuenta = new ArrayList<>();
        if (!listadoPlanCuentaBd.isEmpty()) {
            for (PlanCuentaBd item : listadoPlanCuentaBd) {
                planesDeCuenta.add(mappingToDto(item));
            }
        }
        return planesDeCuenta;
    }

    @Override
    public List<PlanCuenta> recoveryForEgreso() throws Exception {
        List<PlanCuentaBd> listadoPlanCuentaBd = _planCuentaDao.recoveryForEgreso();
        List<PlanCuenta> planesDeCuenta = new ArrayList<>();
        if (!listadoPlanCuentaBd.isEmpty()) {
            for (PlanCuentaBd item : listadoPlanCuentaBd) {
                planesDeCuenta.add(mappingToDto(item));
            }
        }
        return planesDeCuenta;
    }

    public Integer create(PlanCuenta entity) throws Exception {
        return null;
    }

    public PlanCuenta recoveryByID(Integer id) throws Exception {
        return mappingToDto(_planCuentaDao.recoveryByID(id));
    }

    public List<PlanCuenta> recoveryAll() throws Exception {
        return null;
    }

    public void update(PlanCuenta entity) throws Exception {

    }

    public void delete(PlanCuenta entity) throws Exception {

    }

    public void delete(Integer id) throws Exception {
    }

    @Override
    public PlanCuenta mappingToDto(PlanCuentaBd planCuentaBd) {
        PlanCuenta planCuenta = new PlanCuenta();
        if (planCuentaBd != null) {
            planCuenta.setId(planCuentaBd.getId());
            planCuenta.setCategoria(planCuentaBd.getCategoria());
            planCuenta.setDescripcion(planCuentaBd.getDescripcion());
            planCuenta.setSnEgresoMovil(planCuentaBd.getSnEgresoMovil());
        }
        return planCuenta;
    }
}
