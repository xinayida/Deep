package com.xinayida.lib.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * 图片加载工具类
 * Created by stephan on 16/8/25.
 */
public class ImageUtil {

//    public static void loadImage(String url, ImageView image) {
//        Glide.with(image.getContext()).load(url).asBitmap().centerCrop().into(image);
//    }

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
        if(imageView == null){
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
     * @param uri
     * @return
     */
    public static Bitmap loadBitmapFromUri(Uri uri) {
        Bitmap b = BitmapFactory.decodeFile(uri.getPath());
//        String scheme = uri.getScheme();
//        Bitmap b = null;
//        if (ContentResolver.SCHEME_CONTENT.equals(scheme)
//                || ContentResolver.SCHEME_FILE.equals(scheme)) {
//            InputStream stream = null;
//            try {
//                stream = context.getContentResolver().openInputStream(uri);
//                b = BitmapFactory.decodeStream(stream);
////                d = Drawable.createFromStream(stream, null);
//            } catch (Exception e) {
//                Log.w("ImageView", "Unable to open content: " + uri, e);
//            } finally {
//                if (stream != null) {
//                    try {
//                        stream.close();
//                    } catch (IOException e) {
//                        Log.w("ImageView", "Unable to close content: " + uri, e);
//                    }
//                }
//            }
//        } else {
//            b = BitmapFactory.decodeFile(uri.toString());
////            d = Drawable.createFromPath(uri.toString());
//        }
        return b;
    }

}
