package com.xinayida.deep.pages.category.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ww on 2017/6/24.
 */
@Entity
public class CategoryItem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    /**
     * 标签标题
     */
    public String categoryName;

}
