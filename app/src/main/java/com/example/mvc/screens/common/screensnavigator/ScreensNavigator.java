package com.example.mvc.screens.common.screensnavigator;

import android.content.Context;

import com.example.mvc.screens.questiondetails.QuestionDetailsActivity;
import com.example.mvc.screens.questionslist.QuestionsListActivity;

public class ScreensNavigator {
    private final Context mContext;

    public ScreensNavigator(Context mContext) {
        this.mContext = mContext;
    }

    public void toDialogDetails(String id) {
        QuestionDetailsActivity.start(mContext, id);
    }

    public void toQuestionsListClearTop() {
        QuestionsListActivity.startClearTop(mContext);
    }
}
