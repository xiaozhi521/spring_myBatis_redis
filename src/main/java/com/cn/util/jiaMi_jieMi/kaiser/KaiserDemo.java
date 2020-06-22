package com.cn.util.jiaMi_jieMi.kaiser;

/**
 *  凯撒加密
 *      它是一种替换加密的技术，明文中的所有字母都在字母表上向后（或向前）按照一个固定数目进行偏移后被替换成密文
 * @author mu_qingfeng
 * @date 2020/6/13 6:35 PM
 */
public class KaiserDemo {

    //偏移量，秘钥
    public static final int KEY = 3;

    public static void main(String[] args) {
        // 定义原文
        String input = "Hello World";
        // 把原文右边移动3位
        // 凯撒加密
        String s = encryptKaiser(input);
        System.out.println("加密:" + s);
        String s1 = dencryptKaiser(s);
        System.out.println("明文:"+s1);
    }


    /**
     *  解密
     * @param encryptContent 密文
     * @return
     */
    public static String dencryptKaiser(String encryptContent){
        String str = "";
        char[] chars = encryptContent.toCharArray();
        for(char oldChar : chars){
            int a = oldChar;
            //偏移数据
            a -= KEY;
            char newChar = (char) a;
            str += newChar;
        }
        return str;
    }

    /**
     * 解密
     * @param encryptContent 密文
     * @param key 偏移量
     * @return
     */
    public static String dencryptKaiser(String encryptContent,int key){
        String str = "";
        char[] chars = encryptContent.toCharArray();
        for(char oldChar : chars){
            int a = oldChar;
            //偏移数据
            a -= key;
            char newChar = (char) a;
            str += newChar;
        }
        return str;
    }

    /**
     *  加密
     * @param content 原文
     * @return
     */
    public static String encryptKaiser(String content){
        String str = "";
        char[] chars = content.toCharArray();
        for(char oldChar : chars){
            int a = oldChar;
            //偏移数据
            a += KEY;
            char newChar = (char) a;
            str += newChar;
        }
        return str;
    }

    /**
     * 加密
     * @param content 原文
     * @param key 偏移量
     * @return
     */
    public static String encryptKaiser(String content,int key){
        String str = "";
        char[] chars = content.toCharArray();
        for(char oldChar : chars){
            int a = oldChar;
            //偏移数据
            a += key;
            char newChar = (char) a;
            str += newChar;
        }
        return str;
    }
}
