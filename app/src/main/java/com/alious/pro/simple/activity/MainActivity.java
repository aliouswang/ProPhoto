package com.alious.pro.simple.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.alious.pro.photo.library.widget.NineFrescoGridViewGroup;
import com.alious.pro.photo.library.widget.NinePicassoGridViewGroup;
import com.alious.pro.photo.library.widget.RatioImageView;
import com.alious.pro.simple.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;

public class MainActivity extends AppCompatActivity {

    private RatioImageView img_head;

    private NinePicassoGridViewGroup nine_grid;
    private NineFrescoGridViewGroup nine_grid2;

    private Button btn_goto_fresco;
    private Button btn_goto_picasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
//        imagePipeline.clearCaches();
        initView();
    }

    private void initView() {
//        img_head = (RatioImageView) findViewById(R.id.img_head);
//
//        Picasso.with(MainActivity.this)
//                .load(Photo.images[7])
//                .into(img_head);
//
//        final ArrayList<Data> datas = new ArrayList<>();
//        for (String str : Photo.images) {
//            Data data = new Data();
//            data.setImage_url(str);
//            datas.add(data);
//        }
//
//        img_head.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                        }
//                                    });

//        DefaultNineImageAdapter<Data> defaultNineImageAdapter =
//                new DefaultNineImageAdapter(datas);
//        nine_grid = (NinePicassoGridViewGroup) findViewById(R.id.nine_grid);
//        nine_grid.setGridAdapter(defaultNineImageAdapter);
//
//        DefaultNineImageAdapter<Data> defaultNineImageAdapter2 =
//                new DefaultNineImageAdapter(datas);
//        nine_grid2 = (NineFrescoGridViewGroup) findViewById(R.id.nine_grid2);
//        nine_grid2.setGridAdapter(defaultNineImageAdapter2);
//
        btn_goto_fresco = (Button) findViewById(R.id.btn_goto_fresco);
        btn_goto_fresco.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FrescoRecyclerActivity.class);
                startActivity(intent);
            }
        });

        btn_goto_picasso = (Button) findViewById(R.id.btn_goto_picasso);
        btn_goto_picasso.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PicassoRecyclerActivity.class);
                startActivity(intent);
            }
        });
    }
}
