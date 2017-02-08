package com.xiaoqianchang.gankreader.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * $desc$
 * <p>
 * Created by Chang.Xiao on 2017/1/23.
 *
 * @version 1.0
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<?> mFragmentList;
    private List<String> mTitleList;

    /**
     * default，主页使用
     *
     * @param fm
     * @param fragments
     */
    public MyFragmentPagerAdapter(FragmentManager fm, List<?> fragments) {
        super(fm);
        this.mFragmentList = fragments;
    }

    /**
     * 接收首页传递的标题
     *
     * @param fm
     * @param mFragmentList
     * @param mTitleList
     */
    public MyFragmentPagerAdapter(FragmentManager fm, List<?> mFragmentList, List<String> mTitleList) {
        super(fm);
        this.mFragmentList = mFragmentList;
        this.mTitleList = mTitleList;
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    public CharSequence getPageTitle(int position) {
        if (null != mTitleList) {
            return mTitleList.get(position);
        }
        return "";
    }

    public void addFragmentList(List<?> fragmentList) {
        this.mFragmentList.clear();
        this.mFragmentList = null;
        this.mFragmentList = fragmentList;
        notifyDataSetChanged();
    }
}
