package com.example.day3z_mn.model;


import com.example.day3z_mn.callback.MyCallBack;

public interface Model {
    void getMyData(String murl, MyCallBack myCallBack);
}
