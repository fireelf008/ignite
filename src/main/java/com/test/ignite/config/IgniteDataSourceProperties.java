package com.test.ignite.config;

import lombok.Data;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class IgniteDataSourceProperties {

    @Value("${ignite.datasource.url}")
    private String url;

    @Value("${ignite.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${ignite.datasource.username}")
    private String username;

    @Value("${ignite.datasource.password}")
    private String password;

}
