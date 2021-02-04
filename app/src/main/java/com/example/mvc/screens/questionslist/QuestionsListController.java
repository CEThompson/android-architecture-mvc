package com.example.mvc.screens.questionslist;

import com.example.mvc.questions.FetchLastActiveQuestionsUseCase;
import com.example.mvc.questions.Question;
import com.example.mvc.screens.common.controllers.BackpressDispatcher;
import com.example.mvc.screens.common.controllers.BackpressListener;
import com.example.mvc.screens.common.dialogs.DialogsEventBus;
import com.example.mvc.screens.common.dialogs.DialogsManager;
import com.example.mvc.screens.common.dialogs.promptdialog.PromptDialogEvent;
import com.example.mvc.screens.common.toasthelper.ToastHelper;
import com.example.mvc.screens.common.screensnavigator.ScreensNavigator;

import java.io.Serializable;
import java.util.List;

public class QuestionsListController
        implements
        QuestionsListViewMvcImpl.Listener,
        FetchLastActiveQuestionsUseCase.Listener, DialogsEventBus.Listener {

    private enum ScreenState {
        IDLE, FETCHING_QUESTIONS, QUESTIONS_LIST_SHOWN, NETWORK_ERROR
    }

    private final FetchLastActiveQuestionsUseCase mFetchLastActiveQuestionsUseCase;
    private final ScreensNavigator mScreensNavigator;
    private final DialogsManager mDialogManager;
    private final DialogsEventBus mDialogsEventBus;

    private QuestionsListViewMvc mViewMvc;

    private ScreenState mScreenState = ScreenState.IDLE;

    public QuestionsListController(FetchLastActiveQuestionsUseCase fetchLastActiveQuestionsUseCase,
                                   ScreensNavigator screensNavigator,
                                   DialogsManager dialogsManager,
                                   DialogsEventBus dialogsEventBus) {
        this.mFetchLastActiveQuestionsUseCase = fetchLastActiveQuestionsUseCase;
        this.mScreensNavigator = screensNavigator;
        this.mDialogManager = dialogsManager;
        this.mDialogsEventBus = dialogsEventBus;
    }

    public void bindView(QuestionsListViewMvc viewMvc){
        mViewMvc = viewMvc;
    }

    public SavedState getSavedState() {
        return new SavedState(mScreenState);
    }

    public void restoreSavedState(SavedState savedState) {
        mScreenState = savedState.mScreenState;
    }

    public void onStart(){
        mViewMvc.registerListener(this);
        mFetchLastActiveQuestionsUseCase.registerListener(this);
        mDialogsEventBus.registerListener(this);

        if (mScreenState != ScreenState.NETWORK_ERROR) {
            fetchQuestionsAndNotify();
        }


    }

    public void onStop(){
        mViewMvc.unregisterListener(this);
        mFetchLastActiveQuestionsUseCase.unregisterListener(this);
        mDialogsEventBus.registerListener(this);
    }

    @Override
    public void onQuestionClicked(Question question) {
        mScreensNavigator.toQuestionDetails(question.getId());
    }

    @Override
    public void onFetchLastActiveQuestionsFetched(List<Question> questions) {
        mScreenState = ScreenState.QUESTIONS_LIST_SHOWN;
        mViewMvc.hideProgressIndication();
        mViewMvc.bindQuestions(questions);
    }

    @Override
    public void onFetchLastActiveQuestionsFailed() {
        mScreenState = ScreenState.NETWORK_ERROR;
        mViewMvc.hideProgressIndication();
        mDialogManager.showUseCaseErrorDialog("NETWORK_ERROR_TAG");
    }

    @Override
    public void onDialogEvent(Object event) {
        if (event instanceof PromptDialogEvent){
            switch (((PromptDialogEvent) event).getClickedButton()){
                case POSITIVE:
                    fetchQuestionsAndNotify();
                    break;
                case NEGATIVE:
                    mScreenState = ScreenState.IDLE;
                    break;
            }
        }
    }

    private void fetchQuestionsAndNotify() {
        mScreenState = ScreenState.FETCHING_QUESTIONS;
        mViewMvc.showProgressIndication();
        mFetchLastActiveQuestionsUseCase.fetchLastActiveQuestions();
    }

    public static class SavedState implements Serializable {
        private final ScreenState mScreenState;

        public SavedState(ScreenState screenState) {
            mScreenState = screenState;
        }
    }
}
