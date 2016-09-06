package com.alious.pro.photo.library.interfaces;

/**
 * Created by aliouswang on 16/9/6.
 */
public interface NineGridDelegate<T extends NineImageUrl> {

    void onItemClicked(T t, int position);

}
