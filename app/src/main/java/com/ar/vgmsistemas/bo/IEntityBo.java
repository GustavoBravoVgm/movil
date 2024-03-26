package com.ar.vgmsistemas.bo;

import java.util.List;

public interface IEntityBo<T> {
    List<T> recoveryAllEntities() throws Exception;
}
