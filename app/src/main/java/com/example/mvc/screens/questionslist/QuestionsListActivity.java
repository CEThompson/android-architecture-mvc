package com.example.mvc.screens.questionslist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.mvc.screens.common.controllers.BaseActivity;

// NOTE: This class represents the Application Layer
public class QuestionsListActivity extends BaseActivity
{

    private QuestionsListController mQuestionsListController;

    public static void startClearTop(Context context) {
        Intent intent = new Intent(context, QuestionsListActivity.class);
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

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
