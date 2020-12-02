package com.example.config;

import com.example.bean.MysqlRoutingDataSource;
import com.example.enums.DBTypeEnum;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@EnableTransactionManagement
@Configuration
@MapperScan(basePackages = "com.example.mapper")
@ComponentScan({"com.example.bean"})
public class mybatisConfig {

    @Autowired
    MysqlRoutingDataSource myRoutingDataSource;

    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.master")
    public DataSource masterDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix="spring.datasource.slave1")
    public DataSource slave1DataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix="spring.datasource.slave2")
    public DataSource slave2DataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    public MysqlRoutingDataSource myRoutingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                          @Qualifier("slave1DataSource") DataSource slave1DataSource,
                                          @Qualifier("slave2DataSource") DataSource slave2DataSource){
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DBTypeEnum.MASTER,masterDataSource);
        targetDataSources.put(DBTypeEnum.SLAVE1,slave1DataSource);
        targetDataSources.put(DBTypeEnum.SLAVE2,slave2DataSource);
//        MysqlRoutingDataSource myRoutingDataSource = new MysqlRoutingDataSource();
        myRoutingDataSource.setDefaultTargetDataSource(masterDataSource);  // 设置默认的datasource
        myRoutingDataSource.setTargetDataSources(targetDataSources);  // 该方法是AbstractRoutingDataSource的方法
        return myRoutingDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(myRoutingDataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/example/mapper/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.example.mapper");
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(myRoutingDataSource);
    }
}
