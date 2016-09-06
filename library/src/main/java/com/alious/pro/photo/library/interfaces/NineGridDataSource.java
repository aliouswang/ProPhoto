package com.alious.pro.photo.library.interfaces;

/**
 * Created by aliouswang on 16/9/6.
 */
public interface NineGridDataSource<T extends NineImageUrl> {

    int getCount();

    T getItem(int position);

}
