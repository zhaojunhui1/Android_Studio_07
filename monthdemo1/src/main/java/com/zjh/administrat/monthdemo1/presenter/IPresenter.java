package com.zjh.administrat.monthdemo1.presenter;

import java.util.Map;

public interface IPresenter {
    void pRequestData(String urlStr, Map<String, Integer> params, Class clazz);
}
