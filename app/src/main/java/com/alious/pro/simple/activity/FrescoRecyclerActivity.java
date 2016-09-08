package com.alious.pro.simple.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alious.pro.simple.R;
import com.alious.pro.simple.adapter.FrescoRecyclerAdapter;

/**
 * Created by aliouswang on 16/9/8.
 */
public class FrescoRecyclerActivity extends Activity{

    private RecyclerView recycler_view;
    private FrescoRecyclerAdapter mFrescoRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco_recyecler);
        initView();
    }

    private void initView() {
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        mFrescoRecyclerAdapter = new FrescoRecyclerAdapter();
        recycler_view.setAdapter(mFrescoRecyclerAdapter);
    }
}
