package com.example.mvc.screens.questionslist;

import android.os.Bundle;

import com.example.mvc.screens.common.controllers.BaseActivity;

// NOTE: This class represents the Application Layer
public class QuestionsListActivity extends BaseActivity
{

    private QuestionsListController mQuestionsListController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QuestionsListViewMvc viewMvc = getCompositionRoot().getViewMvcFactory().getQuestionsListViewMvc(null);
        mQuestionsListController = getCompositionRoot().getQuestionsListController();
        mQuestionsListController.bindView(viewMvc);
        setContentView(viewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQuestionsListController.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mQuestionsListController.onStop();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (!mQuestionsListController.onBackpressed()){
            super.onBackPressed();
        }
    }
}
