package com.yt.autoupdate.http;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.yt.autoupdate.constant.MyConst.*;

import android.app.DownloadManager;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.File;


public class RequestService {

    /**
     * 单例
     */
    private static RequestService instance;

    /**
     * @return 获取单例
     */
    public static synchronized RequestService getInstance() {
        if (instance == null) {
            instance = new RequestService();
        }
        return instance;
    }

    public JSONObject checkVersion() throws Exception {
        String api = "/versionData/check?platform=1";
        String res = Request.getInstance().get(api, "");
        JSONObject dataJson = JSON.parseObject(res);
        return dataJson.getJSONObject("data");
    }

    public void download(Context context) {
        String api = "/versionData/download?platform=1&wp=gdc95211";
        String fileName = "tablet.apk";
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(URL + api);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, fileName);
        request.setMimeType("application/vnd.android.package-archive");
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);
        if (file.exists()) {
            file.delete();
        }
        downloadManager.enqueue(request);
    }
}
