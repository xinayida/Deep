package com.xinayida.lib.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ww on 2017/7/24.
 */

public class ChangeColorTab extends LinearLayout implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private List<ChangeColorIconView> tabIndicator = new ArrayList<ChangeColorIconView>();
    private ViewPager viewPager;

    public ChangeColorTab(Context context) {
        super(context);
    }

    public ChangeColorTab(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setUp(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initTabIndicator();
    }

    private void initTabIndicator() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View v = getChildAt(i);
            if (!(v instanceof ChangeColorIconView)) {
                throw new RuntimeException("Child view must instanceof ChangeColorIconView");
            } else {
                v.setTag(i);
                v.setOnClickListener(this);
                tabIndicator.add((ChangeColorIconView) v);
            }
        }
        tabIndicator.get(0).setIconAlpha(1.0f);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            ChangeColorIconView left = tabIndicator.get(position);
            ChangeColorIconView right = tabIndicator.get(position + 1);

            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        reset();
        int tag = (int) view.getTag();
        if (tag >= 0 && tag < tabIndicator.size()) {
            tabIndicator.get(tag).setIconAlpha(1.0f);
            viewPager.setCurrentItem(tag, true);
        }
    }

    private void reset() {
        int size = tabIndicator.size();
        for (int i = 0; i < size; i++) {
            tabIndicator.get(i).setIconAlpha(0);
        }
    }
}
