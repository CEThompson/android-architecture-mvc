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
import com.example.mvc.screens.common.dialogs.DialogsEventBus;
import com.example.mvc.screens.common.dialogs.DialogsManager;
import com.example.mvc.screens.common.dialogs.infodialog.InfoDialog;
import com.example.mvc.screens.common.dialogs.promptdialog.PromptDialogEvent;
import com.example.mvc.screens.common.navdrawer.DrawerItems;
import com.example.mvc.screens.common.screensnavigator.ScreensNavigator;
import com.example.mvc.screens.common.toasthelper.ToastHelper;

public class QuestionDetailsFragment extends BaseFragment implements
        FetchQuestionDetailsUseCase.Listener,
        QuestionDetailsViewMvc.Listener, DialogsEventBus.Listener {

    public static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";

    public static QuestionDetailsFragment newInstance(String questionId) {
        Bundle args = new Bundle();
        args.putString(EXTRA_QUESTION_ID, questionId);
        QuestionDetailsFragment fragment = new QuestionDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private FetchQuestionDetailsUseCase mFetchQuestionsDetailsUseCase;
    private DialogsManager mDialogsManager;
    private ScreensNavigator mScreensNavigator;
    private QuestionDetailsViewMvc mViewMvc;

    private DialogsEventBus mDialogsEventBus;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFetchQuestionsDetailsUseCase = getCompositionRoot().getFetchQuestionDetailsUseCase();
        mDialogsManager = getCompositionRoot().getDialogsManager();
        mViewMvc = getCompositionRoot().getViewMvcFactory().getQuestionDetailsViewMvc(container);
        mScreensNavigator = getCompositionRoot().getScreensNavigator();
        mDialogsEventBus = getCompositionRoot().getDialogsEventBus();
        return mViewMvc.getRootView();
    }


    @Override
    public void onStart() {
        super.onStart();
        mFetchQuestionsDetailsUseCase.registerListener(this);
        mViewMvc.registerListener(this);
        mViewMvc.showProgressIndication();
        mFetchQuestionsDetailsUseCase.fetchQuestionDetailsAndNotify(getQuestionId());
        mDialogsEventBus.registerListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mFetchQuestionsDetailsUseCase.unregisterListener(this);
        mViewMvc.unregisterListener(this);
        mDialogsEventBus.unregisterListener(this);
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
        mDialogsManager.showUseCaseErrorDialog(null);
    }

    @Override
    public void onNavigateUpClicked() {
        mScreensNavigator.navigateUp();
    }


    @Override
    public void onDialogEvent(Object event) {
        if (event instanceof PromptDialogEvent){
            switch (((PromptDialogEvent) event).getClickedButton()) {
                case POSITIVE:
                    mFetchQuestionsDetailsUseCase.fetchQuestionDetailsAndNotify(getQuestionId());
                case NEGATIVE:
                    break;
            }
        }
    }
}
