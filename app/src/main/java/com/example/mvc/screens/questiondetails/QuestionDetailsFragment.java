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

    private static final String EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID";

    private static final String DIALOG_ID_NETWORK_ERROR = "DIALOG_ID_NETWORK_ERROR";

    private static final String SAVED_STATE = "SAVED_STATE";

    public static QuestionDetailsFragment newInstance(String questionId) {
        Bundle args = new Bundle();
        args.putString(EXTRA_QUESTION_ID, questionId);
        QuestionDetailsFragment fragment = new QuestionDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private enum ScreenState {
        IDLE, DETAILS_SHOWN, NETWORK_ERROR
    }

    private FetchQuestionDetailsUseCase mFetchQuestionsDetailsUseCase;
    private DialogsManager mDialogsManager;
    private ScreensNavigator mScreensNavigator;
    private QuestionDetailsViewMvc mViewMvc;
    private DialogsEventBus mDialogsEventBus;
    private ScreenState mScreenState = ScreenState.IDLE;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            mScreenState = (ScreenState) savedInstanceState.getSerializable(SAVED_STATE);
        }
    }

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
        mDialogsEventBus.registerListener(this);

        mViewMvc.showProgressIndication();

        // Only fetch question details if dialog not shown
        if (mScreenState != ScreenState.NETWORK_ERROR)
        if (!DIALOG_ID_NETWORK_ERROR.equals(mDialogsManager.getShownDialogTag())) {
            mFetchQuestionsDetailsUseCase.fetchQuestionDetailsAndNotify(getQuestionId());
        }
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
        mScreenState = ScreenState.DETAILS_SHOWN;
        mViewMvc.hideProgressIndication();
        mViewMvc.bindQuestion(questionDetails);
    }

    @Override
    public void onQuestionDetailsFetchFailed() {
        mScreenState = ScreenState.NETWORK_ERROR;
        mViewMvc.hideProgressIndication();
        mDialogsManager.showUseCaseErrorDialog(DIALOG_ID_NETWORK_ERROR);
    }

    @Override
    public void onNavigateUpClicked() {
        mScreensNavigator.navigateUp();
    }

    @Override
    public void onLocationRequestClicked() {
        // TODO handle permission
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
            mScreenState = ScreenState.IDLE;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SAVED_STATE, mScreenState);
    }

}
