package com.cn.util.jiaMi_jieMi.ascii;

/**
 * ASCII（American Standard Code for Information Interchange，美国信息交换标准代码）
 *      是基于拉丁字母的一套电脑编码系统，主要用于显示现代英语和其他西欧语言。
 *      它是现今最通用的单字节编码系统，并等同于国际标准ISO/IEC 646。
 * @author mu_qingfeng
 * @date 2020/6/13 6:25 PM
 */
public class ASCIIDemo {

    public static void main(String[] args) {
//        char a = 'a';
//        int b = a;
        //打印b，在zascii当中十进制的数字对应是多少
//        System.out.println(b);

        // 定义字符串
        String a = "AaZ";
        // 需要拆开字符串
        char[] chars = a.toCharArray();
        for (char aChar : chars) {
            int asciiCode = aChar;
            System.out.println(asciiCode);
        }

    }
}
