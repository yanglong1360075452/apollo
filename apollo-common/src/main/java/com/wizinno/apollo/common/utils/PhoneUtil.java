package com.wizinno.apollo.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by HP on 2017/9/14.
 */
public class PhoneUtil {

    public static boolean phoneNumber(String phoneNumber) {
        if (phoneNumber == null || "".equals(phoneNumber)) {
            return false;
        }
        phoneNumber = phoneNumber.trim();
        String regExp = "^1\\d{10}$|^(0\\d{2,3}-?|\\(0\\d{2,3}\\))?[1-9]\\d{4,7}(-\\d{1,8})?$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phoneNumber);
        boolean flag = m.matches();
        return flag;
    }
}
