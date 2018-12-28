package com.zjh.administrat.monthdemo1.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;
import com.zjh.administrat.monthdemo1.R;
import com.zjh.administrat.monthdemo1.adapter.DetailAdapter;
import com.zjh.administrat.monthdemo1.api.Apis;
import com.zjh.administrat.monthdemo1.bean.DetailBean;
import com.zjh.administrat.monthdemo1.bean.EventBean;
import com.zjh.administrat.monthdemo1.bean.PhoneBean;
import com.zjh.administrat.monthdemo1.bean.sBean;
import com.zjh.administrat.monthdemo1.presenter.IPresenterImpl;
import com.zjh.administrat.monthdemo1.view.IView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsFragment extends Fragment implements IView {
    private RecyclerView listView;
    private Banner banner;
    private IPresenterImpl iPresenter;
    private DetailAdapter mAdapter;
    private List<String> list;
    private int pid;
    private String title;
    private double price;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detailsfragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.listView);
        banner = view.findViewById(R.id.banner);

        iPresenter = new IPresenterImpl(this);
        mAdapter = new DetailAdapter(getActivity());
        listView.setAdapter(mAdapter);

        Map<String, Integer> map = new HashMap<>();
        map.put("pid", pid);
        iPresenter.pRequestData(Apis.path, map, DetailBean.class);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        listView.setLayoutManager(linearLayoutManager);
        initbanner();
    }

    private void initbanner() {
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setImageLoader(new ImageLoaderInterface<ImageView>() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }

            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                return imageView;
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void enentBus(EventBean eventBean){
        pid = eventBean.pid;
    }



    //传回的数据
    @Override
    public void viewRequestSuccess(Object data) {
        DetailBean detailBean = (DetailBean) data;
        //Toast.makeText(getActivity(), data+"", Toast.LENGTH_SHORT).show();
        mAdapter.setDatas(detailBean.getData());
        list = new ArrayList<>();

        title = detailBean.getData().getTitle();
        price = detailBean.getData().getPrice();
        Toast.makeText(getActivity(), title+price, Toast.LENGTH_SHORT).show();
        sBean bean = new sBean(title, price);
        EventBus.getDefault().postSticky(bean);
        //event传值

        String images = detailBean.getData().getImages();

        String[] split = images.split("\\|");
        for (int i = 0; i < split.length; i++) {
            list.add(split[i]);
        }

            banner.setImages(list);
            banner.start();
        }

    @Override
    public void viewRequestFail(String error) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        iPresenter.onDetch();
        EventBus.getDefault().unregister(this);
    }
}
