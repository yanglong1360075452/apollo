package com.wizinno.apollo.app.config;

/**
 * Created by LiuMei on 2017-05-03.
 */
public class Constant {

    /**
     * jwt
     */
    public static final String JWT_ID = "jwt";
    public static final String JWT_SECRET = "7786df7fc3a34e26a61c034d5ec8245d";
    public static final Long JWT_TTL = 3153600000000L;  //millisecond 一百年
    public static final int JWT_REFRESH_INTERVAL = 55*60*1000;  //millisecond
    public static final int JWT_REFRESH_TTL = 12*60*60*1000; //millisecond
}
