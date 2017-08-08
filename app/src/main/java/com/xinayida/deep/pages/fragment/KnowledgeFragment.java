package com.xinayida.deep.pages.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import com.xinayida.deep.R;
import com.xinayida.deep.annotation.PageConfig;
import com.xinayida.deep.base.BaseFragment;
import com.xinayida.deep.flux.action.ActionType;
import com.xinayida.deep.flux.store.AppStore;
import com.xinayida.deep.injection.ViewModelFactory;
import com.xinayida.deep.pages.category.CategoryLayoutProxy;
import com.xinayida.deep.pages.category.model.CategoryItem;
import com.xinayida.deep.store.entity.JobMenu;
import com.xinayida.deep.store.entity.Tag;
import com.xinayida.lib.rxflux.Action;
import com.xinayida.lib.rxflux.Dispatcher;
import com.xinayida.lib.utils.DialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ww on 2017/7/24.
 */

@PageConfig(contentViewId = R.layout.fragment_knowledge, title = "职业名称")
public class KnowledgeFragment extends BaseFragment implements View.OnClickListener {

    private PopupMenu popupMenu;
    private List<CategoryLayoutProxy> categoryLayoutProxies;
    private View addCategoryBtn;
    private LinearLayout contentView;

    private KnowledgeFragmentViewModel viewModel;

    @Override
    protected void setupViews(View rootView) {
//        inject();
        contentView = (LinearLayout) rootView.findViewById(R.id.tab_knowledge_content);
        View title = rootView.findViewById(R.id.toolbar_title_layout);
        addCategoryBtn = rootView.findViewById(R.id.knowledge_add_category);
        popupMenu = new PopupMenu(getContext(), title, Gravity.TOP | Gravity.CENTER_HORIZONTAL);

    }

    @Override
    protected void setupClickListeners() {
        addCategoryBtn.setOnClickListener(this);
        rootView.findViewById(R.id.toolbar_icon_menu).setOnClickListener(this);
        rootView.findViewById(R.id.toolbar_edit).setOnClickListener(this);
        rootView.findViewById(R.id.toolbar_title_layout).setOnClickListener(this);
        popupMenu.setOnMenuItemClickListener(item -> {
            int index = item.getItemId() - Menu.FIRST;
            viewModel.setJobIndex(index);
            setTitle(viewModel.getJobMenus().getValue().get(index).name);
            List<Tag> tagList = viewModel.getTags().getValue();
            if (tagList != null && tagList.size() > 0) {
//                List<String> tags = new ArrayList<String>();
//                for (Tag tag : tagList) {
//                    tags.add(tag.getName());
//                }
//                viewModel.loadCategory(tags, viewModel.getJobMenus().getValue().get(index).name);
            } else {

            }
            return false;
        });
    }

    @Override
    protected void setupViewModel() {
        viewModel = ViewModelProviders.of(this, new ViewModelFactory(getAppContext())).get(KnowledgeFragmentViewModel.class);
        viewModel.getTags().observe(this, tags -> {
            Menu menu = popupMenu.getMenu();
            menu.clear();
            List<JobMenu> subJobs = viewModel.getJobMenus().getValue();
            if (subJobs != null && subJobs.size() != 0) {
                int size = subJobs.size();
                for (int i = 0; i < size; i++) {
                    menu.add(Menu.NONE, Menu.FIRST + i, i, subJobs.get(i).name);
                }
            }
        });

        viewModel.loadCategory().observe(this, categoryItems -> {
            initCategories(categoryItems);
            if (categoryItems == null || categoryItems.size() == 0) {
                addCategoryBtn.setVisibility(View.VISIBLE);
            }
        });
//        if(viewModel.loadCategory().getValue().size() == 0){
//            addCategoryBtn.setVisibility(View.VISIBLE);
//        }
    }

    private void initCategories(List<CategoryItem> categoryItems) {
        if (categoryItems != null && categoryItems.size() > 0) {
            contentView.removeAllViews();
            if (categoryLayoutProxies == null) {
                categoryLayoutProxies = new ArrayList<>();
            } else {
                categoryLayoutProxies.clear();
            }
            for (CategoryItem item : categoryItems) {
                CategoryLayoutProxy proxy = new CategoryLayoutProxy(getContext(), item);
                proxy.inject(getAppContext().getAppComponent());
                categoryLayoutProxies.add(proxy);
                contentView.addView(proxy.getView());
            }
        }
    }

//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        creator.loadJobsMenu(null);
//        creator.loadTags();
//    }

//    private void inject() {
//        ((MainTabActivity) getActivity()).getComponent().knowledgeCOmponent().build().inject(this);
//        store.setObserver(this);
//        store.register(ActionType.LOAD_JOBS_MENU, ActionType.LOAD_JOB_CONTENTS, ActionType.LOAD_TAGS);
//    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.knowledge_add_category:
                DialogUtil.showEditDialog(getContext(), "添加分类", "添加", str -> viewModel.addCategory(str));
                break;
            case R.id.toolbar_icon_menu:
                Dispatcher.get().postAction(Action.type(ActionType.TOGGLE_DRAWER).build());
                break;
            case R.id.toolbar_edit:
                viewModel.setEditState(!viewModel.isEditState());
                swapEditState();
                break;
            case R.id.toolbar_title_layout:
                popupMenu.show();
                break;
        }
    }

    private void swapEditState() {
        if (viewModel.isEditState()) {
            addCategoryBtn.setVisibility(View.VISIBLE);
        } else {
            addCategoryBtn.setVisibility(View.GONE);
        }
        if (categoryLayoutProxies != null && categoryLayoutProxies.size() > 0) {
            for (CategoryLayoutProxy item : categoryLayoutProxies) {
                item.toggleEdit();
            }
        }
    }

//    @Override
//    public void onChange(Action action) {
//        String type = action.getType();
//        if (type.equals(ActionType.LOAD_JOBS_MENU)) {
//            Menu menu = popupMenu.getMenu();
//            menu.clear();
//            List<JobMenu> subJobs = viewModel.getJobMenus().getValue();
//            if (subJobs != null && subJobs.size() != 0) {
//                int size = subJobs.size();
//                for (int i = 0; i < size; i++) {
//                    menu.add(Menu.NONE, Menu.FIRST + i, i, subJobs.get(i).name);
//                }
//            }
//        } else if (type.equals(ActionType.LOAD_JOB_CONTENTS)) {
//
//        } else if (type.equals(ActionType.LOAD_TAGS)) {
//            creator.loadCategory(store.getTags(), store.getSubJobs().get(0).name);
//        }
//    }
}
