####分布式、高并发下的id生成要求
    - ID唯一
    - 趋势递增
    - 效率高（生成，使用，索引）
    - 控制并发
    
### 策略一：UUID、GUID（通用唯一识别码）
  - UUID按照开放软件基金会（OSF）指定的标准计算。
    用到了以太网卡地址MAC、纳秒级时间、芯片ID码和许多可能的数字
  - 由以下几部分的组合
    - 当前日期和时间
    - 时钟序列
    - 全局唯一的IEEE机器序列识别号（如果有网卡，从网卡中获得，没有网卡以其他方式获得）
  - 示例UUID：长度为36的字符串：6098abb5-9cfd-444e-b957-26f718d2e7c6
  - 优点
    - 使用简单
    - 不依赖其他组件
    - 不影响数据库拓展
  - 缺点
    - 数据库索引效率低
    - 太过于无意义，用户不友好
    - 长度36位的字符串，空间占用太大
    - 应用集群环境，机器多的时候，重复几率大
### 策略二 数据库自增长
  - MySql使用AUTO_INCREMENT、Oracle使用Sequence序列
  - 集群环境下，不同的库，设置不同的初始值，每次自增加100
  - MySql下修改起点和步长
     - set @@auto_increment_offset = 1; --设置起点
     - set @@auto_increment_increment=100; --设置步长为100
     - show variables like 'auto_inc%'; -- 查看参数
  - 优点
    - 无需编码
    - 性能过的去
    - 索引友好
  - 缺点
    - 大表不能做水平分表，否则插入删除易出现问题
    - 依赖前期规划，拓展麻烦
    - 依赖MySQl内部维护“自增锁”，高并发下插入数据影响性能
    - 在业务操作父子表（关联表）插入时，要先父后子
### 策略三 推特的雪花算法
   - snowflake 算法是 Twitter 开源的分布式ID生成算法，结果是一个Long长整型的ID
            
            0-000 0000 0000 0000 0000 0000 0000 0000 0000 0000 00 - 00 0000 0000 - 0000 0000 0000
      
      - 第一位未使用，固定为0，1bit
      - 41位毫秒级时间，41位的长度可以使用69年；
      - 然后是10位节点的ID，最多支持部署1024个节点（一般是数据中心编号和机器编号组成）
      - 最后12位是毫秒内单位的算法调用计数（意味着每个节点每毫秒产生4096个ID序号）
   - 上面四部分加起来是64bit = 8字节 = Long（转换成字符串后长度最多19）
   - 优点
        - 性能较优，速度快
        - 无需第三方依赖，实现也很简单
        - 可以根据实际情况调整和拓展算法，方便灵活
   - 缺点
        - 依赖机器时间，如果发生回拨会导致可能生成id重复
        - 业界使用一般是根据雪花算法进行拓展
### 策略四 基于Redis自增
  - 思路：利用增长计数API，业务系统在自增长的基础上，配合其他信息组装成为一个唯一ID
  - Redis 的 incr(key) API 用于将key的值进行递增，并返回增长数值。如果key不存在，则创建并赋值为0，在进行加1
  - 利用到Redis的特性：单线程原子操作、自增计数API、数据有效期机制
  - 示例：业务编码+地区+自增数值（9 010 000000000001）
    