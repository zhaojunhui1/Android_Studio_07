package com.zjh.administrat.greendaodemo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private TextBeanDao mDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataBase();
        ButterKnife.bind(this);
        //EventBus.getDefault().register(MainActivity.this);

    }

   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(MainActivity.this);
    }*/

    //greenDao
    private void dataBase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "user");
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        mDao = daoSession.getTextBeanDao();
    }

    @OnClick({R.id.insert, R.id.delete, R.id.update, R.id.query, R.id.send})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.insert:
                for (int i = 0; i < 20; i++) {
                    TextBean textBean = new TextBean();
                    textBean.setId((long) i);
                    textBean.setName("百度醋业委会"+i);
                    mDao.insertOrReplace(textBean);
                }
                break;
            case R.id.delete:
                mDao.deleteByKey((long) 9);
                break;
            case R.id.update:
                TextBean textBean = new TextBean();
                textBean.setId((long) 9);
                textBean.setName("第十一"+100);
                mDao.update(textBean);
                break;
            case R.id.query:
                QueryBuilder builder = mDao.queryBuilder();
                List list = builder.where(TextBeanDao.Properties.Id.eq(9))
                      .orderAsc(TextBeanDao.Properties.Id)
                      .list();
                for (Object o : list){
                    if (o instanceof TextBean){
                        Toast.makeText(this, ((TextBean) o).getName(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;

                //EventBus使用
            case R.id.send:
                //点击按钮，跳转到StickActivity并携带参数，参数类型为String
                //EventBus.getDefault().postSticky(new event("我是粘性"));
                EventBus.getDefault().postSticky("粘性事件");
                startActivity(new Intent(MainActivity.this, StickActivity.class));
                break;

            default:
                    break;
        }
    }

}
