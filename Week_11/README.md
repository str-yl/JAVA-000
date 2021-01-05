学习笔记

1、基于redis实现分布式锁，模拟减库存场景：
实现思路：
             
    1）主要通过jedis.set(lockKey, requestId, "NX", "PX", expireTime);
    
    方法实现加锁；
                       
    2）如果加锁成功，则只需redis里的库存key的value减一操作(获取库存value值之后执行
    
    减一操作则封装在lua脚本里提交给redis执行，保证整个操作的原子性)；

    3）最后执行释放锁的操作同样封装在lua脚本里提交给redis执行，保证整个操作的原子性；
    
    4）最后使用100个线程模拟100个用户秒杀5个商品的场景，并发提交到executorService线程池

    触发抢购动作；

主要的实现类为：io.kimmking.cache.lockbyjedis、
io.kimmking.cache.lockbyjedis.bugProd。

改进:基于redis的分布式锁需要考虑到重入锁的场景，后续将
针对该点进行优化。