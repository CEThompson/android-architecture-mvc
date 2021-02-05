package com.example.mvc.common.di;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;

import com.example.mvc.common.permissions.PermissionsHelper;
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

    private final ActivityCompositionRoot mActivityCompositionRoot;

    public ControllerCompositionRoot(ActivityCompositionRoot activityCompositionRoot) {
        this.mActivityCompositionRoot = activityCompositionRoot;
    }

    private FragmentActivity getActivity(){
        return mActivityCompositionRoot.getActivity();
    }

    private FragmentManager getFragmentManager(){
        return getActivity().getSupportFragmentManager();
    }

    private StackoverflowApi getStackOverflowApi() {
        return mActivityCompositionRoot.getStackOverflowApi();
    }

    private LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mActivityCompositionRoot.getActivity());
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
                getDialogsManager(),
                getDialogsEventBus());
    }

    private Context getContext() {
        return getActivity();
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
        return mActivityCompositionRoot.getDialogsEventBus();
    }

    public PermissionsHelper getPermissionsHelper() {
        return mActivityCompositionRoot.getPermissionsHelper();
    }

}
