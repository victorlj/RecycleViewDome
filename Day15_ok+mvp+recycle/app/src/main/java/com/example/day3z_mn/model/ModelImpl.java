package com.example.day3z_mn.model;

import android.os.Handler;
import android.os.Message;

import com.example.day3z_mn.bean.MyBean;
import com.example.day3z_mn.callback.MyCallBack;
import com.example.day3z_mn.utils.Okutil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ModelImpl implements Model {
    private MyCallBack myCallBack;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                Gson gson = new Gson();
                MyBean myBean = gson.fromJson((String) msg.obj, MyBean.class);
                myCallBack.success(myBean);
            }
        }
    };

    /**
     * 首页的布局
     * @param murl
     * @param myCallBack
     */
    @Override
    public void getMyData(String murl, final MyCallBack myCallBack) {
        this.myCallBack =myCallBack;
        Okutil.getInstance().getsync(murl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                myCallBack.error(e.getMessage()+"");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String s = response.body().string();
                handler.sendMessage(handler.obtainMessage(0,s));
            }
        });
    }
}
