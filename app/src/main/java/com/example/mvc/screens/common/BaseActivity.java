package com.example.mvc.screens.common;

import android.support.v7.app.AppCompatActivity;

import com.example.mvc.CustomApplication;
import com.example.mvc.common.di.CompositionRoot;

public class BaseActivity extends AppCompatActivity {

    protected CompositionRoot getCompositionRoot(){
        return ((CustomApplication) getApplication()).getmCompositionRoot();
    }

}
