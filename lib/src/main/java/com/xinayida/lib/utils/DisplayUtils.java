package com.xinayida.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.xinayida.lib.R;


/**
 * 显示相关工具类
 * Created by ww on 2016/12/28.
 */

public class DisplayUtils {

    private static final String TAG = DisplayUtils.class.getSimpleName();
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @param pxValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取ToolBar的高度
     *
     * @param context
     * @return
     */
    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme()
                .obtainStyledAttributes(new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    /**
     * 获取NavigationBar的高度
     * @param activity
     * @return
     */
    public static int getNavigationBarHeight(Activity activity) {

        Resources resources = activity.getResources();

        int rid = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid > 0) {
            Log.e(TAG, "获取导航栏是否显示true or false" + resources
                    .getBoolean(rid) + ""); //获取导航栏是否显示true or false
        }

        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            Log.e(TAG, "获取导航栏高度 " + resources.getDimensionPixelSize(resourceId) + ""); //获取高度
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    public static void loadImage(String url, ImageView imageView) {
        if (imageView == null) {
            return;
        }
        Glide.with(imageView.getContext()).load(url).asBitmap().centerCrop().into(imageView);
    }

    public static void loadImage(String url, ImageView imageView, int defaultImg) {
        if (imageView == null) {
            return;
        }
        if (TextUtils.isEmpty(url)) {
            imageView.setImageResource(defaultImg);
            return;
        }
        Glide.with(imageView.getContext()).load(url).asBitmap().centerCrop().placeholder(defaultImg).into(imageView);
    }

    public static void loadCircleImage(String url, final ImageView imageView) {
        if (imageView == null) {
            return;
        }
        Glide.with(imageView.getContext()).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                if (imageView == null) {
                    return;
                }
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(imageView.getContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void loadCircleImage(String url, final ImageView imageView, int defaultImg) {
        if (imageView == null) {
            return;
        }
        Bitmap src = BitmapFactory.decodeResource(imageView.getContext().getResources(), defaultImg);
        RoundedBitmapDrawable circularBitmapDrawable =
                RoundedBitmapDrawableFactory.create(imageView.getContext().getResources(), src);
        circularBitmapDrawable.setCircular(true);
        if (TextUtils.isEmpty(url)) {
            imageView.setImageDrawable(circularBitmapDrawable);
            return;
        }
        Glide.with(imageView.getContext()).load(url).asBitmap().centerCrop().placeholder(circularBitmapDrawable).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(imageView.getContext().getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    /**
     * 将Bitmap转为圆角图
     *
     * @param imageView
     * @param bitmap
     */
    public static void setCircleBitmap(ImageView imageView, Bitmap bitmap) {
        RoundedBitmapDrawable circularBitmapDrawable =
                RoundedBitmapDrawableFactory.create(imageView.getContext().getResources(), bitmap);
        circularBitmapDrawable.setCircular(true);
        imageView.setImageDrawable(circularBitmapDrawable);
    }

    /**
     * 根据Uri解析Bitmap
     *
     * @param uri
     * @return
     */
    public static Bitmap loadBitmapFromUri(Uri uri) {
        Bitmap b = BitmapFactory.decodeFile(uri.getPath());
        return b;
    }

    /**
     * 获取屏幕尺寸
     *
     * @param context 上下文
     * @return 屏幕尺寸像素值，下标为0的值为宽，下标为1的值为高
     */
    public static Point getScreenSize(Context context) {

        // 获取屏幕宽高
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point screenSize = new Point();
        wm.getDefaultDisplay().getSize(screenSize);
        return screenSize;
    }
}
