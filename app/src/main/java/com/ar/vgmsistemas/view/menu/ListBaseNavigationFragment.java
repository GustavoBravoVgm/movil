package com.ar.vgmsistemas.view.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.ar.vgmsistemas.R;

public class ListBaseNavigationFragment extends BaseNavigationFragment {
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyt_one_listview, null);
        return view;
    }

    public ListView getListView() {
        if (mListView == null) {
            mListView = (ListView) getView().findViewById(R.id.listview);
        }
        return mListView;
    }

    public void setListAdapter(ListAdapter adapter) {
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
