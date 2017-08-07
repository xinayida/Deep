package com.xinayida.deep.pages.category;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.util.Log;

import com.xinayida.deep.pages.category.model.ContentItem;
import com.xinayida.lib.widget.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ww on 2017/6/26.
 */

public class CategoryView extends RecyclerView {

    private CategoryAdapter adapter;
    private static final int ANIMATION_DURATION = 150;
    private String categoryId;

    public CategoryView(Context context) {
        super(context);
        Log.d("Stefan", "11111");
        init();
    }

    public CategoryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d("Stefan", "222222");
        init();
    }

    public CategoryView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Log.d("Stefan", "333333");
        init();
    }

    public void setCategoryId(String id) {
        categoryId = id;
        Log.d("Stefan", "4444444");
//        DBUtils.instance.loadCategoryItems(categoryId);
    }

    public void toggleEdit() {
        if (adapter != null)
            adapter.toggleEdit();
    }

    private void init() {
        adapter = new CategoryAdapter(getContext());
        setLayoutManager(new GridLayoutManager(getContext(), 3, VERTICAL, false));
        ItemAnimator animator = getItemAnimator();
        setItemAnimator(new DefaultItemAnimator());
        animator.setAddDuration(ANIMATION_DURATION);
        animator.setChangeDuration(ANIMATION_DURATION);
        animator.setMoveDuration(ANIMATION_DURATION);
        animator.setRemoveDuration(ANIMATION_DURATION);
        SimpleItemTouchHelperCallback callback = new SimpleItemTouchHelperCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(this);
        adapter.setItemTouchHelperCallback(callback);
        setAdapter(adapter);

        loadFakeData();
    }

    private void loadFakeData() {
        List<ContentItem> items = new ArrayList<>();
        ContentItem cItem = new ContentItem();
        cItem.contentName = "123";
        items.add(cItem);
        cItem = new ContentItem();
        cItem.contentName = "456";
        items.add(cItem);
        cItem = new ContentItem();
        cItem.contentName = "789";
        items.add(cItem);
        cItem = new ContentItem();
        cItem.contentName = "111";
        items.add(cItem);
        cItem = new ContentItem();
        cItem.contentName = "222";
        items.add(cItem);
        cItem = new ContentItem();
        cItem.contentName = "333";
        items.add(cItem);
        adapter.setData(items);
    }

    public void setData(List<ContentItem> items) {
        if (adapter != null) {
            adapter.setData(items);
        }
    }
}
