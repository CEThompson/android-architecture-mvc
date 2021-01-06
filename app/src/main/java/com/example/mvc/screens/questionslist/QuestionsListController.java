package com.example.mvc.screens.questionslist;

import android.widget.Toast;

import com.example.mvc.R;
import com.example.mvc.questions.FetchLastActiveQuestionsUseCase;
import com.example.mvc.questions.Question;
import com.example.mvc.screens.common.MessagesDisplayer;
import com.example.mvc.screens.common.ScreensNavigator;

import java.util.List;

public class QuestionsListController implements QuestionsListViewMvcImpl.Listener, FetchLastActiveQuestionsUseCase.Listener {

    private final FetchLastActiveQuestionsUseCase mFetchLastActiveQuestionsUseCase;
    private QuestionsListViewMvc mViewMvc;

    private final ScreensNavigator mScreensNavigator;
    private final MessagesDisplayer mMessagesDisplayer;

    public QuestionsListController(FetchLastActiveQuestionsUseCase mFetchLastActiveQuestionsUseCase, ScreensNavigator mScreensNavigator, MessagesDisplayer mMessagesDisplayer) {
        this.mFetchLastActiveQuestionsUseCase = mFetchLastActiveQuestionsUseCase;
        this.mScreensNavigator = mScreensNavigator;
        this.mMessagesDisplayer = mMessagesDisplayer;
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
    public void onFetchLastActiveQuestionsFetched(List<Question> questions) {
        mViewMvc.hideProgressIndication();
        mViewMvc.bindQuestions(questions);
    }

    @Override
    public void onFetchLastActiveQuestionsFailed() {
        mViewMvc.hideProgressIndication();
        mMessagesDisplayer.showUseCaseError();
    }

}
