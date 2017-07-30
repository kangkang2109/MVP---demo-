package com.example.kk.gankio.utils;

import com.example.kk.gankio.bean.GankBean;

import java.util.List;

/**
 * Created by kk on 2017/7/21.
 */

public interface GankContract {

    interface View{

        void showGank(List<GankBean> lists);

        void showFailed(String s);

    }
    interface Presenter{

        void loadData(String type, int page);

    }
}
