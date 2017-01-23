package com.xiaoqianchang.gankreader;

import android.databinding.DataBindingUtil;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xiaoqianchang.gankreader.adapter.MainFragmentPagerAdapter;
import com.xiaoqianchang.gankreader.databinding.ActivityMainBinding;
import com.xiaoqianchang.gankreader.fragment.BookFragment;
import com.xiaoqianchang.gankreader.fragment.GankFragment;
import com.xiaoqianchang.gankreader.fragment.OneFragment;
import com.xiaoqianchang.gankreader.http.rx.RxBus;
import com.xiaoqianchang.gankreader.http.rx.RxCodeConstants;
import com.xiaoqianchang.gankreader.utils.ImageLoadUtils;

import java.util.ArrayList;

import rx.functions.Action1;

import static com.xiaoqianchang.gankreader.R.id.fab;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ActivityMainBinding mBinding;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar;
    private FrameLayout flTitleMenu;
    private ViewPager viewPager;
    private ImageView ivTitleGank;
    private ImageView ivTitleOne;
    private ImageView ivTitleDou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initId();
        initRxBus();
        initContentFragment();
        initDrawerLayout();
        initListener();
    }

    private void initId() {
        drawerLayout = mBinding.drawerLayout;
        navigationView = mBinding.navView;
        floatingActionButton = mBinding.icAppBarMain.fab;
        toolbar = mBinding.icAppBarMain.toolBar;
        flTitleMenu = mBinding.icAppBarMain.flTitleMenu;
        viewPager = mBinding.icAppBarMain.vpContent;

        ivTitleGank = mBinding.icAppBarMain.ivTitleGank;
        ivTitleOne = mBinding.icAppBarMain.ivTitleOne;
        ivTitleDou = mBinding.icAppBarMain.ivTitleDou;

        floatingActionButton.setVisibility(View.GONE);
    }

    /**
     * 每日推荐点击"新电影热映榜"跳转
     */
    private void initRxBus() {
        RxBus.getDefault().toObservable(RxCodeConstants.JUMP_TYPE_TO_ONE, Boolean.class)
                .subscribe(aBoolean -> {
                    mBinding.icAppBarMain.vpContent.setCurrentItem(1);
        });
    }

    private void initContentFragment() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new GankFragment());
        fragments.add(new OneFragment());
        fragments.add(new BookFragment());
        MainFragmentPagerAdapter mainFragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(mainFragmentPagerAdapter);
        // 设置ViewPager最大缓存的页面个数(cpu消耗少)
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(this);
        mBinding.icAppBarMain.ivTitleGank.setSelected(true);
        viewPager.setCurrentItem(0);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            // 去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void initDrawerLayout() {
        navigationView.inflateHeaderView(R.layout.nav_header_main);
        View headerView = navigationView.getHeaderView(0);
        ImageView ivAvatar = (ImageView) headerView.findViewById(R.id.iv_avatar);
        ImageLoadUtils.getInstance().displayCircle(ivAvatar, R.drawable.ic_avatar);
        LinearLayout llNavHomepage = (LinearLayout) headerView.findViewById(R.id.ll_nav_homepage);
        LinearLayout llNavScanDownload = (LinearLayout) headerView.findViewById(R.id.ll_nav_scan_download);
        LinearLayout llNavDeedback = (LinearLayout) headerView.findViewById(R.id.ll_nav_deedback);
        LinearLayout llNavAbout = (LinearLayout) headerView.findViewById(R.id.ll_nav_about);
        llNavHomepage.setOnClickListener(this);
        llNavScanDownload.setOnClickListener(this);
        llNavDeedback.setOnClickListener(this);
        llNavAbout.setOnClickListener(this);
    }

    private void initListener() {
        flTitleMenu.setOnClickListener(this);
        mBinding.icAppBarMain.ivTitleGank.setOnClickListener(this);
        mBinding.icAppBarMain.ivTitleDou.setOnClickListener(this);
        mBinding.icAppBarMain.ivTitleOne.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_title_menu:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.iv_title_gank: // 干货栏
                if (viewPager.getCurrentItem() != 0) {
                    ivTitleGank.setSelected(true);
                    ivTitleOne.setSelected(false);
                    ivTitleDou.setSelected(false);
                    viewPager.setCurrentItem(0);
                }
                break;
            case R.id.iv_title_one: // 电影栏
                if (viewPager.getCurrentItem() != 1) {
                    ivTitleOne.setSelected(true);
                    ivTitleGank.setSelected(false);
                    ivTitleDou.setSelected(false);
                    viewPager.setCurrentItem(1);
                }
                break;
            case R.id.iv_title_dou: // 书籍栏
                if (viewPager.getCurrentItem() != 2) {
                    ivTitleDou.setSelected(true);
                    ivTitleOne.setSelected(false);
                    ivTitleGank.setSelected(false);
                    viewPager.setCurrentItem(2);
                }
                break;
            case R.id.ll_nav_homepage: // 主页
                mBinding.drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.ll_nav_scan_download: //扫码下载
                break;
            case R.id.ll_nav_deedback: // 问题反馈
                break;
            case R.id.ll_nav_about: // 关于云阅
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
