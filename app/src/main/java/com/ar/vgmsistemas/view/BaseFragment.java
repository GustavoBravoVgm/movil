package com.ar.vgmsistemas.view;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.MenuBo;

public class BaseFragment extends Fragment {

    private Menu mMenu;

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        mMenu = menu;
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.im_multiEmpresa) {
            MenuBo.changeToOtherApp(getActivity());
            return false;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCreate(android.os.Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuBo.inflateBaseMenuOptions(getActivity(), menu, inflater);
    }


    public Menu getMenu() {
        return mMenu;
    }
}
