package com.xinayida.deep.pages.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;

import com.xinayida.deep.R;
import com.xinayida.deep.base.BaseActivity;
import com.xinayida.deep.flux.action.ActionType;
import com.xinayida.deep.flux.store.AppStore;
import com.xinayida.deep.pages.category.DrawerMenuContainer;
import com.xinayida.deep.pages.fragment.KnowledgeFragment;
import com.xinayida.deep.pages.fragment.MeFragment;
import com.xinayida.deep.pages.fragment.NoteFragment;
import com.xinayida.lib.rxflux.Action;
import com.xinayida.lib.rxflux.StoreObserver;
import com.xinayida.lib.utils.L;
import com.xinayida.lib.widget.ChangeColorTab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ww on 2017/7/21.
 */

public class MainTabActivity extends BaseActivity implements StoreObserver {
    private ViewPager viewPager;
    private FragmentPagerAdapter adapter;
    private List<Fragment> tabs = new ArrayList<Fragment>();
    protected DrawerLayout drawerLayout;

    private DrawerMenuContainer containerCategory;
    private DrawerMenuContainer containerTag;
    private ChangeColorTab changeColorTab;

//    private MainTabComponent component;

    //    @Inject
    AppStore store;

    @Override
    protected void setup() {
        setContentView(R.layout.activity_main_tab);
//        initInjector();
        store = new AppStore();
        store.setObserver(this);
        store.register(ActionType.TOGGLE_DRAWER);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        initPager();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        containerCategory = (DrawerMenuContainer) findViewById(R.id.drawer_menu_container_category);
        containerTag = (DrawerMenuContainer) findViewById(R.id.drawer_menu_container_tag);
        containerCategory.initSelections(new String[]{"默认", "分享", "我的"});
        containerTag.initSelections(new String[]{"产品经理市场", "商业", "市场运营", "其他"});
        changeColorTab = (ChangeColorTab) findViewById(R.id.tab_layout);
        changeColorTab.setUp(viewPager);
    }

//    private void initInjector() {
//        component = getAppComponent().mainTabComponent().activity(this).build();

//        store.register(ActionType.CHANGE_MAJOR);
//    }

//    public MainTabComponent getComponent(){
//        return component;
//    }

    @Override
    protected void onDestroy() {
        store.unRegister();
        super.onDestroy();
    }

    private long mExitTime;

    private void initPager() {
        tabs.add(new KnowledgeFragment());
        tabs.add(new NoteFragment());
        tabs.add(new MeFragment());
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return tabs.get(position);
            }

            @Override
            public int getCount() {
                return tabs.size();
            }
        };
        viewPager.setAdapter(adapter);
    }

    public void openDrawer() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onBack() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            toastOri("再按一次退出Deep");
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBack();
        }
    }

    @Override
    public void onChange(Action action) {
        if (action.getType().equals(ActionType.TOGGLE_DRAWER)) {
            if (drawerLayout != null) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        }
        L.d(true, action.getType());
    }

    @Override
    public void onError(Action action) {

    }
}
