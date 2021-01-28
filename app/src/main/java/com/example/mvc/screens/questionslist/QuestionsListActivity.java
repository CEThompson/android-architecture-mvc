package com.example.mvc.screens.questionslist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.example.mvc.R;
import com.example.mvc.screens.common.controllers.BackpressListener;
import com.example.mvc.screens.common.controllers.BaseActivity;

// NOTE: This class represents the Application Layer
public class QuestionsListActivity extends BaseActivity
{

    public static void startClearTop(Context context) {
        Intent intent = new Intent(context, QuestionsListActivity.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    private BackpressListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_content_frame);

        QuestionsListFragment fragment;
        if (savedInstanceState == null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            fragment = new QuestionsListFragment();
            ft.add(R.id.frame_content, fragment).commit();
        } else {
            fragment = (QuestionsListFragment) getSupportFragmentManager().findFragmentById(R.id.frame_content);
        }

        mListener = fragment;
    }

    @Override
    public void onBackPressed() {
        if (!mListener.onBackPressed()) super.onBackPressed();
    }
}
