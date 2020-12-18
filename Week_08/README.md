学习笔记
1、Mysql分库分表（2库16个表）的配置文件application.properties如下：
# 这里要注册所有的数据源
spring.shardingsphere.datasource.names=ds0,ds1

# 这是数据源0的配置
spring.shardingsphere.datasource.ds0.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.ds0.url=jdbc:mysql://localhost:3306/ds0?serverTimezone=GMT%2B8&characterEncoding=utf-8
spring.shardingsphere.datasource.ds0.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.ds0.username=root
spring.shardingsphere.datasource.ds0.password=123456
spring.shardingsphere.datasource.ds0.maxActive=20

# 这是数据源1的配置
spring.shardingsphere.datasource.ds1.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.ds1.url=jdbc:mysql://localhost:3307/ds1?serverTimezone=GMT%2B8&characterEncoding=utf-8
spring.shardingsphere.datasource.ds1.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.ds1.username=root
spring.shardingsphere.datasource.ds1.password=123456
spring.shardingsphere.datasource.ds1.maxActive=20


# 分库策略
# 分库的列是order_id
spring.shardingsphere.sharding.default-database-strategy.inline.sharding-column=customer_id
spring.shardingsphere.sharding.default-database-strategy.inline.algorithm-expression=ds_$->{customer_id % 2}
#自定义分库算法
#spring.shardingsphere.sharding.default-database-strategy.standard.precise-algorithm-class-name=com.example.job01.sharding.PreciseShardingAlgorithmImpl

# 分表策略
spring.shardingsphere.sharding.tables.t_order.actual-data-nodes=ds$->{0..1}.t_order$->{0..15}
spring.shardingsphere.sharding.tables.t_order.table-strategy.inline.sharding-column=order_id
spring.shardingsphere.sharding.tables.t_order.table-strategy.inline.algorithm-expression=t_order$->{order_id % 16}
spring.shardingsphere.sharding.tables.t_order.key-generator.column=order_id
spring.shardingsphere.sharding.tables.t_order.key-generator.type=SNOWFLAKE

#spring.shardingsphere.sharding.binding-tables=t_order
spring.shardingsphere.props.sql.show=true

# 广播表, 其主节点是ds0
#spring.shardingsphere.sharding.broadcast-tables=t_config
#spring.shardingsphere.sharding.tables.t_config.actual-data-nodes=ds$->{0}.t_config

#spring.jpa.show-sql=true
server.address=localhost
server.port=18080

mybatis.config-location=classpath:mybatis/mybatis-config.xml

swagger.enabled=true

2、分布式事务：
在Service层的业务方法上通过注解标注分布式事务：
@Override
@ShardingTransactionType(TransactionType.XA)
@Transactional(rollbackFor=Exception.class)
public boolean addBatchOrders(List<t_order> orders) throws Exception{
    boolean res = false;
    res = or.addBatchOrders(orders);
    if(!res){
        throw new Exception("批量添加订单失败");
    }
    return res;
}
    
详细代码见项目代码
