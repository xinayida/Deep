package com.xinayida.deep.store.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.xinayida.deep.pages.category.model.CategoryItem;
import com.xinayida.deep.pages.category.model.ContentItem;
import com.xinayida.deep.store.entity.JobMenu;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by ww on 2017/7/18.
 */

@Dao
public interface CategoryDao {

    @Query("Select * from ContentItem where category_id = :categoryId")
    Flowable<List<ContentItem>> loadContentItems(int categoryId);

//    //TODO 修改ContentItem -> tag为外链
//    @Query("Select * from ContentItem where tag IN (:tag) AND job eq :job")
//    LiveData<List<ContentItem>> getBaseContent(List<String> tags, String job);

    @Query("Select * from CategoryItem")
    LiveData<List<CategoryItem>> loadCategoryItems();

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void addCategoryItem(CategoryItem... items);

    @Delete
    void delCategoryItem(CategoryItem... items);

    @Update//(onConflict = OnConflictStrategy.REPLACE)
    void updateCategoryItem(CategoryItem items);


//    @Query("Select * from ContentItem where job LIKE :job")
//    LiveData<List<ContentItem>> getBaseContent(String job);

    @Query("Select * from JobMenu")
    LiveData<List<JobMenu>> getJobMenu();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void saveContentItem(ContentItem... items);

    @Delete
    void delContentItem(ContentItem... item);
}
