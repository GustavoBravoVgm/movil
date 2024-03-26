package com.ar.vgmsistemas.view.reparto.hojas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.ar.vgmsistemas.R;
import com.ar.vgmsistemas.bo.HojaBo;
import com.ar.vgmsistemas.entity.Hoja;
import com.ar.vgmsistemas.repository.RepositoryFactory;
import com.ar.vgmsistemas.view.menu.BaseNavigationFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class FrmHojas extends BaseNavigationFragment {
    private HojaBo mHojaBo;
    private List<Hoja> mHojas;

    //implementación con tablelayout y viewpager2
    ViewPager2 mViewPagerHoja;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        loadData();
        loadViews();
    }

    private void loadData() {
        RepositoryFactory _repoFactory = RepositoryFactory.getRepositoryFactory(getContext(), RepositoryFactory.ROOM);
        mHojaBo = new HojaBo(_repoFactory);
        try {
            mHojas = mHojaBo.recoveryAllEntities();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return loadViews();
    }

    private View loadViews() {
        View view = View.inflate(getActivity(), R.layout.lyt_hojas, null);

        //implementación con viewpager
        mViewPagerHoja = view.findViewById(R.id.vpHojas);
        CustomPagerAdapter mViewAdapterHoja = new CustomPagerAdapter(getActivity(), mHojas);
        //seteo el adapter al viewpager
        mViewPagerHoja.setAdapter(mViewAdapterHoja);
        //obtengo el tabLayout
        TabLayout mTabLayoutConfig = view.findViewById(R.id.tabLayoutHoja);
        //creo un mediator para gestionar el tab con el viewpager
        new TabLayoutMediator(mTabLayoutConfig, mViewPagerHoja, ((tab, position) -> tab.setText(mViewAdapterHoja.getPageTitle(position)))).attach();

        return view;

    }

    protected class CustomPagerAdapter extends FragmentStateAdapter {
        private List<Hoja> mHojasLocal;

        public CustomPagerAdapter(FragmentActivity fragmentActivity, List<Hoja> list) {
            super(fragmentActivity);
            mHojasLocal = list;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            HojaFragment fragment = new HojaFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(HojaFragment.EXTRA_HOJA, mHojasLocal.get(position));
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getItemCount() {
            return mHojasLocal.size();
        }

        public CharSequence getPageTitle(int position) {
            return "Hoja N " + mHojasLocal.get(position).getIdHoja();
        }
    }


    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        super.onActivityResult(requestCode, resultCode, data);
    }*/
}
