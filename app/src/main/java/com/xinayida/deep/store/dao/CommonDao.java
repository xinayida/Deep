package com.xinayida.deep.store.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.xinayida.deep.store.entity.Tag;

import java.util.List;

/**
 * Created by ww on 2017/7/28.
 */

@Dao
public interface CommonDao {
//    @Query()
//    LiveData<List<String>> getSubJobs(String id);

    @Query("select * from Tag")
    LiveData<List<Tag>> getTags();

    @Insert
    void saveTags(Tag... tags);

}
