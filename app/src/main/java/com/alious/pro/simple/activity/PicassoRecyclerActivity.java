package com.alious.pro.simple.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alious.pro.simple.R;
import com.alious.pro.simple.adapter.PicassoRecyclerAdapter;

/**
 * Picasso recycler view
 *
 * Created by aliouswang on 16/9/8.
 */
public class PicassoRecyclerActivity extends Activity{

    private RecyclerView recycler_view;
    private PicassoRecyclerAdapter mPicassoRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_recyecler);
        initView();
    }

    private void initView() {
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        mPicassoRecyclerAdapter = new PicassoRecyclerAdapter();
        recycler_view.setAdapter(mPicassoRecyclerAdapter);
    }
}
