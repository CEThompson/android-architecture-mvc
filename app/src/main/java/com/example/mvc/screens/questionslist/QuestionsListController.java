package com.example.mvc.screens.questionslist;

import com.example.mvc.questions.FetchLastActiveQuestionsUseCase;
import com.example.mvc.questions.Question;
import com.example.mvc.screens.common.controllers.BackpressDispatcher;
import com.example.mvc.screens.common.controllers.BackpressListener;
import com.example.mvc.screens.common.toasthelper.ToastHelper;
import com.example.mvc.screens.common.screensnavigator.ScreensNavigator;

import java.util.List;

public class QuestionsListController implements QuestionsListViewMvcImpl.Listener, FetchLastActiveQuestionsUseCase.Listener, BackpressListener {

    private final FetchLastActiveQuestionsUseCase mFetchLastActiveQuestionsUseCase;
    private QuestionsListViewMvc mViewMvc;

    private final ScreensNavigator mScreensNavigator;
    private final ToastHelper mToastHelper;

    private final BackpressDispatcher mDispatcher;

    public QuestionsListController(FetchLastActiveQuestionsUseCase fetchLastActiveQuestionsUseCase, ScreensNavigator screensNavigator, ToastHelper toastHelper, BackpressDispatcher backpressDispatcher) {
        this.mFetchLastActiveQuestionsUseCase = fetchLastActiveQuestionsUseCase;
        this.mScreensNavigator = screensNavigator;
        this.mToastHelper = toastHelper;
        this.mDispatcher = backpressDispatcher;
    }

    public void bindView(QuestionsListViewMvc viewMvc){
        mViewMvc = viewMvc;
    }

    public void onStart(){
        mFetchLastActiveQuestionsUseCase.registerListener(this);
        mViewMvc.registerListener(this);
        mViewMvc.showProgressIndication();
        mFetchLastActiveQuestionsUseCase.fetchLastActiveQuestions();
        mDispatcher.registerListener(this);
    }

    public void onStop(){
        mViewMvc.unregisterListener(this);
        mFetchLastActiveQuestionsUseCase.unregisterListener(this);
        mDispatcher.unregisterListener(this);
    }

    @Override
    public void onQuestionClicked(Question question) {
        mScreensNavigator.toQuestionDetails(question.getId());
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

    /*public boolean onBackpressed() {
        if (mViewMvc.isDrawerOpen()) {
            mViewMvc.closeDrawer();
            return true;
        } else return false;
    }*/

    @Override
    public boolean onBackPressed() {
        if (mViewMvc.isDrawerOpen()) {
            mViewMvc.closeDrawer();
            return true;
        } else return false;
    }
}
