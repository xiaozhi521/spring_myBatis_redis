package com.cn.util;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

public class SessionFactoryUtil {
    public static SqlSessionFactory getSession() throws IOException {
        String resource = "mybatis-config.xml";
        //加载mybatis的配置文件（它也加载关联的映射文件）
        Reader reader = Resources.getResourceAsReader(resource);
        //构建sqlSession的工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        return sessionFactory;
    }

    public static SqlSessionFactory getSession1(){
        SqlSessionFactory sqlSessionFactory = null;
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSessionFactory;
    }
    public static SqlSessionFactory getSession2(){
        //数据库连接池信息
        PooledDataSource dataSource = new PooledDataSource();
        dataSource.setDriver("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mybatis001");
        dataSource.setDefaultAutoCommit(false);
        //采用MyBatis 的JDBC 事务方式
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development",transactionFactory,dataSource);
        //创建Configuration对象
        Configuration configuration = new Configuration(environment);
        //注册一个MyBatis 上下文别名
        configuration.getTypeAliasRegistry().registerAliases("com.org.bean");
        //注册自定义typeHandler
//        configuration.getTypeHandlerRegistry().register("com.org.typeHandler");
        //加入一个映射器
        configuration.addMappers("com.cn.dao");
        //使用 SqlSessionFactoryBuilder 构建 SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return sqlSessionFactory;
    }

    /**
     *  使用程序传递方式传递参数
     *  j解密用户名 密码 连接数据库
     * @return
     */
    public static SqlSessionFactory getSession3() throws IOException {
        SqlSessionFactory sqlSessionFactory = null;
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        InputStream in = Resources.getResourceAsStream("db.properties");
        Properties properties = new Properties();
        try {
            properties.load(in);
            String userName = properties.getProperty("name");
            String password = properties.getProperty("password");
            //解密用户和密码，并在属性中重置
//            properties.put("name", CodeUtil.decode(userName));
//            properties.put("password", CodeUtil.decode(password));
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream,properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSessionFactory;
    }


}
