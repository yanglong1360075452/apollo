package com.wizinno.apollo.test.netTestLog;

import com.google.gson.Gson;
import com.wizinno.apollo.common.data.TokenInfo;
import com.wizinno.apollo.common.data.UserInfo;
import com.wizinno.apollo.common.exception.CustomException;
import com.wizinno.apollo.common.exception.CustomExceptionCode;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP on 2017/10/10.
 */
public class test {



        private static Logger logger = Logger.getLogger(test.class);
        /**
         * @param args
         */
        public static void main(String[] args){
            String a ="eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJqd3QiLCJpYXQiOjE1MTAyOTY0NDAsInN1YiI6IntcInVzZXJuYW1lXCI6XCJ5YW5nbG9uZ1wiLFwiaWRcIjpcIjYyZDc2ZmU2LTg4ZTktNGRhNS05NzFhLWQ1NTM5ZWMzYWFmMFwifSIsImV4cCI6NDY2Mzg5NjQ0MH0.73ucvsvl0APnVglWzPXupvnzalYlEkxlM5WRK4eePMU";
            String[] split = a.split(".");
            String result=a.substring(a.indexOf(".")+1,a.lastIndexOf("."));

            Base64 base64 = new Base64();
            String s = null;
            try {
                s = new String(base64.decode(result), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            Gson gson = new Gson();
            TokenInfo tokenInfo = gson.fromJson(s, TokenInfo.class);
            String sub = tokenInfo.getSub();
            UserInfo userInfo = gson.fromJson(sub, UserInfo.class);
            String id = userInfo.getId();
            System.out.print(id);

        }




}
