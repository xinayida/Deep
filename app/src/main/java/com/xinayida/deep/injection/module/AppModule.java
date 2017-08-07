package com.xinayida.deep.injection.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xinayida.deep.AppContext;
import com.xinayida.deep.api.AppService;
import com.xinayida.deep.flux.CategoryRepository;
import com.xinayida.deep.flux.CategoryRepositoryImpl;
import com.xinayida.deep.flux.store.AppStore;
import com.xinayida.deep.store.AppDatabase;
import com.xinayida.lib.rxflux.Store;
import com.xinayida.lib.utils.AppUtils;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ww on 2017/7/18.
 */

@Module
public class AppModule {
    private AppContext appImpl;

    public AppModule(AppContext app) {
        appImpl = app;
    }

    @Provides
    public Context provideContext() {
        return appImpl;
    }

    @Singleton
    @Provides
    public Store provideStore() {
        return new AppStore();
    }

    @Singleton
    @Provides
    AppDatabase providerDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "mydb").build();
    }

    @Singleton
    @Provides
    CategoryRepository providerRepository(AppDatabase database){
        return new CategoryRepositoryImpl(database);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Context context) {
        return new OkHttpClient.Builder()
//                .addNetworkInterceptor(getNetworkInterceptor())
                .cache(new Cache(new File(AppUtils.getCacheDir(context), "http_reponse"), 10 * 1024 * 1024))
                .build();
    }

    @Provides
    @Singleton
    AppService provideApiService(OkHttpClient okHttpClient){
        Gson dateGson = new GsonBuilder()
//                .registerTypeAdapter(Date.class, new DateDeserializer(DATE_PATTERN1, DATE_PATTERN2))
                .serializeNulls()
                .create();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(AppService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(dateGson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(AppService.class);
    }


}
