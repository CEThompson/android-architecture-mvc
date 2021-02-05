package com.example.mvc.screens.common.controllers;

import android.support.v7.app.AppCompatActivity;

import com.example.mvc.common.CustomApplication;
import com.example.mvc.common.di.ActivityCompositionRoot;
import com.example.mvc.common.di.ControllerCompositionRoot;

public class BaseActivity extends AppCompatActivity {

    private ControllerCompositionRoot mControllerCompositionRoot;
    private ActivityCompositionRoot mActivityCompositionRoot;

    public ActivityCompositionRoot getActivityCompositionRoot() {
        if (mActivityCompositionRoot == null) {
            mActivityCompositionRoot = new ActivityCompositionRoot(
                    ((CustomApplication) getApplication()).getCompositionRoot(),
                    this);
        }
        return mActivityCompositionRoot;
    }

    protected ControllerCompositionRoot getCompositionRoot() {
        if (mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    getActivityCompositionRoot()
            );
        }
        return mControllerCompositionRoot;
    }

}
