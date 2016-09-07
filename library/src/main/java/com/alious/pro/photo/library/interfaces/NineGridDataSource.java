package com.alious.pro.photo.library.interfaces;

import java.util.ArrayList;

/**
 * Created by aliouswang on 16/9/6.
 */
public interface NineGridDataSource<T extends NineImageUrl> {

    int getCount();

    T getItem(int position);

    ArrayList<T> getItemList();

}
