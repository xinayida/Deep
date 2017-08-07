package com.xinayida.deep.store;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.xinayida.deep.pages.category.model.CategoryItem;
import com.xinayida.deep.pages.category.model.ContentItem;
import com.xinayida.deep.store.dao.CategoryDao;
import com.xinayida.deep.store.dao.CommonDao;
import com.xinayida.deep.store.entity.JobMenu;
import com.xinayida.deep.store.entity.Tag;

/**
 * Created by ww on 2017/7/14.
 */
@Database(entities = {JobMenu.class, CategoryItem.class, ContentItem.class, Tag.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CommonDao commonDao();

    public abstract CategoryDao categoryDao();
}
