package com.xinayida.deep.base;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xinayida.deep.AppContext;
import com.xinayida.deep.R;
import com.xinayida.deep.annotation.PageConfig;

/**
 * Created by ww on 2017/7/24.
 */

public abstract class BaseFragment extends LifecycleFragment {
    // layout id
    private int contentViewId;
    // 根布局
    protected View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            contentViewId = savedInstanceState.getInt("viewid");
        }
        String toobarTitle = null;
        if (rootView == null) {
            if (getClass().isAnnotationPresent(PageConfig.class)) {
                PageConfig annotation = getClass()
                        .getAnnotation(PageConfig.class);
                contentViewId = annotation.contentViewId();
                toobarTitle = annotation.title();
            } else {
                throw new RuntimeException("Class must add annotations of ActivityFragmentInitParams.class");
            }
            if (contentViewId == -1) {
                throw new RuntimeException("ContentViewId must be set a valid layout");
            }
            rootView = inflater.inflate(contentViewId, container, false);
            if (toobarTitle != null) {
                setTitle(toobarTitle);
            }
        }
        setupViews(rootView);
        setupClickListeners();
        setupViewModel();
        return rootView;
    }

    protected void setTitle(String str) {
        TextView tv = (TextView) rootView.findViewById(R.id.toolbar_title);
        if (tv != null) {
            tv.setText(str);
        }
    }

    /**
     * 页面初始化
     *
     * @param rootView
     */
    protected abstract void setupViews(View rootView);

    protected abstract void setupClickListeners();

    protected abstract void setupViewModel();

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("viewid", contentViewId);
        super.onSaveInstanceState(outState);
    }

    protected AppContext getAppContext() {
        return (AppContext) getActivity().getApplication();
    }

}
