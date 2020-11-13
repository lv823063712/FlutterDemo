package com.example.fluttershuffling;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.Nullable;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * @ClassName: BaseActivity
 * @Description: java类作用描述
 * @CreateDate: 2020/11/13 14:50
 * @Creator: lf
 */
public abstract class BaseActivity extends SupportActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        init();

    }

    protected abstract int getLayout();

    protected abstract void initData();

    protected abstract void setClick();

    protected abstract void preLogic();

    void init() {
        if (getLayout() != 0) {
            setContentView(getLayout());
            initData();
            setClick();
            preLogic();
        }
    }
}
