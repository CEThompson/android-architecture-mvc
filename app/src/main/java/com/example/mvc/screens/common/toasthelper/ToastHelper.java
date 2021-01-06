package com.example.mvc.screens.common.toasthelper;

import android.content.Context;
import android.widget.Toast;

import com.example.mvc.R;

public class ToastHelper {
    private final Context mContext;

    public ToastHelper(Context mContext) {
        this.mContext = mContext;
    }

    public void showUseCaseError() {
        Toast.makeText(mContext, R.string.error_network_call_failed, Toast.LENGTH_SHORT).show();
    }
}
