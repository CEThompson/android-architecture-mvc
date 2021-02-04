package com.example.mvc.screens.common.toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mvc.R;
import com.example.mvc.screens.common.views.BaseViewMvc;

public class ToolbarViewMvc extends BaseViewMvc {

    public interface NavigateUpClickListener {
        void onNavigateUpClicked();
    }

    public interface MenuClickListener {
        void onMenuClicked();
    }

    public interface LocationRequestListener {
        void onLocationRequestClicked();
    }

    private final ImageButton mBtnBack;
    private final TextView mTxtTitle;
    private final ImageButton mBtnMenu;
    private final ImageButton mBtnLocationRequest;

    private NavigateUpClickListener mNavigateUpClickListener;
    private MenuClickListener mMenuClickListener;
    private LocationRequestListener mLocationRequestListener;

    public ToolbarViewMvc(LayoutInflater inflater, ViewGroup parent){
        setRootView(inflater.inflate(R.layout.layout_toolbar, parent, false));
        mTxtTitle = findViewById(R.id.txt_toolbar_title);
        mBtnBack = findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigateUpClickListener.onNavigateUpClicked();
            }
        });

        mBtnMenu = findViewById(R.id.hamburger);
        mBtnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMenuClickListener.onMenuClicked();
            }
        });

        mBtnLocationRequest = findViewById(R.id.btn_location);
        mBtnLocationRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocationRequestListener.onLocationRequestClicked();
            }
        });
    }

    public void setTitle(String title) { mTxtTitle.setText(title); }

    public void enableUpButtonAndListen(NavigateUpClickListener navigateUpClickListener){
        if (mNavigateUpClickListener != null){
            throw new RuntimeException(("Hamburger and up should'nt be shown together"));
        }
        mNavigateUpClickListener = navigateUpClickListener;
        mBtnBack.setVisibility(View.VISIBLE);
    }

    public void enableMenuButtonAndListen(MenuClickListener listener){
        if (mNavigateUpClickListener != null){
            throw new RuntimeException(("Hamburger and up should'nt be shown together"));
        }
        mMenuClickListener = listener;
        mBtnMenu.setVisibility(View.VISIBLE);
    }

    public void enableLocationRequestButtonAndListen(LocationRequestListener locationRequestListener) {
        mLocationRequestListener = locationRequestListener;
        mBtnLocationRequest.setVisibility(View.VISIBLE);
    }
}
