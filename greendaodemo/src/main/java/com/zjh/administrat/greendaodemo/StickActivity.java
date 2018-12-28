package com.zjh.administrat.greendaodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StickActivity extends AppCompatActivity {

    private String name1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stick);
        ButterKnife.bind(this);

        EventBus.getDefault().register(StickActivity.this);
    }

  /*  @OnClick(R.id.button)
    public void onClick(View view){
        Toast.makeText(StickActivity.this, name1, Toast.LENGTH_SHORT).show();

    }*/

    @Subscribe(sticky = true)
    public void staik(final String str){
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StickActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(StickActivity.this);
    }
}
