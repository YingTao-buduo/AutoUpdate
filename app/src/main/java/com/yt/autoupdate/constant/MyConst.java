package com.yt.autoupdate.constant;

public final class MyConst {
    private static final String IP = "192.168.3.2";
    private static final String PORT = "3881";

    public static final String URL = String.format("http://%s:%s", IP, PORT);
    public static final String URI = String.format("ws://%s:%s", IP, PORT);
}
