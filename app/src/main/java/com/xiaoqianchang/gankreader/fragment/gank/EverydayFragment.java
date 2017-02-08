package com.xiaoqianchang.gankreader.fragment.gank;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.xiaoqianchang.gankreader.R;
import com.xiaoqianchang.gankreader.app.Constants;
import com.xiaoqianchang.gankreader.base.BaseFragment;
import com.xiaoqianchang.gankreader.databinding.FragmentEverydayBinding;
import com.xiaoqianchang.gankreader.databinding.HeaderItemEverydayBinding;
import com.xiaoqianchang.gankreader.http.cache.ACache;
import com.xiaoqianchang.gankreader.utils.SPUtils;
import com.xiaoqianchang.gankreader.utils.TimeUtil;
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
    private RotateAnimation animation;
    private ACache maCache;
    private EverydayViewModel mEverydayViewModel;
    private List<String> mBannerImageList;
    private HeaderItemEverydayBinding mHeaderItemBinding;
    private boolean mIsPrepared;

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

        showContentView();
        mBindingView.llLoading.setVisibility(View.VISIBLE);
        animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 05f);
        animation.setDuration(3000); // 设置动画持续时间
        animation.setInterpolator(new LinearInterpolator()); // 不停顿
        animation.setRepeatCount(10);
        mBindingView.ivLoading.setAnimation(animation);
        animation.startNow();

        maCache =ACache.get(getContext());
        mEverydayViewModel = new EverydayViewModel();
        mBannerImageList = (List<String>) maCache.getAsObject(Constants.BANNER_PIC);

        mHeaderItemBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.header_item_everyday, null, false);
        // 设置本地数据点击事件等
        initLocalSetting();
    }

    private void initLocalSetting() {
        mEverydayViewModel.init(getTodayTime().get(0), getTodayTime().get(1), getTodayTime().get(2));
        mHeaderItemBinding.includeEveryday.tvDaily.setText(getTodayTime().get(2).indexOf("0") == 0 ?
                getTodayTime().get(2).replace("0", "") : getTodayTime().get(2));
        mHeaderItemBinding.includeEveryday.ibXiandu.setOnClickListener(v -> {});
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
        super.loadData();
        if (mHeaderItemBinding != null && mHeaderItemBinding.banner != null) {
            mHeaderItemBinding.banner.startAutoPlay();
            mHeaderItemBinding.banner.setDelayTime(5000);
        }

        if (!isVisible() || !mIsPrepared) {
            return;
        }

        String oneData = SPUtils.getString("everyday_data", "2016-11-26");

    }
}
