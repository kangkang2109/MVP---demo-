package com.example.kk.gankio.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by kk on 2017/7/21.
 */

public abstract class BaseMvpFragment extends BaseFragment {

    private boolean visible;

    private boolean prepare;

    protected abstract void fetchData();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        visible = isVisibleToUser;
        initFetch();
    }

    protected void initFetch(){
        if(visible && prepare){
            fetchData();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prepare = true;
        initFetch();
    }
}
