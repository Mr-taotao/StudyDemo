package com.example.chtlei.mydemo.videoview;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.VideoView;

import com.example.chtlei.mydemo.R;

/**
 * @Description: 启动页
 * @Author: jzhou
 * @CreateDate: 19-8-13 上午9:12
 */
public class VideoSplashActivity extends Activity  {
    private int mPositionWhenPaused = -1;
    private VideoView mVideoView;
//    private String path = "android.resource://" + getPackageName() + "/" + R.raw.vivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_view);

        mVideoView = findViewById(R.id.video_view);
        //mVideoView.setOnPreparedListener(this);
//        mVideoView.setOnCompletionListener(this);

//        getWindow().getDecorView().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startNextActivity();
//            }
//        }, 2000);
        requestSDpermission();
    }


    private void requestSDpermission() {
        if (ContextCompat.checkSelfPermission(VideoSplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(VideoSplashActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            initVideoPath();
        }
    }
    private void initVideoPath() {
//        File file = new File("android.resource://" + getPackageName() + "/" + R.raw.vivo,"vivo.mp4");
//        vvVideoView.setVideoPath(file.getPath());
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.vivo;
//        mVideoView.setVideoPath(uri);
//mVideoView.setVideoPath(uri);
        mVideoView.setVideoURI(Uri.parse(uri));
        mVideoView.start();

    }

    private void startNextActivity() {
//        boolean firstLogin = SPUtils.getBoolean(this, Constants.FIRST_LOGIN, true);
//        if (firstLogin) {
//            BirthSelectorActivity.start(this);
//        } else {
//            MainActivity.start(this);
//        }
//        finish();
    }

    @Override
    protected void onResume() {
        if (mPositionWhenPaused >= 0 && mVideoView != null) {
//            mVideoView.seekTo(mPositionWhenPaused);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mVideoView != null && mVideoView.isPlaying() && mVideoView.canPause()) {
            mPositionWhenPaused = mVideoView.getCurrentPosition();
//            mVideoView.pause();
        }
        super.onPause();
    }

//    @Override
//    public void onPrepared(MediaPlayer mp) {
//        //mp.setOnSeekCompleteListener(this);
//    }
//
//    @Override
//    public void onCompletion(MediaPlayer mp) {
////        mVideoView.start();
//        startNextActivity();
//    }
//
//    @Override
//    public void onSeekComplete(MediaPlayer mp) {
//        if (mPositionWhenPaused >= 0 && mVideoView != null) {
////            mVideoView.start();
//            mPositionWhenPaused = -1;
//        }
//    }
}
