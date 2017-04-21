package com.xiaoqianchang.gankreader.viewmodel;

import com.xiaoqianchang.gankreader.bean.response.AndroidBean;
import com.xiaoqianchang.gankreader.http.IRequest;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * $desc$
 * <p>
 * Created by Chang.Xiao on 2017/2/8.
 *
 * @version 1.0
 */

public class EverydayViewModel {

    private String year = "2016";
    private String month = "11";
    private String day = "24";

    public void setData(String year, String month, String day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * 轮播图
     */
    public void showBanncerPage(final IRequest listener) {
        Subscription subscription = HttpUtils.getInstance().getDongTingServer().getFrontpage()
                .observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Observer<FrontpageBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.loadFailed();
                    }

                    @Override
                    public void onNext(FrontpageBean frontpageBean) {
                        listener.loadSuccess(frontpageBean);
                    }
                });
        listener.addSubscription(subscription);
    }

    private List<List<AndroidBean>> lists;

    /**
     * 显示RecyclerView数据
     */
    public void showRecyclerViewData(final RequestImpl listener) {
        Func1<GankIoDayBean, Observable<List<List<AndroidBean>>>> func1 = new Func1<GankIoDayBean, Observable<List<List<AndroidBean>>>>() {
            @Override
            public Observable<List<List<AndroidBean>>> call(GankIoDayBean gankIoDayBean) {

                lists = new ArrayList<>();
                GankIoDayBean.ResultsBean results = gankIoDayBean.getResults();

                if (results.getAndroid() != null && results.getAndroid().size() > 0) {
                    addList(results.getAndroid(), "Android");
                }
                if (results.getWelfare() != null && results.getWelfare().size() > 0) {
                    addList(results.getWelfare(), "福利");
                }
                if (results.getiOS() != null && results.getiOS().size() > 0) {
                    addList(results.getiOS(), "IOS");
                }
                if (results.getRestMovie() != null && results.getRestMovie().size() > 0) {
                    addList(results.getRestMovie(), "休息视频");
                }
                if (results.getResource() != null && results.getResource().size() > 0) {
                    addList(results.getResource(), "拓展资源");
                }
                if (results.getRecommend() != null && results.getRecommend().size() > 0) {
                    addList(results.getRecommend(), "瞎推荐");
                }
                if (results.getFront() != null && results.getFront().size() > 0) {
                    addList(results.getFront(), "前端");
                }
                if (results.getApp() != null && results.getApp().size() > 0) {
                    addList(results.getApp(), "App");
                }

                return Observable.just(lists);
            }
        };

        Observer<List<List<AndroidBean>>> observer = new Observer<List<List<AndroidBean>>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                listener.loadFailed();
            }

            @Override
            public void onNext(List<List<AndroidBean>> lists) {
                listener.loadSuccess(lists);
            }
        };

        Subscription subscription = HttpUtils.getInstance().getGankIOServer().getGankIoDay(year, month, day)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .flatMap(func1)
                .subscribe(observer);
        listener.addSubscription(subscription);
    }


    // subList没有实现序列化！缓存时会出错！
    private void addList(List<AndroidBean> arrayList, String typeTitle) {
        // title
        AndroidBean bean = new AndroidBean();
        bean.setType_title(typeTitle);
        ArrayList<AndroidBean> androidBeen = new ArrayList<>();
        androidBeen.add(bean);
        lists.add(androidBeen);

        int androidSize = arrayList.size();
        if (androidSize > 0 && androidSize < 4) {
            lists.add(arrayList);
        } else if (androidSize >= 4) {

            ArrayList<AndroidBean> list1 = new ArrayList<>();
            ArrayList<AndroidBean> list2 = new ArrayList<>();
            for (int i = 0; i < androidSize; i++) {
                if (i < 3) {
                    list1.add(arrayList.get(i));
                } else if (i < 6) {
                    list2.add(arrayList.get(i));
                }
            }
            lists.add(list1);
            lists.add(list2);


            //            lists.add(arrayList.subList(0, 3));
            //            if (androidSize > 6) {
            //                lists.add(arrayList.subList(3, 6));
            //            } else {
            //                lists.add(arrayList.subList(3, androidSize));
            //            }
        }
    }
}
