package com.xinayida.deep.store.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ww on 2017/7/25.
 */

@Entity
public class JobMenu {

    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "parent_id")
    public int parentId;

    public String name;

    public JobMenu(int id, int parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }
}
