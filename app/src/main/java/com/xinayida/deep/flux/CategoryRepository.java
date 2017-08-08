package com.xinayida.deep.flux;

import android.arch.lifecycle.LiveData;

import com.xinayida.deep.pages.category.model.CategoryItem;
import com.xinayida.deep.pages.category.model.ContentItem;
import com.xinayida.deep.store.entity.JobMenu;
import com.xinayida.deep.store.entity.Tag;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by ww on 2017/7/31.
 */

public interface CategoryRepository {

    LiveData<List<Tag>> loadTags();
    LiveData<List<JobMenu>> loadJobMenus();
    Flowable<List<ContentItem>> loadContent(int categoryId);
    Completable addContent(ContentItem... items);
    Completable delContent(ContentItem... items);

    LiveData<List<CategoryItem>> loadCategory();
    Completable addCategory(CategoryItem... items);
    Completable delCategory(CategoryItem... items);
    Completable updateCategory(CategoryItem items);

}
