### ACT_RE_PROCDEF（流程定义数据表）



#### 1、根据DEPLOYMENT_ID_（部署表ID）排序

```
/** Order by deployment id (needs to be followed by {@link #asc()} or {@link #desc()}). */
 ProcessDefinitionQuery orderByDeploymentId();
```

#### 2、根据流程定义的ID排序

```
/** Order by process definition key (needs to be followed by {@link #asc()} or {@link #desc()}). */
 ProcessDefinitionQuery orderByProcessDefinitionKey();
```

#### 3、根据主键ID排序

```
 /** Order by the id of the process definitions (needs to be followed by {@link #asc()} or {@link #desc()}). */
 ProcessDefinitionQuery orderByProcessDefinitionId();
```


#### 4、根据 version（版本）排序

```
 /** Order by the version of the process definitions (needs to be followed by {@link #asc()} or {@link #desc()}). */
 ProcessDefinitionQuery orderByProcessDefinitionVersion();
```

#### 5、根据 name（名称）排序

```
 /** Order by the name of the process definitions (needs to be followed by {@link #asc()} or {@link #desc()}). */
 ProcessDefinitionQuery orderByProcessDefinitionName();
 
```


#### 6、根据tenant（ 租户）排序 ---- 我们的表是空值

```
 
/**
 * Order by tenant id (needs to be followed by {@link #asc()} or
 * {@link #desc()}).
 */
 ProcessDefinitionQuery orderByTenantId();
```

#### 7、根据category（类别）排序  

类别：流程定义的Namespace就是类别

```
/** Order by the category of the process definitions (needs to be followed by {@link #asc()} or {@link #desc()}). */ 
 ProcessDefinitionQuery orderByProcessDefinitionCategory();

```

