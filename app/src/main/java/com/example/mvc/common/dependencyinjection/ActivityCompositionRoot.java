package com.example.mvc.common.dependencyinjection;


import android.support.v4.app.FragmentActivity;

import com.example.mvc.common.permissions.PermissionsHelper;
import com.example.mvc.networking.StackoverflowApi;
import com.example.mvc.screens.common.dialogs.DialogsEventBus;

public class ActivityCompositionRoot {

    private final CompositionRoot mCompositionRoot;
    private final FragmentActivity mActivity;

    private PermissionsHelper mPermissionsHelper;

    public ActivityCompositionRoot(CompositionRoot compositionRoot, FragmentActivity activity) {
        mCompositionRoot = compositionRoot;
        mActivity = activity;
    }

    public FragmentActivity getActivity() {
        return mActivity;
    }

    public StackoverflowApi getStackoverflowApi() {
        return mCompositionRoot.getStackoverflowApi();
    }

    public DialogsEventBus getDialogsEventBus() {
        return mCompositionRoot.getDialogsEventBus();
    }

    public PermissionsHelper getPermissionsHelper() {
        if (mPermissionsHelper == null) {
            mPermissionsHelper = new PermissionsHelper(getActivity());
        }
        return mPermissionsHelper;
    }
}
