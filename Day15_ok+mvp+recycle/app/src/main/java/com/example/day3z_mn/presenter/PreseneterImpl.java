package com.example.day3z_mn.presenter;

import com.example.day3z_mn.callback.MyCallBack;
import com.example.day3z_mn.model.ModelImpl;
import com.example.day3z_mn.view.IView;

public class PreseneterImpl implements IPesenter,MyCallBack {

    private IView iView;
    private ModelImpl model;

    public PreseneterImpl(IView iView) {
        this.iView = iView;
        model = new ModelImpl();
    }

    @Override
    public void getMyData(String murl) {
        iView.showLoagin();
        model.getMyData(murl,this);
    }

    @Override
    public void success(Object data) {
        iView.success(data);
    }

    @Override
    public void error(String error) {
        iView.error(error);
    }

//    内存优化，避免内存泄漏
    public void onDetach(){
        if(model!=null){
            model=null;
        }
        if(iView!=null){
            iView=null;
        }
    }
}
