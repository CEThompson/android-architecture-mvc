package com.example.mvc.screens.questiondetails;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mvc.R;
import com.example.mvc.questions.FetchQuestionDetailsUseCase;
import com.example.mvc.questions.QuestionDetails;
import com.example.mvc.screens.common.controllers.BackpressDispatcher;
import com.example.mvc.screens.common.controllers.BackpressListener;
import com.example.mvc.screens.common.controllers.BaseFragment;
import com.example.mvc.screens.common.navdrawer.DrawerItems;
import com.example.mvc.screens.common.screensnavigator.ScreensNavigator;
import com.example.mvc.screens.common.toasthelper.ToastHelper;

public class QuestionDetailsFragment extends BaseFragment implements
        BackpressListener,
        FetchQuestionDetailsUseCase.Listener,
        QuestionDetailsViewMvc.Listener {

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";

    public static QuestionDetailsFragment newInstance(String questionId) {
        Bundle args = new Bundle();
        args.putString(EXTRA_QUESTION_ID, questionId);
        QuestionDetailsFragment fragment = new QuestionDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private BackpressDispatcher mDispatcher;

    private FetchQuestionDetailsUseCase mFetchQuestionsDetailsUseCase;
    private ToastHelper mToastHelper;
    private ScreensNavigator mScreensNavigator;
    private QuestionDetailsViewMvc mViewMvc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFetchQuestionsDetailsUseCase = getCompositionRoot().getFetchQuestionDetailsUseCase();
        mToastHelper = getCompositionRoot().getToastHelper();
        
        // TODO: determine why passing container causes this to fail while vasily's code works
        mViewMvc = getCompositionRoot().getViewMvcFactory().getQuestionDetailsViewMvc(null);
        mScreensNavigator = getCompositionRoot().getScreensNavigator();
        mDispatcher = getCompositionRoot().getBackpressDispatcher();
        return mViewMvc.getRootView();
    }


    @Override
    public void onStart() {
        super.onStart();
        mFetchQuestionsDetailsUseCase.registerListener(this);
        mViewMvc.registerListener(this);
        mViewMvc.showProgressIndication();
        mFetchQuestionsDetailsUseCase.fetchQuestionDetailsAndNotify(getQuestionId());
        mDispatcher.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mFetchQuestionsDetailsUseCase.unregisterListener(this);
        mViewMvc.unregisterListener(this);
        mDispatcher.unregisterListener(this);
    }

    private String getQuestionId() {
        return getArguments().getString(EXTRA_QUESTION_ID);
    }

    @Override
    public void onQuestionDetailsFetched(QuestionDetails questionDetails) {
        mViewMvc.hideProgressIndication();
        mViewMvc.bindQuestion(questionDetails);
    }

    @Override
    public void onQuestionDetailsFetchFailed() {
        mViewMvc.hideProgressIndication();
        mToastHelper.showUseCaseError();
    }

    @Override
    public void onNavigateUpClicked() {
        mScreensNavigator.navigateUp();
    }

    @Override
    public void onDrawerItemClicked(DrawerItems item) {
        switch (item) {
            case QUESTIONS_LIST:
                mScreensNavigator.toQuestionsList();
        }
    }

    @Override
    public boolean onBackPressed() {
        Log.d("QuestionDetailsFragment", "on backpressed");
        if (mViewMvc.isDrawerOpen()) {
            mViewMvc.closeDrawer();
            Log.d("QuestionDetailsFragment", "returning true");
            return true;
        } else {
            Log.d("QuestionDetailsFragment", "returning false");
            return false;
        }
    }

}
