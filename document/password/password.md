### 1、[凯撒加密](https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/src/main/java/com/cn/util/jiaMi_jieMi/ascii/ASCIIDemo.java)

#### 1.1、[频度分析法破解恺撒加密](https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/src/main/java/com/cn/util/jiaMi_jieMi/ascii/ASCIIDemo.java)

### 2、[Byte和bit][2]

### 3、[DES加密解密][3]

### 4、[AES加密解密][4]

### 5、[toString()与new String ()用法区别][5]

### 6、[消息摘要][6]
- **消息摘要（Message Digest）又称为数字摘要(Digital Digest)**
- **它是一个唯一对应一个消息或文本的固定长度的值，它由一个单向Hash加密函数对消息进行作用而产生**
- **使用数字摘要生成的值是不可以篡改的，为了保证文件或者值的安全**

#### 6.1、 特点
         
- 无论输入的消息有多长，计算出来的消息摘要的长度总是固定的。例如应用MD5算法摘要的消息有128个比特位，用SHA-1算法摘要的消息最终有160比特位的输出
- 只要输入的消息不同，对其进行摘要以后产生的摘要消息也必不相同；但相同的输入必会产生相同的输出
- 消息摘要是单向、不可逆的

#### 6.2、常见算法
- MD5
- SHA1
- SHA256
- SHA512

#### 6.3、[在线工具](https://tool.oschina.net/encrypt?type=2)
#### 6.4、总结
    MD5算法 : 摘要结果16个字节, 转16进制后32个字节
    SHA1算法 : 摘要结果20个字节, 转16进制后40个字节
    SHA256算法 : 摘要结果32个字节, 转16进制后64个字节
    SHA512算法 : 摘要结果64个字节, 转16进制后128个字节
### 7、[非对称加密][7]
- 简介：

    - ① 非对称加密算法又称现代加密算法。

    - ② 非对称加密是计算机通信安全的基石，保证了加密数据不会被破解。

    - ③ 与对称加密算法不同，非对称加密算法需要两个密钥：公开密钥(publickey) 和私有密(privatekey)

    - ④ 公开密钥和私有密钥是一对

    - ⑤ 如果用公开密钥对数据进行加密，只有用对应的私有密钥才能解密。

    - ⑥ 如果用私有密钥对数据进行加密，只有用对应的公开密钥才能解密。

    - ⑦ 因为加密和解密使用的是两个不同的密钥，所以这种算法叫作非对称加密算法。

- 示例
    - 首先生成密钥对, 公钥为(5,14), 私钥为(11,14)
    - 现在A希望将原文2发送给B
    - A使用公钥加密数据. 2的5次方mod 14 = 4 , 将密文4发送给B
    - B使用私钥解密数据. 4的11次方mod14 = 2, 得到原文2
- 特点
    - 加密和解密使用不同的密钥
    - 如果使用私钥加密, 只能使用公钥解密
    - 如果使用公钥加密, 只能使用私钥解密
    - 处理数据的速度较慢, 因为安全级别高
- 常见算法
    - RSA
    - ECC
### 8、[数字签名][8]

    数字签名（又称公钥数字签名）是只有信息的发送者才能产生的别人无法伪造的一段数字串，这段数字串同时也是对信息的发送者发送信息真实性的一个有效证明。它是一种类似写在纸上的普通的物理签名，但是使用了公钥加密领域的技术来实现的，用于鉴别数字信息的方法。一套数字签名通常定义两种互补的运算，一个用于签名，另一个用于验证。数字签名是非对称密钥加密技术与数字摘要技术的应用。

#### 8.1、keytool工具使用
Mac 
   - 查看jdk安装路径：/usr/libexec/java_home -V
    
    Matching Java Virtual Machines (1):
        1.8.0_241, x86_64:	"Java SE 8"	/Library/Java/JavaVirtualMachines/jdk1.8.0_241.jdk/Contents/Home
    
    /Library/Java/JavaVirtualMachines/jdk1.8.0_241.jdk/Contents/Home - 这个路径就是jdk安装路径
    
   - keytool工具路径：/Library/Java/JavaVirtualMachines/jdk1.8.0_241.jdk/Contents/Home
   
    dembp bin % keytool
    密钥和证书管理工具
    命令:
     -certreq            生成证书请求
     -changealias        更改条目的别名
     -delete             删除条目
     -exportcert         导出证书
     -genkeypair         生成密钥对
     -genseckey          生成密钥
     -gencert            根据证书请求生成证书
     -importcert         导入证书或证书链
     -importpass         导入口令
     -importkeystore     从其他密钥库导入一个或所有条目
     -keypasswd          更改条目的密钥口令
     -list               列出密钥库中的条目
     -printcert          打印证书内容
     -printcertreq       打印证书请求的内容
     -printcrl           打印 CRL 文件的内容
     -storepasswd        更改密钥库的存储口令
     
 





[2]: https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/src/main/java/com/cn/util/jiaMi_jieMi/bytebit/ByteBit.java
[3]: https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/src/main/java/com/cn/util/jiaMi_jieMi/desaes/DesDemo.java
[4]: https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/src/main/java/com/cn/util/jiaMi_jieMi/desaes/AesDemo.java

[5]: https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/src/main/java/com/cn/util/jiaMi_jieMi/TestString.java
[6]: https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/src/main/java/com/cn/util/jiaMi_jieMi/digest/DigestDemo.java

[7]: https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/src/main/java/com/cn/util/jiaMi_jieMi/rsa/RSADemo.java

[8]: https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/src/main/java/com/cn/util/jiaMi_jieMi/rsa/SignatureDemo.java



