package com.xiaoqianchang.gankreader.fragment.gank;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.xiaoqianchang.gankreader.R;
import com.xiaoqianchang.gankreader.app.Constants;
import com.xiaoqianchang.gankreader.base.BaseFragment;
import com.xiaoqianchang.gankreader.bean.response.AndroidBean;
import com.xiaoqianchang.gankreader.databinding.FooterItemEverydayBinding;
import com.xiaoqianchang.gankreader.databinding.FragmentEverydayBinding;
import com.xiaoqianchang.gankreader.databinding.HeaderItemEverydayBinding;
import com.xiaoqianchang.gankreader.http.cache.ACache;
import com.xiaoqianchang.gankreader.http.rx.RxBus;
import com.xiaoqianchang.gankreader.http.rx.RxCodeConstants;
import com.xiaoqianchang.gankreader.utils.OnPerfectClickListener;
import com.xiaoqianchang.gankreader.utils.SPUtils;
import com.xiaoqianchang.gankreader.utils.TimeUtil;
import com.xiaoqianchang.gankreader.view.webview.WebViewActivity;
import com.xiaoqianchang.gankreader.viewmodel.EverydayViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 每日推荐
 *
 * A simple {@link Fragment} subclass.
 */
public class EverydayFragment extends BaseFragment<FragmentEverydayBinding> {

    private static final String TAG = EverydayFragment.class.getSimpleName();
    private ACache maCache;
    private ArrayList<List<AndroidBean>> mLists;
    private ArrayList<String> mBannerImages;
    private EverydayViewModel mEverydayViewModel;
    private HeaderItemEverydayBinding mHeaderItemBinding;
    private FooterItemEverydayBinding mFooterItemBinding;
    private View mHeaderView;
    private View mFooterView;
    private EverydayAdapter mEverydayAdapter;
    private boolean mIsPrepared = false;
    private boolean mIsFirst = true;
    // 是否正在刷新（避免重复刷新）
    private boolean mIsLoading = false;
    // 是否是上一天的请求
    private boolean isOldDayRequest;
    private RotateAnimation animation;

    public EverydayFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_everyday;
    }

    public static EverydayFragment newInstance() {
        EverydayFragment everydayFragment = new EverydayFragment();
        return everydayFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        showLoading();
        showContentView();
        mBindingView.llLoading.setVisibility(View.VISIBLE);
        animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(3000); // 设置动画持续时间
        animation.setInterpolator(new LinearInterpolator()); // 不停顿
        animation.setRepeatCount(10);
        mBindingView.ivLoading.setAnimation(animation);
        animation.startNow();

        maCache =ACache.get(getContext());
        mEverydayViewModel = new EverydayViewModel();
        mBannerImages = (ArrayList<String>) maCache.getAsObject(Constants.BANNER_PIC);

        mHeaderItemBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.header_item_everyday, null, false);
        // 设置本地数据点击事件等
        initLocalSetting();
        initRecyclerView();

        mIsPrepared = true;
        /**
         * 因为启动时先走loadData()再走onActivityCreated，
         * 所以此处要额外调用load(),不然最初不会加载内容
         */
        loadData();
    }

    private void initRecyclerView() {
        mBindingView.xrvEveryday.setPullRefreshEnabled(false);
        mBindingView.xrvEveryday.setLoadingMoreEnabled(false);
        if (null == mHeaderView) {
            mHeaderView = mHeaderItemBinding.getRoot();
            mBindingView.xrvEveryday.addHeaderView(mHeaderView);
        }
        if (null == mFooterView) {
            mFooterItemBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.footer_item_everyday, null, false);
            mFooterView = mFooterItemBinding.getRoot();
            mBindingView.xrvEveryday.addFootView(mFooterView, true);
            mBindingView.xrvEveryday.noMoreLoading();
        }
        mBindingView.xrvEveryday.setLayoutManager(new LinearLayoutManager(getContext()));
        // 需加，不然滑动不流畅
        mBindingView.xrvEveryday.setNestedScrollingEnabled(false);
        mBindingView.xrvEveryday.setHasFixedSize(false);
        mBindingView.xrvEveryday.setItemAnimator(new DefaultItemAnimator());
    }

    private void initLocalSetting() {
        mEverydayViewModel.init(getTodayTime().get(0), getTodayTime().get(1), getTodayTime().get(2));
        mHeaderItemBinding.includeEveryday.tvDaily.setText(getTodayTime().get(2).indexOf("0") == 0 ?
                getTodayTime().get(2).replace("0", "") : getTodayTime().get(2));
        mHeaderItemBinding.includeEveryday.ibXiandu.setOnClickListener(new OnPerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                WebViewActivity.loadUrl(v.getContext(), "https://gank.io/xiandu", "加载中...");
            }
        });
        mHeaderItemBinding.includeEveryday.ibMovieHot.setOnClickListener(new OnPerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                RxBus.getDefault().post(RxCodeConstants.JUMP_TYPE_TO_ONE, true);
            }
        });
    }

    /**
     * 获取当天日期
     */
    private ArrayList<String> getTodayTime() {
        String data = TimeUtil.getData();
        String[] split = data.split("-");
        String year = split[0];
        String month = split[1];
        String day = split[2];
        ArrayList<String> list = new ArrayList<>();
        list.add(year);
        list.add(month);
        list.add(day);
        return list;
    }

    @Override
    protected void loadData() {
        // 显示时轮播图滚动
        if (mHeaderItemBinding != null && mHeaderItemBinding.banner != null) {
            mHeaderItemBinding.banner.startAutoPlay();
            mHeaderItemBinding.banner.setDelayTime(5000);
        }

        if (!isVisible() || !mIsPrepared) {
            return;
        }

        String oneData = SPUtils.getString("everyday_data", "2016-11-26");
        if (!oneData.equals(TimeUtil.getData())) {// 是第二天
            if (TimeUtil.isRightTime()) {//大于12：30,请求

                showRotaLoading(true);
                loadBannerPicture();
                showContentData();
            } else {// 小于，取缓存没有请求前一天
                ArrayList<String> lastTime = TimeUtil.getLastTime(getTodayTime().get(0), getTodayTime().get(1), getTodayTime().get(2));
                mEverydayModel.setData(lastTime.get(0), lastTime.get(1), lastTime.get(2));
                isOldDayRequest = true;
                getACacheData();
            }
        } else {// 当天，取缓存没有请求当天
            isOldDayRequest = false;
            getACacheData();
        }
    }

    private void loadBannerPicture() {
        mEverydayViewModel.shw
    }

    private void showRotaLoading(boolean isLoading) {
        if (isLoading) {
            mBindingView.llLoading.setVisibility(View.VISIBLE);
            mBindingView.xrvEveryday.setVisibility(View.GONE);
        } else {
            mBindingView.llLoading.setVisibility(View.GONE);
            mBindingView.xrvEveryday.setVisibility(View.VISIBLE);
            animation.cancel();
        }
    }
}
