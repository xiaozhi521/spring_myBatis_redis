package com.cn.util.jiaMi_jieMi.bytebit;

/**
 * @author mu_qingfeng
 * @date 2020/6/13 7:41 PM
 *
 *  根据编码的格式不一样，对应的字节也不一样
 *  如果是UTF-8:一个中文对应的是三个字节
 *  如果是GBK : 一个中文对应的是二个字节
 *
 *  如果是英文，就无所谓编码格式
 */
public class ByteBit {

    public static void main(String[] args) {
        byteBit("a");
        System.out.println("========");
        /**
         * 一个中文对应三个字节
         */
        byteBit("沐");
    }

    public static void byteBit(String content){
        byte[] bytes = content.getBytes();
//        byte[] bytes = content.getBytes("GBK");
        for (byte aByte : bytes) {
            int c = aByte;
            System.out.println(c);
            // byte 字节，对应的bit是多少
            String s = Integer.toBinaryString(c);
            System.out.println(s);
        }
    }
}
