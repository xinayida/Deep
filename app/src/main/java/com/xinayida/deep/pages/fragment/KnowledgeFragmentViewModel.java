package com.xinayida.deep.pages.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.widget.Toast;

import com.xinayida.deep.AppConfig;
import com.xinayida.deep.flux.CategoryRepository;
import com.xinayida.deep.injection.component.AppComponent;
import com.xinayida.deep.pages.category.model.CategoryItem;
import com.xinayida.deep.pages.category.model.ContentItem;
import com.xinayida.deep.store.entity.JobMenu;
import com.xinayida.deep.store.entity.Tag;
import com.xinayida.lib.utils.FileUtils;
import com.xinayida.lib.utils.StringUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by ww on 2017/7/31.
 */

public class KnowledgeFragmentViewModel extends ViewModel implements AppComponent.Injectable {

    @Inject
    CategoryRepository repository;

    private LiveData<List<JobMenu>> jobMenus;
    private LiveData<List<Tag>> tags;
    private LiveData<List<CategoryItem>> categories;
    private int jobIndex;

    private AppComponent appComponent;

    public boolean isEditState() {
        return editState;
    }

    public void setEditState(boolean editState) {
        this.editState = editState;
    }

    private boolean editState;

    @Override
    public void inject(AppComponent component) {
        appComponent = component;
        component.inject(this);
        tags = repository.loadTags();
        categories = repository.loadCategory();
        jobMenus = repository.loadJobMenus();
    }

    public int getJobIndex() {
        return jobIndex;
    }

    public void setJobIndex(int jobIndex) {
        this.jobIndex = jobIndex;
    }


    public LiveData<List<JobMenu>> getJobMenus() {
        return jobMenus;
    }

    public LiveData<List<Tag>> getTags() {
        return tags;
    }

    public void addContentItem(String path, int parentId, int categoryId) {

        ContentItem item = new ContentItem();
        item.contentPath = path;
        item.categoryId = categoryId;
        item.parentId = parentId;

        repository.addContent(item).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }

    /**
     * 添加分类
     *
     * @param name
     */
    public void addCategory(String name) {
        if (StringUtils.isEmpty(name)) {
            Toast.makeText(appComponent.getAppContext(), "分类名称不能为空", Toast.LENGTH_SHORT).show();
        }
//        String dir = FileUtils.concatPath(appComponent.getAppContext().getExternalFilesDir(AppConfig.BASE_FOLDER).getAbsolutePath(), name);
        String dir = FileUtils.concatPath(FileUtils.getExternalStorageDirectory(), AppConfig.BASE_FOLDER, name);
        FileUtils.makeDirIfNotExist(dir);
        CategoryItem item = new CategoryItem();
        item.categoryName = name;
        repository.addCategory(item)
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d("Stefan", "onSubscribe " + d.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Stefan", "onComplete");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("Stefan", "onError " + e.toString());
                    }
                });
    }

//    public void loadCategory(List<String> tags, String job) {
//        categories = repository.loadCategory();
//    }

    public LiveData<List<CategoryItem>> loadCategory() {
        return categories;
    }
}
