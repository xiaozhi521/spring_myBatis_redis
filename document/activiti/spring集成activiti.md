Spring 集成 activiti

一、环境配置

spring5.0 + mybatis + 	redis + activiti + tomcat9.0

二、加入Maven

    <!-- https://mvnrepository.com/artifact/org.activiti/activiti-spring -->
        <dependency>
          <groupId>org.activiti</groupId>
          <artifactId>activiti-spring</artifactId>
          <version>5.22.0</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
        <dependency>
          <groupId>com.fasterxml.jackson.core</groupId>
          <artifactId>jackson-databind</artifactId>
          <version>2.9.5</version>
        </dependency>

三、activiti.cfg.xml

    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:context="http://www.springframework.org/schema/context" xmlns:tx="http://www.springframework.org/schema/tx"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-2.5.xsd
    http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-3.0.xsd">
    
    
        <bean id="processEngineConfiguration" class="org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration">
            <!-- 数据库连接配置 -->
            <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/mybatis001"></property>
            <property name="jdbcDriver" value="com.mysql.jdbc.Driver"></property>
            <property name="jdbcUsername" value="root"></property>
            <property name="jdbcPassword" value="root"></property>
            <!-- 建表策略 -->
            <property name="databaseSchemaUpdate" value="true"></property>
        </bean>
    
    </beans>

四、集成 spring - applicationContext-MapperFactoryBean.xml

    <!--集成 activiti-->
        <bean id="processEngineConfiguration" class="org.activiti.spring.SpringProcessEngineConfiguration">
            <!-- 数据源 -->
            <property name="dataSource" ref="dataSource" />
            <!-- 配置事务管理器，统一事务 -->
            <property name="transactionManager" ref="transcationManager" />
            <!-- 设置建表策略 -->
            <property name="databaseSchemaUpdate" value="true" />
        </bean>
    
        <bean id="processEngine" class="org.activiti.spring.ProcessEngineFactoryBean">
            <property name="processEngineConfiguration" ref="processEngineConfiguration" />
        </bean>
            <!--bean id repositoryService-->
            <!--RepositoryServicie repositoryService = processEngine.getRepositoryService();-->
        <bean id="repositoryService" factory-bean="processEngine" factory-method="getRepositoryService" />
        <bean id="runtimeService" factory-bean="processEngine" factory-method="getRuntimeService" />
        <bean id="taskService" factory-bean="processEngine" factory-method="getTaskService" />
        <bean id="historyService" factory-bean="processEngine" factory-method="getHistoryService" />
        <bean id="managementService" factory-bean="processEngine" factory-method="getManagementService" />
        <bean id="identityService" factory-bean="processEngine" factory-method="getIdentityService" />
        <bean id="formService" factory-bean="processEngine" factory-method="getFormService" />

五、运行项目，会自动创建 act_* 表。


