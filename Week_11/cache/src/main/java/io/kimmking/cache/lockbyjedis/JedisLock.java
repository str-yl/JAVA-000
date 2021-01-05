package io.kimmking.cache.lockbyjedis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Slf4j
@Component
public class JedisLock {

    @Autowired
    private JedisProperties jedisProperties;

    private static final Long RELEASE_SUCCESS = 1L;

    private static JedisPool pool;

    public static Jedis getJedis(){
        return pool.getResource();
    }

    @PostConstruct
    private void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();//连接池配置类

        config.setMaxIdle(jedisProperties.getMaxIdle());
        config.setMaxTotal(jedisProperties.getMaxTotal());
        config.setMinIdle(jedisProperties.getMinIdle());
        config.setMaxWaitMillis(jedisProperties.getMaxWaitMillis());
        config.setTestOnBorrow(true);
        config.setTestOnReturn(false);
        config.setBlockWhenExhausted(false);

        pool = new JedisPool(config, jedisProperties.getHost(), jedisProperties.getPort(),
                jedisProperties.getTimeOut()*1000);
    }

    /**
     * 获取锁
     * @param jedis
     * @param lockKey
     * @param requestId
     * @param expireTime
     * @param waitTime 获取锁操作的限制时间,如果超过限制时间将停止获取锁
     * @return
     */
    public static String tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime, Long waitTime) {
        try {
            while (System.currentTimeMillis() < waitTime) {
                String result = jedis.set(lockKey, requestId, "NX", "PX", expireTime);
                if ("OK".equals(result)) {
                    return requestId;
                }
            }
        } catch (Exception e) {
            log.error(requestId + " got Lock failed : ", e);
        }
        return null;
    }

    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        //将操作封装在lua脚本发送给redis server执行来保证多个操作的原子性
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

}
