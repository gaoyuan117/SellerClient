package com.kaichaohulian.baocms;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;

public class DownloadService extends Service {
    private DownloadManager dm;
    private long enqueue;
    private BroadcastReceiver receiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/zhongyu.apk")),
                        "application/vnd.android.package-archive");
                startActivity(intent);
                stopSelf();
            }
        };

        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        String path = intent.getStringExtra("path");
        startDownload(path);
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void startDownload(String path) {
        try{
            dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(
                    Uri.parse(path));
            request.setMimeType("application/vnd.android.package-archive");
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "zhongyu.apk");
            enqueue = dm.enqueue(request);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}