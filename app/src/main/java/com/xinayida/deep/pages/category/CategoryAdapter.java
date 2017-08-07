package com.xinayida.deep.pages.category;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.xinayida.deep.R;
import com.xinayida.deep.pages.category.model.ContentItem;
import com.xinayida.lib.base.BaseRecyclerViewHolder;
import com.xinayida.lib.utils.DialogUtil;
import com.xinayida.lib.widget.SimpleItemTouchHelperCallback;

import java.util.List;

/**
 * Created by ww on 2017/6/24.
 */

class CategoryAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> implements SimpleItemTouchHelperCallback.OnMoveAndSwipeListener {
    private SimpleItemTouchHelperCallback itemTouchHelperCallback;
    private List<ContentItem> data;
    private LayoutInflater inflater;
    private Context context;

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ADD = 1;//添加按钮

    private boolean editMode;//编辑状态切换标志
    //    private OnItemMoveListener mItemMoveListener;

    CategoryAdapter(Context mContext) {
        context = mContext;
        inflater = LayoutInflater.from(context);
    }

    public void setItemTouchHelperCallback(SimpleItemTouchHelperCallback callback){
        itemTouchHelperCallback = callback;
    }

    @Override
    public int getItemViewType(int position) {
        if (data == null || data.size() == 0 || (position == data.size() && editMode)) {
            return TYPE_ADD;
        }
        return TYPE_ITEM;
    }

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final BaseRecyclerViewHolder holder = new BaseRecyclerViewHolder(context, inflater.inflate(getItemLayoutId(viewType), parent, false));
        if (viewType == TYPE_ADD) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final EditText editText = new EditText(context);
                    DialogUtil.dialogBuilder(context, null, editText)
                            .setPositiveButton("取消", null)
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
            });
        } else if (viewType == TYPE_ITEM) {
            holder.getView(R.id.category_delete_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(holder.getLayoutPosition());
                }
            });
            if (itemTouchHelperCallback != null) {
                holder.itemView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        itemTouchHelperCallback.setLongPressDragEnabled(true);
                        return false;
                    }
                });
            }
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        int itemType = getItemViewType(position);
        if (itemType == TYPE_ITEM) {
            ContentItem item = data.get(position);
            holder.getTextView(R.id.category_name).setText(item.contentName);
            if (editMode) {
                holder.getView(R.id.category_delete_btn).setVisibility(View.VISIBLE);
            } else {
                holder.getView(R.id.category_delete_btn).setVisibility(View.GONE);
            }
        }
    }

    public void setData(List<ContentItem> mData) {
        data = mData;
        notifyDataSetChanged();
    }

    /**
     * 开关edit
     */
    public void toggleEdit() {
        editMode = !editMode;
        notifyDataSetChanged();
    }

    private void removeItem(int pos) {
        if (data == null || pos >= data.size()) {
            return;
        }
        ContentItem item = data.remove(pos);
        notifyItemRemoved(pos);
//        DialogUtil.showAlert(AppImpl)
//        DBUtils.instance.deleteContent(item);
    }

    private int getItemLayoutId(int viewType) {
        int layoutId = 0;
        switch (viewType) {
            case TYPE_ADD:
                layoutId = R.layout.category_plus_item;
                break;
            default:
                layoutId = R.layout.category_item;
        }
        return layoutId;
    }

    @Override
    public int getItemCount() {
        int count = data == null ? 0 : data.size();
        if (editMode) {
            count++;
        }
        return count;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        data.add(toPosition, data.remove(fromPosition));
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }
}
