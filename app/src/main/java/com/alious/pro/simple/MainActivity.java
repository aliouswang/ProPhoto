package com.alious.pro.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.alious.pro.photo.library.adapter.DefaultNineImageAdapter;
import com.alious.pro.photo.library.interfaces.NineImageUrl;
import com.alious.pro.photo.library.ui.activity.ImageDetailActivity;
import com.alious.pro.photo.library.widget.NineFrescoGridViewGroup;
import com.alious.pro.photo.library.widget.NineGlideGridViewGroup;
import com.alious.pro.photo.library.widget.RatioImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//    private ScaleSimpleDraweeView img_head;

    private RatioImageView img_head;

    private Button btn_start;
    private float mScale;

    private NineGlideGridViewGroup nine_grid;
    private NineFrescoGridViewGroup nine_grid2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        img_head = (RatioImageView) findViewById(R.id.img_head);

//        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
//            @Override
//            public void onFinalImageSet(
//                    String id,
//                    @Nullable ImageInfo imageInfo,
//                    @Nullable Animatable anim) {
//                if (imageInfo == null) {
//                    return;
//                }
//                mScale = (float) imageInfo.getHeight() / (float) imageInfo.getWidth();
//            }
//
//            @Override
//            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
//            }
//
//            @Override
//            public void onFailure(String id, Throwable throwable) {
//            }
//        };
//        Uri uri = Uri.parse(Photo.images[0]);
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setControllerListener(controllerListener)
//                .setUri(uri)
//                .build();
//        img_head.setController(controller);

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

                final ArrayList<NineImageUrl> imageUrls = new ArrayList<>();
                for (String str : Photo.images) {
                    Data data = new Data();
                    data.setImage_url(str);
                    imageUrls.add(data);
                }

                startActivity(ImageDetailActivity.newIntent(MainActivity.this,
                        view, 7, imageUrls,
                        ((RatioImageView)view).getRatio()));
            }
        });

//        btn_start = (Button) findViewById(R.id.btn_start);
//        btn_start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });



        DefaultNineImageAdapter<Data> defaultNineImageAdapter =
                new DefaultNineImageAdapter(datas);
        nine_grid = (NineGlideGridViewGroup) findViewById(R.id.nine_grid);
        nine_grid.setGridAdapter(defaultNineImageAdapter);

        DefaultNineImageAdapter<Data> defaultNineImageAdapter2 =
                new DefaultNineImageAdapter(datas);
        nine_grid2 = (NineFrescoGridViewGroup) findViewById(R.id.nine_grid2);
        nine_grid2.setGridAdapter(defaultNineImageAdapter2);
    }
}
