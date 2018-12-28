package com.zjh.administrat.monthdemo1.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import com.zjh.administrat.monthdemo1.R;
import com.zjh.administrat.monthdemo1.adapter.PhoneAdapter;
import com.zjh.administrat.monthdemo1.api.Apis;
import com.zjh.administrat.monthdemo1.bean.EventBean;
import com.zjh.administrat.monthdemo1.bean.PhoneBean;
import com.zjh.administrat.monthdemo1.presenter.IPresenterImpl;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView {

    private IPresenterImpl iPresenter;
    private RecyclerView recyclerView;
    private PhoneAdapter mAdapter;
    private PhoneBean phoneBean;
    private boolean flag = true;
    private int mSpanCount = 2;
    private int pid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        change();
        click();
        //默认展示线性管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    //切换界面
    private void change() {
        findViewById(R.id.change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    flag = false;
                }else {
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, mSpanCount);
                    gridLayoutManager.setOrientation(OrientationHelper.VERTICAL);
                    recyclerView.setLayoutManager(gridLayoutManager);
                    flag = true;
                }
            }
        });
    }

    //初始化
    private void initView() {
        iPresenter = new IPresenterImpl(this);
        recyclerView = findViewById(R.id.recycleview);

    }

    private void initData() {
        mAdapter = new PhoneAdapter(this, flag);
        Map<String, Integer> map = new HashMap<>();
        map.put("pid", pid);
        iPresenter.pRequestData(Apis.urlStr, map, PhoneBean.class);

        recyclerView.setAdapter(mAdapter);

    }

    //获取返回数据
    @Override
    public void viewRequestSuccess(Object data) {
        phoneBean = (PhoneBean) data;

        mAdapter.setDatas(phoneBean.getData());
    }

    @Override
    public void viewRequestFail(String error) {
        Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
    }

    //点击
    private void click() {

        mAdapter.setClickLinear(new PhoneAdapter.Click() {

            @Override
            public void OnClick(int i, int id) {
                pid = id;
                EventBean bean = new EventBean(pid);
                EventBus.getDefault().postSticky(bean);
                startActivity(new Intent(MainActivity.this, DetailsActivity.class));
            }

            @Override
            public void OnLongClick(int i) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.onDetch();
    }

}
