package com.ar.vgmsistemas.view.menu;

import android.os.Bundle;
import android.view.Menu;

import com.ar.vgmsistemas.view.BaseFragment;
import com.ar.vgmsistemas.view.menu.NavigationMenu.NavigationListener;

public class BaseNavigationFragment extends BaseFragment implements NavigationListener {

    private NavigationMenu mNavigationMenu;


    @Override
    public void onDrawerOpened() {
        changeVisibilityMenuOptions(getMenu(), false);
    }

    @Override
    public void onDrawerClosed() {
        if (getMenu() != null) {
            changeVisibilityMenuOptions(getMenu(), true);
        }
    }

    public void changeVisibilityMenuOptions(Menu menu, boolean visible) {
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(visible);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (getActivity() instanceof NavigationMenu) {
            mNavigationMenu = (NavigationMenu) getActivity();
        }
        super.onActivityCreated(savedInstanceState);
    }

    public NavigationMenu getNavigationMenu() {
        if (mNavigationMenu == null && getActivity() instanceof NavigationMenu) {
            mNavigationMenu = (NavigationMenu) getActivity();
        }
        return mNavigationMenu;
    }
}
