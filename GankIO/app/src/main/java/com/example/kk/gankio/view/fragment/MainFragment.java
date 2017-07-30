package com.example.kk.gankio.view.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.kk.gankio.R;
import com.example.kk.gankio.view.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kk on 2017/7/21.
 */

public class MainFragment extends BaseFragment {

    private TabLayout tab;

    private ViewPager viewPager;

    private ViewPagerAdapter adapter;

    private List<Fragment> fragments;

    private List<String> titles;



    public MainFragment() {

    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_gankio;
    }

    @Override
    protected void initView(View rootView) {
        tab = (TabLayout) rootView.findViewById(R.id.tablayout);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter(getFragmentManager(), titles, fragments);
        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initData() {
        titles = new ArrayList<>();
        fragments = new ArrayList<>();
        titles = Arrays.asList(getResources().getStringArray(R.array.gankio_type));
        for(String s : titles){
            fragments.add(new GankFragment(s));
        }
    }
}
