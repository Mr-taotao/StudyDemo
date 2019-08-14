package com.example.chtlei.mydemo.photowall;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.chtlei.mydemo.R;

/**
 * Created by chtlei on 18-11-8.
 */

public class PhotoWallActivity extends Activity {
    public static final String TAG = "PhotoWallActivity";
    private long mLastTime;
    private long mCurrTime;
    /**
     * 用于展示照片墙的GridView
     */
    private GridView mPhotoWall;

    /**
     * GridView的适配器
     */
    private PhotoWallAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_photo_wall);

        setContentView(R.layout.activity_photo_wall_recycle);

//        bindView();

        initView();
    }

    private void bindView() {
        mPhotoWall = (GridView) findViewById(R.id.photo_wall);
        adapter = new PhotoWallAdapter(this, 0, Images.imageThumbUrls, mPhotoWall);
        mPhotoWall.setAdapter(adapter);
        mPhotoWall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG,"onItemClick position is " + position);
                mLastTime = mCurrTime;
                mCurrTime = System.currentTimeMillis();
                if (mCurrTime - mLastTime < 500) {
                    mLastTime = 0;
                    mCurrTime = 0;
                    Log.i(TAG,"onItemClick double click ");
                } else {

                }
            }
        });
    }

    private void initView () {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        PhotoRecycleViewAdapter adapter = new PhotoRecycleViewAdapter(this,Images.imageThumbUrls);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator (new DefaultItemAnimator());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出程序时结束所有的下载任务
//        adapter.cancelAllTasks();
    }
}
