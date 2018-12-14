package com.example.day3z_mn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.day3z_mn.base.BaseActivity;

public class Main2Activity extends BaseActivity {


    private WebView web;
    private String url;

    @Override
    protected int getLayout() {
        return R.layout.activity_main2;
    }

    @Override
    protected void getFindByid() {
        web = findViewById(R.id.web);
    }

    @Override
    protected void getonClick() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        WebSettings webSettings=web.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true);

        web.setWebViewClient(new WebViewClient());
        web.setWebChromeClient(new WebChromeClient());
        web.loadUrl(url);
    }

    @Override
    protected void getProcassLogin() {

    }

    @Override
    public void onClick(View v) {

    }
}
