package io.kimmking.cache.lockbyjedis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ConditionalOnClass(JedisPool.class)
@EnableConfigurationProperties(JedisProperties.class)
public class JedisAutoConfiguration {

    private JedisProperties jedisProperties;

    public JedisAutoConfiguration(JedisProperties jedisProperties) {
        this.jedisProperties = jedisProperties;
    }

    @Bean
    @ConditionalOnMissingBean(JedisPool.class)
    public JedisPool jedisPool() {

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(jedisProperties.getMaxIdle());
        poolConfig.setMaxTotal(jedisProperties.getMaxTotal());
        poolConfig.setMinIdle(jedisProperties.getMinIdle());
        poolConfig.setMaxWaitMillis(jedisProperties.getMaxWaitMillis());

        JedisPool jedisPool = new JedisPool(poolConfig, jedisProperties.getHost(), jedisProperties.getPort(),
                jedisProperties.getTimeOut()*1000);
        return jedisPool;
    }
}
