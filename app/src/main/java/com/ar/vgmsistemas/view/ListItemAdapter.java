package com.ar.vgmsistemas.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public abstract class ListItemAdapter<T> extends ArrayAdapter<T> {

    protected List<T> items;
    protected int mCampoBusqueda;

    private String filteredString;
    protected List<T> allItems;
    protected int mCampoFiltro;
    protected int campoOrden;
    protected ViewGroup parent;
    protected T itemSeleccionado;


    public ListItemAdapter(Context context, int textViewResourceId, List<T> objects) {
        super(context, textViewResourceId, objects);
        this.items = ((objects != null) ? objects : new ArrayList<T>());
        this.allItems = this.items;
        filteredString = "";
    }

    public void setItems(List<T> listItems) {
        this.items = allItems = listItems;
        notifyDataSetChanged();
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public abstract Filter getFilter();

    public void setCampoBusqueda(int campoBusqueda) {
        this.mCampoBusqueda = campoBusqueda;
    }

    public int getCampoBusqueda() {
        return mCampoBusqueda;
    }

    public String getFilteredString() {
        return filteredString;
    }

    public void setFilteredString(String filteredString) {
        this.filteredString = filteredString;
    }

    public abstract void sort();


    /**
     * @param campoFiltro the campoFiltro to set
     */
    public void setCampoFiltro(int campoFiltro) {
        this.mCampoFiltro = campoFiltro;
    }

    public int getCampoFiltro() {
        return mCampoFiltro;
    }

    //-------------- selection-------------------


    protected ListView getParent() {
        return (ListView) this.parent;
    }

    protected void setParent(ViewGroup group) {
        this.parent = group;
    }

    public int getCampoOrden() {
        return campoOrden;
    }

    public void setCampoOrden(int campoOrden) {
        this.campoOrden = campoOrden;
    }
}
