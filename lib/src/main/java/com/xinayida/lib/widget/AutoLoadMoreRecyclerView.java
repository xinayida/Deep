package com.xinayida.lib.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

/**
 * ClassName: AutoLoadMoreRecyclerView<p>
 * Author: oubowu<p>
 * Fuction: 添加了滑动到底部自动加载的RecyclerView<p>
 * CreateDate: 2016/2/21 16:30<p>
 * UpdateUser: <p>
 * UpdateDate: <p>
 */
public class AutoLoadMoreRecyclerView extends RecyclerView {

    // 所处的状态
    public static final int STATE_MORE_LOADING = 1;
    public static final int STATE_IDLE = 2;
    public static final int STATE_ALL_LOADED = 3;
    public static final int STATE_LOAD_FAILED = 4;

    private int[] mVisiblePositions;

    private int mCurrentState = STATE_IDLE;

//    private ItemTouchHelper mItemTouchHelper;

    public AutoLoadMoreRecyclerView(Context context) {
        super(context);
        init();
    }

    public AutoLoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutoLoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

//    /**
//     * 初始化拖拽删除功能
//     */
//    public void initDragDel(){
//        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
//            @Override
//            public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
//                final int dragFlags;
//                final int swipeFlags;
//                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
//                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
//                    swipeFlags = 0;
//                } else {
//                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//                    swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
//                }
//                return makeMovementFlags(dragFlags, swipeFlags);
//            }
////            在设置了dragFlags的时候，就会在长按item的时候进入拖动模式，然后就会一直回调onMove函数。我们可以在onMove中进行数据集的更新：
//            @Override
//            public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
////                int fromPosition = viewHolder.getAdapterPosition();
////                int toPosition = target.getAdapterPosition();
////                data.add(toPosition, data.remove(fromPosition));
////                adapter.notifyItemMoved(fromPosition, toPosition);
//                return false;
//            }
//
////           如果需要在拖拽过程中进行高亮背景切换，可以重写onSelectedChanged和clearView方法。
////           当长按的时候调用
////            @Override
////            public void onSelectedChanged(ViewHolder viewHolder, int actionState) {
////                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
////                    viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
////                }
////                super.onSelectedChanged(viewHolder, actionState);
////            }
////            当手指松开的时候调用
////            @Override
////            public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
////                super.clearView(recyclerView, viewHolder);
////                viewHolder.itemView.setBackgroundResource(0);
////            }
//
//            @Override
//            public void onSwiped(ViewHolder viewHolder, int direction) {
////                int position = viewHolder.getAdapterPosition();
////                adapter.notifyItemRemoved(position);
////                data.remove(position);
//            }
//
//
//        });
//        mItemTouchHelper.attachToRecyclerView(this);
//    }

    private void init() {
        super.addOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastCaculatePos = calculateRecyclerViewLastPosition();
                int adapterCount = getAdapter().getItemCount() - 1;
//                L.d("mCurrentState: " + mCurrentState + " lastCaculatePos: " + lastCaculatePos + " adapterCount: " + adapterCount);
                if (mCurrentState == STATE_LOAD_FAILED) {
                    if (lastCaculatePos != adapterCount) {
                        mCurrentState = STATE_IDLE;
                    }
                    return;
                }
                if (mCurrentState == STATE_IDLE && lastCaculatePos == adapterCount && mLoadMoreListener != null) {
                    // 之前的状态为非正在加载状态
                    mCurrentState = STATE_MORE_LOADING;
                    mLoadMoreListener.loadMore();
                }
            }

        });
    }

    /**
     * 是否正在加载底部
     *
     * @return true为正在加载
     */
    public boolean isMoreLoading() {
        return mCurrentState == STATE_MORE_LOADING;
    }

    /**
     * 是否全部加载完毕
     *
     * @return true为全部数据加载完毕
     */
    public boolean isAllLoaded() {
        return mCurrentState == STATE_ALL_LOADED;
    }

    /**
     * 计算RecyclerView当前最后一个完全可视位置
     */
    private int calculateRecyclerViewLastPosition() {
        // 判断LayoutManager类型获取第一个完全可视位置
        if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
            if (mVisiblePositions == null) {
                mVisiblePositions = new int[((StaggeredGridLayoutManager) getLayoutManager())
                        .getSpanCount()];
            }
            ((StaggeredGridLayoutManager) getLayoutManager())
                    .findLastCompletelyVisibleItemPositions(mVisiblePositions);
            int max = -1;
            for (int pos : mVisiblePositions) {
                max = Math.max(max, pos);
            }
            return max;
            // return mVisiblePositions[0];
        } else if (getLayoutManager() instanceof GridLayoutManager) {
            return ((GridLayoutManager) getLayoutManager()).findLastCompletelyVisibleItemPosition();
        } else {
            return ((LinearLayoutManager) getLayoutManager())
                    .findLastCompletelyVisibleItemPosition();
        }
    }

    /**
     * 通知底部加载完成了
     */
    public void notifyIdle() {
        mCurrentState = STATE_IDLE;
    }

    /**
     * 加载失败
     */
    public void notifyFailed() {
        mCurrentState = STATE_LOAD_FAILED;
    }

    /**
     * 通知全部数据加载完了
     */
    public void notifyAllLoaded() {
        mCurrentState = STATE_ALL_LOADED;
    }

    public void notifyLoading() {
        mCurrentState = STATE_MORE_LOADING;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        mLoadMoreListener = loadMoreListener;
    }

    private OnLoadMoreListener mLoadMoreListener;

    public interface OnLoadMoreListener {
        void loadMore();
    }

    public AutoLoadMoreRecyclerView setAutoLayoutManager(LayoutManager layoutManager) {
        super.setLayoutManager(layoutManager);
        return this;
    }

    public AutoLoadMoreRecyclerView addAutoItemDecoration(ItemDecoration decor) {
        super.addItemDecoration(decor);
        return this;
    }

    public AutoLoadMoreRecyclerView setAutoItemAnimator(ItemAnimator anim) {
        super.setItemAnimator(anim);
        return this;
    }

    public AutoLoadMoreRecyclerView setAutoAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        return this;
    }

    public AutoLoadMoreRecyclerView setAutoHasFixedSize(boolean fixed) {
        super.setHasFixedSize(fixed);
        return this;
    }

    public AutoLoadMoreRecyclerView setAutoItemAnimatorDuration(int duration) {
        super.getItemAnimator().setAddDuration(duration);
        super.getItemAnimator().setMoveDuration(duration);
        super.getItemAnimator().setChangeDuration(duration);
        super.getItemAnimator().setRemoveDuration(duration);
        return this;
    }


}
