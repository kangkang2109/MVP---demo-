package com.example.kk.gankio.view.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.kk.gankio.R;
import com.example.kk.gankio.bean.GankBean;

import java.util.List;

/**
 * Created by kk on 2017/7/22.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<StrongViewHolder> {

    private final String TAG = "adapter";

    private final int TYPE_FOOT_VIEW = 1;

    private final int TYPE_COMMON_VIEW = 2;

    private boolean isAutoLoadMore = true;

    private List<T> data;

    private Context context;

    private boolean loadMore = true;

    private View footView;

    private View failedView;

    private RelativeLayout footLayout;

    private LoadMoreLitener lisener;

    private OnItemClickLitener itemLitener;

    abstract void convert(StrongViewHolder holder, T resultBean);

    abstract int getLayoutId();

    public interface LoadMoreLitener{
        void loadMore(boolean reload);
    }
    public interface OnItemClickLitener{
        void itemClick(View view, GankBean gankBean);
    }

    public BaseAdapter(Context context, List<T> data){
        this.data = data;
        this.context = context;
        setDefaultFootView();
    }

    public void setOnItemClickLitener(OnItemClickLitener itemLitener){
        this.itemLitener = itemLitener;
    }
    public void setLoadMoreLitener(LoadMoreLitener lisener){
        this.lisener = lisener;
    }

    @Override
    public StrongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e(TAG, "onCreateViewHolder: " );
        if(viewType == TYPE_COMMON_VIEW) {
            return StrongViewHolder.create(context, getLayoutId(), parent);
        }else {
            if(footLayout == null){
                footLayout = new RelativeLayout(context);
            }
            return StrongViewHolder.create(footLayout);
        }
    }

    @Override
    public void onBindViewHolder(StrongViewHolder holder, final int position) {
        Log.e(TAG, "onBindViewHolder: " );
        if(holder.getItemViewType() == TYPE_COMMON_VIEW){
            convert(holder, (T) data.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemLitener.itemClick(view, (GankBean) data.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return loadMore && data.size() != 0 ? data.size() + 1 : data.size();

    }

    @Override
    public int getItemViewType(int position) {
        if(loadMore && isFootView(position)){
            return TYPE_FOOT_VIEW;
        }
        return TYPE_COMMON_VIEW;
    }

    public void changeFootLayoutId(int footId){
        footView = inflate(footId);
        addFootLayout(footView);
    }

    public void setData(List<T> data){
        this.data.addAll(data);
        notifyItemRangeInserted(this.data.size(), data.size());
    }

    public void clearData(){
        this.data.clear();
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        Log.e(TAG, "onAttachedToRecyclerView: " );
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager){
            ((GridLayoutManager) manager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(isFootView(position)){
                        return ((GridLayoutManager) manager).getSpanCount();
                    }
                    return 1;
                }
            });
        }
        startLoadMoreData(recyclerView);
    }

    protected void startLoadMoreData(RecyclerView recyclerView){
        if(!loadMore || lisener == null){
            return;
        }
        final RecyclerView.LayoutManager manager= recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Log.e(TAG, "onScrollStateChanged: " );
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    if (!isAutoLoadMore && findLastVisibleItemPosition(manager) + 1 == getItemCount()) {
                        scrollLoadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.e(TAG, "onScrolled: " + isAutoLoadMore );
                if (!isAutoLoadMore && findLastVisibleItemPosition(manager) + 1 == getItemCount()) {
                    scrollLoadMore();
                }else if(isAutoLoadMore){
                    isAutoLoadMore = false;
                }
            }
        });


    }

    private void scrollLoadMore(){
        if(footLayout.getChildAt(0) == failedView){
            addFootLayout(footView);
        }
        lisener.loadMore(false);
    }

    private int findLastVisibleItemPosition(RecyclerView.LayoutManager layoutManager){
        Log.e(TAG, "findLastVisibleItemPosition: + " +  (layoutManager instanceof StaggeredGridLayoutManager));
        if(layoutManager instanceof LinearLayoutManager){
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }else if(layoutManager instanceof StaggeredGridLayoutManager){
            int[] n = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            int max = n[0];
            for(int i : n){
                if(i > max){
                    max = i;
                    Log.e(TAG, "findLastVisibleItemPosition: \t\t" + i );
                }
            }
            return max;
        }
        return -1;
    }

    protected boolean isFootView(int position){
        if(loadMore && position >= getItemCount() - 1){
            return true;
        }
        return false;
    }

    @Override
    public void onViewAttachedToWindow(StrongViewHolder holder) {
        Log.e(TAG, "onViewAttachedToWindow: " );
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(holder.getItemViewType() == TYPE_FOOT_VIEW && lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams){
            ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(true);
            //lisener.loadMore();
        }
    }

    public void setLoadFailedView(int id){
        failedView = inflate(id);
        failedView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFootLayout(footView);
                lisener.loadMore(true);
            }
        });
        addFootLayout(failedView);
    }

    private void addFootLayout(View view){
        if(view == null){
            return;
        }
        if(footLayout == null){
            footLayout = new RelativeLayout(context);
        }
        footLayout.removeAllViews();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        footLayout.addView(view,params);
    }

    public View inflate(int id){
        return LayoutInflater.from(context).inflate(id, null);
    }

    private void setDefaultFootView(){
        footView = inflate(R.layout.foot_view);
        addFootLayout(footView);
    }
}

