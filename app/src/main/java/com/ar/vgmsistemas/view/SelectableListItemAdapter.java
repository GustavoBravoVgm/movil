package com.ar.vgmsistemas.view;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.ar.vgmsistemas.R;

import java.util.ArrayList;
import java.util.List;

public abstract class SelectableListItemAdapter<T> extends ListItemAdapter<T> {

    private SparseBooleanArray miSelectedItems;
    private boolean select = false;

    public SelectableListItemAdapter(Context context, int textViewResourceId, List<T> objects) {
        super(context, textViewResourceId, objects);
        miSelectedItems = new SparseBooleanArray();
    }


    public void toggleSelection(int position) {
        selectView(position, !miSelectedItems.get(position));

    }

    public boolean isSelectAll() {
        return select;
    }

    public void setSelectAll(boolean select) {
        this.select = select;
    }

    @Override
    public void remove(T object) {
        removeItem(items, object);
        removeItem(allItems, object);

        notifyDataSetChanged();
    }

    private void removeItem(List<T> objects, T objectToRemove) {
        int i = 0;
        for (T object : objects) {
            if (object.equals(objectToRemove)) {
                objects.remove(i);
                break;
            }
            i++;
        }
    }

    public void setBackground(int position, View convertView) {
        if (miSelectedItems.get(position)) {
            convertView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_item_selected));
        } else {
            //convertView.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.shape_item_normal));
            convertView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_item_normal));
        }
    }

    public void selectAllViews() {
        miSelectedItems.clear();
        for (int i = 0; i < items.size(); i++) {
            miSelectedItems.put(i, true);
        }

        select = true;
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean selected) {
        if (selected) {
            miSelectedItems.put(position, selected);
            if (miSelectedItems.size() == items.size()) {
                select = true;
            }

        } else {
            select = false;
            miSelectedItems.delete(position);
        }
        notifyDataSetChanged();
    }

    public void removeSelection() {
        miSelectedItems = new SparseBooleanArray();
        select = false;
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return miSelectedItems.size();
    }

    public void setSelectedItems(SparseBooleanArray miSelectedItems) {
        this.miSelectedItems = miSelectedItems;
        notifyDataSetChanged();
    }

    public SparseBooleanArray getSelectedItems() {
        return miSelectedItems;
    }

    public void update(List<T> objects) {
        items = objects;
        notifyDataSetChanged();
    }

    public void removeSelected() {
        for (int i = 0; i < miSelectedItems.size(); i++) {
            int key = miSelectedItems.keyAt(i);
            T object = items.get(key - i);
            remove(object); // si tiene filtro lo borro

        }
        notifyDataSetChanged();
    }

    public List<T> getSelected() {
        List<T> objects = new ArrayList<>();
        for (int i = 0; i < miSelectedItems.size(); i++) {
            int key = miSelectedItems.keyAt(i);
            if (miSelectedItems.get(key)) {
                objects.add(items.get(key));

            }
        }
        return objects;
    }

    public void deselectView(int position) {
        miSelectedItems.delete(position);
        notifyDataSetChanged();
    }

    public boolean isSelected(int position) {
        return miSelectedItems.get(position);
    }


}
