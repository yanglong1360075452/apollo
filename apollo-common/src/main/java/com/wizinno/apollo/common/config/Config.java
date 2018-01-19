package com.wizinno.apollo.common.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by LiuMei on 2017-05-24.
 */
public class Config {

    protected static final Log logger = LogFactory.getLog(Config.class);
    public static final Properties p = new Properties();

    public static String getConfig(String key) {
        return (String) p.get(key);
    }

    /**
     * 静态读入属性文件到Properties p变量中
     */
    protected static void init(String propertyFileName) {
        InputStream in = null;
        try {
            in = Config.class.getClassLoader().getResourceAsStream(propertyFileName);
            if (in != null) {
                p.load(in);
            }
        } catch (IOException e) {
            logger.error("load " + propertyFileName + " into Constants error!");
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("close " + propertyFileName + " error!");
                }
            }
        }
    }

    /**
     * 封装了Properties类的getProperty函数,使p变量对子类透明.
     *
     * @param key          property key.
     * @param defaultValue 当使用property key在properties中取不到值时的默认值.
     */
    public static String getProperty(String key, String defaultValue) {
        return p.getProperty(key, defaultValue);
    }

    static {
        init("config.properties");
    }

    public final static String jwtId =getProperty("jwt.id", "jwt");

    public final static String jwtSecret = getProperty("jwt.secret", "7786df7fc3a34e26a61c034d5ec8245d");

    //6小时
    public final static Integer jwtExpire = Integer.parseInt(getProperty("jwt.expire", "21600000"));

    public final static String wechatAppId = getProperty("wechat.appId", "");
    public final static String wechatAppSecret = getProperty("wechat.appSecret", "");

    public final static String nfsMntRw = getProperty("rsync.nfs.rw", "/img/");


}
