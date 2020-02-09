package com.pxq.lib;

import java.io.UnsupportedEncodingException;

import crypt.CryptUtil;

public class MyClass {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String key = "6837c0e3716f406daeecce1eb82c512d";
        String src = "itvAccount=testiptv002|contentId=50000024371550646713786578640499|contentName=绝世千金第一季";
        String result = CryptUtil.doEncryptWithUrlBase64(src, key, "01234567", "utf-8");
        result = CryptUtil.urlDecode(result, "utf-8");
        System.out.println("加密结果：" + result);
    }
}
