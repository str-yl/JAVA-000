学习笔记

1、redis的主从模式配置：

     redis.conf里主要配置项 slaveof 127.0.0.1 6379

     启动命令 redis-server redis_6379.conf
     
     redis-server redis_6380.conf
     
     通过info命令查看当前redis实例是主的还是从的实例
     
     具体的配置文件见：redis_6379.conf和redis_6380.conf
     
 2、redis的sentinel模式：
     
      主要的配置：
      sentinel monitor mymaster 172.17.0.2 6379 1
      port 26379
      
      具体的配置文件见：sentinel_26379.conf 和 sentinel_26380.conf
      启动命令：redis-sentinel ./sentinel.conf
      
 3、cluster模式的：
     
     主要配置：
     cluster-enabled yes
     
     具体的配置文件见：redis_cluster_6379.conf，redis_cluster_6380.conf，redis_cluster_6381.conf
     
     创建redis集群:
     redis-cli --cluster create 172.17.0.2:6379 172.17.0.3:6380 172.17.0.4:6381 --cluster-replicas 0
     进入任意redis
     #redis-cli -p 6379 -c          #必须加-c，否则测试的时候不会自动切换节点。
     # 127.0.0.1:6380> CLUSTER INFO    #查看Node节点情况
     
二、练习RedisApplication.java里的作业题

   见io.kimmking.cache.RedisApplication.java
 