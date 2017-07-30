package com.example.kk.gankio.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.gankio.R;
import com.example.kk.gankio.bean.GankBean;
import com.example.kk.gankio.utils.ImageLoader;

import java.util.List;

/**
 * Created by kk on 2017/7/22.
 */

public class RecyclerViewAdapter extends BaseAdapter<GankBean> {

    private String TAG = "adapter";

    private Context context;

    public RecyclerViewAdapter(Context context, List<GankBean> data) {
        super(context, data);
        this.context = context;
    }

    @Override
    void convert(StrongViewHolder holder, GankBean resultBean) {
        holder.<TextView>getView(R.id.userName).setText(resultBean.getWho());
        holder.<TextView>getView(R.id.date).setText(resultBean.getPublishAt());
        holder.<TextView>getView(R.id.content).setText(resultBean.getDesc());
        ImageView image = holder.getView(R.id.icon);
        String images = resultBean.getImages();
        Log.e(TAG, "convert: images" + images );
        if (images != null) {
            ImageLoader.load(context,
                    images.replace("\\", "") + "?imageView2/0/w/100", image, R.drawable.web);
        } else {
            String url = resultBean.getUrl();
            int iconId;
            if (url.contains("github")) {
                iconId = R.drawable.github;
            } else if (url.contains("jianshu")) {
                iconId = R.drawable.jianshu;
            } else if (url.contains("csdn")) {
                iconId = R.drawable.csdn;
            } else if (url.contains("miaopai")) {
                iconId = R.drawable.miaopai;
            } else if (url.contains("acfun")) {
                iconId = R.drawable.acfun;
            } else if (url.contains("bilibili")) {
                iconId = R.drawable.bilibili;
            } else if (url.contains("youku")) {
                iconId = R.drawable.youku;
            } else if (url.contains("weibo")) {
                iconId = R.drawable.weibo;
            } else if (url.contains("weixin")) {
                iconId = R.drawable.weixin;
            } else {
                iconId = R.drawable.web;
            }
            ImageLoader.load(context, iconId, image);
        }
    }

    @Override
    int getLayoutId() {
        return R.layout.gank_item;
    }
}
