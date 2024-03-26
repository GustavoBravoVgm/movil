package com.ar.vgmsistemas.repository;

import com.ar.vgmsistemas.database.dao.entity.SubrubroBd;
import com.ar.vgmsistemas.entity.Rubro;
import com.ar.vgmsistemas.entity.Subrubro;
import com.ar.vgmsistemas.entity.key.PkSubrubro;

import java.util.List;

public interface ISubrubroRepository extends IGenericRepository<Subrubro, PkSubrubro> {

    List<Subrubro> recoveryByRubro(Rubro rubro) throws Exception;

    Subrubro mappingToDto(SubrubroBd subrubroBd) throws Exception;

    Subrubro mappingToDtoSinRubro(SubrubroBd subrubroBd);
}
