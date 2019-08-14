package com.example.chtlei.mydemo.filedownload;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.chtlei.mydemo.R;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;

/**
 * Created by chtlei on 19-6-21.
 */

public class FileDownloadActivity extends Activity implements View.OnClickListener{
    private static final String TAG = FileDownloadActivity.class.getSimpleName();

    private Button startBtn;
    private Button pauseBtn;
    private Button deleteBtn;
    private TextView filenameTv;
    private TextView speedTv;

    private ProgressBar progressBar;
    private int downloadId;
    String url = "http://cdn.llsapp.com/android/LLS-v4.0-595-20160908-143200.apk";
    String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        initDownload();

        initView();
    }

    private void initDownload() {
        FileDownloader.setup(this);
    }

    private void startDownload() {
        path = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "mp4";
        downloadId = FileDownloader.getImpl().create(url)
                .setPath(path,true)
//                .setCallbackProgressTimes(300)
//                .setMinIntervalUpdateSpeed(400)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.i("LCT","startDownload pending totalBytes is " + totalBytes + " soFarBytes is " + soFarBytes);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.i("LCT","startDownload progress");
                        if (totalBytes == -1) {
                            // chunked transfer encoding data
                            progressBar.setIndeterminate(true);
                        } else {
                            progressBar.setMax(totalBytes);
                            progressBar.setProgress(soFarBytes);
                        }
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Log.i("LCT","startDownload completed");
                        progressBar.setIndeterminate(false);
                        progressBar.setMax(task.getSmallFileTotalBytes());
                        progressBar.setProgress(task.getSmallFileSoFarBytes());
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.i("LCT","startDownload paused");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.i("LCT","startDownload error");
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Log.i("LCT","startDownload warn");
                    }
                })
                .start();
    }

    private void initView() {
        startBtn = findViewById(R.id.start_btn_1);
        startBtn.setOnClickListener(this);

        pauseBtn = findViewById(R.id.pause_btn_1);
        pauseBtn.setOnClickListener(this);

        deleteBtn = findViewById(R.id.delete_btn_1);
        deleteBtn.setOnClickListener(this);

        filenameTv = findViewById(R.id.filename_tv_1);
        speedTv = findViewById(R.id.speed_tv_1);
        progressBar = findViewById(R.id.progressBar_1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_btn_1:
                startDownload();
                break;
            case R.id.pause_btn_1:

                break;
            case R.id.delete_btn_1:
                try {
                    deleteAllFiles(new File(path));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LCT","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LCT","onDestroy");
    }
}
