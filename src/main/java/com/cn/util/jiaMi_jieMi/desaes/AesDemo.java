package com.cn.util.jiaMi_jieMi.desaes;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 对称加密
 *  特点：
 *      加密速度快, 可以加密大文件
 *      密文可逆, 一旦密钥文件泄漏, 就会导致数据暴露
 *      加密后编码表找不到对应字符, 出现乱码
 *      一般结合Base64使用
 *
 *      AES : Advanced Encryption Standard, 高级加密标准 .在密码学中又称Rijndael加密法，是美国联邦政府采用的一种区块加密标
 *          准。这个标准用来替代原先的DES，已经被多方分析且广为全世界所使用。
 *
 * @author mu_qingfeng
 * @date 2020/6/14 4:25 PM
 */
public class AesDemo {
    public static void main(String[] args) throws Exception {
        // 原文
        String input = "沐清风";
        // 定义key
        // 如果使用des进行加密，那么密钥必须是8个字节
        // 如果使用的是AES加密，那么密钥必须是16个字节
        String key = "1234567812345678";
        // 算法
        String transformation = "AES";
        // 加密类型
        String algorithm = "AES";
        // 指定获取密钥的算法
        String encryptDES = encryptAES(input, key, transformation, algorithm);
        System.out.println("加密:"+encryptDES);

        String s = decryptAES(encryptDES, key, transformation, algorithm);
        System.out.println("解密:" + s);
    }




    /**
     * 使用AES加密数据
     *
     * @param input          : 原文
     * @param key            : 密钥(DES,密钥的长度必须是8个字节)
     * @param transformation : 获取Cipher对象的算法
     * @param algorithm      : 获取密钥的算法
     * @return : 密文
     * @throws Exception
     */
    private static String encryptAES(String input, String key, String transformation, String algorithm) throws Exception {
        // 获取加密对象
        Cipher cipher = Cipher.getInstance(transformation);
        // 创建加密规则
        // 第一个参数key的字节
        // 第二个参数表示加密算法
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), algorithm);
        // ENCRYPT_MODE：加密模式
        // DECRYPT_MODE: 解密模式
        // 初始化加密模式和算法
        cipher.init(Cipher.ENCRYPT_MODE,sks);
        // 加密
        byte[] bytes = cipher.doFinal(input.getBytes());

        // 输出加密后的数据
        String encode = Base64.encode(bytes);

        return encode;
    }

    /**
     * 使用AES解密
     *
     * @param input          : 密文
     * @param key            : 密钥
     * @param transformation : 获取Cipher对象的算法
     * @param algorithm      : 获取密钥的算法
     * @throws Exception
     * @return: 原文
     */
    private static String decryptAES(String input, String key, String transformation, String algorithm) throws Exception {
        // 1,获取Cipher对象
        Cipher cipher = Cipher.getInstance(transformation);
        // 指定密钥规则
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), algorithm);
        cipher.init(Cipher.DECRYPT_MODE, sks);
        // 3. 解密，上面使用的base64编码，下面直接用密文
        byte[] bytes = cipher.doFinal(Base64.decode(input));
        //  因为是明文，所以直接返回
        return new String(bytes);
    }

}
