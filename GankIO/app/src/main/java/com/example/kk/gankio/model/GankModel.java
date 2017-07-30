package com.example.kk.gankio.model;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.example.kk.gankio.R;
import com.example.kk.gankio.utils.ApiStores;
import com.example.kk.gankio.utils.LoadLisener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executors;

import static android.R.attr.type;

/**
 * Created by kk on 2017/7/21.
 */

public class GankModel {

    String TAG = "model";

    public GankModel() {
    }

    public void getGankIoData(String path, final LoadLisener lisener) throws MalformedURLException {

        new AsyncTask<String, Void, String>(){
                @Override
                protected String doInBackground(String... strings) {
                    try {
                        URL url = new URL(strings[0]);
                        Log.e(TAG, "doInBackground: " + url );
                        String s;
                        StringBuilder result = new StringBuilder();
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        InputStream in = conn.getInputStream();
                        InputStreamReader read = new InputStreamReader(in);
                        BufferedReader buffer = new BufferedReader(read);
                        while((s = buffer.readLine()) != null){
                            result.append(s);
                        }
                        return result.toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(String s) {
                    if(s == null){
                        lisener.onFailed(s);
                    }else {
                        lisener.onSuccess(s);
                    }
                }
            }.execute(getUrl(path));

    }

    private String getUrl(String path) {
        return ApiStores.getGankIoApi() + path;
    }
}
