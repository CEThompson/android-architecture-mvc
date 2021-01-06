package com.example.mvc.questions;

import com.example.mvc.common.BaseObservable;
import com.example.mvc.networking.QuestionDetailsResponseSchema;
import com.example.mvc.networking.QuestionSchema;
import com.example.mvc.networking.StackoverflowApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchQuestionDetailsUseCase extends BaseObservable<FetchQuestionDetailsUseCase.Listener> {

    private final StackoverflowApi mStackoverflowApi;

    public FetchQuestionDetailsUseCase(StackoverflowApi mStackoverflowApi) {
        this.mStackoverflowApi = mStackoverflowApi;
    }

    public interface Listener {
        void onQuestionDetailsFetched(QuestionDetails questionDetails);
        void onQuestionDetailsFetchFailed();
    }

    public void fetchQuestionDetailsAndNotify(String questionId){
        mStackoverflowApi.fetchQuestionDetails(questionId)
                .enqueue(new Callback<QuestionDetailsResponseSchema>() {
                    @Override
                    public void onResponse(Call<QuestionDetailsResponseSchema> call, Response<QuestionDetailsResponseSchema> response) {
                        if (response.isSuccessful()) {
                            notifySuccess(response.body().getQuestion());
                        } else {
                            notifyFailure();
                        }
                    }

                    @Override
                    public void onFailure(Call<QuestionDetailsResponseSchema> call, Throwable t) {
                        notifyFailure();
                    }
                });
    }

    private void notifySuccess(QuestionSchema questionSchema) {
        for (Listener listener: getListeners()){
            listener.onQuestionDetailsFetched(
                    new QuestionDetails(
                            questionSchema.getId(),
                            questionSchema.getTitle(),
                            questionSchema.getBody()
                    )
            );
        }
    }

    private void notifyFailure() {
        for (Listener listener: getListeners()){
            listener.onQuestionDetailsFetchFailed();
        }
    }

}
