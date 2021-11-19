package com.yt.autoupdate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.yt.autoupdate.constant.MyConst;
import com.yt.autoupdate.http.RequestService;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            versionUpdate();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    void versionUpdate() throws PackageManager.NameNotFoundException {
        String currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    JSONObject res = RequestService.getInstance().checkVersion();
                    if (currentVersion.equals(res.getString("version"))) {
                        return;
                    }
                    RequestService.getInstance().download(MainActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}