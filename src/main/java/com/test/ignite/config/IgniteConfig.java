package com.test.ignite.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.jdbc.TcpDiscoveryJdbcIpFinder;
import org.apache.logging.slf4j.SLF4JLogger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Slf4j
@Configuration
public class IgniteConfig {

    @Resource
    private IgniteProperties igniteProperties;

    @Resource
    private IgniteStorageProperties igniteStorageProperties;

    public IgniteConfig() {
    }

    @Bean(name = "igniteDataSource")
    @ConditionalOnClass(HikariDataSource.class)
    @ConditionalOnBean(IgniteDataSourceProperties.class)
    @ConditionalOnMissingBean(DataSource.class)
    @ConditionalOnProperty(value = "ignite.type", havingValue = "jdbc", matchIfMissing = true)
    public DataSource igniteDataSource(IgniteDataSourceProperties igniteDataSourceProperties){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setPoolName("ignite-ds-pool");
        dataSource.setJdbcUrl(igniteDataSourceProperties.getUrl());
        dataSource.setDriverClassName(igniteDataSourceProperties.getDriverClassName());
        dataSource.setUsername(igniteDataSourceProperties.getUsername());
        dataSource.setPassword(igniteDataSourceProperties.getPassword());
        log.info("init igniteDataSource success");
        return dataSource;
    }

    @Bean
    @ConditionalOnProperty(value = "ignite.enable", havingValue = "true", matchIfMissing = true)
    public Ignite ignite(IgniteConfiguration igniteConfiguration){
        Ignite ignite = Ignition.start(igniteConfiguration);
        log.info("{} ignite started with discovery type {}", ignite.name(), igniteProperties.getType());
        return ignite;
    }

    @Bean
    @ConditionalOnProperty(value = "ignite.enable", havingValue = "true", matchIfMissing = true)
    public IgniteConfiguration igniteConfiguration(DataSource igniteDataSource){
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
//        igniteConfiguration.setGridLogger(new SLF4JLogger());
        igniteConfiguration.setIgniteInstanceName(igniteProperties.getInstanceName());
        //自动发现配置
        log.info("discovery ip finder {}", igniteProperties.getType());
        if(IgniteType.JDBC.equals(igniteProperties.getType())){
            TcpDiscoverySpi tcpDiscoverySpi = new TcpDiscoverySpi();
            TcpDiscoveryJdbcIpFinder ipFinder = new TcpDiscoveryJdbcIpFinder();
            ipFinder.setDataSource(igniteDataSource);
            ipFinder.setInitSchema(true);
            tcpDiscoverySpi.setIpFinder(ipFinder);
            igniteConfiguration.setDiscoverySpi(tcpDiscoverySpi);
        }
        //持久化
        log.info("native storage {}", igniteStorageProperties.getEnable());
        if(igniteStorageProperties.getEnable()){
            DataStorageConfiguration storageConfiguration = new DataStorageConfiguration();
            storageConfiguration.setStoragePath(igniteStorageProperties.getPath());
            storageConfiguration.getDefaultDataRegionConfiguration()
                    .setName(igniteStorageProperties.getRegionName())
                    .setPersistenceEnabled(true);
            storageConfiguration.setWalMode(igniteStorageProperties.getWalMode());
            //walPath与walArchivePath一致时表示禁止使用walArchive
            storageConfiguration.setWalPath(igniteStorageProperties.getWalPath());
            storageConfiguration.setWalArchivePath(igniteStorageProperties.getWalArchivePath());
            storageConfiguration.setWalCompactionEnabled(igniteStorageProperties.getWalCompactionEnabled());
            igniteConfiguration.setDataStorageConfiguration(storageConfiguration);

        }
        return igniteConfiguration;
    }

}
