package com.docking.statusbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class StatusBarActivity extends Activity {

    private RelativeLayout mContainier;
    private Button mFullBtn;
    private Button mUnFullBtn;
    private Button mLowFullBtn;
    private Button mLowUnFullBtn;
    private Button mImmesiveFullBtn;
    private Button mImmesiveUnFullBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar);

        StatusBarUtils.initStatusBar(this);

        StatusBarUtils.initStatusBar(this, R.color.color3097fd);
        init();
        initListener();
    }

    private void init() {
        mContainier = (RelativeLayout) this.findViewById(R.id.container);
        mFullBtn = (Button) this.findViewById(R.id.show_fullscreen);
        mUnFullBtn = (Button) this.findViewById(R.id.hide_fullscreen);
        mLowFullBtn = (Button) this.findViewById(R.id.low_fullscreen);
        mLowUnFullBtn = (Button) this.findViewById(R.id.low_unfullscreen);
        mImmesiveFullBtn = (Button) this.findViewById(R.id.immersive_fullscreen);
        mImmesiveUnFullBtn = (Button) this.findViewById(R.id.immersive_unfullscreen);
    }

    private void initListener() {
        mFullBtn.setOnClickListener(mOnClickListener);
        mUnFullBtn.setOnClickListener(mOnClickListener);
        mLowFullBtn.setOnClickListener(mOnClickListener);
        mLowUnFullBtn.setOnClickListener(mOnClickListener);
        mImmesiveFullBtn.setOnClickListener(mOnClickListener);
        mImmesiveUnFullBtn.setOnClickListener(mOnClickListener);
    }

    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == mFullBtn.getId()) {
                StatusBarUtils.reset(StatusBarActivity.this);
                StatusBarUtils.fullScreen(StatusBarActivity.this, mContainier);
            } else if (id == mUnFullBtn.getId()) {
                StatusBarUtils.reset(StatusBarActivity.this);
                StatusBarUtils.reset(StatusBarActivity.this);
                StatusBarUtils.unFullScreen(StatusBarActivity.this, mContainier);
            } else if (id == mLowFullBtn.getId()) {
                StatusBarUtils.reset(StatusBarActivity.this);
                StatusBarUtils.lowFullScreen(StatusBarActivity.this);
            } else if (id == mLowUnFullBtn.getId()) {
                StatusBarUtils.reset(StatusBarActivity.this);
                StatusBarUtils.lowUnFullScreen(StatusBarActivity.this);
            } else if (id == mImmesiveFullBtn.getId()) {
                StatusBarUtils.immersiveFullScreen(StatusBarActivity.this);
            } else if (id == mImmesiveUnFullBtn.getId()) {
                StatusBarUtils.immersiveUnFullScreen(StatusBarActivity.this);
            }
        }
    };

}
