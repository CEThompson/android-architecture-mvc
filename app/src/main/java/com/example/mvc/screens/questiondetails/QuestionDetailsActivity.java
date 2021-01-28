package com.example.mvc.screens.questiondetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.mvc.R;
import com.example.mvc.questions.FetchQuestionDetailsUseCase;
import com.example.mvc.questions.QuestionDetails;
import com.example.mvc.screens.common.controllers.BackpressListener;
import com.example.mvc.screens.common.controllers.BaseActivity;
import com.example.mvc.screens.common.navdrawer.DrawerItems;
import com.example.mvc.screens.common.screensnavigator.ScreensNavigator;
import com.example.mvc.screens.common.toasthelper.ToastHelper;
import com.example.mvc.screens.questionslist.QuestionsListFragment;

import static com.example.mvc.screens.questiondetails.QuestionDetailsFragment.newInstance;


// NOTE: This activity represents a controller
// It orchestrates usecases when appropriate
public class QuestionDetailsActivity extends BaseActivity {

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";

    public static void start(Context context, String questionId) {
        Intent intent = new Intent(context, QuestionDetailsActivity.class);
        intent.putExtra(EXTRA_QUESTION_ID, questionId);
        context.startActivity(intent);
    }

    private BackpressListener mListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_content_frame);

        QuestionDetailsFragment fragment;
        if (savedInstanceState == null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            fragment = QuestionDetailsFragment.newInstance(getQuestionId());
            ft.add(R.id.frame_content, fragment).commit();
        } else {
            fragment = (QuestionDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.frame_content);
        }

        mListener = fragment;

    }

    @Override
    public void onBackPressed() {
        Log.d("QuestionDetailsFragment", "on backpressed in activity");
        if (!mListener.onBackPressed()) super.onBackPressed();
    }

    private String getQuestionId() {
        return getIntent().getStringExtra(EXTRA_QUESTION_ID);
    }
}
