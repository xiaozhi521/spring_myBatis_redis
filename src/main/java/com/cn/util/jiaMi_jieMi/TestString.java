package com.cn.util.jiaMi_jieMi;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/**
 * @author mu_qingfeng
 * @date 2020/6/14 5:13 PM
 *
 *  toString()与new String ()用法区别
 *      1、str.toString是调用了这个object对象的类的toString方法。一般是返回这么一个String：[class name]@[hashCode]
 *      2、new String(str)是根据parameter是一个字节数组，使用java虚拟机默认的编码格式，将这个字节数组decode为对应的字符。
 *          若虚拟机默认的编码格式是ISO-8859-1，按照ascii编码表即可得到字节对应的字符
 *
 *  什么时候用什么方法呢？
 *      new String（）一般使用字符转码的时候,byte[]数组的时候
 *      toString（）对象打印的时候使用
 *
 */
public class TestString {
    public static void main(String[] args) {
        // 表示密文
        String str="TU0jV0xBTiNVYys5bEdiUjZlNU45aHJ0bTdDQStBPT0jNjQ2NDY1Njk4IzM5OTkwMDAwMzAwMA==";
        // 使用base64进行解码
        String rlt1=new String(Base64.decode(str));
        // 使用base64进行解码
        String rlt2=Base64.decode(str).toString(
        );

        System.out.println("new String：" + rlt1);

        System.out.println("toString：" + rlt2);
    }
}
