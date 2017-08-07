package com.xinayida.deep.pages.category.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

/**
 * Created by ww on 2017/6/26.
 */
//@NotProguard
@Entity
public class ContentItem {

    public static String TYPE_FOLDER = "folder";
    public static String TYPE_PDF = "pdf";
    public static String TYPE_HTML = "html";

    @PrimaryKey(autoGenerate = true)
    public int id;

    /**
     * 分类id
     */
    @ColumnInfo(name = "category_id")
    public int categoryId;

    /**
     * 父结点id
     */
    public int parentId;

//    /**
//     * 对应
//     */
//    public String job;

    /**
     * 文件/文件夹名称
     */
    public String contentName;


    /**
     * 内容类型
     * folder、pdf、html
     */
    public String contentType;

    /**
     * 内容地址
     */
    public String contentPath = "";

    /**
     * 是否已读
     */
    public boolean readFlag;

    /**
     * 当前阅读页数
     */
    public int currentPage;

    /**
     * 资源总数（文件夹）
     */
    @Ignore
    public int contentTotalCount;

    /**
     * 已读资源数
     */
    @Ignore
    public int contentReadCount;

    @Ignore
    private List<ContentItem> subItems;


    public List<ContentItem> loadSubItems() {
//        if (subItems == null) {
//            subItems = DBUtils.instance.loadSubContentItems(contentPath);
//        }
        return subItems;
    }

    public List<ContentItem> forceReload() {
        subItems = null;
        return loadSubItems();
    }
}
