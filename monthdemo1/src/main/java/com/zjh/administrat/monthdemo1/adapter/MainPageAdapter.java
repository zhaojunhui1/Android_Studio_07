package com.zjh.administrat.monthdemo1.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zjh.administrat.monthdemo1.fragment.DetailsFragment;
import com.zjh.administrat.monthdemo1.fragment.GoodsFragment;
import com.zjh.administrat.monthdemo1.fragment.likeFragment;

public class MainPageAdapter extends FragmentPagerAdapter {

    private String[] menus = new String[]{"商品","详情", "评论"};
    public MainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DetailsFragment();
            case 1:
                return new GoodsFragment();
            case 2:
                return new likeFragment();
            default:
                return new GoodsFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return menus[position];
    }

    @Override
    public int getCount() {
        return menus.length;
    }

}
