package com.yjl.distributed.mq.config.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StyleCheckUtils {
    /**
     * 大陆号码或香港号码均可
     * 
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean isMobile(String str) throws PatternSyntaxException {
        return isCnMobile(str) || isHKMobile(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 此方法中前三位格式有： 13+任意数 15+除4的任意数 18+除1和4的任意数 17+除9的任意数 147
     * 
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean isCnMobile(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     * 
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean isHKMobile(String str) throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 邮箱格式验证
     * 
     * @param email
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean isEmail(String email) throws PatternSyntaxException {
        String regExp = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static void main(String[] args) {
        System.out.println(StyleCheckUtils.isCnMobile(555 + ""));
        System.out.println(StyleCheckUtils.isEmail("aa@163.cm"));
    }
}
