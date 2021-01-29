package com.example.mvc.screens.common.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.example.mvc.R;
import com.example.mvc.screens.common.controllers.BackpressDispatcher;
import com.example.mvc.screens.common.controllers.BackpressListener;
import com.example.mvc.screens.common.controllers.BaseActivity;
import com.example.mvc.screens.common.controllers.FragmentFrameWrapper;
import com.example.mvc.screens.questionslist.QuestionsListFragment;

import java.util.HashSet;
import java.util.Set;

// NOTE: This class represents the Application Layer
public class MainActivity extends BaseActivity implements BackpressDispatcher, FragmentFrameWrapper
{
    public static void startClearTop(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    private final Set<BackpressListener> mBackPressListeners = new HashSet<>();

    @Override
    public void registerListener(BackpressListener listener) {
        mBackPressListeners.add(listener);
    }

    @Override
    public void unregisterListener(BackpressListener listener) {
        mBackPressListeners.remove(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_content_frame);
        if (savedInstanceState == null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            QuestionsListFragment fragment = new QuestionsListFragment();
            ft.add(R.id.frame_content, fragment).commit();
        }     }

    @Override
    public void onBackPressed() {
        boolean isBackPressConsumedByAnyListener = false;
        for (BackpressListener listener: mBackPressListeners){
            if (listener.onBackPressed()){
                isBackPressConsumedByAnyListener = true;
            }
        }

        if (!isBackPressConsumedByAnyListener) super.onBackPressed();
    }

    @Override
    public FrameLayout getFragmentFrame() {
        return findViewById(R.id.frame_content);
    }
}