package com.zjh.administrat.monthdemo1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zjh.administrat.monthdemo1.R;
import com.zjh.administrat.monthdemo1.bean.DetailBean;

import java.util.ArrayList;
import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<DetailBean.DataBean> mData;
    private Context mContext;

    public DetailAdapter(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    public void setDatas(DetailBean.DataBean data) {
        if (data != null){
            mData.add(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.detail_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
        mHolder.name.setText(mData.get(i).getTitle());
        mHolder.price.setText("￥"+mData.get(i).getPrice());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
        }
    }

}
