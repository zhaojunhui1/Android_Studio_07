package com.zjh.administrat.monthdemo1.model;

import com.zjh.administrat.monthdemo1.callback.MyCallBack;

import java.util.Map;

public interface IModel {
    void mRequestData(String urlStr, Map<String, Integer> params, Class clazz, MyCallBack myCallBack);
}
