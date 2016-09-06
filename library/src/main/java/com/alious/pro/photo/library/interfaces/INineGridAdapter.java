package com.alious.pro.photo.library.interfaces;

public interface INineGridAdapter<T extends NineImageUrl> {

    int getCount();

    T getItem(int position);

    void onItemClicked(T t, int position);

}