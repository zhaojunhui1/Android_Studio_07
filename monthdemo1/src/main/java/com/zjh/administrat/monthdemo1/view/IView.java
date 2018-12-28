package com.zjh.administrat.monthdemo1.view;

public interface IView<T> {
    void viewRequestSuccess(T data);
    void viewRequestFail(String error);
}
