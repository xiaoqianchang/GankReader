package com.xiaoqianchang.gankreader.http;

import rx.Subscription;

/**
 * 用于数据请求的回调
 * 
 * Created by Chang.Xiao on 2017/4/21.
 * @version 1.0
 */
public interface IRequest {
    void loadSuccess(Object object);

    void loadFailed();

    void addSubscription(Subscription subscription);
}
