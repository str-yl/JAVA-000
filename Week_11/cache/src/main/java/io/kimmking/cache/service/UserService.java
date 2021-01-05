package io.kimmking.cache.service;

import io.kimmking.cache.entity.User;
import org.springframework.cache.annotation.CacheConfig;

import java.util.List;

//这里的cacheNames配置默认的缓存的value值，如果未指定value就会使用这个默认值
@CacheConfig(cacheNames = "users")
public interface UserService {

    User find(int id);

    List<User> list();

}
