package com.test.config;

import com.test.dao.jdbc.UserCacheStore;
import com.test.pojo.User;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.configuration.WALMode;
import org.apache.ignite.springdata20.repository.support.IgniteRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.configuration.FactoryBuilder;

@Configuration
public class IgniteConfig {
    /**
     * Creating Apache Ignite instance bean. A bean will be passed to {@link IgniteRepositoryFactoryBean} to initialize
     * all Ignite based Spring Data repositories and connect to a cluster.
     */
    @Bean
    public Ignite igniteInstance() {
        IgniteConfiguration cfg = new IgniteConfiguration();

        //配置节点名称，相同节点名称会自动发现组成集群
        cfg.setIgniteInstanceName("springDataNode");

        // Enabling peer-class loading feature.
        cfg.setPeerClassLoadingEnabled(true);

        //配置允许持久化到磁盘
//        DataStorageConfiguration storageCfg = new DataStorageConfiguration();
//        storageCfg.getDefaultDataRegionConfiguration().setPersistenceEnabled(true);
//        storageCfg.setWalMode(WALMode.FSYNC);
//        cfg.setDataStorageConfiguration(storageCfg);

        //配置缓存
        CacheConfiguration userCacheConfiguration = new CacheConfiguration();
        userCacheConfiguration.setName("userCache");
        userCacheConfiguration.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        userCacheConfiguration.setIndexedTypes(Long.class, User.class);

        //配置缓存与数据库的通读通写实现类
        userCacheConfiguration.setCacheStoreFactory(FactoryBuilder.factoryOf(UserCacheStore.class));
        userCacheConfiguration.setReadThrough(true);
        userCacheConfiguration.setWriteThrough(true);

        //配置缓存为后写缓存，缓存与数据库非实时同步
//        userCacheConfiguration.setWriteBehindEnabled(true);
//        //缓冲区大小
//        userCacheConfiguration.setWriteBehindFlushSize(10240);
//        //刷新间隔
//        userCacheConfiguration.setWriteBehindFlushFrequency(5000);

        //设置缓存配置到上下文环境中
        cfg.setCacheConfiguration(userCacheConfiguration);

        //激活集群使持久化生效
        Ignite ignite = Ignition.start(cfg);
        ignite.cluster().active(true);

        //缓存预加载
        ignite.cache("userCache").loadCache(null);
        return ignite;
    }
}