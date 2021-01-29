package com.example.mvc.screens.common.screensnavigator;

import com.example.mvc.screens.common.fragmentframehelper.FragmentFrameHelper;
import com.example.mvc.screens.questiondetails.QuestionDetailsFragment;
import com.example.mvc.screens.questionslist.QuestionsListFragment;

public class ScreensNavigator {

    private FragmentFrameHelper mHelper;

    public ScreensNavigator(FragmentFrameHelper helper) {
        mHelper = helper;
    }

    public void toQuestionDetails(String id) {
        mHelper.replaceFragment(QuestionDetailsFragment.newInstance(id));
    }

    public void toQuestionsList() {
        mHelper.replaceFragmentAndClearBackstack(QuestionsListFragment.newInstance());
    }

    public void navigateUp() {
        mHelper.navigateUp();
    }
}
