package com.example.day3z_mn;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.day3z_mn.adapter.MyGoodAdapter;
import com.example.day3z_mn.adapter.MyLinAdapter;
import com.example.day3z_mn.base.BaseActivity;
import com.example.day3z_mn.bean.MyBean;
import com.example.day3z_mn.presenter.PreseneterImpl;
import com.example.day3z_mn.view.IView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements IView {
    private String murl = "http://www.zhaoapi.cn/product/searchProducts?keywords=手机";
    private TextView main_qh;
    private XRecyclerView recycler;
    private List<MyBean.DataBean> list = new ArrayList<>();
    private MyLinAdapter linAdapter;
    private MyGoodAdapter goodAdapter;
    private PreseneterImpl preseneter;
    private boolean c = true;
    private boolean jg = true;
    private boolean xl = true;
    private Button main_zh;
    private Button main_xl;
    private Button main_jg;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void getFindByid() {
        main_qh = findViewById(R.id.main_qh);

        main_zh = findViewById(R.id.main_zh);
        main_xl = findViewById(R.id.main_xl);
        main_jg = findViewById(R.id.main_jg);

        main_jg.setOnClickListener(this);
        main_xl.setOnClickListener(this);
        main_zh.setOnClickListener(this);


        recycler = findViewById(R.id.xrecycle);
//       这是一个线性布局
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);
//        下拉刷新的代码
//        添加的一些样式
        recycler.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recycler.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        recycler.setArrowImageView(R.drawable.ic_launcher_background);

//         单纯的线性布局
//        linAdapter = new MyLinAdapter(list, this);
//        recycler.setAdapter(linAdapter);

        goodAdapter = new MyGoodAdapter(list, this, 1);
        recycler.setAdapter(goodAdapter);

//        点击事件
        /*linAdapter.setOnClickListener(new MyLinAdapter.ItemClick() {
            @Override
            public void setOnItemClick(View view, int position) {
//                Toast.makeText(MainActivity.this, list.get(position).getTitle()+"", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("url", list.get(position).getDetailUrl() + "");
                startActivity(intent);
            }
        });*/
//        设置上拉刷新，下拉加载
        recycler.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
//                刷新
                list.clear();
                preseneter.getMyData(murl);
                recycler.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                preseneter.getMyData(murl);
                recycler.loadMoreComplete();
            }
        });
    }

    @Override
    protected void getonClick() {
        main_qh.setOnClickListener(this);
    }

    @Override
    protected void getProcassLogin() {
        preseneter = new PreseneterImpl(this);
        preseneter.getMyData(murl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_qh:
//                切换
                if (c) {
                    c = false;
//                   1： 设置布局类型
                    goodAdapter.setType(2);
//                    2：设置布局类型
                    RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
                    recycler.setLayoutManager(manager);
//                   3： 刷新适配器
                    goodAdapter.notifyDataSetChanged();
                } else {
                    c = true;
                    goodAdapter.setType(1);
                    LinearLayoutManager manager = new LinearLayoutManager(this);
                    recycler.setLayoutManager(manager);
                    goodAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.main_zh:

                break;
//                销量切换
            case R.id.main_xl:
                if (xl) {
                    xl = false;
                    mppxxl();
                } else {
                    xl = true;
                    mppxxlxd();
                }
                break;
//                价格切换
            case R.id.main_jg:
                if (jg) {
                    jg = false;
                    mppx();
                } else {
                    jg = true;
                    mppxxd();
                }
                break;
        }
    }

    @Override
    public void showLoagin() {

    }

    @Override
    public void success(Object data) {
        MyBean bean = (MyBean) data;
        list.addAll(bean.getData());
//        linAdapter.notifyDataSetChanged();
        goodAdapter.notifyDataSetChanged();
    }

    @Override
    public void error(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        preseneter.onDetach();
    }

    //冒泡排序 价格 从大到小
    private void mppx() {
        for (int i = 0; i < list.size() - 1; i++) {//最多做n-1趟排序
            //对当前无序区间进行排序(j的范围很关键，这个范围是在逐步缩小的)
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(j).getBargainPrice() < list.get(j + 1).getBargainPrice()) { //把小的值交换到后面
                    MyBean.DataBean temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
//        linAdapter.notifyDataSetChanged();
        goodAdapter.notifyDataSetChanged();
    }

    //冒泡排序 价格 从小到大
    private void mppxxd() {
        for (int i = 0; i < list.size() - 1; i++) {//最多做n-1趟排序
            //对当前无序区间进行排序(j的范围很关键，这个范围是在逐步缩小的)
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(j).getBargainPrice() > list.get(j + 1).getBargainPrice()) { //把小的值交换到后面
                    MyBean.DataBean temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
//        linAdapter.notifyDataSetChanged();
        goodAdapter.notifyDataSetChanged();
    }

    //冒泡排序 销量 从大到小
    private void mppxxl() {
        for (int i = 0; i < list.size() - 1; i++) {//最多做n-1趟排序
            //对当前无序区间进行排序(j的范围很关键，这个范围是在逐步缩小的)
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(j).getSalenum() < list.get(j + 1).getSalenum()) { //把小的值交换到后面
                    MyBean.DataBean temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
//        linAdapter.notifyDataSetChanged();
        goodAdapter.notifyDataSetChanged();
    }

    //冒泡排序 销量 从小到大
    private void mppxxlxd() {
        for (int i = 0; i < list.size() - 1; i++) {//最多做n-1趟排序
            //对当前无序区间进行排序(j的范围很关键，这个范围是在逐步缩小的)
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(j).getSalenum() > list.get(j + 1).getSalenum()) { //把小的值交换到后面
                    MyBean.DataBean temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
//        linAdapter.notifyDataSetChanged();
        goodAdapter.notifyDataSetChanged();
    }
}
