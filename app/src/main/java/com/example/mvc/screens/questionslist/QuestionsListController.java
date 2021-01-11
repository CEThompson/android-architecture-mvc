package com.example.mvc.screens.questionslist;

import com.example.mvc.questions.FetchLastActiveQuestionsUseCase;
import com.example.mvc.questions.Question;
import com.example.mvc.screens.common.toasthelper.ToastHelper;
import com.example.mvc.screens.common.screensnavigator.ScreensNavigator;

import java.util.List;

public class QuestionsListController implements QuestionsListViewMvcImpl.Listener, FetchLastActiveQuestionsUseCase.Listener {

    private final FetchLastActiveQuestionsUseCase mFetchLastActiveQuestionsUseCase;
    private QuestionsListViewMvc mViewMvc;

    private final ScreensNavigator mScreensNavigator;
    private final ToastHelper mToastHelper;

    public QuestionsListController(FetchLastActiveQuestionsUseCase mFetchLastActiveQuestionsUseCase, ScreensNavigator mScreensNavigator, ToastHelper mToastHelper) {
        this.mFetchLastActiveQuestionsUseCase = mFetchLastActiveQuestionsUseCase;
        this.mScreensNavigator = mScreensNavigator;
        this.mToastHelper = mToastHelper;
    }

    public void bindView(QuestionsListViewMvc viewMvc){
        mViewMvc = viewMvc;
    }

    public void onStart(){
        mFetchLastActiveQuestionsUseCase.registerListener(this);
        mViewMvc.registerListener(this);
        mViewMvc.showProgressIndication();
        mFetchLastActiveQuestionsUseCase.fetchLastActiveQuestions();
    }

    public void onStop(){
        mViewMvc.unregisterListener(this);
        mFetchLastActiveQuestionsUseCase.unregisterListener(this);
    }

    @Override
    public void onQuestionClicked(Question question) {
        mScreensNavigator.toDialogDetails(question.getId());
    }

    @Override
    public void onQuestionListClicked() {
        // this is the questions list screen - no-op
        // empty by design
    }

    @Override
    public void onFetchLastActiveQuestionsFetched(List<Question> questions) {
        mViewMvc.hideProgressIndication();
        mViewMvc.bindQuestions(questions);
    }

    @Override
    public void onFetchLastActiveQuestionsFailed() {
        mViewMvc.hideProgressIndication();
        mToastHelper.showUseCaseError();
    }

    public boolean onBackpressed() {
        if (mViewMvc.isDrawerOpen()) {
            mViewMvc.closeDrawer();
            return true;
        } else return false;
    }
}
