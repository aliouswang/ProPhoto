package com.alious.pro.photo.library.adapter;

import com.alious.pro.photo.library.interfaces.IImageUrl;
import com.alious.pro.photo.library.interfaces.INineGridAdapter;

import java.util.List;

/**
 * Default nine image adpater
 *
 * Created by aliouswang on 16/9/5.
 */
public class DefaultNineImageAdapter<T extends IImageUrl>
        implements INineGridAdapter{

    private List<T> mDataSources;

    public DefaultNineImageAdapter() {

    }

    public DefaultNineImageAdapter(List<T> datas) {
        this.mDataSources = datas;
    }

    public List<T> getDataSources() {
        return mDataSources;
    }

    public void setDataSources(List<T> dataSources) {
        mDataSources = dataSources;
    }

    @Override
    public int getCount() {
        return getDataSources() != null ? getDataSources().size() : 0;
    }

    @Override
    public String getUrlByPosition(int position) {
        return getDataSources() != null ? getDataSources().get(position).getImageUrl() : "";
    }

}
