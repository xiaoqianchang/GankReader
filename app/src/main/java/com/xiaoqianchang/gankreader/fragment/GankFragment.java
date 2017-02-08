package com.xiaoqianchang.gankreader.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.xiaoqianchang.gankreader.R;
import com.xiaoqianchang.gankreader.adapter.MyFragmentPagerAdapter;
import com.xiaoqianchang.gankreader.base.BaseFragment;
import com.xiaoqianchang.gankreader.databinding.FragmentGankBinding;
import com.xiaoqianchang.gankreader.fragment.gank.AndroidFragment;
import com.xiaoqianchang.gankreader.fragment.gank.CustomFragment;
import com.xiaoqianchang.gankreader.fragment.gank.EverydayFragment;
import com.xiaoqianchang.gankreader.fragment.gank.WelfareFragment;
import com.xiaoqianchang.gankreader.http.rx.RxBus;
import com.xiaoqianchang.gankreader.http.rx.RxCodeConstants;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class GankFragment extends BaseFragment<FragmentGankBinding> {

    private List<String> mTitleList = new ArrayList<>(4);
    private List<Fragment> mFragmentList = new ArrayList<>(4);

    public GankFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_gank;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        showLoading();
        initFragmentList();
        /**
         * 注意使用的是：getChildFragmentManager，
         * 这样setOffscreenPageLimit()就可以添加上，保留相邻3个实例，切换时不会卡
         * 但会内存溢出，在显示时加载数据
         */
        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), mFragmentList, mTitleList);
        mBindingView.vpGank.setAdapter(myFragmentPagerAdapter);
        // 左右预加载页面的个数
        mBindingView.vpGank.setOffscreenPageLimit(3);
        mBindingView.tabGank.setTabMode(TabLayout.MODE_FIXED);
        mBindingView.tabGank.setupWithViewPager(mBindingView.vpGank);
        showContentView();
        // item点击跳转
        initRxBus();
    }

    private void initFragmentList() {
        mTitleList.add("每日推荐");
        mTitleList.add("福利");
        mTitleList.add("干货订制");
        mTitleList.add("大安卓");
        mFragmentList.add(EverydayFragment.newInstance());
        mFragmentList.add(WelfareFragment.newInstance());
        mFragmentList.add(CustomFragment.newInstance());
        mFragmentList.add(AndroidFragment.newInstance());
    }

    /**
     * 每日推荐点击"更多"跳转
     */
    private void initRxBus() {
        Subscription subscription = RxBus.getDefault().toObservable(RxCodeConstants.JUMP_TYPE, Integer.class)
                .subscribe(integer -> {
            if (integer == 0) {
                mBindingView.vpGank.setCurrentItem(3);
            } else if (integer == 1) {
                mBindingView.vpGank.setCurrentItem(1);
            } else if (integer == 2) {
                mBindingView.vpGank.setCurrentItem(2);
            }
        });
        addSubscription(subscription);
    }
}
