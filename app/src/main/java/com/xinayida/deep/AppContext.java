package com.xinayida.deep;

import android.app.Application;

import com.chenenyu.router.Router;
import com.xinayida.deep.injection.component.AppComponent;
import com.xinayida.deep.injection.component.DaggerAppComponent;
import com.xinayida.deep.injection.module.AppModule;

/**
 * Created by ww on 2017/7/18.
 */

public class AppContext extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initInjector();
        initRouter();
    }

    private void initInjector() {
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    /**
     * 初始化路由
     */
    private void initRouter() {
        Router.initialize(this, BuildConfig.DEBUG);
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }

}
