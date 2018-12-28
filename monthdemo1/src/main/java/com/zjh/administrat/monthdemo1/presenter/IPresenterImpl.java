package com.zjh.administrat.monthdemo1.presenter;

import android.util.Log;

import com.zjh.administrat.monthdemo1.callback.MyCallBack;
import com.zjh.administrat.monthdemo1.model.IModelImpl;
import com.zjh.administrat.monthdemo1.view.IView;

import java.util.Map;

public class IPresenterImpl implements IPresenter {
    private IModelImpl iModel;
    private IView iView;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        iModel = new IModelImpl();
    }

    @Override
    public void pRequestData(String urlStr, Map<String, Integer> params, Class clazz) {
        iModel.mRequestData(urlStr, params, clazz, new MyCallBack() {
            @Override
            public void OnSuccess(Object data) {
                iView.viewRequestSuccess(data);
            }

            @Override
            public void OnFail(String error) {
                iView.viewRequestFail(error);
            }
        });
    }

    public void onDetch(){
        if (iView != null){
            iView = null;
        }
        if (iModel != null){
            iModel = null;
        }
    }
}
