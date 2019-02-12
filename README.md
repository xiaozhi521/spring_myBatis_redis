# spring_myBatis_redis
### 一、创建maven工程

### 二、[加入maven依赖](https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/pom.xml)

web.xml :SpringMVC 配置

```xml
<servlet>
    <servlet-name>springMVCDispatcher</servlet-name>
    <servlet-class>
      org.springframework.web.servlet.DispatcherServlet
    </servlet-class>
    <!--读取配置文件-->
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath*:spring/springmvc.xml</param-value>
    </init-param>
    <load-on-startup>1</load-on-startup>
  </servlet>
  <!--映射-->
  <servlet-mapping>
    <servlet-name>springMVCDispatcher</servlet-name>
    <url-pattern>*.mvc</url-pattern>
    <!--配置多个映射-->
    <!--<url-pattern>*.do</url-pattern>-->
  </servlet-mapping>
```

[springmvc.xml 配置](https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/src/main/resources/spring/springmvc.xml)

### 三、Spring整合MyBatis 

**javaBean 创建省略**

1、[applicationContext-MapperFactoryBean.xml  配置](https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/src/main/resources/spring/applicationContext-MapperFactoryBean.xml)

```xml
<!-- 数据库连接池 -->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.SimpleDriverDataSource">
        <property name="username" value="${jdbc.username}" />
        <property name="password" value="root"/>
        <property name="driverClass" value="com.mysql.jdbc.Driver" />
        <property name="url" value="jdbc:mysql://localhost:3306/mybatis001" />
    </bean>

    <!--集成mybatis-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource" />
        <property name="configLocation" value="classpath:sqlMapConfig.xml" />
    </bean>
    <!--配置数据源事务管理器-->
    <bean id="transcationManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    <!--使用注解定义事务-->
    <tx:annotation-driven transaction-manager="transcationManager"/>

    <!--采用自动扫描方式创建mapper bean-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.cn" />
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
        <!-- 指定标注才扫描成为Mapper -->
        <property name="annotationClass" value="org.springframework.stereotype.Repository" />
    </bean>
```

2、[db.properties](https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/src/main/resources/db.properties)

```pro
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/mybatis001
jdbc.username=root
jdbc.password=root
```

3、[sqlMapConfig.xml](https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/src/main/resources/sqlMapConfig.xml)

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <!-- 这个配置使全局的映射器启用或禁用缓存 -->
        <setting name="cacheEnabled" value="true" />
        <!-- 允许 JDBC 支持生成的键。需要适合[修改为：适当]的驱动。如果设置为true，则这个设置强制生成的键被使用，尽管一些驱动拒绝兼容但仍然有效（比如 Derby） -->
        <setting name="useGeneratedKeys" value="true" />
        <!-- 配置默认的执行器。SIMPLE 执行器没有什么特别之处。REUSE 执行器重用预处理语句。BATCH 执行器重用语句和批量更新  -->
        <setting name="defaultExecutorType" value="REUSE" />
        <!-- 全局启用或禁用延迟加载。当禁用时，所有关联对象都会即时加载 -->
        <setting name="lazyLoadingEnabled" value="true"/>
        <!-- 设置超时时间，它决定驱动等待一个数据库响应的时间  -->
        <setting name="defaultStatementTimeout" value="25000"/> 
    </settings>
    <!-- 别名配置 -->
    <typeAliases>
        <package name="com.cn.bean"/>
    </typeAliases>
 
    <!-- 指定映射器路径 -->
    <mappers>
        <!--<package name="com.cn.mapper"/>-->
        <package name="com.cn.mappings"/>
    </mappers>
</configuration>
```

4、创建 [RoleMapper.java](https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/src/main/java/com/cn/mappings/RoleMapper.java)

```java
@Repository
public interface RoleMapper {
    public Role getRole(Long id);
    public int deleteRole(Long id);
    public int insertRole(Role role);
    public int updateRole(Role role);
    public List<Role> findRoles(@Param("roleName") String roleName,@Param("note")String note);
}
```

5、创建 [RoleMapper.xml](https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/src/main/resources/com/cn/mappings/RoleMapper.xml)

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.cn.mappings.RoleMapper">
    <select id="getRole" resultType="Role">
        SELECT id,role_name as roleName,note FROM t_role where id = #{id}
    </select>

    <delete id="deleteRole">
        DELETE FROM t_role where id = #{id}
    </delete>
    <insert id="insertRole" parameterType="com.cn.bean.Role" useGeneratedKeys="true" keyProperty="id">
        INSERT INTO t_role (role_name,note) VALUES (#{roleName},#{note})
    </insert>

    <update id="updateRole" parameterType="com.cn.bean.Role">
        UPDATE t_role set role_name = #{roleName},note = #{note} WHERE id = #{id}
    </update>

    <select id="findRoles" resultType="Role">
        SELECT id,role_name as roleName,note FROM t_role
          <where>
              <if test="roleName != null">
                  role_name like concat('%',#{roleName},'%')
              </if>
              <if test="note != null">
                  note like concat('%',#{note},'%')
              </if>
          </where>
    </select>
</mapper>
```

6、[RoleServiceImpl.java](https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/src/main/java/com/cn/serviceImpl/RoleServiceImpl.java)

```java
	@Autowired
	private RoleMapper roleMapper = null;
	/**
	 * @param id 角色编号
	 * @return
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public Role getRole(Long id) {
		System.out.println("11111111111111");
		return roleMapper.getRole(id);
	}
	/**
	 * @param role
	 *            角色对象
	 * @return 角色对象（会回填主键）
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public Role insertRole(Role role) {
		roleMapper.insertRole(role);
		return role;
	}

	/**
	 * @param role
	 *            角色对象
	 * @return 影响条数
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public int updateRole(Role role) {
		return roleMapper.updateRole(role);
	}

	/**
	 * @param id
	 *            角色编号
	 * @return 返回删除记录数
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public int deleteRole(Long id) {
		return roleMapper.deleteRole(id);
	}

	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<Role> findRoles(String roleName, String note) {
		return roleMapper.findRoles(roleName, note);
	}
```

7、[RoleController.java](https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/src/main/java/com/cn/controller/RoleController.java)

```java
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{

    @Autowired
    private RoleService roleService;

    public RoleController() {
        System.out.println("RoleController Constructor...");
    }

    @RequestMapping("/{id}.mvc")
    @ResponseBody
    public Object getRole(@PathVariable Long id){
        Role role = roleService.getRole(id);

        return role;
    }
}
```

###  四、整合Redis

1、[applicationContext-MapperFactoryBean.xml  配置](https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/src/main/resources/spring/applicationContext-MapperFactoryBean.xml)

```xml
<import resource="spring-redis.xml" />
```

2、[spring-redis.xml](https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/src/main/resources/spring/spring-redis.xml)

```xml
<!-- 连接池基本参数配置，类似数据库连接池 -->
    <!--<context:property-placeholder location="classpath*:spring/redis.properties"-->
                                  <!--ignore-unresolvable="true" />-->
    <!--Redis配置-->
    <!--JedisPoolConfig - 连接池配置-->
    <bean id="poolConfig" class="redis.clients.jedis.JedisPoolConfig">
        <!--最大空闲连接数-->
        <!--<property name="maxIdle" value="${redis.maxIdle}"/>-->
        <!--最大连接数-->
        <!--<property name="maxTotal" value="${redis.maxTotal}"/>-->
        <!--<property name="maxWaitMillis" value="${redis.maxWaitMillis}"/>-->
        <property name="maxIdle" value="50"/>
        <!--最大连接数-->
        <property name="maxTotal" value="600"/>
        <property name="maxWaitMillis" value="1000"/>
    </bean>

    <!--配置序列化器-->
    <!--使用JDK 序列化器进行转化-->
    <bean id="jdkSerializationRedisSerializer" class="org.springframework.data.redis.serializer.JdkSerializationRedisSerializer"/>
    <!--使用 字符串 序列化器进行转化-->
    <bean id="stringRediSerializer" class="org.springframework.data.redis.serializer.StringRedisSerializer"/>

    <!--
        Spring 所提供的的连接工厂，在 Data Redis 方案中他提供了4中工厂模型
        JredisConnectionFactory, LettuceConnectionFactory, SrpConnectionFactory, JedisConnectionFactory
    -->
    <bean id="connectionFactory" class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory">
        <!--<property name="hostName" value="${redis.host}" />-->
        <!--<property name="port" value="${redis.port}"/>-->
        <!--<property name="password" value="${redis.password}"/>-->
        <property name="hostName" value="127.0.0.1" />
        <property name="port" value="6379"/>
        <property name="password" value="qwertyuiop@123"/>
        <property name="poolConfig" ref="poolConfig"/>
    </bean>

    <!--配置spring关于redis字符串的运行环境-->
    <bean id="redisTemplate" class="org.springframework.data.redis.core.RedisTemplate">
        <property name="connectionFactory" ref="connectionFactory"/>
        <!--修改默认的序列化器为字符串序列化-->
        <property name="defaultSerializer" ref="stringRediSerializer"/>

        <property name="keySerializer" ref="stringRediSerializer"/>
        <property name="valueSerializer" ref="jdkSerializationRedisSerializer"/>

        <property name="hashKeySerializer" ref="stringRediSerializer"/>
        <property name="hashValueSerializer" ref="jdkSerializationRedisSerializer"/>
    </bean>

    <!--
       使用注解驱动，其中 cache-manage 默认值是 cachManage ，
       如果使用的缓存管理器名称也是 cachManage ，则无需重新定义
   -->
    <cache:annotation-driven cache-manager="redisCacheManager"/>

    <!--定义缓存管理器，如果使用  id="cachManage" ， 则驱动不需要显示配置 cache-manager 属性-->
    <bean id="redisCacheManager" class="org.springframework.data.redis.cache.RedisCacheManager">
        <constructor-arg index="0" ref="redisTemplate"/>
        <!--定义默认超时时间，单位秒-->
        <property name="defaultExpiration" value="6000"/>
        <!--缓存管理器名称-->
        <property name="cacheNames">
            <list>
                <value>redisCacheManager</value>
            </list>
        </property>
    </bean>
</beans>
```

3、[RoleServiceImpl.java](https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/src/main/java/com/cn/serviceImpl/RoleServiceImpl.java)

```java
	@Autowired
	private RoleMapper roleMapper = null;

	/**
	 *  使用 @Cacheable 定义缓存策略，
	 *  当缓存中有值，则返回缓存数据，否则访问方法得到数据
	 *  通过value 引用缓存管理器，通过 key 定义键
	 * @param id 角色编号
	 * @return
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	@Cacheable(value = "redisCacheManager", key = "'redis_role_'+#id")
	public Role getRole(Long id) {
		System.out.println("11111111111111");
		return roleMapper.getRole(id);
	}
	/**
	 * 使用@CachePut则表示无论如何都会执行方法，最后将方法的返回值再保存到缓存中
	 * 使用在插入数据的地方，则表示保存到数据库后，会同期插入到Redis缓存中
	 *
	 * @param role
	 *            角色对象
	 * @return 角色对象（会回填主键）
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	@CachePut(value = "redisCacheManager", key = "'redis_role_'+#result.id")
	public Role insertRole(Role role) {
		roleMapper.insertRole(role);
		return role;
	}

	/**
	 * 使用@CachePut，表示更新数据库数据的同时，也会同步更新缓存
	 *
	 * @param role
	 *            角色对象
	 * @return 影响条数
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	@CachePut(value = "redisCacheManager", key = "'redis_role_'+#role.id")
	public int updateRole(Role role) {
		return roleMapper.updateRole(role);
	}

	/**
	 * 使用@CacheEvict删除缓存对应的key
	 *
	 * @param id
	 *            角色编号
	 * @return 返回删除记录数
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	@CacheEvict(value = "redisCacheManager", key = "'redis_role_'+#id")
	public int deleteRole(Long id) {
		return roleMapper.deleteRole(id);
	}
```

### 五、加入日志

[log4j.properties](https://github.com/xiaozhi521/spring_myBatis_redis/blob/master/src/main/resources/log4j.properties)

```pr
log4j.properties£¬
log4j.rootLogger=DEBUG , stdout
log4j.logger.org.mybatis=DEBUG
log4j.logger.org.springframework=DEBUG
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%5p %d %C: %m%n
```

```java
Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
```

###  六、自调用失效问题

同一类的方法调用自己方法，产生自调用[插入：失效]问题 --- 自调用失效

```java
	/**
	 *  同一类的方法调用自己方法，产生自调用[插入：失效]问题 --- 自调用失效
	 * @param roleList
	 * @return
	 */
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)  
	public int insertRoles(List<Role> roleList) {
	    for (Role role : roleList) {
			//同一类的方法调用自己方法，产生自调用[插入：失效]问题
	        this.insertRole(role);
	    }
	    return roleList.size();
	}
```



*SeriviceImpl 实现 ApplicationContextAware 接口，重写setApplicationContext方法

```java
public class RoleServiceImpl implements RoleService, ApplicationContextAware {

//	public Logger logger = Logger.getLogger(RoleServiceImpl.class.getName());
	Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Autowired
	private RoleMapper roleMapper = null;

	private ApplicationContext applicationContext = null;

	/**
	 *  解决自调用失效问题
	 * @param roleList
	 * @return
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public int insertRoles1(List<Role> roleList) {
		for (Role role : roleList) {
			//从容器中获取RoleService对象，实际是一个代理对象
			RoleService roleService= applicationContext.getBean(RoleService.class);
			roleService.insertRole(role);
		}
		return roleList.size();
	}
	//使用生命周期的接口方法，获取IoC容器
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
```





