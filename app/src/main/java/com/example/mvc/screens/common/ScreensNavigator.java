package com.example.mvc.screens.common;

import android.content.Context;

import com.example.mvc.screens.questiondetails.QuestionDetailsActivity;

public class ScreensNavigator {
    private final Context mContext;

    public ScreensNavigator(Context mContext) {
        this.mContext = mContext;
    }

    public void toDialogDetails(String id) {
        QuestionDetailsActivity.start(mContext, id);
    }
}
