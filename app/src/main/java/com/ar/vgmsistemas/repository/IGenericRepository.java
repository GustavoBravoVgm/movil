package com.ar.vgmsistemas.repository;

import java.io.Serializable;
import java.util.List;

public interface IGenericRepository<T, ID extends Serializable> {

    ID create(T entity) throws Exception;

    T recoveryByID(ID id) throws Exception;

    List<T> recoveryAll() throws Exception;

    void update(T entity) throws Exception;

    void delete(T entity) throws Exception;

    void delete(ID id) throws Exception;

}
