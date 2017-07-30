package com.example.kk.gankio.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by kk on 2017/7/22.
 */

public class StrongViewHolder extends RecyclerView.ViewHolder {

    SparseArray<View> views;

    View rootView;

    public static StrongViewHolder create(Context context, int layoutId, ViewGroup parent){
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new StrongViewHolder(view);
    }
    public static StrongViewHolder create(View view){
        return new StrongViewHolder(view);
    }


    public StrongViewHolder(View itemView) {
        super(itemView);
        if(views == null) {
            views = new SparseArray<>();
        }
        rootView = itemView;
    }

    public <T extends View> T getView(int id){
        if(views.get(id) == null){
            View view = rootView.findViewById(id);
            views.put(id, view);
            return (T) view;
        }else{
            return (T) views.get(id);
        }
    }
}
