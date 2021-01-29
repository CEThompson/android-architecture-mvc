package com.example.mvc.screens.common.screensnavigator;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.mvc.screens.common.controllers.FragmentFrameWrapper;
import com.example.mvc.screens.questiondetails.QuestionDetailsFragment;
import com.example.mvc.screens.questionslist.QuestionsListFragment;

public class ScreensNavigator {

    private final FragmentManager mFragmentManager;
    private final FragmentFrameWrapper mWrapper;

    public ScreensNavigator(FragmentManager fm, FragmentFrameWrapper wrapper) {
        this.mFragmentManager = fm;
        this.mWrapper = wrapper;
    }

    public void toQuestionDetails(String id) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(mWrapper.getFragmentFrame().getId(), QuestionDetailsFragment.newInstance(id)).commit();
    }

    public void toQuestionsList() {
        mFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.replace(mWrapper.getFragmentFrame().getId(), QuestionsListFragment.newInstance()).commit();
    }
}
