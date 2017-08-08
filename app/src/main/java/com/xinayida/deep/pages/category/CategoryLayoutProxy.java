package com.xinayida.deep.pages.category;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinayida.deep.R;
import com.xinayida.deep.flux.CategoryRepository;
import com.xinayida.deep.injection.component.AppComponent;
import com.xinayida.deep.pages.category.model.CategoryItem;
import com.xinayida.deep.pages.category.model.ContentItem;
import com.xinayida.lib.utils.DialogUtil;
import com.xinayida.lib.utils.L;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by ww on 2017/7/31.
 */

public class CategoryLayoutProxy implements AppComponent.Injectable, View.OnClickListener {
    private Context context;
    private View view;
    private CategoryItem item;
    private List<ContentItem> contentItems;

    private TextView title;
    private View rename;
    private View delete;
    private View share;
    private CategoryView categoryView;

    @Inject
    CategoryRepository repository;

    public CategoryLayoutProxy(Context context, CategoryItem item) {
        this.context = context;
        view = LinearLayout.inflate(context, R.layout.category_layout, null);
        this.item = item;
        title = (TextView) view.findViewById(R.id.category_name);
        title.setText(item.categoryName);
        rename = view.findViewById(R.id.category_bar_rename);
        rename.setOnClickListener(this);
        delete = view.findViewById(R.id.category_bar_delete);
        delete.setOnClickListener(this);
        share = view.findViewById(R.id.category_bar_share);
        share.setOnClickListener(this);
        categoryView = (CategoryView) view.findViewById(R.id.categoryView);
    }

    public View getView() {
        return view;
    }

    @Override
    public void inject(AppComponent component) {
        component.inject(this);
        repository.loadContent(item.id).subscribe(contentItems -> {
            this.contentItems = contentItems;
//            categoryView.setData(contentItems);
        });
    }

    public List<ContentItem> getContentItems() {
        return contentItems;
    }

    public void toggleEdit() {
        if (categoryView != null) {
            categoryView.toggleEdit();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.category_bar_delete:
                repository.delCategory(item).subscribe(() -> {
                    L.d("delete category success");
                });
                break;
            case R.id.category_bar_rename:
                DialogUtil.showEditDialog(context, "重命名", "确认", str -> {
                    item.categoryName = str;
                    repository.updateCategory(item).subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            L.d("update category success");
                            title.setText(str);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            e.printStackTrace();
                        }
                    });
                });
                break;
            case R.id.category_bar_share:
                break;
        }
    }
}
