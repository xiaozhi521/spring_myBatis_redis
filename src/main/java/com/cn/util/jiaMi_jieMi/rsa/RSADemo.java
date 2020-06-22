package com.cn.util.jiaMi_jieMi.rsa;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.commons.io.FileUtils;

import javax.crypto.Cipher;
import java.io.File;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 非对称加密
 *      ① 非对称加密算法又称现代加密算法。
 *      ② 非对称加密是计算机通信安全的基石，保证了加密数据不会被破解。
 *      ③ 与对称加密算法不同，非对称加密算法需要两个密钥：公开密钥(publickey) 和私有密(privatekey)
 *      ④ 公开密钥和私有密钥是一对
 *      ⑤ 如果用公开密钥对数据进行加密，只有用对应的私有密钥才能解密。
 *      ⑥ 如果用私有密钥对数据进行加密，只有用对应的公开密钥才能解密。
 *      ⑦ 因为加密和解密使用的是两个不同的密钥，所以这种算法叫作非对称加密算法。
 *
 *  特点
 *      加密和解密使用不同的密钥
 *      如果使用私钥加密, 只能使用公钥解密
 *      如果使用公钥加密, 只能使用私钥解密
 *      处理数据的速度较慢, 因为安全级别高
 *
 *  常见算法
 *      RSA
 *      ECC
 * @author mu_qingfeng
 * @date 2020/6/20 9:35 PM
 */
public class RSADemo {
    public static void main(String[] args) throws Exception {
        String input = "清风";
        // 加密算法
        String algorithm = "RSA";


//        generateKeyToFile(algorithm, "a.pub", "a.pri");


        String privateKey = encryptRSA(algorithm, getPrivateKey("a.pri", algorithm), input);
        String publicKey = decryptRSA(algorithm, getPublicKey("a.pub", algorithm), privateKey);



        //  创建密钥对生成器对象
//        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
//        // 生成密钥对
//        KeyPair keyPair = keyPairGenerator.generateKeyPair();
//        // 生成私钥
//        PrivateKey privateKey = keyPair.getPrivate();
//        // 生成公钥
//        PublicKey publicKey = keyPair.getPublic();
//        // 获取私钥字节数组
//        byte[] privateKeyEncoded = privateKey.getEncoded();
//        // 获取公钥字节数组
//        byte[] publicKeyEncoded = publicKey.getEncoded();
//        // 对公私钥进行base64编码
//        String privateKeyString = Base64.encode(privateKeyEncoded);
//        String publicKeyString = Base64.encode(publicKeyEncoded);
//        // 打印私钥
//        System.out.println(privateKeyString);
//        // 打印公钥
//        System.out.println(publicKeyString);
    }

    /**
     * 读取公钥
     * @param publicPath 公钥路径
     * @param algorithm  算法
     * @return
     */
    public static PublicKey getPublicKey(String publicPath, String algorithm) throws Exception{
        String publicKeyString = FileUtils.readFileToString(new File(publicPath), Charset.defaultCharset());
        // 创建key的工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        // 创建公钥规则
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(publicKeyString));
        return keyFactory.generatePublic(keySpec);
    }

    /**
     *  读取私钥
     * @param priPath 私钥的路径
     * @param algorithm 算法
     * @return 返回私钥的key对象
     */
    public static PrivateKey getPrivateKey(String priPath, String algorithm) throws Exception{
        String privateKeyString = FileUtils.readFileToString(new File(priPath), Charset.defaultCharset());
        // 创建key的工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        // 创建私钥key的规则
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyString));
        // 返回私钥对象
        return keyFactory.generatePrivate(keySpec);
    }


    /**
     * 解密数据
     *
     * @param algorithm      : 算法
     * @param encrypted      : 密文
     * @param key            : 密钥
     * @return : 原文
     * @throws Exception
     */
    public static String decryptRSA(String algorithm,Key key,String encrypted) throws Exception{
        // 创建加密对象
        // 参数表示加密算法
        Cipher cipher = Cipher.getInstance(algorithm);
        // 私钥进行解密
        cipher.init(Cipher.DECRYPT_MODE,key);
        // 由于密文进行了Base64编码, 在这里需要进行解码
        byte[] decode = Base64.decode(encrypted);
        // 对密文进行解密，不需要使用base64，因为原文不会乱码
        byte[] bytes1 = cipher.doFinal(decode);
        System.out.println(new String(bytes1));
        return new String(bytes1);

    }


    /**
     * 使用密钥加密数据
     *
     * @param algorithm      : 算法
     * @param input          : 原文
     * @param key            : 密钥
     * @return : 密文
     * @throws Exception
     */
    public static String encryptRSA(String algorithm,Key key,String input) throws Exception{
        // 创建加密对象
        // 参数表示加密算法
        Cipher cipher = Cipher.getInstance(algorithm);
        // 初始化加密
        // 第一个参数:加密的模式
        // 第二个参数：使用私钥进行加密
        cipher.init(Cipher.ENCRYPT_MODE,key);
        // 私钥加密
        byte[] bytes = cipher.doFinal(input.getBytes());
        // 对密文进行Base64编码
        System.out.println(Base64.encode(bytes));
        return Base64.encode(bytes);
    }




    /**
     * 保存公钥和私钥，把公钥和私钥保存到根目录
     * @param algorithm 算法
     * @param pubPath 公钥路径
     * @param priPath 私钥路径
     */
    private static void generateKeyToFile(String algorithm, String pubPath, String priPath) throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        // 生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 生成私钥
        PrivateKey privateKey = keyPair.getPrivate();
        // 生成公钥
        PublicKey publicKey = keyPair.getPublic();
        // 获取私钥的字节数组
        byte[] privateKeyEncoded = privateKey.getEncoded();
        // 获取公钥字节数组
        byte[] publicKeyEncoded = publicKey.getEncoded();
        // 使用base64进行编码
        String privateEncodeString = Base64.encode(privateKeyEncoded);
        String publicEncodeString = Base64.encode(publicKeyEncoded);
        // 把公钥和私钥保存到根目录
        FileUtils.writeStringToFile(new File(pubPath),publicEncodeString, Charset.forName("UTF-8"));
        FileUtils.writeStringToFile(new File(priPath),privateEncodeString, Charset.forName("UTF-8"));

    }
}
