package com.xinayida.deep.injection.component;

import android.content.Context;

import com.xinayida.deep.injection.module.AppModule;
import com.xinayida.deep.pages.category.CategoryLayoutProxy;
import com.xinayida.deep.pages.fragment.KnowledgeFragmentViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ww on 2017/7/19.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    Context getAppContext();

    void inject(KnowledgeFragmentViewModel viewModel);
    void inject(CategoryLayoutProxy layoutProxy);

//    MainTabComponent.Builder  mainTabComponent();

    interface Injectable {
        void inject(AppComponent component);
    }
}
