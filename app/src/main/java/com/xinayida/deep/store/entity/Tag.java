package com.xinayida.deep.store.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ww on 2017/7/31.
 */

@Entity
public class Tag {

    @PrimaryKey
    public int id;
    public String name;
    public boolean select;

//    @Ignore
//    public int getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public boolean isSelect() {
//        return select;
//    }
}
