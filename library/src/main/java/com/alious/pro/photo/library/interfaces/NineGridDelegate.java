package com.alious.pro.photo.library.interfaces;

import android.view.View;

/**
 * Created by aliouswang on 16/9/6.
 */
public interface NineGridDelegate<T extends NineImageUrl> {

    void onItemClicked(View view, T t);

}
