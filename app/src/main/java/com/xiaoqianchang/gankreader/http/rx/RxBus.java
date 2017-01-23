package com.xiaoqianchang.gankreader.http.rx;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;
import rx.subscriptions.CompositeSubscription;

/**
 * RxBus
 * <p>
 * Created by Chang.Xiao on 2017/1/23.
 *
 * @version 1.0
 */

public final class RxBus {

    private static volatile RxBus mDefaultInstance;

    // 主题
    private final Subject<Object, Object> _bus;

    private Map<String, CompositeSubscription> mSubscriptionMap;

    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    private RxBus() {
        _bus = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getDefault() {
        if (null == mDefaultInstance) {
            synchronized (RxBus.class) {
                if (null == mDefaultInstance) {
                    mDefaultInstance = new RxBus();
                }
            }
        }
        return mDefaultInstance;
    }

    /**
     * 提供了一个新的事件,单一类型
     *
     * @param object    事件数据
     */
    public void post(Object object) {
        _bus.onNext(object);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     *
     * @param eventType     事件类型
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return _bus.ofType(eventType);
    }

    /**
     * 提供了一个新的事件,根据code进行分发
     *
     * @param code      事件code
     * @param object    事件类型
     */
    public void post(int code, Object object) {
        _bus.onNext(new EventObject(code, object));
    }

    /**
     * 根据传递的code和 eventType 类型返回特定类型(eventType)的 被观察者
     *
     * @param code          事件code
     * @param eventType     事件类型
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable(final int code, final Class<T> eventType) {
        return _bus.ofType(EventObject.class)
                .filter(new Func1<EventObject, Boolean>() {
            @Override
            public Boolean call(EventObject eventObject) {
                // 过滤code和eventType都相同的事件
                return eventObject.getCode() == code && eventType.isInstance(eventObject.getObject());
            }
        }).map(new Func1<EventObject, Object>() {
                    @Override
                    public Object call(EventObject eventObject) {
                        return eventObject.getObject();
                    }
                }).cast(eventType);
    }

    /**
     * 判断是否有订阅者
     *
     * @return
     */
    public boolean hasObservers() {
        return _bus.hasObservers();
    }

    /**
     * 一个默认的订阅方法
     *
     * @param type
     * @param next
     * @param error
     * @param <T>
     * @return
     */
    public <T>Subscription doSubscribe(Class<T> type, Action1<T> next, Action1<Throwable> error) {
        return toObservable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next, error);
    }

    /**
     * 保存订阅后的subscription
     *
     * @param object
     * @param subscription
     */
    public void addSubscription(Object object, Subscription subscription) {
        if (null == mSubscriptionMap) {
            mSubscriptionMap = new HashMap<>();
        }
        String key = object.getClass().getName();
        if (null != mSubscriptionMap.get(key)) {
            mSubscriptionMap.get(key).add(subscription);
        } else {
            CompositeSubscription compositeSubscription = new CompositeSubscription();
            compositeSubscription.add(subscription);
            mSubscriptionMap.put(key, compositeSubscription);
        }
    }

    /**
     * 取消订阅
     *
     * @param object
     */
    public void unSubscribe(Object object) {
        if (null == mSubscriptionMap) {
            return;
        }

        String key = object.getClass().getName();
        if (!mSubscriptionMap.containsKey(key)) {
            return;
        }
        if (null != mSubscriptionMap.get(key)) {
            mSubscriptionMap.get(key).unsubscribe();
        }

        mSubscriptionMap.remove(key);
    }

    /**
     * 事件类
     */
    public static class EventObject extends Object {
        private int code;
        private Object object;

        public EventObject(int code, Object object) {
            this.code = code;
            this.object = object;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
