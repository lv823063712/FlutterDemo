package com.example.fluttershuffling;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @ClassName: BaseFragment
 * @Description: 基类
 * @CreateDate: 2020/11/13 14:32
 * @Creator: lf
 */
public abstract class BaseFragment extends SupportFragment {
    protected Context mContext;
    protected Activity mActivity;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getActivity().getWindow().getAttributes();
            localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        }
        return view;

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initView();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    /**
     * 初始化子类布局
     */
    protected abstract int getLayout();

    /**
     * 初始化子类View
     */
    protected abstract void initView();

    /**
     * 初始化子类一些数据
     */
    protected abstract void initData();

    protected abstract void loadData();
}
