package com.alious.pro.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alious.pro.photo.library.adapter.DefaultNineImageAdapter;
import com.alious.pro.photo.library.widget.NineFrescoGridViewGroup;
import com.alious.pro.photo.library.widget.NinePicassoGridViewGroup;
import com.alious.pro.photo.library.widget.RatioImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RatioImageView img_head;

    private NinePicassoGridViewGroup nine_grid;
    private NineFrescoGridViewGroup nine_grid2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        img_head = (RatioImageView) findViewById(R.id.img_head);

        Picasso.with(MainActivity.this)
                .load(Photo.images[7])
                .into(img_head);

        final ArrayList<Data> datas = new ArrayList<>();
        for (String str : Photo.images) {
            Data data = new Data();
            data.setImage_url(str);
            datas.add(data);
        }

        img_head.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                        }
                                    });

        DefaultNineImageAdapter<Data> defaultNineImageAdapter =
                new DefaultNineImageAdapter(datas);
        nine_grid = (NinePicassoGridViewGroup) findViewById(R.id.nine_grid);
        nine_grid.setGridAdapter(defaultNineImageAdapter);

        DefaultNineImageAdapter<Data> defaultNineImageAdapter2 =
                new DefaultNineImageAdapter(datas);
        nine_grid2 = (NineFrescoGridViewGroup) findViewById(R.id.nine_grid2);
        nine_grid2.setGridAdapter(defaultNineImageAdapter2);
    }
}
