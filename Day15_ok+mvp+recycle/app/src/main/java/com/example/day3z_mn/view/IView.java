package com.example.day3z_mn.view;

public interface IView<T> {
    void showLoagin();
    void success(T data);
    void error(String error);
}
