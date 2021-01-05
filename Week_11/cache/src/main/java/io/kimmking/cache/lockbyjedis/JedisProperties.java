package io.kimmking.cache.lockbyjedis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="spring.jedis")
@Data
public class JedisProperties {

    private String host;
    /**Redis服务器登录密码*/
    private String password;
    /**Redis服务器端口*/
    private int port;

    /**最大连接数, 默认8个*/
    private int maxTotal;
    /**最大空闲连接数, 默认8个*/
    private int maxIdle;
    /**最小空闲连接数, 默认0*/
    private int minIdle;

    private boolean testOnBorrow;

    private boolean testOnReturn;

    /**连接超时s*/
    private int timeOut;

    private long maxWaitMillis;

}
