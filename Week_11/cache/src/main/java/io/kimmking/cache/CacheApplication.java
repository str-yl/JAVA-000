package io.kimmking.cache;

import io.kimmking.cache.lockbyjedis.bugProd;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.*;

@SpringBootApplication(scanBasePackages = "io.kimmking.cache")
@MapperScan("io.kimmking.cache.mapper")
//@EnableCaching
public class CacheApplication {

	public static void main(String[] args) {

		SpringApplication.run(CacheApplication.class).close();

	}

	@Bean
	public ApplicationRunner runner() {
		return args -> {
			//使用固定线程数为4 的线程池处理并发请求
			ExecutorService executorService = Executors.newFixedThreadPool(10);
			//50个人抢购编码为101的共计5个商品
			for(int i=0;i<100;i++) {
				String userCode = 10*(i+1)+"";
				//System.out.println("用户"+(i+1)+"的编码:"+userCode);
				executorService.execute(new bugProd(userCode,1L,"001"));
			}
		};
	}

}
