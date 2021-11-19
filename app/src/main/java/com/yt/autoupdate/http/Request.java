package com.yt.autoupdate.http;

import static com.yt.autoupdate.constant.MyConst.*;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;


public class Request {

    /**
     * 单例
     */
    private static Request instance;

    /**
     * @return 获取单例
     */
    public static synchronized Request getInstance() {
        if (instance == null) {
            instance = new Request();
        }
        return instance;
    }

    public String get(String api, String token) throws Exception {
        Connection conn = Jsoup.connect(URL + api);
        conn.header("Token", token);
        Response response = conn.ignoreContentType(true)
                .method(Method.GET)
                .execute();
        return response.body();
    }
}
