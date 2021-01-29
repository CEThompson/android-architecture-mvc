package com.example.mvc.screens.common.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.mvc.R;
import com.example.mvc.screens.common.controllers.BackpressDispatcher;
import com.example.mvc.screens.common.controllers.BackpressListener;
import com.example.mvc.screens.common.controllers.BaseActivity;
import com.example.mvc.screens.common.fragmentframehelper.FragmentFrameWrapper;
import com.example.mvc.screens.common.navdrawer.NavDrawerHelper;
import com.example.mvc.screens.common.navdrawer.NavDrawerViewMvc;
import com.example.mvc.screens.common.screensnavigator.ScreensNavigator;

import java.util.HashSet;
import java.util.Set;

// NOTE: This class represents the Application Layer
public class MainActivity extends BaseActivity implements
        //BackpressDispatcher,
        FragmentFrameWrapper,
        NavDrawerViewMvc.Listener,
        NavDrawerHelper {

    private final Set<BackpressListener> mBackPressListeners = new HashSet<>();

    private ScreensNavigator mScreensNavigator;
    private NavDrawerViewMvc mViewMvc;

    /*@Override
    public void registerListener(BackpressListener listener) {
        mBackPressListeners.add(listener);
    }

    @Override
    public void unregisterListener(BackpressListener listener) {
        mBackPressListeners.remove(listener);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScreensNavigator = getCompositionRoot().getScreensNavigator();
        mViewMvc = getCompositionRoot().getViewMvcFactory().getNavDrawerViewMvc(null);
        setContentView(mViewMvc.getRootView());
        if (savedInstanceState == null) mScreensNavigator.toQuestionsList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViewMvc.registerListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
    }

    @Override
    public void onBackPressed() {
        /*boolean isBackPressConsumedByAnyListener = false;
        for (BackpressListener listener : mBackPressListeners) {
            if (listener.onBackPressed()) {
                isBackPressConsumedByAnyListener = true;
            }
        }

        if (!isBackPressConsumedByAnyListener) super.onBackPressed();*/
        if (mViewMvc.isDrawerOpen()) mViewMvc.closeDrawer();
        else super.onBackPressed();
    }

    @Override
    public FrameLayout getFragmentFrame() {
        return mViewMvc.getFragmentFrame();
    }

    @Override
    public void onQuestionsListClicked() {
        mScreensNavigator.toQuestionsList();
    }

    @Override
    public void openDrawer() {
        mViewMvc.openDrawer();
    }

    @Override
    public void closeDrawer() {
        mViewMvc.closeDrawer();
    }

    @Override
    public boolean isDrawerOpen() {
        return mViewMvc.isDrawerOpen();
    }
}
