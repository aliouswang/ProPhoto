package com.alious.pro.photo.library.adapter;

import android.view.View;

import com.alious.pro.photo.library.interfaces.NineGridDataSource;
import com.alious.pro.photo.library.interfaces.NineGridDelegate;
import com.alious.pro.photo.library.interfaces.NineImageUrl;

import java.util.List;

/**
 * Default nine image adapter
 *
 * Created by aliouswang on 16/9/5.
 */
public class DefaultNineImageAdapter<T extends NineImageUrl>
        implements NineGridDataSource<T> {

    private List<T> mDataSources;
    private NineGridDelegate<T> mNineGridDelegate;

    public DefaultNineImageAdapter() {

    }

    public DefaultNineImageAdapter(List<T> datas) {
        this.mDataSources = datas;
    }

    public DefaultNineImageAdapter(List<T> datas, NineGridDelegate<T> nineGridDelegate) {
        this.mDataSources = datas;
        this.mNineGridDelegate = nineGridDelegate;
    }

    public List<T> getDataSources() {
        return mDataSources;
    }

    public void setDataSources(List<T> dataSources) {
        mDataSources = dataSources;
    }

    public NineGridDelegate<T> getNineGridDelegate() {
        return mNineGridDelegate;
    }

    public void setNineGridDelegate(NineGridDelegate<T> nineGridDelegate) {
        mNineGridDelegate = nineGridDelegate;
    }

    @Override
    public int getCount() {
        return getDataSources() != null ? getDataSources().size() : 0;
    }

    @Override
    public T getItem(int position) {
        return getDataSources() != null ? getDataSources().get(position) : null;
    }

    public void onItemClicked(View view, T t) {
        if (this.mNineGridDelegate != null) {
            this.mNineGridDelegate.onItemClicked(view, t);
        }
    }
}
