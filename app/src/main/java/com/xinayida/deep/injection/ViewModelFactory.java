package com.xinayida.deep.injection;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.xinayida.deep.AppContext;
import com.xinayida.deep.injection.component.AppComponent;

/**
 * Created by ww on 2017/7/31.
 */

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private AppContext appContext;

    public ViewModelFactory(AppContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        T t = super.create(modelClass);
        if (t instanceof AppComponent.Injectable) {
            ((AppComponent.Injectable) t).inject(appContext.getAppComponent());
        }
        return t;
    }
}
