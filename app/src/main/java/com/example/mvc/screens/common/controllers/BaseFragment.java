package com.example.mvc.screens.common.controllers;


import android.support.v4.app.Fragment;

import com.example.mvc.common.CustomApplication;
import com.example.mvc.common.di.ControllerCompositionRoot;
import com.example.mvc.screens.common.main.MainActivity;

public class BaseFragment extends Fragment {

    private ControllerCompositionRoot mControllerCompositionRoot;

    protected ControllerCompositionRoot getCompositionRoot() {
        if (mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((MainActivity) requireActivity()).getActivityCompositionRoot() // note assumes only 1 activity
            );
        }
        return mControllerCompositionRoot;
    }

}
