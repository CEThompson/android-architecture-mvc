package com.example.mvc.screens.questionslist;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mvc.R;
import com.example.mvc.common.Constants;
import com.example.mvc.networking.QuestionSchema;
import com.example.mvc.networking.QuestionsListResponseSchema;
import com.example.mvc.networking.StackoverflowApi;
import com.example.mvc.questions.FetchLastActiveQuestionsUseCase;
import com.example.mvc.questions.Question;
import com.example.mvc.screens.common.BaseActivity;
import com.example.mvc.screens.questiondetails.QuestionDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// NOTE: This class represents the Application Layer
public class QuestionsListActivity extends BaseActivity
        implements QuestionsListViewMvcImpl.Listener, FetchLastActiveQuestionsUseCase.Listener {

    private FetchLastActiveQuestionsUseCase mFetchLastActiveQuestionsUseCase;
    private QuestionsListViewMvc mViewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewMvc = getCompositionRoot().getViewMvcFactory().getQuestionsListViewMvc(null);
        mFetchLastActiveQuestionsUseCase = getCompositionRoot().getFetchLastActiveQuestionsUseCase();
        setContentView(mViewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFetchLastActiveQuestionsUseCase.registerListener(this);
        mViewMvc.registerListener(this);
        mViewMvc.showProgressIndication();
        mFetchLastActiveQuestionsUseCase.fetchLastActiveQuestions();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViewMvc.unregisterListener(this);
        mFetchLastActiveQuestionsUseCase.unregisterListener(this);
    }


    private void bindQuestions(List<QuestionSchema> questionSchemas) {
        List<Question> questions = new ArrayList<>(questionSchemas.size());
        for (QuestionSchema questionSchema : questionSchemas) {
            questions.add(new Question(questionSchema.getId(), questionSchema.getTitle()));
        }
        mViewMvc.bindQuestions(questions);
    }

    @Override
    public void onQuestionClicked(Question question) {
        QuestionDetailsActivity.start(this, question.getId());
    }

    @Override
    public void onFetchLastActiveQuestionsFailed() {
        mViewMvc.hideProgressIndication();
        Toast.makeText(this, R.string.error_network_call_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFetchLastActiveQuestionsFetched(List<QuestionSchema> questions) {
        mViewMvc.hideProgressIndication();
        bindQuestions(questions);
    }
}
