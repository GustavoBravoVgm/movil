package com.ar.vgmsistemas.utils;

import com.ar.vgmsistemas.view.extend.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class InsertarItemsValidosMenues extends ArrayList<MenuItem> {
    private List<Integer> _menu;
    private int _contador = 0;

    public InsertarItemsValidosMenues(List<Integer> menu) {
        set_menu(menu);
    }

    @Override
    public boolean add(MenuItem object) {

        if (get_menu().contains(get_contador())) {
            set_contador(get_contador() + 1);
            return super.add(object);
        } else {
            set_contador(get_contador() + 1);
            return false;
        }

    }

    public List<Integer> get_menu() {
        return _menu;
    }

    public void set_menu(List<Integer> _menu) {
        this._menu = _menu;
    }

    public int get_contador() {
        return _contador;
    }

    public void set_contador(int _contador) {
        this._contador = _contador;
    }


}
