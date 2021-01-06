package io.kimmking.cache;

import com.alibaba.fastjson.JSON;
import io.kimmking.cache.cluster.ClusterJedis;
import io.kimmking.cache.sentinel.RedisClient_au;
import io.kimmking.cache.sentinel.SentinelJedis;
import io.lettuce.core.RedisURI;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.sentinel.api.StatefulRedisSentinelConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.sentinel.api.sync.RedisSentinelCommands;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication(scanBasePackages = "io.kimmking.cache")
public class RedisApplication {
	@Autowired
	RedisClient_au redisClient;

	public static void main(String[] args) {

		// C1.最简单demo
//		Jedis jedis = new Jedis("localhost", 6379);
//		System.out.println(jedis.info());
//		jedis.set("uptime", new Long(System.currentTimeMillis()).toString());
//		System.out.println(jedis.get("uptime"));

		// C2.基于sentinel和连接池的demo
//		Jedis sjedis = SentinelJedis.getJedis();
//		System.out.println(sjedis.info());
//		sjedis.set("uptime2", new Long(System.currentTimeMillis()).toString());
//		System.out.println(sjedis.get("uptime2"));
//		SentinelJedis.close();

		// C3. 直接连接sentinel进行操作
//		Jedis jedisSentinel = new Jedis("localhost", 26379); // 连接到sentinel
//		List<Map<String, String>> masters = jedisSentinel.sentinelMasters();
//		System.out.println(JSON.toJSONString(masters));
//		List<Map<String, String>> slaves = jedisSentinel.sentinelSlaves("mymaster");
//		System.out.println(JSON.toJSONString(slaves));


		// 作业：
		// 1. 参考C2，实现基于Lettuce和Redission的Sentinel配置
//		Config config = new Config();
//		config.useSentinelServers()
//				.setMasterName("mymaster")
//				.setConnectTimeout(10000)
//				.setCheckSentinelsList(false)
//				//可以用"rediss://"来启用SSL连接
//				.addSentinelAddress("redis://127.0.0.1:26379");
//		RedissonClient redisson = Redisson.create(config);
//		System.out.println(redisson.getSet("one").stream().map(x -> x.toString()));


//		RedisClient client = RedisClient.create();
//		StatefulRedisSentinelConnection connection = client.connectSentinel(RedisURI.builder().withSentinel("127.0.0.1", 26379).withSentinelMasterId("mymaster").build());
//		connection.setAutoFlushCommands(true);
//		RedisSentinelCommands<String, String> commands = connection.sync();
//		System.out.println(commands.getMasterAddrByName("mymaster"));
//		System.out.println(commands.set("kking","","5tgb"));
//		connection.close();
//		client.shutdown();

		// 2. 实现springboot/spring data redis的sentinel配置
		SpringApplication.run(RedisApplication.class).close();



		// 3. 使用jedis命令，使用java代码手动切换 redis 主从
//		 	  Jedis jedis1 = new Jedis("localhost", 6380);
//		    jedis1.slaveofNoOne();
//		    jedis1.set("one","bbff");
//			  Jedis jedis2 = new Jedis("localhost", 6381);
//		    System.out.println(jedis2.get("one"));




		// 4. 使用C3的方式，使用java代码手动操作sentinel
//		Jedis jedisSentinel = new Jedis("localhost", 26379); // 连接到sentinel
//		jedisSentinel.sentinelFailover("mymaster");   //redis切换主从
//		System.out.println(jedisSentinel.sentinelGetMasterAddrByName("mymaster").get(0));  //获取redis主的ip地址

		// C4. Redis Cluster
		// 作业：
		// 5.使用命令行配置Redis cluster:
		//  1) 以cluster方式启动redis-server
		//  2) 用meet，添加cluster节点，确认集群节点数目
		//  3) 分配槽位，确认分配成功
		//  4) 测试简单的get/set是否成功
		// 然后运行如下代码
// 		JedisCluster cluster = ClusterJedis.getJedisCluster();
//		for (int i = 0; i < 1; i++) {
//			System.out.println("cluster:" + i);
//			cluster.set("cluster:" + i, "data:" + i);
//		}
//		System.out.println(cluster.get("cluster:1"));
//		ClusterJedis.close();

		//SpringApplication.run(RedisApplication.class, args);

	}

	@Bean
	public ApplicationRunner runner() {
		return args -> {
			try{
				redisClient.set("testKey","redis测试值");
				String testValue = redisClient.get("testKey");
				System.out.println("从redis获取的key值："+testValue);
			}catch (Exception e){
				e.printStackTrace();
			}
		};
	}

}
