package com.example.mvc.common.di;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;

import com.example.mvc.networking.StackoverflowApi;
import com.example.mvc.questions.FetchLastActiveQuestionsUseCase;
import com.example.mvc.questions.FetchQuestionDetailsUseCase;
import com.example.mvc.screens.common.ViewMvcFactory;
import com.example.mvc.screens.common.controllers.BackpressDispatcher;
import com.example.mvc.screens.common.dialogs.DialogsEventBus;
import com.example.mvc.screens.common.dialogs.DialogsManager;
import com.example.mvc.screens.common.fragmentframehelper.FragmentFrameHelper;
import com.example.mvc.screens.common.fragmentframehelper.FragmentFrameWrapper;
import com.example.mvc.screens.common.navdrawer.NavDrawerHelper;
import com.example.mvc.screens.common.screensnavigator.ScreensNavigator;
import com.example.mvc.screens.common.toasthelper.ToastHelper;
import com.example.mvc.screens.questionslist.QuestionsListController;

public class ControllerCompositionRoot {

    private final CompositionRoot mCompositionRoot;
    private FragmentActivity mActivity;

    public ControllerCompositionRoot(CompositionRoot compositionRoot, FragmentActivity activity) {
        mCompositionRoot = compositionRoot;
        mActivity = activity;
    }

    private FragmentActivity getActivity(){
        return mActivity;
    }

    private FragmentManager getFragmentManager(){
        return getActivity().getSupportFragmentManager();
    }

    private StackoverflowApi getStackOverflowApi() {
        return mCompositionRoot.getStackOverflowApi();
    }

    private LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mActivity);
    }

    public ViewMvcFactory getViewMvcFactory() {
        return new ViewMvcFactory(getLayoutInflater(), getNavDrawerHelper());
    }

    private NavDrawerHelper getNavDrawerHelper() {
        return (NavDrawerHelper) getActivity();
    }

    public FetchQuestionDetailsUseCase getFetchQuestionDetailsUseCase() {
        return new FetchQuestionDetailsUseCase(getStackOverflowApi());
    }

    public FetchLastActiveQuestionsUseCase getFetchLastActiveQuestionsUseCase() {
        return new FetchLastActiveQuestionsUseCase(getStackOverflowApi());
    }

    public QuestionsListController getQuestionsListController() {
        return new QuestionsListController(
                getFetchLastActiveQuestionsUseCase(),
                getScreensNavigator(),
                getToastHelper());
    }

    private Context getContext() {
        return mActivity;
    }

    public ScreensNavigator getScreensNavigator() {
        return new ScreensNavigator(getFragmentFrameHelper());
    }

    private FragmentFrameHelper getFragmentFrameHelper() {
        return new FragmentFrameHelper(getActivity(), getFragmentFrameWrapper(), getFragmentManager());
    }

    private FragmentFrameWrapper getFragmentFrameWrapper(){
        return (FragmentFrameWrapper) getActivity();
    }

    public ToastHelper getToastHelper() {
        return new ToastHelper(getContext());
    }

    public BackpressDispatcher getBackpressDispatcher() {
        return (BackpressDispatcher) getActivity();
    }

    public DialogsManager getDialogsManager() {
        return new DialogsManager(getContext(), getFragmentManager());
    }

    public DialogsEventBus getDialogsEventBus() {
        return mCompositionRoot.getDialogsEventBus();
    }
}
