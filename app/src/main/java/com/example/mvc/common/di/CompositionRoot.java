package com.example.mvc.common.di;

import com.example.mvc.common.Constants;
import com.example.mvc.networking.StackoverflowApi;
import com.example.mvc.screens.common.dialogs.DialogsEventBus;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CompositionRoot {
    private Retrofit mRetrofit;
    private DialogsEventBus mDialogsEventBus;

    public StackoverflowApi getStackOverflowApi() {
        return getRetrofit().create(StackoverflowApi.class);
    }

    private Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    public DialogsEventBus getDialogsEventBus() {
        if (mDialogsEventBus == null) mDialogsEventBus = new DialogsEventBus();
        return mDialogsEventBus;
    }
}
