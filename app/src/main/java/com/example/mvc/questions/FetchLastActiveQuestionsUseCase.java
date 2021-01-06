package com.example.mvc.questions;

import android.util.Log;

import com.example.mvc.common.BaseObservable;
import com.example.mvc.common.Constants;
import com.example.mvc.networking.QuestionSchema;
import com.example.mvc.networking.QuestionsListResponseSchema;
import com.example.mvc.networking.StackoverflowApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchLastActiveQuestionsUseCase extends BaseObservable<FetchLastActiveQuestionsUseCase.Listener> {

    private StackoverflowApi mStackoverflowApi;

    public FetchLastActiveQuestionsUseCase(StackoverflowApi mStackoverflowApi) {
        this.mStackoverflowApi = mStackoverflowApi;
    }

    public interface Listener {
        void onFetchLastActiveQuestionsFailed();
        void onFetchLastActiveQuestionsFetched(List<QuestionSchema> questions);
    }

    public void fetchLastActiveQuestions(){
        mStackoverflowApi.fetchLastActiveQuestions(Constants.QUESTIONS_LIST_PAGE_SIZE)
                .enqueue(new Callback<QuestionsListResponseSchema>() {
                    @Override
                    public void onResponse(Call<QuestionsListResponseSchema> call, Response<QuestionsListResponseSchema> response) {
                        if (response.isSuccessful()) {
                            notifySuccess(response.body().getQuestions());
                        } else {
                            Log.d("QuestionsListActivity", "onResponse failed " + response.message());
                            notifyFailure();
                        }
                    }

                    @Override
                    public void onFailure(Call<QuestionsListResponseSchema> call, Throwable t) {
                        Log.d("QuestionsListActivity", "onFailure " + t.getLocalizedMessage());
                        notifyFailure();
                    }
                });
    }

    private void notifyFailure() {
        for (Listener listener: getListeners()){
            listener.onFetchLastActiveQuestionsFailed();
        }
    }

    private void notifySuccess(List<QuestionSchema> questions) {
        for (Listener listener: getListeners()){
            listener.onFetchLastActiveQuestionsFetched(questions);
        }
    }

}
