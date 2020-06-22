package com.cn.util.jiaMi_jieMi.digest;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * @author mu_qingfeng
 * @date 2020/6/20 7:07 PM
 * @Description:消息摘要算法
 *
 */
public class DigestDemo {
    public static void main(String[] args) throws Exception{
        /**
         * 4124bc0a9335c27f086f24ba207a4912     md5 在线校验
         * 4124bca9335c27f86f24ba207a4912
         * 4124bc0a9335c27f086f24ba207a4912
         * QSS8CpM1wn8IbyS6IHpJEg==             消息摘要使用的是16进制
         */
        // 原文
        String input = "aa";
        // 算法
        String algorithm = "MD5";



        String md5 = getDigest(input, "MD5");
        System.out.println(md5);

        String sha1 = getDigest(input, "SHA-1");
        System.out.println(sha1);

        String sha256 = getDigest(input, "SHA-256");
        System.out.println(sha256);

        String sha512 = getDigest(input, "SHA-512");
        System.out.println(sha512);


    }

    /**
     * 获取数字摘要
     * @param input
     * @param algorithm
     * @return
     * @throws Exception
     */
    public static String getDigest(String input,String algorithm) throws Exception {
        // 获取数字摘要对象
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        // 获取消息数字摘要的字节数组
        byte[] digest = messageDigest.digest(input.getBytes());
        System.out.println("密文的字节长度:" + digest.length);
        //对密文进行迭代
        return toHex(digest);
    }

    public static String toHex(byte[] digest){
//        System.out.println(new String(digest));
//        //使用base64转码
//        System.out.println(Base64.encode(digest));
        String newStr = "";
        for (byte b : digest) {
            //把密文转化成16进制
            String s = Integer.toHexString(b & 0xff);

//            System.out.print(s);

            if (s.length() == 1){
                // 如果生成的字符只有一个，前面补0
                s = "0"+s;
            }
            newStr+=s;
        }
        System.out.println("16进制数据的长度：" + newStr.getBytes().length);
        return newStr;

    }
}
