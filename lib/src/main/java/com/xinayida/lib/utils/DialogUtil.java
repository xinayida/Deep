package com.xinayida.lib.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.xinayida.lib.R;

/**
 * Dialog辅助类
 */
public class DialogUtil {

    public static AlertDialog.Builder dialogBuilder(Context context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (msg != null) {
            builder.setMessage(msg);
        }
        if (title != null) {
            builder.setTitle(title);
        }
        return builder;
    }

    public static AlertDialog.Builder dialogBuilder(Context context, String title, String msg, int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (msg != null) {
            builder.setMessage(Html.fromHtml(msg));
        }
        if (title != null) {
            builder.setTitle(title);
        }
        return builder;
    }


    public static AlertDialog.Builder dialogBuilder(Context context, int title, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (view != null) {
            builder.setView(view);
        }
        if (title > 0) {
            builder.setTitle(title);
        }
        return builder;
    }

    public static AlertDialog.Builder dialogBuilder(Context context, String title, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (view != null) {
            builder.setView(view);
        }
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        return builder;
    }

    public static AlertDialog.Builder listDialogBuilder(Context context, int title, String[] items, DialogInterface.OnClickListener listener) {
        CharSequence sTitle = null;
        if (title > 0) {
            sTitle = context.getResources().getString(title);
        }
        return listDialogBuilder(context, sTitle, items, listener);
    }

    public static AlertDialog.Builder listDialogBuilder(Context context, CharSequence title, String[] items, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (title != null) {
            builder.setTitle(title);
        }
        if (items != null) {
            builder.setItems(items, listener);
        }

        return builder;
    }

//    public static Dialog showTips(Context context, String title, String des) {
//        return showTips(context, title, des, null, null);
//    }


    public static Dialog showAlert(Context context, String des, String positiveText, DialogInterface.OnClickListener posiListener) {
        return showAlert(context, des, positiveText, "取消", posiListener, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }

    public interface OnEditDialogConfirm {
        void onConfirm(String str);
    }

    public static Dialog showEditDialog(Context context, String title, String positiveText, final OnEditDialogConfirm listener) {
        final EditText editText = new EditText(context);
        AlertDialog.Builder builder = dialogBuilder(context, title, editText);
        builder.setCancelable(true);
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onConfirm(editText.getText().toString());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        Dialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static Dialog showAlert(Context context, String des, String positiveText, String NegativeText, DialogInterface.OnClickListener posiListener, DialogInterface.OnClickListener negaListener) {
        AlertDialog.Builder builder = dialogBuilder(context, null, des);
        builder.setCancelable(true);
        builder.setPositiveButton(positiveText, posiListener);
        builder.setNegativeButton(NegativeText, negaListener);
        Dialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }


    public static Dialog showTips(Context context, String title, String des, String btn, DialogInterface.OnDismissListener dismissListener) {
        AlertDialog.Builder builder = dialogBuilder(context, title, des);
        builder.setCancelable(true);
        builder.setPositiveButton(btn, null);
        Dialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnDismissListener(dismissListener);
        return dialog;
    }

    public static Dialog showLoading(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.LoadingDialog);
//        View contentView = View.inflate(context, R.layout.loading_layout, null);
//        builder.setView(contentView);
        Dialog dialog = builder.show();
        dialog.setContentView(R.layout.loading_layout);
        return dialog;
    }


    public static PopupWindow showCenterPopView(View rootView, View popView) {
        if (rootView == null || popView == null) {
            return null;
        }

        PopupWindow popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(rootView, Gravity.NO_GRAVITY, 0, 0);
        return popupWindow;
    }

    public enum PopPosition {
        TOP, BOTTOM, LEFT, RIGHT, CENTER
    }

    public static PopupWindow showPopView(View rootView, View popView, PopPosition pps) {
        if (rootView == null || popView == null) {
            return null;
        }

        PopupWindow popupWindow = new PopupWindow(popView, DisplayUtils.dip2px(rootView.getContext(), 160), DisplayUtils.dip2px(rootView.getContext(), 85), true);
        int[] location = new int[2];
        rootView.getLocationOnScreen(location);

        switch (pps) {
            case TOP:
                popupWindow.showAtLocation(rootView, Gravity.NO_GRAVITY, location[0], location[1] - popupWindow.getHeight());
                break;
            case BOTTOM:
                popupWindow.showAsDropDown(rootView);
                break;
            case LEFT:
                popupWindow.showAtLocation(rootView, Gravity.NO_GRAVITY, location[0] - popupWindow.getWidth(), location[1]);
                break;
            case RIGHT:
                popupWindow.showAtLocation(rootView, Gravity.NO_GRAVITY, location[0] + rootView.getWidth(), location[1]);
                break;
        }
        return popupWindow;
    }

    public static PopupWindow showGuideView(View rootView, int popView) {
        LayoutInflater li = LayoutInflater.from(rootView.getContext());
        return showGuideView(rootView, li.inflate(popView, null));
    }

    public static PopupWindow showGuideView(final View rootView, View popView) {
        if (rootView == null || popView == null) {
            return null;
        }

        final PopupWindow popupWindow = new PopupWindow(popView, DisplayUtils.getScreenWidth(rootView.getContext()), DisplayUtils.getScreenHeight(rootView.getContext()), true);
        popView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        rootView.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(rootView, Gravity.NO_GRAVITY, 0, 0);
            }
        });

        return popupWindow;
    }
}
