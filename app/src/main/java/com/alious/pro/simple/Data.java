package com.alious.pro.simple;

import com.alious.pro.photo.library.interfaces.NineImageUrl;

/**
 * Created by aliouswang on 16/9/6.
 */
public class Data implements NineImageUrl {

    private String image_url;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String getNineImageUrl() {
        return getImage_url();
    }
}
