package com.example.day3z_mn.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    protected abstract int getLayout();

    protected abstract void getFindByid();

    protected abstract void getonClick();

    protected abstract void getProcassLogin();
    void init(){
        if(getLayout()!=0){
            setContentView(getLayout());
            getFindByid();
            getonClick();
            getProcassLogin();
        }else{
            throw new IllegalStateException("请添加布局");
        }
    }
}
