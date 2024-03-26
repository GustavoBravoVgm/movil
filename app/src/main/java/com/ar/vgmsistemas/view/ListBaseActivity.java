package com.ar.vgmsistemas.view;

import android.os.Bundle;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ar.vgmsistemas.R;

public class ListBaseActivity extends BaseActivity {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_one_listview);
    }

    protected ListView getListView() {
        if (mListView == null) {
            mListView = (ListView) findViewById(R.id.listview);
        }
        return mListView;
    }

    protected void setListAdapter(ListAdapter adapter) {
        getListView().setAdapter(adapter);
    }

    protected ListAdapter getListAdapter() {
        ListAdapter adapter = getListView().getAdapter();
        if (adapter instanceof HeaderViewListAdapter) {
            return ((HeaderViewListAdapter) adapter).getWrappedAdapter();
        } else {
            return adapter;
        }
    }
}
