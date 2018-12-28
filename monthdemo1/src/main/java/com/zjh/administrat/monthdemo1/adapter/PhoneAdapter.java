package com.zjh.administrat.monthdemo1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zjh.administrat.monthdemo1.R;
import com.zjh.administrat.monthdemo1.bean.PhoneBean;

import java.util.ArrayList;
import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PhoneBean.DataBean> mData;
    private Context mContext;
    private boolean mflag;

    public PhoneAdapter(Context mContext, boolean flag) {
        this.mContext = mContext;
        this.mflag = flag;
        mData = new ArrayList<>();
    }

    public void setDatas(List<PhoneBean.DataBean> data) {
        if (data != null) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolder holder = null;
        if (mflag) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_linear_item, viewGroup, false);
            holder = new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_grid_item, viewGroup, false);
            holder = new ViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ViewHolder mHolder = (ViewHolder) viewHolder;
        mHolder.name.setText(mData.get(i).getTitle());
        mHolder.price.setText("￥" + mData.get(i).getPrice() + "");

        String str = "";
        int length = mData.get(i).getImages().length();
        for (int j = 0; j < length; j++) {
            if (mData.get(i).getImages().substring(j, j + 1).equals("|")) {
                str = mData.get(i).getImages().substring(j + 1, length).trim();
            }
        }
        mHolder.imageView.setImageURI(str);

        //点击
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClick != null) {
                    mClick.OnClick(i, mData.get(i).getPid());
                }
            }
        });
        //长按
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mClick != null) {
                    mClick.OnLongClick(i);
                }
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView imageView;
        public TextView name, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
        }
    }


    //成员变量
    Click mClick;

    //set方法
    public void setClickLinear(Click click) {
        mClick = click;
    }

    //定义一个接口
    public interface Click {
        void OnClick(int i, int pid);

        void OnLongClick(int i);
    }

}
