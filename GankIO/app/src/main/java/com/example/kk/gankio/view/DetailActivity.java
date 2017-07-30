package com.example.kk.gankio.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.kk.gankio.R;
import com.example.kk.gankio.bean.GankBean;

import java.lang.reflect.Method;

/**
 * Created by kk on 2017/7/24.
 */

public class DetailActivity extends AppCompatActivity {

    private String TAG = "Detail";

    private WebView web;

    private ProgressBar progressBar;

    private Toolbar toolbar;

    private GankBean gankBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        gankBean = getIntent().getParcelableExtra("GankBean");
        initView();

    }

    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.bar);
        initToolBar();
        initWebView();
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initWebView() {
        web = (WebView) findViewById(R.id.webview);
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true); //webview支持Javascript
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式

        web.loadUrl(gankBean.getUrl());
        Log.e(TAG, "initWebView: " + gankBean.getUrl() );
        web.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e(TAG, "onPageStarted: " );
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e(TAG, "onPageFinished: " );
                progressBar.setVisibility(View.GONE);
            }
        });

        web.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.e(TAG, "onProgressChanged: " + newProgress );
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.e(TAG, "onReceivedTitle: " + title);
                toolbar.setTitle(title);
            }
        });


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && web.canGoBack()){
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    //销毁Webview
    //在关闭了Activity时，如果Webview的音乐或视频，还在播放。就必须销毁Webview
    //但是注意：webview调用destory时,webview仍绑定在Activity上
    //这是由于自定义webview构建时传入了该Activity的context对象
    //因此需要先从父容器中移除webview,然后再销毁webview:
    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy: " );
        if (web != null) {
            web.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            web.clearHistory();
            ((ViewGroup) web.getParent()).removeView(web);
            web.destroy();
            web = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.share:

                break;
            case R.id.copy:

                break;

            default:
                break;
        }
        return true;

    }


    /**
     * 显示menu选项的icon
     * @param view
     * @param menu
     * @return
     */
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try{
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }
}
