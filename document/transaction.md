##  Spring数据库事务管理

#### 配置事务管理器

MyBatis 框架用的最多的是事务管理器是 **org.springframework.jdbc.datasource.DataSourceTransactionManager**

Hibernate 框架用到 spring-orm 包 **org.springframework.orm.hibernate4.HibernateTransactionManager**


>  配置数据源事务管理器

      <bean id="transcationManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
          <property name="dataSource" ref="dataSource"/>
      </bean>
      
> Spring 中可以使用声明式事务或者编程式事务
- 编程式事务 缺点：会产生冗余，代码可读性较差

> 声明式事务
- XML配置 - 不常用
- 注解事务 - 注解**@Transactional**    
   使用声明式事务需要配置注解驱动，需要加入以下代码就可以使用**@Transactional** 配置事务     
   
        <tx:annotation-driven transaction-manager="transcationManager"/>

> Transactional 配置项

配置项     | 含义 | 备注
-------- | --- | ----
value     | 定义事务管理器
transactionManager    | 定义事务管理器  | 他是Spring IOC容器里的一个Bean id，这个bean需要实现接口 PlatformTransactionManager
**issolation**     | 隔离级别   |   他是Spring IOC容器里的一个Bean id，这个bean需要实现接口 PlatformTransactionManager
**propagation**     | 传播行为  | 这是一个数据库在多个事务同时存在时的概念，默认取值是数据库默认隔离级别
timeout   | 超时时间  | 单位为秒 
readOnly   | 是否开启只读事务   | 默认值为false
rollbackFor   | 回滚事务的异常类定义   | 只有当方法发生异常时，回滚事务
rollbackForClassName   | 回滚事务的异常类名定义   | 同rollbackFor，只是使用类名称定义
norollbackFor   | 当产生哪些异常不回滚事务   | 当产生所定义异常时，Spring将继续提交事务
norollbackForClassName   | 同norollbackFor   |  同rollbackFor，只是使用类名称定义


### 数据库的相关知识

> 数据库事务ACID特性

- 原子性（Atomicity）：整个事务中的所有操作，要么全部完成，要么全部不完成，不能能停滞在中间的某个环节。事务在执行过程中发生错误，
    会被回滚到事务开始前的状态，就像这个事务从来没被执行过一样。
- 一致性（Consistency）：指一个事务可以改变封装状态（除非他是一个只读的）。事务必须始终保持系统处于一致的状态，不管在任何给定的时间并发事务有多少
- 隔离性（Isolation）：它是指两个事务间的隔离程度
- 持久性（Durability）：在事务完成以后，该事务对数据库所做的更改便持久保存在数据库之中，并不会回滚。

##  **隔离级别**
- 脏读：最低的隔离级别，允许一个事务去读取另一个事务中未提交的数据。
- 读写提交 ：一个事务只能读取另一个事务已经提交的数据。
- 可重复读：可重复读会使得同一条数据库记录的读/写按照一个序列化进行操作，不会产生交叉的情况，这样就能保证同一条数据的一致性。
- 序列化：消除数据库事务之间并发产生数据不一致的问题。

隔离级别 | 脏读 | 不可重读    | 幻读
---- | --- | ----   | ----
脏读     |    √   |       √      |     √ 
读写提交     |    ×   |       √      |     √ 
可重复读     |    ×   |       ×      |     √ 
序列化     |    ×  |       ×      |     ×

> 大部分场景下，企业会选择读/写提交的方式设置事务。这样既有助于提高并发，有压制了脏读。但是对于数据一致性问题并没有解决

- READ_UNCOMMITTED : 脏读
- READ_COMMITTED : 读写提交
- REPEATABLE_READ : 可重复读
- SERIALIZABLE : 序列化

> 隔离级别需要根据并发的大小和性能来作出决定，对于并发不大又要保证数据安全性的可以使用序列化的隔离级别，这样就能够保证数据库在
    多事务环境中的一致性

- MySQL 可以支持4种隔离级别，默认是可重复读的隔离级别
- Oracle 只能支持读写提交和序列化两种隔离级别，默认为读写提交


##  **传播行为**
在Spring中传播行为的类型，是通过一个枚举类型去定义的，这个枚举类型是 org.springframework.transaction.annotation.Propagation

- REQUIRED ：Spring默认的传播行为。当方法调用时，如果不存在当前事务，那么久创建事务，如果之前的方法已经存在事务了，那么久沿用之前的事务
- SUPPORTS ：当方法调用时，如果不存在当前事务，那么不启用事务；如果存在当前事务，那么久沿用当前事务
- MANDATORY ：方法必须在事务内运行，如果不存在当前事务，那么就抛出异常
- **REQUIRES_NEW** ：无论是否存在当前事务，方法都会在新的事务中运行
- NOT_SUPPORTED ：不支持事务，如果不存在当前事务也不会创建事务；如果存在当前事务，则挂起它，直到该方法结束后才恢复当前事务。
    适用于那些不需要事务的SQL。
- NEVER ：不支持事务，只有在没有事务的环境中才能运行它。如果该方法存在当前事务，则抛出异常
- **NESTED** ：嵌套事务，也就是调用方法抛出异常只回滚自己内部执行的SQL，而不回滚主方法的SQL，他的实现存在两种情况，
    如果当前数据库支持保存点（savepoint），那么他就会在当前事务上使用保存点技术；
    如果发生异常则将方法内执行的SQL回滚到保存点上，而不是全部回滚，否则就等同于 **REQUIRES_NEW** 创建新的事务运行方法代码
    

###  **@Transactional 的自调用失效问题**
> 注解 **@Transactional** 的底层实现是Spring AOP 技术，而Spring AOP 技术使用的是动态代理，所以对于静态（static）方法和非public方法，
    注解 **@Transactional** 是失效的。同时，自调用也会使 **@Transactional** 失效。
    
    
> 自调用：就是一个类的一个方法去调用自身另外一个方法的过程。

> 解决自调用失效问题

    1.实现 ApplicationContextAware 接口
    2.创建私有对象 private ApplicationContext applicationContext = null;
    3.实现 setApplicationContext 方法
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
         	this.applicationContext = applicationContext;
        }
    4.使用 RoleService roleService= applicationContext.getBean(RoleService.class); 获取实例
        
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
      	public int insertRoles1(List<Role> roleList) {
      		for (Role role : roleList) {
      			//从容器中获取RoleService对象，实际是一个代理对象
      			RoleService roleService= applicationContext.getBean(RoleService.class);
      			roleService.insertRole(role);
      		}
      		return roleList.size();
      	}
    

    












