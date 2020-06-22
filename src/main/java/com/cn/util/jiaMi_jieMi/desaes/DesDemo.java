package com.cn.util.jiaMi_jieMi.desaes;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * DES : Data Encryption Standard，即数据加密标准，是一种使用密钥加密的块算法，1977年被美国联邦政府的国家标准局确
 *      定为联邦资料处理标准（FIPS），并授权在非密级政府通信中使用，随后该算法在国际上广泛流传开来。
 *
 *  加密模式：
 *      https://docs.oracle.com/javase/8/docs/api/javax/crypto/Cipher.html
 *      ECB : Electronic codebook, 电子密码本. 需要加密的消息按照块密码的块大小被分为数个块，并对每个块进行独立加密
 *          优点 : 可以并行处理数据
 *          缺点 : 同样的原文生成同样的密文, 不能很好的保护数据
 *          同时加密，原文是一样的，加密出来的密文也是一样的
 *      CBC : Cipher-block chaining, 密码块链接. 每个明文块先与前一个密文块进行异或后，再进行加密。在这种方法中，每个密文块都依赖于它前面的所有明文块
 *          优点 : 同样的原文生成的密文不一样
 *          缺点 : 串行处理数据.
 * @author mu_qingfeng
 * @date 2020/6/14 5:28 PM
 */
public class DesDemo {

    public static void main(String[] args) throws Exception{
        // 原文 如果使用的是不填充的模式，那么原文必须是8个字节的整数倍
        String input = "清风12";
        // 定义key
        // 如果使用des进行加密，那么密钥必须是8个字节
        String key = "12345678";
        // 指定获取Cipher的算法,如果没有指定加密模式和填充模式,ECB/PKCS5Padding就是默认值
//        String transformation = "DES";    //qANksk5lvqM=
        // ECB:表示加密模式
        // PKCS5Padding:表示填充模式
//        String transformation = "DES/ECB/PKCS5Padding";
//        String transformation = "DES/CBC/PKCS5Padding"; //加密:VU3rn8/2ckvJuzkvIdmlkA==

        String transformation = "DES/CBC/NoPadding";//加密:VU3rn8/2cks=
        // 加密类型
        String algorithm = "DES";
        // 指定获取密钥的算法
        String encryptDES = encryptDES(input, key, transformation, algorithm);
        System.out.println("加密:" + encryptDES);

        String s = decryptDES(encryptDES, key, transformation, algorithm);
        System.out.println("解密:" + s);
    }

    /**
     * 解密
     * @param encryptDES  密文
     * @param key         密钥
     * @param transformation 加密算法
     * @param algorithm   加密类型
     * @return
     */
    private static String decryptDES(String encryptDES, String key, String transformation, String algorithm) throws Exception{
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(),algorithm);
        // 创建iv向量
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());
        //Cipher.DECRYPT_MODE:表示解密
        // 解密规则
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,iv);
        // 解密，传入密文
        byte[] bytes = cipher.doFinal(Base64.decode(encryptDES));

        return new String(bytes);
    }

    /**
     * 使用DES加密数据
     *
     * @param input          : 原文
     * @param key            : 密钥(DES,密钥的长度必须是8个字节)
     * @param transformation : 获取Cipher对象的算法
     * @param algorithm      : 获取密钥的算法
     * @return : 密文
     * @throws Exception
     */
    private static String encryptDES(String input, String key, String transformation, String algorithm) throws Exception {
        // 获取加密对象
        Cipher cipher = Cipher.getInstance(transformation);
        // 创建加密规则
        // 第一个参数key的字节
        // 第二个参数表示加密算法
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), algorithm);
        // 创建iv向量，iv向量，是使用到CBC加密模式
        // 在使用iv向量进行加密的时候，iv的字节也必须是8个字节
        IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
        // ENCRYPT_MODE：加密模式
        // DECRYPT_MODE: 解密模式
        // 初始化加密模式和算法
        cipher.init(Cipher.ENCRYPT_MODE,sks,iv);
        // 加密
        byte[] bytes = cipher.doFinal(input.getBytes());

        // 输出加密后的数据
        String encode = Base64.encode(bytes);

        return encode;
    }


}
