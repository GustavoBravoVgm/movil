package com.ar.vgmsistemas.entity;

import java.util.List;

public class VersionInfoVendedoresList {
    private List<VersionInfoVendedor> versionInfoVendedores;

    public List<VersionInfoVendedor> getListaVendedoresInfo() {
        return versionInfoVendedores;
    }

    public void setListaVendedoresInfo(List<VersionInfoVendedor> listaVendedoresInfo) {
        this.versionInfoVendedores = listaVendedoresInfo;
    }
}
