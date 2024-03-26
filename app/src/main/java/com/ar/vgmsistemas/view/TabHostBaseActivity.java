package com.ar.vgmsistemas.view;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;

public class TabHostBaseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    }

}
