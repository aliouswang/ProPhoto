package com.alious.pro.simple.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.alious.pro.simple.R;

public class MainActivity extends AppCompatActivity {

    private Button btn_goto_fresco;
    private Button btn_goto_picasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ImagePipeline imagePipeline = Fresco.getImagePipeline();
//        imagePipeline.clearCaches();
        initView();
    }

    private void initView() {
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
