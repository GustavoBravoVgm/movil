package com.ar.vgmsistemas.view.menu;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.entity.VisibleItemMenu;

import java.util.List;

public class ItemMenuAdapter extends ArrayAdapter<VisibleItemMenu> {

    private List<VisibleItemMenu> mListItemMenus;
    private int mPosSelected;

    @Override
    public VisibleItemMenu getItem(int position) {
        // TODO Auto-generated method stub
        return mListItemMenus.get(position);
    }

    public ItemMenuAdapter(Context context, List<VisibleItemMenu> itemsMenu, int posSelected) {
        super(context, R.layout.lyt_item_menu, itemsMenu);
        mListItemMenus = itemsMenu;
        mPosSelected = posSelected;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mListItemMenus.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lyt_item_menu, null);

            holder = new ViewHolder();
            holder.llItem = (LinearLayout) convertView.findViewById(R.id.ll_item_menu);
            holder.ivItem = (ImageView) convertView.findViewById(R.id.ivItem);
            holder.tvItem = (TextView) convertView.findViewById(R.id.tvItem);
            holder.ivOpen = (ImageView) convertView.findViewById(R.id.ivSubItem);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        VisibleItemMenu currentItemMenu = mListItemMenus.get(position);

        holder.ivItem.setImageResource(currentItemMenu.getImageResource());
        holder.tvItem.setText(currentItemMenu.getItemName());
        if (position == mPosSelected && !currentItemMenu.haveSubItems()) {
            holder.tvItem.setTypeface(null, Typeface.BOLD);
        } else {
            holder.tvItem.setTypeface(null, Typeface.NORMAL);
        }
        if (currentItemMenu.haveSubItems()) {
            holder.ivOpen.setVisibility(View.VISIBLE);
            if (currentItemMenu.isOpen()) {
                holder.ivOpen.setImageResource(R.drawable.ic_find_previous_holo_light);
            } else {
                holder.ivOpen.setImageResource(R.drawable.ic_find_next_holo_light);
            }
        } else {
            holder.ivOpen.setVisibility(View.GONE);
        }
        // si el item actual tiene padre (es un subItem)
        if (currentItemMenu.getFather() != null) {// y el padre esta abierto entonces lo muestro
            if (mListItemMenus.get(getPosFather(currentItemMenu.getFather())).isOpen()) {
                holder.llItem.setVisibility(View.VISIBLE);
            } else { // sino lo oculto
                holder.llItem.setVisibility(View.INVISIBLE);
            }
        } else {

        }

        return convertView;
    }

    private int getPosFather(int idFather) {
        int i = 0;
        for (VisibleItemMenu itemMenu : mListItemMenus) {
            if (itemMenu.getPosItem() == idFather) {
                return i;
            }
            i++;
        }
        return 0;
    }

    public void setOpenClose(int pos) {
        mListItemMenus.get(pos).setOpen(!mListItemMenus.get(pos).isOpen());
        notifyDataSetChanged();
    }

    public void setPosSelected(int posSelected) {
        mPosSelected = posSelected;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        LinearLayout llItem;
        ImageView ivItem;
        TextView tvItem;
        ImageView ivOpen;

    }


}
