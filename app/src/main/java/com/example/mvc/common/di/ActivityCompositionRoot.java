package com.example.mvc.common.di;

import android.support.v4.app.FragmentActivity;

import com.example.mvc.common.permissions.PermissionsHelper;
import com.example.mvc.networking.StackoverflowApi;
import com.example.mvc.screens.common.dialogs.DialogsEventBus;

public class ActivityCompositionRoot {

    private final CompositionRoot mCompositionRoot;
    private final FragmentActivity mActivity;

    private PermissionsHelper mPermissionsHelper;

    public ActivityCompositionRoot(CompositionRoot compositionRoot, FragmentActivity activity) {
        this.mCompositionRoot = compositionRoot;
        this.mActivity = activity;
    }

    public FragmentActivity getActivity(){
        return mActivity;
    }

    public StackoverflowApi getStackOverflowApi() {
        return mCompositionRoot.getStackOverflowApi();
    }

    public DialogsEventBus getDialogsEventBus() {
        return mCompositionRoot.getDialogsEventBus();
    }

    public PermissionsHelper getPermissionsHelper() {
        if (mPermissionsHelper == null){
            mPermissionsHelper = new PermissionsHelper(getActivity());
        }
        return mPermissionsHelper;
    }
}
