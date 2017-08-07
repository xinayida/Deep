package com.xinayida.deep.pages.category;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinayida.deep.R;

/**
 * Created by ww on 2017/7/21.
 */

public class DrawerMenuContainer extends LinearLayout implements CompoundButton.OnCheckedChangeListener {

    private boolean modeSingle;//是否是单选，默认为false
    private Context context;

    private boolean[] selections;
    private int preSelection = -1;

    public DrawerMenuContainer(Context context) {
        this(context, null);
    }

    public DrawerMenuContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public DrawerMenuContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setOrientation(VERTICAL);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.DrawerMenuStyle);
        modeSingle = a.getBoolean(a.getIndex(0), false);
        a.recycle();
    }


    public void initSelections(String[] items) {
        if (items == null || items.length == 0) return;
        selections = new boolean[items.length];
        for (int i = 0; i < items.length; i++) {
            View v = LayoutInflater.from(context).inflate(R.layout.drawer_menu_item, null);
            ((TextView) v.findViewById(R.id.menu_item_text)).setText(items[i]);
            CheckBox cb = (CheckBox) v.findViewById(R.id.menu_item_checkbox);
            cb.setTag(i);
            cb.setOnCheckedChangeListener(this);
            addView(v);
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        int index = (int) compoundButton.getTag();
        selections[index] = b;
        if (modeSingle && b) {
            if (preSelection >= 0) {
                ((CheckBox) getChildAt(preSelection).findViewById(R.id.menu_item_checkbox)).setChecked(false);
            }
            preSelection = index;
        }
    }

    public boolean[] getSelections() {
        return selections;
    }
}
