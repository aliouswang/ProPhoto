package com.alious.pro.simple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView img_head;
    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        img_head = (ImageView) findViewById(R.id.img_head);
        img_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] screenLocation = new int[2];
                img_head.getLocationOnScreen(screenLocation);
                Intent intent = new Intent(MainActivity.this, ImageDetailActivity.class);
                intent.putExtra("left", screenLocation[0]).
                        putExtra("top", screenLocation[1]).
                        putExtra("width", img_head.getWidth()).
                        putExtra("height", img_head.getHeight());
                startActivity(intent);
            }
        });

//        btn_start = (Button) findViewById(R.id.btn_start);
//        btn_start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }
}
