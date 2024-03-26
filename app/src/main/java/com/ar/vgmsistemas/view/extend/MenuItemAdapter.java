package com.ar.vgmsistemas.view.extend;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ar.vgmsistemas.R;

import java.util.List;

public class MenuItemAdapter extends ArrayAdapter<MenuItem> {

    private List<MenuItem> items;

    //private boolean[] _menues;
    //private Context _context;
    public MenuItemAdapter(Context context, int textViewResourceId, List<MenuItem> objects/*, boolean[] menues*/) {
        super(context, textViewResourceId, objects);
        items = objects;
        //_menues = menues;
        //_context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(this.getContext(), R.layout.lyt_menu_principal_item, null);
            holder = new ViewHolder();
            holder.txtDescripcion = (TextView) convertView.findViewById(R.id.lblMenuItem);
            holder.imgIcono = (ImageView) convertView.findViewById(R.id.imgMenuItem);
            //holder.linearLayout = (LinearLayout)convertView.findViewById(R.id.LinearLayout01);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MenuItem item = items.get(position);

        if (item != null) {
            holder.txtDescripcion.setText(item.getTexto());
            holder.imgIcono.setImageResource(item.getIcono());
        }
        convertView.setFocusable(false);
		
		/*if(_menues[position]){
			holder.txtDescripcion.setEnabled(true);
			//convertView.setClickable(true);
						
		}else{		
			//convertView.setClickable(false);
			holder.txtDescripcion.setEnabled(false);
			//isEnabled(position);
		}	*/
        return convertView;
    }

    private static class ViewHolder {
        ImageView imgIcono;
        TextView txtDescripcion;
        //LinearLayout linearLayout;
    }
	
	/*@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		return _menues[position];
	}*/

}
