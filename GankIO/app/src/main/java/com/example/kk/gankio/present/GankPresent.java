package com.example.kk.gankio.present;

import android.util.Log;

import com.example.kk.gankio.bean.GankBean;
import com.example.kk.gankio.model.GankModel;
import com.example.kk.gankio.utils.GankContract;
import com.example.kk.gankio.utils.LoadLisener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kk on 2017/7/21.
 */

public class GankPresent implements GankContract.Presenter {

    String TAG = "present";

    GankContract.View view;

    GankModel model;

    public GankPresent(GankContract.View view) {
        this.view = view;
        model = new GankModel();
    }

    /**
     * URLEncoder.encode(type, "utf-8")
     * url解决中文问题
     *
     * @param type
     * @param page
     */
    @Override
    public void loadData(String type, int page) {
        Log.e(TAG, "loadData: " + type );
        try {
            model.getGankIoData(URLEncoder.encode(type, "utf-8") + "/10/" + page, new LoadLisener() {
                @Override
                public void onSuccess(String s) {
                    view.showGank(getListGankDataFromJson(s));
                }


                @Override
                public void onFailed(String s) {
                    view.showFailed(s);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<GankBean> getListGankDataFromJson(String s) {
        if(s == null){
            return null;
        }
        try {
            Log.e(TAG, "getListGankDataFromJson: " + s );
            JSONObject json = new JSONObject(s);
            JSONArray results = json.getJSONArray("results");

            if (results == null) {
                return null;
            }
            List<GankBean> lists = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject res = results.getJSONObject(i);
                GankBean gb = new GankBean();
                gb.setCreatedAt(res.getString("createdAt"));
                gb.setDesc(res.getString("desc"));
                gb.setSource(res.getString("source"));
                gb.setUrl(res.getString("url"));
                gb.setWho(res.getString("who"));
                gb.setType(res.getString("type"));
                gb.setId(res.getString("_id"));
                gb.setPublishAt(res.getString("publishedAt").substring(0, 10));
                if (res.has("images")) {
                    gb.setImages(res.getString("images").split("\"")[1]);
                    Log.e(TAG, "getListGankDataFromJson: " + gb.getImages() );
                }
                lists.add(gb);
                Log.e(TAG, "getListGankDataFromJson: " + gb.toString() );
            }
            return lists;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
