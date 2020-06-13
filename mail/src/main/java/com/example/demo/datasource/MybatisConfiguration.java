package com.example.demo.datasource;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 将多数据源注入到sqlSessionFactory中
 * 前面提到了这里通过扩展mybatis-spring-boot-starter的org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration来实现多数据源注入的
 */

@Configuration
@AutoConfigureAfter({DataSourceConfiguration.class})
public class MybatisConfiguration extends MybatisAutoConfiguration {

    private static Log logger = LogFactory.getLog(MybatisConfiguration.class);

    @Resource(name = "masterDataSource")
    private DataSource masterDataSource;
    @Resource(name = "slaveDataSource")
    private DataSource slaveDataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        return super.sqlSessionFactory(roundRobinDataSouceProxy());
    }

    public AbstractRoutingDataSource roundRobinDataSouceProxy() {
        ReadWriteSplitRoutingDataSource proxy = new ReadWriteSplitRoutingDataSource();
        //Map<Object, Object> targetDataResources = new ClassLoaderRepository.SoftHashMap();
        Map<Object, Object> targetDataResources = new HashMap<>();
        targetDataResources.put(DbContextHolder.DbType.MASTER, masterDataSource);
        targetDataResources.put(DbContextHolder.DbType.SLAVE, slaveDataSource);
        proxy.setDefaultTargetDataSource(masterDataSource);//默认源
        proxy.setTargetDataSources(targetDataResources);
        return proxy;
    }
}
