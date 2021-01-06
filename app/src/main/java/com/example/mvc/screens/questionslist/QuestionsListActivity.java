package com.example.mvc.screens.questionslist;

import android.os.Bundle;
import android.widget.Toast;

import com.example.mvc.R;
import com.example.mvc.networking.QuestionSchema;
import com.example.mvc.questions.FetchLastActiveQuestionsUseCase;
import com.example.mvc.questions.Question;
import com.example.mvc.screens.common.BaseActivity;
import com.example.mvc.screens.questiondetails.QuestionDetailsActivity;

import java.util.ArrayList;
import java.util.List;

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

}
