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

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ww on 2017/7/31.
 */

public class CategoryLayoutProxy implements AppComponent.Injectable {
    private Context context;
    private View view;
    private CategoryItem item;
    private List<ContentItem> contentItems;

    private TextView title;
    private CategoryView categoryView;

    @Inject
    CategoryRepository repository;

    public CategoryLayoutProxy(Context context, CategoryItem item) {
        view = LinearLayout.inflate(context, R.layout.category_layout, null);
        this.item = item;
        title = (TextView) view.findViewById(R.id.category_name);
        title.setText(item.categoryName);
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

    public void toggleEdit(){
        if(categoryView!=null){
            categoryView.toggleEdit();
        }
    }
}
