package com.example.mvc;

import android.app.Application;

import com.example.mvc.common.di.CompositionRoot;

public class CustomApplication extends Application {

    private CompositionRoot mCompositionRoot;

    @Override
    public void onCreate() {
        super.onCreate();
        mCompositionRoot = new CompositionRoot();
    }

    public CompositionRoot getmCompositionRoot() {
        return mCompositionRoot;
    }
}
