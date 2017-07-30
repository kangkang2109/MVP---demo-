package com.example.kk.gankio.view.fragment;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.kk.gankio.R;
import com.example.kk.gankio.bean.GankBean;
import com.example.kk.gankio.present.GankPresent;
import com.example.kk.gankio.utils.GankContract;
import com.example.kk.gankio.view.DetailActivity;
import com.example.kk.gankio.view.adapter.BaseAdapter;
import com.example.kk.gankio.view.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kk on 2017/7/21.
 */




public class GankFragment extends BaseMvpFragment implements GankContract.View {

    private String TAG = "view";

    private int mPage = 1;

    private String type;

    private GankContract.Presenter presenter;

    private RecyclerView recyclerView;

    private RecyclerViewAdapter adapter;

    private SwipeRefreshLayout swipe;

    private FloatingActionButton fab;

    private boolean refreshing;

    private boolean loading;

    private int mLastVisibleItemPosition;

    private LinearLayoutManager layoutManager;

    public GankFragment(String type) {
        this.type = type;
        presenter = new GankPresent(this);
    }


    /**
     * 有三种方式转换为主线
     * 1、Activity的 runOnUiThread
     * 2、View 的 Post
     * 3、handler 的 Post
     */
    @Override
    public void showGank(final List<GankBean> lists) {
        Log.e(TAG, "run: " + lists);
        adapter.setData(lists);
        changeStatus();
    }

    @Override
    public void showFailed(String s) {
        adapter.setLoadFailedView(R.layout.load_failed_layout);
        changeStatus();
    }

    public void changeStatus(){
        if(refreshing){
            swipe.setRefreshing(false);
            refreshing = false;
        }
        if(loading){
            loading = false;
        }
    }
    @Override
    protected void fetchData() {
        presenter.loadData(type, mPage);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_android;
    }

    @Override
    protected void initView(View rootView) {
        refreshing = true;
        loading = false;
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleview);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setHasFixedSize(true);
        swipe = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
        fab = (FloatingActionButton) rootView.findViewById(R.id.floatbutton);
        adapter = new RecyclerViewAdapter(getContext(), new ArrayList<GankBean>());
        adapter.setLoadMoreLitener(new BaseAdapter.LoadMoreLitener() {
            @Override
            public void loadMore(boolean reload) {
                if(!refreshing && !loading){
                    loading = true;
                    if(!reload){
                        mPage++;
                    }
                    fetchData();
                }
            }
        });
        adapter.setOnItemClickLitener(new BaseAdapter.OnItemClickLitener() {

            @Override
            public void itemClick(View view, GankBean gankBean) {
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra("GankBean", gankBean);
                startActivity(i);
            }
        });
        swipe.setRefreshing(true);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshing = true;
                mPage = 1;
                adapter.clearData();
                fetchData();
            }
        });
        recyclerView.setAdapter(adapter);
        //RecyclerView滚动监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if (mLastVisibleItemPosition < layoutManager.findLastVisibleItemPosition() && mLastVisibleItemPosition == 12) {
                    fab.show();
                }

                if (mLastVisibleItemPosition > layoutManager.findLastVisibleItemPosition() && fab.isShown()) {
                    fab.hide();
                }
                mLastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
