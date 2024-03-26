package com.ar.vgmsistemas.view.menu;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.PreferenciaBo;
import com.ar.vgmsistemas.entity.ItemMenu;
import com.ar.vgmsistemas.entity.VisibleItemMenu;

import java.util.List;

public class ExpandableAdapter extends BaseExpandableListAdapter {
    private List<VisibleItemMenu> mListItemMenus;
    private Context mContext;
    private int mPosGroupSelected;
    private int mPosChildSelected;

    public ExpandableAdapter(Context context, List<VisibleItemMenu> listMenus) {
        mListItemMenus = listMenus;
        VisibleItemMenu itemEmpresa = new VisibleItemMenu(null, null, 0, 0, 0, 0, null);
        mListItemMenus.add(0, itemEmpresa);
        mContext = context;
    }

    @Override
    public VisibleItemMenu getChild(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return mListItemMenus.get(groupPosition).getSubItems()[childPosition];
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup groupView) {
        convertView = View.inflate(mContext, R.layout.lyt_subitem_menu, null);

        VisibleItemMenu subItemMenu = mListItemMenus.get(groupPosition).getSubItems()[childPosition];

        TextView tvItemName = (TextView) convertView.findViewById(R.id.tvItem);
        ImageView ivItem = (ImageView) convertView.findViewById(R.id.ivItem);
        LinearLayout lytSubItem = (LinearLayout) convertView.findViewById(R.id.ll_item_menu);
        lytSubItem.setBackgroundColor(mContext.getResources().getColor(R.color.base_color_menu_subitem));
        tvItemName.setText(subItemMenu.getItemName());
        ivItem.setImageResource(subItemMenu.getImageResource());
        if (childPosition == mPosChildSelected && groupPosition == mPosGroupSelected) {
            tvItemName.setTypeface(null, Typeface.BOLD);
            ivItem.setImageResource(subItemMenu.getImageResourceTwo());
        } else {
            tvItemName.setTypeface(null, Typeface.NORMAL);
            ivItem.setImageResource(subItemMenu.getImageResource());
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO Auto-generated method stub
        VisibleItemMenu itemSelected = mListItemMenus.get(groupPosition);
        VisibleItemMenu[] arrayItems = itemSelected.getSubItems();
        int count = (arrayItems == null) ? 0 : arrayItems.length;
        return count;
    }

    @Override
    public VisibleItemMenu getGroup(int groupPosition) {
        // TODO Auto-generated method stub
        return mListItemMenus.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return mListItemMenus.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup groupView) {

        if (groupPosition == 0) {
            convertView = View.inflate(mContext, R.layout.lyt_textview_empresa, null);
            TextView tvItemEmpresaValue = (TextView) convertView.findViewById(R.id.tvItemEmpresa);
            TextView tvItemLegajoValue = (TextView) convertView.findViewById(R.id.tvItemLegajo);
            tvItemLegajoValue.setText(PreferenciaBo.getInstance().getPreferencia().getIdVendedor() + "");
            tvItemLegajoValue.setTypeface(null, Typeface.NORMAL);
            tvItemEmpresaValue.setText(PreferenciaBo.getInstance().getPreferencia(mContext).getNombreEmpresa().trim());
            tvItemEmpresaValue.setTypeface(null, Typeface.NORMAL);
            convertView.setEnabled(false);
            convertView.setClickable(false);
            convertView.setOnClickListener(null);
        } else {
            VisibleItemMenu item = mListItemMenus.get(groupPosition);
            convertView = View.inflate(mContext, R.layout.lyt_item_menu, null);
            TextView tvItem = (TextView) convertView.findViewById(R.id.tvItem);
            ImageView ivItem = (ImageView) convertView.findViewById(R.id.ivItem);
            ImageView ivOpen = (ImageView) convertView.findViewById(R.id.ivSubItem);

            LinearLayout lytItem = (LinearLayout) convertView.findViewById(R.id.ll_item_menu);
            lytItem.setBackgroundColor(mContext.getResources().getColor(R.color.base_color_menu_item));
            if (item.haveSubItems()) {
                ivOpen.setVisibility(View.VISIBLE);
                if (item.isOpen()) {
                    ivOpen.setImageResource(R.drawable.ic_expand_less_white);
                } else {
                    ivOpen.setImageResource(R.drawable.ic_expand_more_white);
                }
            } else {
                ivOpen.setVisibility(View.GONE);
            }
            if (groupPosition == mPosGroupSelected) {
                tvItem.setTypeface(null, Typeface.BOLD);
                ivItem.setImageResource(item.getImageResourceTwo());
            } else {
                tvItem.setTypeface(null, Typeface.NORMAL);
                ivItem.setImageResource(item.getImageResource());
            }
            tvItem.setText(item.getItemName());
            // ivItem.setImageResource(item.getImageResource());
        }
        return convertView;
    }

    public void setGroupSelected(int position) {
        if (mListItemMenus.get(position).haveSubItems()) {
            mListItemMenus.get(position).setOpen(!mListItemMenus.get(position).isOpen());
        }

    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int mPosChildSelected) {
        // TODO Auto-generated method stub
        return true;
    }

    public int getPosGroup(VisibleItemMenu visibleItem) {
        int i = 0;
        for (VisibleItemMenu itemMenu : mListItemMenus) {
            if (itemMenu.getPosItem() == visibleItem.getPosItem())
                break;
            i++;
        }
        return i;
    }

    public void setPosGroupSelected(int posGroup) {
        if (mListItemMenus.get(posGroup).getType() == ItemMenu.FRAGMENT) {
            mPosGroupSelected = posGroup;
            notifyDataSetChanged();
        }

    }

    public void setPosChildSelected(int posChild, int posGroup) {
        if (mListItemMenus.get(posGroup).getSubItems()[posChild].getType() == ItemMenu.FRAGMENT) {
            mPosGroupSelected = posGroup;
            mPosChildSelected = posChild;
            notifyDataSetChanged();
        }

    }
}
