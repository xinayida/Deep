package com.xinayida.deep.flux;

import android.arch.lifecycle.LiveData;

import com.xinayida.deep.pages.category.model.CategoryItem;
import com.xinayida.deep.pages.category.model.ContentItem;
import com.xinayida.deep.store.AppDatabase;
import com.xinayida.deep.store.entity.JobMenu;
import com.xinayida.deep.store.entity.Tag;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ww on 2017/7/31.
 */

public class CategoryRepositoryImpl implements CategoryRepository {

    AppDatabase appDatabase;

    public CategoryRepositoryImpl(AppDatabase database) {
        appDatabase = database;
    }

    @Override
    public LiveData<List<Tag>> loadTags() {
        return appDatabase.commonDao().getTags();
    }

    @Override
    public LiveData<List<JobMenu>> loadJobMenus() {
        return appDatabase.categoryDao().getJobMenu();
    }

    @Override
    public Flowable<List<ContentItem>> loadContent(int categoryId) {
        return appDatabase.categoryDao().loadContentItems(categoryId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

//    @Override
//    public LiveData<List<ContentItem>> loadCategory(List<String> tags, String job) {
//        return appDatabase.categoryDao().getBaseContent(tags, job);
//    }

    @Override
    public Completable addContent(ContentItem... item) {
        return Completable.fromAction(() -> {
            appDatabase.categoryDao().saveContentItem(item);
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable delContent(ContentItem... item) {
        return Completable.fromAction(() -> {
            appDatabase.categoryDao().delContentItem(item);
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public LiveData<List<CategoryItem>> loadCategory() {
        return appDatabase.categoryDao().loadCategoryItems();
    }

    @Override
    public Completable addCategory(CategoryItem... items) {
        return Completable.fromAction(() -> {
            appDatabase.categoryDao().addCategoryItem(items);
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable delCategory(CategoryItem... items) {
        return Completable.fromAction(() -> {
            appDatabase.categoryDao().delCategoryItem(items);
        }).subscribeOn(Schedulers.io());
    }
}
