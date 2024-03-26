package com.ar.vgmsistemas.bo;

import com.ar.vgmsistemas.entity.Articulo;
import com.ar.vgmsistemas.entity.Cliente;
import com.ar.vgmsistemas.entity.DescuentoProveedor;
import com.ar.vgmsistemas.entity.DescuentoProveedorGeneral;
import com.ar.vgmsistemas.entity.ListaPrecio;
import com.ar.vgmsistemas.entity.key.PkDescuentoProveedor;
import com.ar.vgmsistemas.repository.IDescuentoProveedorGeneralRepository;
import com.ar.vgmsistemas.repository.IDescuentoProveedorRepository;
import com.ar.vgmsistemas.repository.RepositoryFactory;

import java.util.ArrayList;
import java.util.List;

public class DescuentoProveedorBo {
    private final IDescuentoProveedorGeneralRepository _descuentoProveedorGeneralRepository;
    private final IDescuentoProveedorRepository _descuentoProveedorRepository;

    public DescuentoProveedorBo(RepositoryFactory repoFactory) {
        this._descuentoProveedorGeneralRepository = repoFactory.getDescuentoProveedorGeneralRepository();
        this._descuentoProveedorRepository = repoFactory.getDescuentoProveedorRepository();
    }

    public DescuentoProveedor getDescuentoProveedor(Cliente cliente, Articulo articulo, ListaPrecio lista) throws Exception {
        if (lista.getTipoLista() == 1) {
            List<DescuentoProveedorGeneral> descuentosProveedorGeneral = _descuentoProveedorGeneralRepository.recoveryGenerales();
            List<DescuentoProveedor> descuentosProveedor = new ArrayList<>();
            if (descuentosProveedorGeneral != null) {
                for (DescuentoProveedorGeneral item : descuentosProveedorGeneral) {
                    DescuentoProveedor mDtoProveedor = new DescuentoProveedor();
                    PkDescuentoProveedor id = new PkDescuentoProveedor();
                    id.setIdDescuentoProveedor(item.getIdDescuentoProveedor());
                    mDtoProveedor.setId(id);
                    mDtoProveedor.setDescripcionDescuento(item.getDescripcionDescuento());
                    mDtoProveedor.setIdProveedor(item.getIdProveedor());
                    mDtoProveedor.setIdNegocio(item.getIdNegocio());
                    mDtoProveedor.setIdRubro(item.getIdRubro());
                    mDtoProveedor.setIdSubrubro(item.getIdSubrubro());
                    mDtoProveedor.setIdArticulo(item.getIdArticulo());
                    mDtoProveedor.setIdMarca(item.getIdMarca());
                    mDtoProveedor.setTasaDescuento(item.getTasaDescuento());
                    mDtoProveedor.setPrioridad(item.getPrioridad());
                    descuentosProveedor.add(mDtoProveedor);
                }
            }
            List<DescuentoProveedor> list = _descuentoProveedorRepository.recoveryByCliente(cliente.getId());
            if (list != null && !list.isEmpty()) {
                descuentosProveedor.addAll(0, list);
            }
            return obtenerDescuentoProveedor(descuentosProveedor, articulo);
        }
        return null;
    }

    private DescuentoProveedor obtenerDescuentoProveedor(List<DescuentoProveedor> descuentosProveedor, Articulo articuloPedido) {
        if (descuentosProveedor != null) {
            for (DescuentoProveedor descuentoProveedor : descuentosProveedor) {
                if (descuentoProveedor.getIdArticulo() != null) {
                    //Articulo
                    if (descuentoProveedor.getIdArticulo() == articuloPedido.getId()) {
                        return descuentoProveedor;
                    }
                } else {
                    if (descuentoProveedor.getIdProveedor().equals(articuloPedido.getProveedor().getIdProveedor())) {
                        // Subrubro marca
                        if (isSubrubroNotNull(descuentoProveedor) &&
                                descuentoProveedor.getIdMarca() != null &&
                                descuentoProveedor.getIdMarca() == articuloPedido.getMarca().getId()) {
                            if (isSameSubrubroById(descuentoProveedor, articuloPedido)) {
                                return descuentoProveedor;
                            }
                        }

                        // Rubro marca
                        else if (isRubroNotNull(descuentoProveedor) &&
                                descuentoProveedor.getIdMarca() != null &&
                                descuentoProveedor.getIdMarca() == articuloPedido.getMarca().getId()) {
                            if (isSameRubroById(descuentoProveedor, articuloPedido)) {
                                return descuentoProveedor;
                            }
                        }

                        // Negocio marca
                        else if (descuentoProveedor.getIdNegocio() != null &&
                                descuentoProveedor.getIdMarca() != null &&
                                descuentoProveedor.getIdMarca() == articuloPedido.getMarca().getId()) {
                            if (isSameNegocioById(descuentoProveedor, articuloPedido)) {
                                return descuentoProveedor;
                            }
                        }

                        // Proveedor marca
                        else if (descuentoProveedor.getIdMarca() != null &&
                                articuloPedido.getMarca() != null &&
                                descuentoProveedor.getIdMarca() == articuloPedido.getMarca().getId()) {
                            return descuentoProveedor;
                        }


                        // Subrubro
                        if (isSubrubroNotNull(descuentoProveedor) &&
                                descuentoProveedor.getIdMarca() == null) {
                            if (isSameSubrubroById(descuentoProveedor, articuloPedido)) {
                                return descuentoProveedor;
                            }
                        }

                        // Rubro
                        else if (isRubroNotNull(descuentoProveedor) &&
                                descuentoProveedor.getIdMarca() == null) {
                            if (isSameRubroById(descuentoProveedor, articuloPedido)) {
                                return descuentoProveedor;
                            }
                        }

                        // Negocio
                        else if (descuentoProveedor.getIdNegocio() != null &&
                                descuentoProveedor.getIdMarca() == null) {
                            if (isSameNegocioById(descuentoProveedor, articuloPedido)) {
                                return descuentoProveedor;
                            }
                        }

                        // Proveedor
                        else if (descuentoProveedor.getIdMarca() == null) {
                            return descuentoProveedor;
                        }
                    }
                }
            }
        }

        return null;
    }

    private boolean isSubrubroNotNull(DescuentoProveedor descuentoProveedor) {
        return (descuentoProveedor.getIdSubrubro() != null &&
                descuentoProveedor.getIdRubro() != null &&
                descuentoProveedor.getIdNegocio() != null);
    }

    private boolean isRubroNotNull(DescuentoProveedor descuentoProveedor) {
        return (descuentoProveedor.getIdRubro() != null &&
                descuentoProveedor.getIdNegocio() != null);
    }


    private boolean isSameSubrubroById(DescuentoProveedor descuentoProveedor, Articulo articuloPedido) {
        return (descuentoProveedor.getIdSubrubro() == articuloPedido.getSubrubro().getId().getIdSubrubro() &&
                descuentoProveedor.getIdRubro() == articuloPedido.getSubrubro().getId().getIdRubro() &&
                descuentoProveedor.getIdNegocio() == articuloPedido.getSubrubro().getId().getIdNegocio());
    }

    private boolean isSameRubroById(DescuentoProveedor descuentoProveedor, Articulo articuloPedido) {
        return (descuentoProveedor.getIdRubro() == articuloPedido.getSubrubro().getRubro().getId().getIdRubro() &&
                descuentoProveedor.getIdNegocio() == articuloPedido.getSubrubro().getRubro().getId().getIdNegocio());
    }

    private boolean isSameNegocioById(DescuentoProveedor descuentoProveedor, Articulo articuloPedido) {
        return descuentoProveedor.getIdNegocio() == articuloPedido.getSubrubro().getRubro().getId().getIdNegocio();
    }

}
