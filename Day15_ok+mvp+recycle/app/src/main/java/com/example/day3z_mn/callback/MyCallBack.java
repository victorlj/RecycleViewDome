package com.example.day3z_mn.callback;

public interface MyCallBack<T> {
    void success(T data);
    void error(String error);
}
