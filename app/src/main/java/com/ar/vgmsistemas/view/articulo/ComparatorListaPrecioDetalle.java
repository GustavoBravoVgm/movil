package com.ar.vgmsistemas.view.articulo;

import com.ar.vgmsistemas.entity.ListaPrecioDetalle;
import com.ar.vgmsistemas.utils.ComparatorArticulo;

import java.util.Comparator;

public class ComparatorListaPrecioDetalle {
    class ComparatorPrecioXArticulo implements Comparator<ListaPrecioDetalle> {
        public int compare(ListaPrecioDetalle object1, ListaPrecioDetalle object2) {
            ComparatorArticulo comparatorArticulo = new ComparatorArticulo();
            return comparatorArticulo.compare(object1.getArticulo(), object2.getArticulo());
        }
    }

}
