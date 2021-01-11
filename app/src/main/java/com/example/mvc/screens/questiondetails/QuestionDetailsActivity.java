package com.example.mvc.screens.questiondetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.mvc.questions.FetchQuestionDetailsUseCase;
import com.example.mvc.questions.QuestionDetails;
import com.example.mvc.screens.common.controllers.BaseActivity;
import com.example.mvc.screens.common.navdrawer.DrawerItems;
import com.example.mvc.screens.common.screensnavigator.ScreensNavigator;
import com.example.mvc.screens.common.toasthelper.ToastHelper;


// NOTE: This activity represents a controller
// It orchestrates usecases when appropriate
public class QuestionDetailsActivity extends BaseActivity
        implements
        FetchQuestionDetailsUseCase.Listener,
        QuestionDetailsViewMvc.Listener {

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";

    public static void start(Context context, String questionId) {
        Intent intent = new Intent(context, QuestionDetailsActivity.class);
        intent.putExtra(EXTRA_QUESTION_ID, questionId);
        context.startActivity(intent);
    }

    private FetchQuestionDetailsUseCase mFetchQuestionsDetailsUseCase;
    private ToastHelper mToastHelper;
    private ScreensNavigator mScreensNavigator;
    private QuestionDetailsViewMvc mViewMvc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFetchQuestionsDetailsUseCase = getCompositionRoot().getFetchQuestionDetailsUseCase();
        mToastHelper = getCompositionRoot().getMessagesDisplayer();
        mViewMvc = getCompositionRoot().getViewMvcFactory().getQuestionDetailsViewMvc(null);
        mScreensNavigator = getCompositionRoot().getScreensNavigator();

        setContentView(mViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFetchQuestionsDetailsUseCase.registerListener(this);
        mViewMvc.registerListener(this);
        mViewMvc.showProgressIndication();
        mFetchQuestionsDetailsUseCase.fetchQuestionDetailsAndNotify(getQuestionId());
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFetchQuestionsDetailsUseCase.unregisterListener(this);
        mViewMvc.unregisterListener(this);
    }

    private String getQuestionId() {
        return getIntent().getStringExtra(EXTRA_QUESTION_ID);
    }

    @Override
    public void onQuestionDetailsFetched(QuestionDetails questionDetails) {
        bindQuestionDetails(questionDetails);
    }

    private void bindQuestionDetails(QuestionDetails details) {
        mViewMvc.hideProgressIndication();
        mViewMvc.bindQuestion(details);
    }

    @Override
    public void onQuestionDetailsFetchFailed() {
        mViewMvc.hideProgressIndication();
        mToastHelper.showUseCaseError();
    }

    @Override
    public void onNavigateUpClicked() {
        onBackPressed();
    }

    @Override
    public void onDrawerItemClicked(DrawerItems item) {
        switch (item){
            case QUESTIONS_LIST:
                mScreensNavigator.toQuestionsListClearTop();
        }
    }

    @Override
    public void onBackPressed() {
        if (mViewMvc.isDrawerOpen()) mViewMvc.closeDrawer();
        else super.onBackPressed();
    }
}
