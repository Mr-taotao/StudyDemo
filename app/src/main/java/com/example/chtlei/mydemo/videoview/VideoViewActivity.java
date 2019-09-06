package com.example.chtlei.mydemo.videoview;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.chtlei.mydemo.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by chtlei on 19-8-16.
 */

public class VideoViewActivity extends Activity {

    @BindView(R.id.vv_VideoView)
    VideoView vvVideoView;
    Unbinder mUnbinder;
    @BindView(R.id.play)
    Button play;
    @BindView(R.id.pause)
    Button pause;
    @BindView(R.id.replay)
    Button replay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        mUnbinder = ButterKnife.bind(this);
        requestSDpermission();
        initVideoPath();
    }

    private void requestSDpermission() {
        if (ContextCompat.checkSelfPermission(VideoViewActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(VideoViewActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            initVideoPath();
        }
    }

    private void initVideoPath() {
//        File file = new File("android.resource://" + getPackageName() + "/" + R.raw.vivo,"vivo.mp4");
//        vvVideoView.setVideoPath(file.getPath());
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.vivo;
//        vvVideoView.setVideoPath(uri);
//mVideoView.setVideoPath(uri);
        vvVideoView.setVideoURI(Uri.parse(uri));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initVideoPath();
                } else {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        if (vvVideoView != null) {
            vvVideoView.suspend();
        }
    }

    @OnClick({R.id.play, R.id.pause, R.id.replay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.play:
                if (!vvVideoView.isPlaying()) {
                    vvVideoView.start();
                }
                break;
            case R.id.pause:
                if (vvVideoView.isPlaying()) {
                    vvVideoView.pause();
                }
                break;
            case R.id.replay:
                if (vvVideoView.isPlaying()) {
                    vvVideoView.resume();
                }
                break;
        }
    }
}
