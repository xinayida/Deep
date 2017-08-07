package com.xinayida.deep.base;

import android.arch.lifecycle.LifecycleActivity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.widget.Toast;

import com.xinayida.deep.AppContext;
import com.xinayida.deep.injection.component.AppComponent;
import com.xinayida.lib.utils.SlidrUtil;

/**
 * Created by ww on 2017/7/20.
 */

public abstract class BaseActivity extends LifecycleActivity {

    /**
     * 是否开启侧滑关闭
     */
    protected boolean enableSlider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup();
        if (enableSlider) {
            SlidrUtil.initSlidrDefaultConfig(this, true);
        }
    }

    protected abstract void setup();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {//屏蔽菜单按钮
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void onBack() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    public void toastOri(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public AppComponent getAppComponent() {
        return ((AppContext) getApplication()).getAppComponent();
    }

    public void replaceFragment(int containerViewId, Fragment fragment, String tag) {
        if (null == getSupportFragmentManager().findFragmentByTag(tag)) {
            getSupportFragmentManager().beginTransaction()
                    .replace(containerViewId, fragment, tag)
                    .commit();
        }
    }
}