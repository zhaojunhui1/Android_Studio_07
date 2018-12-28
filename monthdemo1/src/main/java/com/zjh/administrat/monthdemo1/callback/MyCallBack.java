package com.zjh.administrat.monthdemo1.callback;

public interface MyCallBack<T> {
    void OnSuccess(T data);
    void OnFail(String error);
}
