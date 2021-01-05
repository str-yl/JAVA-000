package io.kimmking.cache.lockbyjedis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import java.util.Collections;

@Slf4j
public class bugProd implements Runnable{

    private final String userCode;

    private final Long num;

    private final String lockKey;

    private final static String PROD = "prod-";

    public bugProd(String userCode, Long num, String lockKey) {
        this.userCode = userCode;
        this.num = num;
        this.lockKey = lockKey;
    }

    @Override
    public void run() {
        //让线程阻塞，使后续任务进入缓存队列
        System.out.println("当前系统线程:"+Thread.currentThread().getName());
        Jedis jedis = JedisLock.getJedis();
        Long waitEnd = System.currentTimeMillis() + 500;
        String value = JedisLock.tryGetDistributedLock(jedis,lockKey,userCode,500,waitEnd);//加锁成功,获取锁的持有者
        String lk = null;
        if(value != null && value.equals(userCode)){//加锁成功后查询库存
            try {
                lk = lockKey;
                //这个get操作可以和下面的减库存封装在一个lua脚本里?
                Long stock = Long.valueOf(jedis.get(PROD + lk));
                if( stock >= num){
                    //value的值通过lua脚本模拟减库存(-1)，保证原子性
                    String script = "return redis.call('incrby', KEYS[1], ARGV[1])";
                    Object eval = jedis.eval(script, Collections.singletonList(PROD + lk), Collections.singletonList(String.valueOf(0-num)));
                    if(null != eval &&  Integer.valueOf(String.valueOf(eval)) >= 0){
                        System.out.println("用户【"+userCode+"】秒杀商品【"+lk+"】成功,库存剩余:"+String.valueOf(eval));
                    }
                }else{
                    System.out.println("用户【"+userCode+"】秒杀商品【"+lk+"】失败,库存剩余:"+stock.toString());
                }
                Thread.sleep(100);//这里为了更真实的模拟多线程并发,这里线程获取到锁后,线程休眠100ms
                //}
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("当前用户【"+userCode+"】抢购商品【"+lk+"】(锁获取成功，更新库存量失败),请再试");
                log.error("当前用户【"+userCode+"】抢购商品【"+lk+"】(锁获取成功，更新库存量失败),请再试", e);
            } finally {
                System.out.println("用户【"+userCode+"】秒杀商品【"+lk+"】的线程释放锁");
                JedisLock.releaseDistributedLock(jedis,lockKey,userCode);//处理完扣减库存业务释放锁,把抢购这件商品的机会留给其余用户
                //一定要记得将连接归还给连接池
                jedis.close();
            }
        }else{
            System.out.println("当前用户【"+userCode+"】抢购商品【"+lockKey+"】的线程加锁失败,未抢购到,请再试");
            jedis.close();
        }
    }
}
